package com.cuixing.mes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;


/**
 * 生产交班表(MesShiftSchedule)表服务类
 *
 * @author 阿江
 * @since 2025-03-24 11:18:54
 */
public class MesShiftSchedule implements Serializable {
    private static final long serialVersionUID = 635497571444062260L;
    /**
     * 交班表id
     */
    private Long id;
    /**
     * 物料id
     */
    private Object outputId;
    /**
     * 物料名称
     */
    private String outputName;
    /**
     * 物料编码
     */
    private String outputCode;
    /**
     * 报工类型（产品product物料item）
     */
    private String outputType;
    /**
     * 产出物料的唯一标识
     */
    private String outputSn;
    /**
     * 工序id
     */
    private Object workstageId;
    /**
     * 工序编码
     */
    private String workstageCode;
    /**
     * 工序名称
     */
    private String workstageName;
    /**
     * 机台id
     */
    private Object workstationId;
    /**
     * 机台名称
     */
    private String workstationName;
    /**
     * 机台编码
     */
    private String workstationCode;
    /**
     * 来源订单id
     */
    private Object orderId;
    /**
     * 来源订单编码
     */
    private String orderSn;
    /**
     * 投料id
     */
    private Long inputId;
    /**
     * 投料编码
     */
    private String inputSn;
    /**
     * 来源订单明细id
     */
    private Object orderDetailId;
    /**
     * 客户id
     */
    private Object clientId;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 生产工单id
     */
    private Object workorderId;
    /**
     * 生产工单号
     */
    private String workorderSn;
    /**
     * 生产工单明细id
     */
    private Object workorderEntryId;
    /**
     * 工序任务id
     */
    private Object taskId;
    /**
     * 工序任务单号
     */
    private String taskSn;
    /**
     * 产品id
     */
    private Object productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品规格
     */
    private String productSpecification;
    /**
     * 报工数量
     */
    private Object outputNum;
    /**
     * 报工重量
     */
    private Double outputWeight;
    /**
     * 班组id
     */
    private Object workgroupId;
    /**
     * 班组名称
     */
    private String workgroupName;
    /**
     * 班组编码
     */
    private String workgroupCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 乐观锁
     */
    private Integer version;
    /**
     * 逻辑删除
     */
    private Object isDeleted;
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
    private Object attr3;
    /**
     * 工序类型(0:拉丝 1：漆包)
     */
    private String taskType;
    /**
     * 入库批号
     */
    private String batchCode;
    /**
     * 厂家料号
     */
    private String partNumber;
    /**
     * 单位
     */
    private String measureName;
    /**
     * 物料规格
     */
    private String itemSpecification;
    /**
     * 产品型号
     */
    private String productModel;
    /**
     * 工号
     */
    private String jobNumber;
    /**
     * 拉丝类型(0：过程线 1：规格线)
     */
    private String inputWireType;
    /**
     * 订单类型(0：客户订单 1：备库订单)
     */
    private String orderType;
    /**
     * 轴位置数
     */
    private Integer alexPositionNumber;
    /**
     * 批次
     */
    private String alexLot;
    /**
     * 批数
     */
    private Integer alexLotNumber;
    /**
     * 线盘重量
     */
    private Double reelWight;
    /**
     * 拉丝关联漆包机台
     */
    private String qbWorkstationCode;
    /**
     * 线盘类型
     */
    private String wireDisc;
    /**
     * 漆包机打印值
     */
    private Integer qbPrintValue;
    /**
     * 班组值
     */
    private String workgroupValue;
    /**
     * 标准名称
     */
    private String standardName;
    /**
     * 标准值
     */
    private Integer standardValue;
    /**
     * 国际型号
     */
    private String productSizeInternational;
    /**
     * 报工毛重
     */
    private Double outputGrossWeight;
    /**
     * 报工皮重
     */
    private Double outputTareWeight;
    /**
     * 贸易商
     */
    private String ingredientsName;
    /**
     * 材料厂家
     */
    private String manufacturerName;
    /**
     * 称重时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date weightTime;
    /**
     * 轴号
     */
    private String alexNumber;
    /**
     * 半成品出入库状态(0:入库 1：出库)
     */
    private Integer wmsStatus;
    /**
     * 线盘id
     */
    private Long wireDiscId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getOutputId() {
        return outputId;
    }

    public void setOutputId(Object outputId) {
        this.outputId = outputId;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public String getOutputCode() {
        return outputCode;
    }

    public void setOutputCode(String outputCode) {
        this.outputCode = outputCode;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getOutputSn() {
        return outputSn;
    }

    public void setOutputSn(String outputSn) {
        this.outputSn = outputSn;
    }

    public Object getWorkstageId() {
        return workstageId;
    }

    public void setWorkstageId(Object workstageId) {
        this.workstageId = workstageId;
    }

    public String getWorkstageCode() {
        return workstageCode;
    }

    public void setWorkstageCode(String workstageCode) {
        this.workstageCode = workstageCode;
    }

    public String getWorkstageName() {
        return workstageName;
    }

    public void setWorkstageName(String workstageName) {
        this.workstageName = workstageName;
    }

    public Object getWorkstationId() {
        return workstationId;
    }

    public void setWorkstationId(Object workstationId) {
        this.workstationId = workstationId;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String workstationName) {
        this.workstationName = workstationName;
    }

    public String getWorkstationCode() {
        return workstationCode;
    }

    public void setWorkstationCode(String workstationCode) {
        this.workstationCode = workstationCode;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getInputId() {
        return inputId;
    }

    public void setInputId(Long inputId) {
        this.inputId = inputId;
    }

    public String getInputSn() {
        return inputSn;
    }

    public void setInputSn(String inputSn) {
        this.inputSn = inputSn;
    }

    public Object getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Object orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Object getClientId() {
        return clientId;
    }

    public void setClientId(Object clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Object getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(Object workorderId) {
        this.workorderId = workorderId;
    }

    public String getWorkorderSn() {
        return workorderSn;
    }

    public void setWorkorderSn(String workorderSn) {
        this.workorderSn = workorderSn;
    }

    public Object getWorkorderEntryId() {
        return workorderEntryId;
    }

    public void setWorkorderEntryId(Object workorderEntryId) {
        this.workorderEntryId = workorderEntryId;
    }

    public Object getTaskId() {
        return taskId;
    }

    public void setTaskId(Object taskId) {
        this.taskId = taskId;
    }

    public String getTaskSn() {
        return taskSn;
    }

    public void setTaskSn(String taskSn) {
        this.taskSn = taskSn;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public Object getOutputNum() {
        return outputNum;
    }

    public void setOutputNum(Object outputNum) {
        this.outputNum = outputNum;
    }

    public Double getOutputWeight() {
        return outputWeight;
    }

    public void setOutputWeight(Double outputWeight) {
        this.outputWeight = outputWeight;
    }

    public Object getWorkgroupId() {
        return workgroupId;
    }

    public void setWorkgroupId(Object workgroupId) {
        this.workgroupId = workgroupId;
    }

    public String getWorkgroupName() {
        return workgroupName;
    }

    public void setWorkgroupName(String workgroupName) {
        this.workgroupName = workgroupName;
    }

    public String getWorkgroupCode() {
        return workgroupCode;
    }

    public void setWorkgroupCode(String workgroupCode) {
        this.workgroupCode = workgroupCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Object getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Object isDeleted) {
        this.isDeleted = isDeleted;
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

    public Object getAttr3() {
        return attr3;
    }

    public void setAttr3(Object attr3) {
        this.attr3 = attr3;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public String getItemSpecification() {
        return itemSpecification;
    }

    public void setItemSpecification(String itemSpecification) {
        this.itemSpecification = itemSpecification;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getInputWireType() {
        return inputWireType;
    }

    public void setInputWireType(String inputWireType) {
        this.inputWireType = inputWireType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getAlexPositionNumber() {
        return alexPositionNumber;
    }

    public void setAlexPositionNumber(Integer alexPositionNumber) {
        this.alexPositionNumber = alexPositionNumber;
    }

    public String getAlexLot() {
        return alexLot;
    }

    public void setAlexLot(String alexLot) {
        this.alexLot = alexLot;
    }

    public Integer getAlexLotNumber() {
        return alexLotNumber;
    }

    public void setAlexLotNumber(Integer alexLotNumber) {
        this.alexLotNumber = alexLotNumber;
    }

    public Double getReelWight() {
        return reelWight;
    }

    public void setReelWight(Double reelWight) {
        this.reelWight = reelWight;
    }

    public String getQbWorkstationCode() {
        return qbWorkstationCode;
    }

    public void setQbWorkstationCode(String qbWorkstationCode) {
        this.qbWorkstationCode = qbWorkstationCode;
    }

    public String getWireDisc() {
        return wireDisc;
    }

    public void setWireDisc(String wireDisc) {
        this.wireDisc = wireDisc;
    }

    public Integer getQbPrintValue() {
        return qbPrintValue;
    }

    public void setQbPrintValue(Integer qbPrintValue) {
        this.qbPrintValue = qbPrintValue;
    }

    public String getWorkgroupValue() {
        return workgroupValue;
    }

    public void setWorkgroupValue(String workgroupValue) {
        this.workgroupValue = workgroupValue;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public Integer getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Integer standardValue) {
        this.standardValue = standardValue;
    }

    public String getProductSizeInternational() {
        return productSizeInternational;
    }

    public void setProductSizeInternational(String productSizeInternational) {
        this.productSizeInternational = productSizeInternational;
    }

    public Double getOutputGrossWeight() {
        return outputGrossWeight;
    }

    public void setOutputGrossWeight(Double outputGrossWeight) {
        this.outputGrossWeight = outputGrossWeight;
    }

    public Double getOutputTareWeight() {
        return outputTareWeight;
    }

    public void setOutputTareWeight(Double outputTareWeight) {
        this.outputTareWeight = outputTareWeight;
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

    public Date getWeightTime() {
        return weightTime;
    }

    public void setWeightTime(Date weightTime) {
        this.weightTime = weightTime;
    }

    public String getAlexNumber() {
        return alexNumber;
    }

    public void setAlexNumber(String alexNumber) {
        this.alexNumber = alexNumber;
    }

    public Integer getWmsStatus() {
        return wmsStatus;
    }

    public void setWmsStatus(Integer wmsStatus) {
        this.wmsStatus = wmsStatus;
    }

    public Long getWireDiscId() {
        return wireDiscId;
    }

    public void setWireDiscId(Long wireDiscId) {
        this.wireDiscId = wireDiscId;
    }

}

