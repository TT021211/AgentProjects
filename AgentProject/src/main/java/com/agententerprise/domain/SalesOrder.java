package com.agententerprise.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_order")
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no", nullable = false, unique = true, length = 64)
    private String orderNo;

    @Column(nullable = false, length = 128)
    private String customerName;

    @Column(nullable = false, length = 64)
    private String productCode;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDate requiredDate;

    @Column(nullable = false, length = 32)
    private String status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public SalesOrder() {
    }

    public SalesOrder(String orderNo, String customerName, String productCode, Integer quantity,
                      LocalDate requiredDate, String status, LocalDateTime createdAt) {
        this.orderNo = orderNo;
        this.customerName = customerName;
        this.productCode = productCode;
        this.quantity = quantity;
        this.requiredDate = requiredDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getOrderNo() { return orderNo; }
    public String getCustomerName() { return customerName; }
    public String getProductCode() { return productCode; }
    public Integer getQuantity() { return quantity; }
    public LocalDate getRequiredDate() { return requiredDate; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
