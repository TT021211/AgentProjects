package com.cuixing.wms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.io.Serializable;

/**
 * 仓储管理---称重记录(WmsWeighRecord)实体类
 *
 * @author makejava
 * @since 2024-10-21 11:44:27
 */
public class WmsWeighRecord implements Serializable {
    private static final long serialVersionUID = 167840109788835106L;
    /**
     * 称重管理id
     */
    private Long id;
    /**
     * 箱id
     */
    private Long boxId;
    /**
     * 箱编码
     */
    private String boxCode;
    /**
     * 箱流水号
     */
    private String boxSerialNumber;
    /**
     * 每箱盘数
     */
    private Integer boxPerNumber;
    /**
     * 物料编码
     */
    private String itemCode;
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
     * 总净重
     */
    private double totalNetWeight;
    /**
     * 总毛重
     */
    private double totalGrossWeight;
    /**
     * 料号
     */
    private String materialNumber;
    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 批号
     */
    private String batchNumber2;
    /**
     * 称重时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date weighTime;
    /**
     * 单位
     */
    private String wmsUnit;
    /**
     * 入库状态
     */
    private String inboundStatus;
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
     * 轴流水号
     */
    private String axisSerialNumber;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 物料代码
     */
    private String materialCode;
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
     * 贸易商
     */
    private String trader;
    /**
     * 材料厂家
     */
    private String materialManufacturer;

    /**
     * 二级节点标识
     * @return
     */
    private String snidCode;

    /**
     * 产品唯一码
     * @return
     */
    private String productUniqueCode;

    @Transient
    private String startTime;
    @Transient
    private String endTime;

    private boolean isAdd;

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

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getProductUniqueCode() {
        return productUniqueCode;
    }

    public void setProductUniqueCode(String productUniqueCode) {
        this.productUniqueCode = productUniqueCode;
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

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public WmsWeighRecord(String boxCode) {
        this.boxCode = boxCode;
    }

    public WmsWeighRecord() {
    }

    public String getBatchNumber2() {
        return batchNumber2;
    }

    public void setBatchNumber2(String batchNumber2) {
        this.batchNumber2 = batchNumber2;
    }

    public String getBoxSerialNumber() {
        return boxSerialNumber;
    }

    public void setBoxSerialNumber(String boxSerialNumber) {
        this.boxSerialNumber = boxSerialNumber;
    }

    public Integer getBoxPerNumber() {
        return boxPerNumber;
    }

    public void setBoxPerNumber(Integer boxPerNumber) {
        this.boxPerNumber = boxPerNumber;
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

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
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

    public double getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(double totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public double getTotalGrossWeight() {
        return totalGrossWeight;
    }

    public void setTotalGrossWeight(double totalGrossWeight) {
        this.totalGrossWeight = totalGrossWeight;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Date getWeighTime() {
        return weighTime;
    }

    public void setWeighTime(Date weighTime) {
        this.weighTime = weighTime;
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

    public String getAxisSerialNumber() {
        return axisSerialNumber;
    }

    public void setAxisSerialNumber(String axisSerialNumber) {
        this.axisSerialNumber = axisSerialNumber;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
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

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getMaterialManufacturer() {
        return materialManufacturer;
    }

    public void setMaterialManufacturer(String materialManufacturer) {
        this.materialManufacturer = materialManufacturer;
    }

    @Override
    public String toString() {
        return "WmsWeighRecord{" +
                "id=" + id +
                ", boxId=" + boxId +
                ", boxCode='" + boxCode + '\'' +
                ", boxSerialNumber='" + boxSerialNumber + '\'' +
                ", boxPerNumber=" + boxPerNumber +
                ", itemCode='" + itemCode + '\'' +
                ", productName='" + productName + '\'' +
                ", wmsSpec='" + wmsSpec + '\'' +
                ", netWeight=" + netWeight +
                ", grossWeight=" + grossWeight +
                ", axleLoad=" + axleLoad +
                ", totalNetWeight=" + totalNetWeight +
                ", totalGrossWeight=" + totalGrossWeight +
                ", materialNumber='" + materialNumber + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", batchNumber2='" + batchNumber2 + '\'' +
                ", weighTime=" + weighTime +
                ", wmsUnit='" + wmsUnit + '\'' +
                ", inboundStatus='" + inboundStatus + '\'' +
                ", installationId=" + installationId +
                ", installationStatus='" + installationStatus + '\'' +
                ", installationCode='" + installationCode + '\'' +
                ", axisSerialNumber='" + axisSerialNumber + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", artt1='" + artt1 + '\'' +
                ", artt2='" + artt2 + '\'' +
                ", artt3=" + artt3 +
                ", artt4=" + artt4 +
                ", trader='" + trader + '\'' +
                ", materialManufacturer='" + materialManufacturer + '\'' +
                ", snidCode='" + snidCode + '\'' +
                ", productUniqueCode='" + productUniqueCode + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}

