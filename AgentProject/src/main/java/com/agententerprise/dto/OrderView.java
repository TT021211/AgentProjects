package com.agententerprise.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderView {
    private String orderNo;
    private String customerName;
    private String productCode;
    private Integer quantity;
    private LocalDate requiredDate;
    private String status;
    private LocalDateTime createdAt;

    public OrderView(String orderNo, String customerName, String productCode, Integer quantity,
                     LocalDate requiredDate, String status, LocalDateTime createdAt) {
        this.orderNo = orderNo;
        this.customerName = customerName;
        this.productCode = productCode;
        this.quantity = quantity;
        this.requiredDate = requiredDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getOrderNo() { return orderNo; }
    public String getCustomerName() { return customerName; }
    public String getProductCode() { return productCode; }
    public Integer getQuantity() { return quantity; }
    public LocalDate getRequiredDate() { return requiredDate; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
