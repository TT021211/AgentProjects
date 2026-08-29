package com.cuixing.system.api.domain.md;

import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;
import java.util.Date;

/**
 * 机台设置(MdWorkbenchStrandedwire)实体类
 *
 * @author makejava
 * @since 2024-11-25 16:46:10
 */
public class MdWorkbenchStrandedwire extends BaseEntity {
    /**
     * 自增主键
     */
    private BigInteger id;
    /**
     * 机台类型id
     */
    private BigInteger workbenchTypeId;
    /**
     * 机台类型
     */
    private String workbenchType;
    /**
     * 关联设备id
     */
    private BigInteger equipmentId;
    /**
     * 设备编码
     */
    private String equipmentCode;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 设备轴号
     */
    private String axleNumber;
    /**
     * 设备轴数
     */
    private Integer axleQuantity;
    /**
     * 生产上限
     */
    private Double productionUpperLimit;
    /**
     * 生产下限
     */
    private Double productionLowerLimit;
    /**
     * 是否启用
     */
    private Integer isEnable;
    /**
     * 逻辑删除
     */
    private Integer isDeleted;
    /**
     * 乐观锁
     */
    private Integer version;
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
     * 预留字段2(是否被关联 0：未关联 1：已关联)
     */
    private Integer attr2;
    /**
     * 预留字段3
     */
    private String attr3;
    /**
     * 备注
     */
    private String remark;
    /**
     * 工序id
     */
    private BigInteger processId;
    /**
     * 所属工序
     */
    private String process;
    /**
     * 机台型号id
     */
    private BigInteger modelId;
    /**
     * 机台型号名称
     */
    private String modelName;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getWorkbenchTypeId() {
        return workbenchTypeId;
    }

    public void setWorkbenchTypeId(BigInteger workbenchTypeId) {
        this.workbenchTypeId = workbenchTypeId;
    }

    public String getWorkbenchType() {
        return workbenchType;
    }

    public void setWorkbenchType(String workbenchType) {
        this.workbenchType = workbenchType;
    }

    public BigInteger getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(BigInteger equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getAxleNumber() {
        return axleNumber;
    }

    public void setAxleNumber(String axleNumber) {
        this.axleNumber = axleNumber;
    }

    public Integer getAxleQuantity() {
        return axleQuantity;
    }

    public void setAxleQuantity(Integer axleQuantity) {
        this.axleQuantity = axleQuantity;
    }

    public Double getProductionUpperLimit() {
        return productionUpperLimit;
    }

    public void setProductionUpperLimit(Double productionUpperLimit) {
        this.productionUpperLimit = productionUpperLimit;
    }

    public Double getProductionLowerLimit() {
        return productionLowerLimit;
    }

    public void setProductionLowerLimit(Double productionLowerLimit) {
        this.productionLowerLimit = productionLowerLimit;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Integer getAttr2() {
        return attr2;
    }

    public void setAttr2(Integer attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigInteger getProcessId() {
        return processId;
    }

    public void setProcessId(BigInteger processId) {
        this.processId = processId;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public BigInteger getModelId() {
        return modelId;
    }

    public void setModelId(BigInteger modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}

