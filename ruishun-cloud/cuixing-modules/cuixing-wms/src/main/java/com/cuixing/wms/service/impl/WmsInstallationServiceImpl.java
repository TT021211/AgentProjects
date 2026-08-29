package com.cuixing.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.system.api.factory.RemoteFileFallbackFactory;
import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsInstallation;
import com.cuixing.wms.entity.WmsProductInout;
import com.cuixing.wms.entity.WmsWeighRecord;
import com.cuixing.wms.mapper.WmsBoxMapper;
import com.cuixing.wms.mapper.WmsInstallationMapper;
import com.cuixing.wms.mapper.WmsProductInoutMapper;
import com.cuixing.wms.mapper.WmsWeighRecordMapper;
import com.cuixing.wms.secondarynode.vo.SNBoxInfo;
import com.cuixing.wms.secondarynode.vo.SNInstallationInfo;
import com.cuixing.wms.secondarynode.vo.SNProductInfo;
import com.cuixing.wms.service.WmsBoxService;
import com.cuixing.wms.service.WmsInstallationService;
import com.cuixing.wms.service.WmsWeighRecordService;
import com.cuixing.wms.utils.CallBack;
import com.cuixing.wms.utils.NumberGenerator;
import com.cuixing.wms.utils.SecondaryNodeUtil;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import javax.annotation.Resource;

/**
 * (WmsInstallation)表服务实现类
 *
 * @author makejava
 * @since 2024-10-21 11:44:29
 */
@Service("wmsInstallationService")
public class WmsInstallationServiceImpl implements WmsInstallationService {

    private static final Logger log = LoggerFactory.getLogger(WmsInstallationServiceImpl.class);

    private static final double EPSILON = 1e-10;
    private static final int SCALE = 2; // 保留两位小数

    @Autowired
    private WmsInstallationMapper wmsInstallationMapper;
    @Autowired
    public WmsBoxService wmsBoxService;
    @Autowired
    private WmsWeighRecordService wmsWeighRecordService;
    @Autowired
    private WmsProductInoutMapper wmsProductInoutMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsInstallation queryById(Long id) {
        return this.wmsInstallationMapper.queryById(id);
    }

    /**
     * 通过installationCode查询单条数据
     *
     * @param installationCode
     * @return
     */
    public WmsInstallation queryByCode(String installationCode) {
        return this.wmsInstallationMapper.queryByCode(installationCode);
    }

    /**
     * 取消关联
     *
     * @param boxes
     * @return
     */
    @Override
    @Transactional
    public boolean removeForBoxAndWeighRecord(List<WmsBox> boxes) {


        if (boxes == null || boxes.isEmpty()) {
            log.warn("Boxes list is empty or null");
            return false;
        }

        boolean success = wmsWeighRecordService.disassociationToRecord(boxes);
        if (!success) {
            log.error("Failed to disassociate weigh record");
            return false;
        }

        success = wmsBoxService.disassociation(boxes);
        if (!success) {
            log.error("Failed to disassociate boxes");
            return false;
        }

        for (WmsBox wmsBox : boxes) {
            Long installationId = wmsBox.getInstallationId();
            WmsInstallation wmsInstallation = wmsInstallationMapper.queryById(installationId);
            if (wmsInstallation == null) {
                log.warn("WmsInstallation not found for id: {}", installationId);
                continue;
            }

            // 处理净重
            BigDecimal netWeight = BigDecimal.valueOf(wmsBox.getNetWeight()).setScale(SCALE, RoundingMode.HALF_UP);
            BigDecimal netWeight1 = wmsInstallation.getNetWeight() != null ?
                    BigDecimal.valueOf(wmsInstallation.getNetWeight()).setScale(SCALE, RoundingMode.HALF_UP) :
                    BigDecimal.ZERO.setScale(SCALE, RoundingMode.HALF_UP);
            BigDecimal countNetWeight = netWeight1.subtract(netWeight);
            wmsInstallation.setNetWeight(countNetWeight.abs().compareTo(BigDecimal.valueOf(EPSILON)) < 0 ?
                    0.0 : countNetWeight.doubleValue());

            // 处理毛重
            BigDecimal grossWeight = BigDecimal.valueOf(wmsBox.getGrossWeight()).setScale(SCALE, RoundingMode.HALF_UP);
            BigDecimal grossWeight1 = wmsInstallation.getGrossWeight() != null ?
                    BigDecimal.valueOf(wmsInstallation.getGrossWeight()).setScale(SCALE, RoundingMode.HALF_UP) :
                    BigDecimal.ZERO.setScale(SCALE, RoundingMode.HALF_UP);
            BigDecimal countGrossWeight = grossWeight1.subtract(grossWeight);
            wmsInstallation.setGrossWeight(countGrossWeight.abs().compareTo(BigDecimal.valueOf(EPSILON)) < 0 ?
                    0.0 : countGrossWeight.doubleValue());

            // 处理数量
            if (Objects.equals(wmsBox.getArtt6(), "1")){
                //需要调新增接口
                WmsInstallation wmsInstallation1=new WmsInstallation();
                String newCode = NumberGenerator.generateNumber("ZT",new Date());; // 默认编码
                wmsInstallation1.setInstallationCode(newCode);
                wmsInstallation1.setCreateTime(DateUtils.getNowDate());
                wmsInstallation1.setCreateBy(SecurityUtils.getUsername());
                wmsInstallation1.setBatchNumber2(wmsInstallation.getBatchNumber2());
                wmsInstallation1.setItemCode(wmsInstallation.getItemCode());
                wmsInstallation1.setProductName(wmsInstallation.getProductName());
                wmsInstallation1.setWmsSpec(wmsInstallation.getWmsSpec());
                wmsInstallation1.setNetWeight(wmsBox.getNetWeight());
                wmsInstallation1.setGrossWeight(wmsBox.getGrossWeight());
                wmsInstallation1.setInstallationNumber(String.valueOf(wmsBox.getBoxNumber()));
                wmsInstallation1.setShipStatus("IN");
                wmsInstallation1.setArtt1(wmsInstallation.getArtt1());
                wmsInstallation1.setArtt2(wmsInstallation.getArtt2());
                wmsInstallation1.setArtt3(wmsInstallation.getArtt3());
                wmsInstallation1.setArtt4(wmsInstallation.getArtt4());
                wmsInstallation1.setMaterialNumber(wmsInstallation.getMaterialNumber());
                wmsInstallation1.setTrader(wmsInstallation.getTrader());
                wmsInstallation1.setMaterialCode(wmsInstallation.getMaterialCode());

                wmsInstallationMapper.insert(wmsInstallation1);
                //新增箱数据并关联
                WmsBox wmsBox1=new WmsBox();
                wmsBox1.setInstallationId(wmsInstallation1.getId());
                wmsBox1.setInstallationStatus("IN");
                wmsBox1.setInstallationCode(wmsInstallation1.getInstallationCode());
                wmsBox1.setCreateTime(DateUtils.getNowDate());
                wmsBox1.setCreateBy(SecurityUtils.getUsername());
                String xm = NumberGenerator.generateNumber("XM", new Date());
                wmsBox1.setBoxCode(xm);
                wmsBox1.setBoxSerialNumber(wmsBox.getBoxSerialNumber());
                wmsBox1.setBatchNumber2(wmsBox.getBatchNumber2());
                wmsBox1.setBoxPerNumber(wmsBox.getBoxPerNumber());
                wmsBox1.setBoxNumber(wmsBox.getBoxNumber());
                wmsBox1.setItemCode(wmsBox.getItemCode());
                wmsBox1.setProductName(wmsBox.getProductName());
                wmsBox1.setWmsSpec(wmsBox.getWmsSpec());
                wmsBox1.setNetWeight(wmsBox.getNetWeight());
                wmsBox1.setGrossWeight(wmsBox.getGrossWeight());
                wmsBox1.setInboundStatus("IN");
                wmsBox1.setAxleLoad(wmsBox.getAxleLoad());
                wmsBox1.setWmsUnit(wmsBox.getWmsUnit());
                wmsBox1.setArtt1(wmsBox.getArtt1());
                wmsBox1.setArtt2(wmsBox.getArtt2());
                wmsBox1.setArtt3(wmsBox.getArtt3());
                wmsBox1.setArtt4(wmsBox.getArtt4());
                wmsBox1.setMaterialNumber(wmsBox.getMaterialNumber());
                wmsBox1.setTrader(wmsBox.getTrader());
                wmsBox1.setMaterialCode(wmsBox.getMaterialCode());
                wmsBox1.setWmsUnit("kg");
                wmsBox1.setArtt5("N");
                wmsBoxService.insert(wmsBox1);
                List<WmsBox> wmsBoxes = this.wmsBoxService.selectInstallation(Collections.singletonList(wmsBox.getInstallationCode()));
                //更新箱数据重量
                wmsBoxService.updateWeight(wmsBoxes.get(0).getId(),countNetWeight.doubleValue(),countGrossWeight.doubleValue());
                /*//新增成品出入库数据，更新成品出入库重量
                WmsProductInout wmsProductInout=new WmsProductInout();
                wmsProductInout.setProductCode(wmsInstallation.getItemCode());
                wmsProductInout.setProductName(wmsInstallation.getProductName());
                wmsProductInout.setProductSpec(wmsInstallation.getWmsSpec());
                wmsProductInout.setProductUnit("kg");
                wmsProductInout.setProductBatchNumber(wmsInstallation.getBatchNumber2());
                wmsProductInout.setBoxCode(xm);
                wmsProductInout.setWarehouseName(wmsInstallation.getArtt1());
                wmsProductInout.setStatus("IN");
                wmsProductInout.setProductNumber(wmsBox.getBoxNumber());
                wmsProductInout.setNetWeight(wmsBox.getNetWeight());
                wmsProductInout.setGrossWeight(wmsBox.getGrossWeight());
                wmsProductInout.setCreateBy(SecurityUtils.getUsername());
                wmsProductInout.setCreateTime(DateUtils.getNowDate());
                wmsProductInoutMapper.insert(wmsProductInout);*/
                //更新成品出入库数据记录
                WmsProductInout wmsProductInout1 = wmsProductInoutMapper.queryByBoxCode(wmsBoxes.get(0).getBoxCode());
                Integer productNumber = wmsProductInout1.getProductNumber();
                Integer countProductNumber = productNumber - wmsBox.getBoxNumber();
                wmsProductInoutMapper.updateWeight(wmsProductInout1.getId(), countNetWeight.doubleValue(),countGrossWeight.doubleValue(),countProductNumber);

                //更新二级节点标识数据
                updateNodeData(wmsInstallation1);
            }else{
                int installationNumber = Integer.parseInt(wmsInstallation.getInstallationNumber());
                int boxNumber = wmsBox.getBoxNumber();
                wmsInstallation.setInstallationNumber(String.valueOf(installationNumber - boxNumber));
            }
            log.info("Before update: netWeight={}, grossWeight={}",
                    wmsInstallation.getNetWeight(), wmsInstallation.getGrossWeight());
            log.info("装托成功！{}", wmsInstallation);

            int rows = wmsInstallationMapper.update(wmsInstallation);
            if (rows == 0) {
                log.warn("Failed to update WmsInstallation for id: {}", installationId);
            }
        }

        return true;
    }


    /**
     * 分页查询
     *
     * @param wmsInstallation 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WmsInstallation> selectWmsInstallationList(WmsInstallation wmsInstallation) {
        return this.wmsInstallationMapper.selectWmsInstallationList(wmsInstallation);
    }

    /**
     * 新增数据
     *
     * @param wmsInstallation 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public WmsInstallation insert(WmsInstallation wmsInstallation) {
        //新增装托时，更新对应的箱管理记录、以及箱管理下面所属的单条称重记录
        if (wmsInstallation != null) {
            List<WmsBox> wmsBoxes = wmsInstallation.getWmsBoxes();
            if (!wmsBoxes.isEmpty()) {
                for (WmsBox wmsBox : wmsBoxes) {
                    if (!Objects.equals(wmsBox.getInstallationStatus(), "OUT")) {
                        log.error("选择的多箱当中存在已经装托的数据，请重新勾选再进行装托！");
                        throw new RuntimeException("选择的多箱当中存在已经装托的数据，请重新勾选再进行装托！");
                    }
                }
            }
            wmsInstallation.setCreateBy(SecurityUtils.getUsername());
            wmsInstallation.setCreateTime(new Date());
            wmsInstallation.setShipStatus("IN");
            //判断是否是选择的相同托编号
            if (wmsInstallation.getInstallationCode() != null&& !Objects.equals(wmsInstallation.getInstallationCode(), "")) {
                WmsInstallation wmsInstallation1 = this.wmsInstallationMapper.queryByCode(wmsInstallation.getInstallationCode());
                if (wmsInstallation1 != null) {
                    //更新
                    wmsInstallation1.setUpdateTime(new Date());
                    wmsInstallation1.setUpdateBy(SecurityUtils.getUsername());
                    wmsInstallation1.setNetWeight(wmsInstallation1.getNetWeight() + wmsInstallation.getNetWeight());
                    wmsInstallation1.setGrossWeight(wmsInstallation1.getGrossWeight() + wmsInstallation.getGrossWeight());
                    Integer installationNumber1 = Integer.parseInt(wmsInstallation1.getInstallationNumber());
                    Integer installationNumber = Integer.parseInt(wmsInstallation.getInstallationNumber());
                    String countInstallationNumber=String.valueOf(installationNumber1+installationNumber);
                    wmsInstallation1.setInstallationNumber(countInstallationNumber);
                    this.wmsInstallationMapper.update(wmsInstallation1);
                    //更新二级节点标识数据
                    updateNodeData(wmsInstallation1);
                    //修改相应的关联数据
                    updateOther(wmsInstallation, wmsInstallation1.getId(), wmsInstallation1.getInstallationCode());
                } else {
                    log.error("根据托编号查询数据为空");
                }

            } else {
                //如果没有则重新编码托编码
                String newCode = NumberGenerator.generateNumber("ZT",new Date());; // 默认编码
                wmsInstallation.setInstallationCode(newCode);
                //新增
                this.wmsInstallationMapper.insert(wmsInstallation);
                //修改相应的关联数据
                updateOther(wmsInstallation, wmsInstallation.getId(), newCode);
            }

        }
        return wmsInstallation;
    }

    private void updateOther(WmsInstallation wmsInstallation, Long id, String newCode) {
        List<WmsBox> wmsBoxes = wmsInstallation.getWmsBoxes();

        for (WmsBox wmsBox : wmsBoxes) {
            //更新箱信息
            if (wmsBox.getInstallationCode() == null) {
                wmsBox.setInstallationId(id);
                //已装托
                wmsBox.setInstallationStatus("IN");
                wmsBox.setInstallationCode(newCode);
                this.wmsBoxService.update(wmsBox);
            }
            //更新箱里面的盘信息
            List<WmsWeighRecord> wmsWeighRecords = this.wmsWeighRecordService.selectWmsWeighRecordList(new WmsWeighRecord(wmsBox.getBoxCode()));
            if (!wmsWeighRecords.isEmpty()) {
                for (WmsWeighRecord wmsWeighRecord : wmsWeighRecords) {
                    wmsWeighRecord.setBoxId(wmsBox.getId());
                    wmsWeighRecord.setInstallationId(id);
                    wmsWeighRecord.setInstallationCode(newCode);
                    wmsWeighRecord.setInstallationStatus("IN");
                    this.wmsWeighRecordService.update(wmsWeighRecord);
                }
            }
        }
    }

    @Override
    public void uploadSecondaryNode(final WmsInstallation entity) {
            final SNInstallationInfo snInstallationInfo = new SNInstallationInfo();
            BeanUtil.copyProperties(entity, snInstallationInfo, new String[0]);
            snInstallationInfo.setTotalWeight(BigDecimal.valueOf(entity.getGrossWeight()));
            snInstallationInfo.setInstallationCode(entity.getInstallationCode());
            if (Objects.equals(entity.getCreateBy(), "elck")) {
                snInstallationInfo.setWeightJy("二楼称重员");
            } else if (Objects.equals(entity.getCreateBy(), "slck")) {
                snInstallationInfo.setWeightJy("三楼称重员");
            }
            snInstallationInfo.setSpecification(entity.getWmsSpec());
            snInstallationInfo.setWeight(BigDecimal.valueOf(entity.getNetWeight()));
            snInstallationInfo.setCreateTime(entity.getCreateTime());
            SecondaryNodeUtil.add(snInstallationInfo, "出入库产品信息", new CallBack() {
                public void execute(String snidPrefix, Object obj) {
                    System.out.println("这是装托的结果=" + obj);
                    if (obj != null && !"".equals(obj)) {
                        Map jsonObj = (Map) JSONUtil.toBean(obj.toString(), Map.class);
                        if (jsonObj.get("success").equals(true)) {
                            WmsInstallation entity2 = new WmsInstallation();
                            entity2.setId(entity.getId());
                            entity2.setSnidCode(snidPrefix + "/" + snInstallationInfo.getHandle());
                            WmsInstallationServiceImpl.this.wmsInstallationMapper.update(entity2);
                        }
                    }
                }
            });
    }

    /**
     * 更新二级节点数据
     * @param entity
     */
    public void updateNodeData(final WmsInstallation entity) {
        if (StringUtils.isBlank(entity.getSnidCode())) {
            this.uploadSecondaryNode(entity);
        }
        final SNInstallationInfo snInstallationInfo = new SNInstallationInfo();
        BeanUtil.copyProperties(entity, snInstallationInfo, new String[0]);
        snInstallationInfo.setTotalWeight(BigDecimal.valueOf(entity.getGrossWeight()));
        snInstallationInfo.setInstallationCode(entity.getInstallationCode());
        if (Objects.equals(entity.getCreateBy(), "elck")) {
            snInstallationInfo.setWeightJy("二楼称重员");
        } else if (Objects.equals(entity.getCreateBy(), "slck")) {
            snInstallationInfo.setWeightJy("三楼称重员");
        }
        snInstallationInfo.setSpecification(entity.getWmsSpec());
        snInstallationInfo.setWeight(BigDecimal.valueOf(entity.getNetWeight()));
        snInstallationInfo.setCreateTime(entity.getCreateTime());
        SecondaryNodeUtil.update(snInstallationInfo, "出入库产品信息", new CallBack() {
            public void execute(String snidPrefix, Object obj) {
                System.out.println("这是装托更新的结果=" + obj);
                if (obj != null && !"".equals(obj)) {
                    Map jsonObj = (Map) JSONUtil.toBean(obj.toString(), Map.class);
                    if (jsonObj.get("success").equals(true)) {
                        WmsInstallation entity2 = new WmsInstallation();
                        entity2.setId(entity.getId());
                        entity2.setSnidCode(snidPrefix + "/" + snInstallationInfo.getHandle());
                        WmsInstallationServiceImpl.this.wmsInstallationMapper.update(entity2);
                    }
                }
            }
        });

    }

    /**
     * 修改数据
     *
     * @param wmsInstallation 实例对象
     * @return 实例对象
     */
    @Override
    public WmsInstallation update(WmsInstallation wmsInstallation) {
        this.wmsInstallationMapper.update(wmsInstallation);
        return this.queryById(wmsInstallation.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        //删除托前，查看关联状态
        WmsInstallation wmsInstallation = this.wmsInstallationMapper.queryById(id);
        if (wmsInstallation.getShipStatus().equals("OUT")){
            log.error("托已经出库，无法删除");
            return false;
        }
        List<WmsBox> wmsBoxes = this.wmsBoxService.selectInstallation(Collections.singletonList(wmsInstallation.getInstallationCode()));
        if (!wmsBoxes.isEmpty()){
            //清除托关联的箱信息
            this.wmsBoxService.disassociation(wmsBoxes);
            //清除箱下面的盘信息
            this.wmsWeighRecordService.disassociationToRecord(wmsBoxes);
        }
        return this.wmsInstallationMapper.deleteById(id) > 0;
    }
}
