package com.cuixing.eq.entity;

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
 * 设备台账;
 * @author : http://www.chiner.pro
 * @date : 2024-6-17
 */
@ApiModel(value = "设备台账")
public class EqEquipment extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键")
    @Excel(name = "设备台账id", cellType = Excel.ColumnType.NUMERIC, prompt = "设备台账id")
    private BigInteger id ;
    /** 设备编号 */
    @ApiModelProperty(name = "设备编号")
    @Excel(name = "设备编号")
    private String equipmentCode ;
    /** 设备名称 */
    @ApiModelProperty(name = "设备名称")
    @Excel(name = "设备名称")
    private String equipmentName ;
    /** 设备类型id */
    @ApiModelProperty(name = "设备类型id")
    @Excel(name = "设备类型id")
    private BigInteger workbenchId ;
    /** 类型名称 */
    @ApiModelProperty(name = "类型名称")
    @Excel(name = "类型名称")
    private String workbenchType ;
    /** 设备型号 */
    @ApiModelProperty(name = "设备型号")
    @Excel(name = "设备型号")
    private String equipmentModel ;
    /** 所在位置 */
    @ApiModelProperty(name = "所在位置")
    @Excel(name = "所在位置")
    private String position ;
    /** 设备供应商 */
    @ApiModelProperty(name = "设备供应商")
    @Excel(name = "设备供应商")
    private String supplier ;
    /** 状态 */
    @ApiModelProperty(name = "状态")
    @Excel(name = "状态")
    private String status ;
    /** 提供线径 */
    @ApiModelProperty(name = "提供线径")
    @Excel(name = "提供线径")
    private String putWireDiameter ;
    /** 完成线径 */
    @ApiModelProperty(name = "完成线径")
    @Excel(name = "完成线径")
    private String outWireDiameter ;
    /** 购买日期 */
    @ApiModelProperty(name = "购买日期")
    @Excel(name = "购买日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date buyTime ;
    /** 备注 */
    @ApiModelProperty(name = "备注")
    @Excel(name = "备注")
    private String remark ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除")
    @Excel(name = "逻辑删除")
    private Integer isDeleted ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁")
    @Excel(name = "乐观锁")
    private Integer version ;
    /** 创建人 */
    @ApiModelProperty(name = "创建人")
    @Excel(name = "创建人")
    private String createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间")
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;
    /** 更新人 */
    @ApiModelProperty(name = "更新人")
    @Excel(name = "更新人")
    private String updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间")
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1")
    @Excel(name = "预留字段1")
    private String attr1 ;
    /** 是否关联机台设置 */
    @ApiModelProperty(name = "是否关联机台设置")
    @Excel(name = "是否关联机台设置")
    private Integer attr2 ;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3")
    @Excel(name = "预留字段3")
    private String attr3 ;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 设备编号 */
    public String getEquipmentCode(){
        return this.equipmentCode;
    }
    /** 设备编号 */
    public void setEquipmentCode(String equipmentCode){
        this.equipmentCode=equipmentCode;
    }
    /** 设备名称 */
    public String getEquipmentName(){
        return this.equipmentName;
    }
    /** 设备名称 */
    public void setEquipmentName(String equipmentName){
        this.equipmentName=equipmentName;
    }
    /** 设备类型id */
    public BigInteger getWorkbenchId(){
        return this.workbenchId;
    }
    /** 设备类型id */
    public void setWorkbenchId(BigInteger workbenchId){
        this.workbenchId=workbenchId;
    }
    /** 类型名称 */
    public String getWorkbenchType(){
        return this.workbenchType;
    }
    /** 类型名称 */
    public void setWorkbenchType(String workbenchType){
        this.workbenchType=workbenchType;
    }
    /** 设备型号 */
    public String getEquipmentModel(){
        return this.equipmentModel;
    }
    /** 设备型号 */
    public void setEquipmentModel(String equipmentModel){
        this.equipmentModel=equipmentModel;
    }
    /** 所在位置 */
    public String getPosition(){
        return this.position;
    }
    /** 所在位置 */
    public void setPosition(String position){
        this.position=position;
    }
    /** 设备供应商 */
    public String getSupplier(){
        return this.supplier;
    }
    /** 设备供应商 */
    public void setSupplier(String supplier){
        this.supplier=supplier;
    }
    /** 状态 */
    public String getStatus(){
        return this.status;
    }
    /** 状态 */
    public void setStatus(String status){
        this.status=status;
    }
    /** 提供线径 */
    public String getPutWireDiameter(){
        return this.putWireDiameter;
    }
    /** 提供线径 */
    public void setPutWireDiameter(String putWireDiameter){
        this.putWireDiameter=putWireDiameter;
    }
    /** 完成线径 */
    public String getOutWireDiameter(){
        return this.outWireDiameter;
    }
    /** 完成线径 */
    public void setOutWireDiameter(String outWireDiameter){
        this.outWireDiameter=outWireDiameter;
    }
    /** 购买日期 */
    public Date getBuyTime(){
        return this.buyTime;
    }
    /** 购买日期 */
    public void setBuyTime(Date buyTime){
        this.buyTime=buyTime;
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
            .append("equipmentCode", getEquipmentCode())
            .append("equipmentName", getEquipmentName())
            .append("workbenchId", getWorkbenchId())
            .append("workbenchType", getWorkbenchType())
            .append("equipmentModel", getEquipmentModel())
            .append("position", getPosition())
            .append("supplier", getSupplier())
            .append("status", getStatus())
            .append("putWireDiameter", getPutWireDiameter())
            .append("outWireDiameter", getOutWireDiameter())
            .append("buyTime", getBuyTime())
            .append("remark", getRemark())
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