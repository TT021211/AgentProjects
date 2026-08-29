package com.cuixing.qc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 质检管理-表面检缺陷项设置(QcSurface)实体类
 *
 * @author makejava
 * @since 2024-07-02 16:56:25
 */
public class QcSurface implements Serializable {
    private static final long serialVersionUID = 465879459094158302L;
    /**
     * 表面缺陷项设置id
     */
    private Integer id;
    /**
     * 表面缺陷项设置编码
     */
    private String surfaceCode;
    /**
     * 表面缺陷项设置名称
     */
    private String surfaceName;
    /**
     * 缺陷分类项id
     */
    private Integer defectClassificationId;
    /**
     * 是否必检项
     */
    private String isRequired;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurfaceCode() {
        return surfaceCode;
    }

    public QcDefectClassification getQcDefectClassification() {
        return qcDefectClassification;
    }

    public void setQcDefectClassification(QcDefectClassification qcDefectClassification) {
        this.qcDefectClassification = qcDefectClassification;
    }

    public void setSurfaceCode(String surfaceCode) {
        this.surfaceCode = surfaceCode;
    }

    public String getSurfaceName() {
        return surfaceName;
    }

    public void setSurfaceName(String surfaceName) {
        this.surfaceName = surfaceName;
    }

    public Integer getDefectClassificationId() {
        return defectClassificationId;
    }

    public void setDefectClassificationId(Integer defectClassificationId) {
        this.defectClassificationId = defectClassificationId;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
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

