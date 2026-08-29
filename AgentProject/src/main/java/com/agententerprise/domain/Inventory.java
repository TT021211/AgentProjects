package com.agententerprise.domain;

import javax.persistence.*;

@Entity
@Table(name = "inventory", uniqueConstraints = @UniqueConstraint(columnNames = {"product_code", "warehouse_code"}))
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code", nullable = false, length = 64)
    private String productCode;

    @Column(name = "warehouse_code", nullable = false, length = 64)
    private String warehouseCode;

    @Column(nullable = false)
    private Integer quantity = 0;

    @Column(nullable = false)
    private Integer reservedQuantity = 0;

    public Inventory() {
    }

    public Inventory(String productCode, String warehouseCode, Integer quantity, Integer reservedQuantity) {
        this.productCode = productCode;
        this.warehouseCode = warehouseCode;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
    }

    public Long getId() { return id; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getWarehouseCode() { return warehouseCode; }
    public void setWarehouseCode(String warehouseCode) { this.warehouseCode = warehouseCode; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Integer getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(Integer reservedQuantity) { this.reservedQuantity = reservedQuantity; }
}
