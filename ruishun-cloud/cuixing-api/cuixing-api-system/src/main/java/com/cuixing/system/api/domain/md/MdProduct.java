package com.cuixing.system.api.domain.md;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import com.cuixing.common.core.web.domain.BaseEntity;
import java.math.BigInteger;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

/**
 * 产品表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-3-20
 */
@ApiModel(value = "产品表",description = "")
public class MdProduct extends BaseEntity{
    /** 产品id（自增主键） */
    @ApiModelProperty(name = "产品id（自增主键）",notes = "")
    private BigInteger id ;
    /** 产品编码 */
    @ApiModelProperty(name = "产品编码",notes = "")
    private String productCode ;
    /** 产品名称 */
    @ApiModelProperty(name = "产品名称",notes = "")
    private String productName ;
    /** 尺寸 */
    @ApiModelProperty(name = "尺寸",notes = "")
    private String productSize ;
    /** 规格 */
    @ApiModelProperty(name = "规格",notes = "")
    private String specification ;
    /** 数量单位id */
    @ApiModelProperty(name = "数量单位id",notes = "")
    private BigInteger numMeasureId ;
    /** 数量单位名 */
    @ApiModelProperty(name = "数量单位名",notes = "")
    private String numMeasureName ;
    /** 重量单位id */
    @ApiModelProperty(name = "重量单位id",notes = "")
    private BigInteger weightMeasureId ;
    /** 重量单位名 */
    @ApiModelProperty(name = "重量单位名",notes = "")
    private String weightMeasureName ;
    /** 工艺路线id */
    @ApiModelProperty(name = "工艺路线id",notes = "")
    private BigInteger processRouteId ;
    /** 工艺路线名 */
    @ApiModelProperty(name = "工艺路线名",notes = "")
    private String processRouteName ;
    /** 产品分类id */
    @ApiModelProperty(name = "产品分类id",notes = "")
    private BigInteger productTypeId ;
    /** 产品分类编码 */
    @ApiModelProperty(name = "产品分类编码",notes = "")
    private String productTypeCode ;
    /** 产品分类名称 */
    @ApiModelProperty(name = "产品分类名称",notes = "")
    private String productTypeName ;
    /** 产品批号 */
    @ApiModelProperty(name = "产品批号",notes = "")
    private String batchNumber ;
    /** 产品批号 */
    @ApiModelProperty(name = "产品轴型",notes = "")
    private String axialType ;
    /** 产品批号 */
    @ApiModelProperty(name = "产品系数",notes = "")
    private String coefficient ;
    /** 产品批号 */
    @ApiModelProperty(name = "产品物料代码",notes = "")
    private String materialCode ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁",notes = "")
    private Integer version ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除",notes = "")
    private Integer isDeleted ;
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

    /** 国际型号 */
    @ApiModelProperty(name = "国际型号",notes = "")
    private String productSizeInternational ;

    /** 产品id（自增主键） */
    public BigInteger getId(){
        return this.id;
    }
    /** 产品id（自增主键） */
    public void setId(BigInteger id){
        this.id=id;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getAxialType() {
        return axialType;
    }

    public void setAxialType(String axialType) {
        this.axialType = axialType;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    /** 产品编码 */
    public String getProductCode(){
        return this.productCode;
    }
    /** 产品编码 */
    public void setProductCode(String productCode){
        this.productCode=productCode;
    }
    /** 产品名称 */
    public String getProductName(){
        return this.productName;
    }
    /** 产品名称 */
    public void setProductName(String productName){
        this.productName=productName;
    }
    /** 尺寸 */
    public String getProductSize(){
        return this.productSize;
    }
    /** 尺寸 */
    public void setProductSize(String productSize){
        this.productSize=productSize;
    }
    /** 规格 */
    public String getSpecification(){
        return this.specification;
    }
    /** 规格 */
    public void setSpecification(String specification){
        this.specification=specification;
    }
    /** 数量单位id */
    public BigInteger getNumMeasureId(){
        return this.numMeasureId;
    }
    /** 数量单位id */
    public void setNumMeasureId(BigInteger numMeasureId){
        this.numMeasureId=numMeasureId;
    }
    /** 数量单位名 */
    public String getNumMeasureName(){
        return this.numMeasureName;
    }
    /** 数量单位名 */
    public void setNumMeasureName(String numMeasureName){
        this.numMeasureName=numMeasureName;
    }
    /** 重量单位id */
    public BigInteger getWeightMeasureId(){
        return this.weightMeasureId;
    }
    /** 重量单位id */
    public void setWeightMeasureId(BigInteger weightMeasureId){
        this.weightMeasureId=weightMeasureId;
    }
    /** 重量单位名 */
    public String getWeightMeasureName(){
        return this.weightMeasureName;
    }
    /** 重量单位名 */
    public void setWeightMeasureName(String weightMeasureName){
        this.weightMeasureName=weightMeasureName;
    }
    /** 工艺路线id */
    public BigInteger getProcessRouteId(){
        return this.processRouteId;
    }
    /** 工艺路线id */
    public void setProcessRouteId(BigInteger processRouteId){
        this.processRouteId=processRouteId;
    }
    /** 工艺路线名 */
    public String getProcessRouteName(){
        return this.processRouteName;
    }
    /** 工艺路线名 */
    public void setProcessRouteName(String processRouteName){
        this.processRouteName=processRouteName;
    }
    /** 产品分类id */
    public BigInteger getProductTypeId(){
        return this.productTypeId;
    }
    /** 产品分类id */
    public void setProductTypeId(BigInteger productTypeId){
        this.productTypeId=productTypeId;
    }
    /** 产品分类编码 */
    public String getProductTypeCode(){
        return this.productTypeCode;
    }
    /** 产品分类编码 */
    public void setProductTypeCode(String productTypeCode){
        this.productTypeCode=productTypeCode;
    }
    /** 产品分类名称 */
    public String getProductTypeName(){
        return this.productTypeName;
    }
    /** 产品分类名称 */
    public void setProductTypeName(String productTypeName){
        this.productTypeName=productTypeName;
    }
    /** 乐观锁 */
    public Integer getVersion(){
        return this.version;
    }
    /** 乐观锁 */
    public void setVersion(Integer version){
        this.version=version;
    }
    /** 逻辑删除 */
    public Integer getIsDeleted(){
        return this.isDeleted;
    }
    /** 逻辑删除 */
    public void setIsDeleted(Integer isDeleted){
        this.isDeleted=isDeleted;
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

    public String getProductSizeInternational() {
        return productSizeInternational;
    }

    public void setProductSizeInternational(String productSizeInternational) {
        this.productSizeInternational = productSizeInternational;
    }

    @Override
    public String toString() {
        return "MdProduct{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productSize='" + productSize + '\'' +
                ", specification='" + specification + '\'' +
                ", numMeasureId=" + numMeasureId +
                ", numMeasureName='" + numMeasureName + '\'' +
                ", weightMeasureId=" + weightMeasureId +
                ", weightMeasureName='" + weightMeasureName + '\'' +
                ", processRouteId=" + processRouteId +
                ", processRouteName='" + processRouteName + '\'' +
                ", productTypeId=" + productTypeId +
                ", productTypeCode='" + productTypeCode + '\'' +
                ", productTypeName='" + productTypeName + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", axialType='" + axialType + '\'' +
                ", coefficient='" + coefficient + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", version=" + version +
                ", isDeleted=" + isDeleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", attr1='" + attr1 + '\'' +
                ", attr2=" + attr2 +
                ", attr3='" + attr3 + '\'' +
                ", productSizeInternational='" + productSizeInternational + '\'' +
                '}';
    }
}
