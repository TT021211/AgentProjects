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
 * 辅料出入库记录;
 * @author : http://www.chiner.pro
 * @date : 2024-6-28
 */
@ApiModel(value = "辅料出入库记录",description = "")
public class WmsAccessoryLog extends BaseEntity{
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
    /** 辅料id */
    @ApiModelProperty(name = "辅料id",notes = "")
    private BigInteger accessoryId ;
    /** 辅料名称 */
    @ApiModelProperty(name = "辅料名称",notes = "")
    private String accessoryName ;
    /** 型号 */
    @ApiModelProperty(name = "型号",notes = "")
    private String model ;
    /** 重量 */
    @ApiModelProperty(name = "重量",notes = "")
    private Double weight ;
    /** 重量单位id */
    @ApiModelProperty(name = "重量单位id",notes = "")
    private BigInteger weightMeasureId ;
    /** 重量单位名称 */
    @ApiModelProperty(name = "重量单位名称",notes = "")
    private String weightMeasureName ;
    /** 数量 */
    @ApiModelProperty(name = "数量",notes = "")
    private Integer number ;
    /** 数量单位id */
    @ApiModelProperty(name = "数量单位id",notes = "")
    private BigInteger numberMeasureId ;
    /** 数量单位名称 */
    @ApiModelProperty(name = "数量单位名称",notes = "")
    private String numberMeasureName ;
    /** 供应商id */
    @ApiModelProperty(name = "供应商id",notes = "")
    private BigInteger suppierId ;
    /** 供应商 */
    @ApiModelProperty(name = "供应商",notes = "")
    private String supplier ;
    /** 状态(0：入库 1：出库) */
    @ApiModelProperty(name = "状态(0：入库 1：出库)",notes = "")
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
    /** 辅料id */
    public BigInteger getAccessoryId(){
        return this.accessoryId;
    }
    /** 辅料id */
    public void setAccessoryId(BigInteger accessoryId){
        this.accessoryId=accessoryId;
    }
    /** 辅料名称 */
    public String getAccessoryName(){
        return this.accessoryName;
    }
    /** 辅料名称 */
    public void setAccessoryName(String accessoryName){
        this.accessoryName=accessoryName;
    }
    /** 型号 */
    public String getModel(){
        return this.model;
    }
    /** 型号 */
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
    /** 重量单位id */
    public BigInteger getWeightMeasureId(){
        return this.weightMeasureId;
    }
    /** 重量单位id */
    public void setWeightMeasureId(BigInteger weightMeasureId){
        this.weightMeasureId=weightMeasureId;
    }
    /** 重量单位名称 */
    public String getWeightMeasureName(){
        return this.weightMeasureName;
    }
    /** 重量单位名称 */
    public void setWeightMeasureName(String weightMeasureName){
        this.weightMeasureName=weightMeasureName;
    }
    /** 数量 */
    public Integer getNumber(){
        return this.number;
    }
    /** 数量 */
    public void setNumber(Integer number){
        this.number=number;
    }
    /** 数量单位id */
    public BigInteger getNumberMeasureId(){
        return this.numberMeasureId;
    }
    /** 数量单位id */
    public void setNumberMeasureId(BigInteger numberMeasureId){
        this.numberMeasureId=numberMeasureId;
    }
    /** 数量单位名称 */
    public String getNumberMeasureName(){
        return this.numberMeasureName;
    }
    /** 数量单位名称 */
    public void setNumberMeasureName(String numberMeasureName){
        this.numberMeasureName=numberMeasureName;
    }
    /** 供应商id */
    public BigInteger getSuppierId(){
        return this.suppierId;
    }
    /** 供应商id */
    public void setSuppierId(BigInteger suppierId){
        this.suppierId=suppierId;
    }
    /** 供应商 */
    public String getSupplier(){
        return this.supplier;
    }
    /** 供应商 */
    public void setSupplier(String supplier){
        this.supplier=supplier;
    }
    /** 状态(0：入库 1：出库) */
    public String getStatus(){
        return this.status;
    }
    /** 状态(0：入库 1：出库) */
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
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("itemTypeId", getItemTypeId())
            .append("itemTypeCode", getItemTypeCode())
            .append("itemTypeName", getItemTypeName())
            .append("accessoryId", getAccessoryId())
            .append("accessoryName", getAccessoryName())
            .append("model", getModel())
            .append("weight", getWeight())
            .append("weightMeasureId", getWeightMeasureId())
            .append("weightMeasureName", getWeightMeasureName())
            .append("number", getNumber())
            .append("numberMeasureId", getNumberMeasureId())
            .append("numberMeasureName", getNumberMeasureName())
            .append("suppierId", getSuppierId())
            .append("supplier", getSupplier())
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