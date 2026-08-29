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
 * 点检保养计划;
 * @author : zhang
 * @date : 2024-7-8
 */
@ApiModel(value = "点检保养计划")
public class EqPlan extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键")
    private BigInteger id ;
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
    /** 开始时间 */
    @ApiModelProperty(name = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime ;
    /** 结束时间 */
    @ApiModelProperty(name = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime ;
    /** 频率 */
    @ApiModelProperty(name = "频率")
    private Integer frequency ;
    /** 单位id */
    @ApiModelProperty(name = "单位id")
    private BigInteger measureId ;
    /** 单位名称 */
    @ApiModelProperty(name = "单位名称")
    private String measureName ;
    /** 是否启用 */
    @ApiModelProperty(name = "是否启用")
    private Integer ifEnable ;
    /** 备注 */
    @ApiModelProperty(name = "备注")
    private String remark ;
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

    @ApiModelProperty(name = "关联定时任务id")
    private BigInteger jobId;

     @ApiModelProperty(name = "关联定时任务名")
     private String jobName;
    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
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
    /** 开始时间 */
    public Date getStartTime(){
        return this.startTime;
    }
    /** 开始时间 */
    public void setStartTime(Date startTime){
        this.startTime=startTime;
    }
    /** 结束时间 */
    public Date getEndTime(){
        return this.endTime;
    }
    /** 结束时间 */
    public void setEndTime(Date endTime){
        this.endTime=endTime;
    }
    /** 频率 */
    public Integer getFrequency(){
        return this.frequency;
    }
    /** 频率 */
    public void setFrequency(Integer frequency){
        this.frequency=frequency;
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
    /** 是否启用 */
    public Integer getIfEnable(){
        return this.ifEnable;
    }
    /** 是否启用 */
    public void setIfEnable(Integer ifEnable){
        this.ifEnable=ifEnable;
    }
    /** 备注 */
    public String getRemark(){
        return this.remark;
    }
    /** 备注 */
    public void setRemark(String remark){
        this.remark=remark;
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

     public BigInteger getJobId() {
         return jobId;
     }

     public void setJobId(BigInteger jobId) {
         this.jobId = jobId;
     }

     public String getJobName() {
         return jobName;
     }

     public void setJobName(String jobName) {
         this.jobName = jobName;
     }

     @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planType", getPlanType())
            .append("planCode", getPlanCode())
            .append("planName", getPlanName())
            .append("itemTypeId", getItemTypeId())
            .append("itemTypeName", getItemTypeName())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("frequency", getFrequency())
            .append("measureId", getMeasureId())
            .append("measureName", getMeasureName())
            .append("ifEnable", getIfEnable())
            .append("remark", getRemark())
            .append("isDeleted", getIsDeleted())
            .append("version", getVersion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("jobId", getJobId())
            .append("jobName", getJobName())
        .toString();
    }
}