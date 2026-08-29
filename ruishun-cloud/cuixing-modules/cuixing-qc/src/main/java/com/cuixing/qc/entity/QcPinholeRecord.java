package com.cuixing.qc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 质量管理-针孔送检记录(QcPinholeRecord)实体类
 *
 * @author makejava
 * @since 2024-07-02 16:56:24
 */
public class QcPinholeRecord implements Serializable {
    private static final long serialVersionUID = -56736878311643222L;
    /**
     * 针孔送检id
     */
    private Long id;
    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    /**
     * 检测项id
     */
    private Long inspectionId;
    /**
     * 批号
     */
    private Long batchNumber;
    /**
     * 型号
     */
    private String paintSpec;
    /**
     * 产品规格
     */
    private String productSpecifications;
    /**
     * 机台
     */
    private String deviceName;
    /**
     * 轴号
     */
    private String axisNumber;
    /**
     * 工号
     */
    private Long jobId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public Long getBatchNumber() {
        return batchNumber;
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

    public void setBatchNumber(Long batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getPaintSpec() {
        return paintSpec;
    }

    public void setPaintSpec(String paintSpec) {
        this.paintSpec = paintSpec;
    }

    public String getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecifications(String productSpecifications) {
        this.productSpecifications = productSpecifications;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAxisNumber() {
        return axisNumber;
    }

    public void setAxisNumber(String axisNumber) {
        this.axisNumber = axisNumber;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
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

