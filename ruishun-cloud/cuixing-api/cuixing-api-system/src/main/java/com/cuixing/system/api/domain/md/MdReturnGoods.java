package com.cuixing.system.api.domain.md;

import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigInteger;
import java.util.Date;

/**
* 退货记录;
* @author : http://www.chiner.pro
* @date : 2024-11-1
*/
@ApiModel(value = "退货记录",description = "")
public class MdReturnGoods extends BaseEntity{
   /** 自增主键 */
   @ApiModelProperty(name = "自增主键",notes = "")
   private BigInteger id ;
   /** 退货类型 */
   @ApiModelProperty(name = "退货类型",notes = "")
   private String type ;
   /** 采购单id */
   @ApiModelProperty(name = "采购单id",notes = "")
   private BigInteger purchasOrderId ;
   /** 采购编号 */
   @ApiModelProperty(name = "采购编号",notes = "")
   private String purchasOrderCode ;
   /** 退货数量 */
   @ApiModelProperty(name = "退货数量",notes = "")
   private Integer returnNumber ;
   /** 退货重量 */
   @ApiModelProperty(name = "退货重量",notes = "")
   private Double returnWeight ;
    /** 入库重量 */
   @ApiModelProperty(name = "入库重量",notes = "")
   private Double oldWeight ;
   /** 数量单位id */
   @ApiModelProperty(name = "数量单位id",notes = "")
   private BigInteger quantityUnitId ;
   /** 数量单位 */
   @ApiModelProperty(name = "数量单位",notes = "")
   private String quantityUnit ;
   /** 退货二维码 */
   @ApiModelProperty(name = "退货二维码",notes = "")
   private String returnSnCode ;
   /** 退货名称 */
   @ApiModelProperty(name = "退货名称",notes = "")
   private String returnName ;
   /** 退货型号 */
   @ApiModelProperty(name = "退货型号",notes = "")
   private String returnModel ;
   /** 退货规格 */
   @ApiModelProperty(name = "退货规格",notes = "")
   private String returnSpecification ;
   /** 退货供应商 */
   @ApiModelProperty(name = "退货供应商",notes = "")
   private String returnSuppier ;
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
   /** 退货类型 */
   public String getType(){
       return this.type;
   }
   /** 退货类型 */
   public void setType(String type){
       this.type=type;
   }
   /** 采购单id */
   public BigInteger getPurchasOrderId(){
       return this.purchasOrderId;
   }
   /** 采购单id */
   public void setPurchasOrderId(BigInteger purchasOrderId){
       this.purchasOrderId=purchasOrderId;
   }
   /** 采购编号 */
   public String getPurchasOrderCode(){
       return this.purchasOrderCode;
   }
   /** 采购编号 */
   public void setPurchasOrderCode(String purchasOrderCode){
       this.purchasOrderCode=purchasOrderCode;
   }
   /** 退货数量 */
   public Integer getReturnNumber(){
       return this.returnNumber;
   }
   /** 退货数量 */
   public void setReturnNumber(Integer returnNumber){
       this.returnNumber=returnNumber;
   }
   /** 退货重量 */
   public Double getReturnWeight(){
       return this.returnWeight;
   }
   /** 退货重量 */
   public void setReturnWeight(Double returnWeight){
       this.returnWeight=returnWeight;
   }
   /** 数量单位id */
   public BigInteger getQuantityUnitId(){
       return this.quantityUnitId;
   }
   /** 数量单位id */
   public void setQuantityUnitId(BigInteger quantityUnitId){
       this.quantityUnitId=quantityUnitId;
   }
   /** 数量单位 */
   public String getQuantityUnit(){
       return this.quantityUnit;
   }
   /** 数量单位 */
   public void setQuantityUnit(String quantityUnit){
       this.quantityUnit=quantityUnit;
   }
   /** 退货二维码 */
   public String getReturnSnCode(){
       return this.returnSnCode;
   }
   /** 退货二维码 */
   public void setReturnSnCode(String returnSnCode){
       this.returnSnCode=returnSnCode;
   }
   /** 退货名称 */
   public String getReturnName(){
       return this.returnName;
   }
   /** 退货名称 */
   public void setReturnName(String returnName){
       this.returnName=returnName;
   }
   /** 退货型号 */
   public String getReturnModel(){
       return this.returnModel;
   }
   /** 退货型号 */
   public void setReturnModel(String returnModel){
       this.returnModel=returnModel;
   }
   /** 退货规格 */
   public String getReturnSpecification(){
       return this.returnSpecification;
   }
   /** 退货规格 */
   public void setReturnSpecification(String returnSpecification){
       this.returnSpecification=returnSpecification;
   }
   /** 退货供应商 */
   public String getReturnSuppier(){
       return this.returnSuppier;
   }
   /** 退货供应商 */
   public void setReturnSuppier(String returnSuppier){
       this.returnSuppier=returnSuppier;
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

    public Double getOldWeight() {
        return oldWeight;
    }

    public void setOldWeight(Double oldWeight) {
        this.oldWeight = oldWeight;
    }

    @Override
    public String toString() {
        return "MdReturnGoods{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", purchasOrderId=" + purchasOrderId +
                ", purchasOrderCode='" + purchasOrderCode + '\'' +
                ", returnNumber=" + returnNumber +
                ", returnWeight=" + returnWeight +
                ", oldWeight=" + oldWeight +
                ", quantityUnitId=" + quantityUnitId +
                ", quantityUnit='" + quantityUnit + '\'' +
                ", returnSnCode='" + returnSnCode + '\'' +
                ", returnName='" + returnName + '\'' +
                ", returnModel='" + returnModel + '\'' +
                ", returnSpecification='" + returnSpecification + '\'' +
                ", returnSuppier='" + returnSuppier + '\'' +
                ", isDeleted=" + isDeleted +
                ", version=" + version +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", attr1='" + attr1 + '\'' +
                ", attr2=" + attr2 +
                ", attr3='" + attr3 + '\'' +
                '}';
    }
}