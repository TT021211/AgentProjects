package com.cuixing.crm.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 客诉表(CrmCustomer)表服务类
 *
 * @author 阿江
 * @since 2025-07-14 20:37:17
 */
public class CrmCustomer implements Serializable {
    private static final long serialVersionUID = -71748264051861085L;
    /**
     * 客诉id
     */
    private Long id;
    /**
     * 物料唯一码
     */
    private String materialUniqueCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 客诉问题
     */
    private String customerComplaints;
    /**
     * 客诉人
     */
    private String customerPerson;
    /**
     * 联系方式
     */
    private String contactInformation;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
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
    private String attr3;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialUniqueCode() {
        return materialUniqueCode;
    }

    public void setMaterialUniqueCode(String materialUniqueCode) {
        this.materialUniqueCode = materialUniqueCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerComplaints() {
        return customerComplaints;
    }

    public void setCustomerComplaints(String customerComplaints) {
        this.customerComplaints = customerComplaints;
    }

    public String getCustomerPerson() {
        return customerPerson;
    }

    public void setCustomerPerson(String customerPerson) {
        this.customerPerson = customerPerson;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
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

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

}

