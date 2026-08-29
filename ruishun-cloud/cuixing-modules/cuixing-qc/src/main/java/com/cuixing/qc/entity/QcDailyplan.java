package com.cuixing.qc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 日常送检计划(QcDailyplan)实体类
 *
 * @author makejava
 * @since 2024-07-02 16:56:18
 */
public class QcDailyplan implements Serializable {
    private static final long serialVersionUID = 416576657446367219L;
    /**
     * 送检计划-id
     */
    private Long dailyplanId;
    /**
     * 送检计划-编码
     */
    private String dailyplanCode;
    /**
     * 送检计划-名称
     */
    private String dailyplanName;
    /**
     * 送检计划-QB机台
     */
    private String dailyplanDevice;
    /**
     * 送检计划-轴号
     */
    private String dailyplanAxle;
    /**
     * 送检计划-更换前规格
     */
    private String dailyplanBeforeSpec;
    /**
     * 送检计划-更换后规格
     */
    private String dailyplanAfterSpec;
    /**
     * 送检计划-规格
     */
    private String dailyplanSpec;
    /**
     * 送检计划-日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dailyplanDate;
    /**
     * 送检计划-备注
     */
    private String dailyplanRemark;
    /**
     * 送检计划-送检人
     */
    private String dailyplanSubmitter;
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


    public Long getDailyplanId() {
        return dailyplanId;
    }

    public void setDailyplanId(Long dailyplanId) {
        this.dailyplanId = dailyplanId;
    }

    public String getDailyplanCode() {
        return dailyplanCode;
    }

    public void setDailyplanCode(String dailyplanCode) {
        this.dailyplanCode = dailyplanCode;
    }

    public String getDailyplanName() {
        return dailyplanName;
    }

    public void setDailyplanName(String dailyplanName) {
        this.dailyplanName = dailyplanName;
    }

    public String getDailyplanDevice() {
        return dailyplanDevice;
    }

    public void setDailyplanDevice(String dailyplanDevice) {
        this.dailyplanDevice = dailyplanDevice;
    }

    public String getDailyplanAxle() {
        return dailyplanAxle;
    }

    public void setDailyplanAxle(String dailyplanAxle) {
        this.dailyplanAxle = dailyplanAxle;
    }

    public String getDailyplanBeforeSpec() {
        return dailyplanBeforeSpec;
    }

    public void setDailyplanBeforeSpec(String dailyplanBeforeSpec) {
        this.dailyplanBeforeSpec = dailyplanBeforeSpec;
    }

    public String getDailyplanAfterSpec() {
        return dailyplanAfterSpec;
    }

    public void setDailyplanAfterSpec(String dailyplanAfterSpec) {
        this.dailyplanAfterSpec = dailyplanAfterSpec;
    }

    public String getDailyplanSpec() {
        return dailyplanSpec;
    }

    public void setDailyplanSpec(String dailyplanSpec) {
        this.dailyplanSpec = dailyplanSpec;
    }

    public Date getDailyplanDate() {
        return dailyplanDate;
    }

    public void setDailyplanDate(Date dailyplanDate) {
        this.dailyplanDate = dailyplanDate;
    }

    public String getDailyplanRemark() {
        return dailyplanRemark;
    }

    public void setDailyplanRemark(String dailyplanRemark) {
        this.dailyplanRemark = dailyplanRemark;
    }

    public String getDailyplanSubmitter() {
        return dailyplanSubmitter;
    }

    public void setDailyplanSubmitter(String dailyplanSubmitter) {
        this.dailyplanSubmitter = dailyplanSubmitter;
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

}

