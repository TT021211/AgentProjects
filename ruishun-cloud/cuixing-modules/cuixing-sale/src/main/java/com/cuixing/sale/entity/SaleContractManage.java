package com.cuixing.sale.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;


/**
 * (SaleContractManage)表服务类
 *
 * @author shrimp
 * @since 2025-02-26 10:51:35
 */
public class SaleContractManage implements Serializable {
  private static final long serialVersionUID = 427473464553034240L;
  /**
   * 销售合同id
   */
  private Long id;
  /**
   * 逻辑删除
   */
  private Integer isDeleted;
  /**
   * 乐观锁
   */
  private Object version;
  /**
   * 创建人
   */
  private String createBy;
  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  /**
   * 更新人
   */
  private String updateBy;
  /**
   * 更新时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;
  /**
   * 预留字段
   */
  private String status;
  /**
   * 预留字段1
   */
  private String attr1;
  /**
   * 预留字段2
   */
  private Integer attr2;
  /**
   * 预留字段3
   */
  private Object attr3;
  /**
   * 备用字段4
   */
  private BigDecimal attr4;
  /**
   * 备用字段5
   */
  private String attr5;
  /**
   * 备用字段6
   */
  private String attr6;
  /**
   * 备用字段7
   */
  private String attr7;
  /**
   * 备用字段8
   */
  private String attr8;
  /**
   * 备用字段9
   */
  private String attr9;
  /**
   * 合同编号
   */
  private String contractNumber;
  /**
   * 产品名称
   */
  private String productName;
  /**
   * 规格型号
   */
  private String specificationModel;
  /**
   * 合同重量
   */
  private BigDecimal weight;
  /**
   * 单位
   */
  private String unit;
  /**
   * 电铜价
   */
  private BigDecimal copperPrice;
  /**
   * 加工费
   */
  private BigDecimal processingFee;
  /**
   * 单价
   */
  private BigDecimal price;
  /**
   * 总金额
   */
  private BigDecimal totalPrice;
  /**
   * 总金额大写
   */
  private String totalPriceUpper;
  /**
   * 签订日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date dateOfSigning;
  /**
   * 签订地点
   */
  private String signedAt;
  /**
   * 合同说明
   */
  private String contractNote;
  /**
   * 本厂名称
   */
  private String demandName;
  /**
   * 本厂地址
   */
  private String demandAddress;
  /**
   * 本厂代表
   */
  private String demandLegal;
  /**
   * 本厂电话
   */
  private String demandPhone;
  /**
   * 本厂传真号
   */
  private String demandFax;
  /**
   * 本厂开户银行
   */
  private String demandBank;
  /**
   * 本厂账户
   */
  private String demandAccount;
  /**
   * 本厂税号
   */
  private String demandTax;
  /**
   * 客户名称
   */
  private String userName;
  /**
   * 客户地址
   */
  private String userAddress;
  /**
   * 客户代表
   */
  private String userLegal;
  /**
   * 客户电话
   */
  private String userPhone;
  /**
   * 客户传真号
   */
  private String userFax;
  /**
   * 客户开户银行
   */
  private String userBank;
  /**
   * 客户账户
   */
  private String userAccount;
  /**
   * 客户税号
   */
  private String userTax;
  /**
   * 客户id
   */
  private Long userId;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(Integer isDeleted) {
    this.isDeleted = isDeleted;
  }

  public Object getVersion() {
    return version;
  }

  public void setVersion(Object version) {
    this.version = version;
  }

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getUpdateBy() {
    return updateBy;
  }

  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAttr1() {
    return attr1;
  }

  public void setAttr1(String attr1) {
    this.attr1 = attr1;
  }

  public Integer getAttr2() {
    return attr2;
  }

  public void setAttr2(Integer attr2) {
    this.attr2 = attr2;
  }

  public Object getAttr3() {
    return attr3;
  }

  public void setAttr3(Object attr3) {
    this.attr3 = attr3;
  }

  public BigDecimal getAttr4() {
    return attr4;
  }

  public void setAttr4(BigDecimal attr4) {
    this.attr4 = attr4;
  }

  public String getAttr5() {
    return attr5;
  }

  public void setAttr5(String attr5) {
    this.attr5 = attr5;
  }

  public String getAttr6() {
    return attr6;
  }

  public void setAttr6(String attr6) {
    this.attr6 = attr6;
  }

  public String getAttr7() {
    return attr7;
  }

  public void setAttr7(String attr7) {
    this.attr7 = attr7;
  }

  public String getAttr8() {
    return attr8;
  }

  public void setAttr8(String attr8) {
    this.attr8 = attr8;
  }

  public String getAttr9() {
    return attr9;
  }

  public void setAttr9(String attr9) {
    this.attr9 = attr9;
  }

  public String getContractNumber() {
    return contractNumber;
  }

  public void setContractNumber(String contractNumber) {
    this.contractNumber = contractNumber;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getSpecificationModel() {
    return specificationModel;
  }

  public void setSpecificationModel(String specificationModel) {
    this.specificationModel = specificationModel;
  }

  public BigDecimal getWeight() {
    return weight;
  }

  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public BigDecimal getCopperPrice() {
    return copperPrice;
  }

  public void setCopperPrice(BigDecimal copperPrice) {
    this.copperPrice = copperPrice;
  }

  public BigDecimal getProcessingFee() {
    return processingFee;
  }

  public void setProcessingFee(BigDecimal processingFee) {
    this.processingFee = processingFee;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getTotalPriceUpper() {
    return totalPriceUpper;
  }

  public void setTotalPriceUpper(String totalPriceUpper) {
    this.totalPriceUpper = totalPriceUpper;
  }

  public Date getDateOfSigning() {
    return dateOfSigning;
  }

  public void setDateOfSigning(Date dateOfSigning) {
    this.dateOfSigning = dateOfSigning;
  }

  public String getSignedAt() {
    return signedAt;
  }

  public void setSignedAt(String signedAt) {
    this.signedAt = signedAt;
  }

  public String getContractNote() {
    return contractNote;
  }

  public void setContractNote(String contractNote) {
    this.contractNote = contractNote;
  }

  public String getDemandName() {
    return demandName;
  }

  public void setDemandName(String demandName) {
    this.demandName = demandName;
  }

  public String getDemandAddress() {
    return demandAddress;
  }

  public void setDemandAddress(String demandAddress) {
    this.demandAddress = demandAddress;
  }

  public String getDemandLegal() {
    return demandLegal;
  }

  public void setDemandLegal(String demandLegal) {
    this.demandLegal = demandLegal;
  }

  public String getDemandPhone() {
    return demandPhone;
  }

  public void setDemandPhone(String demandPhone) {
    this.demandPhone = demandPhone;
  }

  public String getDemandFax() {
    return demandFax;
  }

  public void setDemandFax(String demandFax) {
    this.demandFax = demandFax;
  }

  public String getDemandBank() {
    return demandBank;
  }

  public void setDemandBank(String demandBank) {
    this.demandBank = demandBank;
  }

  public String getDemandAccount() {
    return demandAccount;
  }

  public void setDemandAccount(String demandAccount) {
    this.demandAccount = demandAccount;
  }

  public String getDemandTax() {
    return demandTax;
  }

  public void setDemandTax(String demandTax) {
    this.demandTax = demandTax;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserAddress() {
    return userAddress;
  }

  public void setUserAddress(String userAddress) {
    this.userAddress = userAddress;
  }

  public String getUserLegal() {
    return userLegal;
  }

  public void setUserLegal(String userLegal) {
    this.userLegal = userLegal;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getUserFax() {
    return userFax;
  }

  public void setUserFax(String userFax) {
    this.userFax = userFax;
  }

  public String getUserBank() {
    return userBank;
  }

  public void setUserBank(String userBank) {
    this.userBank = userBank;
  }

  public String getUserAccount() {
    return userAccount;
  }

  public void setUserAccount(String userAccount) {
    this.userAccount = userAccount;
  }

  public String getUserTax() {
    return userTax;
  }

  public void setUserTax(String userTax) {
    this.userTax = userTax;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

}

