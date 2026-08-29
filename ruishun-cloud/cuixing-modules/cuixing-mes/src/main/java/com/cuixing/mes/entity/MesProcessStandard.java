package com.cuixing.mes.entity;

import com.cuixing.common.core.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 工艺标准;
 * @author : http://www.chiner.pro
 * @date : 2024-7-23
 */
@ApiModel(value = "工艺标准",description = "")
public class MesProcessStandard extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 标准编号(0：内控标准 1：国家标准 2：客户标准) */
    @ApiModelProperty(name = "标准编号(0：内控标准 1：国家标准 2：客户标准)",notes = "")
    private String standardCode ;
    /** 标准名称 */
    @Excel(name = "标准名称")
    @ApiModelProperty(name = "标准名称",notes = "")
    private String standardName ;
    /** 客户id */
    @ApiModelProperty(name = "客户id",notes = "")
    private BigInteger customerId ;
    /** 客户名称 */
    @Excel(name = "客户名称")
    @ApiModelProperty(name = "客户名称",notes = "")
    private String customerName ;
    /** 客户编码 */
    @Excel(name = "客户编码")
    @ApiModelProperty(name = "客户编码",notes = "")
    private String customerCode ;
    /** 导体标称直径 */
    @Excel(name = "导体标称直径")
    @ApiModelProperty(name = "导体标称直径",notes = "")
    private Double standardDiameter ;
    /** 漆包线最大外径薄漆膜 */
    @Excel(name = "漆包线最大外径薄漆膜")
    @ApiModelProperty(name = "漆包线最大外径薄漆膜",notes = "")
    private Double wireFilmMax ;
    /** 漆包线最大外径厚漆膜 */
    @Excel(name = "漆包线最大外径厚漆膜")
    @ApiModelProperty(name = "漆包线最大外径厚漆膜",notes = "")
    private Double wireThickMax ;
    /** 最小漆膜厚度薄漆膜 */
    @Excel(name = "最小漆膜厚度薄漆膜")
    @ApiModelProperty(name = "最小漆膜厚度薄漆膜",notes = "")
    private Double wireFilmMin ;
    /** 最小漆膜厚度厚漆膜 */
    @Excel(name = "最小漆膜厚度厚漆膜")
    @ApiModelProperty(name = "最小漆膜厚度厚漆膜",notes = "")
    private Double wireThickMin ;
    /** 最大外径 */
    @Excel(name = "最大外径")
    @ApiModelProperty(name = "最大外径",notes = "")
    private Double outsideDiameterMax ;
    /** 导体偏差 */
    @Excel(name = "导体偏差")
    @ApiModelProperty(name = "导体偏差",notes = "")
    private Double conductorDeviation ;
    /** 导体f值 */
    @Excel(name = "导体f值")
    @ApiModelProperty(name = "导体f值",notes = "")
    private Double conductorFNumber ;
    /** 最小直流电阻(℃) */
    @Excel(name = "最小直流电阻(℃)")
    @ApiModelProperty(name = "最小直流电阻(℃)",notes = "")
    private Double dcResistanceMin ;
    /** 最大直流电阻(℃) */
    @Excel(name = "最大直流电阻(℃)")
    @ApiModelProperty(name = "最大直流电阻(℃)",notes = "")
    private Double dcResistanceMax ;
    /** 伸长率 */
    @Excel(name = "伸长率")
    @ApiModelProperty(name = "伸长率",notes = "")
    private Double elongation ;
    /** 回弹性 */
    @Excel(name = "回弹性")
    @ApiModelProperty(name = "回弹性",notes = "")
    private Double resilience ;
    /** 圆棒卷绕 */
    @Excel(name = "圆棒卷绕")
    @ApiModelProperty(name = "圆棒卷绕",notes = "")
    private String wind ;
    /** 拉伸 */
    @Excel(name = "拉伸")
    @ApiModelProperty(name = "拉伸",notes = "")
    private String stretch ;
    /** 急拉断 */
    @Excel(name = "急拉断")
    @ApiModelProperty(name = "急拉断",notes = "")
    private String snapBreak ;
    /** 剥离扭绞 */
    @Excel(name = "剥离扭绞")
    @ApiModelProperty(name = "剥离扭绞",notes = "")
    private String strippingTwist ;
    /** 热冲击 */
    @Excel(name = "热冲击")
    @ApiModelProperty(name = "热冲击",notes = "")
    private String thermalShock ;
    /** 软化冲击 */
    @Excel(name = "软化冲击")
    @ApiModelProperty(name = "软化冲击",notes = "")
    private String softeningImpact ;
    /** 软化击穿 */
    @Excel(name = "软化击穿")
    @ApiModelProperty(name = "软化击穿",notes = "")
    private String softeningBreakdown ;
    /** 刮漆1 */
    @Excel(name = "刮漆1")
    @ApiModelProperty(name = "刮漆1",notes = "")
    private Double scraperPaintingOne ;
    /** 刮漆2 */
    @Excel(name = "刮漆2")
    @ApiModelProperty(name = "刮漆2",notes = "")
    private Double scraperPaintingTwo ;
    /** 耐溶剂 */
    @Excel(name = "耐溶剂")
    @ApiModelProperty(name = "耐溶剂",notes = "")
    private String solventResistance ;
    /** 室温击穿电压 */
    @Excel(name = "室温击穿电压")
    @ApiModelProperty(name = "室温击穿电压",notes = "")
    private Double roomtempBreakdown ;
    /** 高压针孔 */
    @Excel(name = "高压针孔")
    @ApiModelProperty(name = "高压针孔",notes = "")
    private String highPressurePinhole ;
    /** 盐水针孔 */
    @Excel(name = "盐水针孔")
    @ApiModelProperty(name = "盐水针孔",notes = "")
    private String brinePinhole ;
    /** 焊锡性 */
    @Excel(name = "焊锡性")
    @ApiModelProperty(name = "焊锡性",notes = "")
    private String solderability ;
    /** 外观 */
    @Excel(name = "外观")
    @ApiModelProperty(name = "外观",notes = "")
    private String appearance ;
    /** 包装 */
    @Excel(name = "包装")
    @ApiModelProperty(name = "包装",notes = "")
    private String packaged ;
    /** 环保材料 */
    @Excel(name = "环保材料")
    @ApiModelProperty(name = "环保材料",notes = "")
    private String envMaterial ;
    /** 其他要求 */
    @Excel(name = "其他要求")
    @ApiModelProperty(name = "其他要求",notes = "")
    private String otherRequirements ;
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
    /** 预留字段4 */
    @ApiModelProperty(name = "预留字段4",notes = "")
    private Double attr5 ;
    /** 预留字段5 */
    @ApiModelProperty(name = "预留字段5",notes = "")
    private String attr6 ;
    /** 预留字段6 */
    @ApiModelProperty(name = "预留字段6",notes = "")
    private String attr7 ;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 标准编号(0：内控标准 1：国家标准 2：客户标准) */
    public String getStandardCode(){
        return this.standardCode;
    }
    /** 标准编号(0：内控标准 1：国家标准 2：客户标准) */
    public void setStandardCode(String standardCode){
        this.standardCode=standardCode;
    }
    /** 标准名称 */
    public String getStandardName(){
        return this.standardName;
    }
    /** 标准名称 */
    public void setStandardName(String standardName){
        this.standardName=standardName;
    }
    /** 客户id */
    public BigInteger getCustomerId(){
        return this.customerId;
    }
    /** 客户id */
    public void setCustomerId(BigInteger customerId){
        this.customerId=customerId;
    }
    /** 客户名称 */
    public String getCustomerName(){
        return this.customerName;
    }
    /** 客户名称 */
    public void setCustomerName(String customerName){
        this.customerName=customerName;
    }
    /** 客户编码 */
    public String getCustomerCode(){
        return this.customerCode;
    }
    /** 客户编码 */
    public void setCustomerCode(String customerCode){
        this.customerCode=customerCode;
    }
    /** 导体标称直径 */
    public Double getStandardDiameter(){
        return this.standardDiameter;
    }
    /** 导体标称直径 */
    public void setStandardDiameter(Double standardDiameter){
        this.standardDiameter=standardDiameter;
    }
    /** 漆包线最大外径薄漆膜 */
    public Double getWireFilmMax(){
        return this.wireFilmMax;
    }
    /** 漆包线最大外径薄漆膜 */
    public void setWireFilmMax(Double wireFilmMax){
        this.wireFilmMax=wireFilmMax;
    }
    /** 漆包线最大外径厚漆膜 */
    public Double getWireThickMax(){
        return this.wireThickMax;
    }
    /** 漆包线最大外径厚漆膜 */
    public void setWireThickMax(Double wireThickMax){
        this.wireThickMax=wireThickMax;
    }
    /** 最小漆膜厚度薄漆膜 */
    public Double getWireFilmMin(){
        return this.wireFilmMin;
    }
    /** 最小漆膜厚度薄漆膜 */
    public void setWireFilmMin(Double wireFilmMin){
        this.wireFilmMin=wireFilmMin;
    }
    /** 最小漆膜厚度厚漆膜 */
    public Double getWireThickMin(){
        return this.wireThickMin;
    }
    /** 最小漆膜厚度厚漆膜 */
    public void setWireThickMin(Double wireThickMin){
        this.wireThickMin=wireThickMin;
    }
    /** 最大外径 */
    public Double getOutsideDiameterMax(){
        return this.outsideDiameterMax;
    }
    /** 最大外径 */
    public void setOutsideDiameterMax(Double outsideDiameterMax){
        this.outsideDiameterMax=outsideDiameterMax;
    }
    /** 导体偏差 */
    public Double getConductorDeviation(){
        return this.conductorDeviation;
    }
    /** 导体偏差 */
    public void setConductorDeviation(Double conductorDeviation){
        this.conductorDeviation=conductorDeviation;
    }
    /** 导体f值 */
    public Double getConductorFNumber(){
        return this.conductorFNumber;
    }
    /** 导体f值 */
    public void setConductorFNumber(Double conductorFNumber){
        this.conductorFNumber=conductorFNumber;
    }
    /** 最小直流电阻(℃) */
    public Double getDcResistanceMin(){
        return this.dcResistanceMin;
    }
    /** 最小直流电阻(℃) */
    public void setDcResistanceMin(Double dcResistanceMin){
        this.dcResistanceMin=dcResistanceMin;
    }
    /** 最大直流电阻(℃) */
    public Double getDcResistanceMax(){
        return this.dcResistanceMax;
    }
    /** 最大直流电阻(℃) */
    public void setDcResistanceMax(Double dcResistanceMax){
        this.dcResistanceMax=dcResistanceMax;
    }
    /** 伸长率 */
    public Double getElongation(){
        return this.elongation;
    }
    /** 伸长率 */
    public void setElongation(Double elongation){
        this.elongation=elongation;
    }
    /** 回弹性 */
    public Double getResilience(){
        return this.resilience;
    }
    /** 回弹性 */
    public void setResilience(Double resilience){
        this.resilience=resilience;
    }
    /** 圆棒卷绕 */
    public String getWind(){
        return this.wind;
    }
    /** 圆棒卷绕 */
    public void setWind(String wind){
        this.wind=wind;
    }
    /** 拉伸 */
    public String getStretch(){
        return this.stretch;
    }
    /** 拉伸 */
    public void setStretch(String stretch){
        this.stretch=stretch;
    }
    /** 急拉断 */
    public String getSnapBreak(){
        return this.snapBreak;
    }
    /** 急拉断 */
    public void setSnapBreak(String snapBreak){
        this.snapBreak=snapBreak;
    }
    /** 剥离扭绞 */
    public String getStrippingTwist(){
        return this.strippingTwist;
    }
    /** 剥离扭绞 */
    public void setStrippingTwist(String strippingTwist){
        this.strippingTwist=strippingTwist;
    }
    /** 热冲击 */
    public String getThermalShock(){
        return this.thermalShock;
    }
    /** 热冲击 */
    public void setThermalShock(String thermalShock){
        this.thermalShock=thermalShock;
    }
    /** 软化冲击 */
    public String getSofteningImpact(){
        return this.softeningImpact;
    }
    /** 软化冲击 */
    public void setSofteningImpact(String softeningImpact){
        this.softeningImpact=softeningImpact;
    }
    /** 软化击穿 */
    public String getSofteningBreakdown(){
        return this.softeningBreakdown;
    }
    /** 软化击穿 */
    public void setSofteningBreakdown(String softeningBreakdown){
        this.softeningBreakdown=softeningBreakdown;
    }
    /** 刮漆1 */
    public Double getScraperPaintingOne(){
        return this.scraperPaintingOne;
    }
    /** 刮漆1 */
    public void setScraperPaintingOne(Double scraperPaintingOne){
        this.scraperPaintingOne=scraperPaintingOne;
    }
    /** 刮漆2 */
    public Double getScraperPaintingTwo(){
        return this.scraperPaintingTwo;
    }
    /** 刮漆2 */
    public void setScraperPaintingTwo(Double scraperPaintingTwo){
        this.scraperPaintingTwo=scraperPaintingTwo;
    }
    /** 耐溶剂 */
    public String getSolventResistance(){
        return this.solventResistance;
    }
    /** 耐溶剂 */
    public void setSolventResistance(String solventResistance){
        this.solventResistance=solventResistance;
    }
    /** 室温击穿电压 */
    public Double getRoomtempBreakdown(){
        return this.roomtempBreakdown;
    }
    /** 室温击穿电压 */
    public void setRoomtempBreakdown(Double roomtempBreakdown){
        this.roomtempBreakdown=roomtempBreakdown;
    }
    /** 高压针孔 */
    public String getHighPressurePinhole(){
        return this.highPressurePinhole;
    }
    /** 高压针孔 */
    public void setHighPressurePinhole(String highPressurePinhole){
        this.highPressurePinhole=highPressurePinhole;
    }
    /** 盐水针孔 */
    public String getBrinePinhole(){
        return this.brinePinhole;
    }
    /** 盐水针孔 */
    public void setBrinePinhole(String brinePinhole){
        this.brinePinhole=brinePinhole;
    }
    /** 焊锡性 */
    public String getSolderability(){
        return this.solderability;
    }
    /** 焊锡性 */
    public void setSolderability(String solderability){
        this.solderability=solderability;
    }
    /** 外观 */
    public String getAppearance(){
        return this.appearance;
    }
    /** 外观 */
    public void setAppearance(String appearance){
        this.appearance=appearance;
    }
    /** 包装 */
    public String getPackaged(){
        return this.packaged;
    }
    /** 包装 */
    public void setPackaged(String packaged){
        this.packaged=packaged;
    }
    /** 环保材料 */
    public String getEnvMaterial(){
        return this.envMaterial;
    }
    /** 环保材料 */
    public void setEnvMaterial(String envMaterial){
        this.envMaterial=envMaterial;
    }
    /** 其他要求 */
    public String getOtherRequirements(){
        return this.otherRequirements;
    }
    /** 其他要求 */
    public void setOtherRequirements(String otherRequirements){
        this.otherRequirements=otherRequirements;
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
    /** 预留字段4 */
    public Double getAttr5(){
        return this.attr5;
    }
    /** 预留字段4 */
    public void setAttr5(Double attr5){
        this.attr5=attr5;
    }
    /** 预留字段5 */
    public String getAttr6(){
        return this.attr6;
    }
    /** 预留字段5 */
    public void setAttr6(String attr6){
        this.attr6=attr6;
    }
    /** 预留字段6 */
    public String getAttr7(){
        return this.attr7;
    }
    /** 预留字段6 */
    public void setAttr7(String attr7){
        this.attr7=attr7;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("standardCode", getStandardCode())
            .append("standardName", getStandardName())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("customerCode", getCustomerCode())
            .append("standardDiameter", getStandardDiameter())
            .append("wireFilmMax", getWireFilmMax())
            .append("wireThickMax", getWireThickMax())
            .append("wireFilmMin", getWireFilmMin())
            .append("wireThickMin", getWireThickMin())
            .append("outsideDiameterMax", getOutsideDiameterMax())
            .append("conductorDeviation", getConductorDeviation())
            .append("conductorFNumber", getConductorFNumber())
            .append("dcResistanceMin", getDcResistanceMin())
            .append("dcResistanceMax", getDcResistanceMax())
            .append("elongation", getElongation())
            .append("resilience", getResilience())
            .append("wind", getWind())
            .append("stretch", getStretch())
            .append("snapBreak", getSnapBreak())
            .append("strippingTwist", getStrippingTwist())
            .append("thermalShock", getThermalShock())
            .append("softeningImpact", getSofteningImpact())
            .append("softeningBreakdown", getSofteningBreakdown())
            .append("scraperPaintingOne", getScraperPaintingOne())
            .append("scraperPaintingTwo", getScraperPaintingTwo())
            .append("solventResistance", getSolventResistance())
            .append("roomtempBreakdown", getRoomtempBreakdown())
            .append("highPressurePinhole", getHighPressurePinhole())
            .append("brinePinhole", getBrinePinhole())
            .append("solderability", getSolderability())
            .append("appearance", getAppearance())
            .append("packaged", getPackaged())
            .append("envMaterial", getEnvMaterial())
            .append("otherRequirements", getOtherRequirements())
            .append("isDeleted", getIsDeleted())
            .append("version", getVersion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr5", getAttr5())
            .append("attr6", getAttr6())
            .append("attr7", getAttr7())
        .toString();
    }
}