package com.cuixing.md.entity;

import com.cuixing.common.core.annotation.Excel;
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
 * 物料表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-3-20
 */
@ApiModel(value = "物料表",description = "")
public class MdItem extends BaseEntity{
    /** 产品物料ID */
    @ApiModelProperty(name = "产品物料ID",notes = "")
    @Excel(name = "产品物料ID", cellType = Excel.ColumnType.NUMERIC, prompt = "产品物料ID")
    private BigInteger id ;
    /** 产品物料编码 */
    @ApiModelProperty(name = "产品物料编码",notes = "")
    @Excel(name = "产品物料编码")
    private String itemCode ;
    /** 产品物料名称 */
    @ApiModelProperty(name = "产品物料名称",notes = "")
    @Excel(name = "产品物料名称")
    private String itemName ;
    /** 规格型号 */
    @ApiModelProperty(name = "规格型号",notes = "")
    @Excel(name = "规格型号")
    private String specification ;
    /** 数量单位id（单位表） */
    @ApiModelProperty(name = "数量单位id（单位表）",notes = "")
    @Excel(name = "数量单位id")
    private BigInteger numMeasureId ;
    /** 数量单位名（单位表） */
    @ApiModelProperty(name = "数量单位名（单位表）",notes = "")
    @Excel(name = "数量单位名")
    private String numMeasureName ;
    /** 重量单位id（单位表） */
    @ApiModelProperty(name = "重量单位id（单位表）",notes = "")
    @Excel(name = "重量单位id")
    private BigInteger weightMeasureId ;
    /** 重量单位名（单位表） */
    @ApiModelProperty(name = "重量单位名（单位表）",notes = "")
    @Excel(name = "重量单位名")
    private String weightMeasureName ;
    /** 物料类型ID */
    @ApiModelProperty(name = "物料类型ID",notes = "")
    @Excel(name = "物料类型ID")
    private BigInteger itemTypeId ;
    /** 物料类型编码 */
    @ApiModelProperty(name = "物料类型编码",notes = "")
    @Excel(name = "物料类型编码")
    private String itemTypeCode ;
    /** 工序id */
    @ApiModelProperty(name = "工序id",notes = "")
    @Excel(name = "工序id")
    private BigInteger workstageId ;
    /** 工序名称 */
    @ApiModelProperty(name = "工序名称",notes = "")
    @Excel(name = "工序名称")
    private String workstageName ;
    /** 物料类型名称 */
    @ApiModelProperty(name = "物料类型名称",notes = "")
    @Excel(name = "物料类型名称")
    private String itemTypeName ;
    /** 是否启用 */
    @ApiModelProperty(name = "是否启用",notes = "")
    @Excel(name = "是否启用")
    private Integer ifEnable ;
    /** 是否设置安全库存 */
    @ApiModelProperty(name = "是否设置安全库存",notes = "")
    @Excel(name = "是否设置安全库存")
    private Integer ifSafeStock ;
    /** 最低库存量 */
    @ApiModelProperty(name = "最低库存量",notes = "")
    @Excel(name = "最低库存量")
    private BigDecimal minStock ;
    /** 最大库存量 */
    @ApiModelProperty(name = "最大库存量",notes = "")
    @Excel(name = "最大库存量")
    private BigDecimal maxStock ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    @Excel(name = "备注")
    private String remark ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁",notes = "")
    private Integer version ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除",notes = "")
      private Integer isDeleted ;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1",notes = "")
      private String attr1 ;
    /** 预留字段2 */
    @ApiModelProperty(name = "预留字段2",notes = "")
      private Integer attr2 ;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3",notes = "")
        private String attr3 ;
    /** 创建者 */
    @ApiModelProperty(name = "创建者",notes = "")
    @Excel(name = "创建者")
    private String createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间")
    private Date createTime ;
    /** 更新者 */
    @ApiModelProperty(name = "更新者",notes = "")
    @Excel(name = "更新者")
    private String updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间")
    private Date updateTime ;

    /** 产品物料ID */
    public BigInteger getId(){
        return this.id;
    }
    /** 产品物料ID */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 产品物料编码 */
    public String getItemCode(){
        return this.itemCode;
    }
    /** 产品物料编码 */
    public void setItemCode(String itemCode){
        this.itemCode=itemCode;
    }
    /** 产品物料名称 */
    public String getItemName(){
        return this.itemName;
    }
    /** 产品物料名称 */
    public void setItemName(String itemName){
        this.itemName=itemName;
    }
    /** 规格型号 */
    public String getSpecification(){
        return this.specification;
    }
    /** 规格型号 */
    public void setSpecification(String specification){
        this.specification=specification;
    }
    /** 数量单位id（单位表） */
    public BigInteger getNumMeasureId(){
        return this.numMeasureId;
    }
    /** 数量单位id（单位表） */
    public void setNumMeasureId(BigInteger numMeasureId){
        this.numMeasureId=numMeasureId;
    }
    /** 数量单位名（单位表） */
    public String getNumMeasureName(){
        return this.numMeasureName;
    }
    /** 数量单位名（单位表） */
    public void setNumMeasureName(String numMeasureName){
        this.numMeasureName=numMeasureName;
    }
    /** 重量单位id（单位表） */
    public BigInteger getWeightMeasureId(){
        return this.weightMeasureId;
    }
    /** 重量单位id（单位表） */
    public void setWeightMeasureId(BigInteger weightMeasureId){
        this.weightMeasureId=weightMeasureId;
    }
    /** 重量单位名（单位表） */
    public String getWeightMeasureName(){
        return this.weightMeasureName;
    }
    /** 重量单位名（单位表） */
    public void setWeightMeasureName(String weightMeasureName){
        this.weightMeasureName=weightMeasureName;
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
    /** 是否启用 */
    public Integer getIfEnable(){
        return this.ifEnable;
    }
    /** 是否启用 */
    public void setIfEnable(Integer ifEnable){
        this.ifEnable=ifEnable;
    }
    /** 是否设置安全库存 */
    public Integer getIfSafeStock(){
        return this.ifSafeStock;
    }
    /** 是否设置安全库存 */
    public void setIfSafeStock(Integer ifSafeStock){
        this.ifSafeStock=ifSafeStock;
    }
    /** 最低库存量 */
    public BigDecimal getMinStock(){
        return this.minStock;
    }
    /** 最低库存量 */
    public void setMinStock(BigDecimal minStock){
        this.minStock=minStock;
    }
    /** 最大库存量 */
    public BigDecimal getMaxStock(){
        return this.maxStock;
    }
    /** 最大库存量 */
    public void setMaxStock(BigDecimal maxStock){
        this.maxStock=maxStock;
    }
    /** 备注 */
    public String getRemark(){
        return this.remark;
    }
    /** 备注 */
    public void setRemark(String remark){
        this.remark=remark;
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
    /** 创建者 */
    public String getCreateBy(){
        return this.createBy;
    }
    /** 创建者 */
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
    /** 更新者 */
    public String getUpdateBy(){
        return this.updateBy;
    }
    /** 更新者 */
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

    public BigInteger getWorkstageId() {return workstageId;}
    public void setWorkstageId(BigInteger workstageId) {this.workstageId = workstageId;}
    public String getWorkstageName() {return workstageName;}
    public void setWorkstageName(String workstageName) {this.workstageName = workstageName;}

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("itemCode", getItemCode())
                .append("itemName", getItemName())
                .append("specification", getSpecification())
                .append("numMeasureId", getNumMeasureId())
                .append("numMeasureName", getNumMeasureName())
                .append("weightMeasureId", getWeightMeasureId())
                .append("weightMeasureName", getWeightMeasureName())
                .append("itemTypeId", getItemTypeId())
                .append("itemTypeCode", getItemTypeCode())
                .append("itemTypeName", getItemTypeName())
                .append("workstageId", getWorkstageId())
                .append("workstageName", getWorkstageName())
                .append("ifEnable", getIfEnable())
                .append("ifSafeStock", getIfSafeStock())
                .append("minStock", getMinStock())
                .append("maxStock", getMaxStock())
                .append("remark", getRemark())
                .append("version", getVersion())
                .append("isDeleted", getIsDeleted())
                .append("attr1", getAttr1())
                .append("attr2", getAttr2())
                .append("attr3", getAttr3())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}