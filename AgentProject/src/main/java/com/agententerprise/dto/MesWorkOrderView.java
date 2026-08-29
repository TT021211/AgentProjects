package com.agententerprise.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MES生产工单的Agent安全展示模型，不暴露完整实体字段。
 */
public class MesWorkOrderView {

    /**
     * MES工单主键。
     */
    private Long id;
    /**
     * 生产工单号。
     */
    private String workorderCode;
    /**
     * 来源订单号。
     */
    private String orderCode;
    /**
     * 客户名称。
     */
    private String customerName;
    /**
     * 产品型号。
     */
    private String productModel;
    /**
     * 产品规格。
     */
    private String productSpecification;
    /**
     * 工单状态：1排产中，2生产中，3完工。
     */
    private Integer status;
    /**
     * 计划重量。
     */
    private BigDecimal weight;
    /**
     * 创建时间。
     */
    private LocalDateTime createTime;
    /**
     * 更新时间。
     */
    private LocalDateTime updateTime;

    public MesWorkOrderView() {
    }

    public MesWorkOrderView(Long id, String workorderCode, String orderCode, String customerName,
                            String productModel, String productSpecification, Integer status,
                            BigDecimal weight, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.workorderCode = workorderCode;
        this.orderCode = orderCode;
        this.customerName = customerName;
        this.productModel = productModel;
        this.productSpecification = productSpecification;
        this.status = status;
        this.weight = weight;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public String getWorkorderCode() {
        return workorderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductModel() {
        return productModel;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public Integer getStatus() {
        return status;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
