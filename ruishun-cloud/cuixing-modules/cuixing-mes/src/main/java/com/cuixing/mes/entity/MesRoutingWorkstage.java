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

/**
 * 工艺路线与工序关联表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-4-12
 */
@ApiModel(value = "工艺路线与工序关联表",description = "")
public class MesRoutingWorkstage extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 工艺路线id */
    @ApiModelProperty(name = "工艺路线id",notes = "")
    private BigInteger routingId ;
    /** 工艺路线名称 */
    @ApiModelProperty(name = "工艺路线名称",notes = "")
    private String routingName ;
    /** 工艺路线编码 */
    @ApiModelProperty(name = "工艺路线编码",notes = "")
    private String routingCode ;
    /** 工序id */
    @ApiModelProperty(name = "工序id",notes = "")
    private BigInteger workstageId ;
    /** 工序名称 */
    @ApiModelProperty(name = "工序名称",notes = "")
    private String workstageName ;
    /** 工序编码 */
    @ApiModelProperty(name = "工序编码",notes = "")
    private String workstageCode ;
    /** 工序排序 */
    @ApiModelProperty(name = "工序排序",notes = "")
    private Integer orderNum ;
    /** 排序标识(first、last) */
    @ApiModelProperty(name = "排序标识(first、last)",notes = "")
    private String orderMark ;
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

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 工艺路线id */
    public BigInteger getRoutingId(){
        return this.routingId;
    }
    /** 工艺路线id */
    public void setRoutingId(BigInteger routingId){
        this.routingId=routingId;
    }
    /** 工艺路线名称 */
    public String getRoutingName(){
        return this.routingName;
    }
    /** 工艺路线名称 */
    public void setRoutingName(String routingName){
        this.routingName=routingName;
    }
    /** 工艺路线编码 */
    public String getRoutingCode(){
        return this.routingCode;
    }
    /** 工艺路线编码 */
    public void setRoutingCode(String routingCode){
        this.routingCode=routingCode;
    }
    /** 工序id */
    public BigInteger getWorkstageId(){
        return this.workstageId;
    }
    /** 工序id */
    public void setWorkstageId(BigInteger workstageId){
        this.workstageId=workstageId;
    }
    /** 工序名称 */
    public String getWorkstageName(){
        return this.workstageName;
    }
    /** 工序名称 */
    public void setWorkstageName(String workstageName){
        this.workstageName=workstageName;
    }
    /** 工序编码 */
    public String getWorkstageCode(){
        return this.workstageCode;
    }
    /** 工序编码 */
    public void setWorkstageCode(String workstageCode){
        this.workstageCode=workstageCode;
    }
    /** 工序排序 */
    public Integer getOrderNum(){
        return this.orderNum;
    }
    /** 工序排序 */
    public void setOrderNum(Integer orderNum){
        this.orderNum=orderNum;
    }
    /** 排序标识(first、last) */
    public String getOrderMark(){
        return this.orderMark;
    }
    /** 排序标识(first、last) */
    public void setOrderMark(String orderMark){
        this.orderMark=orderMark;
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
                .append("routingId", getRoutingId())
                .append("routingName", getRoutingName())
                .append("routingCode", getRoutingCode())
                .append("workstageId", getWorkstageId())
                .append("workstageName", getWorkstageName())
                .append("workstageCode", getWorkstageCode())
                .append("orderNum", getOrderNum())
                .append("orderMark", getOrderMark())
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