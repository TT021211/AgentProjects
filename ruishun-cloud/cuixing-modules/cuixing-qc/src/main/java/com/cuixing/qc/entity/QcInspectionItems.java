package com.cuixing.qc.entity;

import com.cuixing.system.api.domain.md.MdItem;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 质量管理-质检检测项(QcInspectionItems)实体类
 *
 * @author makejava
 * @since 2024-07-02 16:56:22
 */
public class QcInspectionItems implements Serializable {
    private static final long serialVersionUID = -35333693594827993L;
    /**
     * 质检检测项id
     */
    private Long id;
    /**
     * 质检检测项编码
     */
    private String detectionCode;
    /**
     * 质检检测项名称
     */
    private String detectionName;
    /**
     * 物料类型id
     */
    private Long itemTypeId;
    /**
     * 是否必检项
     */
    private String isRequired;
    /**
     * 是否启用
     */
    private String enable;
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

    /**
     * 物料类型对象
     * @return
     */
    private QcClassification qcClassification;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetectionCode() {
        return detectionCode;
    }

    public void setDetectionCode(String detectionCode) {
        this.detectionCode = detectionCode;
    }

    public String getDetectionName() {
        return detectionName;
    }

    public void setDetectionName(String detectionName) {
        this.detectionName = detectionName;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public QcClassification getQcClassification() {
        return qcClassification;
    }

    public void setQcClassification(QcClassification qcClassification) {
        this.qcClassification = qcClassification;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
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

