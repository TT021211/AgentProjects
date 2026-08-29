package com.cuixing.qc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 质检管理-缺陷项设置(QcDefect)实体类
 *
 * @author makejava
 * @since 2024-07-02 17:22:36
 */
public class QcDefect implements Serializable {
    private static final long serialVersionUID = -58001633424130562L;
    /**
     * 缺陷ID
     */
    private Long defectId;
    /**
     * 缺陷编码
     */
    private String defectCode;
    /**
     * 缺陷描述
     */
    private String defectName;
    /**
     * 缺陷分类
     */
    private String defectClassificationId;
    /**
     * 缺陷等级
     */
    private String defectLevel;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否启用
     */
    private String enable;
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
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 缺陷分类对象
     * @return
     */
    private QcDefectClassification qcDefectClassification;
    public Long getDefectId() {
        return defectId;
    }

    public void setDefectId(Long defectId) {
        this.defectId = defectId;
    }

    public String getDefectCode() {
        return defectCode;
    }

    public void setDefectCode(String defectCode) {
        this.defectCode = defectCode;
    }

    public String getDefectName() {
        return defectName;
    }

    public QcDefectClassification getQcDefectClassification() {
        return qcDefectClassification;
    }

    public void setQcDefectClassification(QcDefectClassification qcDefectClassification) {
        this.qcDefectClassification = qcDefectClassification;
    }

    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

    public String getDefectClassificationId() {
        return defectClassificationId;
    }

    public void setDefectClassificationId(String defectClassificationId) {
        this.defectClassificationId = defectClassificationId;
    }

    public String getDefectLevel() {
        return defectLevel;
    }

    public void setDefectLevel(String defectLevel) {
        this.defectLevel = defectLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
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

}

