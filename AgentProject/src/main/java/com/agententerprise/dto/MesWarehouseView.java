package com.agententerprise.dto;

/**
 * WMS仓库的Agent安全展示模型。
 */
public class MesWarehouseView {

    /**
     * 仓库主键。
     */
    private Long warehouseId;
    /**
     * 仓库编码。
     */
    private String warehouseCode;
    /**
     * 仓库名称。
     */
    private String warehouseName;
    /**
     * 仓库位置。
     */
    private String location;
    /**
     * 仓库负责人。
     */
    private String charge;
    /**
     * 仓库状态。
     */
    private String status;
    /**
     * 关联工序名称。
     */
    private String workstageName;

    public MesWarehouseView() {
    }

    public MesWarehouseView(Long warehouseId, String warehouseCode, String warehouseName,
                            String location, String charge, String status, String workstageName) {
        this.warehouseId = warehouseId;
        this.warehouseCode = warehouseCode;
        this.warehouseName = warehouseName;
        this.location = location;
        this.charge = charge;
        this.status = status;
        this.workstageName = workstageName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getLocation() {
        return location;
    }

    public String getCharge() {
        return charge;
    }

    public String getStatus() {
        return status;
    }

    public String getWorkstageName() {
        return workstageName;
    }
}
