package com.cuixing.system.api.domain.md;

import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
* 辅料列表;
* @author : http://www.chiner.pro
* @date : 2024-6-11
*/
@ApiModel(value = "辅料列表",description = "")
public class MdAccessory extends BaseEntity{
   /** 自增主键 */
   @ApiModelProperty(name = "自增主键",notes = "")
   private BigInteger id ;
   /** 物料类型ID */
   @ApiModelProperty(name = "物料类型ID",notes = "")
   private BigInteger itemTypeId ;
   /** 物料类型编码 */
   @ApiModelProperty(name = "物料类型编码",notes = "")
   private String itemTypeCode ;
   /** 物料类型名称 */
   @ApiModelProperty(name = "物料类型名称",notes = "")
   private String itemTypeName ;
   /** 型号 */
   @ApiModelProperty(name = "型号",notes = "")
   private String model ;
   /** 单位id */
   @ApiModelProperty(name = "单位id",notes = "")
   private BigInteger measureId ;
   /** 单位名称 */
   @ApiModelProperty(name = "单位名称",notes = "")
   private String measureName ;
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
   /** 重量 */
   @ApiModelProperty(name = "重量",notes = "")
   private BigDecimal weight ;

   /** 父型号id */
   @ApiModelProperty(name = "父型号id",notes = "")
   private BigInteger parentModelId ;
    /** 父型号名称 */
    @ApiModelProperty(name = "父型号名称",notes = "")
    private String parentModel ;

   /** 自增主键 */
   public BigInteger getId(){
       return this.id;
   }
   /** 自增主键 */
   public void setId(BigInteger id){
       this.id=id;
   }
   /** 物料类型ID */
   public BigInteger getItemTypeId(){
       return this.itemTypeId;
   }
   /** 物料类型ID */
   public void setItemTypeId(BigInteger itemTypeId){
       this.itemTypeId=itemTypeId;
   }
   /** 物料类型编码 */
   public String getItemTypeCode(){
       return this.itemTypeCode;
   }
   /** 物料类型编码 */
   public void setItemTypeCode(String itemTypeCode){
       this.itemTypeCode=itemTypeCode;
   }
   /** 物料类型名称 */
   public String getItemTypeName(){
       return this.itemTypeName;
   }
   /** 物料类型名称 */
   public void setItemTypeName(String itemTypeName){
       this.itemTypeName=itemTypeName;
   }
   /** 型号 */
   public String getModel(){
       return this.model;
   }
   /** 型号 */
   public void setModel(String model){
       this.model=model;
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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigInteger getParentModelId() {
        return parentModelId;
    }

    public void setParentModelId(BigInteger parentModelId) {
        this.parentModelId = parentModelId;
    }

    public String getParentModel() {
        return parentModel;
    }

    public void setParentModel(String parentModel) {
        this.parentModel = parentModel;
    }

    @Override
    public String toString() {
        return "MdAccessory{" +
                "id=" + id +
                ", itemTypeId=" + itemTypeId +
                ", itemTypeCode='" + itemTypeCode + '\'' +
                ", itemTypeName='" + itemTypeName + '\'' +
                ", model='" + model + '\'' +
                ", measureId=" + measureId +
                ", measureName='" + measureName + '\'' +
                ", isDeleted=" + isDeleted +
                ", version=" + version +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", attr1='" + attr1 + '\'' +
                ", attr2=" + attr2 +
                ", attr3='" + attr3 + '\'' +
                ", weight=" + weight +
                ", parentModelId=" + parentModelId +
                ", parentModel='" + parentModel + '\'' +
                '}';
    }
}