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
 * 供应商表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-3-22
 */
@ApiModel(value = "供应商表",description = "")
public class MdVendor extends BaseEntity{
    /** 供应商ID */
    @ApiModelProperty(name = "供应商ID",notes = "")
    private BigInteger id ;
    /** 供应商编码 */
    @ApiModelProperty(name = "供应商编码",notes = "")
    private String vendorCode ;
    /** 供应商名称 */
    @ApiModelProperty(name = "供应商名称",notes = "")
    private String vendorName ;
    /** 供应商简称 */
    @ApiModelProperty(name = "供应商简称",notes = "")
    private String vendorNick ;
    /** 供应商英文名称 */
    @ApiModelProperty(name = "供应商英文名称",notes = "")
    private String vendorEn ;
    /** 供应商简介 */
    @ApiModelProperty(name = "供应商简介",notes = "")
    private String vendorDes ;
    /** 供应商LOGO地址 */
    @ApiModelProperty(name = "供应商LOGO地址",notes = "")
    private String vendorLogo ;
    /** 供应商等级 */
    @ApiModelProperty(name = "供应商等级",notes = "")
    private String vendorLevel ;
    /** 供应商评分 */
    @ApiModelProperty(name = "供应商评分",notes = "")
    private Integer vendorScore ;
    /** 供应商地址 */
    @ApiModelProperty(name = "供应商地址",notes = "")
    private String address ;
    /** 供应商官网地址 */
    @ApiModelProperty(name = "供应商官网地址",notes = "")
    private String website ;
    /** 供应商邮箱地址 */
    @ApiModelProperty(name = "供应商邮箱地址",notes = "")
    private String email ;
    /** 供应商电话 */
    @ApiModelProperty(name = "供应商电话",notes = "")
    private String tel ;
    /** 联系人1 */
    @ApiModelProperty(name = "联系人1",notes = "")
    private String contact1 ;
    /** 联系人1-电话 */
    @ApiModelProperty(name = "联系人1-电话",notes = "")
    private String contact1Tel ;
    /** 联系人1-邮箱 */
    @ApiModelProperty(name = "联系人1-邮箱",notes = "")
    private String contact1Email ;
    /** 联系人2 */
    @ApiModelProperty(name = "联系人2",notes = "")
    private String contact2 ;
    /** 联系人2-电话 */
    @ApiModelProperty(name = "联系人2-电话",notes = "")
    private String contact2Tel ;
    /** 联系人2-邮箱 */
    @ApiModelProperty(name = "联系人2-邮箱",notes = "")
    private String contact2Email ;
    /** 统一社会信用代码 */
    @ApiModelProperty(name = "统一社会信用代码",notes = "")
    private String creditCode ;
    /** 是否启用 */
    @ApiModelProperty(name = "是否启用",notes = "")
    private Integer isEnabled ;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1",notes = "")
    private String attr1 ;
    /** 预留字段2 */
    @ApiModelProperty(name = "预留字段2",notes = "")
    private Integer attr2 ;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3",notes = "")
    private String attr3 ;
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
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁",notes = "")
    private Integer version ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除",notes = "")
    private Integer isDeleted ;
    /** 同步erp */
    @ApiModelProperty(name = "同步erp",notes = "")
    private String erpJson ;

    /** 供应商ID */
    public BigInteger getId(){
        return this.id;
    }
    /** 供应商ID */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 供应商编码 */
    public String getVendorCode(){
        return this.vendorCode;
    }
    /** 供应商编码 */
    public void setVendorCode(String vendorCode){
        this.vendorCode=vendorCode;
    }
    /** 供应商名称 */
    public String getVendorName(){
        return this.vendorName;
    }
    /** 供应商名称 */
    public void setVendorName(String vendorName){
        this.vendorName=vendorName;
    }
    /** 供应商简称 */
    public String getVendorNick(){
        return this.vendorNick;
    }
    /** 供应商简称 */
    public void setVendorNick(String vendorNick){
        this.vendorNick=vendorNick;
    }
    /** 供应商英文名称 */
    public String getVendorEn(){
        return this.vendorEn;
    }
    /** 供应商英文名称 */
    public void setVendorEn(String vendorEn){
        this.vendorEn=vendorEn;
    }
    /** 供应商简介 */
    public String getVendorDes(){
        return this.vendorDes;
    }
    /** 供应商简介 */
    public void setVendorDes(String vendorDes){
        this.vendorDes=vendorDes;
    }
    /** 供应商LOGO地址 */
    public String getVendorLogo(){
        return this.vendorLogo;
    }
    /** 供应商LOGO地址 */
    public void setVendorLogo(String vendorLogo){
        this.vendorLogo=vendorLogo;
    }
    /** 供应商等级 */
    public String getVendorLevel(){
        return this.vendorLevel;
    }
    /** 供应商等级 */
    public void setVendorLevel(String vendorLevel){
        this.vendorLevel=vendorLevel;
    }
    /** 供应商评分 */
    public Integer getVendorScore(){
        return this.vendorScore;
    }
    /** 供应商评分 */
    public void setVendorScore(Integer vendorScore){
        this.vendorScore=vendorScore;
    }
    /** 供应商地址 */
    public String getAddress(){
        return this.address;
    }
    /** 供应商地址 */
    public void setAddress(String address){
        this.address=address;
    }
    /** 供应商官网地址 */
    public String getWebsite(){
        return this.website;
    }
    /** 供应商官网地址 */
    public void setWebsite(String website){
        this.website=website;
    }
    /** 供应商邮箱地址 */
    public String getEmail(){
        return this.email;
    }
    /** 供应商邮箱地址 */
    public void setEmail(String email){
        this.email=email;
    }
    /** 供应商电话 */
    public String getTel(){
        return this.tel;
    }
    /** 供应商电话 */
    public void setTel(String tel){
        this.tel=tel;
    }
    /** 联系人1 */
    public String getContact1(){
        return this.contact1;
    }
    /** 联系人1 */
    public void setContact1(String contact1){
        this.contact1=contact1;
    }
    /** 联系人1-电话 */
    public String getContact1Tel(){
        return this.contact1Tel;
    }
    /** 联系人1-电话 */
    public void setContact1Tel(String contact1Tel){
        this.contact1Tel=contact1Tel;
    }
    /** 联系人1-邮箱 */
    public String getContact1Email(){
        return this.contact1Email;
    }
    /** 联系人1-邮箱 */
    public void setContact1Email(String contact1Email){
        this.contact1Email=contact1Email;
    }
    /** 联系人2 */
    public String getContact2(){
        return this.contact2;
    }
    /** 联系人2 */
    public void setContact2(String contact2){
        this.contact2=contact2;
    }
    /** 联系人2-电话 */
    public String getContact2Tel(){
        return this.contact2Tel;
    }
    /** 联系人2-电话 */
    public void setContact2Tel(String contact2Tel){
        this.contact2Tel=contact2Tel;
    }
    /** 联系人2-邮箱 */
    public String getContact2Email(){
        return this.contact2Email;
    }
    /** 联系人2-邮箱 */
    public void setContact2Email(String contact2Email){
        this.contact2Email=contact2Email;
    }
    /** 统一社会信用代码 */
    public String getCreditCode(){
        return this.creditCode;
    }
    /** 统一社会信用代码 */
    public void setCreditCode(String creditCode){
        this.creditCode=creditCode;
    }
    /** 是否启用 */
    public Integer getIsEnabled(){
        return this.isEnabled;
    }
    /** 是否启用 */
    public void setIsEnabled(Integer isEnabled){
        this.isEnabled=isEnabled;
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
    /** 同步erp */
    public String getErpJson(){
        return this.erpJson;
    }
    /** 同步erp */
    public void setErpJson(String erpJson){
        this.erpJson=erpJson;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("vendorCode", getVendorCode())
                .append("vendorName", getVendorName())
                .append("vendorNick", getVendorNick())
                .append("vendorEn", getVendorEn())
                .append("vendorDes", getVendorDes())
                .append("vendorLogo", getVendorLogo())
                .append("vendorLevel", getVendorLevel())
                .append("vendorScore", getVendorScore())
                .append("address", getAddress())
                .append("website", getWebsite())
                .append("email", getEmail())
                .append("tel", getTel())
                .append("contact1", getContact1())
                .append("contact1Tel", getContact1Tel())
                .append("contact1Email", getContact1Email())
                .append("contact2", getContact2())
                .append("contact2Tel", getContact2Tel())
                .append("contact2Email", getContact2Email())
                .append("creditCode", getCreditCode())
                .append("isEnabled", getIsEnabled())
                .append("attr1", getAttr1())
                .append("attr2", getAttr2())
                .append("attr3", getAttr3())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("version", getVersion())
                .append("isDeleted", getIsDeleted())
                .append("erpJson", getErpJson())
                .toString();
    }
}