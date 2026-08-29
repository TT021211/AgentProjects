package com.cuixing.wms.entity;

import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class WmsTransaction extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private Long transactionId;
    @Excel(
            name = "事务类型"
    )
    private String transactionType;
    @Excel(
            name = "产品物料ID"
    )
    private Long itemId;
    @Excel(
            name = "产品物料编码"
    )
    private String itemCode;
    @Excel(
            name = "产品物料名称"
    )
    private String itemName;
    @Excel(
            name = "规格型号"
    )
    private String specification;
    @Excel(
            name = "单位"
    )
    private String unitOfMeasure;
    @Excel(
            name = "入库批次号"
    )
    private String batchCode;
    @Excel(
            name = "容器码"
    )
    private String containerCode;
    @Excel(
            name = "仓库ID"
    )
    private Long warehouseId;
    @Excel(
            name = "仓库编码"
    )
    private String warehouseCode;
    @Excel(
            name = "仓库名称"
    )
    private String warehouseName;
    @Excel(
            name = "库区ID"
    )
    private Long locationId;
    @Excel(
            name = "库区编码"
    )
    private String locationCode;
    @Excel(
            name = "库区名称"
    )
    private String locationName;
    @Excel(
            name = "库位ID"
    )
    private Long areaId;
    @Excel(
            name = "库位编码"
    )
    private String areaCode;
    @Excel(
            name = "库位名称"
    )
    private String areaName;
    @Excel(
            name = "供应商ID"
    )
    private Long vendorId;
    @Excel(
            name = "供应商编号"
    )
    private String vendorCode;
    @Excel(
            name = "供应商名称"
    )
    private String vendorName;
    @Excel(
            name = "供应商简称"
    )
    private String vendorNick;
    @Excel(
            name = "单据类型"
    )
    private String sourceDocType;
    @Excel(
            name = "单据ID"
    )
    private Long sourceDocId;
    @Excel(
            name = "单据编号"
    )
    private String sourceDocCode;
    @Excel(
            name = "单据行ID"
    )
    private Long sourceDocLineId;
    @Excel(
            name = "库存记录ID"
    )
    private Long materialStockId;
    @Excel(
            name = "库存方向"
    )
    private Integer transactionFlag;
    @Excel(
            name = "事务数量"
    )
    private BigDecimal transactionQuantity;
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @Excel(
            name = "事务日期",
            width = 30.0,
            dateFormat = "yyyy-MM-dd"
    )
    private Date transactionDate;
    @Excel(
            name = "关联的事务ID"
    )
    private Long relatedTransactionId;
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @Excel(
            name = "ERP账期",
            width = 30.0,
            dateFormat = "yyyy-MM-dd"
    )
    private Date erpDate;
    private Long workorderId;
    @Excel(
            name = "生产工单编号"
    )
    private String workorderCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date recptDate;

    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @Excel(
            name = "库存有效期",
            width = 30.0,
            dateFormat = "yyyy-MM-dd"
    )
    private Date expireDate;
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @Excel(
            name = "记录日期",
            width = 30.0,
            dateFormat = "yyyy-MM-dd"
    )
    private Date createDate;
    private boolean storageCheckFlag;
    @Excel(
            name = "预留字段1"
    )
    private String attr1;
    @Excel(
            name = "预留字段2"
    )
    private String attr2;
    @Excel(
            name = "预留字段3"
    )
    private Long attr3;
    @Excel(
            name = "预留字段4"
    )
    private Long attr4;
    private BigDecimal weight;
    private BigDecimal totalWeight;
    private String weightUnit;
    //预警值
    private BigDecimal waringValue;


    public BigDecimal getBeforeQuantity() {
        return beforeQuantity;
    }

    public void setBeforeQuantity(BigDecimal beforeQuantity) {
        this.beforeQuantity = beforeQuantity;
    }

    public BigDecimal getBeforeWeight() {
        return beforeWeight;
    }

    public void setBeforeWeight(BigDecimal beforeWeight) {
        this.beforeWeight = beforeWeight;
    }

    public BigDecimal getAfterQuantity() {
        return afterQuantity;
    }

    public void setAfterQuantity(BigDecimal afterQuantity) {
        this.afterQuantity = afterQuantity;
    }

    public BigDecimal getAfterWeight() {
        return afterWeight;
    }

    public void setAfterWeight(BigDecimal afterWeight) {
        this.afterWeight = afterWeight;
    }

    private BigDecimal beforeQuantity;
    private BigDecimal beforeWeight;
    private BigDecimal afterQuantity;
    private BigDecimal afterWeight;


    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSpecification() {
        return this.specification;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getBatchCode() {
        return this.batchCode;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() {
        return this.warehouseId;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName() {
        return this.warehouseName;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getAreaId() {
        return this.areaId;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getVendorId() {
        return this.vendorId;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorNick(String vendorNick) {
        this.vendorNick = vendorNick;
    }

    public String getVendorNick() {
        return this.vendorNick;
    }

    public void setSourceDocType(String sourceDocType) {
        this.sourceDocType = sourceDocType;
    }

    public String getSourceDocType() {
        return this.sourceDocType;
    }

    public void setSourceDocId(Long sourceDocId) {
        this.sourceDocId = sourceDocId;
    }

    public Long getSourceDocId() {
        return this.sourceDocId;
    }

    public void setSourceDocCode(String sourceDocCode) {
        this.sourceDocCode = sourceDocCode;
    }

    public String getSourceDocCode() {
        return this.sourceDocCode;
    }

    public void setSourceDocLineId(Long sourceDocLineId) {
        this.sourceDocLineId = sourceDocLineId;
    }

    public Long getSourceDocLineId() {
        return this.sourceDocLineId;
    }

    public void setMaterialStockId(Long materialStockId) {
        this.materialStockId = materialStockId;
    }

    public Long getMaterialStockId() {
        return this.materialStockId;
    }

    public void setTransactionFlag(Integer transactionFlag) {
        this.transactionFlag = transactionFlag;
    }

    public Integer getTransactionFlag() {
        return this.transactionFlag;
    }

    public void setTransactionQuantity(BigDecimal transactionQuantity) {
        this.transactionQuantity = transactionQuantity;
    }

    public BigDecimal getTransactionQuantity() {
        return this.transactionQuantity;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public void setRelatedTransactionId(Long relatedTransactionId) {
        this.relatedTransactionId = relatedTransactionId;
    }

    public Long getRelatedTransactionId() {
        return this.relatedTransactionId;
    }

    public void setErpDate(Date erpDate) {
        this.erpDate = erpDate;
    }

    public Date getErpDate() {
        return this.erpDate;
    }

    public Long getWorkorderId() {
        return this.workorderId;
    }

    public void setWorkorderId(Long workorderId) {
        this.workorderId = workorderId;
    }

    public String getWorkorderCode() {
        return this.workorderCode;
    }

    public void setWorkorderCode(String workorderCode) {
        this.workorderCode = workorderCode;
    }

    public Date getRecptDate() {
        return this.recptDate;
    }

    public void setRecptDate(Date recptDate) {
        this.recptDate = recptDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr1() {
        return this.attr1;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr2() {
        return this.attr2;
    }

    public void setAttr3(Long attr3) {
        this.attr3 = attr3;
    }

    public Long getAttr3() {
        return this.attr3;
    }

    public void setAttr4(Long attr4) {
        this.attr4 = attr4;
    }

    public Long getAttr4() {
        return this.attr4;
    }

    public boolean isStorageCheckFlag() {
        return this.storageCheckFlag;
    }

    public void setStorageCheckFlag(boolean storageCheckFlag) {
        this.storageCheckFlag = storageCheckFlag;
    }

    public String getContainerCode() {
        return this.containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getWeight() {
        return this.weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getTotalWeight() {
        return this.totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getWeightUnit() {
        return this.weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }


    public BigDecimal getWaringValue() {
        return waringValue;
    }

    public void setWaringValue(BigDecimal waringValue) {
        this.waringValue = waringValue;
    }

    @Override
    public String toString() {
        return "WmsTransaction{" +
                "transactionId=" + transactionId +
                ", transactionType='" + transactionType + '\'' +
                ", itemId=" + itemId +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", specification='" + specification + '\'' +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", containerCode='" + containerCode + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", locationId=" + locationId +
                ", locationCode='" + locationCode + '\'' +
                ", locationName='" + locationName + '\'' +
                ", areaId=" + areaId +
                ", areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", vendorId=" + vendorId +
                ", vendorCode='" + vendorCode + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorNick='" + vendorNick + '\'' +
                ", sourceDocType='" + sourceDocType + '\'' +
                ", sourceDocId=" + sourceDocId +
                ", sourceDocCode='" + sourceDocCode + '\'' +
                ", sourceDocLineId=" + sourceDocLineId +
                ", materialStockId=" + materialStockId +
                ", transactionFlag=" + transactionFlag +
                ", transactionQuantity=" + transactionQuantity +
                ", transactionDate=" + transactionDate +
                ", relatedTransactionId=" + relatedTransactionId +
                ", erpDate=" + erpDate +
                ", workorderId=" + workorderId +
                ", workorderCode='" + workorderCode + '\'' +
                ", recptDate=" + recptDate +
                ", expireDate=" + expireDate +
                ", createDate=" + createDate +
                ", storageCheckFlag=" + storageCheckFlag +
                ", attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", attr3=" + attr3 +
                ", attr4=" + attr4 +
                ", weight=" + weight +
                ", totalWeight=" + totalWeight +
                ", weightUnit='" + weightUnit + '\'' +
                ", waringValue=" + waringValue +
                ", beforeQuantity=" + beforeQuantity +
                ", beforeWeight=" + beforeWeight +
                ", afterQuantity=" + afterQuantity +
                ", afterWeight=" + afterWeight +
                '}';
    }

    public String getCode() {
        return null;
    }
}
