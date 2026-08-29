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
 * 物料分类表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-3-20
 */
@ApiModel(value = "物料分类表",description = "")
public class MdItemType extends BaseEntity{
    /** 物料类型ID */
    @ApiModelProperty(name = "物料类型ID",notes = "")
    private BigInteger id ;
    /** 物料类型编码 */
    @ApiModelProperty(name = "物料类型编码",notes = "")
    private String itemTypeCode ;
    /** 物料类型名称 */
    @ApiModelProperty(name = "物料类型名称",notes = "")
    private String itemTypeName ;
    /** 父类型ID */
    @ApiModelProperty(name = "父类型ID",notes = "")
    private BigInteger parentTypeId ;
    /** 所有层级父节点 */
    @ApiModelProperty(name = "所有层级父节点",notes = "")
    private String ancestors ;
    /** 排列顺序 */
    @ApiModelProperty(name = "排列顺序",notes = "")
    private Integer orderNum ;
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

    /** 物料类型ID */
    public BigInteger getId(){
        return this.id;
    }
    /** 物料类型ID */
    public void setId(BigInteger id){
        this.id=id;
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
    /** 父类型ID */
    public BigInteger getParentTypeId(){
        return this.parentTypeId;
    }
    /** 父类型ID */
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
    /** 排列顺序 */
    public Integer getOrderNum(){
        return this.orderNum;
    }
    /** 排列顺序 */
    public void setOrderNum(Integer orderNum){
        this.orderNum=orderNum;
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
                .append("itemTypeCode", getItemTypeCode())
                .append("itemTypeName", getItemTypeName())
                .append("parentTypeId", getParentTypeId())
                .append("ancestors", getAncestors())
                .append("orderNum", getOrderNum())
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