package com.cuixing.mes.entity;

import com.cuixing.mes.message.vo.Message;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 拉丝工单;
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@ApiModel(value = "拉丝工单",description = "")
public class MesWiredrawingWorkorder extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
     /** 生产工单id */
    @ApiModelProperty(name = "生产工单id",notes = "")
    private BigInteger workorderId ;
    /** 订单类型(0：客户订单 1：备库订单) */
    @ApiModelProperty(name = "订单类型(0：客户订单 1：备库订单)",notes = "")
    private String orderType ;
    /** 客户名称 */
    @ApiModelProperty(name = "客户名称",notes = "")
    private String customerName ;
    /** 订单编号 */
    @ApiModelProperty(name = "订单编号",notes = "")
    private String orderCode ;
    /** 生产工单号 */
    @ApiModelProperty(name = "生产工单号",notes = "")
    private String workorderCode ;
    /** 设备id */
    @ApiModelProperty(name = "设备id",notes = "")
    private String equipmentId ;
    /** 设备编码 */
    @ApiModelProperty(name = "设备编码",notes = "")
    private String equipmentCode ;
    /** 产品规格 */
    @ApiModelProperty(name = "产品规格",notes = "")
    private String specification ;
    /** 产品型号 */
    @ApiModelProperty(name = "产品型号",notes = "")
    private String model ;
    /** 重量 */
    @ApiModelProperty(name = "重量",notes = "")
    private Double weight ;
    /** 单位id（单位表） */
    @ApiModelProperty(name = "单位id（单位表）",notes = "")
    private BigInteger measureId ;
    /** 单位名称（单位表） */
    @ApiModelProperty(name = "单位名称（单位表）",notes = "")
    private String measureName ;
    /** 盘数 */
    @ApiModelProperty(name = "盘数",notes = "")
    private Integer discNumber ;
    /** 开始时间 */
    @ApiModelProperty(name = "开始时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime ;
    /** 结束时间 */
    @ApiModelProperty(name = "结束时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime ;
    /** 排序 */
    @ApiModelProperty(name = "排序",notes = "")
    private Integer sort ;
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
    /** 状态 */
    @ApiModelProperty(name = "状态",notes = "")
    private String status ;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1",notes = "")
    private String attr1 ;
    /** 预留字段2 */
    @ApiModelProperty(name = "预留字段2",notes = "")
    private Integer attr2 ;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3",notes = "")
    private String attr3 ;
     /** 拉丝工单号 */
    @ApiModelProperty(name = "拉丝工单号",notes = "")
    private String wiredrawingCode ;
     /** 产品id */
    @ApiModelProperty(name = "产品id",notes = "")
    private BigInteger productId ;

     /**
      * 漆包机台id
      */
     @ApiModelProperty(name = "漆包机台id",notes = "")
     private BigInteger enamellingEquipmentId ;

     /**
      * 漆包机台名称
      */
     @ApiModelProperty(name = "漆包机台编码",notes = "")
     private String enamellingEquipmentCode ;


     /**
      * 完成重量
      */
     @ApiModelProperty(name = "完成重量",notes = "")
     private Double wightCompletion ;
     /**
      * 完成度
      */
     @ApiModelProperty(name = "完成度",notes = "")
     private Double degreeOfCompletion ;
     /**
      * 线盘id
      */
     @ApiModelProperty(name = "线盘id",notes = "")
     private BigInteger wireDiscId ;
     /** 预留字段1 */
     @ApiModelProperty(name = "预留字段5",notes = "")
     private Double attr5 ;
     /** 预留字段1 */
     @ApiModelProperty(name = "预留字段6",notes = "")
     private String attr6 ;
     /** 预留字段1 */
     @ApiModelProperty(name = "预留字段7",notes = "")
     private String attr7 ;
     /** 预留字段1 */
     @ApiModelProperty(name = "预留字段8",notes = "")
     private String attr8 ;
     /** 预留字段1 */
     @ApiModelProperty(name = "预留字段9",notes = "")
     private String attr9 ;
     /** 预留字段1 */
     @ApiModelProperty(name = "预留字段10",notes = "")
     private String attr10 ;
     /** 预留字段1 */
     @ApiModelProperty(name = "预留字段10",notes = "")
     private String artt6 ;
     /**
      * 消息体
      * @return
      */
     private Message message;

     /**
      * 机台id
      */
     private String deviceId;

     public String getDeviceId() {
         return deviceId;
     }

     public void setDeviceId(String deviceId) {
         this.deviceId = deviceId;
     }
     public Message getMessage() {
         return message;
     }

     public void setMessage(Message message) {
         this.message = message;
     }


    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 订单类型(0：客户订单 1：备库订单) */
    public String getOrderType(){
        return this.orderType;
    }
    /** 订单类型(0：客户订单 1：备库订单) */
    public void setOrderType(String orderType){
        this.orderType=orderType;
    }
    /** 客户名称 */
    public String getCustomerName(){
        return this.customerName;
    }
    /** 客户名称 */
    public void setCustomerName(String customerName){
        this.customerName=customerName;
    }
    /** 订单编号 */
    public String getOrderCode(){
        return this.orderCode;
    }
    /** 订单编号 */
    public void setOrderCode(String orderCode){
        this.orderCode=orderCode;
    }
    /** 生产工单号 */
    public String getWorkorderCode(){
        return this.workorderCode;
    }
    /** 生产工单号 */
    public void setWorkorderCode(String workorderCode){
        this.workorderCode=workorderCode;
    }
    /** 设备id */
    public String getEquipmentId(){
        return this.equipmentId;
    }
    /** 设备id */
    public void setEquipmentId(String equipmentId){
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
    /** 产品规格 */
    public String getSpecification(){
        return this.specification;
    }
    /** 产品规格 */
    public void setSpecification(String specification){
        this.specification=specification;
    }
    /** 产品型号 */
    public String getModel(){
        return this.model;
    }
    /** 产品型号 */
    public void setModel(String model){
        this.model=model;
    }
    /** 重量 */
    public Double getWeight(){
        return this.weight;
    }
    /** 重量 */
    public void setWeight(Double weight){
        this.weight=weight;
    }
    /** 单位id（单位表） */
    public BigInteger getMeasureId(){
        return this.measureId;
    }
    /** 单位id（单位表） */
    public void setMeasureId(BigInteger measureId){
        this.measureId=measureId;
    }
    /** 单位名称（单位表） */
    public String getMeasureName(){
        return this.measureName;
    }
    /** 单位名称（单位表） */
    public void setMeasureName(String measureName){
        this.measureName=measureName;
    }
    /** 盘数 */
    public Integer getDiscNumber(){
        return this.discNumber;
    }
    /** 盘数 */
    public void setDiscNumber(Integer discNumber){
        this.discNumber=discNumber;
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
    /** 排序 */
    public Integer getSort(){
        return this.sort;
    }
    /** 排序 */
    public void setSort(Integer sort){
        this.sort=sort;
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
    /** 状态 */
    public String getStatus(){
        return this.status;
    }
    /** 状态 */
    public void setStatus(String status){
        this.status=status;
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

    public BigInteger getWorkorderId() {return workorderId;}
    public void setWorkorderId(BigInteger workorderId) {this.workorderId = workorderId;}

     public String getWiredrawingCode() {
         return wiredrawingCode;
     }

     public void setWiredrawingCode(String wiredrawingCode) {
         this.wiredrawingCode = wiredrawingCode;
     }

     public BigInteger getProductId() {
         return productId;
     }

     public void setProductId(BigInteger productId) {
         this.productId = productId;
     }

     public BigInteger getEnamellingEquipmentId() {
         return enamellingEquipmentId;
     }

     public void setEnamellingEquipmentId(BigInteger enamellingEquipmentId) {
         this.enamellingEquipmentId = enamellingEquipmentId;
     }

     public BigInteger getWireDiscId() {
         return wireDiscId;
     }

     public void setWireDiscId(BigInteger wireDiscId) {
         this.wireDiscId = wireDiscId;
     }

     public Double getAttr5() {
         return attr5;
     }

     public void setAttr5(Double attr5) {
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

     public String getAttr10() {
         return attr10;
     }

     public void setAttr10(String attr10) {
         this.attr10 = attr10;
     }

     public String getEnamellingEquipmentCode() {
         return enamellingEquipmentCode;
     }

     public void setEnamellingEquipmentCode(String enamellingEquipmentCode) {
         this.enamellingEquipmentCode = enamellingEquipmentCode;
     }

     public Double getWightCompletion() {
         return wightCompletion;
     }

     public void setWightCompletion(Double wightCompletion) {
         this.wightCompletion = wightCompletion;
     }

     public Double getDegreeOfCompletion() {
         return degreeOfCompletion;
     }

     public void setDegreeOfCompletion(Double degreeOfCompletion) {
         this.degreeOfCompletion = degreeOfCompletion;
     }

     public String getArtt6() {
         return artt6;
     }

     public void setArtt6(String artt6) {
         this.artt6 = artt6;
     }

     @Override
     public String toString() {
         return "MesWiredrawingWorkorder{" +
                 "id=" + id +
                 ", workorderId=" + workorderId +
                 ", orderType='" + orderType + '\'' +
                 ", customerName='" + customerName + '\'' +
                 ", orderCode='" + orderCode + '\'' +
                 ", workorderCode='" + workorderCode + '\'' +
                 ", equipmentId='" + equipmentId + '\'' +
                 ", equipmentCode='" + equipmentCode + '\'' +
                 ", specification='" + specification + '\'' +
                 ", model='" + model + '\'' +
                 ", weight=" + weight +
                 ", measureId=" + measureId +
                 ", measureName='" + measureName + '\'' +
                 ", discNumber=" + discNumber +
                 ", startTime=" + startTime +
                 ", endTime=" + endTime +
                 ", sort=" + sort +
                 ", remark='" + remark + '\'' +
                 ", isDeleted=" + isDeleted +
                 ", version=" + version +
                 ", createBy='" + createBy + '\'' +
                 ", createTime=" + createTime +
                 ", updateBy='" + updateBy + '\'' +
                 ", updateTime=" + updateTime +
                 ", status='" + status + '\'' +
                 ", attr1='" + attr1 + '\'' +
                 ", attr2=" + attr2 +
                 ", attr3='" + attr3 + '\'' +
                 ", wiredrawingCode='" + wiredrawingCode + '\'' +
                 ", productId=" + productId +
                 ", enamellingEquipmentId=" + enamellingEquipmentId +
                 ", enamellingEquipmentCode='" + enamellingEquipmentCode + '\'' +
                 ", wightCompletion=" + wightCompletion +
                 ", degreeOfCompletion=" + degreeOfCompletion +
                 ", wireDiscId=" + wireDiscId +
                 ", attr5=" + attr5 +
                 ", attr6='" + attr6 + '\'' +
                 ", attr7='" + attr7 + '\'' +
                 ", attr8='" + attr8 + '\'' +
                 ", attr9='" + attr9 + '\'' +
                 ", attr10='" + attr10 + '\'' +
                 ", artt6='" + artt6 + '\'' +
                 '}';
     }
 }
