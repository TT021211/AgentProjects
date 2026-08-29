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
 * 班组表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-3-20
 */
@ApiModel(value = "班组表",description = "")
public class MdWorkgroup extends BaseEntity{
    /** 班组id */
    @ApiModelProperty(name = "班组id",notes = "")
    private BigInteger id ;
    /** 班组编码 */
    @ApiModelProperty(name = "班组编码",notes = "")
    private String workgroupCode ;
    /** 班组名称 */
    @ApiModelProperty(name = "班组名称",notes = "")
    private String workgroupName ;
    /** 车间id */
    @ApiModelProperty(name = "车间id",notes = "")
    private BigInteger workshopId ;
    /** 车间名称 */
    @ApiModelProperty(name = "车间名称",notes = "")
    private String workshopName ;
    /** 工序ID */
    @ApiModelProperty(name = "工序ID",notes = "")
    private BigInteger workstageId ;
    /** 工序名称 */
    @ApiModelProperty(name = "工序名称",notes = "")
    private String workstageName ;
    /** 负责人id */
    @ApiModelProperty(name = "负责人id",notes = "")
    private BigInteger headId ;
    /** 负责人姓名 */
    @ApiModelProperty(name = "负责人姓名",notes = "")
    private String headName ;
    /** 班组人数 */
    @ApiModelProperty(name = "班组人数",notes = "")
    private Integer totalMembers ;
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

    /** 班组id */
    public BigInteger getId(){
        return this.id;
    }
    /** 班组id */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 班组编码 */
    public String getWorkgroupCode(){
        return this.workgroupCode;
    }
    /** 班组编码 */
    public void setWorkgroupCode(String workgroupCode){
        this.workgroupCode=workgroupCode;
    }
    /** 班组名称 */
    public String getWorkgroupName(){
        return this.workgroupName;
    }
    /** 班组名称 */
    public void setWorkgroupName(String workgroupName){
        this.workgroupName=workgroupName;
    }
    /** 车间id */
    public BigInteger getWorkshopId(){
        return this.workshopId;
    }
    /** 车间id */
    public void setWorkshopId(BigInteger workshopId){
        this.workshopId=workshopId;
    }
    /** 车间名称 */
    public String getWorkshopName(){
        return this.workshopName;
    }
    /** 车间名称 */
    public void setWorkshopName(String workshopName){
        this.workshopName=workshopName;
    }
    /** 工序ID */
    public BigInteger getWorkstageId(){
        return this.workstageId;
    }
    /** 工序ID */
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
    /** 负责人id */
    public BigInteger getHeadId(){
        return this.headId;
    }
    /** 负责人id */
    public void setHeadId(BigInteger headId){
        this.headId=headId;
    }
    /** 负责人姓名 */
    public String getHeadName(){
        return this.headName;
    }
    /** 负责人姓名 */
    public void setHeadName(String headName){
        this.headName=headName;
    }
    /** 班组人数 */
    public Integer getTotalMembers(){
        return this.totalMembers;
    }
    /** 班组人数 */
    public void setTotalMembers(Integer totalMembers){
        this.totalMembers=totalMembers;
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
                .append("workgroupCode", getWorkgroupCode())
                .append("workgroupName", getWorkgroupName())
                .append("workshopId", getWorkshopId())
                .append("workshopName", getWorkshopName())
                .append("workstageId", getWorkstageId())
                .append("workstageName", getWorkstageName())
                .append("headId", getHeadId())
                .append("headName", getHeadName())
                .append("totalMembers", getTotalMembers())
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