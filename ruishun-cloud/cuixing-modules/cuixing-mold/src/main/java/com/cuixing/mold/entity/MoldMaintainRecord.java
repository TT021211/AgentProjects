package com.cuixing.mold.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 模具保养表--保养记录表(MoldMaintainRecord)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:01
 */
public class MoldMaintainRecord implements Serializable {
    private static final long serialVersionUID = -78881756062311009L;
    /**
     * 保养记录id
     */
    private Long maintainRecordId;
    /**
     * 保养类型
     */
    private String maintainRecordType;
    /**
     * 套模id
     */
    private Long nestingMoldId;
    /**
     * 计划id
     */
    private Long maintainPlanId;
    /**
     * 供应商id
     */
    private Long clientId;
    /**
     * 供应商名称
     */
    private String clientName;
    /**
     * 保养项id
     */
    private String maintainItemId;
    /**
     * 保养状态
     */
    private String status;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 模具编号
     */
    private String nestingName;
    /**
     * 套模范围
     */
    private String nestingScope;
    /**
     * 套模存放位置
     */
    private String nestingArea;
    /**
     * 计划名称
     */
    private String maintainPlanName;
    /**
     * 保养类型
     */
    private String maintainPlanType;
    /**
     * 保养项
     */
    private String maintainItemName;
    /**
     * 保养项
     */
    private List<MoldMaintainItem> maintainItems;

    public Long getMaintainRecordId() {
        return maintainRecordId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setMaintainRecordId(Long maintainRecordId) {
        this.maintainRecordId = maintainRecordId;
    }

    public String getMaintainRecordType() {
        return maintainRecordType;
    }

    public void setMaintainRecordType(String maintainRecordType) {
        this.maintainRecordType = maintainRecordType;
    }

    public String getNestingArea() {
        return nestingArea;
    }

    public void setNestingArea(String nestingArea) {
        this.nestingArea = nestingArea;
    }

    public Long getNestingMoldId() {
        return nestingMoldId;
    }

    public void setNestingMoldId(Long nestingMoldId) {
        this.nestingMoldId = nestingMoldId;
    }

    public Long getMaintainPlanId() {
        return maintainPlanId;
    }

    public void setMaintainPlanId(Long maintainPlanId) {
        this.maintainPlanId = maintainPlanId;
    }

    public String getMaintainItemId() {
        return maintainItemId;
    }

    public void setMaintainItemId(String maintainItemId) {
        this.maintainItemId = maintainItemId;
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

    public String getNestingName() {
        return nestingName;
    }

    public void setNestingName(String nestingName) {
        this.nestingName = nestingName;
    }

    public String getNestingScope() {
        return nestingScope;
    }

    public void setNestingScope(String nestingScope) {
        this.nestingScope = nestingScope;
    }

    public String getMaintainPlanName() {
        return maintainPlanName;
    }

    public void setMaintainPlanName(String maintainPlanName) {
        this.maintainPlanName = maintainPlanName;
    }

    public String getMaintainPlanType() {
        return maintainPlanType;
    }

    public void setMaintainPlanType(String maintainPlanType) {
        this.maintainPlanType = maintainPlanType;
    }

    public String getMaintainItemName() {
        return maintainItemName;
    }

    public void setMaintainItemName(String maintainItemName) {
        this.maintainItemName = maintainItemName;
    }

    public List<MoldMaintainItem> getMaintainItems() {
        return maintainItems;
    }

    public void setMaintainItems(List<MoldMaintainItem> maintainItems) {
        this.maintainItems = maintainItems;
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

