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
* 客户访问记录;
* @author : http://www.chiner.pro
* @date : 2025-1-7
*/
@ApiModel(value = "客户访问记录",description = "")
public class CrmClientInterview extends BaseEntity{
   /** 自增主键 */
   @ApiModelProperty(name = "自增主键",notes = "")
   private BigInteger id ;
   /** 客户id */
   @ApiModelProperty(name = "客户id",notes = "")
   private BigInteger clientId ;
   /** 客户名称 */
   @ApiModelProperty(name = "客户名称",notes = "")
   private String clientName ;
   /** 走访人 */
   @ApiModelProperty(name = "走访人",notes = "")
   private String interviewName ;
   /** 走访时间 */
   @ApiModelProperty(name = "走访时间",notes = "")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date interviewTime ;
   /** 走访地址 */
   @ApiModelProperty(name = "走访地址",notes = "")
   private String interviewAddress ;
   /** 联系人 */
   @ApiModelProperty(name = "联系人",notes = "")
   private String connectionName ;
   /** 联系电话 */
   @ApiModelProperty(name = "联系电话",notes = "")
   private String connectionPhone ;
   /** 走访状态 */
   @ApiModelProperty(name = "走访状态",notes = "")
   private String interviewStatus ;
   /** 走访描述 */
   @ApiModelProperty(name = "走访描述",notes = "")
   private String description ;
   /** 备注 */
   @ApiModelProperty(name = "备注",notes = "")
   private String remark ;
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
   /** 走访人 */
   public String getInterviewName(){
       return this.interviewName;
   }
   /** 走访人 */
   public void setInterviewName(String interviewName){
       this.interviewName=interviewName;
   }
   /** 走访时间 */
   public Date getInterviewTime(){
       return this.interviewTime;
   }
   /** 走访时间 */
   public void setInterviewTime(Date interviewTime){
       this.interviewTime=interviewTime;
   }
   /** 走访地址 */
   public String getInterviewAddress(){
       return this.interviewAddress;
   }
   /** 走访地址 */
   public void setInterviewAddress(String interviewAddress){
       this.interviewAddress=interviewAddress;
   }
   /** 联系人 */
   public String getConnectionName(){
       return this.connectionName;
   }
   /** 联系人 */
   public void setConnectionName(String connectionName){
       this.connectionName=connectionName;
   }
   /** 联系电话 */
   public String getConnectionPhone(){
       return this.connectionPhone;
   }
   /** 联系电话 */
   public void setConnectionPhone(String connectionPhone){
       this.connectionPhone=connectionPhone;
   }
   /** 走访状态 */
   public String getInterviewStatus(){
       return this.interviewStatus;
   }
   /** 走访状态 */
   public void setInterviewStatus(String interviewStatus){
       this.interviewStatus=interviewStatus;
   }
   /** 走访描述 */
   public String getDescription(){
       return this.description;
   }
   /** 走访描述 */
   public void setDescription(String description){
       this.description=description;
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
   @Override
   public String toString() {
       return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
           .append("id", getId())
           .append("clientId", getClientId())
           .append("clientName", getClientName())
           .append("interviewName", getInterviewName())
           .append("interviewTime", getInterviewTime())
           .append("interviewAddress", getInterviewAddress())
           .append("connectionName", getConnectionName())
           .append("connectionPhone", getConnectionPhone())
           .append("interviewStatus", getInterviewStatus())
           .append("description", getDescription())
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
       .toString();
   }
}