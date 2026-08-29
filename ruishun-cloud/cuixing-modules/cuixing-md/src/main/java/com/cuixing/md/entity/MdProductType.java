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
 * 产品分类表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-3-20
 */
@ApiModel(value = "产品分类表",description = "")
public class MdProductType extends BaseEntity{
    /** 分类id */
    @ApiModelProperty(name = "分类id",notes = "")
    private BigInteger id ;
    /** 分类名称 */
    @ApiModelProperty(name = "分类名称",notes = "")
    private String typeName ;
    /** 分类编码 */
    @ApiModelProperty(name = "分类编码",notes = "")
    private String typeCode ;
    /** 父分类id */
    @ApiModelProperty(name = "父分类id",notes = "")
    private BigInteger parentTypeId ;
    /** 所有层级父节点 */
    @ApiModelProperty(name = "所有层级父节点",notes = "")
    private String ancestors ;
    /** 是否启用 */
    @ApiModelProperty(name = "是否启用",notes = "")
    private Integer ifEnable ;
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

    /** 分类id */
    public BigInteger getId(){
        return this.id;
    }
    /** 分类id */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 分类名称 */
    public String getTypeName(){
        return this.typeName;
    }
    /** 分类名称 */
    public void setTypeName(String typeName){
        this.typeName=typeName;
    }
    /** 分类编码 */
    public String getTypeCode(){
        return this.typeCode;
    }
    /** 分类编码 */
    public void setTypeCode(String typeCode){
        this.typeCode=typeCode;
    }
    /** 父分类id */
    public BigInteger getParentTypeId(){
        return this.parentTypeId;
    }
    /** 父分类id */
    public void setParentTypeId(BigInteger parentTypeId){
        this.parentTypeId=parentTypeId;
    }
    /** 所有层级父节点 */
    public String getAncestors(){
        return this.ancestors;
    }
    /** 所有层级父节点 */
    public void setAncestors(String ancestors){
        this.ancestors=ancestors;
    }
    /** 是否启用 */
    public Integer getIfEnable(){
        return this.ifEnable;
    }
    /** 是否启用 */
    public void setIfEnable(Integer ifEnable){
        this.ifEnable=ifEnable;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("typeName", getTypeName())
                .append("typeCode", getTypeCode())
                .append("parentTypeId", getParentTypeId())
                .append("ancestors", getAncestors())
                .append("ifEnable", getIfEnable())
                .append("version", getVersion())
                .append("isDeleted", getIsDeleted())
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