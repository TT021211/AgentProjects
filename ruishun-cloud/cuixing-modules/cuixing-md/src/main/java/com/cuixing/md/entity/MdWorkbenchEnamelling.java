package com.cuixing.md.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

/**
 * 机台设置;
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@ApiModel(value = "机台设置",description = "")
public class MdWorkbenchEnamelling extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 机台类型id */
    @ApiModelProperty(name = "机台类型id",notes = "")
    private BigInteger workbenchTypeId ;
    /** 机台类型 */
    @ApiModelProperty(name = "机台类型",notes = "")
    private String workbenchType ;
    /** 设备编码 */
    @ApiModelProperty(name = "设备编码",notes = "")
    private String equipmentCode ;
    /** 设备名称 */
    @ApiModelProperty(name = "设备名称",notes = "")
    private String equipmentName ;
    /** 设备轴号 */
    @ApiModelProperty(name = "设备轴号",notes = "")
    private String axleNumber ;
    /** 生产上限 */
    @ApiModelProperty(name = "生产上限",notes = "")
    private Double productionUpperLimit ;
    /** 生产下限 */
    @ApiModelProperty(name = "生产下限",notes = "")
    private Double productionLowerLimit ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除",notes = "")
    private Integer isDeleted ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁",notes = "")
    private Integer version ;
    /** 创建人 */
    @ApiModelProperty(name = "创建人",notes = "")
    private String createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;
    /** 更新人 */
    @ApiModelProperty(name = "更新人",notes = "")
    private String updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
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
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;

    /** 工序id */
    @ApiModelProperty(name = "工序id",notes = "")
    private BigInteger processId ;

    /** 工序 */
    @ApiModelProperty(name = "工序",notes = "")
    private String process ;

    /** 关联设备id */
    @ApiModelProperty(name = "关联设备id",notes = "")
    private BigInteger equipmentId ;

    /** 是否启用 */
    @ApiModelProperty(name = "是否启用",notes = "")
    private Integer isEnable ;

    /** 机台型号id */
    @ApiModelProperty(name = "机台型号id",notes = "")
    private BigInteger modelId ;

    /** 机台型号名称 */
    @ApiModelProperty(name = "机台型号名称",notes = "")
    private String modelName ;

    /** 设备轴数 */
    @ApiModelProperty(name = "设备轴数",notes = "")
    private String axleQuantity ;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 机台类型id */
    public BigInteger getWorkbenchTypeId(){
        return this.workbenchTypeId;
    }
    /** 机台类型id */
    public void setWorkbenchTypeId(BigInteger workbenchTypeId){
        this.workbenchTypeId=workbenchTypeId;
    }
    /** 机台类型 */
    public String getWorkbenchType(){
        return this.workbenchType;
    }
    /** 机台类型 */
    public void setWorkbenchType(String workbenchType){
        this.workbenchType=workbenchType;
    }
    /** 设备编码 */
    public String getEquipmentCode(){
        return this.equipmentCode;
    }
    /** 设备编码 */
    public void setEquipmentCode(String equipmentCode){
        this.equipmentCode=equipmentCode;
    }
    /** 设备名称 */
    public String getEquipmentName(){
        return this.equipmentName;
    }
    /** 设备名称 */
    public void setEquipmentName(String equipmentName){
        this.equipmentName=equipmentName;
    }
    /** 设备轴号 */
    public String getAxleNumber(){
        return this.axleNumber;
    }
    /** 设备轴号 */
    public void setAxleNumber(String axleNumber){
        this.axleNumber=axleNumber;
    }
    /** 生产上限 */
    public Double getProductionUpperLimit(){
        return this.productionUpperLimit;
    }
    /** 生产上限 */
    public void setProductionUpperLimit(Double productionUpperLimit){
        this.productionUpperLimit=productionUpperLimit;
    }
    /** 生产下限 */
    public Double getProductionLowerLimit(){
        return this.productionLowerLimit;
    }
    /** 生产下限 */
    public void setProductionLowerLimit(Double productionLowerLimit){
        this.productionLowerLimit=productionLowerLimit;
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
    /** 备注 */
    public String getRemark(){
        return this.remark;
    }
    /** 备注 */
    public void setRemark(String remark){
        this.remark=remark;
    }

    public BigInteger getProcessId() {
        return processId;
    }

    public void setProcessId(BigInteger processId) {
        this.processId = processId;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public BigInteger getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(BigInteger equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public BigInteger getModelId() {
        return modelId;
    }

    public void setModelId(BigInteger modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getAxleQuantity() {
        return axleQuantity;
    }

    public void setAxleQuantity(String axleQuantity) {
        this.axleQuantity = axleQuantity;
    }

    @Override
    public String toString() {
        return "MdWorkbenchEnamelling{" +
                "id=" + id +
                ", workbenchTypeId=" + workbenchTypeId +
                ", workbenchType='" + workbenchType + '\'' +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", axleNumber='" + axleNumber + '\'' +
                ", productionUpperLimit=" + productionUpperLimit +
                ", productionLowerLimit=" + productionLowerLimit +
                ", isDeleted=" + isDeleted +
                ", version=" + version +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", attr1='" + attr1 + '\'' +
                ", attr2=" + attr2 +
                ", attr3='" + attr3 + '\'' +
                ", remark='" + remark + '\'' +
                ", processId=" + processId +
                ", process='" + process + '\'' +
                ", equipmentId=" + equipmentId +
                ", isEnable=" + isEnable +
                ", modelId=" + modelId +
                ", modelName='" + modelName + '\'' +
                ", axleQuantity='" + axleQuantity + '\'' +
                '}';
    }
}
