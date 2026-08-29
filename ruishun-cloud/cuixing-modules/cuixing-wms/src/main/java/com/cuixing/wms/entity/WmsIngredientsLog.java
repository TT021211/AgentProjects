package com.cuixing.wms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 原料出入库日志;
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@ApiModel(value = "原料出入库日志",description = "")
public class WmsIngredientsLog extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 二维码条码 */
    @ApiModelProperty(name = "二维码条码",notes = "")
    private String codeSn ;
    /** 贸易商id */
    @ApiModelProperty(name = "贸易商id",notes = "")
    private BigInteger ingredientsId ;
    /** 贸易商名称 */
    @ApiModelProperty(name = "贸易商名称",notes = "")
    private String ingredientsName ;
    /** 厂家id */
    @ApiModelProperty(name = "厂家id",notes = "")
    private BigInteger manufacturerId ;
    /** 厂家名称 */
    @ApiModelProperty(name = "厂家名称",notes = "")
    private String manufacturerName ;
    /** 料号 */
    @ApiModelProperty(name = "料号",notes = "")
    private String partNumber ;
    /** 批号 */
    @ApiModelProperty(name = "批号",notes = "")
    private String lotNumber ;
    /** 品名id */
    @ApiModelProperty(name = "品名id",notes = "")
    private BigInteger productId ;
    /** 品名 */
    @ApiModelProperty(name = "品名",notes = "")
    private String productName ;
     /** 物料id */
     @ApiModelProperty(name = "物料id",notes = "")
     private BigInteger itemId ;
    /** 规格 */
    @ApiModelProperty(name = "规格",notes = "")
    private String itemSpecification ;
    /** 净重 */
    @ApiModelProperty(name = "净重",notes = "")
    private Double netWeight;
    /** 毛重 */
    @ApiModelProperty(name = "毛重",notes = "")
    private Double grossWeight ;
    /** 皮重 */
    @ApiModelProperty(name = "皮重",notes = "")
    private Double tareWeight ;
    /** 单位id */
    @ApiModelProperty(name = "单位id",notes = "")
    private BigInteger measureId ;
    /** 单位名称 */
    @ApiModelProperty(name = "单位名称",notes = "")
    private String measureName ;
    /** 工号 */
    @ApiModelProperty(name = "工号",notes = "")
    private String jobNumber ;
    /** 状态(0:入库 1：出库) */
    @ApiModelProperty(name = "状态(0:入库 1：出库)",notes = "")
    private String status ;
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
    /** 二维码条码 */
    public String getCodeSn(){
        return this.codeSn;
    }
    /** 二维码条码 */
    public void setCodeSn(String codeSn){
        this.codeSn=codeSn;
    }
    /** 贸易商id */
    public BigInteger getIngredientsId(){
        return this.ingredientsId;
    }
    /** 贸易商id */
    public void setIngredientsId(BigInteger ingredientsId){
        this.ingredientsId=ingredientsId;
    }
    /** 贸易商名称 */
    public String getIngredientsName(){
        return this.ingredientsName;
    }
    /** 贸易商名称 */
    public void setIngredientsName(String ingredientsName){
        this.ingredientsName=ingredientsName;
    }
    /** 厂家id */
    public BigInteger getManufacturerId(){
        return this.manufacturerId;
    }
    /** 厂家id */
    public void setManufacturerId(BigInteger manufacturerId){
        this.manufacturerId=manufacturerId;
    }
    /** 厂家名称 */
    public String getManufacturerName(){
        return this.manufacturerName;
    }
    /** 厂家名称 */
    public void setManufacturerName(String manufacturerName){
        this.manufacturerName=manufacturerName;
    }
    /** 料号 */
    public String getPartNumber(){
        return this.partNumber;
    }
    /** 料号 */
    public void setPartNumber(String partNumber){
        this.partNumber=partNumber;
    }
    /** 批号 */
    public String getLotNumber(){
        return this.lotNumber;
    }
    /** 批号 */
    public void setLotNumber(String lotNumber){
        this.lotNumber=lotNumber;
    }
    /** 品名id */
    public BigInteger getProductId(){
        return this.productId;
    }
    /** 品名id */
    public void setProductId(BigInteger productId){
        this.productId=productId;
    }
    /** 品名 */
    public String getProductName(){
        return this.productName;
    }
    /** 品名 */
    public void setProductName(String productName){
        this.productName=productName;
    }
    /** 规格 */
    public String getItemSpecification(){
        return this.itemSpecification;
    }
    /** 规格 */
    public void setItemSpecification(String itemSpecification){
        this.itemSpecification=itemSpecification;
    }
    /** 净重 */
    public Double getNetWeight(){
        return this.netWeight;
    }
    /** 净重 */
    public void setNetWeight(Double netWeight){
        this.netWeight=netWeight;
    }
    /** 毛重 */
    public Double getGrossWeight(){
        return this.grossWeight;
    }
    /** 毛重 */
    public void setGrossWeight(Double grossWeight){
        this.grossWeight=grossWeight;
    }
    /** 皮重 */
    public Double getTareWeight(){
        return this.tareWeight;
    }
    /** 皮重 */
    public void setTareWeight(Double tareWeight){
        this.tareWeight=tareWeight;
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
    /** 工号 */
    public String getJobNumber(){
        return this.jobNumber;
    }
    /** 工号 */
    public void setJobNumber(String jobNumber){
        this.jobNumber=jobNumber;
    }
    /** 状态(0:入库 1：出库) */
    public String getStatus(){
        return this.status;
    }
    /** 状态(0:入库 1：出库) */
    public void setStatus(String status){
        this.status=status;
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

     public BigInteger getItemId() {
         return itemId;
     }

     public void setItemId(BigInteger itemId) {
         this.itemId = itemId;
     }

     @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("codeSn", getCodeSn())
            .append("ingredientsId", getIngredientsId())
            .append("ingredientsName", getIngredientsName())
            .append("manufacturerId", getManufacturerId())
            .append("manufacturerName", getManufacturerName())
            .append("partNumber", getPartNumber())
            .append("lotNumber", getLotNumber())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("itemId", getItemId())
            .append("itemSpecification", getItemSpecification())
            .append("netWeight", getNetWeight())
            .append("grossWeight", getGrossWeight())
            .append("tareWeight", getTareWeight())
            .append("measureId", getMeasureId())
            .append("measureName", getMeasureName())
            .append("jobNumber", getJobNumber())
            .append("status", getStatus())
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