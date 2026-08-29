package com.cuixing.wms.entity;


import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class WmsStorageArea extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long areaId;
    @Excel(
            name = "库位编码"
    )
    private String areaCode;
    @Excel(
            name = "库位名称"
    )
    private String areaName;
    @Excel(
            name = "库区ID"
    )
    private Long locationId;
    @Excel(
            name = "库区编码"
    )
    private String locationCode;
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
            name = "最大载重量"
    )
    private BigDecimal maxLoa;
    @Excel(
            name = "库位位置X"
    )
    private Long positionX;
    @Excel(
            name = "库位位置y"
    )
    private Long positionY;
    @Excel(
            name = "库位位置z"
    )
    private Long positionZ;
    @Excel(
            name = "是否启用"
    )
    private String enableFlag;
    private String attr1;
    private String attr2;
    private Long attr3;
    private Long attr4;
    private Long pid;
    private String pname;

    public WmsStorageArea() {
    }

    public Long getPid() {
        return this.getAreaId();
    }

    public String getPname() {
        return this.getAreaName();
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getAreaId() {
        return this.areaId;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getArea() {
        return this.area;
    }

    public void setMaxLoa(BigDecimal maxLoa) {
        this.maxLoa = maxLoa;
    }

    public BigDecimal getMaxLoa() {
        return this.maxLoa;
    }

    public void setPositionX(Long positionX) {
        this.positionX = positionX;
    }

    public Long getPositionX() {
        return this.positionX;
    }

    public void setPositionY(Long positionY) {
        this.positionY = positionY;
    }

    public Long getPositionY() {
        return this.positionY;
    }

    public void setPositionZ(Long positionZ) {
        this.positionZ = positionZ;
    }

    public Long getPositionZ() {
        return this.positionZ;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() {
        return this.enableFlag;
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

    public String getLocationCode() {
        return this.locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getWarehouseId() {
        return this.warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
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

    public String toString() {
        return (new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)).append("areaId", this.getAreaId()).append("areaCode", this.getAreaCode()).append("areaName", this.getAreaName()).append("locationId", this.getLocationId()).append("area", this.getArea()).append("maxLoa", this.getMaxLoa()).append("positionX", this.getPositionX()).append("positionY", this.getPositionY()).append("positionZ", this.getPositionZ()).append("enableFlag", this.getEnableFlag()).append("remark", this.getRemark()).append("attr1", this.getAttr1()).append("attr2", this.getAttr2()).append("attr3", this.getAttr3()).append("attr4", this.getAttr4()).append("createBy", this.getCreateBy()).append("createTime", this.getCreateTime()).append("updateBy", this.getUpdateBy()).append("updateTime", this.getUpdateTime()).toString();
    }

    public String getCode() {
        return null;
    }
}
