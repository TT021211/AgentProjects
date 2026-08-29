package com.cuixing.mold.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 模具采购(MoldPurchase)实体类
 *
 * @author makejava
 * @since 2024-07-24 09:25:07
 */
public class MoldPurchase implements Serializable {
    private static final long serialVersionUID = -76885371160785353L;
    /**
     * id
     */
    private Long id;
    /**
     * 采购订单号
     */
    private String purchaseCode;
    /**
     * 采购规格
     */
    private String purchaseSpec;
    /**
     * 采购公差
     */
    private BigDecimal purchaseTolerance;
    /**
     * 采购材料
     */
    private String purchaseMaterial;
    /**
     * 采购类型
     */
    private String purchaseType;
    /**
     * 状态
     */
    private String status;
    /**
     * 入库状态
     */
    private String inboundStatus;
    /**
     * 采购厂家
     */
    private String purchaseFactory;
    /**
     * 预留字段1
     */
    private String attr1;
    /**
     * 预留字段2
     */
    private String attr2;
    /**
     * 预留字段3
     */
    private Integer attr3;
    /**
     * 预留字段4
     */
    private Integer attr4;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 模具编码
     */
    private String moldCode; // 新增字段

    public String getMoldCode() {
        return moldCode;
    }

    public void setMoldCode(String moldCode) {
        this.moldCode = moldCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getPurchaseSpec() {
        return purchaseSpec;
    }

    public void setPurchaseSpec(String purchaseSpec) {
        this.purchaseSpec = purchaseSpec;
    }

    public BigDecimal getPurchaseTolerance() {
        return purchaseTolerance;
    }

    public void setPurchaseTolerance(BigDecimal purchaseTolerance) {
        this.purchaseTolerance = purchaseTolerance;
    }

    public String getPurchaseMaterial() {
        return purchaseMaterial;
    }

    public void setPurchaseMaterial(String purchaseMaterial) {
        this.purchaseMaterial = purchaseMaterial;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInboundStatus() {
        return inboundStatus;
    }

    public void setInboundStatus(String inboundStatus) {
        this.inboundStatus = inboundStatus;
    }

    public String getPurchaseFactory() {
        return purchaseFactory;
    }

    public void setPurchaseFactory(String purchaseFactory) {
        this.purchaseFactory = purchaseFactory;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public Integer getAttr3() {
        return attr3;
    }

    public void setAttr3(Integer attr3) {
        this.attr3 = attr3;
    }

    public Integer getAttr4() {
        return attr4;
    }

    public void setAttr4(Integer attr4) {
        this.attr4 = attr4;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

}

