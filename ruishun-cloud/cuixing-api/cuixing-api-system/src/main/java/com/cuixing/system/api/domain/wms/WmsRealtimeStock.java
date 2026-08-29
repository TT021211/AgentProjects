package com.cuixing.system.api.domain.wms;

import com.cuixing.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class WmsRealtimeStock extends BaseEntity {

    @ApiModelProperty("id")
    private Long materialStockId;

    /**
     * 物料类型ID
     */
    @ApiModelProperty("物料类型ID")
    private Long itemTypeId;

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    @ApiModelProperty("物料类型名称")
    private String itemTypeName;

    /**
     * 产品物料ID
     */
    @ApiModelProperty("产品物料ID")
    private Long itemId;

    /**
     * 产品物料编码
     */
    @ApiModelProperty("产品物料编码")
    private String itemCode;

    /**
     * 产品物料名称
     */
    @ApiModelProperty("产品物料名称")
    private String itemName;

    /**
     * 物料识别码
     */
    @ApiModelProperty("物料识别码")
    private String itemIdentifier;

    /**
     * 规格型号
     */
    @ApiModelProperty("规格型号")
    private String specification;

    /**
     * 单位
     */
    @ApiModelProperty("单位")
    private String unitOfMeasure;

    /**
     * 入库批次号
     */
    @ApiModelProperty("入库批次号")
    private String batchCode;

    /**
     * 容器码
     */
    @ApiModelProperty("容器码")
    private String containerCode;

    /**
     * 仓库ID
     */
    @ApiModelProperty("仓库ID")
    private Long warehouseId;

    /**
     * 仓库编码
     */
    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    /**
     * 仓库名称
     */
    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /**
     * 库区ID
     */
    @ApiModelProperty("仓库名称")
    private Long locationId;

    /**
     * 库区编码
     */
    @ApiModelProperty("库区编码")
    private String locationCode;

    /**
     * 库区名称
     */
    @ApiModelProperty("库区编码")
    private String locationName;

    /**
     * 库位ID
     */
    @ApiModelProperty("库位ID")
    private Long areaId;

    /**
     * 库位编码
     */
    @ApiModelProperty("库位编码")
    private String areaCode;

    /**
     * 库位名称
     */
    @ApiModelProperty("库位名称")
    private String areaName;

    /**
     * 供应商ID
     */
    @ApiModelProperty("供应商ID")
    private Long vendorId;

    /**
     * 供应商编号
     */
    @ApiModelProperty("供应商编号")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @ApiModelProperty("供应商名称")
    private String vendorName;

    /**
     * 供应商简称
     */
    @ApiModelProperty("供应商简称")
    private String vendorNick;

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private BigDecimal quantityOnhand;

    /**
     * 生产工单ID
     */
    @ApiModelProperty("生产工单ID")
    private Long workorderId;

    /**
     * 生产工单编号
     */
    @ApiModelProperty("生产工单编号")
    private String workorderCode;

    /**
     * 入库时间
     */
    @ApiModelProperty("入库时间")
    private Date recptDate;

    /**
     * 库存有效期
     */
    @ApiModelProperty("入库时间")
    private Date expireDate;

    /**
     * 预留字段1
     */
    @ApiModelProperty("预留字段1")
    private String attr1;

    /**
     * 预留字段2
     */
    @ApiModelProperty("预留字段2")
    private String attr2;

    /**
     * 预留字段3
     */
    @ApiModelProperty("预留字段3")
    private Integer attr3;

    /**
     * 预留字段4
     */
    @ApiModelProperty("预留字段4")
    private Integer attr4;

    /**
     * 产品分类
     */
    @ApiModelProperty("产品分类")
    private String productTypeName;

    /**
     * 状态分类
     */
    @ApiModelProperty("状态分类")
    private Integer productInoutStatus;
    /**
     * 预留字段4
     */
    @ApiModelProperty("预留字段4")
    private Integer artt4;
    /**
     * 预留字段5
     */
    @ApiModelProperty("预留字段5")
    private String artt5;
    /**
     * 预留字段6
     */
    @ApiModelProperty("预留字段6")
    private String artt6;
    /**
     * 预留字段7
     */
    @ApiModelProperty("预留字段7")
    private String artt7;
    /**
     * 预留字段8
     */
    @ApiModelProperty("预留字段8")
    private String artt8;
    /**
     * 预留字段9
     */
    @ApiModelProperty("预留字段9")
    private String artt9;
    /**
     * 预留字段10
     */
    @ApiModelProperty("预留字段10")
    private String artt10;


    /**
     * 库存物资编码
     */
    @ApiModelProperty("库存物资编码")
    private String stockCode;

    /**
     * 库存重量
     */
    @ApiModelProperty("库存重量")
    private BigDecimal weight;

    /**
     * 毛重
     */
    @ApiModelProperty("毛重")
    private BigDecimal totalWeight;

    /**
     * 重量单位
     */
    @ApiModelProperty("重量单位")
    private String weightUnit;

    /**
     * 预警值
     */
    @ApiModelProperty("预警值")
    private Double waringValue;

    /**
     * 料号
     */
    @ApiModelProperty("料号")
    private String partNumber;

    private Integer version;

    private String status;

    private Integer scanCount;

    private BigDecimal tareWeight;

    private String ingredientsName;

    private String manufacturerName;

    private String materialCode;

    private String productBatchCode;

    public String getProductBatchCode() {
        return productBatchCode;
    }

    public void setProductBatchCode(String productBatchCode) {
        this.productBatchCode = productBatchCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getStatus() {
        return status;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public Integer getProductInoutStatus() {
        return productInoutStatus;
    }

    public void setProductInoutStatus(Integer productInoutStatus) {
        this.productInoutStatus = productInoutStatus;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getMaterialStockId() {
        return materialStockId;
    }

    public void setMaterialStockId(Long materialStockId) {
        this.materialStockId = materialStockId;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemIdentifier() {
        return itemIdentifier;
    }

    public void setItemIdentifier(String itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorNick() {
        return vendorNick;
    }

    public void setVendorNick(String vendorNick) {
        this.vendorNick = vendorNick;
    }

    public BigDecimal getQuantityOnhand() {
        return quantityOnhand;
    }

    public void setQuantityOnhand(BigDecimal quantityOnhand) {
        this.quantityOnhand = quantityOnhand;
    }

    public Long getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(Long workorderId) {
        this.workorderId = workorderId;
    }

    public String getWorkorderCode() {
        return workorderCode;
    }

    public void setWorkorderCode(String workorderCode) {
        this.workorderCode = workorderCode;
    }

    public Date getRecptDate() {
        return recptDate;
    }

    public void setRecptDate(Date recptDate) {
        this.recptDate = recptDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public Integer getAttr3() {
        return attr3;
    }

    public void setAttr3(Integer attr3) {
        this.attr3 = attr3;
    }

    public Integer getAttr4() {
        return attr4;
    }

    public void setAttr4(Integer attr4) {
        this.attr4 = attr4;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Double getWaringValue() {
        return waringValue;
    }

    public void setWaringValue(Double waringValue) {
        this.waringValue = waringValue;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Integer getScanCount() {
        return scanCount;
    }

    public void setScanCount(Integer scanCount) {
        this.scanCount = scanCount;
    }

    public BigDecimal getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(BigDecimal tareWeight) {
        this.tareWeight = tareWeight;
    }

    public String getIngredientsName() {
        return ingredientsName;
    }

    public void setIngredientsName(String ingredientsName) {
        this.ingredientsName = ingredientsName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}
