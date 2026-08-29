package com.agententerprise.dto;

public class InventoryView {
    private String productCode;
    private String productName;
    private String warehouseCode;
    private Integer quantity;
    private Integer reservedQuantity;
    private Integer availableQuantity;

    public InventoryView(String productCode, String productName, String warehouseCode,
                         Integer quantity, Integer reservedQuantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.warehouseCode = warehouseCode;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
        this.availableQuantity = quantity - reservedQuantity;
    }

    public String getProductCode() { return productCode; }
    public String getProductName() { return productName; }
    public String getWarehouseCode() { return warehouseCode; }
    public Integer getQuantity() { return quantity; }
    public Integer getReservedQuantity() { return reservedQuantity; }
    public Integer getAvailableQuantity() { return availableQuantity; }
}
