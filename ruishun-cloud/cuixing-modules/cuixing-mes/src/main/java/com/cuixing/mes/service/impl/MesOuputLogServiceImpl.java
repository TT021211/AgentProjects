package com.cuixing.mes.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.md.ItemException;
import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mes.entity.*;
import com.cuixing.mes.entity.vo.MesOutputLogVo;
import com.cuixing.mes.mapper.MesAnnealingWorkorderMapper;
import com.cuixing.mes.mapper.MesShiftScheduleMapper;
import com.cuixing.mes.mapper.MesTinningWorkorderMapper;
import com.cuixing.mes.service.*;
import com.cuixing.mes.utils.BatchNumberGenerator;
import com.cuixing.mes.utils.SnowflakeIdGenerator;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.RemoteWmsService;
import com.cuixing.system.api.domain.md.*;
import com.cuixing.system.api.domain.sale.SaleOrder;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.mes.mapper.MesOuputLogMapper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 报工记录表;(mes_ouput_log)表服务实现类
 *
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Service
public class MesOuputLogServiceImpl implements IMesOuputLogService {
    private static final Logger log = LoggerFactory.getLogger(MesOuputLogServiceImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor outputLogExecutor; // 注入线程池
    @Autowired
    private MesOuputLogMapper mesOuputLogMapper;

    @Autowired
    private RemoteMdService remoteMdService;

    @Autowired
    private RemoteWmsService remoteWmsService;

    @Autowired
    private RemoteSaleService remoteSaleService;

    @Autowired
    private RemoteSystemService remoteSystemService;

    @Autowired
    private IMesInputLogService iMesInputLogService;

    @Autowired
    private IMesProductWorkorderService iMesProductWorkorderService;

    @Autowired
    private IMesWiredrawingWorkorderService iMesWiredrawingWorkorderService;

    @Autowired
    private IMesEnamellingWorkorderService iMesEnamellingWorkorderService;

    @Autowired
    private MesAnnealingWorkorderService mesAnnealingWorkorderMapper;
    @Autowired
    private MesTinningWorkorderService mesTinningWorkorderMapper;

    @Autowired
    private MesShiftScheduleMapper mesShiftScheduleMapper;

    @Value("${cuixing.mes.outputTime.timeOne}")
    private Double timeOne;
    @Value("${cuixing.mes.outputTime.timeTwo}")
    private Double timeTwo;
    @Value("${cuixing.mes.outputTime.timeThree}")
    private Double timeThree;
    @Value("${cuixing.mes.outputTime.timeFour}")
    private Double timeFour;
    @Value("${cuixing.mes.outputTime.timeFive}")
    private Double timeFive;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MesOuputLog queryById(BigInteger id) {
        return mesOuputLogMapper.queryById(id);
    }

    @Override
    public List<MesOuputLog> selectMesOuputLogWmsList(MesOuputLog mesOuputLog) {
        return mesOuputLogMapper.selectMesOuputLogWmsList(mesOuputLog);
    }

    /**
     * 通过物料唯一码查询单条数据
     *
     * @param outputSn 主键
     * @return 实例对象
     */
    @Override
    public MesOuputLog getInfoByOutputSn(String outputSn) {
        return mesOuputLogMapper.getInfoByOutputSn(outputSn);
    }

    /**
     * 分页查询
     *
     * @param mesOuputLog 筛选条件
     * @return 查询结果
     */
    @Override

    public List<MesOuputLog> selectMesOuputLogList(MesOuputLog mesOuputLog) {
        return mesOuputLogMapper.selectMesOuputLogList(mesOuputLog);
    }

    /**
     * 新增数据
     *
     * @param mesOuputLogs 实例对象
     * @return 最后插入的数据的自增主键ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MesOuputLog> insert(List<MesOuputLog> mesOuputLogs) {
        List<MesOuputLog> result = new ArrayList<>();
        // 将 inputLogs 查询移到循环外，缓存结果
        List<MesInputLog> inputLogs = iMesInputLogService.selectMesInputLogList(null);
        Map<BigInteger, MesInputLog> inputLogMap = inputLogs.stream()
                .collect(Collectors.toMap(MesInputLog::getId, log -> log));

        // 缓存 workorder 查询结果
        Map<BigInteger, MesWiredrawingWorkorder> wiredrawingWorkorderMap = new HashMap<>();
        Map<BigInteger, MesAnnealingWorkorder> annealingWorkorderMap = new HashMap<>();
        Map<BigInteger, MesTinningWorkorder> tinningWorkorderMap = new HashMap<>();

        // 记录每个 taskId 的累计 outputWeight，按 taskType 分组
        Map<String, Map<BigInteger, BigDecimal>> taskWeightMap = new ConcurrentHashMap<>();
        taskWeightMap.put("0", new ConcurrentHashMap<>());
        taskWeightMap.put("1", new ConcurrentHashMap<>());
        taskWeightMap.put("2", new ConcurrentHashMap<>());

        // 使用线程池并行处理
        List<CompletableFuture<MesOuputLog>> futures = mesOuputLogs.stream().map(mesOuputLog ->
                CompletableFuture.supplyAsync(() -> {
                    try {
                        // 设置创建时间和创建者
                        if (null == mesOuputLog.getCreateTime() && null == mesOuputLog.getCreateBy()) {
                            mesOuputLog.setCreateTime(DateUtils.getNowDate());
                            mesOuputLog.setCreateBy(SecurityUtils.getUsername());
                        }
                        if (null == mesOuputLog.getCreateTime()) {
                            mesOuputLog.setCreateTime(DateUtils.getNowDate());
                        }

                        // 获取投料记录
                        MesInputLog mesInputLog = inputLogMap.get(mesOuputLog.getInputId());
                        if (mesOuputLog.getInputId() == null) {
                            throw new ProductException("报工错误，投料数据为空！");
                        }
                        if (mesInputLog == null) {
                            throw new ProductException(mesOuputLog.getAlexPositionNumber() + "号轴无投料记录,不能报工!!!");
                        }

                        BigInteger taskId = mesInputLog.getTaskId();
                        mesOuputLog.setInputWireType(mesInputLog.getInputWireType());
                        mesOuputLog.setOrderId(mesInputLog.getOrderId());
                        mesOuputLog.setOrderSn(mesInputLog.getOrderSn());
                        mesOuputLog.setOrderType(mesInputLog.getOrderType());
                        mesOuputLog.setWorkorderId(mesInputLog.getWorkorderId());
                        mesOuputLog.setWorkstageId(mesInputLog.getWorkstageId());
                        mesOuputLog.setWorkstageName(mesInputLog.getWorkstageName());
                        mesOuputLog.setInputSn(mesInputLog.getInputSn());
                        mesOuputLog.setWorkorderSn(mesInputLog.getWorkorderSn());
                        mesOuputLog.setTaskId(taskId);
                        mesOuputLog.setTaskSn(mesInputLog.getTaskSn());
                        mesOuputLog.setClientId(mesInputLog.getClientId());
                        mesOuputLog.setClientName(mesInputLog.getClientName());
                        mesOuputLog.setProductId(mesInputLog.getProductId());
                        mesOuputLog.setProductSpecification(mesInputLog.getProductSpecification());
                        mesOuputLog.setProductModel(mesInputLog.getProductModel());
                        mesOuputLog.setBatchCode(mesInputLog.getBatchCode());
                        mesOuputLog.setPartNumber(mesInputLog.getPartNumber());

                        WmsRealtimeStock outStock = new WmsRealtimeStock();
                        MdItem mdItem = new MdItem();

                        if (mesOuputLog.getTaskType().equals("0")) {
                            if (taskId != null) {
                                if (mesOuputLog.getWorkstationCode() != null) {
                                    MesOuputLog mesOuputLog1 = this.mesOuputLogMapper.queryByWorkstageName(mesOuputLog.getWorkstationCode(), mesOuputLog.getWorkorderSn());
                                    if (mesOuputLog1 != null) {
                                        Date createTime = mesOuputLog1.getCreateTime();
                                        Instant now = Instant.now();
                                        if (!isMoreThanHalfAnHour(createTime, now)) {
                                            throw new ProductException("报工时间间隔太短，请勿重复报工！");
                                        }
                                    }
                                }
                                MesWiredrawingWorkorder mesWiredrawingWorkorder = wiredrawingWorkorderMap.computeIfAbsent(
                                        taskId, id -> iMesWiredrawingWorkorderService.queryById(id)
                                );
                                // 累加 outputWeight
                                taskWeightMap.get("0").compute(taskId, (key, currentWeight) -> {
                                    BigDecimal outputWeight = mesOuputLog.getOutputWeight();
                                    return currentWeight == null ? outputWeight : currentWeight.add(outputWeight);
                                });
                                addOutStock(mesOuputLog, mesWiredrawingWorkorder, outStock, mesInputLog);
                            } else {
                                BigInteger outputId = mesOuputLog.getOutputId();
                                mdItem = remoteMdService.getItem(outputId, SecurityConstants.INNER).getData();
                                if (null == mdItem) {
                                    throw new ItemException("物料获取失败，请查看物料列表是否有该工单中的规格!");
                                }
                                mesOuputLog.setOutputId(mdItem.getId());
                                mesOuputLog.setItemSpecification(mdItem.getSpecification());
                                mesOuputLog.setOutputName(mdItem.getItemName());
                                mesOuputLog.setOutputCode(mdItem.getItemCode());
                                mesOuputLog.setMeasureName(mdItem.getWeightMeasureName());
                                mesOuputLog.setOutputType("物料");

                                MesOuputLog newMesOuputLog = getInfoByWorkstationCode(mesOuputLog.getWorkstationCode());
                                String itemSpecification = mesOuputLog.getItemSpecification();
                                if (!SecurityUtils.getUsername().equals("admin")) {
                                    double intervalTime = checkTime(itemSpecification);
                                    String timeUnit = intervalTime < 1 ? ((int) Math.round(intervalTime * 60)) + "分钟" : ((int) intervalTime) + "小时";
                                    if (newMesOuputLog != null) {
                                        Date newOutputTime = newMesOuputLog.getCreateTime();
                                        Date now = DateUtils.getNowDate();
                                        long differMilliseconds = Math.abs(now.getTime() - newOutputTime.getTime());
                                        double differHours = (double) differMilliseconds / (1000 * 60 * 60);
                                        if (differHours < intervalTime) {
                                            throw new ProductException("已报工，请间隔" + timeUnit + "后报工");
                                        }
                                    }
                                }
                                String code=BatchNumberGenerator.generateBatchNumber("ITEM_");
                                mesOuputLog.setOutputSn(code);
                                outStock.setItemIdentifier(code);
                                outStock.setItemId(mesOuputLog.getOutputId().longValue());
                                outStock.setItemName(mesOuputLog.getOutputName());
                                outStock.setItemTypeId(0L);
                                outStock.setItemCode(mesOuputLog.getOutputCode());
                                outStock.setSpecification(mdItem.getSpecification());
                                outStock.setUnitOfMeasure(mdItem.getWeightMeasureName());
                                outStock.setBatchCode(mesInputLog.getBatchCode());
                                outStock.setPartNumber(mesInputLog.getPartNumber());
                                outStock.setWeightUnit(mdItem.getWeightMeasureName());
                                outStock.setTotalWeight(mesOuputLog.getOutputWeight());
                                outStock.setWeight(mesOuputLog.getOutputWeight());
                                outStock.setAttr1("0");
                                outStock.setCreateBy(SecurityUtils.getUsername());
                                outStock.setCreateTime(DateUtils.getNowDate());
                            }
                        } else if (mesOuputLog.getTaskType().equals("1")) {
                            MesAnnealingWorkorder mesAnnealingWorkorder = annealingWorkorderMap.computeIfAbsent(
                                    taskId, id -> mesAnnealingWorkorderMapper.queryById(id)
                            );
                            BigInteger wireDiscId = mesOuputLog.getWireDiscId() != null ? mesOuputLog.getWireDiscId() : mesAnnealingWorkorder.getWireDiscId();
                            MdAccessory mdAccessory = remoteMdService.getAccessory(wireDiscId, SecurityConstants.INNER).getData();
                            if (null == mdAccessory) {
                                throw new ItemException("报工线盘查询失败");
                            }
                            mesOuputLog.setWireDisc(mdAccessory.getParentModel());
                            mesOuputLog.setReelWight(mdAccessory.getWeight());
                            mesOuputLog.setStandardName(mesAnnealingWorkorder.getStandardName());
                            mesOuputLog.setStandardValue(mesAnnealingWorkorder.getStandardValue());

                            MesOuputLog newMesouputLog = mesOuputLogMapper.getInfoByWorkstationIdAndAlexPositionNumberAndAlexNumber(
                                    mesOuputLog.getWorkstationId(), mesOuputLog.getAlexPositionNumber(), mesOuputLog.getAlexNumber()
                            );
                            if (newMesouputLog != null) {
                                long newMillis = mesOuputLog.getCreateTime().getTime();
                                long oldMillis = newMesouputLog.getCreateTime().getTime();
                                long diffMillis = newMillis - oldMillis;
                                long day = diffMillis / (1000 * 60 * 60 * 24);
                                if (!mesOuputLog.getJobNumber().equals(newMesouputLog.getJobNumber()) || day >= 1) {
                                    mesOuputLog.setAlexLotNumber(1);
                                } else {
                                    mesOuputLog.setAlexLotNumber(newMesouputLog.getAlexLotNumber() + 1);
                                }
                            } else {
                                mesOuputLog.setAlexLotNumber(1);
                            }
                            mesOuputLog.setAlexLot(mesOuputLog.getAlexLotNumber() + "-" + mesOuputLog.getAlexPositionNumber());

                            // 累加 outputWeight
                            taskWeightMap.get("1").compute(taskId, (key, currentWeight) -> {
                                BigDecimal outputWeight = mesOuputLog.getOutputWeight();
                                return currentWeight == null ? outputWeight : currentWeight.add(outputWeight);
                            });

                            if (mesAnnealingWorkorder.getInsertId() != null && mesAnnealingWorkorder.getInsertStatus().equals("1")) {
                                mesAnnealingWorkorder.setStatus("1");
                                mesAnnealingWorkorder.setVersion(mesAnnealingWorkorder.getVersion() + 1);
                                synchronized (annealingWorkorderMap) {
                                    annealingWorkorderMap.put(taskId, mesAnnealingWorkorder);
                                }
                                MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesAnnealingWorkorder.getWorkorderId());
                                if (mesProductWorkorder.getStatus().equals("1")) {
                                    mesProductWorkorder.setStatus("2");
                                    iMesProductWorkorderService.update(mesProductWorkorder);
                                }
                                if (mesProductWorkorder.getOrderId() != null) {
                                    SaleOrder mdSaleOrder = remoteSaleService.getOrder(mesProductWorkorder.getOrderId(), SecurityConstants.INNER).getData();
                                    if (mdSaleOrder.getStatus().equals("2")) {
                                        mdSaleOrder.setStatus("3");
                                        R<AjaxResult> ajaxResultR = remoteSaleService.updateOrder(mdSaleOrder, SecurityConstants.INNER);
                                        if (ajaxResultR.getCode() != 200) {
                                            throw new RuntimeException("md服务调用失败{" + ajaxResultR.getMsg() + "}");
                                        }
                                    }
                                }
                            }
                            addOutStock(mesOuputLog, mesAnnealingWorkorder, outStock, mesInputLog);
                        } else if (mesOuputLog.getTaskType().equals("2")) {
                            MesTinningWorkorder mesTinningWorkorder = tinningWorkorderMap.computeIfAbsent(
                                    taskId, id -> mesTinningWorkorderMapper.queryById(id)
                            );
                            BigInteger wireDiscId = mesOuputLog.getWireDiscId() != null ? mesOuputLog.getWireDiscId() : mesTinningWorkorder.getWireDiscId();
                            MdAccessory mdAccessory = remoteMdService.getAccessory(wireDiscId, SecurityConstants.INNER).getData();
                            if (null == mdAccessory) {
                                throw new ItemException("报工线盘查询失败");
                            }
                            mesOuputLog.setWireDisc(mdAccessory.getParentModel());
                            mesOuputLog.setReelWight(mdAccessory.getWeight());
                            mesOuputLog.setStandardName(mesTinningWorkorder.getStandardName());
                            mesOuputLog.setStandardValue(mesTinningWorkorder.getStandardValue());

                            MesOuputLog newMesouputLog = mesOuputLogMapper.getInfoByWorkstationIdAndAlexPositionNumberAndAlexNumber(
                                    mesOuputLog.getWorkstationId(), mesOuputLog.getAlexPositionNumber(), mesOuputLog.getAlexNumber()
                            );
                            if (newMesouputLog != null) {
                                long newMillis = mesOuputLog.getCreateTime().getTime();
                                long oldMillis = newMesouputLog.getCreateTime().getTime();
                                long diffMillis = newMillis - oldMillis;
                                long day = diffMillis / (1000 * 60 * 60 * 24);
                                if (!mesOuputLog.getJobNumber().equals(newMesouputLog.getJobNumber()) || day >= 1) {
                                    mesOuputLog.setAlexLotNumber(1);
                                } else {
                                    mesOuputLog.setAlexLotNumber(newMesouputLog.getAlexLotNumber() + 1);
                                }
                            } else {
                                mesOuputLog.setAlexLotNumber(1);
                            }
                            mesOuputLog.setAlexLot(mesOuputLog.getAlexLotNumber() + "-" + mesOuputLog.getAlexPositionNumber());

                            // 累加 outputWeight
                            taskWeightMap.get("2").compute(taskId, (key, currentWeight) -> {
                                BigDecimal outputWeight = mesOuputLog.getOutputWeight();
                                return currentWeight == null ? outputWeight : currentWeight.add(outputWeight);
                            });

                            if (mesTinningWorkorder.getInsertId() != null && mesTinningWorkorder.getInsertStatus().equals("1")) {
                                mesTinningWorkorder.setStatus("1");
                                mesTinningWorkorder.setVersion(mesTinningWorkorder.getVersion() + 1);
                                synchronized (tinningWorkorderMap) {
                                    tinningWorkorderMap.put(taskId, mesTinningWorkorder);
                                }
                                MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesTinningWorkorder.getWorkorderId());
                                if (mesProductWorkorder.getStatus().equals("1")) {
                                    mesProductWorkorder.setStatus("2");
                                    iMesProductWorkorderService.update(mesProductWorkorder);
                                }
                                if (mesProductWorkorder.getOrderId() != null) {
                                    SaleOrder mdSaleOrder = remoteSaleService.getOrder(mesProductWorkorder.getOrderId(), SecurityConstants.INNER).getData();
                                    if (mdSaleOrder.getStatus().equals("2")) {
                                        mdSaleOrder.setStatus("3");
                                        R<AjaxResult> ajaxResultR = remoteSaleService.updateOrder(mdSaleOrder, SecurityConstants.INNER);
                                        if (ajaxResultR.getCode() != 200) {
                                            throw new RuntimeException("md服务调用失败{" + ajaxResultR.getMsg() + "}");
                                        }
                                    }
                                }
                            }
                            addOutStock(mesOuputLog, mesTinningWorkorder, outStock, mesInputLog);
                        }
                        if (!mesInputLog.getTaskType().equals("6")) {
                            WmsWarehouse wmsWarehouse = remoteWmsService.getinfoByWorkstageId(mesOuputLog.getWorkstageId(), SecurityConstants.INNER).getData();
                            if (wmsWarehouse == null) {
                                throw new ProductException("获取仓库信息失败");
                            }
                            outStock.setWarehouseId(wmsWarehouse.getWarehouseId());
                            outStock.setWarehouseCode(wmsWarehouse.getWarehouseCode());
                            outStock.setWarehouseName(wmsWarehouse.getWarehouseName());
                        } else {
                            addOutStock2(mesOuputLog, outStock, mesInputLog);
                            outStock.setWarehouseId(52L);
                            outStock.setWarehouseCode("XKC");
                            outStock.setWarehouseName("锡水仓");
                            mesOuputLog.setWorkstageId(BigInteger.valueOf(100L));
                        }
                        outStock.setAttr4(0);
                        addRealtimeStock(outStock);

                        if (mesOuputLog.getShiftScheduleId() != null) {
                            MesShiftSchedule mesShiftSchedule = mesShiftScheduleMapper.queryById(mesOuputLog.getShiftScheduleId().longValue());
                            mesShiftSchedule.setAttr1("1");
                            mesShiftScheduleMapper.update(mesShiftSchedule);
                        }

                        mesOuputLogMapper.insert(mesOuputLog);
                        return mesOuputLogMapper.queryById(mesOuputLog.getId());
                    } catch (Exception e) {
                        log.error("Error processing mesOuputLog with id: {}, taskType: {}", mesOuputLog.getId(), mesOuputLog.getTaskType(), e);
                        throw e;
                    }
                }, outputLogExecutor)
        ).collect(Collectors.toList());

        /*// 等待所有任务完成并收集结果
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        result = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        // 更新拉丝工单的 wightCompletion 和 degreeOfCompletion
        for (Map.Entry<BigInteger, BigDecimal> entry : taskWeightMap.get("0").entrySet()) {
            BigInteger taskId = entry.getKey();
            BigDecimal totalOutputWeight = entry.getValue();
            MesWiredrawingWorkorder mesWiredrawingWorkorder = wiredrawingWorkorderMap.get(taskId);
            if (mesWiredrawingWorkorder != null) {
                BigDecimal wightCompletion = BigDecimal.valueOf(mesWiredrawingWorkorder.getWightCompletion());
                BigDecimal newWightCompletion = wightCompletion.add(totalOutputWeight);
                BigDecimal weight = BigDecimal.valueOf(mesWiredrawingWorkorder.getWeight());
                BigDecimal degreeOfCompletion = weight.compareTo(BigDecimal.ZERO) > 0 ?
                        newWightCompletion.divide(weight, 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;

                mesWiredrawingWorkorder.setWightCompletion(newWightCompletion.doubleValue());
                mesWiredrawingWorkorder.setDegreeOfCompletion(degreeOfCompletion.doubleValue());
                mesWiredrawingWorkorder.setVersion(mesWiredrawingWorkorder.getVersion() + 1);
            }
        }

        // 更新退火工单的 wightCompletion 和 degreeOfCompletion
        for (Map.Entry<BigInteger, BigDecimal> entry : taskWeightMap.get("1").entrySet()) {
            BigInteger taskId = entry.getKey();
            BigDecimal totalOutputWeight = entry.getValue();
            MesAnnealingWorkorder mesAnnealingWorkorder = annealingWorkorderMap.get(taskId);
            if (mesAnnealingWorkorder != null) {
                BigDecimal wightCompletion = BigDecimal.valueOf(mesAnnealingWorkorder.getWightCompletion());
                BigDecimal newWightCompletion = wightCompletion.add(totalOutputWeight);
                BigDecimal weight = BigDecimal.valueOf(mesAnnealingWorkorder.getWeight());
                BigDecimal degreeOfCompletion = weight.compareTo(BigDecimal.ZERO) > 0 ?
                        newWightCompletion.divide(weight, 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;

                mesAnnealingWorkorder.setWightCompletion(newWightCompletion.doubleValue());
                mesAnnealingWorkorder.setDegreeOfCompletion(degreeOfCompletion.doubleValue());
                mesAnnealingWorkorder.setVersion(mesAnnealingWorkorder.getVersion() + 1);
            }
        }

        // 更新镀锡工单的 wightCompletion 和 degreeOfCompletion
        for (Map.Entry<BigInteger, BigDecimal> entry : taskWeightMap.get("2").entrySet()) {
            BigInteger taskId = entry.getKey();
            BigDecimal totalOutputWeight = entry.getValue();
            MesTinningWorkorder mesTinningWorkorder = tinningWorkorderMap.get(taskId);
            if (mesTinningWorkorder != null) {
                BigDecimal wightCompletion = BigDecimal.valueOf(mesTinningWorkorder.getWightCompletion());
                BigDecimal newWightCompletion = wightCompletion.add(totalOutputWeight);
                BigDecimal weight = BigDecimal.valueOf(mesTinningWorkorder.getWeight());
                BigDecimal degreeOfCompletion = weight.compareTo(BigDecimal.ZERO) > 0 ?
                        newWightCompletion.divide(weight, 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;

                mesTinningWorkorder.setWightCompletion(newWightCompletion.doubleValue());
                mesTinningWorkorder.setDegreeOfCompletion(degreeOfCompletion.doubleValue());
                mesTinningWorkorder.setVersion(mesTinningWorkorder.getVersion() + 1);
            }
        }*/
        /*// 批量更新工单
        if (!wiredrawingWorkorderMap.isEmpty()) {
            iMesWiredrawingWorkorderService.batchUpdate(new ArrayList<>(wiredrawingWorkorderMap.values()));
        }
        if (!annealingWorkorderMap.isEmpty()) {
            mesAnnealingWorkorderMapper.batchUpdate(new ArrayList<>(annealingWorkorderMap.values()));
        }
        if (!tinningWorkorderMap.isEmpty()) {
            mesTinningWorkorderMapper.batchUpdate(new ArrayList<>(tinningWorkorderMap.values()));
        }*/

        return result;
    }

    /**
     * 异步+延时进行仓储事务表新增，控制时间
     * @param outStock
     */
    @Async
    protected void addRealtimeStock(WmsRealtimeStock outStock) {
        try {
            Thread.sleep(1000);  // 延时 100 毫秒（可以根据需要调整）

            R<AjaxResult> ajaxResultR = remoteWmsService.addRealtimeStock(outStock, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200) {
                throw new RuntimeException("wms服务调用失败{" + ajaxResultR.getMsg() + "}");

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
            throw new RuntimeException("插入数据时线程被中断", e);
        }
    }
    // 计算时间差是否大于半个小时
    public static boolean isMoreThanHalfAnHour(Date createTime, Instant now) {
        // 将 Date 转换为 Instant
        Instant createInstant = createTime.toInstant();
        Duration duration = Duration.between(createInstant, now);
        return true;  // 获取时间差，判断是否大于 60 分钟
    }

    private void addOutStock(MesOuputLog mesOuputLog, Object workorder, WmsRealtimeStock outStock, MesInputLog mesInputLog) {
        // 查询产品信息
        MdProduct mdProducts = getProductById(mesOuputLog.getProductId());

        // 设置产品相关信息到 MesOuputLog
        mesOuputLog.setOutputType("产品");
        mesOuputLog.setProductSizeInternational(mdProducts.getProductSizeInternational());
        mesOuputLog.setItemSpecification(mdProducts.getSpecification());
        mesOuputLog.setMeasureName(mdProducts.getWeightMeasureName());
        mesOuputLog.setProductName(mdProducts.getProductName());
        mesOuputLog.setProductCode(mdProducts.getProductCode());

        // 设置客户名称，根据工单类型动态获取
        String customerName = getCustomerNameFromWorkorder(workorder);
        if (customerName != null) {
            outStock.setIngredientsName(customerName);
        }

        //设置工单号
        outStock.setWorkorderCode(mesOuputLog.getWorkorderSn());

        // 获取自动生成的二维码
        String code=BatchNumberGenerator.generateBatchNumber("PROD_");
        mesOuputLog.setOutputSn(code);
        outStock.setItemIdentifier(code);

        // 设置库存的产品相关信息
        outStock.setItemId(mesOuputLog.getProductId().longValue());
        outStock.setItemName(mdProducts.getProductName());
        outStock.setItemTypeId(1L); // 产品类型标识
        outStock.setItemCode(mdProducts.getProductCode());
        outStock.setProductTypeName(mdProducts.getProductTypeName());
        outStock.setSpecification(mdProducts.getSpecification());
        outStock.setUnitOfMeasure(mdProducts.getWeightMeasureName());
        outStock.setBatchCode(mesInputLog.getBatchCode());
        outStock.setProductBatchCode(mdProducts.getBatchNumber());
        outStock.setPartNumber(mesInputLog.getPartNumber());
        outStock.setWeightUnit(mdProducts.getWeightMeasureName());
        //outStock.setTotalWeight(mesOuputLog.getOutputWeight());
        outStock.setWeight(mesOuputLog.getOutputWeight());
        outStock.setMaterialCode(mdProducts.getMaterialCode());
        outStock.setAttr1("3"); // 产品标识
    }
    private void addOutStock2(MesOuputLog mesOuputLog, WmsRealtimeStock outStock, MesInputLog mesInputLog) {
        // 查询产品信息
        // MdProduct mdProducts = getProductById(mesOuputLog.getProductId());

        // 设置产品相关信息到 MesOuputLog
        mesOuputLog.setOutputType("产品");
        mesOuputLog.setProductSizeInternational("XS");
        mesOuputLog.setItemSpecification("0");
        mesOuputLog.setMeasureName("kg");
        mesOuputLog.setProductName("锡水");
        mesOuputLog.setProductCode("PROD_LIST_02XS");

        //设置工单号
        // outStock.setWorkorderCode(mesOuputLog.getWorkorderSn());

        // 获取自动生成的二维码
        String code=BatchNumberGenerator.generateBatchNumber("PROD_");
        mesOuputLog.setOutputSn(code);
        outStock.setItemIdentifier(code);

        // 设置库存的产品相关信息
        outStock.setItemId(112L);
        outStock.setItemName("锡水");
        outStock.setItemTypeId(1L); // 产品类型标识
        outStock.setItemCode("PROD_LIST_02XS");
        outStock.setProductTypeName("辅料");
        outStock.setSpecification("0");
        outStock.setUnitOfMeasure("kg");
        outStock.setBatchCode(mesInputLog.getBatchCode());
        outStock.setProductBatchCode(mesOuputLog.getBatchCode());
        outStock.setPartNumber(mesInputLog.getPartNumber());
        outStock.setWeightUnit("kg");
        //outStock.setTotalWeight(mesOuputLog.getOutputWeight());
        outStock.setWeight(mesOuputLog.getOutputWeight());
        outStock.setMaterialCode("--");
        outStock.setAttr1("5"); // 产品标识
    }
    /**
     * 根据工单类型获取客户名称
     */
    private String getCustomerNameFromWorkorder(Object workorder) {
        if (workorder instanceof MesAnnealingWorkorder) {
            return ((MesAnnealingWorkorder) workorder).getCustomerName();
        } else if (workorder instanceof MesWiredrawingWorkorder) {
            return ((MesWiredrawingWorkorder) workorder).getCustomerName();
        } else if (workorder instanceof MesTinningWorkorder) {
            return ((MesTinningWorkorder) workorder).getCustomerName();
        } else if (workorder instanceof MesStrandedwireWorkorder) {
            return ((MesStrandedwireWorkorder) workorder).getCustomerName();
        } else if (workorder instanceof MesDoublewireWorkorder) {
            return ((MesDoublewireWorkorder) workorder).getCustomerName();
        } else {
            throw new IllegalArgumentException("不支持的工单类型：" + workorder.getClass().getName());
        }
    }
    /**
     * 根据产品ID查询产品信息
     */
    private MdProduct getProductById(BigInteger productId) {
        MdProduct mdProduct = remoteMdService.getProduct(productId, SecurityConstants.INNER).getData();
        if (mdProduct == null) {
            throw new ItemException("产品查询失败，产品ID：" + productId);
        }
        return mdProduct;
    }

    /**
     * 工单完成
     */
    @Override
    @GlobalTransactional
    @Transactional
    public List<MesOuputLog> WorkorderComplete(List<MesOuputLog> mesOuputLogs) {
        List<MesOuputLog> resultList = insert(mesOuputLogs);
        int result = 0;
        //拉丝工单完成
        MesOuputLog mesOuputLog = mesOuputLogs.get(0);
        if (mesOuputLog.getTaskType().equals("0")) {
            //拉丝工单完成
            MesWiredrawingWorkorder mesWiredrawingWorkorder = iMesWiredrawingWorkorderService.queryById(mesOuputLog.getTaskId());
            mesWiredrawingWorkorder.setStatus("2");
            iMesWiredrawingWorkorderService.update(mesWiredrawingWorkorder);
            //通过拉丝工单查看漆包工单是否完工
//            MesEnamellingWorkorder mesEnamellingWorkorder = new MesEnamellingWorkorder();
//            mesEnamellingWorkorder.setWorkorderId(mesWiredrawingWorkorder.getWorkorderId());
//            List<MesEnamellingWorkorder> mesEnamellingWorkorders = iMesEnamellingWorkorderService.selectMesEnamellingWorkorderList(mesEnamellingWorkorder);
//            if (null == mesEnamellingWorkorders || mesEnamellingWorkorders.size() == 0){
//                throw new ProductException("完工异常，该拉丝工单对应漆包工单为空");
//            }
//            if (mesEnamellingWorkorders.get(0).getStatus().equals("2")){
//                //修改生产工单状态为生产完成
//                MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesWiredrawingWorkorder.getWorkorderId());
//                mesProductWorkorder.setStatus("3");
//                result = iMesProductWorkorderService.update(mesProductWorkorder);
//
//                //销售订单状态变更
//                MdSaleOrder mdSaleOrder = remoteSaleService.getOrder(mesProductWorkorder.getOrderId(), SecurityConstants.INNER).getData();
//                mdSaleOrder.setStatus("4");
//                R<AjaxResult> ajaxResultR = remoteSaleService.updateOrder(mdSaleOrder, SecurityConstants.INNER);
//                if (ajaxResultR.getCode() != 200){
//                    throw new RuntimeException("主数据服务调用失败{"+ajaxResultR.getMsg()+"}");
//                }
//            }
        } else {
            //漆包工单完成
            MesEnamellingWorkorder mesEnamellingWorkorder = iMesEnamellingWorkorderService.queryById(mesOuputLog.getTaskId());
            //该工单为插单工单
            if (mesEnamellingWorkorder.getInsertId() != null && mesEnamellingWorkorder.getInsertStatus().equals("1")) {
                iMesEnamellingWorkorderService.cancelInsertWorkorder(mesEnamellingWorkorder.getInsertId());
            } else {
                mesEnamellingWorkorder.setStatus("2");
                iMesEnamellingWorkorderService.update(mesEnamellingWorkorder);
            }
            //拉丝是否完工，若拉丝工单完工，则工单完成
            MesWiredrawingWorkorder mesWiredrawingWorkorder = new MesWiredrawingWorkorder();
            mesWiredrawingWorkorder.setWorkorderId(mesEnamellingWorkorder.getWorkorderId());
            List<MesWiredrawingWorkorder> mesWiredrawingWorkorders = iMesWiredrawingWorkorderService.selectMesWiredrawingWorkorderList(mesWiredrawingWorkorder);

            if (null != mesWiredrawingWorkorders && mesWiredrawingWorkorders.size() > 0 && !mesWiredrawingWorkorders.get(0).getStatus().equals("2")) {
                return resultList;
            } else {
                //修改生产工单状态为生产完成
                MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesEnamellingWorkorder.getWorkorderId());
                mesProductWorkorder.setStatus("3");
                result = iMesProductWorkorderService.update(mesProductWorkorder);

                //销售订单状态变更
                SaleOrder saleOrder = remoteSaleService.getOrder(mesProductWorkorder.getOrderId(), SecurityConstants.INNER).getData();
                saleOrder.setStatus("4");
                R<AjaxResult> ajaxResultR = remoteSaleService.updateOrder(saleOrder, SecurityConstants.INNER);
                if (ajaxResultR.getCode() != 200) {
                    throw new RuntimeException("主数据服务调用失败{" + ajaxResultR.getMsg() + "}");
                }
            }
        }
        return resultList;
    }

    /**
     * 批量新增数据
     *
     * @param mesOuputLogs 实例对象数组
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<MesOuputLog> mesOuputLogs) {
        int rs = 0;
        for (MesOuputLog mesOuputLog : mesOuputLogs) {
            mesOuputLog.setCreateTime(DateUtils.getNowDate());
//            rs = mesOuputLogMapper.insert(mesOuputLog) + 1;
        }
        return rs;
    }


    /**
     * 更新数据
     *
     * @param mesOuputLog 实例对象
     * @return 影响行数
     */
    @Override
    public int update(MesOuputLog mesOuputLog) {
        mesOuputLog.setUpdateBy(SecurityUtils.getUsername());
        mesOuputLog.setUpdateTime(DateUtils.getNowDate());
        return mesOuputLogMapper.update(mesOuputLog);
    }


    /**
     * 修改半成品出入库信息
     *
     * @param mesOuputLog 实例对象
     * @return 影响行数
     */
    @Override
    @GlobalTransactional
    public int updateInfo(MesOuputLog mesOuputLog) {
        //通过二维码条码查询对应库存
        WmsRealtimeStock wmsRealtimeStock = remoteWmsService.getInfoByItemIdentifier(mesOuputLog.getOutputSn(), SecurityConstants.INNER).getData();
//        if (wmsRealtimeStock.getScanCount().equals(0)){
        wmsRealtimeStock.setIngredientsName(mesOuputLog.getIngredientsName());
        wmsRealtimeStock.setManufacturerName(mesOuputLog.getManufacturerName());
        R<AjaxResult> ajaxResultR = remoteWmsService.updateWmsRealtimeStock(wmsRealtimeStock, SecurityConstants.INNER);
        if (ajaxResultR.getCode() != 200) {
            throw new RuntimeException("wms服务调用失败{" + ajaxResultR.getMsg() + "}");
        }
//        }else{
//            throw new RealtimeStockException("该物料已使用，不能进行修改!!!");
//        }
        mesOuputLog.setUpdateBy(SecurityUtils.getUsername());
        mesOuputLog.setUpdateTime(DateUtils.getNowDate());

        return mesOuputLogMapper.update(mesOuputLog);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(BigInteger id) {
        int total = mesOuputLogMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    @Override
    public boolean deleteByIds(BigInteger[] ids) {
        int total = mesOuputLogMapper.deleteByIds(ids);
        return total > 0;
    }

    /**
     * 通过机台、轴数、轴号查询最新报工记录
     */
    public MesOuputLog getInfoByWorkstationIdAndAlexPositionNumberAndAlexNumber(BigInteger workstationId, Integer alexPositionNumber, String axleNumber) {
        return mesOuputLogMapper.getInfoByWorkstationIdAndAlexPositionNumberAndAlexNumber(workstationId, alexPositionNumber, axleNumber);
    }

    /**
     * 通过机台查询最新报工记录
     */
    public MesOuputLog getInfoByWorkstationCode(String workstationCode) {
        return mesOuputLogMapper.getInfoByWorkstationCode(workstationCode);
    }

    /**
     * 拉丝报工时间限制
     */
    public Double checkTime(String itemSpecificationCode) {
        double itemSpecificationDouble = Double.parseDouble(itemSpecificationCode);

        if (1.2 < itemSpecificationDouble && itemSpecificationDouble <= 2.6) {
            return timeOne;
        } else if (0.39 <= itemSpecificationDouble && itemSpecificationDouble <= 1.2) {
            return timeTwo;
        } else if (0.16 <= itemSpecificationDouble && itemSpecificationDouble < 0.39) {
            return timeThree;
        } else if (0.11 <= itemSpecificationDouble && itemSpecificationDouble < 0.16) {
            return timeFour;
        } else if (0.06 <= itemSpecificationDouble && itemSpecificationDouble < 0.11) {
            return timeFive;
        }
        return 0.0;
    }

    @Override
    public MesOutputLogVo selectWmsmesOutputLogListSum(MesOuputLog mesOuputLog) {
        List<MesOuputLog> mesOuputLogList = mesOuputLogMapper.selectMesOuputLogWmsList(mesOuputLog);
        int count = mesOuputLogList.size();

        MesOutputLogVo mesOutputLogVo = new MesOutputLogVo();
        mesOutputLogVo.setCount(count);
        double totalTareWeight = 0.0;
        double totalNetWeight = 0.0;
        for (MesOuputLog mesOuputLog1 : mesOuputLogList) {
            totalTareWeight += mesOuputLog1.getOutputTareWeight().doubleValue();
            totalNetWeight += mesOuputLog1.getOutputWeight().doubleValue();
        }
        mesOutputLogVo.setCount(count);
        mesOutputLogVo.setTotalNetWeight(totalNetWeight);
        mesOutputLogVo.setTotalTareWeight(totalTareWeight);
        mesOutputLogVo.setMesOutputLogList(mesOuputLogList);
        return mesOutputLogVo;
    }

    @Override
    public MesOuputLog getSpecificationByWorkstationCode(String workstationCode) {
        return mesOuputLogMapper.getInfoByWorkstationCode(workstationCode);
    }

    @Override
    public List<MesOuputLog> selectByTime(String taskType) {
        return mesOuputLogMapper.selectByTime(taskType);
    }

    @Override
    public boolean scrapped(MesOuputLog mesOuputLog) {
        //先进行逻辑删除报工记录
        mesOuputLog.setWmsStatus(4);
        //做线边仓出库
        WmsRealtimeStock data = this.remoteWmsService.getInfoByItemIdentifier(mesOuputLog.getOutputSn(), SecurityConstants.INNER).getData();
        if (data != null){
            R<AjaxResult> ajaxResultR = remoteWmsService.subtractRealtimeStock(data, "subtract");
            if (ajaxResultR.getCode() != 200) {
                throw new RuntimeException("wms服务调用失败{" + ajaxResultR.getMsg() + "}");
            }
        }
        return mesOuputLogMapper.update(mesOuputLog) > 0;
    }
}

