package com.cuixing.wms.entity;

import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

public class WmsStorageLocation extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long locationId;
    @Excel(
            name = "库区编码"
    )
    private String locationCode;
    private String areaCode;
    @Excel(
            name = "库区名称"
    )
    private String locationName;
    @Excel(
            name = "仓库ID"
    )
    private Long warehouseId;
    @Excel(
            name = "仓库编码"
    )
    private String warehouseCode;
    @Excel(
            name = "仓库名称"
    )
    private String warehouseName;
    @Excel(
            name = "面积"
    )
    private BigDecimal area;
    @Excel(
            name = "是否开启库位管理"
    )
    private String areaFlag;
    private String attr1;
    private String attr2;
    private Long attr3;
    private Long attr4;
    private Long maxPositionY;
    private List<WmsStorageArea> children;
    private Long pid;
    private String pname;

    public WmsStorageLocation() {
    }

    public Long getPid() {
        return this.getLocationId();
    }

    public String getPname() {
        return this.getLocationName();
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() {
        return this.warehouseId;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getArea() {
        return this.area;
    }

    public void setAreaFlag(String areaFlag) {
        this.areaFlag = areaFlag;
    }

    public String getAreaFlag() {
        return this.areaFlag;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr1() {
        return this.attr1;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr2() {
        return this.attr2;
    }

    public void setAttr3(Long attr3) {
        this.attr3 = attr3;
    }

    public Long getAttr3() {
        return this.attr3;
    }

    public void setAttr4(Long attr4) {
        this.attr4 = attr4;
    }

    public Long getAttr4() {
        return this.attr4;
    }

    public List<WmsStorageArea> getChildren() {
        return this.children;
    }

    public void setChildren(List<WmsStorageArea> children) {
        this.children = children;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return this.warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getMaxPositionY() {
        return this.maxPositionY;
    }

    public void setMaxPositionY(Long maxPositionY) {
        this.maxPositionY = maxPositionY;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String toString() {
        return "WmStorageLocation{locationId=" + this.locationId + ", locationCode='" + this.locationCode + '\'' + ", locationName='" + this.locationName + '\'' + ", warehouseId=" + this.warehouseId + ", area=" + this.area + ", areaFlag='" + this.areaFlag + '\'' + ", attr1='" + this.attr1 + '\'' + ", attr2='" + this.attr2 + '\'' + ", attr3=" + this.attr3 + ", attr4=" + this.attr4 + ", children=" + this.children + '}';
    }

    public String getCode() {
        return null;
    }
}