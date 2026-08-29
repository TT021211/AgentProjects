package com.cuixing.system.api.domain.wms;

import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class WmsWarehouse extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long warehouseId;
    @Excel(
            name = "仓库编码"
    )
    @ApiModelProperty("仓库编码")
    private String warehouseCode;
    @Excel(
            name = "仓库名称"
    )
    @ApiModelProperty("仓库名称")
    private String warehouseName;
    @Excel(
            name = "位置"
    )
    @ApiModelProperty("位置")
    private String location;
    @Excel(
            name = "面积"
    )
    @ApiModelProperty("面积")
    private BigDecimal area;
    @Excel(
            name = "负责人"
    )
    @ApiModelProperty("负责人")
    private String charge;
    private String attr1;
    private String attr2;
    private Long attr3;
    private Long attr4;
    private Long pid;
    private String pname;

    @ApiModelProperty("工序id")
    private Long workstageId; // 工序id
    @ApiModelProperty("工序name")
    private String workstageName; // 工序name
    @ApiModelProperty("工序code")
    private String workstageCode; // 工序code

    private Integer version;

    private String status;

    //类型名称
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getWorkstationCode() {
        return workstationCode;
    }

    public void setWorkstationCode(String workstationCode) {
        this.workstationCode = workstationCode;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String workstationName) {
        this.workstationName = workstationName;
    }

    public Long getWorkstationId() {
        return workstationId;
    }

    public void setWorkstationId(Long workstationId) {
        this.workstationId = workstationId;
    }

    @ApiModelProperty("机台code")
    private String workstationCode; // 机台code
    @ApiModelProperty("机台name")
    private String workstationName; // 机台name
    @ApiModelProperty("机台id")
    private Long workstationId; // 机台id


    public String getWorkstageCode() {
        return workstageCode;
    }

    public void setWorkstageCode(String workstageCode) {
        this.workstageCode = workstageCode;
    }



    public Long getWorkstageId() {
        return workstageId;
    }

    public void setWorkstageId(Long workstageId) {
        this.workstageId = workstageId;
    }

    public String getWorkstageName() {
        return workstageName;
    }

    public void setWorkstageName(String workstageName) {
        this.workstageName = workstageName;
    }

    public WmsWarehouse() {
    }

    public Long getPid() {
        return this.getWarehouseId();
    }

    public String getPname() {
        return this.getWarehouseName();
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() {
        return this.warehouseId;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName() {
        return this.warehouseName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getArea() {
        return this.area;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getCharge() {
        return this.charge;
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


    public String toString() {
        return "WmWarehouse{warehouseId=" + this.warehouseId + ", warehouseCode='" + this.warehouseCode + '\'' + ", warehouseName='" + this.warehouseName + '\'' + ", location='" + this.location + '\'' + ", area=" + this.area + ", charge='" + this.charge + '\'' + ", attr1='" + this.attr1 + '\'' + ", attr2='" + this.attr2 + '\'' + ", attr3=" + this.attr3 + ", attr4=" + this.attr4 + '}';
    }

    public String getCode() {
        return null;
    }
}
