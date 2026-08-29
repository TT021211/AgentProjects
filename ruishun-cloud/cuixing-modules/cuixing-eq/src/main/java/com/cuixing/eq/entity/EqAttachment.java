package com.cuixing.eq.entity;

import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 设备配件设置;
 *
 * @author : zhan
 * @date : 2024-7-11
 */
@ApiModel(value = "设备配件设置", description = "EmsAttachment")
public class EqAttachment extends BaseEntity {
    /**
     * 自增主键
     */
    @ApiModelProperty("自增主键")
    private BigInteger id;
    /**
     * 配件名称
     */
    @ApiModelProperty("配件名称")
    private String attachmentName;
    /**
     * 配件编码
     */
    @ApiModelProperty("配件编码")
    private String attachmentCode;
    /**
     * 所属设备id
     */
    @ApiModelProperty("所属设备id")
    private BigInteger equipmentId;
    /**
     * 所属设备编码
     */
    @ApiModelProperty("所属设备编码")
    private String equipmentCode;
    /**
     * 适配设备
     */
    @ApiModelProperty("适配设备")
    private String equipmentName;
    /**
     * 配件分配路径
     */
    @ApiModelProperty("配件分配路径")
    private String distributionPath;
    /**
     * 已使用时间
     */
    @ApiModelProperty("已使用时间")
    private Integer usedTime;
    /**
     * 已生产重量
     */
    @ApiModelProperty("已生产重量")
    private BigDecimal producedWeight;
    /**
     * 最大服务时间（使用寿命时间）
     */
    @ApiModelProperty("最大服务时间（使用寿命时间）")
    private Integer maxServiceTime;

    /**
     * 最大生产重量（使用寿命重量）
     */
    @ApiModelProperty("最大生产重量（使用寿命重量）")
    private BigDecimal maxServiceWeight;
    /**
     * 供应商
     */
    @ApiModelProperty("供应商")
    private String supplier;
    /**
     * 图纸
     */
    @ApiModelProperty("图纸")
    private String drawing;
    /**
     * 配件状态
     */
    @ApiModelProperty("配件状态")
    private String status;
    /**
     * 更换时间
     */
    @ApiModelProperty("更换时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date replaceTime;

    /**
     * 逻辑删除
     */
    @ApiModelProperty("逻辑删除")
    private Integer isDeleted;
    /**
     * 乐观锁
     */
    @ApiModelProperty("乐观锁")
    private Integer version;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 预留字段1
     */
    @ApiModelProperty("预留字段1")
    private String attr1;
    /**
     * 预留字段2
     */
    @ApiModelProperty("预留字段2")
    private Integer attr2;
    /**
     * 预留字段3
     */
    @ApiModelProperty("预留字段3")
    private String attr3;
    /**
     * 配件型号
     */
    @ApiModelProperty("配件型号")
    private String model;

    /**
     * 配件规格
     */
    @ApiModelProperty("配件规格")
    private String specification;
    /**
     * 配件尺寸
     */
    @ApiModelProperty("配件尺寸")
    private String dimension;
    /**
     * 功率
     */
    @ApiModelProperty("功率")
    private BigDecimal power;

    /**
     * 自增主键
     */
    public BigInteger getId() {
        return this.id;
    }

    /**
     * 自增主键
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * 配件名称
     */
    public String getAttachmentName() {
        return this.attachmentName;
    }

    /**
     * 配件名称
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    /**
     * 配件编码
     */
    public String getAttachmentCode() {
        return this.attachmentCode;
    }

    /**
     * 配件编码
     */
    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    /**
     * 所属设备id
     */
    public BigInteger getEquipmentId() {
        return this.equipmentId;
    }

    /**
     * 所属设备id
     */
    public void setEquipmentId(BigInteger equipmentId) {
        this.equipmentId = equipmentId;
    }

    /**
     * 所属设备编码
     */
    public String getEquipmentCode() {
        return this.equipmentCode;
    }

    /**
     * 所属设备编码
     */
    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    /**
     * 适配设备
     */
    public String getEquipmentName() {
        return this.equipmentName;
    }

    /**
     * 适配设备
     */
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    /**
     * 配件分配路径
     */
    public String getDistributionPath() {
        return this.distributionPath;
    }

    /**
     * 配件分配路径
     */
    public void setDistributionPath(String distributionPath) {
        this.distributionPath = distributionPath;
    }

    /**
     * 使用寿命
     */
    public Integer getUsedTime() {
        return this.usedTime;
    }

    /**
     * 使用寿命
     */
    public void setUsedTime(Integer usedTime) {
        this.usedTime = usedTime;
    }

    public BigDecimal getProducedWeight() {
        return producedWeight;
    }

    public void setProducedWeight(BigDecimal producedWeight) {
        this.producedWeight = producedWeight;
    }

    public Integer getMaxServiceTime() {
        return maxServiceTime;
    }

    public void setMaxServiceTime(Integer maxServiceTime) {
        this.maxServiceTime = maxServiceTime;
    }

    public BigDecimal getMaxServiceWeight() {
        return maxServiceWeight;
    }

    public void setMaxServiceWeight(BigDecimal maxServiceWeight) {
        this.maxServiceWeight = maxServiceWeight;
    }

    /**
     * 供应商
     */
    public String getSupplier() {
        return this.supplier;
    }

    /**
     * 供应商
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * 图纸
     */
    public String getDrawing() {
        return this.drawing;
    }

    /**
     * 图纸
     */
    public void setDrawing(String drawing) {
        this.drawing = drawing;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReplaceTime() {
        return replaceTime;
    }

    public void setReplaceTime(Date replaceTime) {
        this.replaceTime = replaceTime;
    }

    /**
     * 逻辑删除
     */
    public Integer getIsDeleted() {
        return this.isDeleted;
    }

    /**
     * 逻辑删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 乐观锁
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * 乐观锁
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 创建人
     */
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新人
     */
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 更新人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 预留字段1
     */
    public String getAttr1() {
        return this.attr1;
    }

    /**
     * 预留字段1
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    /**
     * 预留字段2
     */
    public Integer getAttr2() {
        return this.attr2;
    }

    /**
     * 预留字段2
     */
    public void setAttr2(Integer attr2) {
        this.attr2 = attr2;
    }

    /**
     * 预留字段3
     */
    public String getAttr3() {
        return this.attr3;
    }

    /**
     * 预留字段3
     */
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "EqAttachment{" +
                "id=" + id +
                ", attachmentName='" + attachmentName + '\'' +
                ", attachmentCode='" + attachmentCode + '\'' +
                ", equipmentId=" + equipmentId +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", distributionPath='" + distributionPath + '\'' +
                ", usedTime=" + usedTime +
                ", producedWeight=" + producedWeight +
                ", maxServiceTime=" + maxServiceTime +
                ", maxServiceWeight=" + maxServiceWeight +
                ", supplier='" + supplier + '\'' +
                ", drawing='" + drawing + '\'' +
                ", status='" + status + '\'' +
                ", replaceTime=" + replaceTime +
                ", isDeleted=" + isDeleted +
                ", version=" + version +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", attr1='" + attr1 + '\'' +
                ", attr2=" + attr2 +
                ", attr3='" + attr3 + '\'' +
                ", model='" + model + '\'' +
                ", specification='" + specification + '\'' +
                ", dimension='" + dimension + '\'' +
                ", power=" + power +
                '}';
    }
}