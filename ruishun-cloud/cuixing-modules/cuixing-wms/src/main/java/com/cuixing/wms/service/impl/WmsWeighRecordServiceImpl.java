package com.cuixing.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.system.api.RemoteMesService;
import com.cuixing.system.api.domain.mes.MesAnnealingWorkorder;
import com.cuixing.system.api.domain.mes.MesTinningWorkorder;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsProductInout;
import com.cuixing.wms.entity.WmsWeighRecord;
import com.cuixing.wms.entity.vo.WmsBoxListKanbanVo;
import com.cuixing.wms.entity.vo.WmsCountDataVo;
import com.cuixing.wms.entity.vo.WmsIngredientsLog12Vo;
import com.cuixing.wms.entity.vo.WmsWeighRecordVo;
import com.cuixing.wms.mapper.*;
import com.cuixing.wms.secondarynode.vo.SNProductInfo;
import com.cuixing.wms.service.IWmsRealtimeStockService;
import com.cuixing.wms.service.WmsBoxService;
import com.cuixing.wms.service.WmsWeighRecordService;
import com.cuixing.wms.utils.CallBack;
import com.cuixing.wms.utils.CommonUtils;
import com.cuixing.wms.utils.SecondaryNodeUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 仓储管理---称重记录(WmsWeighRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-10-21 11:44:27
 */
@Service("wmsWeighRecordService")
public class WmsWeighRecordServiceImpl implements WmsWeighRecordService {
    private static final Logger log = LoggerFactory.getLogger(WmsWeighRecordServiceImpl.class);
    @Autowired
    private WmsWeighRecordMapper wmsWeighRecordMapper;
    @Autowired
    private WmsProductInoutMapper wmsProductInoutMapper;
    @Autowired
    private IWmsRealtimeStockService wmsRealtimeStockService;
    @Autowired
    private WmsBoxMapper wmsBoxMapper;
    @Autowired
    private WmsIngredientsLogMapper wmsIngredientsLogMapper;
    @Autowired
    private WmsTransactionMapper wmsTransactionMapper;
    @Autowired
    private RemoteMesService remoteMesService;
    /*@Autowired
    private WmsBoxService wmsBoxService;*/

    @Autowired
    private CommonUtils commonUtils;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsWeighRecord queryById(Long id) {
        return this.wmsWeighRecordMapper.queryById(id);
    }

    @Override
    public WmsWeighRecord selectMaxValues() {
        return this.wmsWeighRecordMapper.selectMaxValues(SecurityUtils.getUsername());
    }

    /**
     * 从托中移除关联信息(取消关联盘、箱记录)
     *
     * @param wmsBox
     * @return
     */
    @Override
    public boolean disassociationToRecord(List<WmsBox> wmsBox) {
        if (!wmsBox.isEmpty()) {
            for (WmsBox wmsBox1 : wmsBox) {
                if (wmsBox1.getBoxCode() != null) {
                    List<WmsWeighRecord> wmsWeighRecords = this.wmsWeighRecordMapper.selectWmsWeighRecordList(new WmsWeighRecord(wmsBox1.getBoxCode()));
                    if (!wmsWeighRecords.isEmpty()) {
                        for (WmsWeighRecord wmsWeighRecord : wmsWeighRecords) {
                            this.wmsWeighRecordMapper.disassociationToRecord(wmsWeighRecord);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 分页查询
     *
     * @param wmsWeighRecord 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WmsWeighRecord> selectWmsWeighRecordList(WmsWeighRecord wmsWeighRecord) {
        return this.wmsWeighRecordMapper.selectWmsWeighRecordList(wmsWeighRecord);
    }

    /**
     * 新增数据
     *
     * @param wmsWeighRecord 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public WmsWeighRecord insert(WmsWeighRecord wmsWeighRecord) {
        // 验证用户
        String username = SecurityUtils.getUsername();
        if (username == null) {
            log.error("用户未登录，无法插入称重记录");
            throw new BussinessException("用户未登录");
        }

        // 检查 boxCode 是否存在且创建人匹配
        List<WmsWeighRecord> wmsWeighRecords = this.wmsWeighRecordMapper.selectByBoxCode(wmsWeighRecord.getBoxCode());
        for (WmsWeighRecord record : wmsWeighRecords) {
            if (!Objects.equals(record.getCreateBy(), username)) {
                log.error("当前用户不是此称重记录的创建人，无法进行操作");
                throw new BussinessException("当前用户不是此称重记录的创建人");
            }
        }

        // 四舍五入 grossWeight 和 axleLoad
        roundWeights(wmsWeighRecord);

        // 计算净重
        BigDecimal grossWeightBD = BigDecimal.valueOf(wmsWeighRecord.getGrossWeight());
        BigDecimal axleLoadBD = BigDecimal.valueOf(wmsWeighRecord.getAxleLoad());
        BigDecimal netWeightBD = getBigDecimal2(wmsWeighRecord, grossWeightBD, axleLoadBD);
        wmsWeighRecord.setNetWeight(netWeightBD.doubleValue());

        // 设置默认字段
        wmsWeighRecord.setCreateBy(username);
        wmsWeighRecord.setCreateTime(new Date());
        wmsWeighRecord.setWeighTime(new Date());
        wmsWeighRecord.setInboundStatus("IN");

        /*// 检查箱盘数并插入 WmsBox
        Integer boxPerNumber = wmsWeighRecord.getBoxPerNumber();
        if (boxPerNumber == null) {
            log.error("箱盘数未设置");
            throw new BussinessException("箱盘数未设置");
        }
        List<WmsWeighRecord> wmsWeighRecords1 = this.wmsWeighRecordMapper.selectCountByBoxCodeAndCy(wmsWeighRecord);
        if (!wmsWeighRecords1.isEmpty() && wmsWeighRecords1.size() == boxPerNumber || boxPerNumber == 1) {
            createWmsBox(wmsWeighRecord, wmsWeighRecords, boxPerNumber);
        }*/

        // 更新库存和工单
        if (wmsWeighRecord.getProductUniqueCode() != null) {
            WmsRealtimeStock wmsRealtimeStock = this.wmsRealtimeStockService.getInfoByItemIdentifier(wmsWeighRecord.getProductUniqueCode());
            if (wmsRealtimeStock == null) {
                log.error("未找到产品唯一码对应的库存信息：{}", wmsWeighRecord.getProductUniqueCode());
                throw new BussinessException("未找到库存信息");
            }
            try {
                MesAnnealingWorkorder data = remoteMesService.queryByMesAnnealingWorkorderCode(wmsRealtimeStock.getWorkorderCode(), SecurityConstants.INNER).getData();
                MesTinningWorkorder data1 = remoteMesService.queryByTinningWorkorderCode(wmsRealtimeStock.getWorkorderCode(), SecurityConstants.INNER).getData();
                updateWorkOrder(data, data1, wmsWeighRecord);
                wmsRealtimeStockService.insertWmsRealtimeStock(wmsRealtimeStock, "subtract");
            } catch (Exception e) {
                log.error("更新工单或库存失败：{}", e.getMessage());
                throw new BussinessException("更新工单或库存失败", e);
            }
        }

        // 插入称重记录
        int result = this.wmsWeighRecordMapper.insert(wmsWeighRecord);
        if (result > 0 && wmsWeighRecord.getId() != null) {
            log.info("新插入记录的 ID：{}", wmsWeighRecord.getId());
            return wmsWeighRecord;
        } else {
            log.error("插入称重记录失败");
            throw new BussinessException("插入称重记录失败");
        }
    }
    private void createWmsBox(WmsWeighRecord wmsWeighRecord, List<WmsWeighRecord> wmsWeighRecords, int boxPerNumber) {
        WmsBox wmsBox = new WmsBox();
        wmsBox.setBoxCode(wmsWeighRecord.getBoxCode());
        wmsBox.setMaterialNumber(wmsWeighRecord.getMaterialManufacturer());
        wmsBox.setBoxSerialNumber(wmsWeighRecord.getBoxSerialNumber());
        wmsBox.setBatchNumber2(wmsWeighRecord.getBatchNumber2());
        wmsBox.setBoxPerNumber(boxPerNumber);
        wmsBox.setBoxNumber(1);
        wmsBox.setItemCode(wmsWeighRecord.getItemCode());
        wmsBox.setProductName(wmsWeighRecord.getProductName());
        wmsBox.setWmsSpec(wmsWeighRecord.getWmsSpec());
        wmsBox.setWmsUnit(wmsWeighRecord.getWmsUnit());
        wmsBox.setInboundStatus("IN");
        wmsBox.setArtt1(wmsWeighRecord.getArtt1());
        wmsBox.setArtt2(wmsWeighRecord.getArtt2());
        wmsBox.setArtt3(wmsWeighRecord.getArtt3());
        wmsBox.setArtt4(wmsWeighRecord.getArtt4());
        wmsBox.setTrader(wmsWeighRecord.getTrader());

        // 计算总重量
        BigDecimal totalNetWeightBD = BigDecimal.ZERO;
        BigDecimal totalGrossWeightBD = BigDecimal.ZERO;
        BigDecimal totalAxleLoadBD = BigDecimal.ZERO;
        int decimalPlaces = wmsWeighRecord.getDecimalPlaces() != null ? wmsWeighRecord.getDecimalPlaces() : 2;

        if (boxPerNumber == 1) {
            totalNetWeightBD = BigDecimal.valueOf(wmsWeighRecord.getNetWeight());
            totalGrossWeightBD = BigDecimal.valueOf(wmsWeighRecord.getGrossWeight());
            totalAxleLoadBD = BigDecimal.valueOf(wmsWeighRecord.getAxleLoad());
        } else if (!wmsWeighRecords.isEmpty()) {
            for (WmsWeighRecord record : wmsWeighRecords) {
                totalNetWeightBD = totalNetWeightBD.add(BigDecimal.valueOf(record.getNetWeight()));
                totalGrossWeightBD = totalGrossWeightBD.add(BigDecimal.valueOf(record.getGrossWeight()));
                totalAxleLoadBD = totalAxleLoadBD.add(BigDecimal.valueOf(record.getAxleLoad()));
            }
        }

        // 四舍五入
        wmsBox.setNetWeight(totalNetWeightBD.setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue());
        wmsBox.setGrossWeight(totalGrossWeightBD.setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue());
        wmsBox.setAxleLoad(totalAxleLoadBD.setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue());

        /*// 插入 WmsBox
        this.wmsBoxService.insert(wmsBox);*/
    }
    private void roundWeights(WmsWeighRecord wmsWeighRecord) {
        Integer decimalPlaces = wmsWeighRecord.getDecimalPlaces();
        if (decimalPlaces == null) {
            log.error("小数位数未设置");
            throw new IllegalArgumentException("请选择小数位数");
        }
        if (decimalPlaces < 0) {
            log.error("小数位数无效：{}", decimalPlaces);
            throw new IllegalArgumentException("小数位数不能为负数");
        }

        int scale = (decimalPlaces == 2 || decimalPlaces == 3) ? decimalPlaces : 2; // 默认 2 位
        BigDecimal grossWeightBD = BigDecimal.valueOf(wmsWeighRecord.getGrossWeight())
                .setScale(scale, RoundingMode.HALF_UP);
        BigDecimal axleLoadBD = BigDecimal.valueOf(wmsWeighRecord.getAxleLoad())
                .setScale(scale, RoundingMode.HALF_UP);

        wmsWeighRecord.setGrossWeight(grossWeightBD.doubleValue());
        wmsWeighRecord.setAxleLoad(axleLoadBD.doubleValue());
    }
    private BigDecimal getBigDecimal2(WmsWeighRecord wmsWeighRecord, BigDecimal grossWeightBD, BigDecimal axleLoadBD) {

        BigDecimal netWeightBD= BigDecimal.valueOf(0);


        Integer decimalPlaces = wmsWeighRecord.getDecimalPlaces();
        if (decimalPlaces!=null){
            if (decimalPlaces == 2){
                // 计算净重（毛重 - 轴重）
                // 计算净重（毛重 - 轴重），保留两位小数
                netWeightBD = grossWeightBD.subtract(axleLoadBD).setScale(2, RoundingMode.HALF_UP);
            }
            if (decimalPlaces == 3){
                // 计算净重（毛重 - 轴重）
                // 计算净重（毛重 - 轴重），保留三位小数
                netWeightBD = grossWeightBD.subtract(axleLoadBD).setScale(3, RoundingMode.HALF_UP);
            }
            // 验证净重非负（根据业务需求）
            if (netWeightBD.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("净重不能为负值");
            }

        }
        return netWeightBD;
    }

    /**
     * 更新工单信息
     */
    public void updateWorkOrder(MesAnnealingWorkorder mesAnnealingWorkorder,MesTinningWorkorder mesTinningWorkorder,WmsWeighRecord wmsWeighRecord){
        if (mesAnnealingWorkorder != null && mesTinningWorkorder != null){
            throw new RuntimeException("退火工单和镀锡工单不能同时存在");
        }
        if (mesAnnealingWorkorder != null){
            //完成量增加
            if (mesAnnealingWorkorder.getWightCompletion() == null){
                mesAnnealingWorkorder.setWightCompletion(0.0);
            }
            BigDecimal wightCompletion = BigDecimal.valueOf(mesAnnealingWorkorder.getWightCompletion());
            BigDecimal outputWeight = BigDecimal.valueOf(wmsWeighRecord.getNetWeight());
            BigDecimal newWightCompletion = wightCompletion.add(outputWeight);
            BigDecimal weight = BigDecimal.valueOf(mesAnnealingWorkorder.getWeight());
            BigDecimal degreeOfCompletion = newWightCompletion.divide(weight, 2, BigDecimal.ROUND_HALF_UP);
            mesAnnealingWorkorder.setWightCompletion(newWightCompletion.doubleValue());
            mesAnnealingWorkorder.setDegreeOfCompletion(degreeOfCompletion.doubleValue());
            remoteMesService.updateMesAnnealingWorkorder(mesAnnealingWorkorder, SecurityConstants.INNER);
        }
        if (mesTinningWorkorder != null){
            //完成量增加
            if (mesTinningWorkorder.getWightCompletion() == null){
                mesTinningWorkorder.setWightCompletion(0.0);
            }
            BigDecimal wightCompletion = BigDecimal.valueOf(mesTinningWorkorder.getWightCompletion());
            BigDecimal outputWeight = BigDecimal.valueOf(wmsWeighRecord.getNetWeight());
            BigDecimal newWightCompletion = wightCompletion.add(outputWeight);
            BigDecimal weight = BigDecimal.valueOf(mesTinningWorkorder.getWeight());
            BigDecimal degreeOfCompletion = newWightCompletion.divide(weight, 2, BigDecimal.ROUND_HALF_UP);
            mesTinningWorkorder.setWightCompletion(newWightCompletion.doubleValue());
            mesTinningWorkorder.setDegreeOfCompletion(degreeOfCompletion.doubleValue());
            remoteMesService.updateMesTinningWorkorder(mesTinningWorkorder, SecurityConstants.INNER);
        }
    }


    /**
     * 修改数据
     *
     * @param wmsWeighRecord 实例对象
     * @return 实例对象
     */
    @Override
    public WmsWeighRecord update(WmsWeighRecord wmsWeighRecord) {
        wmsWeighRecord.setUpdateBy(SecurityUtils.getUsername());
        wmsWeighRecord.setUpdateTime(new Date());
        this.wmsWeighRecordMapper.update(wmsWeighRecord);
        return this.queryById(wmsWeighRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        try {
            int rowsAffected = this.wmsWeighRecordMapper.deleteById(id);
            if (rowsAffected <= 0) {
                log.warn("删除记录失败，ID: {}，无匹配记录", id);
            } else {
                log.info("成功删除记录，ID: {}", id);
            }
            return true; // 强制返回 true
        } catch (Exception e) {
            log.error("删除记录时发生异常，ID: {}", id, e);
            return true; // 即使异常也返回 true
        }
    }

    @Override
    public List<WmsWeighRecord> queryYesterday() {
        return this.wmsWeighRecordMapper.queryYesterday(SecurityUtils.getUsername());
    }

    @Override
    @Transactional
    public boolean deleteByBoxCode(String boxCode) {
        //删除成品出入库数据
        List<WmsProductInout> wmsProductInouts = this.wmsProductInoutMapper.selectToBoxCode(boxCode.trim());
        if (!wmsProductInouts.isEmpty()){
            commonUtils.deleteBoxAndUpdateSubsequentRecords(boxCode, wmsProductInouts.get(0).getProductName(), wmsProductInouts.get(0).getProductSpec(),wmsProductInouts.get(0).getProductBatchNumber(), wmsProductInouts.get(0).getProductCode());
        }
        //删除箱记录
        this.wmsBoxMapper.deleteByBoxCode(boxCode);
        return this.wmsWeighRecordMapper.deleteByBoxCode(boxCode);
    }

    @Override
    public WmsWeighRecord selectWmsWeighRecordToParams(WmsWeighRecord wmsWeighRecord) {
        wmsWeighRecord.setCreateBy(SecurityUtils.getUsername());
        return this.wmsWeighRecordMapper.selectWmsWeighRecordToParams(wmsWeighRecord);
    }

    @Override
    public WmsWeighRecord selectWmsWeighRecordToParams2(WmsWeighRecord wmsWeighRecord) {
        return this.wmsWeighRecordMapper.selectWmsWeighRecordToParams2(wmsWeighRecord);
    }

    @Override
    public void updateNodeData(final WmsWeighRecord entity) {
        System.out.println("boxCode=" + entity.getBoxCode() + "/" + entity.getBoxPerNumber().compareTo(entity.getBoxPerNumber()));
        if (StringUtils.isBlank(entity.getSnidCode())) {
            this.uploadSecondaryNode(entity);
        }

        final SNProductInfo sNProductInfo = new SNProductInfo();
        BeanUtil.copyProperties(entity, sNProductInfo, new String[0]);
        sNProductInfo.setTotalWeight(BigDecimal.valueOf(entity.getGrossWeight()));
        sNProductInfo.setBoxSerialChildCode(entity.getAxisSerialNumber());
        SecondaryNodeUtil.update(sNProductInfo, "出入库产品信息", new CallBack() {
            public void execute(String snidPrefix, Object obj) {
                System.out.println("这是产品的结果=" + obj);
                if (obj != null && !"".equals(obj)) {
                    Map jsonObj = (Map) JSONUtil.toBean(obj.toString(), Map.class);
                    if (jsonObj.get("success").equals(true)) {
                        WmsWeighRecord entity2 = new WmsWeighRecord();
                        entity2.setId(entity.getId());
                        entity2.setSnidCode(snidPrefix + "/" + sNProductInfo.getHandle());
                        WmsWeighRecordServiceImpl.this.wmsWeighRecordMapper.update(entity2);
                    }
                }
            }
        });

    }


    @Override
    public void uploadSecondaryNode(final WmsWeighRecord entity) {
        if (StringUtils.isBlank(entity.getSnidCode())) {
            final SNProductInfo sNProductInfo = new SNProductInfo();
            BeanUtil.copyProperties(entity, sNProductInfo, new String[0]);
            sNProductInfo.setTotalWeight(BigDecimal.valueOf(entity.getGrossWeight()));
            sNProductInfo.setBoxSerialChildCode(entity.getAxisSerialNumber());
            if (Objects.equals(entity.getCreateBy(), "elck")) {
                sNProductInfo.setWeightJy("二楼称重员");
            } else if (Objects.equals(entity.getCreateBy(), "slck")) {
                sNProductInfo.setWeightJy("三楼称重员");
            }
            sNProductInfo.setSpecification(entity.getWmsSpec());
            sNProductInfo.setBatchCode(entity.getBatchNumber2());
            sNProductInfo.setWeight(BigDecimal.valueOf(entity.getNetWeight()));
            sNProductInfo.setCreateTime(entity.getCreateTime());
            sNProductInfo.setContainerCode(entity.getBoxCode());
            sNProductInfo.setBoxSerialCode(entity.getBoxSerialNumber());
            sNProductInfo.setItemCode2(entity.getMaterialCode());
            sNProductInfo.setOtherWeight(String.valueOf(entity.getAxleLoad()));
            SecondaryNodeUtil.add(sNProductInfo, "出入库产品信息", new CallBack() {
                public void execute(String snidPrefix, Object obj) {
                    System.out.println("这是产品的结果=" + obj);
                    if (obj != null && !"".equals(obj)) {
                        Map jsonObj = (Map) JSONUtil.toBean(obj.toString(), Map.class);
                        if (jsonObj.get("success").equals(true)) {
                            WmsWeighRecord entity2 = new WmsWeighRecord();
                            entity2.setId(entity.getId());
                            entity2.setSnidCode(snidPrefix + "/" + sNProductInfo.getHandle());
                            WmsWeighRecordServiceImpl.this.wmsWeighRecordMapper.update(entity2);
                        }
                    }
                }
            });
        }
    }

    /**
     * 获取统计数据
     * @return
     */
    @Override
    public WmsCountDataVo getWmsCountData() {
        //1 成品总箱数(入库)、原料在库净重总重、铜丝在制品总重、成品总重量（入库净重）
        WmsCountDataVo wmsCountDataVo = new WmsCountDataVo();
        //1.1 获取成品总箱数(入库)
        int countBoxNumber = wmsBoxMapper.getCountBoxNumber("IN");
        wmsCountDataVo.setCountBoxNum(countBoxNumber);
        wmsCountDataVo.setProductBoxNum(countBoxNumber);
        //1.1.1 获取成品今日入库箱数
        int countBoxNumbertoDay = wmsBoxMapper.getCountBoxNumbertoDay("IN");
        wmsCountDataVo.setProductInBoxNum(countBoxNumbertoDay);
        //1.1.2 获取成品今日入库重量
        BigDecimal productInWeight = wmsBoxMapper.getCountWeighttoDay("IN");
        wmsCountDataVo.setProductInWeight(productInWeight);
        //1.1.3 获取成品今日出库箱数
        int productOutBoxNum = wmsBoxMapper.getCountBoxNumbertoDay("OUT");
        wmsCountDataVo.setProductOutBoxNum(productOutBoxNum);
        //1.1.4 获取成品今日出库重量
        BigDecimal productOutWeight = wmsBoxMapper.getCountWeighttoDay("OUT");
        wmsCountDataVo.setProductOutWeight(productOutWeight);
        //1.1.5 最近三天入库箱数据
        List<WmsBox> wmsBoxListTowDay = wmsBoxMapper.selectWmsBoxListTowDay("IN");
        wmsCountDataVo.setWmsBoxListTowDay(wmsBoxListTowDay);
        //1.1.6 今年每月出入库重量及数据
        List<WmsIngredientsLog12Vo> wmsBoxListKanbanVo = wmsIngredientsLogMapper.getCountBoxDataToYear();
        wmsCountDataVo.setWmsIngredientsLog12Vo(wmsBoxListKanbanVo);
        //1.2 获取原料在库净重总重
        BigDecimal countIngredientsLogWeight = wmsIngredientsLogMapper.getCountIngredientsLogWeight("0");
        wmsCountDataVo.setIngredientsTotalWeight(countIngredientsLogWeight);
        //1.3 获取铜丝在制品总重
        BigDecimal itemIssueOut = wmsTransactionMapper.getCountWeight("ITEM_ISSUE_OUT");
        wmsCountDataVo.setSemiProductTotalWeight(itemIssueOut);
        //1.4 获取成品总重量（入库净重）
        BigDecimal countWeight = wmsBoxMapper.getCountWeight("IN");
        wmsCountDataVo.setProductTotalWeight(countWeight);
        //2 统计箱数据
        List<WmsBoxListKanbanVo> in = wmsBoxMapper.getCountBoxData("IN");
        wmsCountDataVo.setWmsBoxListKanbanVo(in);

        return wmsCountDataVo;
    }

    // 定义 wmsSpec 对应的 gross_weight 范围
    private static final Map<String, WeightRange> SPEC_WEIGHT_RANGES = new HashMap<>();

    static {
        SPEC_WEIGHT_RANGES.put("0.06", new WeightRange(3, 5));
        SPEC_WEIGHT_RANGES.put("0.08", new WeightRange(3, 8));
        SPEC_WEIGHT_RANGES.put("0.05", new WeightRange(2, 4));
        SPEC_WEIGHT_RANGES.put("0.127", new WeightRange(10, 15));
        SPEC_WEIGHT_RANGES.put("0.15", new WeightRange(10, 15));
    }
    // 重量范围类
    private static class WeightRange {
        int start;
        int end;

        WeightRange(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    // 查询类型枚举
    public enum QueryType {
        DAY("1"), MONTH("2"), YEAR("3");

        private final String value;

        QueryType(String value) {
            this.value = value;
        }

        public static QueryType fromValue(String value) {
            for (QueryType type : values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("无效的查询类型: " + value);
        }
    }
    @Override
    public Map<String, List<WmsWeighRecordVo>> getWmsCountDataForTime(List<String> wmsSpecs, Date date, String type) {
        // 输入验证
        if (wmsSpecs == null || wmsSpecs.isEmpty()) {
            return Collections.emptyMap(); // 返回空 Map，避免 null
        }
        if (date == null ) {
            date = new Date(); // 默认为当前日期
        }
        QueryType queryType;
        try {
            queryType = QueryType.fromValue(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("查询类型必须为 '1'（当天）、'2'（当月）或 '3'（当年）", e);
        }

        Map<String, List<WmsWeighRecordVo>> result = new HashMap<>();

        try {
            switch (queryType) {
                case DAY:
                    // 按规格查询当天数据
                    String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    result = wmsSpecs.stream()
                            .filter(SPEC_WEIGHT_RANGES::containsKey) // 过滤有效规格
                            .collect(Collectors.toMap(
                                    spec -> spec,
                                    spec -> {
                                        WeightRange range = SPEC_WEIGHT_RANGES.get(spec);
                                        return wmsWeighRecordMapper.queryBySpecForDate(day,spec, range.start, range.end);
                                    },
                                    (v1, v2) -> v1, // 合并策略（理论上不会重复）
                                    HashMap::new
                            ));
                    break;

                case MONTH:
                    // 获取当月时间（基于传入的 date）
                    String yearMonth = new SimpleDateFormat("yyyy-MM").format(date);
                    result = wmsSpecs.stream()
                            .filter(SPEC_WEIGHT_RANGES::containsKey) // 过滤有效规格
                            .collect(Collectors.toMap(
                                    spec -> spec,
                                    spec -> {
                                        WeightRange range = SPEC_WEIGHT_RANGES.get(spec);
                                        return wmsWeighRecordMapper.queryBySpecForMonth(yearMonth,spec, range.start, range.end);
                                    },
                                    (v1, v2) -> v1, // 合并策略（理论上不会重复）
                                    HashMap::new
                            ));
                    break;
                case YEAR:
                    // 获取当年（基于传入的 date）
                    String year = new SimpleDateFormat("yyyy").format(date);
                    result = wmsSpecs.stream()
                            .filter(SPEC_WEIGHT_RANGES::containsKey) // 过滤有效规格
                            .collect(Collectors.toMap(
                                    spec -> spec,
                                    spec -> {
                                        WeightRange range = SPEC_WEIGHT_RANGES.get(spec);
                                        return wmsWeighRecordMapper.queryBySpecForYear(year,spec, range.start, range.end);
                                    },
                                    (v1, v2) -> v1, // 合并策略（理论上不会重复）
                                    HashMap::new
                            ));
                    break;
            }
        } catch (Exception e) {
            // 记录异常并抛出
            throw new RuntimeException("查询称重记录数据失败: " + e.getMessage(), e);
        }

        return result.isEmpty() ? Collections.emptyMap() : result;
    }

}
