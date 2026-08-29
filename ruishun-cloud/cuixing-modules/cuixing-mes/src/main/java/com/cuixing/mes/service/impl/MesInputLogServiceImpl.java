package com.cuixing.mes.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.md.ItemException;
import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.exception.wms.RealtimeStockException;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mes.entity.*;
import com.cuixing.mes.entity.vo.*;
import com.cuixing.mes.mapper.MesOuputLogMapper;
import com.cuixing.mes.service.*;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.RemoteWmsService;
import com.cuixing.system.api.domain.md.*;
import com.cuixing.system.api.domain.sale.SaleOrder;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.mes.mapper.MesInputLogMapper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

/**
 * 投料记录表;(mes_input_log)表服务实现类
 *
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Service
public class MesInputLogServiceImpl implements IMesInputLogService {
    @Autowired
    private MesInputLogMapper mesInputLogMapper;

    @Autowired
    private RemoteMdService remoteMdService;

    @Autowired
    private RemoteSaleService remoteSaleService;

    @Autowired
    private RemoteWmsService remoteWmsService;

    @Autowired
    private IMesProductWorkorderService iMesProductWorkorderService;

    @Autowired
    private IMesWiredrawingWorkorderService iMesWiredrawingWorkorderService;

    @Autowired
    private IMesEnamellingWorkorderService iMesEnamellingWorkorderService;

    @Autowired
    private MesTinningWorkorderService mesTinningWorkorderService;

    @Autowired
    private MesAnnealingWorkorderService mesAnnealingWorkorderService;

    @Autowired
    private MesStrandedwireWorkorderService mesStrandedwireWorkorderService;

    @Autowired
    private MesDoublewireWorkorderService mesDoublewireWorkorderService;

    @Autowired
    private MesOuputLogMapper mesOuputLogMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MesInputLog queryById(BigInteger id) {
        return mesInputLogMapper.queryById(id);
    }


    /**
     * 通过机台查询单条数据
     *
     * @param workstationId 主键
     * @return 实例对象
     */
    @Override
    public List<MesInputLog> queryByWorkstationId(BigInteger workstationId) {
        return mesInputLogMapper.queryByWorkstationId(workstationId);
    }

    @Override
    public MesInputLog queryByWorkstationIdAndAxleNumber(BigInteger workstationId, String axleNumber) {
        return mesInputLogMapper.queryByWorkstationIdAndAxleNumber(workstationId, axleNumber);
    }

    @Override
    public MesInputLog getInfoByWorkstationIdAndAlexPositionNumberAndAxleNumber(BigInteger workstationId, Integer alexPositionNumber, String axleNumber) {
        return mesInputLogMapper.getInfoByWorkstationIdAndAlexPositionNumberAndAxleNumber(workstationId, alexPositionNumber, axleNumber);
    }
    @Override
    public MesInputLog getInfoByWorkstationIdAndAxleNumber(BigInteger workstationId, String axleNumber,String workorderSn) {
        return mesInputLogMapper.getInfoByWorkstationIdAndAxleNumber(workstationId, axleNumber,workorderSn);
    }
    @Override
    public MesInputLog getInfoByWorkstationCodeAndWorkorderSn(String workstationCode,String workorderSn) {
        return mesInputLogMapper.getInfoByWorkstationCodeAndWorkorderSn(workstationCode,workorderSn);
    }

    /**
     * 分页查询
     *
     * @param mesInputLog 筛选条件
     * @return 查询结果
     */
    public List<MesInputLog> selectMesInputLogList(MesInputLog mesInputLog) {
        return mesInputLogMapper.selectMesInputLogList(mesInputLog);
    }

    /**
     * 新增数据
     *
     * @param mesInputLog 实例对象
     * @return 最后插入的数据的自增主键ID
     */
//    @Transactional
    @Override
    public int insert(MesInputLog mesInputLog) {
        if (mesInputLog.getCreateTime()==null){
            mesInputLog.setCreateTime(DateUtils.getNowDate());
        }
        mesInputLog.setCreateBy(SecurityUtils.getUsername());

        BigInteger processId = null;
        String workorderSn = null;
        //通过工序判断拉丝
        if (mesInputLog.getTaskType().equals("0")) {
            //拉丝投料
            //根据机台查询所属工序
            MdWorkbenchWiredrawing mdWorkbenchWiredrawing = remoteMdService.getWorkbenchWiredrawingById(mesInputLog.getWorkstationId(), SecurityConstants.INNER).getData();
            //获取工序id
            processId = mdWorkbenchWiredrawing.getProcessId();

            if (mesInputLog.getTaskId() != null) {
                //通过拉丝工单id查询生产工单号
                MesWiredrawingWorkorder mesWiredrawingWorkorder = iMesWiredrawingWorkorderService.queryById(mesInputLog.getTaskId());
                workorderSn = mesWiredrawingWorkorder.getWorkorderCode();

                //工单检验，如果工单上的机台信息与选择机台不匹配，则抛出异常
                boolean result = wiredrawingCheckWorkorder(mesWiredrawingWorkorder, mesInputLog.getWorkstationId());
                if (!result) {
                    throw new ProductException("机台选择错误！");
                }

                //拉丝工单状态变更
                mesWiredrawingWorkorder.setStatus("1");
                iMesWiredrawingWorkorderService.update(mesWiredrawingWorkorder);
                //保存产品信息
                mesInputLog.setProductId(mesWiredrawingWorkorder.getProductId());
                mesInputLog.setProductSpecification(mesWiredrawingWorkorder.getSpecification());
                mesInputLog.setProductModel(mesWiredrawingWorkorder.getModel());

            }
            //通过工序判断退火
        }
        else if (mesInputLog.getTaskType().equals("1")) {
            //退火投料
            //根据机台查询所属工序
            MdWorkbenchAnnealing mdWorkbenchAnnealing = remoteMdService.getWorkbenchAnnealingById(mesInputLog.getWorkstationId(), SecurityConstants.INNER).getData();
            //获取工序id
            processId = mdWorkbenchAnnealing.getProcessId();

            if (mesInputLog.getTaskId() != null) {
                //通过退火工单id查询生产工单号
                MesAnnealingWorkorder mesAnnealingWorkorder = mesAnnealingWorkorderService.queryById(mesInputLog.getTaskId());
                workorderSn = mesAnnealingWorkorder.getWorkorderCode();

                //退火工单状态变更
                mesAnnealingWorkorder.setStatus("1");
                mesAnnealingWorkorderService.update(mesAnnealingWorkorder);

                //保存产品信息
                mesInputLog.setProductId(mesAnnealingWorkorder.getProductId());
                mesInputLog.setProductSpecification(mesAnnealingWorkorder.getSpecification());
                mesInputLog.setProductModel(mesAnnealingWorkorder.getModel());
            }
            //通过工序判断镀锡
        }
        else if (mesInputLog.getTaskType().equals("2")) {
            //镀锡投料
            //根据机台查询所属工序
            MdWorkbenchTinning mdWorkbenchTinning = remoteMdService.getWorkbenchTinningById(mesInputLog.getWorkstationId(), SecurityConstants.INNER).getData();
            //获取工序id
            processId = mdWorkbenchTinning.getProcessId();

            if (mesInputLog.getTaskId() != null) {
                //通过镀锡工单id查询生产工单号
                MesTinningWorkorder mesTinningWorkorder = mesTinningWorkorderService.queryById(mesInputLog.getTaskId());
                workorderSn = mesTinningWorkorder.getWorkorderCode();

                //镀锡工单状态变更
                mesTinningWorkorder.setStatus("1");
                mesTinningWorkorderService.update(mesTinningWorkorder);
                //保存产品信息
                mesInputLog.setProductId(mesTinningWorkorder.getProductId());
                mesInputLog.setProductSpecification(mesTinningWorkorder.getSpecification());
                mesInputLog.setProductModel(mesTinningWorkorder.getModel());
            }
            //通过工序判断并丝
        }
        else if (mesInputLog.getTaskType().equals("3")) {
            //并丝投料
            //根据机台查询所属工序
            MdWorkbenchDoublewire mdWorkbenchDoublewire = remoteMdService.getWorkbenchDoublewireById(mesInputLog.getWorkstationId(), SecurityConstants.INNER).getData();
            //获取工序id
            processId = mdWorkbenchDoublewire.getProcessId();

            if (mesInputLog.getTaskId() != null) {
                //通过并丝工单id查询生产工单号
                MesDoublewireWorkorder mesDoublewireWorkorder = mesDoublewireWorkorderService.queryById(mesInputLog.getTaskId());
                workorderSn = mesDoublewireWorkorder.getWorkorderCode();

                //并丝工单状态变更
                mesDoublewireWorkorder.setStatus("1");
                mesDoublewireWorkorderService.update(mesDoublewireWorkorder);
                //保存产品信息
                mesInputLog.setProductId(mesDoublewireWorkorder.getProductId());
                mesInputLog.setProductSpecification(mesDoublewireWorkorder.getSpecification());
                mesInputLog.setProductModel(mesDoublewireWorkorder.getModel());
            }
            //绞线
        }
        else if (mesInputLog.getTaskType().equals("4")) {
            //绞线投料
            //根据机台查询所属工序
            MdWorkbenchStrandedwire mdWorkbenchStrandedwire = remoteMdService.getWorkbenchStrandedwireById(mesInputLog.getWorkstationId(), SecurityConstants.INNER).getData();
            //获取工序id
            processId = mdWorkbenchStrandedwire.getProcessId();

            if (mesInputLog.getTaskId() != null) {
                //通过绞线工单id查询生产工单号
                MesStrandedwireWorkorder mesStrandedwireWorkorder = mesStrandedwireWorkorderService.queryById(mesInputLog.getTaskId());
                workorderSn = mesStrandedwireWorkorder.getWorkorderCode();

                //绞线工单状态变更
                mesStrandedwireWorkorder.setStatus("1");
                mesStrandedwireWorkorderService.update(mesStrandedwireWorkorder);
                //保存产品信息
                mesInputLog.setProductId(mesStrandedwireWorkorder.getProductId());
                mesInputLog.setProductSpecification(mesStrandedwireWorkorder.getSpecification());
                mesInputLog.setProductModel(mesStrandedwireWorkorder.getModel());
            }
        }
        // 只有非taskType=5的情况下才执行仓库相关逻辑
        if (!mesInputLog.getTaskType().equals("6") && !mesInputLog.getTaskType().equals("7")) {
            //通过工序查询对应仓库
            WmsWarehouse wmsWarehouse = remoteWmsService.getinfoByWorkstageId(processId, SecurityConstants.INNER).getData();
            //添加工序信息
            mesInputLog.setWorkstageId(processId);
            mesInputLog.setWorkstageName(wmsWarehouse.getWorkstageName());
            mesInputLog.setWorkstageCode(wmsWarehouse.getWorkstageCode());
        }
        //通过物料唯一码查询物料数据
        WmsRealtimeStock inputStock = remoteWmsService.getInfoByItemIdentifier(mesInputLog.getInputSn(), SecurityConstants.INNER).getData();
        if (inputStock == null) {
            throw new RealtimeStockException("物料不存在!!!");
        } else {
            if (!inputStock.getWarehouseName().equals("原料仓") && !inputStock.getWarehouseName().equals("锡水仓")) {
                //根据二维码编码查询该物料报工记录
                MesOuputLog infoByOutputSn = mesOuputLogMapper.getInfoByOutputSn(mesInputLog.getInputSn());
                if (infoByOutputSn == null) {
                    throw new ProductException("该二维码对应报工记录不存在");
                }
            }
            mesInputLog.setInputId(new BigInteger(inputStock.getItemId().toString()));
            mesInputLog.setInputName(inputStock.getItemName());
            mesInputLog.setInputCode(inputStock.getItemCode());
            Long itemTypeId = inputStock.getItemTypeId();
            if (itemTypeId == 1) {
                mesInputLog.setInputType("物料");
            } else {
                mesInputLog.setInputType("产品");
            }
            //毛重
            mesInputLog.setInputWeight(inputStock.getWeight());
            mesInputLog.setMeasureName(inputStock.getUnitOfMeasure());
            mesInputLog.setBatchCode(inputStock.getBatchCode());
            //料号
            mesInputLog.setPartNumber(inputStock.getPartNumber());
            mesInputLog.setItemSpecification(inputStock.getSpecification());
            mesInputLog.setAttr2(inputStock.getScanCount() + 1);
        }
        //工单、订单
        //根据工单号查询生产工单信息
        if (workorderSn != null) {
            MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryByWorkorderCode(workorderSn);
            //通过工序id查询拉丝工单/漆包信息
            if (mesProductWorkorder != null) {
                mesInputLog.setOrderId(mesProductWorkorder.getOrderId());
                mesInputLog.setOrderSn(mesProductWorkorder.getOrderCode());
                mesInputLog.setOrderType(mesProductWorkorder.getOrderType());
                mesInputLog.setClientName(mesProductWorkorder.getCustomerName());
                mesInputLog.setWorkorderId(mesProductWorkorder.getId());
                mesInputLog.setWorkorderSn(mesProductWorkorder.getWorkorderCode());
                //根据工艺类型，查询对应工艺工单关联的产品信息



                //通过工单号改变生产工单状态
                mesProductWorkorder.setStatus("2");
                iMesProductWorkorderService.update(mesProductWorkorder);

                //修改销售订单状态为“生产中”
                if (mesProductWorkorder.getOrderId() != null) {
                    SaleOrder saleOrder = remoteSaleService.getOrder(mesProductWorkorder.getOrderId(), SecurityConstants.INNER).getData();
                    if (saleOrder == null) {
                        throw new ItemException("销售订单不存在");
                    }
                    saleOrder.setStatus("3");
                    R<AjaxResult> ajaxResultR = remoteSaleService.updateOrder(saleOrder, SecurityConstants.INNER);
                    if (ajaxResultR.getCode() != 200) {
                        throw new RuntimeException("主数据服务调用失败{" + ajaxResultR.getMsg() + "}");
                    }
                }
            }
        }
        //库存减少对应物料
        R<AjaxResult> ajaxResultR = remoteWmsService.subtractRealtimeStock(inputStock, SecurityConstants.INNER);
        if (ajaxResultR.getCode() != 200) {
            throw new RuntimeException("wms服务调用失败{" + ajaxResultR.getMsg() + "}");
        }
        return mesInputLogMapper.insert(mesInputLog);
    }

    /**
     * 拉丝工单校验
     */
    private boolean wiredrawingCheckWorkorder(MesWiredrawingWorkorder mesWiredrawingWorkorder, BigInteger workstationId) {
        //拉丝校验
        String[] split = mesWiredrawingWorkorder.getEquipmentId().split(",");
        for (String s : split) {
            if (workstationId.equals(new BigInteger(s))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 漆包工单校验
     */
    private boolean enamellingCheckWorkorder(MesEnamellingWorkorder mesEnamellingWorkorder) {
        //漆包工单校验

        return true;
    }

    /**
     * 批量新增数据
     *
     * @param mesInputLogList 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MesInputLog> mesInputLogList) {
        if (mesInputLogList == null || mesInputLogList.isEmpty()) {
            return 0; // 如果传入列表为空，返回 0
        }

        // 循环处理每条记录
        for (MesInputLog mesInputLog : mesInputLogList) {
            // 设置创建时间和创建者
            if (mesInputLog.getCreateTime() == null){
                mesInputLog.setCreateTime(DateUtils.getNowDate());
            }
            mesInputLog.setCreateBy(SecurityUtils.getUsername());

            BigInteger processId = null;
            String workorderSn = null;

            // 通过工序判断拉丝
            if ("0".equals(mesInputLog.getTaskType())) {
                // 拉丝投料
                MdWorkbenchWiredrawing mdWorkbenchWiredrawing = remoteMdService.getWorkbenchWiredrawingById(mesInputLog.getWorkstationId(), SecurityConstants.INNER).getData();
                processId = mdWorkbenchWiredrawing.getProcessId();

                if (mesInputLog.getTaskId() != null) {
                    MesWiredrawingWorkorder mesWiredrawingWorkorder = iMesWiredrawingWorkorderService.queryById(mesInputLog.getTaskId());
                    workorderSn = mesWiredrawingWorkorder.getWorkorderCode();

                    // 工单检验
                    boolean result = wiredrawingCheckWorkorder(mesWiredrawingWorkorder, mesInputLog.getWorkstationId());
                    if (!result) {
                        throw new ProductException("机台选择错误！");
                    }

                    // 拉丝工单状态变更
                    mesWiredrawingWorkorder.setStatus("1");
                    iMesWiredrawingWorkorderService.update(mesWiredrawingWorkorder);

                    // 保存产品信息
                    mesInputLog.setProductId(mesWiredrawingWorkorder.getProductId());
                    mesInputLog.setProductSpecification(mesWiredrawingWorkorder.getSpecification());
                    mesInputLog.setProductModel(mesWiredrawingWorkorder.getModel());
                }
            }
            // 通过工序判断退火
            else if ("1".equals(mesInputLog.getTaskType())) {
                MdWorkbenchAnnealing mdWorkbenchAnnealing = remoteMdService.getWorkbenchAnnealingById(mesInputLog.getWorkstationId(), SecurityConstants.INNER).getData();
                processId = mdWorkbenchAnnealing.getProcessId();

                if (mesInputLog.getTaskId() != null) {
                    MesAnnealingWorkorder mesAnnealingWorkorder = mesAnnealingWorkorderService.queryById(mesInputLog.getTaskId());
                    workorderSn = mesAnnealingWorkorder.getWorkorderCode();

                    mesAnnealingWorkorder.setStatus("1");
                    mesAnnealingWorkorderService.update(mesAnnealingWorkorder);

                    mesInputLog.setProductId(mesAnnealingWorkorder.getProductId());
                    mesInputLog.setProductSpecification(mesAnnealingWorkorder.getSpecification());
                    mesInputLog.setProductModel(mesAnnealingWorkorder.getModel());
                }
            }
            // 通过工序判断镀锡
            else if ("2".equals(mesInputLog.getTaskType())) {
                MdWorkbenchTinning mdWorkbenchTinning = remoteMdService.getWorkbenchTinningById(mesInputLog.getWorkstationId(), SecurityConstants.INNER).getData();
                processId = mdWorkbenchTinning.getProcessId();

                if (mesInputLog.getTaskId() != null) {
                    MesTinningWorkorder mesTinningWorkorder = mesTinningWorkorderService.queryById(mesInputLog.getTaskId());
                    workorderSn = mesTinningWorkorder.getWorkorderCode();

                    mesTinningWorkorder.setStatus("1");
                    mesTinningWorkorderService.update(mesTinningWorkorder);

                    mesInputLog.setProductId(mesTinningWorkorder.getProductId());
                    mesInputLog.setProductSpecification(mesTinningWorkorder.getSpecification());
                    mesInputLog.setProductModel(mesTinningWorkorder.getModel());
                }
            }
            // 通过工序判断并丝
            else if ("3".equals(mesInputLog.getTaskType())) {
                MdWorkbenchDoublewire mdWorkbenchDoublewire = remoteMdService.getWorkbenchDoublewireById(mesInputLog.getWorkstationId(), SecurityConstants.INNER).getData();
                processId = mdWorkbenchDoublewire.getProcessId();

                if (mesInputLog.getTaskId() != null) {
                    MesDoublewireWorkorder mesDoublewireWorkorder = mesDoublewireWorkorderService.queryById(mesInputLog.getTaskId());
                    workorderSn = mesDoublewireWorkorder.getWorkorderCode();

                    mesDoublewireWorkorder.setStatus("1");
                    mesDoublewireWorkorderService.update(mesDoublewireWorkorder);

                    mesInputLog.setProductId(mesDoublewireWorkorder.getProductId());
                    mesInputLog.setProductSpecification(mesDoublewireWorkorder.getSpecification());
                    mesInputLog.setProductModel(mesDoublewireWorkorder.getModel());
                }
            }
            // 通过工序判断绞线
            else if ("4".equals(mesInputLog.getTaskType())) {
                MdWorkbenchStrandedwire mdWorkbenchStrandedwire = remoteMdService.getWorkbenchStrandedwireById(mesInputLog.getWorkstationId(), SecurityConstants.INNER).getData();
                processId = mdWorkbenchStrandedwire.getProcessId();

                if (mesInputLog.getTaskId() != null) {
                    MesStrandedwireWorkorder mesStrandedwireWorkorder = mesStrandedwireWorkorderService.queryById(mesInputLog.getTaskId());
                    workorderSn = mesStrandedwireWorkorder.getWorkorderCode();

                    mesStrandedwireWorkorder.setStatus("1");
                    mesStrandedwireWorkorderService.update(mesStrandedwireWorkorder);

                    mesInputLog.setProductId(mesStrandedwireWorkorder.getProductId());
                    mesInputLog.setProductSpecification(mesStrandedwireWorkorder.getSpecification());
                    mesInputLog.setProductModel(mesStrandedwireWorkorder.getModel());
                }
            }

            // 通过工序查询对应仓库
            WmsWarehouse wmsWarehouse = remoteWmsService.getinfoByWorkstageId(processId, SecurityConstants.INNER).getData();
            mesInputLog.setWorkstageId(processId);
            mesInputLog.setWorkstageName(wmsWarehouse.getWorkstageName());
            mesInputLog.setWorkstageCode(wmsWarehouse.getWorkstageCode());

            // 通过物料唯一码查询物料数据
            WmsRealtimeStock inputStock = remoteWmsService.getInfoByItemIdentifier(mesInputLog.getInputSn(), SecurityConstants.INNER).getData();
            if (inputStock == null) {
                throw new RealtimeStockException("物料不存在!!!");
            } else {
                if (!"原料仓".equals(inputStock.getWarehouseName())) {
                    MesOuputLog infoByOutputSn = mesOuputLogMapper.getInfoByOutputSn(mesInputLog.getInputSn());
                    if (infoByOutputSn == null) {
                        throw new ProductException("该二维码对应报工记录不存在");
                    }
                }
                mesInputLog.setInputId(new BigInteger(inputStock.getItemId().toString()));
                mesInputLog.setInputName(inputStock.getItemName());
                mesInputLog.setInputCode(inputStock.getItemCode());
                Long itemTypeId = inputStock.getItemTypeId();
                mesInputLog.setInputType(itemTypeId == 1 ? "物料" : "产品");
                mesInputLog.setInputWeight(inputStock.getWeight());
                mesInputLog.setMeasureName(inputStock.getUnitOfMeasure());
                mesInputLog.setBatchCode(inputStock.getBatchCode());
                mesInputLog.setPartNumber(inputStock.getPartNumber());
                mesInputLog.setItemSpecification(inputStock.getSpecification());
                mesInputLog.setAttr2(inputStock.getScanCount() + 1);
            }

            // 处理工单和订单
            if (workorderSn != null) {
                MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryByWorkorderCode(workorderSn);
                if (mesProductWorkorder != null) {
                    mesInputLog.setOrderId(mesProductWorkorder.getOrderId());
                    mesInputLog.setOrderSn(mesProductWorkorder.getOrderCode());
                    mesInputLog.setOrderType(mesProductWorkorder.getOrderType());
                    mesInputLog.setClientName(mesProductWorkorder.getCustomerName());
                    mesInputLog.setWorkorderId(mesProductWorkorder.getId());
                    mesInputLog.setWorkorderSn(mesProductWorkorder.getWorkorderCode());

                    mesProductWorkorder.setStatus("2");
                    iMesProductWorkorderService.update(mesProductWorkorder);

                    if (mesProductWorkorder.getOrderId() != null) {
                        SaleOrder saleOrder = remoteSaleService.getOrder(mesProductWorkorder.getOrderId(), SecurityConstants.INNER).getData();
                        if (saleOrder == null) {
                            throw new ItemException("销售订单不存在");
                        }
                        saleOrder.setStatus("3");
                        R<AjaxResult> ajaxResultR = remoteSaleService.updateOrder(saleOrder, SecurityConstants.INNER);
                        if (ajaxResultR.getCode() != 200) {
                            throw new RuntimeException("主数据服务调用失败{" + ajaxResultR.getMsg() + "}");
                        }
                    }
                }
            }

            // 库存减少对应物料
            R<AjaxResult> ajaxResultR = remoteWmsService.subtractRealtimeStock(inputStock, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200) {
                throw new RuntimeException("wms服务调用失败{" + ajaxResultR.getMsg() + "}");
            }
        }

        // 批量插入所有处理后的记录
        return mesInputLogMapper.insertBatch(mesInputLogList);
    }

    /**
     * 更新数据
     *
     * @param mesInputLog 实例对象
     * @return 影响行数
     */
    @Override
    @Transactional
    public int update(MesInputLog mesInputLog) {
        BigInteger id = mesInputLog.getId();
        MesInputLog theUpdatemesInputLog = mesInputLogMapper.queryById(id);
        Integer version = theUpdatemesInputLog.getVersion();
        if (version != null) {
            mesInputLog.setVersion(version);
        }
        mesInputLog.setUpdateTime(DateUtils.getNowDate());
        return mesInputLogMapper.update(mesInputLog);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id) {
        int total = mesInputLogMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids) {
        int total = mesInputLogMapper.deleteByIds(ids);
        return total > 0;
    }

    @Override
    public List<EnamellingWorkorderVo> getWorkOdrerListByMesInputLogs(List<MesInputLog> mesInputLogs) {
        List<EnamellingWorkorderVo> list = new ArrayList<>();
        Map<BigInteger, String> map = new HashMap<>();
        Map<BigInteger, List<InputArrayVo>> inputIdMap = new HashMap<>();
        for (int i = 0; i < mesInputLogs.size(); i++) {
            MesInputLog mesInputLog = mesInputLogs.get(i);
            //通过投料机台、轴数查询最新投料记录
            MesInputLog mesInput = getInfoByWorkstationIdAndAlexPositionNumberAndAxleNumber(mesInputLog.getWorkstationId(), mesInputLog.getAlexPositionNumber(), mesInputLog.getAxleNumber());
            if (null == mesInput) {
                throw new ProductException(mesInputLog.getAlexPositionNumber() + "号轴无投料工单");
            }
            BigInteger taskId = mesInput.getTaskId();
            InputArrayVo inputArrayVo = new InputArrayVo();
            inputArrayVo.setInputId(mesInput.getId());
            inputArrayVo.setAlexPositionNumber(mesInput.getAlexPositionNumber());
            if (map.containsKey(taskId)) {
                //轴号
                String alexPositionNumbers = map.get(taskId);
                alexPositionNumbers = alexPositionNumbers + "," + mesInput.getAlexPositionNumber();
                map.replace(taskId, alexPositionNumbers);
                //投料id
                List<InputArrayVo> inputArrayVos = inputIdMap.get(taskId);
                inputArrayVos.add(inputArrayVo);
                inputIdMap.replace(taskId, inputArrayVos);
            } else {
                map.put(taskId, String.valueOf(mesInput.getAlexPositionNumber()));
                List<InputArrayVo> inputArrayVoList = new ArrayList<>();
                inputArrayVoList.add(inputArrayVo);
                inputIdMap.put(taskId, inputArrayVoList);
            }
        }
        Set<BigInteger> taskIds = map.keySet();
        for (BigInteger taskId : taskIds) {
            EnamellingWorkorderVo enamellingWorkorderVo = new EnamellingWorkorderVo();
            //根据taskId获取工单信息
            MesEnamellingWorkorder mesEnamellingWorkorder = iMesEnamellingWorkorderService.queryById(taskId);
            BeanUtils.copyProperties(mesEnamellingWorkorder, enamellingWorkorderVo);
            String alexPositionNumbers = map.get(taskId);
            enamellingWorkorderVo.setAlexPositionNumbers(alexPositionNumbers);
            List<InputArrayVo> inputArrayVos = inputIdMap.get(taskId);
            enamellingWorkorderVo.setInputIds(inputArrayVos);
            list.add(enamellingWorkorderVo);
        }
        return list;
    }

    @Override
    public List<AnnealingWorkorderVo> getWorkOdrerListByMesInputLogsToA(List<MesInputLog> mesInputLogs) {
        List<AnnealingWorkorderVo> list = new ArrayList<>();
        Map<BigInteger, String> map = new HashMap<>();
        Map<BigInteger, List<InputArrayVo>> inputIdMap = new HashMap<>();
        for (int i = 0; i < mesInputLogs.size(); i++) {
            MesInputLog mesInputLog = mesInputLogs.get(i);
            //通过投料机台、轴数查询最新投料记录
            MesInputLog mesInput = getInfoByWorkstationIdAndAlexPositionNumberAndAxleNumber(mesInputLog.getWorkstationId(), mesInputLog.getAlexPositionNumber(), mesInputLog.getAxleNumber());
            if (null == mesInput) {
                throw new ProductException(mesInputLog.getAlexPositionNumber() + "号轴无投料工单");
            }
            BigInteger taskId = mesInput.getTaskId();
            InputArrayVo inputArrayVo = new InputArrayVo();
            inputArrayVo.setInputId(mesInput.getId());
            inputArrayVo.setAlexPositionNumber(mesInput.getAlexPositionNumber());
            if (map.containsKey(taskId)) {
                //轴号
                String alexPositionNumbers = map.get(taskId);
                alexPositionNumbers = alexPositionNumbers + "," + mesInput.getAlexPositionNumber();
                map.replace(taskId, alexPositionNumbers);
                //投料id
                List<InputArrayVo> inputArrayVos = inputIdMap.get(taskId);
                inputArrayVos.add(inputArrayVo);
                inputIdMap.replace(taskId, inputArrayVos);
            } else {
                map.put(taskId, String.valueOf(mesInput.getAlexPositionNumber()));
                List<InputArrayVo> inputArrayVoList = new ArrayList<>();
                inputArrayVoList.add(inputArrayVo);
                inputIdMap.put(taskId, inputArrayVoList);
            }
        }
        Set<BigInteger> taskIds = map.keySet();
        for (BigInteger taskId : taskIds) {
            AnnealingWorkorderVo annealingWorkorderVo = new AnnealingWorkorderVo();
            //根据taskId获取工单信息
            MesAnnealingWorkorder mesAnnealingWorkorder = mesAnnealingWorkorderService.queryById(taskId);
            BeanUtils.copyProperties(mesAnnealingWorkorder, annealingWorkorderVo);
            String alexPositionNumbers = map.get(taskId);
            annealingWorkorderVo.setAlexPositionNumbers(alexPositionNumbers);
            List<InputArrayVo> inputArrayVos = inputIdMap.get(taskId);
            annealingWorkorderVo.setInputIds(inputArrayVos);
            list.add(annealingWorkorderVo);
        }
        return list;
    }

    @Override
    public List<DoublewireWorkorderVo> getWorkOdrerListByMesInputLogsToD(List<MesInputLog> mesInputLogs) {
        List<DoublewireWorkorderVo> list = new ArrayList<>();
        Map<BigInteger, String> map = new HashMap<>();
        Map<BigInteger, List<InputArrayVo>> inputIdMap = new HashMap<>();
        for (int i = 0; i < mesInputLogs.size(); i++) {
            MesInputLog mesInputLog = mesInputLogs.get(i);
            //通过投料机台、轴数查询最新投料记录
            MesInputLog mesInput = getInfoByWorkstationIdAndAxleNumber(mesInputLog.getWorkstationId(), mesInputLog.getAxleNumber(), mesInputLog.getWorkorderSn());
            if (null == mesInput) {
                throw new ProductException(mesInputLog.getAlexPositionNumber() + "号轴无投料工单");
            }
            BigInteger taskId = mesInput.getTaskId();
            InputArrayVo inputArrayVo = new InputArrayVo();
            inputArrayVo.setInputId(mesInput.getId());
            inputArrayVo.setAlexPositionNumber(mesInput.getAlexPositionNumber());
            if (map.containsKey(taskId)) {
                //轴号
                String alexPositionNumbers = map.get(taskId);
                alexPositionNumbers = alexPositionNumbers + "," + mesInput.getAlexPositionNumber();
                map.replace(taskId, alexPositionNumbers);
                //投料id
                List<InputArrayVo> inputArrayVos = inputIdMap.get(taskId);
                inputArrayVos.add(inputArrayVo);
                inputIdMap.replace(taskId, inputArrayVos);
            } else {
                map.put(taskId, String.valueOf(mesInput.getAlexPositionNumber()));
                List<InputArrayVo> inputArrayVoList = new ArrayList<>();
                inputArrayVoList.add(inputArrayVo);
                inputIdMap.put(taskId, inputArrayVoList);
            }
        }
        Set<BigInteger> taskIds = map.keySet();
        for (BigInteger taskId : taskIds) {
            DoublewireWorkorderVo doublewireWorkorderVo = new DoublewireWorkorderVo();
            //根据taskId获取工单信息
            MesDoublewireWorkorder mesDoublewireWorkorder = mesDoublewireWorkorderService.queryById(taskId);
            BeanUtils.copyProperties(mesDoublewireWorkorder, doublewireWorkorderVo);
            String alexPositionNumbers = map.get(taskId);
            doublewireWorkorderVo.setAlexPositionNumbers(alexPositionNumbers);
            List<InputArrayVo> inputArrayVos = inputIdMap.get(taskId);
            doublewireWorkorderVo.setInputIds(inputArrayVos);
            list.add(doublewireWorkorderVo);
        }
        return list;
    }

    @Override
    public List<StrandedwireWorkorderVo> getWorkOdrerListByMesInputLogsToS(List<MesInputLog> mesInputLogs) {
        List<StrandedwireWorkorderVo> list = new ArrayList<>();
        Map<BigInteger, String> map = new HashMap<>();
        Map<BigInteger, List<InputArrayVo>> inputIdMap = new HashMap<>();
        for (int i = 0; i < mesInputLogs.size(); i++) {
            MesInputLog mesInputLog = mesInputLogs.get(i);
            //通过投料机台、轴数查询最新投料记录
            MesInputLog mesInput = getInfoByWorkstationCodeAndWorkorderSn(mesInputLog.getWorkstationCode(),mesInputLog.getWorkorderSn());
            if (null == mesInput) {
                throw new ProductException(mesInputLog.getAlexPositionNumber() + "号轴无投料工单");
            }
            BigInteger taskId = mesInput.getTaskId();
            InputArrayVo inputArrayVo = new InputArrayVo();
            inputArrayVo.setInputId(mesInput.getId());
            inputArrayVo.setAlexPositionNumber(mesInput.getAlexPositionNumber());
            if (map.containsKey(taskId)) {
                //轴号
                String alexPositionNumbers = map.get(taskId);
                alexPositionNumbers = alexPositionNumbers + "," + mesInput.getAlexPositionNumber();
                map.replace(taskId, alexPositionNumbers);
                //投料id
                List<InputArrayVo> inputArrayVos = inputIdMap.get(taskId);
                inputArrayVos.add(inputArrayVo);
                inputIdMap.replace(taskId, inputArrayVos);
            } else {
                map.put(taskId, String.valueOf(mesInput.getAlexPositionNumber()));
                List<InputArrayVo> inputArrayVoList = new ArrayList<>();
                inputArrayVoList.add(inputArrayVo);
                inputIdMap.put(taskId, inputArrayVoList);
            }
        }
        Set<BigInteger> taskIds = map.keySet();
        for (BigInteger taskId : taskIds) {
            StrandedwireWorkorderVo strandedwireWorkorderVo = new StrandedwireWorkorderVo();
            //根据taskId获取工单信息
            MesStrandedwireWorkorder mesStrandedwireWorkorder = mesStrandedwireWorkorderService.queryById(taskId);
            BeanUtils.copyProperties(mesStrandedwireWorkorder, strandedwireWorkorderVo);
            String alexPositionNumbers = map.get(taskId);
            strandedwireWorkorderVo.setAlexPositionNumbers(alexPositionNumbers);
            List<InputArrayVo> inputArrayVos = inputIdMap.get(taskId);
            strandedwireWorkorderVo.setInputIds(inputArrayVos);
            list.add(strandedwireWorkorderVo);
        }
        return list;
    }

    @Override
    public List<TinningWorkorderVo> getWorkOdrerListByMesInputLogsToT(List<MesInputLog> mesInputLogs) {
        List<TinningWorkorderVo> list = new ArrayList<>();
        Map<BigInteger, String> map = new HashMap<>();
        Map<BigInteger, List<InputArrayVo>> inputIdMap = new HashMap<>();
        for (int i = 0; i < mesInputLogs.size(); i++) {
            MesInputLog mesInputLog = mesInputLogs.get(i);
            //通过投料机台、轴数查询最新投料记录
            MesInputLog mesInput = getInfoByWorkstationIdAndAlexPositionNumberAndAxleNumber(mesInputLog.getWorkstationId(), mesInputLog.getAlexPositionNumber(), mesInputLog.getAxleNumber());
            if (null == mesInput) {
                throw new ProductException(mesInputLog.getAlexPositionNumber() + "号轴无投料工单");
            }
            BigInteger taskId = mesInput.getTaskId();
            InputArrayVo inputArrayVo = new InputArrayVo();
            inputArrayVo.setInputId(mesInput.getId());
            inputArrayVo.setAlexPositionNumber(mesInput.getAlexPositionNumber());
            if (map.containsKey(taskId)) {
                //轴号
                String alexPositionNumbers = map.get(taskId);
                alexPositionNumbers = alexPositionNumbers + "," + mesInput.getAlexPositionNumber();
                map.replace(taskId, alexPositionNumbers);
                //投料id
                List<InputArrayVo> inputArrayVos = inputIdMap.get(taskId);
                inputArrayVos.add(inputArrayVo);
                inputIdMap.replace(taskId, inputArrayVos);
            } else {
                map.put(taskId, String.valueOf(mesInput.getAlexPositionNumber()));
                List<InputArrayVo> inputArrayVoList = new ArrayList<>();
                inputArrayVoList.add(inputArrayVo);
                inputIdMap.put(taskId, inputArrayVoList);
            }
        }
        Set<BigInteger> taskIds = map.keySet();
        for (BigInteger taskId : taskIds) {
            TinningWorkorderVo tinningWorkorderVo = new TinningWorkorderVo();
            //根据taskId获取工单信息
            MesTinningWorkorder mesTinningWorkorder = mesTinningWorkorderService.queryById(taskId);
            BeanUtils.copyProperties(mesTinningWorkorder, tinningWorkorderVo);
            String alexPositionNumbers = map.get(taskId);
            tinningWorkorderVo.setAlexPositionNumbers(alexPositionNumbers);
            List<InputArrayVo> inputArrayVos = inputIdMap.get(taskId);
            tinningWorkorderVo.setInputIds(inputArrayVos);
            list.add(tinningWorkorderVo);
        }
        return list;
    }

    @Override
    public int updateWorkorderComplete(String id) {
        return mesInputLogMapper.updateWorkorderComplete(id);
    }

    @Override
    public MesInputLog getInfoByInputSn(String inputSn) {
        return mesInputLogMapper.getInfoByInputSn(inputSn);
    }

    @Override
    public MesInputLog getWiringInputLog() {
        return mesInputLogMapper.getWiringInputLog();
    }

    @Override
    public List<MesInputLog> queryByIds(Set<BigInteger> inputIds) {
        return this.mesInputLogMapper.queryByIds(inputIds);
    }
}
