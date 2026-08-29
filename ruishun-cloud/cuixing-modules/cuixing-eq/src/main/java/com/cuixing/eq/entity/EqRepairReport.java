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
 * 设备报修单;
 * @author : http://www.chiner.pro
 * @date : 2024-7-1
 */
@ApiModel(value = "设备报修单")
public class EqRepairReport extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键")
    private BigInteger id ;
    /** 报修设备id */
    @ApiModelProperty(name = "报修设备id")
    private BigInteger equipmentId ;
    /** 报修设备名称 */
    @ApiModelProperty(name = "报修设备名称")
    private String equipmentName ;
    /** 报修设备编码 */
    @ApiModelProperty(name = "报修设备编码")
    private String equipmentCode ;
    /** 故障类型id */
    @ApiModelProperty(name = "故障类型id")
    private BigInteger breakdownId ;
    /** 故障类型名称 */
    @ApiModelProperty(name = "故障类型名称")
    private String breakdownName ;
    /** 故障描述 */
    @ApiModelProperty(name = "故障描述")
    private String breakdownDescription ;
    /** 故障图片 */
    @ApiModelProperty(name = "故障图片")
    private String breakdownImage ;
    /** 报修时间 */
    @ApiModelProperty(name = "报修时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime ;
    /** 报修人 */
    @ApiModelProperty(name = "报修人")
    private String applyBy ;
    /** 维修时间 */
    @ApiModelProperty(name = "维修时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date repairTime ;
    /** 维修人 */
    @ApiModelProperty(name = "维修人")
    private String repairBy ;
    /** 维修方法 */
    @ApiModelProperty(name = "维修方法")
    private String repairMethod ;
    /** 审批人 */
    @ApiModelProperty(name = "审批人")
    private String approveBy ;
    /** 状态 */
    @ApiModelProperty(name = "状态")
    private String status ;
    /** 是否写入知识库 */
    @ApiModelProperty(name = "是否写入知识库")
    private Integer isKnowledge ;
    /** 知识库评论 */
    @ApiModelProperty(name = "知识库评论")
    private String review ;
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
    /** 审批时间 */
    @ApiModelProperty(name = "预留字段3")
    private Date approveTime ;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 报修设备id */
    public BigInteger getEquipmentId(){
        return this.equipmentId;
    }
    /** 报修设备id */
    public void setEquipmentId(BigInteger equipmentId){
        this.equipmentId=equipmentId;
    }
    /** 报修设备名称 */
    public String getEquipmentName(){
        return this.equipmentName;
    }
    /** 报修设备名称 */
    public void setEquipmentName(String equipmentName){
        this.equipmentName=equipmentName;
    }
    /** 报修设备编码 */
    public String getEquipmentCode(){
        return this.equipmentCode;
    }
    /** 报修设备编码 */
    public void setEquipmentCode(String equipmentCode){
        this.equipmentCode=equipmentCode;
    }
    /** 故障类型id */
    public BigInteger getBreakdownId(){
        return this.breakdownId;
    }
    /** 故障类型id */
    public void setBreakdownId(BigInteger breakdownId){
        this.breakdownId=breakdownId;
    }
    /** 故障类型名称 */
    public String getBreakdownName(){
        return this.breakdownName;
    }
    /** 故障类型名称 */
    public void setBreakdownName(String breakdownName){
        this.breakdownName=breakdownName;
    }
    /** 故障描述 */
    public String getBreakdownDescription(){
        return this.breakdownDescription;
    }
    /** 故障描述 */
    public void setBreakdownDescription(String breakdownDescription){
        this.breakdownDescription=breakdownDescription;
    }
    /** 故障图片 */
    public String getBreakdownImage(){
        return this.breakdownImage;
    }
    /** 故障图片 */
    public void setBreakdownImage(String breakdownImage){
        this.breakdownImage=breakdownImage;
    }
    /** 报修时间 */
    public Date getApplyTime(){
        return this.applyTime;
    }
    /** 报修时间 */
    public void setApplyTime(Date applyTime){
        this.applyTime=applyTime;
    }
    /** 报修人 */
    public String getApplyBy(){
        return this.applyBy;
    }
    /** 报修人 */
    public void setApplyBy(String applyBy){
        this.applyBy=applyBy;
    }
    /** 维修时间 */
    public Date getRepairTime(){
        return this.repairTime;
    }
    /** 维修时间 */
    public void setRepairTime(Date repairTime){
        this.repairTime=repairTime;
    }
    /** 维修人 */
    public String getRepairBy(){
        return this.repairBy;
    }
    /** 维修人 */
    public void setRepairBy(String repairBy){
        this.repairBy=repairBy;
    }
    /** 维修方法 */
    public String getRepairMethod(){
        return this.repairMethod;
    }
    /** 维修方法 */
    public void setRepairMethod(String repairMethod){
        this.repairMethod=repairMethod;
    }
    /** 审批人 */
    public String getApproveBy(){
        return this.approveBy;
    }
    /** 审批人 */
    public void setApproveBy(String approveBy){
        this.approveBy=approveBy;
    }
    /** 状态 */
    public String getStatus(){
        return this.status;
    }
    /** 状态 */
    public void setStatus(String status){
        this.status=status;
    }
    /** 是否写入知识库 */
    public Integer getIsKnowledge(){
        return this.isKnowledge;
    }
    /** 是否写入知识库 */
    public void setIsKnowledge(Integer isKnowledge){
        this.isKnowledge=isKnowledge;
    }
    /** 知识库评论 */
    public String getReview(){
        return this.review;
    }
    /** 知识库评论 */
    public void setReview(String review){
        this.review=review;
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
     public Date getApproveTime() {
         return approveTime;
     }

     public void setApproveTime(Date approveTime) {
         this.approveTime = approveTime;
     }

     @Override
     public String toString() {
         return "EqRepairReport{" +
                 "id=" + id +
                 ", equipmentId=" + equipmentId +
                 ", equipmentName='" + equipmentName + '\'' +
                 ", equipmentCode='" + equipmentCode + '\'' +
                 ", breakdownId=" + breakdownId +
                 ", breakdownName='" + breakdownName + '\'' +
                 ", breakdownDescription='" + breakdownDescription + '\'' +
                 ", breakdownImage='" + breakdownImage + '\'' +
                 ", applyTime=" + applyTime +
                 ", applyBy='" + applyBy + '\'' +
                 ", repairTime=" + repairTime +
                 ", repairBy='" + repairBy + '\'' +
                 ", repairMethod='" + repairMethod + '\'' +
                 ", approveBy='" + approveBy + '\'' +
                 ", status='" + status + '\'' +
                 ", isKnowledge=" + isKnowledge +
                 ", review='" + review + '\'' +
                 ", isDeleted=" + isDeleted +
                 ", version=" + version +
                 ", createBy='" + createBy + '\'' +
                 ", createTime=" + createTime +
                 ", updateBy='" + updateBy + '\'' +
                 ", updateTime=" + updateTime +
                 ", attr1='" + attr1 + '\'' +
                 ", attr2=" + attr2 +
                 ", attr3='" + attr3 + '\'' +
                 ", approveTime=" + approveTime +
                 '}';
     }
 }