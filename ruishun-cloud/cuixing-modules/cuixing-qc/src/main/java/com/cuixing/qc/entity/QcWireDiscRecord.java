package com.cuixing.qc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 质量管理-线盘检记录(QcWireDiscRecord)实体类
 *
 * @author makejava
 * @since 2024-07-02 16:56:25
 */
public class QcWireDiscRecord implements Serializable {
    private static final long serialVersionUID = -88266021078884952L;
    /**
     * 线盘检记录id
     */
    private Long id;
    /**
     * 检测项id
     */
    private Long inspectionId;
    /**
     * 型号
     */
    private String paintSpec;
    /**
     * 采购订单
     */
    private String purchaseOrder;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 批号
     */
    private Long batchNumber;
    /**
     * 抽检个数
     */
    private Long number;
    /**
     * 质检人
     */
    private String qualityInspector;
    /**
     * 检测结果
     */
    private String detectionResult;
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
     * 质检检测项对象
     * @return
     */
    private QcInspectionItems qcInspectionItems;

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

    public QcInspectionItems getQcInspectionItems() {
        return qcInspectionItems;
    }

    public void setQcInspectionItems(QcInspectionItems qcInspectionItems) {
        this.qcInspectionItems = qcInspectionItems;
    }

    public List<QcDefect> getQcDefectList() {
        return qcDefectList;
    }

    public void setQcDefectList(List<QcDefect> qcDefectList) {
        this.qcDefectList = qcDefectList;
    }

    public Long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getPaintSpec() {
        return paintSpec;
    }

    public void setPaintSpec(String paintSpec) {
        this.paintSpec = paintSpec;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Long getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Long batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getQualityInspector() {
        return qualityInspector;
    }

    public void setQualityInspector(String qualityInspector) {
        this.qualityInspector = qualityInspector;
    }

    public String getDetectionResult() {
        return detectionResult;
    }

    public void setDetectionResult(String detectionResult) {
        this.detectionResult = detectionResult;
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

