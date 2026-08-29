package com.cuixing.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsProductInout;
import com.cuixing.wms.entity.WmsWeighRecord;
import com.cuixing.wms.mapper.WmsBoxMapper;
import com.cuixing.wms.mapper.WmsProductInoutMapper;
import com.cuixing.wms.mapper.WmsWeighRecordMapper;
import com.cuixing.wms.secondarynode.vo.SNBoxInfo;
import com.cuixing.wms.secondarynode.vo.SNProductInfo;
import com.cuixing.wms.service.WmsBoxService;
import com.cuixing.wms.utils.CallBack;
import com.cuixing.wms.utils.CommonUtils;
import com.cuixing.wms.utils.SecondaryNodeUtil;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * (WmsBox)表服务实现类
 *
 * @author makejava
 * @since 2024-10-21 11:44:28
 */
@Service("wmsBoxService")
public class WmsBoxServiceImpl implements WmsBoxService {
    private static final Logger log = LoggerFactory.getLogger(WmsBoxServiceImpl.class);
    @Autowired
    private WmsBoxMapper wmsBoxMapper;
    @Autowired
    private WmsWeighRecordMapper wmsWeighRecordMapper;
    @Autowired
    private WmsProductInoutMapper wmsProductInoutMapper;
    @Autowired
    private CommonUtils commonUtils;

    //private ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsBox queryById(Long id) {
        return this.wmsBoxMapper.queryById(id);
    }

    @Override
    public WmsBox queryByBoxCode(String boxCode) {
        return this.wmsBoxMapper.queryByCode(boxCode);
    }

    /**
     * 分页查询
     *
     * @param wmsBox 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WmsBox> selectWmsBoxList(WmsBox wmsBox) {
        return this.wmsBoxMapper.selectWmsBoxList(wmsBox);
    }

    /**
     * 从托中移除关联信息(取消关联盘、箱记录)
     *
     * @param wmsBox
     * @return
     */
    @Override
    public boolean disassociation(List<WmsBox> wmsBox) {
        if (!wmsBox.isEmpty()) {
            for (WmsBox wmsBox1 : wmsBox) {
                if (wmsBox1.getInstallationId() != null) {
                    //删除关联托信息
                    this.wmsBoxMapper.disassociation(wmsBox1);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 新增数据
     *
     * @param wmsBox 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public WmsBox insert(WmsBox wmsBox) {
        List<WmsBox> wmsBoxes = this.wmsBoxMapper.selectWmsBoxList(wmsBox);
        if (!wmsBoxes.isEmpty()) {
            log.error("箱编号重复");
            throw new RuntimeException("箱编号重复！！新增失败！！");
        }
        wmsBox.setCreateTime(new Date());
        wmsBox.setCreateBy(SecurityUtils.getUsername());
        wmsBox.setInboundStatus("IN");
        List<WmsWeighRecord> wmsWeighRecords = this.wmsWeighRecordMapper.selectWmsWeighRecordToBoxCodeList(wmsBox.getBoxCode());
        if (!wmsWeighRecords.isEmpty()) {
            for (WmsWeighRecord wmsWeighRecord : wmsWeighRecords) {
                wmsWeighRecord.setArtt2("1");
                this.wmsWeighRecordMapper.update(wmsWeighRecord);
            }
        }
        if (wmsBox.getArtt5() == null || !Objects.equals(wmsBox.getArtt5(), "N")) {
            BigDecimal grossWeightBD =BigDecimal.ZERO;
            BigDecimal axleLoadBD =BigDecimal.ZERO;
            //查询称重记录箱重量
            List<WmsWeighRecord> wmsWeighRecords1 = this.wmsWeighRecordMapper.selectCountByBoxCodeAndCy(wmsBox.getBoxCode(), wmsBox.getProductName(), wmsBox.getItemCode(), wmsBox.getWmsSpec(), wmsBox.getBatchNumber2(), wmsBox.getInstallationStatus(), wmsBox.getCreateBy());
            if (wmsWeighRecords1.isEmpty()){
                throw new RuntimeException("未找到称重记录");
            }else{
                for (WmsWeighRecord wmsWeighRecord : wmsWeighRecords1){
                    grossWeightBD=grossWeightBD.add(BigDecimal.valueOf(wmsWeighRecord.getGrossWeight()));
                    axleLoadBD=axleLoadBD.add(BigDecimal.valueOf(wmsWeighRecord.getAxleLoad()));
                }
            }
            Integer decimalPlaces = wmsBox.getDecimalPlaces();
            if (decimalPlaces ==null){
                throw new RuntimeException("请选择小数位数");
            }

            wmsBox.setGrossWeight(grossWeightBD.setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue());
            wmsBox.setAxleLoad(axleLoadBD.setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue());
            BigDecimal netWeightBD = grossWeightBD.subtract(axleLoadBD).setScale(decimalPlaces, RoundingMode.HALF_UP);
            // 验证净重非负（根据业务需求）
            if (netWeightBD.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("净重不能为负值");
            }

            // 更新 wmsBox 的净重
            wmsBox.setNetWeight(netWeightBD.doubleValue());
        }
        int insert1 = this.wmsBoxMapper.insert(wmsBox);
        log.info("箱信息插入成功，返回值：{}", insert1);
        //新增成品出入库表入库数据
        // 3. 查询并更新成品出入库记录（异步插入）
        WmsProductInout wmsProductInout = prepareWmsProductInout(wmsBox);
        int insert = wmsProductInoutMapper.insert(wmsProductInout);
        log.info("入库记录插入成功，返回值：{}", insert);
       /* // 6. 异步插入成品出入库记录
        CompletableFuture.runAsync(() -> {
            try {

                log.info("成品出入库记录插入成功，箱码：{}", wmsBox.getBoxCode());
            } catch (Exception e) {
                log.error("插入成品出入库记录失败，箱码：{}", wmsBox.getBoxCode(), e);
                throw new RuntimeException("插入成品出入库记录失败", e);
            }
        }, executorService).exceptionally(throwable -> {
            log.error("异步插入成品出入库记录时发生错误", throwable);
            throw new RuntimeException("异步插入失败", throwable);
        });*/
        return wmsBox;
    }
    /**
     * 准备成品出入库记录
     */
    private WmsProductInout prepareWmsProductInout(WmsBox wmsBox) {
        WmsProductInout wmsProductInout = new WmsProductInout();
        WmsProductInout existingInout = wmsProductInoutMapper.selectMaxValues(
                wmsBox.getItemCode(),
                wmsBox.getProductName(),
                wmsBox.getWmsSpec(),
                wmsBox.getBatchNumber2()
        );

        if (existingInout != null) {
           /* // 更新数量
            String beforeNumber = existingInout.getArtt8();
            wmsProductInout.setArtt7(beforeNumber);
            int nowNumber = wmsBox.getBoxNumber();
            wmsProductInout.setArtt8(String.valueOf(Integer.parseInt(beforeNumber) + nowNumber));

            // 更新重量
            String beforeWeight = existingInout.getArtt10();
            wmsProductInout.setArtt9(beforeWeight);
            BigDecimal beforeWeightDecimal = new BigDecimal(beforeWeight);
            BigDecimal currentWeight = new BigDecimal(String.valueOf(wmsBox.getGrossWeight()));
            BigDecimal afterWeightDecimal = beforeWeightDecimal.add(currentWeight).setScale(3, RoundingMode.HALF_UP);
            wmsProductInout.setArtt10(afterWeightDecimal.toString());
        } else {
            // 新记录
            wmsProductInout.setArtt7("0");
            wmsProductInout.setArtt8(String.valueOf(wmsBox.getArtt3()));
            wmsProductInout.setArtt9("0");
            BigDecimal grossWeight = new BigDecimal(String.valueOf(wmsBox.getGrossWeight())).setScale(3, RoundingMode.HALF_UP);
            wmsProductInout.setArtt10(grossWeight.toString());
        }*/
        }

        // 设置其他字段
        wmsProductInout.setProductCode(wmsBox.getItemCode());
        wmsProductInout.setProductName(wmsBox.getProductName());
        wmsProductInout.setProductSpec(wmsBox.getWmsSpec());
        wmsProductInout.setProductUnit(wmsBox.getWmsUnit());
        wmsProductInout.setProductBatchNumber(wmsBox.getBatchNumber2());
        wmsProductInout.setBoxCode(wmsBox.getBoxCode());
        wmsProductInout.setWarehouseName(wmsBox.getArtt1());
        wmsProductInout.setStatus("IN");
        wmsProductInout.setProductNumber(wmsBox.getBoxNumber());
        wmsProductInout.setNetWeight(wmsBox.getNetWeight());
        wmsProductInout.setGrossWeight(wmsBox.getGrossWeight());
        wmsProductInout.setCreateBy(SecurityUtils.getUsername());
        wmsProductInout.setCreateTime(new Date());

        return wmsProductInout;
    }

    /**
     * 修改数据
     *
     * @param wmsBox 实例对象
     * @return 实例对象
     */
    @Override
    public WmsBox update(WmsBox wmsBox) {
        wmsBox.setUpdateTime(new Date());
        wmsBox.setUpdateBy(SecurityUtils.getUsername());
        this.wmsBoxMapper.update(wmsBox);
        return this.queryById(wmsBox.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(Long id) {
        //删除箱之前先清除箱关联的信息
        WmsBox wmsBox = this.wmsBoxMapper.queryById(id);
        if (wmsBox.getInstallationCode() != null) {
            log.error("存在与托关联信息，无法删除");
            return false;
        }
        if (wmsBox.getInboundStatus().equals("OUT")) {
            log.error("箱已经出库，无法删除");
            return false;
        }
        //删除称重记录数据
        List<WmsWeighRecord> wmsWeighRecords = this.wmsWeighRecordMapper.selectWmsWeighRecordToBoxCodeList(wmsBox.getBoxCode());
        if (!wmsWeighRecords.isEmpty()) {
            for (WmsWeighRecord wmsWeighRecord : wmsWeighRecords) {
                this.wmsWeighRecordMapper.deleteById(wmsWeighRecord.getId());
            }
        }
        //删除成品出入库数据
        List<WmsProductInout> wmsProductInouts = this.wmsProductInoutMapper.selectToBoxCode(wmsBox.getBoxCode());
        if (!wmsProductInouts.isEmpty()) {
            log.info("删除成品出入库数据" + wmsProductInouts.get(0));
            commonUtils.deleteBoxAndUpdateSubsequentRecords(wmsBox.getBoxCode(), wmsBox.getProductName(), wmsBox.getWmsSpec(), wmsBox.getBatchNumber2(), wmsBox.getItemCode());
        }
        return this.wmsBoxMapper.deleteById(id) > 0;
    }

    @Override
    public List<WmsBox> selectInstallation(List<String> installationCodes) {
        return this.wmsBoxMapper.selectInstallation(installationCodes);
    }

    @Override
    public boolean deleteByBoxCode(String boxCode) {
        return this.wmsBoxMapper.deleteByBoxCode(boxCode) > 0;
    }

    @Override
    public void uploadSecondaryNode(final WmsBox entity) {
        if (StringUtils.isBlank(entity.getSnidCode())) {
            final SNBoxInfo snBoxInfo = new SNBoxInfo();
            BeanUtil.copyProperties(entity, snBoxInfo, new String[0]);
            snBoxInfo.setTotalWeight(BigDecimal.valueOf(entity.getGrossWeight()));//
            if (Objects.equals(entity.getCreateBy(), "elck")) {
                snBoxInfo.setWeightJy("二楼称重员");
            } else if (Objects.equals(entity.getCreateBy(), "slck")) {
                snBoxInfo.setWeightJy("三楼称重员");
            }
            snBoxInfo.setSpecification(entity.getWmsSpec());//
            snBoxInfo.setBatchCode(entity.getBatchNumber2());//
            snBoxInfo.setQuantity(String.valueOf(entity.getBoxPerNumber()));//
            snBoxInfo.setCreateTime(entity.getCreateTime());//
            snBoxInfo.setContainerCode(entity.getBoxCode());//
            snBoxInfo.setBoxSerialCode(entity.getBoxSerialNumber());//
            snBoxInfo.setItemCode2(entity.getMaterialCode());//
            snBoxInfo.setItemName(entity.getProductName());//
            snBoxInfo.setItemCode(entity.getItemCode());//
            SecondaryNodeUtil.add(snBoxInfo, "出入库箱信息", new CallBack() {
                public void execute(String snidPrefix, Object obj) {
                    System.out.println("这是产品的结果=" + obj);
                    if (obj != null && !"".equals(obj)) {
                        Map jsonObj = (Map) JSONUtil.toBean(obj.toString(), Map.class);
                        if (jsonObj.get("success").equals(true)) {
                            WmsBox entity2 = new WmsBox();
                            entity2.setId(entity.getId());
                            entity2.setSnidCode(snidPrefix + "/" + snBoxInfo.getHandle());
                            WmsBoxServiceImpl.this.wmsBoxMapper.update(entity2);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void updateWeight(Long id, Double countNetWeight, Double countGrossWeight) {
        this.wmsBoxMapper.updateWeight(id, countNetWeight, countGrossWeight);
    }
}
