package com.cuixing.wms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.io.Serializable;

/**
 * (WmsBox)实体类
 *
 * @author makejava
 * @since 2024-10-21 11:44:28
 */
public class WmsBox implements Serializable {
    private static final long serialVersionUID = 956737120348967475L;
    /**
     * 箱管理id
     */
    private Long id;
    /**
     * 装托id
     */
    private Long installationId;
    /**
     * 装托状态
     */
    private String installationStatus;
    /**
     * 装托编码
     */
    private String installationCode;
    /**
     * 称重时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 称重人
     */
    private String createBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 箱编号
     */
    private String boxCode;
    /**
     * 箱流水号
     */
    private String boxSerialNumber;
    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 批号
     */
    private String batchNumber2;
    /**
     * 每箱盘数
     */
    private Integer boxPerNumber;
    /**
     * 箱数
     */
    private Integer boxNumber;
    /**
     * 物料编码
     */
    private String itemCode;
    /**
     * 物料代码
     */
    private String materialCode;
    /**
     * 品名
     */
    private String productName;
    /**
     * 型号
     */
    private String wmsSpec;
    /**
     * 净重
     */
    private double netWeight;
    /**
     * 毛重
     */
    private double grossWeight;
    /**
     * 轴重
     */
    private double axleLoad;
    /**
     * 单位
     */
    private String wmsUnit;
    /**
     * 入库状态
     */
    private String inboundStatus;
    /**
     * 备用字段1
     */
    private String artt1;
    /**
     * 备用字段2
     */
    private String artt2;
    /**
     * 备用字段3
     */
    private Integer artt3;
    /**
     * 备用字段4
     */
    private Integer artt4;
    /**
     * 材料厂家
     */
    private String materialNumber;
    /**
     * 贸易商
     */
    private String trader;
    /**
     * 发货通知单号
     */
    private String noticeNumberCode;
    /**
     * 备用字段5
     */
    private String artt5;
    /**
     * 备用字段6
     */
    private String artt6;
    /**
     * 二级节点标识
     * @return
     */
    private String snidCode;
    @Transient
    private String startTime;
    @Transient
    private String endTime;

    /**
     * 保留几位小数
     * @return
     */
    private Integer decimalPlaces;

    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSnidCode() {
        return snidCode;
    }

    public void setSnidCode(String snidCode) {
        this.snidCode = snidCode;
    }

    public String getNoticeNumberCode() {
        return noticeNumberCode;
    }

    public void setNoticeNumberCode(String noticeNumberCode) {
        this.noticeNumberCode = noticeNumberCode;
    }

    public String getArtt5() {
        return artt5;
    }

    public void setArtt5(String artt5) {
        this.artt5 = artt5;
    }

    public String getArtt6() {
        return artt6;
    }

    public void setArtt6(String artt6) {
        this.artt6 = artt6;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstallationId() {
        return installationId;
    }

    public void setInstallationId(Long installationId) {
        this.installationId = installationId;
    }

    public String getInstallationStatus() {
        return installationStatus;
    }

    public void setInstallationStatus(String installationStatus) {
        this.installationStatus = installationStatus;
    }

    public String getInstallationCode() {
        return installationCode;
    }

    public void setInstallationCode(String installationCode) {
        this.installationCode = installationCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getBoxSerialNumber() {
        return boxSerialNumber;
    }

    public void setBoxSerialNumber(String boxSerialNumber) {
        this.boxSerialNumber = boxSerialNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getBoxPerNumber() {
        return boxPerNumber;
    }

    public void setBoxPerNumber(Integer boxPerNumber) {
        this.boxPerNumber = boxPerNumber;
    }

    public Integer getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(Integer boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWmsSpec() {
        return wmsSpec;
    }

    public void setWmsSpec(String wmsSpec) {
        this.wmsSpec = wmsSpec;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getAxleLoad() {
        return axleLoad;
    }

    public void setAxleLoad(double axleLoad) {
        this.axleLoad = axleLoad;
    }

    public String getWmsUnit() {
        return wmsUnit;
    }

    public void setWmsUnit(String wmsUnit) {
        this.wmsUnit = wmsUnit;
    }

    public String getInboundStatus() {
        return inboundStatus;
    }

    public void setInboundStatus(String inboundStatus) {
        this.inboundStatus = inboundStatus;
    }

    public String getArtt1() {
        return artt1;
    }

    public void setArtt1(String artt1) {
        this.artt1 = artt1;
    }

    public String getArtt2() {
        return artt2;
    }

    public void setArtt2(String artt2) {
        this.artt2 = artt2;
    }

    public Integer getArtt3() {
        return artt3;
    }

    public void setArtt3(Integer artt3) {
        this.artt3 = artt3;
    }

    public Integer getArtt4() {
        return artt4;
    }

    public void setArtt4(Integer artt4) {
        this.artt4 = artt4;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBatchNumber2() {
        return batchNumber2;
    }

    public void setBatchNumber2(String batchNumber2) {
        this.batchNumber2 = batchNumber2;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
}

