package com.cuixing.sale.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;


/**
 * 销售价格管理(SalePriceManagement)表服务类
 *
 * @author 阿江
 * @since 2025-03-04 15:10:39
 */
public class SalePriceManagement implements Serializable {
    private static final long serialVersionUID = -66215867506281571L;
    /**
     * 价格管理id
     */
    private Long id;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 销售合同id
     */
    private Long saleContractId;
    /**
     * 销售合同编号
     */
    private String saleContractCode;
    /**
     * 销售合同日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saleContractDate;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品规格
     */
    private String productSpec;
    /**
     * 销售合同重量
     */
    private Double saleContractWeight;
    /**
     * 电铜价
     */
    private Double copperPrice;
    /**
     * 加工费
     */
    private Double processingFee;
    /**
     * 单价
     */
    private Double unitPrice;
    /**
     * 发货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date shipmentDate;
    /**
     * 送货重量
     */
    private Double deliveryWeight;
    /**
     * 总金额
     */
    private Double totalAmount;
    /**
     * 贷款
     */
    private String loan;
    /**
     * 逻辑删除
     */
    private Integer isDeleted;
    /**
     * 乐观锁
     */
    private Integer version;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
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
     * 预留字段4
     */
    private String artt4;
    /**
     * 预留字段5
     */
    private String artt5;
    /**
     * 预留字段6
     */
    private String artt6;
    /**
     * 预留字段7
     */
    private String artt7;
    /**
     * 预留字段8
     */
    private String artt8;
    /**
     * 预留字段9
     */
    private String artt9;
    /**
     * 预留字段10
     */
    private String artt10;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getSaleContractId() {
        return saleContractId;
    }

    public void setSaleContractId(Long saleContractId) {
        this.saleContractId = saleContractId;
    }

    public String getSaleContractCode() {
        return saleContractCode;
    }

    public void setSaleContractCode(String saleContractCode) {
        this.saleContractCode = saleContractCode;
    }

    public Date getSaleContractDate() {
        return saleContractDate;
    }

    public void setSaleContractDate(Date saleContractDate) {
        this.saleContractDate = saleContractDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public Double getSaleContractWeight() {
        return saleContractWeight;
    }

    public void setSaleContractWeight(Double saleContractWeight) {
        this.saleContractWeight = saleContractWeight;
    }

    public Double getCopperPrice() {
        return copperPrice;
    }

    public void setCopperPrice(Double copperPrice) {
        this.copperPrice = copperPrice;
    }

    public Double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(Double processingFee) {
        this.processingFee = processingFee;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Double getDeliveryWeight() {
        return deliveryWeight;
    }

    public void setDeliveryWeight(Double deliveryWeight) {
        this.deliveryWeight = deliveryWeight;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getArtt4() {
        return artt4;
    }

    public void setArtt4(String artt4) {
        this.artt4 = artt4;
    }

    public String getArtt5() {
        return artt5;
    }

    public void setArtt5(String artt5) {
        this.artt5 = artt5;
    }

    public String getArtt6() {
        return artt6;
    }

    public void setArtt6(String artt6) {
        this.artt6 = artt6;
    }

    public String getArtt7() {
        return artt7;
    }

    public void setArtt7(String artt7) {
        this.artt7 = artt7;
    }

    public String getArtt8() {
        return artt8;
    }

    public void setArtt8(String artt8) {
        this.artt8 = artt8;
    }

    public String getArtt9() {
        return artt9;
    }

    public void setArtt9(String artt9) {
        this.artt9 = artt9;
    }

    public String getArtt10() {
        return artt10;
    }

    public void setArtt10(String artt10) {
        this.artt10 = artt10;
    }

}

