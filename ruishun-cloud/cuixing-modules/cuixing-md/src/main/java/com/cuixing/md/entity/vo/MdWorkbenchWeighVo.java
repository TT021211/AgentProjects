package com.cuixing.md.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

/**
 * 称重机台设置;
 * @author : http://www.chiner.pro
 * @date : 2024-8-2
 */
@ApiModel(value = "称重机台设置",description = "")
public class MdWorkbenchWeighVo extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 机台类型id */
    @ApiModelProperty(name = "机台类型id",notes = "")
    private BigInteger workbenchTypeId ;
    /** 机台类型 */
    @ApiModelProperty(name = "机台类型",notes = "")
    private String workbenchType ;
    /** 称重机台名称 */
    @ApiModelProperty(name = "称重机台名称",notes = "")
    private String machineName ;
    /** 称重机编码 */
    @ApiModelProperty(name = "称重机编码",notes = "")
    private String machineCode ;
    /** 称重机型号 */
    @ApiModelProperty(name = "称重机型号",notes = "")
    private String machineType ;
    /** 称重机品牌 */
    @ApiModelProperty(name = "称重机品牌",notes = "")
    private String machineBrand ;
    /** 称重机模板值 */
    @ApiModelProperty(name = "称重机模板值",notes = "")
    private Integer machineTemplate ;
    /** 机台型号id */
    @ApiModelProperty(name = "机台型号id",notes = "")
    private BigInteger modelId ;
    /** 机台型号名称 */
    @ApiModelProperty(name = "机台型号名称",notes = "")
    private String modelName ;
    /** 是否启用 */
    @ApiModelProperty(name = "是否启用",notes = "")
    private Integer ifEnable ;
    /** 关联设备id */
    @ApiModelProperty(name = "关联设备id",notes = "")
    private BigInteger equipmentId ;
    /** 关联设备编码 */
    @ApiModelProperty(name = "关联设备编码",notes = "")
    private String equipmentCode ;
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

    /** 关联打印模板 */
    @ApiModelProperty(name = "关联打印模板",notes = "")
    private BigInteger printTemplateId ;
    /** 关联模板名称 */
    @ApiModelProperty(name = "关联模板名称",notes = "")
    private String printTemplateName ;

    /** 打印模板内容 */
    @ApiModelProperty(name = "打印模板内容",notes = "")
    private String content ;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 机台类型id */
    public BigInteger getWorkbenchTypeId(){
        return this.workbenchTypeId;
    }
    /** 机台类型id */
    public void setWorkbenchTypeId(BigInteger workbenchTypeId){
        this.workbenchTypeId=workbenchTypeId;
    }
    /** 机台类型 */
    public String getWorkbenchType(){
        return this.workbenchType;
    }
    /** 机台类型 */
    public void setWorkbenchType(String workbenchType){
        this.workbenchType=workbenchType;
    }
    /** 称重机台名称 */
    public String getMachineName(){
        return this.machineName;
    }
    /** 称重机台名称 */
    public void setMachineName(String machineName){
        this.machineName=machineName;
    }
    /** 称重机编码 */
    public String getMachineCode(){
        return this.machineCode;
    }
    /** 称重机编码 */
    public void setMachineCode(String machineCode){
        this.machineCode=machineCode;
    }
    /** 称重机型号 */
    public String getMachineType(){
        return this.machineType;
    }
    /** 称重机型号 */
    public void setMachineType(String machineType){
        this.machineType=machineType;
    }
    /** 称重机品牌 */
    public String getMachineBrand(){
        return this.machineBrand;
    }
    /** 称重机品牌 */
    public void setMachineBrand(String machineBrand){
        this.machineBrand=machineBrand;
    }
    /** 称重机模板值 */
    public Integer getMachineTemplate(){
        return this.machineTemplate;
    }
    /** 称重机模板值 */
    public void setMachineTemplate(Integer machineTemplate){
        this.machineTemplate=machineTemplate;
    }
    /** 机台型号id */
    public BigInteger getModelId(){
        return this.modelId;
    }
    /** 机台型号id */
    public void setModelId(BigInteger modelId){
        this.modelId=modelId;
    }
    /** 机台型号名称 */
    public String getModelName(){
        return this.modelName;
    }
    /** 机台型号名称 */
    public void setModelName(String modelName){
        this.modelName=modelName;
    }
    /** 是否启用 */
    public Integer getIfEnable(){
        return this.ifEnable;
    }
    /** 是否启用 */
    public void setIfEnable(Integer ifEnable){
        this.ifEnable=ifEnable;
    }
    /** 关联设备id */
    public BigInteger getEquipmentId(){
        return this.equipmentId;
    }
    /** 关联设备id */
    public void setEquipmentId(BigInteger equipmentId){
        this.equipmentId=equipmentId;
    }
    /** 关联设备编码 */
    public String getEquipmentCode(){
        return this.equipmentCode;
    }
    /** 关联设备编码 */
    public void setEquipmentCode(String equipmentCode){
        this.equipmentCode=equipmentCode;
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

    public BigInteger getPrintTemplateId() {
        return printTemplateId;
    }

    public void setPrintTemplateId(BigInteger printTemplateId) {
        this.printTemplateId = printTemplateId;
    }

    public String getPrintTemplateName() {
        return printTemplateName;
    }

    public void setPrintTemplateName(String printTemplateName) {
        this.printTemplateName = printTemplateName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("workbenchTypeId", getWorkbenchTypeId())
                .append("workbenchType", getWorkbenchType())
                .append("machineName", getMachineName())
                .append("machineCode", getMachineCode())
                .append("machineType", getMachineType())
                .append("machineBrand", getMachineBrand())
                .append("machineTemplate", getMachineTemplate())
                .append("modelId", getModelId())
                .append("modelName", getModelName())
                .append("ifEnable", getIfEnable())
                .append("equipmentId", getEquipmentId())
                .append("equipmentCode", getEquipmentCode())
                .append("isDeleted", getIsDeleted())
                .append("version", getVersion())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("attr1", getAttr1())
                .append("attr2", getAttr2())
                .append("attr3", getAttr3())
                .append("printTemplateId", getPrintTemplateId())
                .append("printTemplateName", getPrintTemplateName())
                .append("content", getContent())
                .toString();
    }
}