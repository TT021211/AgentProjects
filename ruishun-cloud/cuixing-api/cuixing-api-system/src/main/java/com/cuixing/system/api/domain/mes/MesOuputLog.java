package com.cuixing.system.api.domain.mes;

import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 报工记录表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-5-30
 */
@ApiModel(description = "报工记录表")
public class MesOuputLog extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty("自增主键")
    @Excel(name = "自增主键", cellType = Excel.ColumnType.NUMERIC, prompt = "自增主键")
    private BigInteger id;
    /** 物料id */
    @ApiModelProperty("物料id")
    @Excel(name = "物料id")
    private BigInteger outputId;
    /** 物料名称 */
    @ApiModelProperty("物料名称")
    @Excel(name = "物料名称")
    private String outputName;
    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @Excel(name = "物料编码")
    private String outputCode;
    /** 报工类型（产品product物料item） */
    @ApiModelProperty("报工类型（产品product物料item）")
    @Excel(name = "报工类型（产品product物料item）")
    private String outputType;
    /** 产出物料的唯一标识 */
    @ApiModelProperty("产出物料的唯一标识")
    @Excel(name = "产出物料的唯一标识")
    private String outputSn;
    /** 工序id */
    @ApiModelProperty("工序id")
    @Excel(name = "工序id")
    private BigInteger workstageId;
    /** 工序名称 */
    @ApiModelProperty("工序名称")
    @Excel(name = "工序名称")
    private String workstageName;
    /** 工序编码 */
    @ApiModelProperty("工序编码")
    @Excel(name = "工序编码")
    private String workstageCode;
    /** 机台id */
    @ApiModelProperty("机台id")
    @Excel(name = "机台id")
    private BigInteger workstationId;
    /** 机台名称 */
    @ApiModelProperty("机台名称")
    @Excel(name = "机台名称")
    private String workstationName;
    /** 机台编码 */
    @ApiModelProperty("机台编码")
    @Excel(name = "机台编码")
    private String workstationCode;
    /** 来源订单id */
    @ApiModelProperty("来源订单id")
    @Excel(name = "来源订单id")
    private BigInteger orderId;
    /** 来源订单编码 */
    @ApiModelProperty("来源订单编码")
    @Excel(name = "来源订单编码")
    private String orderSn;
    /** 来源订单明细id */
    @ApiModelProperty("来源订单明细id")
    @Excel(name = "来源订单明细id")
    private BigInteger orderDetailId;
    /** 客户id */
    @ApiModelProperty("客户id")
    @Excel(name = "客户id")
    private BigInteger clientId;
    /** 客户名称 */
    @ApiModelProperty("客户名称")
    @Excel(name = "客户名称")
    private String clientName;
    /** 生产工单id */
    @ApiModelProperty("生产工单id")
    @Excel(name = "生产工单id")
    private BigInteger workorderId;
    /** 生产工单号 */
    @ApiModelProperty("生产工单号")
    @Excel(name = "生产工单号")
    private String workorderSn;
    /** 生产工单明细id */
    @ApiModelProperty("生产工单明细id")
    @Excel(name = "生产工单明细id")
    private BigInteger workorderEntryId;
    /** 工序任务id */
    @ApiModelProperty("工序任务id")
    @Excel(name = "工序任务id")
    private BigInteger taskId;
    /** 工序任务单号 */
    @ApiModelProperty("工序任务单号")
    @Excel(name = "工序任务单号")
    private String taskSn;
    /** 产品id */
    @ApiModelProperty("产品id")
    @Excel(name = "产品id")
    private BigInteger productId;
    /** 产品名称 */
    @ApiModelProperty("产品名称")
    @Excel(name = "产品名称")
    private String productName;
    /** 产品编码 */
    @ApiModelProperty("产品编码")
    @Excel(name = "产品编码")
    private String productCode;
    /** 产品规格 */
    @ApiModelProperty("产品规格")
    @Excel(name = "产品规格")
    private String productSpecification;
    /** 报工数量 */
    @ApiModelProperty("报工数量")
    @Excel(name = "报工数量")
    private BigDecimal outputNum;
    /** 报工重量 */
    @ApiModelProperty("报工重量")
    @Excel(name = "报工重量")
    private BigDecimal outputWeight;
    /** 班组id */
    @ApiModelProperty("班组id")
    @Excel(name = "班组id")
    private BigInteger workgroupId;
    /** 班组名称 */
    @ApiModelProperty("班组名称")
    @Excel(name = "班组名称")
    private String workgroupName;
    /** 班组编码 */
    @ApiModelProperty("班组编码")
    @Excel(name = "班组编码")
    private String workgroupCode;
    /** 备注 */
    @ApiModelProperty("备注")
    @Excel(name = "备注")
    private String remark;
    /** 乐观锁 */
    @ApiModelProperty("乐观锁")
    @Excel(name = "乐观锁")
    private Integer version;
    /** 逻辑删除 */
    @ApiModelProperty("逻辑删除")
    @Excel(name = "逻辑删除")
    private Integer isDeleted;
    /** 创建人 */
    @ApiModelProperty("创建人")
    @Excel(name = "创建人")
    private String createBy;
    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 更新人 */
    @ApiModelProperty("更新人")
    @Excel(name = "更新人")
    private String updateBy;
    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    /** 预留字段1 */
    @ApiModelProperty("预留字段1")
    @Excel(name = "预留字段1")
    private String attr1;
    /** 预留字段2 */
    @ApiModelProperty("预留字段2")
    @Excel(name = "预留字段2")
    private Integer attr2;
    /** 预留字段3 */
    @ApiModelProperty("预留字段3")
    @Excel(name = "预留字段3")
    private String attr3;

    /** 工序类型(0：拉丝 1：漆包) */
    @ApiModelProperty("工序类型(0：拉丝 1：漆包)")
    @Excel(name = "工序类型(0：拉丝 1：漆包)")
    private String taskType;

    /** 批号 */
    @ApiModelProperty("批号")
    @Excel(name = "批号")
    private String batchCode;
    /** 料号 */
    @ApiModelProperty("料号")
    @Excel(name = "料号")
    private String partNumber;
    /** 单位 */
    @ApiModelProperty("单位")
    @Excel(name = "单位")
    private String measureName;
    /** 报工规格 */
    @ApiModelProperty("报工规格")
    @Excel(name = "报工规格")
    private String itemSpecification;
    /** 产品型号 */
    @ApiModelProperty("产品型号")
    @Excel(name = "产品型号")
    private String productModel;
    /** 工号 */
    @ApiModelProperty("工号")
    @Excel(name = "工号")
    private String jobNumber;
    /** 拉丝类型(0：过程线 1：规格线) */
    @ApiModelProperty("拉丝类型(0：过程线 1：规格线)")
    @Excel(name = "拉丝类型(0：过程线 1：规格线)")
    private String inputWireType;
    /** 投料id */
    @ApiModelProperty("投料id")
    @Excel(name = "投料id")
    private BigInteger inputId;
    /** 投料编码 */
    @ApiModelProperty("投料编码")
    @Excel(name = "投料编码")
    private String inputSn;
    /** 订单类型(0：客户订单 1：备库订单) */
    @ApiModelProperty("订单类型(0：客户订单 1：备库订单)")
    @Excel(name = "订单类型(0：客户订单 1：备库订单)")
    private String orderType;

    /** 轴位置数 */
    @ApiModelProperty("轴位置数")
    @Excel(name = "轴位置数")
    private Integer alexPositionNumber;

    /** 批次 */
    @ApiModelProperty("批次")
    @Excel(name = "批次")
    private String alexLot;

    /** 批数 */
    @ApiModelProperty("批数")
    @Excel(name = "批数")
    private Integer alexLotNumber;


    /** 线盘重量 */
    @ApiModelProperty("线盘重量")
    @Excel(name = "线盘重量")
    private BigDecimal reelWight;

    /** 工单关联漆包机台 */
    @ApiModelProperty("工单关联漆包机台")
    @Excel(name = "工单关联漆包机台")
    private String qbWorkstationCode;

    /** 线盘型号 */
    @ApiModelProperty("线盘型号")
    @Excel(name = "线盘型号")
    private String wireDisc;

    /** 漆包机台打印值 */
    @ApiModelProperty("漆包机台打印值")
    @Excel(name = "漆包机台打印值")
    private Integer qbPrintValue;

    /** 班组值 */
    @ApiModelProperty("班组值")
    @Excel(name = "班组值")
    private String workgroupValue;

    /** 标准名称 */
    @ApiModelProperty("标准名称")
    @Excel(name = "标准名称")
    private String standardName;

    /** 标准值*/
    @ApiModelProperty("标准值")
    @Excel(name = "标准值")
    private Integer standardValue;

    /** 国际型号*/
    @ApiModelProperty("国际型号")
    @Excel(name = "国际型号")
    private String productSizeInternational;

    /** 报工毛重*/
    @ApiModelProperty("报工毛重")
    @Excel(name = "报工毛重")
    private BigDecimal outputGrossWeight;

    /** 报工皮重*/
    @ApiModelProperty("报工皮重")
    @Excel(name = "报工皮重")
    private BigDecimal outputTareWeight;

    /** 贸易商*/
    @ApiModelProperty("贸易商")
    @Excel(name = "贸易商")
    private String ingredientsName;

    /** 材料厂家*/
    @ApiModelProperty("材料厂家")
    @Excel(name = "材料厂家")
    private String manufacturerName;

    /** 称重时间*/
    @ApiModelProperty("称重时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "称重时间")
    private Date weightTime;

    /** 称重时间*/
    @ApiModelProperty("轴号")
    @Excel(name = "轴号")
    private String alexNumber;



    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 物料id */
    public BigInteger getOutputId(){
        return this.outputId;
    }
    /** 物料id */
    public void setOutputId(BigInteger outputId){
        this.outputId=outputId;
    }
    /** 物料名称 */
    public String getOutputName(){
        return this.outputName;
    }
    /** 物料名称 */
    public void setOutputName(String outputName){
        this.outputName=outputName;
    }
    /** 物料编码 */
    public String getOutputCode(){
        return this.outputCode;
    }
    /** 物料编码 */
    public void setOutputCode(String outputCode){
        this.outputCode=outputCode;
    }
    /** 报工类型（产品product物料item） */
    public String getOutputType(){
        return this.outputType;
    }
    /** 报工类型（产品product物料item） */
    public void setOutputType(String outputType){
        this.outputType=outputType;
    }
    /** 产出物料的唯一标识 */
    public String getOutputSn(){
        return this.outputSn;
    }
    /** 产出物料的唯一标识 */
    public void setOutputSn(String outputSn){
        this.outputSn=outputSn;
    }
    /** 工序id */
    public BigInteger getWorkstageId(){
        return this.workstageId;
    }
    /** 工序id */
    public void setWorkstageId(BigInteger workstageId){
        this.workstageId=workstageId;
    }
    /** 工序名称 */
    public String getWorkstageName(){
        return this.workstageName;
    }
    /** 工序名称 */
    public void setWorkstageName(String workstageName){
        this.workstageName=workstageName;
    }
    /** 工序编码 */
    public String getWorkstageCode(){
        return this.workstageCode;
    }
    /** 工序编码 */
    public void setWorkstageCode(String workstageCode){
        this.workstageCode=workstageCode;
    }
    /** 机台id */
    public BigInteger getWorkstationId(){
        return this.workstationId;
    }
    /** 机台id */
    public void setWorkstationId(BigInteger workstationId){
        this.workstationId=workstationId;
    }
    /** 机台名称 */
    public String getWorkstationName(){
        return this.workstationName;
    }
    /** 机台名称 */
    public void setWorkstationName(String workstationName){
        this.workstationName=workstationName;
    }
    /** 机台编码 */
    public String getWorkstationCode(){
        return this.workstationCode;
    }
    /** 机台编码 */
    public void setWorkstationCode(String workstationCode){
        this.workstationCode=workstationCode;
    }
    /** 来源订单id */
    public BigInteger getOrderId(){
        return this.orderId;
    }
    /** 来源订单id */
    public void setOrderId(BigInteger orderId){
        this.orderId=orderId;
    }
    /** 来源订单编码 */
    public String getOrderSn(){
        return this.orderSn;
    }
    /** 来源订单编码 */
    public void setOrderSn(String orderSn){
        this.orderSn=orderSn;
    }
    /** 来源订单明细id */
    public BigInteger getOrderDetailId(){
        return this.orderDetailId;
    }
    /** 来源订单明细id */
    public void setOrderDetailId(BigInteger orderDetailId){
        this.orderDetailId=orderDetailId;
    }
    /** 客户id */
    public BigInteger getClientId(){
        return this.clientId;
    }
    /** 客户id */
    public void setClientId(BigInteger clientId){
        this.clientId=clientId;
    }
    /** 客户名称 */
    public String getClientName(){
        return this.clientName;
    }
    /** 客户名称 */
    public void setClientName(String clientName){
        this.clientName=clientName;
    }
    /** 生产工单id */
    public BigInteger getWorkorderId(){
        return this.workorderId;
    }
    /** 生产工单id */
    public void setWorkorderId(BigInteger workorderId){
        this.workorderId=workorderId;
    }
    /** 生产工单号 */
    public String getWorkorderSn(){
        return this.workorderSn;
    }
    /** 生产工单号 */
    public void setWorkorderSn(String workorderSn){
        this.workorderSn=workorderSn;
    }
    /** 生产工单明细id */
    public BigInteger getWorkorderEntryId(){
        return this.workorderEntryId;
    }
    /** 生产工单明细id */
    public void setWorkorderEntryId(BigInteger workorderEntryId){
        this.workorderEntryId=workorderEntryId;
    }
    /** 工序任务id */
    public BigInteger getTaskId(){
        return this.taskId;
    }
    /** 工序任务id */
    public void setTaskId(BigInteger taskId){
        this.taskId=taskId;
    }
    /** 工序任务单号 */
    public String getTaskSn(){
        return this.taskSn;
    }
    /** 工序任务单号 */
    public void setTaskSn(String taskSn){
        this.taskSn=taskSn;
    }
    /** 产品id */
    public BigInteger getProductId(){
        return this.productId;
    }
    /** 产品id */
    public void setProductId(BigInteger productId){
        this.productId=productId;
    }
    /** 产品名称 */
    public String getProductName(){
        return this.productName;
    }
    /** 产品名称 */
    public void setProductName(String productName){
        this.productName=productName;
    }
    /** 产品编码 */
    public String getProductCode(){
        return this.productCode;
    }
    /** 产品编码 */
    public void setProductCode(String productCode){
        this.productCode=productCode;
    }
    /** 产品规格 */
    public String getProductSpecification(){
        return this.productSpecification;
    }
    /** 产品规格 */
    public void setProductSpecification(String productSpecification){
        this.productSpecification=productSpecification;
    }
    /** 报工数量 */
    public BigDecimal getOutputNum(){
        return this.outputNum;
    }
    /** 报工数量 */
    public void setOutputNum(BigDecimal outputNum){
        this.outputNum=outputNum;
    }
    /** 报工重量 */
    public BigDecimal getOutputWeight(){
        return this.outputWeight;
    }
    /** 报工重量 */
    public void setOutputWeight(BigDecimal outputWeight){
        this.outputWeight=outputWeight;
    }
    /** 班组id */
    public BigInteger getWorkgroupId(){
        return this.workgroupId;
    }
    /** 班组id */
    public void setWorkgroupId(BigInteger workgroupId){
        this.workgroupId=workgroupId;
    }
    /** 班组名称 */
    public String getWorkgroupName(){
        return this.workgroupName;
    }
    /** 班组名称 */
    public void setWorkgroupName(String workgroupName){
        this.workgroupName=workgroupName;
    }
    /** 班组编码 */
    public String getWorkgroupCode(){
        return this.workgroupCode;
    }
    /** 班组编码 */
    public void setWorkgroupCode(String workgroupCode){
        this.workgroupCode=workgroupCode;
    }
    /** 备注 */
    public String getRemark(){
        return this.remark;
    }
    /** 备注 */
    public void setRemark(String remark){
        this.remark=remark;
    }
    /** 乐观锁 */
    public Integer getVersion(){
        return this.version;
    }
    /** 乐观锁 */
    public void setVersion(Integer version){
        this.version=version;
    }
    /** 逻辑删除 */
    public Integer getIsDeleted(){
        return this.isDeleted;
    }
    /** 逻辑删除 */
    public void setIsDeleted(Integer isDeleted){
        this.isDeleted=isDeleted;
    }
    /** 创建人 */
    public String getCreateBy(){
        return this.createBy;
    }
    /** 创建人 */
    public void setCreateBy(String createBy){
        this.createBy=createBy;
    }
    /** 创建时间 */
    public Date getCreateTime(){
        return this.createTime;
    }
    /** 创建时间 */
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    /** 更新人 */
    public String getUpdateBy(){
        return this.updateBy;
    }
    /** 更新人 */
    public void setUpdateBy(String updateBy){
        this.updateBy=updateBy;
    }
    /** 更新时间 */
    public Date getUpdateTime(){
        return this.updateTime;
    }
    /** 更新时间 */
    public void setUpdateTime(Date updateTime){
        this.updateTime=updateTime;
    }
    /** 预留字段1 */
    public String getAttr1(){
        return this.attr1;
    }
    /** 预留字段1 */
    public void setAttr1(String attr1){
        this.attr1=attr1;
    }
    /** 预留字段2 */
    public Integer getAttr2(){
        return this.attr2;
    }
    /** 预留字段2 */
    public void setAttr2(Integer attr2){
        this.attr2=attr2;
    }
    /** 预留字段3 */
    public String getAttr3(){
        return this.attr3;
    }
    /** 预留字段3 */
    public void setAttr3(String attr3){
        this.attr3=attr3;
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

    public BigInteger getInputId() {
        return inputId;
    }

    public void setInputId(BigInteger inputId) {
        this.inputId = inputId;
    }

    public String getInputSn() {
        return inputSn;
    }

    public void setInputSn(String inputSn) {
        this.inputSn = inputSn;
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

    public BigDecimal getReelWight() {
        return reelWight;
    }

    public void setReelWight(BigDecimal reelWight) {
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

    public BigDecimal getOutputGrossWeight() {
        return outputGrossWeight;
    }

    public void setOutputGrossWeight(BigDecimal outputGrossWeight) {
        this.outputGrossWeight = outputGrossWeight;
    }

    public BigDecimal getOutputTareWeight() {
        return outputTareWeight;
    }

    public void setOutputTareWeight(BigDecimal outputTareWeight) {
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


}