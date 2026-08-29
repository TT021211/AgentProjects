package com.cuixing.system.api.domain.md;

import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigInteger;
import java.util.Date;

/**
 * 客户表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-3-22
 */
@ApiModel(value = "客户表",description = "")
public class MdClient extends BaseEntity{
    /** 客户ID */
    @ApiModelProperty(name = "客户ID",notes = "")
    private BigInteger id ;
    /** 客户编码 */
    @ApiModelProperty(name = "客户编码",notes = "")
    private String clientCode ;
    /** 客户名称 */
    @ApiModelProperty(name = "客户名称",notes = "")
    private String clientName ;
    /** 客户简称 */
    @ApiModelProperty(name = "客户简称",notes = "")
    private String clientNick ;
    /** 客户英文名称 */
    @ApiModelProperty(name = "客户英文名称",notes = "")
    private String clientEn ;
    /** 客户简介 */
    @ApiModelProperty(name = "客户简介",notes = "")
    private String clientDes ;
    /** 客户LOGO地址 */
    @ApiModelProperty(name = "客户LOGO地址",notes = "")
    private String clientLogo ;
    /** 客户类型 */
    @ApiModelProperty(name = "客户类型",notes = "")
    private String clientType ;
    /** 客户地址 */
    @ApiModelProperty(name = "客户地址",notes = "")
    private String address ;
    /** 客户官网地址 */
    @ApiModelProperty(name = "客户官网地址",notes = "")
    private String website ;
    /** 客户邮箱地址 */
    @ApiModelProperty(name = "客户邮箱地址",notes = "")
    private String email ;
    /** 客户电话 */
    @ApiModelProperty(name = "客户电话",notes = "")
    private String tel ;
    /** 开户银行名称 */
    @ApiModelProperty(name = "开户银行名称",notes = "")
    private String bankName ;
    /** 开户银行账号 */
    @ApiModelProperty(name = "开户银行账号",notes = "")
    private String bankAccount ;
    /** 法人姓名 */
    @ApiModelProperty(name = "法人姓名",notes = "")
    private String legalPersonName ;
    /** 法人身份证号 */
    @ApiModelProperty(name = "法人身份证号",notes = "")
    private String legalPersonIdcard ;
    /** 法人联系电话 */
    @ApiModelProperty(name = "法人联系电话",notes = "")
    private String legalPersonPhone ;
    /** 联系人1 */
    @ApiModelProperty(name = "联系人1",notes = "")
    private String contact1 ;
    /** 联系人1-电话 */
    @ApiModelProperty(name = "联系人1-电话",notes = "")
    private String contact1Tel ;
    /** 联系人1-邮箱 */
    @ApiModelProperty(name = "联系人1-邮箱",notes = "")
    private String contact1Email ;
    /** 联系人1-职务 */
    @ApiModelProperty(name = "联系人1-职务",notes = "")
    private String contact1Title ;
    /** 联系人1-手机 */
    @ApiModelProperty(name = "联系人1-手机",notes = "")
    private String contact1Phone ;
    /** 联系人1-传真 */
    @ApiModelProperty(name = "联系人1-传真",notes = "")
    private String contact1Fax ;
    /** 联系人2 */
    @ApiModelProperty(name = "联系人2",notes = "")
    private String contact2 ;
    /** 联系人2-电话 */
    @ApiModelProperty(name = "联系人2-电话",notes = "")
    private String contact2Tel ;
    /** 联系人2-邮箱 */
    @ApiModelProperty(name = "联系人2-邮箱",notes = "")
    private String contact2Email ;
    /** 联系人2-职务 */
    @ApiModelProperty(name = "联系人2-职务",notes = "")
    private String contact2Title ;
    /** 联系人2-手机 */
    @ApiModelProperty(name = "联系人2-手机",notes = "")
    private String contact2Phone ;
    /** 联系人2-传真 */
    @ApiModelProperty(name = "联系人2-传真",notes = "")
    private String contact2Fax ;
    /** 成立时间 */
    @ApiModelProperty(name = "成立时间",notes = "")
    private String foundingTime ;
    /** 注册资金 */
    @ApiModelProperty(name = "注册资金",notes = "")
    private String registeredCapital ;
    /** 纳税人类型1一般2小规模 */
    @ApiModelProperty(name = "纳税人类型1一般2小规模",notes = "")
    private Integer taxpayerType ;
    /** 统一社会信用代码 */
    @ApiModelProperty(name = "统一社会信用代码",notes = "")
    private String creditCode ;
    /** 经营范围 */
    @ApiModelProperty(name = "经营范围",notes = "")
    private String businessScope ;
    /** 客户类型0终端1经销商 */
    @ApiModelProperty(name = "客户类型0终端1经销商",notes = "")
    private Integer ifDistributor ;
    /** 线索来源1电话来访2客户介绍3上门拜访 */
    @ApiModelProperty(name = "线索来源1电话来访2客户介绍3上门拜访",notes = "")
    private Integer leadSource ;
    /** 介绍的客户id */
    @ApiModelProperty(name = "介绍的客户id",notes = "")
    private BigInteger leadSourceId ;
    /** 介绍的客户名称 */
    @ApiModelProperty(name = "介绍的客户名称",notes = "")
    private String leadSourceName ;
    /** 价格模式(报价、固定、其他) */
    @ApiModelProperty(name = "价格模式(报价、固定、其他)",notes = "")
    private String pricingModel ;
    /** 需求产品 */
    @ApiModelProperty(name = "需求产品",notes = "")
    private String products ;
    /** 月需求量 */
    @ApiModelProperty(name = "月需求量",notes = "")
    private String productsNum ;
    /** 付款方式(-1款到发货0货到付款x到货x天付款) */
    @ApiModelProperty(name = "付款方式(-1款到发货0货到付款x到货x天付款)",notes = "")
    private Integer paymentMethod ;
    /** 产品要求 */
    @ApiModelProperty(name = "产品要求",notes = "")
    private String requirement ;
    /** 事业部审核人 */
    @ApiModelProperty(name = "事业部审核人",notes = "")
    private String reviewer1 ;
    /** 营销总监审核人 */
    @ApiModelProperty(name = "营销总监审核人",notes = "")
    private String reviewer2 ;
    /** 业务员id */
    @ApiModelProperty(name = "业务员id",notes = "")
    private BigInteger salesmanId ;
    /** 业务员姓名 */
    @ApiModelProperty(name = "业务员姓名",notes = "")
    private String salesmanName ;
    /** 业务员手机号 */
    @ApiModelProperty(name = "业务员手机号",notes = "")
    private String salesmanPhone ;
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

    /** 客户ID */
    public BigInteger getId(){
        return this.id;
    }
    /** 客户ID */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 客户编码 */
    public String getClientCode(){
        return this.clientCode;
    }
    /** 客户编码 */
    public void setClientCode(String clientCode){
        this.clientCode=clientCode;
    }
    /** 客户名称 */
    public String getClientName(){
        return this.clientName;
    }
    /** 客户名称 */
    public void setClientName(String clientName){
        this.clientName=clientName;
    }
    /** 客户简称 */
    public String getClientNick(){
        return this.clientNick;
    }
    /** 客户简称 */
    public void setClientNick(String clientNick){
        this.clientNick=clientNick;
    }
    /** 客户英文名称 */
    public String getClientEn(){
        return this.clientEn;
    }
    /** 客户英文名称 */
    public void setClientEn(String clientEn){
        this.clientEn=clientEn;
    }
    /** 客户简介 */
    public String getClientDes(){
        return this.clientDes;
    }
    /** 客户简介 */
    public void setClientDes(String clientDes){
        this.clientDes=clientDes;
    }
    /** 客户LOGO地址 */
    public String getClientLogo(){
        return this.clientLogo;
    }
    /** 客户LOGO地址 */
    public void setClientLogo(String clientLogo){
        this.clientLogo=clientLogo;
    }
    /** 客户类型 */
    public String getClientType(){
        return this.clientType;
    }
    /** 客户类型 */
    public void setClientType(String clientType){
        this.clientType=clientType;
    }
    /** 客户地址 */
    public String getAddress(){
        return this.address;
    }
    /** 客户地址 */
    public void setAddress(String address){
        this.address=address;
    }
    /** 客户官网地址 */
    public String getWebsite(){
        return this.website;
    }
    /** 客户官网地址 */
    public void setWebsite(String website){
        this.website=website;
    }
    /** 客户邮箱地址 */
    public String getEmail(){
        return this.email;
    }
    /** 客户邮箱地址 */
    public void setEmail(String email){
        this.email=email;
    }
    /** 客户电话 */
    public String getTel(){
        return this.tel;
    }
    /** 客户电话 */
    public void setTel(String tel){
        this.tel=tel;
    }
    /** 开户银行名称 */
    public String getBankName(){
        return this.bankName;
    }
    /** 开户银行名称 */
    public void setBankName(String bankName){
        this.bankName=bankName;
    }
    /** 开户银行账号 */
    public String getBankAccount(){
        return this.bankAccount;
    }
    /** 开户银行账号 */
    public void setBankAccount(String bankAccount){
        this.bankAccount=bankAccount;
    }
    /** 法人姓名 */
    public String getLegalPersonName(){
        return this.legalPersonName;
    }
    /** 法人姓名 */
    public void setLegalPersonName(String legalPersonName){
        this.legalPersonName=legalPersonName;
    }
    /** 法人身份证号 */
    public String getLegalPersonIdcard(){
        return this.legalPersonIdcard;
    }
    /** 法人身份证号 */
    public void setLegalPersonIdcard(String legalPersonIdcard){
        this.legalPersonIdcard=legalPersonIdcard;
    }
    /** 法人联系电话 */
    public String getLegalPersonPhone(){
        return this.legalPersonPhone;
    }
    /** 法人联系电话 */
    public void setLegalPersonPhone(String legalPersonPhone){
        this.legalPersonPhone=legalPersonPhone;
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
    /** 联系人1-职务 */
    public String getContact1Title(){
        return this.contact1Title;
    }
    /** 联系人1-职务 */
    public void setContact1Title(String contact1Title){
        this.contact1Title=contact1Title;
    }
    /** 联系人1-手机 */
    public String getContact1Phone(){
        return this.contact1Phone;
    }
    /** 联系人1-手机 */
    public void setContact1Phone(String contact1Phone){
        this.contact1Phone=contact1Phone;
    }
    /** 联系人1-传真 */
    public String getContact1Fax(){
        return this.contact1Fax;
    }
    /** 联系人1-传真 */
    public void setContact1Fax(String contact1Fax){
        this.contact1Fax=contact1Fax;
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
    /** 联系人2-职务 */
    public String getContact2Title(){
        return this.contact2Title;
    }
    /** 联系人2-职务 */
    public void setContact2Title(String contact2Title){
        this.contact2Title=contact2Title;
    }
    /** 联系人2-手机 */
    public String getContact2Phone(){
        return this.contact2Phone;
    }
    /** 联系人2-手机 */
    public void setContact2Phone(String contact2Phone){
        this.contact2Phone=contact2Phone;
    }
    /** 联系人2-传真 */
    public String getContact2Fax(){
        return this.contact2Fax;
    }
    /** 联系人2-传真 */
    public void setContact2Fax(String contact2Fax){
        this.contact2Fax=contact2Fax;
    }
    /** 成立时间 */
    public String getFoundingTime(){
        return this.foundingTime;
    }
    /** 成立时间 */
    public void setFoundingTime(String foundingTime){
        this.foundingTime=foundingTime;
    }
    /** 注册资金 */
    public String getRegisteredCapital(){
        return this.registeredCapital;
    }
    /** 注册资金 */
    public void setRegisteredCapital(String registeredCapital){
        this.registeredCapital=registeredCapital;
    }
    /** 纳税人类型1一般2小规模 */
    public Integer getTaxpayerType(){
        return this.taxpayerType;
    }
    /** 纳税人类型1一般2小规模 */
    public void setTaxpayerType(Integer taxpayerType){
        this.taxpayerType=taxpayerType;
    }
    /** 统一社会信用代码 */
    public String getCreditCode(){
        return this.creditCode;
    }
    /** 统一社会信用代码 */
    public void setCreditCode(String creditCode){
        this.creditCode=creditCode;
    }
    /** 经营范围 */
    public String getBusinessScope(){
        return this.businessScope;
    }
    /** 经营范围 */
    public void setBusinessScope(String businessScope){
        this.businessScope=businessScope;
    }
    /** 客户类型0终端1经销商 */
    public Integer getIfDistributor(){
        return this.ifDistributor;
    }
    /** 客户类型0终端1经销商 */
    public void setIfDistributor(Integer ifDistributor){
        this.ifDistributor=ifDistributor;
    }
    /** 线索来源1电话来访2客户介绍3上门拜访 */
    public Integer getLeadSource(){
        return this.leadSource;
    }
    /** 线索来源1电话来访2客户介绍3上门拜访 */
    public void setLeadSource(Integer leadSource){
        this.leadSource=leadSource;
    }
    /** 介绍的客户id */
    public BigInteger getLeadSourceId(){
        return this.leadSourceId;
    }
    /** 介绍的客户id */
    public void setLeadSourceId(BigInteger leadSourceId){
        this.leadSourceId=leadSourceId;
    }
    /** 介绍的客户名称 */
    public String getLeadSourceName(){
        return this.leadSourceName;
    }
    /** 介绍的客户名称 */
    public void setLeadSourceName(String leadSourceName){
        this.leadSourceName=leadSourceName;
    }
    /** 价格模式(报价、固定、其他) */
    public String getPricingModel(){
        return this.pricingModel;
    }
    /** 价格模式(报价、固定、其他) */
    public void setPricingModel(String pricingModel){
        this.pricingModel=pricingModel;
    }
    /** 需求产品 */
    public String getProducts(){
        return this.products;
    }
    /** 需求产品 */
    public void setProducts(String products){
        this.products=products;
    }
    /** 月需求量 */
    public String getProductsNum(){
        return this.productsNum;
    }
    /** 月需求量 */
    public void setProductsNum(String productsNum){
        this.productsNum=productsNum;
    }
    /** 付款方式(-1款到发货0货到付款x到货x天付款) */
    public Integer getPaymentMethod(){
        return this.paymentMethod;
    }
    /** 付款方式(-1款到发货0货到付款x到货x天付款) */
    public void setPaymentMethod(Integer paymentMethod){
        this.paymentMethod=paymentMethod;
    }
    /** 产品要求 */
    public String getRequirement(){
        return this.requirement;
    }
    /** 产品要求 */
    public void setRequirement(String requirement){
        this.requirement=requirement;
    }
    /** 事业部审核人 */
    public String getReviewer1(){
        return this.reviewer1;
    }
    /** 事业部审核人 */
    public void setReviewer1(String reviewer1){
        this.reviewer1=reviewer1;
    }
    /** 营销总监审核人 */
    public String getReviewer2(){
        return this.reviewer2;
    }
    /** 营销总监审核人 */
    public void setReviewer2(String reviewer2){
        this.reviewer2=reviewer2;
    }
    /** 业务员id */
    public BigInteger getSalesmanId(){
        return this.salesmanId;
    }
    /** 业务员id */
    public void setSalesmanId(BigInteger salesmanId){
        this.salesmanId=salesmanId;
    }
    /** 业务员姓名 */
    public String getSalesmanName(){
        return this.salesmanName;
    }
    /** 业务员姓名 */
    public void setSalesmanName(String salesmanName){
        this.salesmanName=salesmanName;
    }
    /** 业务员手机号 */
    public String getSalesmanPhone(){
        return this.salesmanPhone;
    }
    /** 业务员手机号 */
    public void setSalesmanPhone(String salesmanPhone){
        this.salesmanPhone=salesmanPhone;
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
                .append("clientCode", getClientCode())
                .append("clientName", getClientName())
                .append("clientNick", getClientNick())
                .append("clientEn", getClientEn())
                .append("clientDes", getClientDes())
                .append("clientLogo", getClientLogo())
                .append("clientType", getClientType())
                .append("address", getAddress())
                .append("website", getWebsite())
                .append("email", getEmail())
                .append("tel", getTel())
                .append("bankName", getBankName())
                .append("bankAccount", getBankAccount())
                .append("legalPersonName", getLegalPersonName())
                .append("legalPersonIdcard", getLegalPersonIdcard())
                .append("legalPersonPhone", getLegalPersonPhone())
                .append("contact1", getContact1())
                .append("contact1Tel", getContact1Tel())
                .append("contact1Email", getContact1Email())
                .append("contact1Title", getContact1Title())
                .append("contact1Phone", getContact1Phone())
                .append("contact1Fax", getContact1Fax())
                .append("contact2", getContact2())
                .append("contact2Tel", getContact2Tel())
                .append("contact2Email", getContact2Email())
                .append("contact2Title", getContact2Title())
                .append("contact2Phone", getContact2Phone())
                .append("contact2Fax", getContact2Fax())
                .append("foundingTime", getFoundingTime())
                .append("registeredCapital", getRegisteredCapital())
                .append("taxpayerType", getTaxpayerType())
                .append("creditCode", getCreditCode())
                .append("businessScope", getBusinessScope())
                .append("ifDistributor", getIfDistributor())
                .append("leadSource", getLeadSource())
                .append("leadSourceId", getLeadSourceId())
                .append("leadSourceName", getLeadSourceName())
                .append("pricingModel", getPricingModel())
                .append("products", getProducts())
                .append("productsNum", getProductsNum())
                .append("paymentMethod", getPaymentMethod())
                .append("requirement", getRequirement())
                .append("reviewer1", getReviewer1())
                .append("reviewer2", getReviewer2())
                .append("salesmanId", getSalesmanId())
                .append("salesmanName", getSalesmanName())
                .append("salesmanPhone", getSalesmanPhone())
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