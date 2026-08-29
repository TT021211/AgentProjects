package com.cuixing.crm.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 客户联系人;
 * @author : http://www.chiner.pro
 * @date : 2025-2-8
 */
@ApiModel(value = "客户联系人",description = "")
public class CrmContactPerson extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 客户id */
    @ApiModelProperty(name = "客户id",notes = "")
    private BigInteger clientId ;
    /** 客户名称 */
    @ApiModelProperty(name = "客户名称",notes = "")
    private String clientName ;
    /** 客户编码 */
    @ApiModelProperty(name = "客户编码",notes = "")
    private String clientCode ;
    /** 联系人名称 */
    @ApiModelProperty(name = "联系人名称",notes = "")
    private String personName ;
    /** 联系人电话 */
    @ApiModelProperty(name = "联系人电话",notes = "")
    private String personPhone ;
    /** 部门 */
    @ApiModelProperty(name = "部门",notes = "")
    private String section ;
    /** 职务 */
    @ApiModelProperty(name = "职务",notes = "")
    private String job ;
    /** 微信号 */
    @ApiModelProperty(name = "微信号",notes = "")
    private String wxNumber ;
    /** 邮箱 */
    @ApiModelProperty(name = "邮箱",notes = "")
    private String mailbox ;
    /** 联系人详情 */
    @ApiModelProperty(name = "联系人详情",notes = "")
    private String detail ;
    /** 决策人 */
    @ApiModelProperty(name = "决策人",notes = "")
    private String isDecision ;
    /** 负责人 */
    @ApiModelProperty(name = "负责人",notes = "")
    private String dutyBy ;
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

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
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
    /** 客户编码 */
    public String getClientCode(){
        return this.clientCode;
    }
    /** 客户编码 */
    public void setClientCode(String clientCode){
        this.clientCode=clientCode;
    }
    /** 联系人名称 */
    public String getPersonName(){
        return this.personName;
    }
    /** 联系人名称 */
    public void setPersonName(String personName){
        this.personName=personName;
    }
    /** 联系人电话 */
    public String getPersonPhone(){
        return this.personPhone;
    }
    /** 联系人电话 */
    public void setPersonPhone(String personPhone){
        this.personPhone=personPhone;
    }
    /** 部门 */
    public String getSection(){
        return this.section;
    }
    /** 部门 */
    public void setSection(String section){
        this.section=section;
    }
    /** 职务 */
    public String getJob(){
        return this.job;
    }
    /** 职务 */
    public void setJob(String job){
        this.job=job;
    }
    /** 微信号 */
    public String getWxNumber(){
        return this.wxNumber;
    }
    /** 微信号 */
    public void setWxNumber(String wxNumber){
        this.wxNumber=wxNumber;
    }
    /** 邮箱 */
    public String getMailbox(){
        return this.mailbox;
    }
    /** 邮箱 */
    public void setMailbox(String mailbox){
        this.mailbox=mailbox;
    }
    /** 联系人详情 */
    public String getDetail(){
        return this.detail;
    }
    /** 联系人详情 */
    public void setDetail(String detail){
        this.detail=detail;
    }
    /** 决策人 */
    public String getIsDecision(){
        return this.isDecision;
    }
    /** 决策人 */
    public void setIsDecision(String isDecision){
        this.isDecision=isDecision;
    }
    /** 负责人 */
    public String getDutyBy(){
        return this.dutyBy;
    }
    /** 负责人 */
    public void setDutyBy(String dutyBy){
        this.dutyBy=dutyBy;
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
            .append("clientId", getClientId())
            .append("clientName", getClientName())
            .append("clientCode", getClientCode())
            .append("personName", getPersonName())
            .append("personPhone", getPersonPhone())
            .append("section", getSection())
            .append("job", getJob())
            .append("wxNumber", getWxNumber())
            .append("mailbox", getMailbox())
            .append("detail", getDetail())
            .append("isDecision", getIsDecision())
            .append("dutyBy", getDutyBy())
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