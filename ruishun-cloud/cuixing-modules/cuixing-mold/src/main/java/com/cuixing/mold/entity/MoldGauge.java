package com.cuixing.mold.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 模具换规表(MoldGauge)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:01
 */
public class MoldGauge implements Serializable {
    private static final long serialVersionUID = 443958504921430014L;
    /**
     * 换规单id
     */
    private Long moldGaugeId;
    /**
     * 客户id
     */
    private Long clientId;
    /**
     * 模具id
     */
    private Long moldId;
    /**
     * 拉丝机台id
     */
    private Long lsDeviceId;
    /**
     * 调换前规格
     */
    private String moldBeforSpec;
    /**
     * 调换后规格
     */
    private String moldAfterSpec;
    /**
     * 调换后规格
     */
    private String model;
    /**
     * 调换后规格
     */
    private String spec;
    /**
     * 调换后规格
     */
    private String quantity;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 调换前模具id
     */
    private Long moldBeforeId;
    /**
     * 漆包机台id
     */
    private Long qbDeviceId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 下单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;
    /**
     * 预留字段1
     */
    private String attr1;
    /**
     * 预留字段2
     */
    private String attr2;
    /**
     * 预留字段3
     */
    private Integer attr3;
    /**
     * 预留字段4
     */
    private Integer attr4;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 模具台账对象
     */
    private MoldLedger moldLedger;
    /**
     * 模具台账对象
     */
    private MoldLedger beforeMoldLedger;
    /**
     * ls机台名称
     */
    private String lsName;
    /**
     * qb机台名称
     */
    private String qbName;
    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 模具台账对象
     *
     * @return
     */
    private MoldOutbound moldOutbound;


    public MoldOutbound getMoldOutbound() {
        return moldOutbound;
    }

    public void setMoldOutbound(MoldOutbound moldOutbound) {
        this.moldOutbound = moldOutbound;
    }

    public Long getMoldGaugeId() {
        return moldGaugeId;
    }

    public MoldLedger getMoldLedger() {
        return moldLedger;
    }

    public void setMoldLedger(MoldLedger moldLedger) {
        this.moldLedger = moldLedger;
    }

    public MoldLedger getBeforeMoldLedger() {
        return beforeMoldLedger;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setBeforeMoldLedger(MoldLedger beforeMoldLedger) {
        this.beforeMoldLedger = beforeMoldLedger;
    }

    public String getLsName() {
        return lsName;
    }

    public void setLsName(String lsName) {
        this.lsName = lsName;
    }

    public String getQbName() {
        return qbName;
    }

    public void setQbName(String qbName) {
        this.qbName = qbName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setMoldGaugeId(Long moldGaugeId) {
        this.moldGaugeId = moldGaugeId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getMoldId() {
        return moldId;
    }

    public void setMoldId(Long moldId) {
        this.moldId = moldId;
    }

    public Long getLsDeviceId() {
        return lsDeviceId;
    }

    public void setLsDeviceId(Long lsDeviceId) {
        this.lsDeviceId = lsDeviceId;
    }

    public String getMoldBeforSpec() {
        return moldBeforSpec;
    }

    public void setMoldBeforSpec(String moldBeforSpec) {
        this.moldBeforSpec = moldBeforSpec;
    }

    public String getMoldAfterSpec() {
        return moldAfterSpec;
    }

    public void setMoldAfterSpec(String moldAfterSpec) {
        this.moldAfterSpec = moldAfterSpec;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getMoldBeforeId() {
        return moldBeforeId;
    }

    public void setMoldBeforeId(Long moldBeforeId) {
        this.moldBeforeId = moldBeforeId;
    }

    public Long getQbDeviceId() {
        return qbDeviceId;
    }

    public void setQbDeviceId(Long qbDeviceId) {
        this.qbDeviceId = qbDeviceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public Integer getAttr3() {
        return attr3;
    }

    public void setAttr3(Integer attr3) {
        this.attr3 = attr3;
    }

    public Integer getAttr4() {
        return attr4;
    }

    public void setAttr4(Integer attr4) {
        this.attr4 = attr4;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}

