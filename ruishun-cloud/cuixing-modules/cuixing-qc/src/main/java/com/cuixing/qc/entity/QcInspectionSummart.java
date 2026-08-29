package com.cuixing.qc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.io.Serializable;


/**
 * 检验汇总表(QcInspectionSummart)表服务类
 *
 * @author 阿江
 * @since 2024-12-13 10:01:27
 */
public class QcInspectionSummart implements Serializable {
    private static final long serialVersionUID = 530553851502799632L;
    /**
     * 检验表id
     */
    private BigInteger id;
    /**
     * 质检分类id
     */
    private BigInteger classificationId;
    /**
     * 质检分类名称
     */
    private String classificationName;
    /**
     * 产品id
     */
    private BigInteger productId;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品规格
     */
    private String productSpecification;
    /**
     * 物料（产品）识别码
     */
    private String itemIdentifier;
    /**
     * 仓库ID
     */
    private BigInteger warehouseId;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 机台
     */
    private String machineTable;
    /**
     * 生产工单ID
     */
    private BigInteger workorderId;
    /**
     * 生产工单编号
     */
    private String workorderCode;
    /**
     * 批号
     */
    private String batchNumber;
    /**
     * 料号
     */
    private String itemNumber;
    /**
     * 销售订单号
     */
    private BigInteger saleOrderId;
    /**
     * 销售订单编号
     */
    private String saleOrderCode;
    /**
     * 重量
     */
    private BigDecimal weight;
    /**
     * 单位id（单位表）
     */
    private BigInteger measureId;
    /**
     * 单位名称（单位表）
     */
    private String measureName;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 逻辑删除
     */
    private Integer isDeleted;
    /**
     * 乐观锁
     */
    private Integer version;
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
     * 状态(0：合格 1：不合格)
     */
    private String status;
    /**
     * 预留字段1
     */
    private String attr1;
    /**
     * 预留字段2
     */
    private Integer attr2;
    /**
     * 预留字段3
     */
    private String attr3;
    /**
     * 备用字段4
     */
    private Double attr4;
    /**
     * 备用字段5
     */
    private String attr5;
    /**
     * 备用字段6
     */
    private String attr6;
    /**
     * 备用字段7
     */
    private String attr7;
    /**
     * 备用字段8
     */
    private String attr8;
    /**
     * 备用字段9
     */
    private String attr9;
    /**
     * 检测项id
     */
    private String inspectionItemId;
    /**
     * 检测项名称（可以多条）
     */
    private String inspectionItemName;
    /**
     * 缺陷项分类id
     */
    private String defectClassificationId;
    /**
     * 缺陷项名称(可以多条)
     */
    private String defectName;
    /**
     * 工艺类型
     */
    private String taskType;
    /**
     * 检验类型（0 首检、1 尾检 、2 过程检)
     */
    private String inspectionType;
    /**
     * 线径数据
     */
    private String wireDiameterData;
    /**
     * 轴号
     */
    private String axisNumber;
    /**
     * 机台位置
     */
    private String machinePosition;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(BigInteger classificationId) {
        this.classificationId = classificationId;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
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

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getItemIdentifier() {
        return itemIdentifier;
    }

    public void setItemIdentifier(String itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
    }

    public BigInteger getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(BigInteger warehouseId) {
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

    public String getMachineTable() {
        return machineTable;
    }

    public void setMachineTable(String machineTable) {
        this.machineTable = machineTable;
    }

    public BigInteger getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(BigInteger workorderId) {
        this.workorderId = workorderId;
    }

    public String getWorkorderCode() {
        return workorderCode;
    }

    public void setWorkorderCode(String workorderCode) {
        this.workorderCode = workorderCode;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public BigInteger getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(BigInteger saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public String getSaleOrderCode() {
        return saleOrderCode;
    }

    public void setSaleOrderCode(String saleOrderCode) {
        this.saleOrderCode = saleOrderCode;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigInteger getMeasureId() {
        return measureId;
    }

    public void setMeasureId(BigInteger measureId) {
        this.measureId = measureId;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public Integer getAttr2() {
        return attr2;
    }

    public void setAttr2(Integer attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public Double getAttr4() {
        return attr4;
    }

    public void setAttr4(Double attr4) {
        this.attr4 = attr4;
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    public String getAttr6() {
        return attr6;
    }

    public void setAttr6(String attr6) {
        this.attr6 = attr6;
    }

    public String getAttr7() {
        return attr7;
    }

    public void setAttr7(String attr7) {
        this.attr7 = attr7;
    }

    public String getAttr8() {
        return attr8;
    }

    public void setAttr8(String attr8) {
        this.attr8 = attr8;
    }

    public String getAttr9() {
        return attr9;
    }

    public void setAttr9(String attr9) {
        this.attr9 = attr9;
    }

    public String getInspectionItemId() {
        return inspectionItemId;
    }

    public void setInspectionItemId(String inspectionItemId) {
        this.inspectionItemId = inspectionItemId;
    }

    public String getInspectionItemName() {
        return inspectionItemName;
    }

    public void setInspectionItemName(String inspectionItemName) {
        this.inspectionItemName = inspectionItemName;
    }

    public String getDefectClassificationId() {
        return defectClassificationId;
    }

    public void setDefectClassificationId(String defectClassificationId) {
        this.defectClassificationId = defectClassificationId;
    }

    public String getDefectName() {
        return defectName;
    }

    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getWireDiameterData() {
        return wireDiameterData;
    }

    public void setWireDiameterData(String wireDiameterData) {
        this.wireDiameterData = wireDiameterData;
    }

    public String getAxisNumber() {
        return axisNumber;
    }

    public void setAxisNumber(String axisNumber) {
        this.axisNumber = axisNumber;
    }

    public String getMachinePosition() {
        return machinePosition;
    }

    public void setMachinePosition(String machinePosition) {
        this.machinePosition = machinePosition;
    }

}

