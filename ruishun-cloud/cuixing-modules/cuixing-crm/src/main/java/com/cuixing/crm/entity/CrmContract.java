package com.cuixing.crm.entity;

import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 销售合同对象 crm_sales_contract
 *
 * @author zhan
 * @date 2024-04-17
 */
@ApiModel(description = "销售合同对象")
public class CrmContract extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 销售合同id
     */
    @ApiModelProperty(value = "销售合同id")
    private Long contractId;

    /**
     * 销售合同编码
     */
    @Excel(name = "销售合同编码")
    @ApiModelProperty(value = "销售合同编码")
    @NotBlank(message = "合同编码不能为空")
    private String contractCode;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    @ApiModelProperty(value = "合同编号")
    private String contractNumber;

    /**
     * 日合同序号
     */
    @Excel(name = "日合同序号")
    @ApiModelProperty(value = "日合同序号")
    private Integer dayContractNum;

    /**
     * 客户Id
     */
    @Excel(name = "客户Id")
    @ApiModelProperty(value = "客户Id")
    private Long clientId;

    /**
     * 客户编码
     */
    @Excel(name = "客户编码")
    @ApiModelProperty(value = "客户编码")
    private String clientCode;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    @ApiModelProperty(value = "客户名称")
    private String clientName;

    /**
     * 客户地址
     */
    @Excel(name = "客户地址")
    @ApiModelProperty(value = "客户地址")
    private String clientAddress;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    @ApiModelProperty(value = "联系人")
    private String contact;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String contactTel;

    /**
     * 签定日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签定日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "签定日期")
    private Date signingDate;

    /**
     * 签定地点
     */
    @Excel(name = "签定地点")
    @ApiModelProperty(value = "签定地点")
    private String signLocation;

    /**
     * 交货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交货日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    /**
     * 交货地点
     */
    @Excel(name = "交货地点")
    @ApiModelProperty(value = "交货地点")
    private String deliveryLocation;

    /**
     * 付款方式
     */
    @Excel(name = "付款方法")
    @ApiModelProperty(value = "付款方式")
    private String paymentMethod;

    /**
     * 交货方法
     */
    @Excel(name = "交货方法")
    @ApiModelProperty(value = "交货方法")
    private String deliveryMethod;
    /**
     * 合同总金额(含税)
     */
    @Excel(name = "合同总金额(含税)")
    @ApiModelProperty(value = "合同总金额(含税)")
    private BigDecimal taxInclusiveTotalAmount;

    /**
     * 业务员id
     */
    @Excel(name = "业务员id")
    @ApiModelProperty(value = "业务员id")
    private Long sellerId;

    /**
     * 业务员名
     */
    @Excel(name = "业务员名")
    @ApiModelProperty(value = "业务员名")
    private String sellerName;


    /**
     * 合同状态
     */
    @Excel(name = "合同状态")
    @ApiModelProperty(value = "合同状态")
    private String contractStatus;

    /**
     * 乐观锁
     */
    @Excel(name = "乐观锁")
    @ApiModelProperty(value = "乐观锁")
    private Long version;

    /**
     * 逻辑删除
     */
    @Excel(name = "逻辑删除")
    @ApiModelProperty(value = "逻辑删除")
    private Long isDeleted;

    /**
     * 预留字段1
     */
    @Excel(name = "预留字段1")
    @ApiModelProperty(value = "预留字段1")
    private String attr1;

    /**
     * 预留字段2
     */
    @Excel(name = "预留字段2")
    @ApiModelProperty(value = "预留字段2")
    private Long attr2;

    /**
     * 预留字段3
     */
    @Excel(name = "预留字段3")
    @ApiModelProperty(value = "预留字段3")
    private String attr3;


    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getDayContractNum() {
        return dayContractNum;
    }

    public void setDayContractNum(Integer dayContractNum) {
        this.dayContractNum = dayContractNum;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public String getSignLocation() {
        return signLocation;
    }

    public void setSignLocation(String signLocation) {
        this.signLocation = signLocation;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public BigDecimal getTaxInclusiveTotalAmount() {
        return taxInclusiveTotalAmount;
    }

    public void setTaxInclusiveTotalAmount(BigDecimal taxInclusiveTotalAmount) {
        this.taxInclusiveTotalAmount = taxInclusiveTotalAmount;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public Long getAttr2() {
        return attr2;
    }

    public void setAttr2(Long attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    @Override
    public String toString() {
        return "CrmContract{" +
                "contractId=" + contractId +
                ", contractCode='" + contractCode + '\'' +
                ", contractNumber='" + contractNumber + '\'' +
                ", dayContractNum=" + dayContractNum +
                ", clientId=" + clientId +
                ", clientCode='" + clientCode + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", contact='" + contact + '\'' +
                ", contactTel='" + contactTel + '\'' +
                ", signingDate=" + signingDate +
                ", signLocation='" + signLocation + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", deliveryLocation='" + deliveryLocation + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                ", taxInclusiveTotalAmount=" + taxInclusiveTotalAmount +
                ", sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", contractStatus='" + contractStatus + '\'' +
                ", version=" + version +
                ", isDeleted=" + isDeleted +
                ", attr1='" + attr1 + '\'' +
                ", attr2=" + attr2 +
                ", attr3='" + attr3 + '\'' +
                '}';
    }
}