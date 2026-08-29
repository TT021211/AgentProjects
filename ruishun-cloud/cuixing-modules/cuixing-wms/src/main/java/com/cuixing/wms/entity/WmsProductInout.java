package com.cuixing.wms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.io.Serializable;

/**
 * 成品出入库(WmsProductInout)实体类
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
public class WmsProductInout implements Serializable {
    private static final long serialVersionUID = 913386047273914336L;
    /**
     * 成品出入表id
     */
    private Long id;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品型号
     */
    private String productSpec;
    /**
     * 单位
     */
    private String productUnit;
    /**
     * 出入库单据号
     */
    private String documentNumber;
    /**
     * 产品批次号
     */
    private String productBatchNumber;
    /**
     * 原料批次号
     */
    private String materialBatchNumber;
    /**
     * 箱编码
     */
    private String boxCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 出入库状态
     */
    private String status;
    /**
     * 产品数量
     */
    private Integer productNumber;
    /**
     * 净重
     */
    private Double  netWeight;
    /**
     * 毛重
     */
    private Double  grossWeight;
    /**
     * 称重时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date weighTime;
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
     * 备用字段5
     */
    private String artt5;
    /**
     * 备用字段6
     */
    private String artt6;
    /**
     * 备用字段7
     */
    private String artt7;
    /**
     * 备用字段8
     */
    private String artt8;
    /**
     * 备用字段9
     */
    private String artt9;
    /**
     * 备用字段10
     */
    private String artt10;
    @Transient
    private String startTime;
    @Transient
    private String endTime;

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

    public WmsProductInout() {
    }

    public WmsProductInout(String productCode, String productName, String productBatchNumber, String productSpec) {
        this.productCode = productCode;
        this.productName = productName;
        this.productBatchNumber = productBatchNumber;
        this.productSpec = productSpec;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getProductBatchNumber() {
        return productBatchNumber;
    }

    public void setProductBatchNumber(String productBatchNumber) {
        this.productBatchNumber = productBatchNumber;
    }

    public String getMaterialBatchNumber() {
        return materialBatchNumber;
    }

    public void setMaterialBatchNumber(String materialBatchNumber) {
        this.materialBatchNumber = materialBatchNumber;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public Double  getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double  netWeight) {
        this.netWeight = netWeight;
    }

    public Double  getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Double  grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Date getWeighTime() {
        return weighTime;
    }

    public void setWeighTime(Date weighTime) {
        this.weighTime = weighTime;
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

    public String getArtt7() {
        return artt7;
    }

    public void setArtt7(String artt7) {
        this.artt7 = artt7;
    }

    public String getArtt8() {
        return artt8;
    }

    public void setArtt8(String artt8) {
        this.artt8 = artt8;
    }

    public String getArtt9() {
        return artt9;
    }

    public void setArtt9(String artt9) {
        this.artt9 = artt9;
    }

    public String getArtt10() {
        return artt10;
    }

    public void setArtt10(String artt10) {
        this.artt10 = artt10;
    }
}

