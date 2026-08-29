package com.cuixing.mold.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 模具改制表(MoldRestructuring)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:01
 */
public class MoldRestructuring implements Serializable {
    private static final long serialVersionUID = -68346512364461839L;
    /**
     * 改制id
     */
    private Long moldRestructuringId;
    /**
     * 模具id
     */
    private Long moldId;
    /**
     * 套模id
     */
    private Long nestingMoldId;
    /**
     * 公差
     */
    private BigDecimal engineeringTolerance;
    /**
     * 供应商id
     */
    private Long clientId;
    /**
     * 供应商名称
     */
    private String clientName;
    /**
     * 改制后公差
     */
    private BigDecimal beforeEngineeringTolerance;
    /**
     * 改制时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date restructuringDate;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    /**
     * 改制原因
     */
    private String remark;
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
     * 改之前孔径
     */
    private String beforeAperture;
    /**
     * 改之后孔径
     */
    private String nowAperture;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 模具对象
     * @return
     */
    private MoldLedger moldLedger;
    /**
     * 模具集合
     * @return
     */
    private List<MoldLedger> moldLedgerList;
    /**
     * 模具编码
     */
    private String moldCode;
    /**
     * 类型
     */
    private String moldType;
    /**
     * 型号
     */
    private String moldSpec;
    /**
     * 材质
     */
    private String moldMaterial;

    public Long getMoldRestructuringId() {
        return moldRestructuringId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public BigDecimal getBeforeEngineeringTolerance() {
        return beforeEngineeringTolerance;
    }

    public void setBeforeEngineeringTolerance(BigDecimal beforeEngineeringTolerance) {
        this.beforeEngineeringTolerance = beforeEngineeringTolerance;
    }

    public BigDecimal getEngineeringTolerance() {
        return engineeringTolerance;
    }

    public void setEngineeringTolerance(BigDecimal engineeringTolerance) {
        this.engineeringTolerance = engineeringTolerance;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setMoldRestructuringId(Long moldRestructuringId) {
        this.moldRestructuringId = moldRestructuringId;
    }

    public Long getMoldId() {
        return moldId;
    }

    public void setMoldId(Long moldId) {
        this.moldId = moldId;
    }

    public MoldLedger getMoldLedger() {
        return moldLedger;
    }

    public String getNowAperture() {
        return nowAperture;
    }

    public void setNowAperture(String nowAperture) {
        this.nowAperture = nowAperture;
    }

    public String getBeforeAperture() {
        return beforeAperture;
    }

    public void setBeforeAperture(String beforeAperture) {
        this.beforeAperture = beforeAperture;
    }

    public void setMoldLedger(MoldLedger moldLedger) {
        this.moldLedger = moldLedger;
    }

    public List<MoldLedger> getMoldLedgerList() {
        return moldLedgerList;
    }

    public void setMoldLedgerList(List<MoldLedger> moldLedgerList) {
        this.moldLedgerList = moldLedgerList;
    }

    public Long getNestingMoldId() {
        return nestingMoldId;
    }

    public void setNestingMoldId(Long nestingMoldId) {
        this.nestingMoldId = nestingMoldId;
    }

    public Date getRestructuringDate() {
        return restructuringDate;
    }

    public void setRestructuringDate(Date restructuringDate) {
        this.restructuringDate = restructuringDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getMoldCode() {
        return moldCode;
    }

    public void setMoldCode(String moldCode) {
        this.moldCode = moldCode;
    }

    public String getMoldType() {
        return moldType;
    }

    public void setMoldType(String moldType) {
        this.moldType = moldType;
    }

    public String getMoldSpec() {
        return moldSpec;
    }

    public void setMoldSpec(String moldSpec) {
        this.moldSpec = moldSpec;
    }

    public String getMoldMaterial() {
        return moldMaterial;
    }

    public void setMoldMaterial(String moldMaterial) {
        this.moldMaterial = moldMaterial;
    }
}

