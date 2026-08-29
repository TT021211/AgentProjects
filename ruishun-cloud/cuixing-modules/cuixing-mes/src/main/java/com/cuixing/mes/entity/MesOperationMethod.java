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
 * 操作规范/作业指导书;
 * @author : http://www.chiner.pro
 * @date : 2024-6-27
 */
@ApiModel(value = "操作规范(作业指导书)",description = "")
public class MesOperationMethod extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 操作类型(0：作业指导书 1：操作规范) */
    @ApiModelProperty(name = "操作类型(0：作业指导书 1：操作规范)",notes = "")
    private String operationType ;
    /** 作业指导书名称 */
    @ApiModelProperty(name = "作业指导书名称",notes = "")
    private String operationName ;
    /** 作业指导书编码 */
    @ApiModelProperty(name = "作业指导书编码",notes = "")
    private String operationCode ;
    /** 关联漆包机机型id */
    @ApiModelProperty(name = "关联漆包机机型id",notes = "")
    private BigInteger enanellingWorkbenchId ;
    /** 关联漆包机机型名称 */
    @ApiModelProperty(name = "关联漆包机机型名称",notes = "")
    private String enanellingWorkbenchName ;
    /** 生效日期 */
    @ApiModelProperty(name = "生效日期",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ableTime ;
    /** 图片 */
    @ApiModelProperty(name = "图片",notes = "")
    private String image ;
    /** html路径 */
    @ApiModelProperty(name = "html路径",notes = "")
    private String filePath ;
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
    /** 操作类型(0：作业指导书 1：操作规范) */
    public String getOperationType(){
        return this.operationType;
    }
    /** 操作类型(0：作业指导书 1：操作规范) */
    public void setOperationType(String operationType){
        this.operationType=operationType;
    }
    /** 作业指导书名称 */
    public String getOperationName(){
        return this.operationName;
    }
    /** 作业指导书名称 */
    public void setOperationName(String operationName){
        this.operationName=operationName;
    }
    /** 作业指导书编码 */
    public String getOperationCode(){
        return this.operationCode;
    }
    /** 作业指导书编码 */
    public void setOperationCode(String operationCode){
        this.operationCode=operationCode;
    }
    /** 关联漆包机机型id */
    public BigInteger getEnanellingWorkbenchId(){
        return this.enanellingWorkbenchId;
    }
    /** 关联漆包机机型id */
    public void setEnanellingWorkbenchId(BigInteger enanellingWorkbenchId){
        this.enanellingWorkbenchId=enanellingWorkbenchId;
    }
    /** 关联漆包机机型名称 */
    public String getEnanellingWorkbenchName(){
        return this.enanellingWorkbenchName;
    }
    /** 关联漆包机机型名称 */
    public void setEnanellingWorkbenchName(String enanellingWorkbenchName){
        this.enanellingWorkbenchName=enanellingWorkbenchName;
    }
    /** 生效日期 */
    public Date getAbleTime(){
        return this.ableTime;
    }
    /** 生效日期 */
    public void setAbleTime(Date ableTime){
        this.ableTime=ableTime;
    }
    /** 图片 */
    public String getImage(){
        return this.image;
    }
    /** 图片 */
    public void setImage(String image){
        this.image=image;
    }
    /** html路径 */
    public String getFilePath(){
        return this.filePath;
    }
    /** html路径 */
    public void setFilePath(String filePath){
        this.filePath=filePath;
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
            .append("operationType", getOperationType())
            .append("operationName", getOperationName())
            .append("operationCode", getOperationCode())
            .append("enanellingWorkbenchId", getEnanellingWorkbenchId())
            .append("enanellingWorkbenchName", getEnanellingWorkbenchName())
            .append("ableTime", getAbleTime())
            .append("image", getImage())
            .append("filePath", getFilePath())
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