package com.cuixing.wms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (WmsInstallation)实体类
 *
 * @author makejava
 * @since 2024-10-21 11:44:28
 */
@ToString
public class WmsInstallation implements Serializable {
    private static final long serialVersionUID = 953468682606128570L;
    /**
     * 装托id
     */
    private Long id;
    /**
     * 装托编号
     */
    private String installationCode;
    /**
     * 装托时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 装托人
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
     * 批次号
     */
    private String batchNumber;
    /**
     * 批号
     */
    private String batchNumber2;
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
    private Double netWeight;
    /**
     * 毛重
     */
    private Double grossWeight;
    /**
     * 已装托数量
     */
    private String installationNumber;
    /**
     * 出货状态
     */
    private String shipStatus;
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
     * 料号
     */
    private String materialNumber;
    /**
     * 物料代码
     */
    private String materialCode;
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
     */
    private String snidCode;
    /**
     * 箱对象
     */
    private List<WmsBox> wmsBoxes;

    /**
     * 判断字段
     * @return
     */
    private int isFlag;

    public int getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(int isFlag) {
        this.isFlag = isFlag;
    }

    public String getSnidCode() {
        return snidCode;
    }

    public void setSnidCode(String snidCode) {
        this.snidCode = snidCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getBatchNumber2() {
        return batchNumber2;
    }

    public void setBatchNumber2(String batchNumber2) {
        this.batchNumber2 = batchNumber2;
    }

    public List<WmsBox> getWmsBoxes() {
        return wmsBoxes;
    }

    public void setWmsBoxes(List<WmsBox> wmsBoxes) {
        this.wmsBoxes = wmsBoxes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
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

    public Double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getInstallationNumber() {
        return installationNumber;
    }

    public void setInstallationNumber(String installationNumber) {
        this.installationNumber = installationNumber;
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
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

    public String getMaterialManufacturer() {
        return materialManufacturer;
    }

    public void setMaterialManufacturer(String materialManufacturer) {
        this.materialManufacturer = materialManufacturer;
    }


}

