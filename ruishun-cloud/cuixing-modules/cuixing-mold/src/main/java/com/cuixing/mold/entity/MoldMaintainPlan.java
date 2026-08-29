package com.cuixing.mold.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 模具保养表--保养计划表(MoldMaintainPlan)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:01
 */
public class MoldMaintainPlan implements Serializable {
    private static final long serialVersionUID = -26733899969790746L;
    /**
     * 计划id
     */
    private Long maintainPlanId;
    /**
     * 计划编号
     */
    private String maintainPlanCode;
    /**
     * 计划名称
     */
    private String maintainPlanName;
    /**
     * 保养类型
     */
    private String maintainPlanType;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maintainPlanStarttime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maintainPlanEndtime;
    /**
     * 频率
     */
    private String maintainPlanFrequency;
    /**
     * 状态
     */
    private String maintainPlanStatus;
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
     * 状态
     */
    private List<MoldNesting> moldNesting;
    /**
     * 任务标识符
     */
    private String jobName;
    /**
     * 任务标识符
     */
    private String triggerName;
    public MoldMaintainPlan() {
    }

    public Long getMaintainPlanId() {
        return maintainPlanId;
    }

    public void setMaintainPlanId(Long maintainPlanId) {
        this.maintainPlanId = maintainPlanId;
    }

    public String getMaintainPlanCode() {
        return maintainPlanCode;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public List<MoldNesting> getMoldNesting() {
        return moldNesting;
    }

    public void setMoldNesting(List<MoldNesting> moldNesting) {
        this.moldNesting = moldNesting;
    }

    public void setMaintainPlanCode(String maintainPlanCode) {
        this.maintainPlanCode = maintainPlanCode;
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

    public Date getMaintainPlanStarttime() {
        return maintainPlanStarttime;
    }

    public void setMaintainPlanStarttime(Date maintainPlanStarttime) {
        this.maintainPlanStarttime = maintainPlanStarttime;
    }

    public Date getMaintainPlanEndtime() {
        return maintainPlanEndtime;
    }

    public void setMaintainPlanEndtime(Date maintainPlanEndtime) {
        this.maintainPlanEndtime = maintainPlanEndtime;
    }

    public String getMaintainPlanFrequency() {
        return maintainPlanFrequency;
    }

    public void setMaintainPlanFrequency(String maintainPlanFrequency) {
        this.maintainPlanFrequency = maintainPlanFrequency;
    }

    public String getMaintainPlanStatus() {
        return maintainPlanStatus;
    }

    public void setMaintainPlanStatus(String maintainPlanStatus) {
        this.maintainPlanStatus = maintainPlanStatus;
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

