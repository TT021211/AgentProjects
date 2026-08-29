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
 * 工艺录入;
 * @author : http://www.chiner.pro
 * @date : 2024-6-6
 */
@ApiModel(value = "工艺录入",description = "")
public class MesProcessEnter extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;

    /** 产品型号 */
    @ApiModelProperty(name = "产品型号",notes = "")
    @Excel(name = "产品型号")
    private String productModel ;
    /** 产品规格 */
    @ApiModelProperty(name = "产品规格",notes = "")
    @Excel(name = "产品规格(mm)")
    private String productSpecification ;
    /** 产品名称 */
    @Excel(name = "产品名称")
    @ApiModelProperty(name = "产品名称",notes = "")
    private String productName ;
    /** 产品编号 */
    @Excel(name = "产品编号")
    @ApiModelProperty(name = "产品编号",notes = "")
    private String productCode ;
    /** 产品版次 */
    @Excel(name = "产品版次")
    @ApiModelProperty(name = "产品版次",notes = "")
    private String productEdition ;
    /** 设备id */
    @ApiModelProperty(name = "设备id",notes = "")
    private BigInteger equipmentId ;
    /** 设备编码 */
    @Excel(name = "设备编码")
    @ApiModelProperty(name = "设备编码",notes = "")
    private String equipmentCode ;
     /** 预留字段1 */
    @ApiModelProperty(name = "位置",notes = "")
    @Excel(name = "位置")
    private String attr1 ;
    /** 审核人 */
    @ApiModelProperty(name = "审核人",notes = "")
    private String examineBy ;
    /** 批准人 */
    @ApiModelProperty(name = "批准人",notes = "")
    private String approveBy ;
    /** 批准状态 */
    @ApiModelProperty(name = "批准状态",notes = "")
    private String approveStatus ;
    /** 交期 */
    @ApiModelProperty(name = "交期",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime ;
    /** 软化控温上层 */
    @Excel(name = "软化上[进口]±30(℃)")
    @ApiModelProperty(name = "软化上",notes = "")
    private Integer softenTemperatureUp ;
    /** 软化控温中 */
    @Excel(name = "软化中[中间]±30(℃)")
    @ApiModelProperty(name = "软化控温下层",notes = "")
    private Integer softenTemperatureDown ;
     /** 软化控温下层 */
     @Excel(name = "软化下[出口]±30(℃)")
     @ApiModelProperty(name = "软化控温下层",notes = "")
     private Integer softenTemperatureOut ;
    /** 烘炉控温进口 */
    @Excel(name = "烘炉进口[炉口]±30(℃)")
    @ApiModelProperty(name = "烘炉控温进口",notes = "")
    private Integer ovenTemperatureEnter ;
     /** 蒸发一 */
     @ApiModelProperty(name = "蒸发一",notes = "")
     @Excel(name = "蒸发一")
     private String evaporationOne ;

     /** 蒸发二 */
     @ApiModelProperty(name = "蒸发二",notes = "")
     @Excel(name = "蒸发二")
     private String evaporationTwo ;
    /** 烘炉控温炉中 */
    @Excel(name = "烘炉炉中[固化一]±30(℃)")
    @ApiModelProperty(name = "烘炉控温炉中",notes = "")
    private Integer ovenTemperatureMiddle ;
    /** 烘炉控温出口 */
    @Excel(name = "烘炉出口[固化二]±30(℃)")
    @ApiModelProperty(name = "烘炉控温出口",notes = "")
    private Integer ovenTemperatureOut ;
    /** 催化后第一次 */

    @ApiModelProperty(name = "催化后第一次",notes = "")
    private String catalyzeFirst ;
     /** 催化前 */
     @Excel(name = "催化前(℃)")
     @ApiModelProperty(name = "催化前控温",notes = "")
     private String catalyzeBefore ;
    /** 催化后 */
    @Excel(name = "催化后(℃)")
    @ApiModelProperty(name = "催化后第二次",notes = "")
    private String catalyzeSecond ;
    /** 交换热风速度 */
    @Excel(name = "交换风机[进口补充、新鲜空气](rpm)")
    @ApiModelProperty(name = "交换热风速度",notes = "")
    private String changeWindSpeed ;
    /** 循环风机速度 */
    @Excel(name = "循环风机(rpm)")
    @ApiModelProperty(name = "循环风机速度",notes = "")
    private String circulatingFanSpeed ;
    /** 排废风机速度 */
    @Excel(name = "排废风机(rpm)")
    @ApiModelProperty(name = "排废风机速度",notes = "")
    private String exhaustFanSpeed ;
    /** 线速 */
    @Excel(name = "线速(m/min)")
    @ApiModelProperty(name = "线速",notes = "")
    private String wireSpeed ;
    /** 粘粘度4号杯 */
    @Excel(name = "粘粘度4号杯±5秒")
    @ApiModelProperty(name = "粘粘度4号杯",notes = "")
    private String viscosityCup ;
    /** 收线轴型号 */
    @Excel(name = "收线轴型号")
    @ApiModelProperty(name = "收线轴型号",notes = "")
    private String putReelModel ;
    /** 执行时间 */
    @ApiModelProperty(name = "执行时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date executeTime ;
    /** 导体标称直径 */
    @ApiModelProperty(name = "导体标称直径",notes = "")
    private Double standardDiameter ;
    /** 漆包线最大外径薄漆膜 */
    @ApiModelProperty(name = "漆包线最大外径薄漆膜",notes = "")
    private Double wireFilmMax ;
    /** 漆包线最大外径厚漆膜 */
    @ApiModelProperty(name = "漆包线最大外径厚漆膜",notes = "")
    private Double wireThickMax ;
    /** 最小漆膜厚度薄漆膜 */
    @ApiModelProperty(name = "最小漆膜厚度薄漆膜",notes = "")
    private Double wireFilmMin ;
    /** 最小漆膜厚度厚漆膜 */
    @ApiModelProperty(name = "最小漆膜厚度厚漆膜",notes = "")
    private Double wireThickMin ;
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
    /** 产品规格 */
    public String getProductSpecification(){
        return this.productSpecification;
    }
    /** 产品规格 */
    public void setProductSpecification(String productSpecification){
        this.productSpecification=productSpecification;
    }
    /** 产品型号 */
    public String getProductModel(){
        return this.productModel;
    }
    /** 产品型号 */
    public void setProductModel(String productModel){
        this.productModel=productModel;
    }
    /** 产品名称 */
    public String getProductName(){
        return this.productName;
    }
    /** 产品名称 */
    public void setProductName(String productName){
        this.productName=productName;
    }
    /** 产品编号 */
    public String getProductCode(){
        return this.productCode;
    }
    /** 产品编号 */
    public void setProductCode(String productCode){
        this.productCode=productCode;
    }
    /** 产品版次 */
    public String getProductEdition(){
        return this.productEdition;
    }
    /** 产品版次 */
    public void setProductEdition(String productEdition){
        this.productEdition=productEdition;
    }
    /** 设备id */
    public BigInteger getEquipmentId(){
        return this.equipmentId;
    }
    /** 设备id */
    public void setEquipmentId(BigInteger equipmentId){
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
    /** 审核人 */
    public String getExamineBy(){
        return this.examineBy;
    }
    /** 审核人 */
    public void setExamineBy(String examineBy){
        this.examineBy=examineBy;
    }
    /** 批准人 */
    public String getApproveBy(){
        return this.approveBy;
    }
    /** 批准人 */
    public void setApproveBy(String approveBy){
        this.approveBy=approveBy;
    }
    /** 批准状态 */
    public String getApproveStatus(){
        return this.approveStatus;
    }
    /** 批准状态 */
    public void setApproveStatus(String approveStatus){
        this.approveStatus=approveStatus;
    }
    /** 交期 */
    public Date getDeliveryTime(){
        return this.deliveryTime;
    }
    /** 交期 */
    public void setDeliveryTime(Date deliveryTime){
        this.deliveryTime=deliveryTime;
    }
    /** 软化控温上层 */
    public Integer getSoftenTemperatureUp(){
        return this.softenTemperatureUp;
    }
    /** 软化控温上层 */
    public void setSoftenTemperatureUp(Integer softenTemperatureUp){
        this.softenTemperatureUp=softenTemperatureUp;
    }
    /** 软化控温下层 */
    public Integer getSoftenTemperatureDown(){
        return this.softenTemperatureDown;
    }
    /** 软化控温下层 */
    public void setSoftenTemperatureDown(Integer softenTemperatureDown){
        this.softenTemperatureDown=softenTemperatureDown;
    }
    /** 烘炉控温进口 */
    public Integer getOvenTemperatureEnter(){
        return this.ovenTemperatureEnter;
    }
    /** 烘炉控温进口 */
    public void setOvenTemperatureEnter(Integer ovenTemperatureEnter){
        this.ovenTemperatureEnter=ovenTemperatureEnter;
    }
    /** 烘炉控温炉中 */
    public Integer getOvenTemperatureMiddle(){
        return this.ovenTemperatureMiddle;
    }
    /** 烘炉控温炉中 */
    public void setOvenTemperatureMiddle(Integer ovenTemperatureMiddle){
        this.ovenTemperatureMiddle=ovenTemperatureMiddle;
    }
    /** 烘炉控温出口 */
    public Integer getOvenTemperatureOut(){
        return this.ovenTemperatureOut;
    }
    /** 烘炉控温出口 */
    public void setOvenTemperatureOut(Integer ovenTemperatureOut){
        this.ovenTemperatureOut=ovenTemperatureOut;
    }
    /** 催化后第一次 */
    public String getCatalyzeFirst(){
        return this.catalyzeFirst;
    }
    /** 催化后第一次 */
    public void setCatalyzeFirst(String catalyzeFirst){
        this.catalyzeFirst=catalyzeFirst;
    }
    /** 催化后第二次 */
    public String getCatalyzeSecond(){
        return this.catalyzeSecond;
    }
    /** 催化后第二次 */
    public void setCatalyzeSecond(String catalyzeSecond){
        this.catalyzeSecond=catalyzeSecond;
    }
    /** 催化前控温 */
    public String getCatalyzeBefore(){
        return this.catalyzeBefore;
    }
    /** 催化前控温 */
    public void setCatalyzeBefore(String catalyzeBefore){
        this.catalyzeBefore=catalyzeBefore;
    }
    /** 交换热风速度 */
    public String getChangeWindSpeed(){
        return this.changeWindSpeed;
    }
    /** 交换热风速度 */
    public void setChangeWindSpeed(String changeWindSpeed){
        this.changeWindSpeed=changeWindSpeed;
    }
    /** 循环风机速度 */
    public String getCirculatingFanSpeed(){
        return this.circulatingFanSpeed;
    }
    /** 循环风机速度 */
    public void setCirculatingFanSpeed(String circulatingFanSpeed){
        this.circulatingFanSpeed=circulatingFanSpeed;
    }
    /** 排废风机速度 */
    public String getExhaustFanSpeed(){
        return this.exhaustFanSpeed;
    }
    /** 排废风机速度 */
    public void setExhaustFanSpeed(String exhaustFanSpeed){
        this.exhaustFanSpeed=exhaustFanSpeed;
    }
    /** 线速 */
    public String getWireSpeed(){
        return this.wireSpeed;
    }
    /** 线速 */
    public void setWireSpeed(String wireSpeed){
        this.wireSpeed=wireSpeed;
    }
    /** 粘粘度4号杯 */
    public String getViscosityCup(){
        return this.viscosityCup;
    }
    /** 粘粘度4号杯 */
    public void setViscosityCup(String viscosityCup){
        this.viscosityCup=viscosityCup;
    }
    /** 收线轴型号 */
    public String getPutReelModel(){
        return this.putReelModel;
    }
    /** 收线轴型号 */
    public void setPutReelModel(String putReelModel){
        this.putReelModel=putReelModel;
    }
    /** 执行时间 */
    public Date getExecuteTime(){
        return this.executeTime;
    }
    /** 执行时间 */
    public void setExecuteTime(Date executeTime){
        this.executeTime=executeTime;
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


     public String getEvaporationOne() {
         return evaporationOne;
     }

     public void setEvaporationOne(String evaporationOne) {
         this.evaporationOne = evaporationOne;
     }

     public String getEvaporationTwo() {
         return evaporationTwo;
     }

     public void setEvaporationTwo(String evaporationTwo) {
         this.evaporationTwo = evaporationTwo;
     }


     public Integer getSoftenTemperatureOut() {
         return softenTemperatureOut;
     }

     public void setSoftenTemperatureOut(Integer softenTemperatureOut) {
         this.softenTemperatureOut = softenTemperatureOut;
     }

     @Override
     public String toString() {
         return "MesProcessEnter{" +
                 "id=" + id +
                 ", productModel='" + productModel + '\'' +
                 ", productSpecification='" + productSpecification + '\'' +
                 ", productName='" + productName + '\'' +
                 ", productCode='" + productCode + '\'' +
                 ", productEdition='" + productEdition + '\'' +
                 ", equipmentId=" + equipmentId +
                 ", equipmentCode='" + equipmentCode + '\'' +
                 ", attr1='" + attr1 + '\'' +
                 ", examineBy='" + examineBy + '\'' +
                 ", approveBy='" + approveBy + '\'' +
                 ", approveStatus='" + approveStatus + '\'' +
                 ", deliveryTime=" + deliveryTime +
                 ", softenTemperatureUp=" + softenTemperatureUp +
                 ", softenTemperatureDown=" + softenTemperatureDown +
                 ", softenTemperatureOut=" + softenTemperatureOut +
                 ", ovenTemperatureEnter=" + ovenTemperatureEnter +
                 ", evaporationOne='" + evaporationOne + '\'' +
                 ", evaporationTwo='" + evaporationTwo + '\'' +
                 ", ovenTemperatureMiddle=" + ovenTemperatureMiddle +
                 ", ovenTemperatureOut=" + ovenTemperatureOut +
                 ", catalyzeFirst='" + catalyzeFirst + '\'' +
                 ", catalyzeBefore='" + catalyzeBefore + '\'' +
                 ", catalyzeSecond='" + catalyzeSecond + '\'' +
                 ", changeWindSpeed='" + changeWindSpeed + '\'' +
                 ", circulatingFanSpeed='" + circulatingFanSpeed + '\'' +
                 ", exhaustFanSpeed='" + exhaustFanSpeed + '\'' +
                 ", wireSpeed='" + wireSpeed + '\'' +
                 ", viscosityCup='" + viscosityCup + '\'' +
                 ", putReelModel='" + putReelModel + '\'' +
                 ", executeTime=" + executeTime +
                 ", standardDiameter=" + standardDiameter +
                 ", wireFilmMax=" + wireFilmMax +
                 ", wireThickMax=" + wireThickMax +
                 ", wireFilmMin=" + wireFilmMin +
                 ", wireThickMin=" + wireThickMin +
                 ", isDeleted=" + isDeleted +
                 ", version=" + version +
                 ", createBy='" + createBy + '\'' +
                 ", createTime=" + createTime +
                 ", updateBy='" + updateBy + '\'' +
                 ", updateTime=" + updateTime +
                 ", attr2=" + attr2 +
                 ", attr3='" + attr3 + '\'' +
                 '}';
     }
 }