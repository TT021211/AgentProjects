package com.cuixing.sale.entity;

import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigInteger;
import java.util.Date;

/**
 * 销售订单;
 *
 * @author : http://www.chiner.pro
 * @date : 2024-6-6
 */
@ApiModel(value = "销售订单", description = "")
public class SaleOrder extends BaseEntity {
    /**
     * 自增主键
     */
    @ApiModelProperty(name = "自增主键", notes = "")
    private BigInteger id;
    /**
     * 订单类型(0：客户订单 1：备库订单)
     */
    @ApiModelProperty(name = "订单类型(0：客户订单 1：备库订单)", notes = "")
    private String orderType;
    /**
     * 客户id
     */
    @ApiModelProperty(name = "客户id", notes = "")
    private BigInteger customerId;
    /**
     * 客户名称
     */
    @ApiModelProperty(name = "客户名称", notes = "")
    private String customerName;
    /**
     * 订单编号
     */
    @ApiModelProperty(name = "订单编号", notes = "")
    private String orderCode;
    /**
     * 产品id
     */
    @ApiModelProperty(name = "产品id", notes = "")
    private BigInteger productId;
    /**
     * 产品规格
     */
    @ApiModelProperty(name = "产品规格", notes = "")
    private String specification;
    /**
     * 产品型号
     */
    @ApiModelProperty(name = "产品型号", notes = "")
    private String model;
    /**
     * 重量
     */
    @ApiModelProperty(name = "重量", notes = "")
    private Double weight;
    /**
     * 单位id（单位表）
     */
    @ApiModelProperty(name = "单位id（单位表）", notes = "")
    private BigInteger measureId;
    /**
     * 单位名称（单位表）
     */
    @ApiModelProperty(name = "单位名称（单位表）", notes = "")
    private String measureName;
    /**
     * 交付时间
     */
    @ApiModelProperty(name = "交付时间", notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payTime;
    /**
     * 订单状态
     */
    @ApiModelProperty(name = "订单状态", notes = "")
    private String status;
    /**
     * 逻辑删除
     */
    @ApiModelProperty(name = "逻辑删除", notes = "")
    private Integer isDeleted;
    /**
     * 乐观锁
     */
    @ApiModelProperty(name = "乐观锁", notes = "")
    private Integer version;
    /**
     * 创建人
     */
    @ApiModelProperty(name = "创建人", notes = "")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(name = "创建时间", notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(name = "更新人", notes = "")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(name = "更新时间", notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 预留字段1
     */
    @ApiModelProperty(name = "预留字段1", notes = "")
    private String attr1;
    /**
     * 预留字段2
     */
    @ApiModelProperty(name = "预留字段2", notes = "")
    private Integer attr2;
    /**
     * 预留字段3
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String attr3;
    /**
     * 预留字段4
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt4;
    /**
     * 预留字段5
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt5;
    /**
     * 预留字段6
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt6;
    /**
     * 预留字段7
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt7;
    /**
     * 预留字段8
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt8;
    /**
     * 预留字段9
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt9;
    /**
     * 预留字段10
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt10;
    /**
     * 预留字段3
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String technicalRequirements;
    /**
     * 预留字段3
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private BigInteger standardId;
    /**
     * 预留字段3
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String standardName;

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
     * 订单类型(0：客户订单 1：备库订单)
     */
    public String getOrderType() {
        return this.orderType;
    }

    /**
     * 订单类型(0：客户订单 1：备库订单)
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 客户名称
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * 客户名称
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 订单编号
     */
    public String getOrderCode() {
        return this.orderCode;
    }

    /**
     * 订单编号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 产品规格
     */
    public String getSpecification() {
        return this.specification;
    }

    /**
     * 产品规格
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }

    /**
     * 产品型号
     */
    public String getModel() {
        return this.model;
    }

    /**
     * 产品型号
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 重量
     */
    public Double getWeight() {
        return this.weight;
    }

    /**
     * 重量
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * 单位id（单位表）
     */
    public BigInteger getMeasureId() {
        return this.measureId;
    }

    /**
     * 单位id（单位表）
     */
    public void setMeasureId(BigInteger measureId) {
        this.measureId = measureId;
    }

    /**
     * 单位名称（单位表）
     */
    public String getMeasureName() {
        return this.measureName;
    }

    /**
     * 单位名称（单位表）
     */
    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    /**
     * 交付时间
     */
    public Date getPayTime() {
        return this.payTime;
    }

    /**
     * 交付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public BigInteger getCustomerId() {
        return customerId;
    }

    public void setCustomerId(BigInteger customerId) {
        this.customerId = customerId;
    }

    public String getArtt4() {
        return artt4;
    }

    public void setArtt4(String artt4) {
        this.artt4 = artt4;
    }

    public String getArtt6() {
        return artt6;
    }

    public void setArtt6(String artt6) {
        this.artt6 = artt6;
    }

    public String getArtt5() {
        return artt5;
    }

    public void setArtt5(String artt5) {
        this.artt5 = artt5;
    }

    public String getArtt7() {
        return artt7;
    }

    public void setArtt7(String artt7) {
        this.artt7 = artt7;
    }

    public String getArtt8() {
        return artt8;
    }

    public void setArtt8(String artt8) {
        this.artt8 = artt8;
    }

    public String getArtt9() {
        return artt9;
    }

    public void setArtt9(String artt9) {
        this.artt9 = artt9;
    }

    public String getArtt10() {
        return artt10;
    }

    public void setArtt10(String artt10) {
        this.artt10 = artt10;
    }

    public String getTechnicalRequirements() {
        return technicalRequirements;
    }

    public void setTechnicalRequirements(String technicalRequirements) {
        this.technicalRequirements = technicalRequirements;
    }

    public BigInteger getStandardId() {
        return standardId;
    }

    public void setStandardId(BigInteger standardId) {
        this.standardId = standardId;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    @Override
    public String toString() {
        return "SaleOrder{" +
                "id=" + id +
                ", orderType='" + orderType + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", productId=" + productId +
                ", specification='" + specification + '\'' +
                ", model='" + model + '\'' +
                ", weight=" + weight +
                ", measureId=" + measureId +
                ", measureName='" + measureName + '\'' +
                ", payTime=" + payTime +
                ", status='" + status + '\'' +
                ", isDeleted=" + isDeleted +
                ", version=" + version +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", attr1='" + attr1 + '\'' +
                ", attr2=" + attr2 +
                ", attr3='" + attr3 + '\'' +
                ", artt4='" + artt4 + '\'' +
                ", artt5='" + artt5 + '\'' +
                ", artt6='" + artt6 + '\'' +
                ", artt7='" + artt7 + '\'' +
                ", artt8='" + artt8 + '\'' +
                ", artt9='" + artt9 + '\'' +
                ", artt10='" + artt10 + '\'' +
                ", technicalRequirements='" + technicalRequirements + '\'' +
                ", standardId=" + standardId +
                ", standardName='" + standardName + '\'' +
                '}';
    }
}
