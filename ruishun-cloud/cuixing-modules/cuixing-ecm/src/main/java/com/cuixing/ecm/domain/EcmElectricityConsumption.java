package com.cuixing.ecm.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;

/**
 * 电能耗数据对象 ecm_electricity_consumption
 *
 * @author zhan
 * @date 2024-07-24
 */
@ApiModel(description = "电能耗数据")
public class EcmElectricityConsumption extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 能耗数据ID
     */
    @ApiModelProperty("能耗数据ID")
    private Long consumptionId;

    /**
     * 电表ID
     */
    @ApiModelProperty("电表ID")
    @Excel(name = "电表ID")
    private Long electricDeviceId;

    /**
     * 电表编码
     */
    @ApiModelProperty("电表编码")
    @Excel(name = "电表编码")
    private String electricDeviceCode;

    /**
     * 记录时间
     */
    @ApiModelProperty("第三方记录时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "第三方记录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date recordingTime;

    /**
     * 读数值 (Wh)
     */
    @ApiModelProperty("读数值 (Wh)")
    @Excel(name = "读数值 (Wh)")
    private Integer value;

    /**
     * consumption_value
     * 消耗量
     */
    @ApiModelProperty("消耗量")
    @Excel(name = "消耗量")
    private Integer consumptionValue;

    /**
     * 单位电价(元/kwh)
     */
    @ApiModelProperty("单位电价(元/kwh)")
    @Excel(name = "单位电价(元/kwh)")
    private BigDecimal electricityPrice;

    /**
     * 电费
     */
    @ApiModelProperty("电费")
    @Excel(name = "电费")
    private BigDecimal electricCharge;

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

    public void setConsumptionId(Long consumptionId) {
        this.consumptionId = consumptionId;
    }

    public Long getConsumptionId() {
        return consumptionId;
    }

    public void setElectricDeviceId(Long electricDeviceId) {
        this.electricDeviceId = electricDeviceId;
    }

    public Long getElectricDeviceId() {
        return electricDeviceId;
    }

    public void setElectricDeviceCode(String electricDeviceCode) {
        this.electricDeviceCode = electricDeviceCode;
    }

    public String getElectricDeviceCode() {
        return electricDeviceCode;
    }

    public void setRecordingTime(Date recordingTime) {
        this.recordingTime = recordingTime;
    }

    public Date getRecordingTime() {
        return recordingTime;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    public Integer getValue() {
        return value;
    }

    public void setConsumptionValue(Integer consumptionValue) {
        this.consumptionValue = consumptionValue;
    }

    public Integer getConsumptionValue() {
        return consumptionValue;
    }

    public BigDecimal getElectricityPrice() {
        return electricityPrice;
    }

    public void setElectricityPrice(BigDecimal electricityPrice) {
        this.electricityPrice = electricityPrice;
    }

    public BigDecimal getElectricCharge() {
        return electricCharge;
    }

    public void setElectricCharge(BigDecimal electricCharge) {
        this.electricCharge = electricCharge;
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

    public void
    setAttr1(String attr1) {
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("consumptionId", getConsumptionId())
                .append("electricDeviceId", getElectricDeviceId())
                .append("electricDeviceCode", getElectricDeviceCode())
                .append("recordingTime", getRecordingTime())
                .append("value", getValue())
                .append("consumptionValue", getConsumptionValue())
                .append("electricityPrice", getElectricityPrice())
                .append("electricCharge", getElectricCharge())
                .append("version", getVersion())
                .append("isDeleted", getIsDeleted())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("attr1", getAttr1())
                .append("attr2", getAttr2())
                .append("attr3", getAttr3())
                .toString();
    }
}
