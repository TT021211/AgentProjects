package com.cuixing.eq.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 项目类型详情;
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@ApiModel(value = "项目类型详情")
public class EqItemTypeDetail extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键")
    private BigInteger id ;
    /** 项目类型id */
    @ApiModelProperty(name = "项目类型id")
    private BigInteger itemTypeId ;
    /** 项目名称 */
    @ApiModelProperty(name = "项目名称")
    private String itemTypeName ;
    /** 项目id */
    @ApiModelProperty(name = "项目id")
    private BigInteger itemId ;
    /** 维护项名称 */
    @ApiModelProperty(name = "维护项名称")
    private String itemName ;
    /** 项目内容 */
    @ApiModelProperty(name = "项目内容")
    private String itemContent ;
    /** 完成标准 */
    @ApiModelProperty(name = "完成标准")
    private String completionStandard ;
    /** 备注 */
    @ApiModelProperty(name = "备注")
    private String remark ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除")
    private Integer isDeleted ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁")
    private Integer version ;
    /** 创建人 */
    @ApiModelProperty(name = "创建人")
    private String createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;
    /** 更新人 */
    @ApiModelProperty(name = "更新人")
    private String updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1")
    private String attr1 ;
    /** 预留字段2 */
    @ApiModelProperty(name = "预留字段2")
    private Integer attr2 ;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3")
    private String attr3 ;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 项目类型id */
    public BigInteger getItemTypeId(){
        return this.itemTypeId;
    }
    /** 项目类型id */
    public void setItemTypeId(BigInteger itemTypeId){
        this.itemTypeId=itemTypeId;
    }
    /** 项目名称 */
    public String getItemTypeName(){
        return this.itemTypeName;
    }
    /** 项目名称 */
    public void setItemTypeName(String itemTypeName){
        this.itemTypeName=itemTypeName;
    }
    /** 项目id */
    public BigInteger getItemId(){
        return this.itemId;
    }
    /** 项目id */
    public void setItemId(BigInteger itemId){
        this.itemId=itemId;
    }
    /** 项目名称 */
    public String getItemName(){
        return this.itemName;
    }
    /** 项目名称 */
    public void setItemName(String itemName){
        this.itemName=itemName;
    }
    /** 项目内容 */
    public String getItemContent(){
        return this.itemContent;
    }
    /** 项目内容 */
    public void setItemContent(String itemContent){
        this.itemContent=itemContent;
    }
    /** 完成标准 */
    public String getCompletionStandard(){
        return this.completionStandard;
    }
    /** 完成标准 */
    public void setCompletionStandard(String completionStandard){
        this.completionStandard=completionStandard;
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
            .append("itemTypeId", getItemTypeId())
            .append("itemTypeName", getItemTypeName())
            .append("itemId", getItemId())
            .append("itemName", getItemName())
            .append("itemContent", getItemContent())
            .append("completionStandard", getCompletionStandard())
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