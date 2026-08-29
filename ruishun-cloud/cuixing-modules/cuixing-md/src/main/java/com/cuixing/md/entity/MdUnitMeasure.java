package com.cuixing.md.entity;

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
 * 单位表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-3-20
 */
@ApiModel(value = "单位表",description = "")
public class MdUnitMeasure extends BaseEntity{
    /** 单位ID */
    @ApiModelProperty(name = "单位ID",notes = "")
    private BigInteger id ;
    /** 单位编码 */
    @ApiModelProperty(name = "单位编码",notes = "")
    private String measureCode ;
    /** 单位名称 */
    @ApiModelProperty(name = "单位名称",notes = "")
    private String measureName ;
    /** 是否是主单位 */
    @ApiModelProperty(name = "是否是主单位",notes = "")
    private Integer ifPrimary ;
    /** 主单位ID */
    @ApiModelProperty(name = "主单位ID",notes = "")
    private BigInteger primaryId ;
    /** 与主单位换算比例 */
    @ApiModelProperty(name = "与主单位换算比例",notes = "")
    private BigDecimal changeRate ;
    /** 是否启用 */
    @ApiModelProperty(name = "是否启用",notes = "")
    private Integer ifEnable ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1",notes = "")
    private String attr1 ;
    /** 预留字段2 */
    @ApiModelProperty(name = "预留字段2",notes = "")
    private Integer attr2 ;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3",notes = "")
    private String attr3 ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除",notes = "")
    private Integer isDeleted ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁",notes = "")
    private Integer version ;
    /** 创建者 */
    @ApiModelProperty(name = "创建者",notes = "")
    private String createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;
    /** 更新者 */
    @ApiModelProperty(name = "更新者",notes = "")
    private String updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;

    /** 单位ID */
    public BigInteger getId(){
        return this.id;
    }
    /** 单位ID */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 单位编码 */
    public String getMeasureCode(){
        return this.measureCode;
    }
    /** 单位编码 */
    public void setMeasureCode(String measureCode){
        this.measureCode=measureCode;
    }
    /** 单位名称 */
    public String getMeasureName(){
        return this.measureName;
    }
    /** 单位名称 */
    public void setMeasureName(String measureName){
        this.measureName=measureName;
    }
    /** 是否是主单位 */
    public Integer getIfPrimary(){
        return this.ifPrimary;
    }
    /** 是否是主单位 */
    public void setIfPrimary(Integer ifPrimary){
        this.ifPrimary=ifPrimary;
    }
    /** 主单位ID */
    public BigInteger getPrimaryId(){
        return this.primaryId;
    }
    /** 主单位ID */
    public void setPrimaryId(BigInteger primaryId){
        this.primaryId=primaryId;
    }
    /** 与主单位换算比例 */
    public BigDecimal getChangeRate(){
        return this.changeRate;
    }
    /** 与主单位换算比例 */
    public void setChangeRate(BigDecimal changeRate){
        this.changeRate=changeRate;
    }
    /** 是否启用 */
    public Integer getIfEnable(){
        return this.ifEnable;
    }
    /** 是否启用 */
    public void setIfEnable(Integer ifEnable){
        this.ifEnable=ifEnable;
    }
    /** 备注 */
    public String getRemark(){
        return this.remark;
    }
    /** 备注 */
    public void setRemark(String remark){
        this.remark=remark;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("measureCode", getMeasureCode())
                .append("measureName", getMeasureName())
                .append("ifPrimary", getIfPrimary())
                .append("primaryId", getPrimaryId())
                .append("changeRate", getChangeRate())
                .append("ifEnable", getIfEnable())
                .append("remark", getRemark())
                .append("attr1", getAttr1())
                .append("attr2", getAttr2())
                .append("attr3", getAttr3())
                .append("isDeleted", getIsDeleted())
                .append("version", getVersion())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}