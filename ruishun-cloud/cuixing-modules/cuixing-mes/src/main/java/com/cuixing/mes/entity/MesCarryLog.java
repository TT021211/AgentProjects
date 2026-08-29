package com.cuixing.mes.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 搬运日志;
 * @author : http://www.chiner.pro
 * @date : 2024-10-10
 */
@ApiModel(value = "搬运日志",description = "")
public class MesCarryLog extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 搬运规格 */
    @ApiModelProperty(name = "搬运规格",notes = "")
    private String specification ;
    /** 产出拉丝机台 */
    @ApiModelProperty(name = "产出拉丝机台",notes = "")
    private String equipmentCode ;
    /** 搬运漆包机台 */
    @ApiModelProperty(name = "搬运漆包机台",notes = "")
    private String enamellingEquipmentCode ;
    /** 搬运半成品二维码 */
    @ApiModelProperty(name = "搬运半成品二维码",notes = "")
    private String itemIdentifier ;
    /** 搬运人工号 */
    @ApiModelProperty(name = "搬运人工号",notes = "")
    private String jobNumber ;
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
    /** 搬运规格 */
    public String getSpecification(){
        return this.specification;
    }
    /** 搬运规格 */
    public void setSpecification(String specification){
        this.specification=specification;
    }
    /** 产出拉丝机台 */
    public String getEquipmentCode(){
        return this.equipmentCode;
    }
    /** 产出拉丝机台 */
    public void setEquipmentCode(String equipmentCode){
        this.equipmentCode=equipmentCode;
    }
    /** 搬运漆包机台 */
    public String getEnamellingEquipmentCode(){
        return this.enamellingEquipmentCode;
    }
    /** 搬运漆包机台 */
    public void setEnamellingEquipmentCode(String enamellingEquipmentCode){
        this.enamellingEquipmentCode=enamellingEquipmentCode;
    }
    /** 搬运半成品二维码 */
    public String getItemIdentifier(){
        return this.itemIdentifier;
    }
    /** 搬运半成品二维码 */
    public void setItemIdentifier(String itemIdentifier){
        this.itemIdentifier=itemIdentifier;
    }
    /** 搬运人工号 */
    public String getJobNumber(){
        return this.jobNumber;
    }
    /** 搬运人工号 */
    public void setJobNumber(String jobNumber){
        this.jobNumber=jobNumber;
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
            .append("specification", getSpecification())
            .append("equipmentCode", getEquipmentCode())
            .append("enamellingEquipmentCode", getEnamellingEquipmentCode())
            .append("itemIdentifier", getItemIdentifier())
            .append("jobNumber", getJobNumber())
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