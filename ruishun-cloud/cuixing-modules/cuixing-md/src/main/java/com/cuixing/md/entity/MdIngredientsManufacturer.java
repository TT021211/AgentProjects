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
 * 材料厂家;
 * @author : http://www.chiner.pro
 * @date : 2024-6-4
 */
@ApiModel(value = "材料厂家",description = "")
public class MdIngredientsManufacturer extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 厂家名称 */
    @ApiModelProperty(name = "厂家名称",notes = "")
    private String manufacturerName ;
    /** 厂家简称 */
    @ApiModelProperty(name = "厂家简称",notes = "")
    private String manufacturerNick ;
    /** 厂家编码 */
    @ApiModelProperty(name = "厂家编码",notes = "")
    private String manufacturerCode ;
    /** 厂家地址 */
    @ApiModelProperty(name = "厂家地址",notes = "")
    private String manufacturerAddress ;
    /** 联系人 */
    @ApiModelProperty(name = "联系人",notes = "")
    private String person ;
    /** 联系电话 */
    @ApiModelProperty(name = "联系电话",notes = "")
    private String phone ;
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
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 厂家名称 */
    public String getManufacturerName(){
        return this.manufacturerName;
    }
    /** 厂家名称 */
    public void setManufacturerName(String manufacturerName){
        this.manufacturerName=manufacturerName;
    }
    /** 厂家简称 */
    public String getManufacturerNick(){
        return this.manufacturerNick;
    }
    /** 厂家简称 */
    public void setManufacturerNick(String manufacturerNick){
        this.manufacturerNick=manufacturerNick;
    }
    /** 厂家编码 */
    public String getManufacturerCode(){
        return this.manufacturerCode;
    }
    /** 厂家编码 */
    public void setManufacturerCode(String manufacturerCode){
        this.manufacturerCode=manufacturerCode;
    }
    /** 厂家地址 */
    public String getManufacturerAddress(){
        return this.manufacturerAddress;
    }
    /** 厂家地址 */
    public void setManufacturerAddress(String manufacturerAddress){
        this.manufacturerAddress=manufacturerAddress;
    }
    /** 联系人 */
    public String getPerson(){
        return this.person;
    }
    /** 联系人 */
    public void setPerson(String person){
        this.person=person;
    }
    /** 联系电话 */
    public String getPhone(){
        return this.phone;
    }
    /** 联系电话 */
    public void setPhone(String phone){
        this.phone=phone;
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
    /** 备注 */
    public String getRemark(){
        return this.remark;
    }
    /** 备注 */
    public void setRemark(String remark){
        this.remark=remark;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("manufacturerName", getManufacturerName())
            .append("manufacturerNick", getManufacturerNick())
            .append("manufacturerCode", getManufacturerCode())
            .append("manufacturerAddress", getManufacturerAddress())
            .append("person", getPerson())
            .append("phone", getPhone())
            .append("isDeleted", getIsDeleted())
            .append("version", getVersion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("remark", getRemark())
        .toString();
    }
}