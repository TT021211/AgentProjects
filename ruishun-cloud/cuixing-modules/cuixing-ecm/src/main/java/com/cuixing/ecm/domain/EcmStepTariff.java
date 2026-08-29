package com.cuixing.ecm.domain;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;

/**
 * 阶梯电价对象 ecm_step_tariff
 * 
 * @author zhan
 * @date 2024-08-06
 */
@ApiModel( description = "阶梯电价")
public class EcmStepTariff extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 阶梯电价id */
    @ApiModelProperty("阶梯电价id")
    private Long stepTariffId;

    /** 名称 */
    @ApiModelProperty("名称")
    @Excel(name = "名称")
    private String name;

    /** 月份 */
    @ApiModelProperty("月份")
    @Excel(name = "月份")
    private String month;

    /** 开始时间段 */
    @ApiModelProperty("开始时间段")
    @Excel(name = "开始时间段")
    private String startTime;

    /** 结束时间段 */
    @ApiModelProperty("结束时间段")
    @Excel(name = "结束时间段")
    private String endTime;

    /** 电价 */
    @ApiModelProperty("电价")
    @Excel(name = "电价")
    private BigDecimal electricityPrice;

    /** 逻辑删除 */
    @ApiModelProperty("逻辑删除")
    @Excel(name = "逻辑删除")
    private Long isDeleted;

    /** 乐观锁 */
    @ApiModelProperty("乐观锁")
    @Excel(name = "乐观锁")
    private Long version;

    /** 预留字段1 */
    @ApiModelProperty("预留字段1")
    @Excel(name = "预留字段1")
    private String attr1;

    /** 预留字段2 */
    @ApiModelProperty("预留字段2")
    @Excel(name = "预留字段2")
    private Long attr2;

    /** 预留字段3 */
    @ApiModelProperty("预留字段3")
    @Excel(name = "预留字段3")
    private String attr3;

    public void setStepTariffId(Long stepTariffId) 
    {
        this.stepTariffId = stepTariffId;
    }

    public Long getStepTariffId() 
    {
        return stepTariffId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setMonth(String month) 
    {
        this.month = month;
    }

    public String getMonth() 
    {
        return month;
    }
    public void setStartTime(String startTime) 
    {
        this.startTime = startTime;
    }

    public String getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(String endTime) 
    {
        this.endTime = endTime;
    }

    public String getEndTime() 
    {
        return endTime;
    }
    public void setElectricityPrice(BigDecimal electricityPrice) 
    {
        this.electricityPrice = electricityPrice;
    }

    public BigDecimal getElectricityPrice() 
    {
        return electricityPrice;
    }
    public void setIsDeleted(Long isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted() 
    {
        return isDeleted;
    }
    public void setVersion(Long version) 
    {
        this.version = version;
    }

    public Long getVersion() 
    {
        return version;
    }
    public void setAttr1(String attr1) 
    {
        this.attr1 = attr1;
    }

    public String getAttr1() 
    {
        return attr1;
    }
    public void setAttr2(Long attr2) 
    {
        this.attr2 = attr2;
    }

    public Long getAttr2() 
    {
        return attr2;
    }
    public void setAttr3(String attr3) 
    {
        this.attr3 = attr3;
    }

    public String getAttr3() 
    {
        return attr3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("stepTariffId", getStepTariffId())
            .append("name", getName())
            .append("month", getMonth())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("electricityPrice", getElectricityPrice())
            .append("remark", getRemark())
            .append("isDeleted", getIsDeleted())
            .append("version", getVersion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .toString();
    }
}
