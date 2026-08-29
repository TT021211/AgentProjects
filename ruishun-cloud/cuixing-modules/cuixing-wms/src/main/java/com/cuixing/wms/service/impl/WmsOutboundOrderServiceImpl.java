package com.cuixing.wms.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.wms.entity.*;
import com.cuixing.wms.mapper.*;
import com.cuixing.wms.service.WmsOutboundOrderService;
import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 * 成品出库单(WmsOutboundOrder)表服务实现类
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
@Service("wmsOutboundOrderService")
public class WmsOutboundOrderServiceImpl implements WmsOutboundOrderService {
    @Autowired
    private WmsOutboundOrderMapper wmsOutboundOrderMapper;
    @Autowired
    private WmsWeighRecordMapper wmsWeighRecordMapper;
    @Autowired
    private WmsBoxMapper wmsBoxMapper;
    @Autowired
    private WmsInstallationMapper wmsInstallationMapper;
    @Autowired
    private WmsProductInoutMapper wmsProductInoutMapper;
    @Autowired
    private WmsNoticeLnvoiceMapper wmsNoticeLnvoiceMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsOutboundOrder queryById(Long id) {
        return this.wmsOutboundOrderMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param wmsOutboundOrder 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WmsOutboundOrder> selectWmsOutboundOrderList(WmsOutboundOrder wmsOutboundOrder) {
        return this.wmsOutboundOrderMapper.selectWmsOutboundOrderList(wmsOutboundOrder);
    }

    /**
     * 新增数据
     *
     * @param wmsOutboundOrder 实例对象
     * @return 实例对象
     */
    @Override
    public WmsOutboundOrder insert(WmsOutboundOrder wmsOutboundOrder) {
        this.wmsOutboundOrderMapper.insert(wmsOutboundOrder);
        return wmsOutboundOrder;
    }

    /**
     * 修改数据
     *
     * @param wmsOutboundOrder 实例对象
     * @return 实例对象
     */
    @Override
    public WmsOutboundOrder update(WmsOutboundOrder wmsOutboundOrder) {
        this.wmsOutboundOrderMapper.update(wmsOutboundOrder);
        return this.queryById(wmsOutboundOrder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.wmsOutboundOrderMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public boolean updateStatus(WmsOutboundOrder wmsOutboundOrder) {
        Integer artt3 = wmsOutboundOrder.getArtt3();
        //出库操作
        if (artt3 == 1) {
            if (wmsOutboundOrder.getDocumentNumber() != null) {
                WmsNoticeLnvoice wmsNoticeLnvoice = this.wmsNoticeLnvoiceMapper.queryByCode(wmsOutboundOrder.getDocumentNumber());
                //修改发货通知单状态
                if (wmsNoticeLnvoice != null) {
                    wmsNoticeLnvoice.setDocumentStatus("OUT");
                    this.wmsNoticeLnvoiceMapper.update(wmsNoticeLnvoice);
                    //修改关联发货通知单下的状态
                    //修改托状态
                    List<WmsBox> wmsBoxes = new ArrayList<>();
                    List<String> list = new ArrayList<>();
                    if (wmsNoticeLnvoice.getArtt2() == null) {
                        wmsBoxes = this.wmsBoxMapper.queryByDocumentNumber(wmsOutboundOrder.getDocumentNumber());
                    } else {
                        String artt2 = wmsNoticeLnvoice.getArtt2();
                        String[] split = artt2.split(",");
                        //将拿到托编号集合
                        list = Arrays.asList(split);
                        if (!list.isEmpty()) {
                            for (String s : list) {
                                //根据托编号修改箱状态、已经盘状态、以及成品状态
                                WmsInstallation wmsInstallation1 = this.wmsInstallationMapper.queryByCode(s);
                                if (wmsInstallation1 != null){
                                    wmsInstallation1.setShipStatus("OUT");
                                    this.wmsInstallationMapper.update(wmsInstallation1);
                                }
                            }
                        }
                        wmsBoxes = this.wmsBoxMapper.selectInstallation(list);
                    }
                    Set<String> boxCodes = new HashSet<>();
                    if (!wmsBoxes.isEmpty()) {
                        for (WmsBox wmsBox : wmsBoxes) {
                            //修改箱状态
                            boxCodes.add(wmsBox.getBoxCode());
                            wmsBox.setInboundStatus("OUT");
                            this.wmsBoxMapper.update(wmsBox);
                            //修改称重记录状态
                            List<WmsWeighRecord> wmsWeighRecords = this.wmsWeighRecordMapper.selectWmsWeighRecordToBoxCodeList(wmsBox.getBoxCode());
                            if (!wmsWeighRecords.isEmpty()) {
                                for (WmsWeighRecord wmsWeighRecord : wmsWeighRecords) {
                                    wmsWeighRecord.setInboundStatus("OUT");
                                    this.wmsWeighRecordMapper.update(wmsWeighRecord);
                                }
                            }
                        }
                        List<String> boxCodelist = new ArrayList<>(boxCodes);
                        // 新增成品出库信息
                        List<WmsProductInout> wmsProductInouts = this.wmsProductInoutMapper.selectToBoxCodes(boxCodelist, wmsOutboundOrder.getDocumentNumber());
                        if (!wmsProductInouts.isEmpty()) {
                            WmsProductInout previousWmsProductInout = null; // 用于保存上一次插入的入库记录

                            for (int i = 0; i < wmsProductInouts.size(); i++) {
                                WmsProductInout wmsProductInout1 = new WmsProductInout();
                                WmsProductInout wmsProductInout2 = new WmsProductInout();

                                if (i == 0) {
                                    // 第一次：查询最大的一条入库记录
                                    wmsProductInout2 = this.wmsProductInoutMapper.selectMaxValues(
                                            wmsProductInouts.get(i).getProductCode(),
                                            wmsProductInouts.get(i).getProductName(),
                                            wmsProductInouts.get(i).getProductSpec(),
                                            wmsProductInouts.get(i).getProductBatchNumber()
                                    );

                                } else {
                                    // 从第二次开始：使用上一次的记录
                                    wmsProductInout2 = previousWmsProductInout;
                                }

                                /*// 变化前数量
                                String beforeNumber = wmsProductInout2.getArtt8();
                                wmsProductInout1.setArtt7(beforeNumber);

                                // 变化后数量
                                Integer nowNumber = wmsProductInouts.get(i).getProductNumber();
                                String afterNumber = String.valueOf(Integer.parseInt(beforeNumber) - nowNumber);
                                wmsProductInout1.setArtt8(afterNumber);

                                // 变化前重量
                                String beforeWeight = wmsProductInout2.getArtt10();
                                wmsProductInout1.setArtt9(beforeWeight);

                                // 使用 BigDecimal 来精确计算重量变化
                                BigDecimal beforeWeightDecimal = new BigDecimal(beforeWeight); // 变化前重量
                                BigDecimal currentWeight = new BigDecimal(String.valueOf(wmsProductInouts.get(i).getGrossWeight())); // 当前出库重量
                                BigDecimal afterWeightDecimal = beforeWeightDecimal.subtract(currentWeight).setScale(3, RoundingMode.HALF_UP); // 变化后重量

                                // 设置变化后的重量
                                wmsProductInout1.setArtt10(afterWeightDecimal.toString());*/
                                if (Objects.equals(wmsOutboundOrder.getOutboundType(), "自制产品订单")){
                                    wmsProductInout1.setArtt3(1);
                                }
                                wmsProductInout1.setStatus("OUT");
                                wmsProductInout1.setCreateTime(new Date());
                                wmsProductInout1.setCreateBy(SecurityUtils.getUsername());
                                wmsProductInout1.setDocumentNumber(wmsOutboundOrder.getOutboundCode());
                                wmsProductInout1.setArtt1(wmsOutboundOrder.getDocumentNumber());
                                wmsProductInout1.setProductNumber(wmsProductInouts.get(i).getProductNumber());
                                wmsProductInout1.setProductBatchNumber(wmsProductInouts.get(i).getProductBatchNumber());
                                wmsProductInout1.setProductCode(wmsProductInouts.get(i).getProductCode());
                                wmsProductInout1.setProductName(wmsProductInouts.get(i).getProductName());
                                wmsProductInout1.setProductSpec(wmsProductInouts.get(i).getProductSpec());
                                wmsProductInout1.setProductUnit(wmsProductInouts.get(i).getProductUnit());
                                wmsProductInout1.setBoxCode(wmsProductInouts.get(i).getBoxCode());
                                wmsProductInout1.setMaterialBatchNumber(wmsProductInouts.get(i).getMaterialBatchNumber());
                                wmsProductInout1.setNetWeight(wmsProductInouts.get(i).getNetWeight());
                                wmsProductInout1.setGrossWeight(wmsProductInouts.get(i).getGrossWeight());
                                wmsProductInout1.setWarehouseName(wmsProductInouts.get(i).getWarehouseName());

                                // 插入记录
                                this.wmsProductInoutMapper.insert(wmsProductInout1);

                                // 更新上一条记录为当前插入的记录
                                previousWmsProductInout = wmsProductInout1;
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("发货通知单不存在");
                }
            }
            wmsOutboundOrder.setOutboundStatus("OUT");
            wmsOutboundOrder.setOutboundDate(new Date());
            return this.wmsOutboundOrderMapper.update(wmsOutboundOrder) > 0;
        } else if (artt3 == 2) {
            //作废处理
            if (wmsOutboundOrder.getDocumentNumber() != null) {
                WmsNoticeLnvoice wmsNoticeLnvoice = this.wmsNoticeLnvoiceMapper.queryByCode(wmsOutboundOrder.getDocumentNumber());
                //删除发货通知单状态
                if (wmsNoticeLnvoice != null) {
                    //删除
                    this.wmsNoticeLnvoiceMapper.deleteById(wmsNoticeLnvoice.getId());
                    //修改关联发货通知单下的状态
                    //修改托状态
                    List<WmsBox> wmsBoxes = new ArrayList<>();
                    List<String> list = new ArrayList<>();
                    if (wmsNoticeLnvoice.getArtt2() == null) {
                        wmsBoxes = this.wmsBoxMapper.queryByDocumentNumber(wmsOutboundOrder.getDocumentNumber());
                    } else {
                        String artt2 = wmsNoticeLnvoice.getArtt2();
                        String[] split = artt2.split(",");
                        //将拿到托编号集合
                        list = Arrays.asList(split);
                        if (!list.isEmpty()) {
                            for (String s : list) {
                                //根据托编号修改箱状态、已经盘状态、以及成品状态
                                WmsInstallation wmsInstallation1 = this.wmsInstallationMapper.queryByCode(s);
                                wmsInstallation1.setShipStatus("IN");
                                this.wmsInstallationMapper.update(wmsInstallation1);
                            }
                        }
                        wmsBoxes = this.wmsBoxMapper.selectInstallation(list);
                    }
                    Set<String> boxCodes = new HashSet<>();
                    if (!wmsBoxes.isEmpty()) {
                        for (WmsBox wmsBox : wmsBoxes) {
                            boxCodes.add(wmsBox.getBoxCode());
                            //修改箱状态
                            wmsBox.setInboundStatus("IN");
                            this.wmsBoxMapper.update(wmsBox);
                            //修改称重记录状态
                            List<WmsWeighRecord> wmsWeighRecords = this.wmsWeighRecordMapper.selectWmsWeighRecordToBoxCodeList(wmsBox.getBoxCode());
                            if (!wmsWeighRecords.isEmpty()) {
                                for (WmsWeighRecord wmsWeighRecord : wmsWeighRecords) {
                                    wmsWeighRecord.setInboundStatus("IN");
                                    this.wmsWeighRecordMapper.update(wmsWeighRecord);
                                }
                            }
                        }
                        // 使用 Stream API 去重并转成 List
                        List<String> boxCodelist = new ArrayList<>(boxCodes);

                        //新增成品入库信息
                        List<WmsProductInout> wmsProductInouts = this.wmsProductInoutMapper.selectToBoxCodesAndStatus(boxCodelist, wmsOutboundOrder.getOutboundCode());
                        if (!wmsProductInouts.isEmpty()) {
                            WmsProductInout previousWmsProductInout = null; // 用于保存上一次插入的入库记录

                            for (int i = 0; i < wmsProductInouts.size(); i++) {

                                WmsProductInout wmsProductInout1 = new WmsProductInout();
                                WmsProductInout wmsProductInout2 = new WmsProductInout();

                                if (i == 0) {
                                    // 第一次：查询最大的一条入库记录
                                    wmsProductInout2 = this.wmsProductInoutMapper.selectMinValues(
                                            wmsProductInouts.get(i).getProductCode(),
                                            wmsProductInouts.get(i).getProductName(),
                                            wmsProductInouts.get(i).getProductSpec(),
                                            wmsProductInouts.get(i).getProductBatchNumber()
                                    );

                                } else {
                                    // 从第二次开始：使用上一次的记录
                                    wmsProductInout2 = previousWmsProductInout;
                                }

                                /*// 变化前数量
                                String beforeNumber = wmsProductInout2.getArtt8();
                                wmsProductInout1.setArtt7(beforeNumber);

                                // 变化后数量
                                Integer nowNumber = wmsProductInouts.get(i).getProductNumber();
                                String afterNumber = String.valueOf(Integer.parseInt(beforeNumber) + nowNumber);
                                wmsProductInout1.setArtt8(afterNumber);

                                // 变化前重量
                                String beforeWeight = wmsProductInout2.getArtt10();
                                wmsProductInout1.setArtt9(beforeWeight);

                                // 使用 BigDecimal 来精确计算重量变化
                                BigDecimal beforeWeightDecimal = new BigDecimal(beforeWeight); // 变化前重量
                                BigDecimal currentWeight = new BigDecimal(String.valueOf(wmsProductInouts.get(i).getGrossWeight())); // 当前出库重量
                                BigDecimal afterWeightDecimal = beforeWeightDecimal.add(currentWeight).setScale(3, RoundingMode.HALF_UP); // 变化后重量

                                // 设置变化后的重量
                                wmsProductInout1.setArtt10(afterWeightDecimal.toString());*/
                                if (Objects.equals(wmsOutboundOrder.getOutboundType(), "自制产品订单")){
                                    wmsProductInout1.setArtt3(1);
                                }
                                wmsProductInout1.setStatus("IN");
                                wmsProductInout1.setCreateTime(new Date());
                                wmsProductInout1.setCreateBy(SecurityUtils.getUsername());
                                wmsProductInout1.setProductNumber(wmsProductInouts.get(i).getProductNumber());
                                wmsProductInout1.setProductBatchNumber(wmsProductInouts.get(i).getProductBatchNumber());
                                wmsProductInout1.setProductCode(wmsProductInouts.get(i).getProductCode());
                                wmsProductInout1.setProductName(wmsProductInouts.get(i).getProductName());
                                wmsProductInout1.setProductSpec(wmsProductInouts.get(i).getProductSpec());
                                wmsProductInout1.setProductUnit(wmsProductInouts.get(i).getProductUnit());
                                wmsProductInout1.setBoxCode(wmsProductInouts.get(i).getBoxCode());
                                wmsProductInout1.setMaterialBatchNumber(wmsProductInouts.get(i).getMaterialBatchNumber());
                                wmsProductInout1.setNetWeight(wmsProductInouts.get(i).getNetWeight());
                                wmsProductInout1.setGrossWeight(wmsProductInouts.get(i).getGrossWeight());
                                wmsProductInout1.setWarehouseName(wmsProductInouts.get(i).getWarehouseName());

                                // 插入记录
                                this.wmsProductInoutMapper.insert(wmsProductInout1);

                                // 更新上一条记录为当前插入的记录
                                previousWmsProductInout = wmsProductInout1;
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("发货通知单不存在");
                }
            }
            wmsOutboundOrder.setOutboundStatus("INVALID");
            wmsOutboundOrder.setUpdateTime(new Date());
            return this.wmsOutboundOrderMapper.update(wmsOutboundOrder) > 0;
        }
        return false;
    }
}
