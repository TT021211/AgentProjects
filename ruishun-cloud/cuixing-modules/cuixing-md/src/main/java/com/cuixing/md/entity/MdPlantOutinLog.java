package com.cuixing.md.entity;

import com.cuixing.common.core.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 油漆出入库记录;
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@ApiModel(value = "油漆出入库记录",description = "")
public class MdPlantOutinLog extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    @Excel(name = "油漆出入库记录id", cellType = Excel.ColumnType.NUMERIC, prompt = "油漆出入库记录id")
    private BigInteger id ;
    /** 条码 */
    @ApiModelProperty(name = "条码",notes = "")
    @Excel(name = "条码")
    private String plantCode ;
     /** 油漆id **/
     @ApiModelProperty(name = "油漆id",notes = "")
     @Excel(name = "油漆id")
     private BigInteger plantId ;
    /** 油漆名称 */
    @ApiModelProperty(name = "油漆名称",notes = "")
    @Excel(name = "油漆名称")
    private String plantName ;
    /** 油漆型号 */
    @ApiModelProperty(name = "油漆型号",notes = "")
    @Excel(name = "油漆型号")
    private String plantType ;
    /** 重量 */
    @ApiModelProperty(name = "重量",notes = "")
    @Excel(name = "重量")
    private Double weight ;
    /** 单位id */
    @ApiModelProperty(name = "单位id",notes = "")
    private BigInteger measureId ;
    /** 单位名称 */
    @ApiModelProperty(name = "单位名称",notes = "")
    @Excel(name = "单位")
    private String measureName ;
    /** 客户id */
    @ApiModelProperty(name = "客户id",notes = "")
    private BigInteger customerId ;
    /** 客户批号 */
    @ApiModelProperty(name = "客户批号",notes = "")
    @Excel(name = "客户批号")
    private String customerBatch ;
    /** 机台id */
    @ApiModelProperty(name = "机台id",notes = "")
    private BigInteger workbenchId ;
    /** 机台号 */
    @ApiModelProperty(name = "机台号",notes = "")
    @Excel(name = "机台号")
    private String workbenchCode ;
    /** 状态 */
    @ApiModelProperty(name = "状态",notes = "")
    @Excel(name = "状态")
    private String status ;
    /** 工号 */
    @ApiModelProperty(name = "工号",notes = "")
    @Excel(name = "工号")
    private String jobNumber ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除",notes = "")
    private Integer isDeleted ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁",notes = "")
    private Integer version ;
    /** 创建人 */
    @ApiModelProperty(name = "创建人",notes = "")
    @Excel(name = "创建人")
    private String createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;
    /** 更新人 */
    @ApiModelProperty(name = "更新人",notes = "")
    @Excel(name = "更新人")
    private String updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1",notes = "")
    private String attr1 ;
    /** 预留字段2 */
    @ApiModelProperty(name = "预留字段2",notes = "")
    private Integer attr2 ;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3",notes = "")
    private String attr3 ;
     /** 供应商id */
    @ApiModelProperty(name = "supplierId",notes = "")
    private BigInteger supplierId ;
     /** 供应商名称 */
     @ApiModelProperty(name = "supplierName",notes = "")
     private String supplierName ;
     /** 批号 */
     @ApiModelProperty(name = "lotNumber",notes = "")
     private String lotNumber ;
     /** 油漆识别码 */
     @ApiModelProperty(name = "identificationCode",notes = "")
     private String identificationCode ;
    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 条码 */
    public String getPlantCode(){
        return this.plantCode;
    }
    /** 条码 */
    public void setPlantCode(String plantCode){
        this.plantCode=plantCode;
    }
    /** 油漆名称 */
    public String getPlantName(){
        return this.plantName;
    }
    /** 油漆名称 */
    public void setPlantName(String plantName){
        this.plantName=plantName;
    }
    /** 油漆型号 */
    public String getPlantType(){
        return this.plantType;
    }
    /** 油漆型号 */
    public void setPlantType(String plantType){
        this.plantType=plantType;
    }
    /** 重量 */
    public Double getWeight(){
        return this.weight;
    }
    /** 重量 */
    public void setWeight(Double weight){
        this.weight=weight;
    }
    /** 单位id */
    public BigInteger getMeasureId(){
        return this.measureId;
    }
    /** 单位id */
    public void setMeasureId(BigInteger measureId){
        this.measureId=measureId;
    }
    /** 单位名称 */
    public String getMeasureName(){
        return this.measureName;
    }
    /** 单位名称 */
    public void setMeasureName(String measureName){
        this.measureName=measureName;
    }
    /** 客户id */
    public BigInteger getCustomerId(){
        return this.customerId;
    }
    /** 客户id */
    public void setCustomerId(BigInteger customerId){
        this.customerId=customerId;
    }
    /** 客户批号 */
    public String getCustomerBatch(){
        return this.customerBatch;
    }
    /** 客户批号 */
    public void setCustomerBatch(String customerBatch){
        this.customerBatch=customerBatch;
    }
    /** 机台id */
    public BigInteger getWorkbenchId(){
        return this.workbenchId;
    }
    /** 机台id */
    public void setWorkbenchId(BigInteger workbenchId){
        this.workbenchId=workbenchId;
    }
    /** 机台号 */
    public String getWorkbenchCode(){
        return this.workbenchCode;
    }
    /** 机台号 */
    public void setWorkbenchCode(String workbenchCode){
        this.workbenchCode=workbenchCode;
    }
    /** 状态 */
    public String getStatus(){
        return this.status;
    }
    /** 状态 */
    public void setStatus(String status){
        this.status=status;
    }
    /** 工号 */
    public String getJobNumber(){
        return this.jobNumber;
    }
    /** 工号 */
    public void setJobNumber(String jobNumber){
        this.jobNumber=jobNumber;
    }
    /** 逻辑删除 */
    public Integer getIsDeleted(){
        return this.isDeleted;
    }
    /** 逻辑删除 */
    public void setIsDeleted(Integer isDeleted){
        this.isDeleted=isDeleted;
    }
    /** 乐观锁 */
    public Integer getVersion(){
        return this.version;
    }
    /** 乐观锁 */
    public void setVersion(Integer version){
        this.version=version;
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

     public BigInteger getSupplierId() {
         return supplierId;
     }

     public void setSupplierId(BigInteger supplierId) {
         this.supplierId = supplierId;
     }

     public String getSupplierName() {
         return supplierName;
     }

     public void setSupplierName(String supplierName) {
         this.supplierName = supplierName;
     }

     public String getLotNumber() {
         return lotNumber;
     }

     public void setLotNumber(String lotNumber) {
         this.lotNumber = lotNumber;
     }

     public String getIdentificationCode() {
         return identificationCode;
     }

     public void setIdentificationCode(String identificationCode) {
         this.identificationCode = identificationCode;
     }

     public BigInteger getPlantId() {
         return plantId;
     }

     public void setPlantId(BigInteger plantId) {
         this.plantId = plantId;
     }

     @Override
     public String toString() {
         return "MdPlantOutinLog{" +
                 "id=" + id +
                 ", plantCode='" + plantCode + '\'' +
                 ", plantId=" + plantId +
                 ", plantName='" + plantName + '\'' +
                 ", plantType='" + plantType + '\'' +
                 ", weight=" + weight +
                 ", measureId=" + measureId +
                 ", measureName='" + measureName + '\'' +
                 ", customerId=" + customerId +
                 ", customerBatch='" + customerBatch + '\'' +
                 ", workbenchId=" + workbenchId +
                 ", workbenchCode='" + workbenchCode + '\'' +
                 ", status='" + status + '\'' +
                 ", jobNumber='" + jobNumber + '\'' +
                 ", isDeleted=" + isDeleted +
                 ", version=" + version +
                 ", createBy='" + createBy + '\'' +
                 ", createTime=" + createTime +
                 ", updateBy='" + updateBy + '\'' +
                 ", updateTime=" + updateTime +
                 ", attr1='" + attr1 + '\'' +
                 ", attr2=" + attr2 +
                 ", attr3='" + attr3 + '\'' +
                 ", supplierId=" + supplierId +
                 ", supplierName='" + supplierName + '\'' +
                 ", lotNumber='" + lotNumber + '\'' +
                 ", identificationCode='" + identificationCode + '\'' +
                 '}';
     }
 }