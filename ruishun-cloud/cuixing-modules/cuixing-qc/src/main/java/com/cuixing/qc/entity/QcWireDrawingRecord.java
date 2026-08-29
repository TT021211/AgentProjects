package com.cuixing.qc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 质量管理-拉丝过程检记录(QcWireDrawingRecord)实体类
 *
 * @author makejava
 * @since 2024-07-02 16:56:26
 */
public class QcWireDrawingRecord implements Serializable {
    private static final long serialVersionUID = -76818548658790415L;
    /**
     * 拉丝过程检id
     */
    private Long id;
    /**
     * 工单类型
     */
    private String workOrdeType;
    /**
     * 机台
     */
    private String deviceName;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 订单编号
     */
    private String orderNumber;
    /**
     * 客户
     */
    private String clientName;
    /**
     * 生产工单号
     */
    private String generateWorkOrderNumber;
    /**
     * 产品规格
     */
    private String productSpecifications;
    /**
     * 批号
     */
    private Long batchNumber;
    /**
     * 重量
     */
    private Object weight;
    /**
     * 单位
     */
    private String measure;
    /**
     * 质检人
     */
    private String qualityInspector;
    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    /**
     * 是否合格
     */
    private String qualifiedOrNot;
    /**
     * 缺陷项id
     */
    private String defectId;
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
     * 缺陷项集合
     * @return
     */
    private List<QcDefect> qcDefectList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkOrdeType() {
        return workOrdeType;
    }

    public List<QcDefect> getQcDefectList() {
        return qcDefectList;
    }

    public void setQcDefectList(List<QcDefect> qcDefectList) {
        this.qcDefectList = qcDefectList;
    }

    public void setWorkOrdeType(String workOrdeType) {
        this.workOrdeType = workOrdeType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getGenerateWorkOrderNumber() {
        return generateWorkOrderNumber;
    }

    public void setGenerateWorkOrderNumber(String generateWorkOrderNumber) {
        this.generateWorkOrderNumber = generateWorkOrderNumber;
    }

    public String getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecifications(String productSpecifications) {
        this.productSpecifications = productSpecifications;
    }

    public Long getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Long batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getQualityInspector() {
        return qualityInspector;
    }

    public void setQualityInspector(String qualityInspector) {
        this.qualityInspector = qualityInspector;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getQualifiedOrNot() {
        return qualifiedOrNot;
    }

    public void setQualifiedOrNot(String qualifiedOrNot) {
        this.qualifiedOrNot = qualifiedOrNot;
    }

    public String getDefectId() {
        return defectId;
    }

    public void setDefectId(String defectId) {
        this.defectId = defectId;
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

