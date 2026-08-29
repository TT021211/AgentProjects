package com.agententerprise.dto;

public class DashboardSummary {
    private long productCount;
    private long inventoryRecordCount;
    private long orderCount;
    private long lowStockCount;

    public DashboardSummary(long productCount, long inventoryRecordCount, long orderCount, long lowStockCount) {
        this.productCount = productCount;
        this.inventoryRecordCount = inventoryRecordCount;
        this.orderCount = orderCount;
        this.lowStockCount = lowStockCount;
    }

    public long getProductCount() { return productCount; }
    public long getInventoryRecordCount() { return inventoryRecordCount; }
    public long getOrderCount() { return orderCount; }
    public long getLowStockCount() { return lowStockCount; }
}
