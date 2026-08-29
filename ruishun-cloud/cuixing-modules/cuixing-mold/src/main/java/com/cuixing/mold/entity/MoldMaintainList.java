package com.cuixing.mold.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 待保养列表(MoldMaintainList)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:01
 */
public class MoldMaintainList implements Serializable {
    private static final long serialVersionUID = 627933915500750357L;
    /**
     * 待保养列表id
     */
    private Long id;
    /**
     * 套模id
     */
    private Long nestingId;
    /**
     * 计划id
     */
    private Long planId;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 套模名称
     */
    private String nestingName;
    /**
     * 套模范围
     */
    private String nestingScope;
    /**
     * 套模位置
     */
    private String nestingArea;
    /**
     * 计划类型
     */
    private String planType;
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
     * 状态
     */
    private String status;
    /**
     * 供应商id
     */
    private Long clientId;
    /**
     * 供应商名称
     */
    private String clientName;
    /**
     * 待检验日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inspectionDate;
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
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 套模对象
     * @return
     */
    private MoldNesting moldNesting;
    /**
     * 计划对象
     * @return
     */
    private MoldMaintainPlan moldMaintainPlan;

    /**
     * 保养项id
     * @return
     */
    private String itemsId;

    public String getPlanName() {
        return planName;
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

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Date getStartDate() {
        return startDate;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemsId() {
        return itemsId;
    }

    public void setItemsId(String itemsId) {
        this.itemsId = itemsId;
    }

    public Long getNestingId() {
        return nestingId;
    }

    public MoldNesting getMoldNesting() {
        return moldNesting;
    }

    public void setMoldNesting(MoldNesting moldNesting) {
        this.moldNesting = moldNesting;
    }

    public MoldMaintainPlan getMoldMaintainPlan() {
        return moldMaintainPlan;
    }

    public void setMoldMaintainPlan(MoldMaintainPlan moldMaintainPlan) {
        this.moldMaintainPlan = moldMaintainPlan;
    }

    public void setNestingId(Long nestingId) {
        this.nestingId = nestingId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
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

    public String getNestingArea() {
        return nestingArea;
    }

    public void setNestingArea(String nestingArea) {
        this.nestingArea = nestingArea;
    }
}

