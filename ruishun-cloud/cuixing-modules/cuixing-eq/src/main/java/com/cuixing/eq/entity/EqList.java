package com.cuixing.eq.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 待点检(保养)列表;
 * @author : http://www.chiner.pro
 * @date : 2024-7-9
 */
@ApiModel(value = "待点检(保养)列表")
public class EqList extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键")
    private BigInteger id ;
    /** 设备id */
    @ApiModelProperty(name = "设备id")
    private BigInteger equipmentId ;
    /** 设备编码 */
    @ApiModelProperty(name = "设备编码")
    private String equipmentCode ;
    /** 设备名称 */
    @ApiModelProperty(name = "设备名称")
    private String equipmentName ;
    /** 设备型号 */
    @ApiModelProperty(name = "设备型号")
    private String equipmentModel ;
    /** 计划id */
    @ApiModelProperty(name = "计划id")
    private BigInteger planId ;
    /** 计划类型(0:点检 1：保养) */
    @ApiModelProperty(name = "计划类型(0:点检 1：保养)")
    private String planType ;
    /** 计划编码 */
    @ApiModelProperty(name = "计划编码")
    private String planCode ;
    /** 计划名称 */
    @ApiModelProperty(name = "计划名称")
    private String planName ;
    /** 项目类型id */
    @ApiModelProperty(name = "项目类型id")
    private BigInteger itemTypeId ;
    /** 项目类型名称 */
    @ApiModelProperty(name = "项目类型名称")
    private String itemTypeName ;
    /** 班组id */
    @ApiModelProperty(name = "班组id")
    private BigInteger workgroupId ;
    /** 班组名称 */
    @ApiModelProperty(name = "班组名称")
    private String workgroupName ;
    /** 操作人 */
    @ApiModelProperty(name = "操作人")
    private BigInteger headId ;
    /** 操作人名称 */
    @ApiModelProperty(name = "操作人名称")
    private String headName ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除")
    private Integer isDeleted ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁")
    private Integer version ;
    /** 创建人 */
    @ApiModelProperty(name = "创建人")
    private String createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;
    /** 更新人 */
    @ApiModelProperty(name = "更新人")
    private String updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1")
    private String attr1 ;
    /** 预留字段2 */
    @ApiModelProperty(name = "预留字段2")
    private Integer attr2 ;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3")
    private String attr3 ;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 设备id */
    public BigInteger getEquipmentId(){
        return this.equipmentId;
    }
    /** 设备id */
    public void setEquipmentId(BigInteger equipmentId){
        this.equipmentId=equipmentId;
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
    /** 设备型号 */
    public String getEquipmentModel(){
        return this.equipmentModel;
    }
    /** 设备型号 */
    public void setEquipmentModel(String equipmentModel){
        this.equipmentModel=equipmentModel;
    }
    /** 计划id */
    public BigInteger getPlanId(){
        return this.planId;
    }
    /** 计划id */
    public void setPlanId(BigInteger planId){
        this.planId=planId;
    }
    /** 计划类型(0:点检 1：保养) */
    public String getPlanType(){
        return this.planType;
    }
    /** 计划类型(0:点检 1：保养) */
    public void setPlanType(String planType){
        this.planType=planType;
    }
    /** 计划编码 */
    public String getPlanCode(){
        return this.planCode;
    }
    /** 计划编码 */
    public void setPlanCode(String planCode){
        this.planCode=planCode;
    }
    /** 计划名称 */
    public String getPlanName(){
        return this.planName;
    }
    /** 计划名称 */
    public void setPlanName(String planName){
        this.planName=planName;
    }
    /** 项目类型id */
    public BigInteger getItemTypeId(){
        return this.itemTypeId;
    }
    /** 项目类型id */
    public void setItemTypeId(BigInteger itemTypeId){
        this.itemTypeId=itemTypeId;
    }
    /** 项目类型名称 */
    public String getItemTypeName(){
        return this.itemTypeName;
    }
    /** 项目类型名称 */
    public void setItemTypeName(String itemTypeName){
        this.itemTypeName=itemTypeName;
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
    /** 操作人 */
    public BigInteger getHeadId(){
        return this.headId;
    }
    /** 操作人 */
    public void setHeadId(BigInteger headId){
        this.headId=headId;
    }
    /** 操作人名称 */
    public String getHeadName(){
        return this.headName;
    }
    /** 操作人名称 */
    public void setHeadName(String headName){
        this.headName=headName;
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
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("equipmentId", getEquipmentId())
            .append("equipmentCode", getEquipmentCode())
            .append("equipmentName", getEquipmentName())
            .append("equipmentModel", getEquipmentModel())
            .append("planId", getPlanId())
            .append("planType", getPlanType())
            .append("planCode", getPlanCode())
            .append("planName", getPlanName())
            .append("itemTypeId", getItemTypeId())
            .append("itemTypeName", getItemTypeName())
            .append("workgroupId", getWorkgroupId())
            .append("workgroupName", getWorkgroupName())
            .append("headId", getHeadId())
            .append("headName", getHeadName())
            .append("isDeleted", getIsDeleted())
            .append("version", getVersion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
        .toString();
    }
}