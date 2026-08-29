package com.cuixing.md.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 油漆清单;undefined
 * @author : http://www.chiner.pro
 * @date : 2024-8-8
 */
@ApiModel(value = "油漆清单",description = "undefined")
public class MdPlantList extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 油漆名称 */
    @ApiModelProperty(name = "油漆名称",notes = "")
    private String plantName ;
    /** 油漆编码 */
    @ApiModelProperty(name = "油漆编码",notes = "")
    private String plantCode ;
    /** 油漆类型id */
    @ApiModelProperty(name = "油漆类型id",notes = "")
    private BigInteger plantTypeId ;
    /** 油漆类型名称 */
    @ApiModelProperty(name = "油漆类型名称",notes = "")
    private String plantTypeName ;
    /** 油漆供应商id */
    @ApiModelProperty(name = "油漆供应商id",notes = "")
    private BigInteger suppierId ;
    /** 油漆供应商名称 */
    @ApiModelProperty(name = "油漆供应商名称",notes = "")
    private String suppierName ;
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
    /** 油漆名称 */
    public String getPlantName(){
        return this.plantName;
    }
    /** 油漆名称 */
    public void setPlantName(String plantName){
        this.plantName=plantName;
    }
    /** 油漆编码 */
    public String getPlantCode(){
        return this.plantCode;
    }
    /** 油漆编码 */
    public void setPlantCode(String plantCode){
        this.plantCode=plantCode;
    }
    /** 油漆类型id */
    public BigInteger getPlantTypeId(){
        return this.plantTypeId;
    }
    /** 油漆类型id */
    public void setPlantTypeId(BigInteger plantTypeId){
        this.plantTypeId=plantTypeId;
    }
    /** 油漆类型名称 */
    public String getPlantTypeName(){
        return this.plantTypeName;
    }
    /** 油漆类型名称 */
    public void setPlantTypeName(String plantTypeName){
        this.plantTypeName=plantTypeName;
    }
    /** 油漆供应商id */
    public BigInteger getSuppierId(){
        return this.suppierId;
    }
    /** 油漆供应商id */
    public void setSuppierId(BigInteger suppierId){
        this.suppierId=suppierId;
    }
    /** 油漆供应商名称 */
    public String getSuppierName(){
        return this.suppierName;
    }
    /** 油漆供应商名称 */
    public void setSuppierName(String suppierName){
        this.suppierName=suppierName;
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
            .append("plantName", getPlantName())
            .append("plantCode", getPlantCode())
            .append("plantTypeId", getPlantTypeId())
            .append("plantTypeName", getPlantTypeName())
            .append("suppierId", getSuppierId())
            .append("suppierName", getSuppierName())
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