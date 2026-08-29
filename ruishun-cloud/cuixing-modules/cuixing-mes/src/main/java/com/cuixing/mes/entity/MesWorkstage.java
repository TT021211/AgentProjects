package com.cuixing.mes.entity;

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
import com.cuixing.common.core.annotation.Excel;

/**
 * 工序表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-4-16
 */
@ApiModel(value = "工序表",description = "")
public class MesWorkstage extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    @Excel(name = "自增主键", cellType = Excel.ColumnType.NUMERIC, prompt = "自增主键")
    private BigInteger id;
    /** 工序编码 */
    @ApiModelProperty(name = "工序编码",notes = "")
    @Excel(name = "工序编码")
    private String workstageCode;
    /** 工序名称 */
    @ApiModelProperty(name = "工序名称",notes = "")
    @Excel(name = "工序名称")
    private String workstageName;
    /** 父工序id */
    @ApiModelProperty(name = "父工序id",notes = "")
    @Excel(name = "父工序id")
    private BigInteger parentWorkstageId;
    /** 父工序编码 */
    @ApiModelProperty(name = "父工序编码",notes = "")
    @Excel(name = "父工序编码")
    private String parentWorkstageCode;
    /** 父工序名称 */
    @ApiModelProperty(name = "父工序名称",notes = "")
    @Excel(name = "父工序名称")
    private String parentWorkstageName;
    /** 是否启用 */
    @ApiModelProperty(name = "是否启用",notes = "")
    @Excel(name = "是否启用")
    private Integer isEnable;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    @Excel(name = "备注")
    private String remark;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁",notes = "")
    @Excel(name = "乐观锁")
    private Integer version;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除",notes = "")
    @Excel(name = "逻辑删除")
    private Integer isDeleted;
    /** 创建人 */
    @ApiModelProperty(name = "创建人",notes = "")
    @Excel(name = "创建人")
    private String createBy;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 更新人 */
    @ApiModelProperty(name = "更新人",notes = "")
    @Excel(name = "更新人")
    private String updateBy;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1",notes = "")
    @Excel(name = "预留字段1")
    private String attr1;
    /** 预留字段2 */
    @ApiModelProperty(name = "预留字段2",notes = "")
    @Excel(name = "预留字段2")
    private Integer attr2;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3",notes = "")
    @Excel(name = "预留字段3")
    private String attr3;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 工序编码 */
    public String getWorkstageCode(){
        return this.workstageCode;
    }
    /** 工序编码 */
    public void setWorkstageCode(String workstageCode){
        this.workstageCode=workstageCode;
    }
    /** 工序名称 */
    public String getWorkstageName(){
        return this.workstageName;
    }
    /** 工序名称 */
    public void setWorkstageName(String workstageName){
        this.workstageName=workstageName;
    }
    /** 父工序id */
    public BigInteger getParentWorkstageId(){
        return this.parentWorkstageId;
    }
    /** 父工序id */
    public void setParentWorkstageId(BigInteger parentWorkstageId){
        this.parentWorkstageId=parentWorkstageId;
    }
    /** 父工序编码 */
    public String getParentWorkstageCode(){
        return this.parentWorkstageCode;
    }
    /** 父工序编码 */
    public void setParentWorkstageCode(String parentWorkstageCode){
        this.parentWorkstageCode=parentWorkstageCode;
    }
    /** 父工序名称 */
    public String getParentWorkstageName(){
        return this.parentWorkstageName;
    }
    /** 父工序名称 */
    public void setParentWorkstageName(String parentWorkstageName){
        this.parentWorkstageName=parentWorkstageName;
    }
    /** 是否启用 */
    public Integer getIsEnable(){
        return this.isEnable;
    }
    /** 是否启用 */
    public void setIsEnable(Integer isEnable){
        this.isEnable=isEnable;
    }
    /** 备注 */
    public String getRemark(){
        return this.remark;
    }
    /** 备注 */
    public void setRemark(String remark){
        this.remark=remark;
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
                .append("workstageCode", getWorkstageCode())
                .append("workstageName", getWorkstageName())
                .append("parentWorkstageId", getParentWorkstageId())
                .append("parentWorkstageCode", getParentWorkstageCode())
                .append("parentWorkstageName", getParentWorkstageName())
                .append("isEnable", getIsEnable())
                .append("remark", getRemark())
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