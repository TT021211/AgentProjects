package com.cuixing.ecm.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;

/**
 * 能耗设备对象 ecm_electric_device
 *
 * @author zhan
 * @date 2024-07-24
 */
@ApiModel(description = "能耗设备")
public class EcmElectricDevice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 电表id
     */
    @ApiModelProperty("电表id")
    private Long electricDeviceId;

    /** 电表名 */
    @ApiModelProperty("电表名")
    @Excel(name = "电表名")
    private String electricDeviceName;
    /**
     * 电表编码
     */
    @ApiModelProperty("电表编码")
    @Excel(name = "电表编码")
    private String electricDeviceCode;

    /**
     * 型号
     */
    @ApiModelProperty("型号")
    @Excel(name = "型号")
    private String model;

    /**
     * 制造商
     */
    @ApiModelProperty("制造商")
    @Excel(name = "制造商")
    private String manufacturer;

    /**
     * 安装日期
     */
    @ApiModelProperty("安装日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "安装日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installationDate;

    /**
     * 安装位置
     */
    @ApiModelProperty("安装位置")
    @Excel(name = "安装位置")
    private String location;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    @Excel(name = "状态")
    private String status;

    /**
     * 乐观锁
     */
    @ApiModelProperty("乐观锁")
    @Excel(name = "乐观锁")
    private Long version;

    /**
     * 逻辑删除
     */
    @ApiModelProperty("逻辑删除")
    @Excel(name = "逻辑删除")
    private Long isDeleted;

    /**
     * 预留字段1
     */
    @ApiModelProperty("预留字段1")
    @Excel(name = "预留字段1")
    private String attr1;

    /**
     * 预留字段2
     */
    @ApiModelProperty("预留字段2")
    @Excel(name = "预留字段2")
    private Long attr2;

    /**
     * 预留字段3
     */
    @ApiModelProperty("预留字段3")
    @Excel(name = "预留字段3")
    private String attr3;

    /**
     * 是否主表
     */
    @ApiModelProperty("是否主表")
    @Excel(name = "是否主表")
    private String isMain;
    /**
     * 主表id
     */
    @ApiModelProperty("主表id")
    @Excel(name = "主表id")
    private String parentId;


    private String parentName;

    public void setElectricDeviceId(Long electricDeviceId) {
        this.electricDeviceId = electricDeviceId;
    }

    public Long getElectricDeviceId() {
        return electricDeviceId;
    }

    public String getElectricDeviceName() {
        return electricDeviceName;
    }

    public void setElectricDeviceName(String electricDeviceName) {
        this.electricDeviceName = electricDeviceName;
    }

    public void setElectricDeviceCode(String electricDeviceCode) {
        this.electricDeviceCode = electricDeviceCode;
    }

    public String getElectricDeviceCode() {
        return electricDeviceCode;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted() {
        return isDeleted;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr2(Long attr2) {
        this.attr2 = attr2;
    }

    public Long getAttr2() {
        return attr2;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr3() {
        return attr3;
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "EcmElectricDevice{" +
                "electricDeviceId=" + electricDeviceId +
                ", electricDeviceName='" + electricDeviceName + '\'' +
                ", electricDeviceCode='" + electricDeviceCode + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", installationDate=" + installationDate +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", version=" + version +
                ", isDeleted=" + isDeleted +
                ", attr1='" + attr1 + '\'' +
                ", attr2=" + attr2 +
                ", attr3='" + attr3 + '\'' +
                ", isMain='" + isMain + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                '}';
    }
}
