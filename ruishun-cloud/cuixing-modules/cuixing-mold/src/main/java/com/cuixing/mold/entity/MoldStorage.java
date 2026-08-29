package com.cuixing.mold.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 模具入库表(MoldStorage)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:02
 */
public class MoldStorage implements Serializable {
    private static final long serialVersionUID = 578340470784361251L;
    /**
     * 入库id
     */
    private Long moldStorageId;
    /**
     * 模具id
     */
    private Long moldId;
    /**
     * 套模id
     */
    private Long nestingId;
    /**
     * ls机台id
     */
    private Long lsId;
    /**
     * qb机台id
     */
    private Long qbId;
    /**
     * 公差
     */
    private BigDecimal engineeringTolerance;
    /**
     * 归还人
     */
    private String recipient;
    /**
     * 入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date moldStorageTime;
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
     * @return
     */
    private List<MoldLedger> moldLedgerList;
    /**
     * 模具台账对象
     * @return
     */
    private MoldLedger moldLedger;
    /**
     * 套模对象
     * @return
     */
    private MoldNesting moldNesting;

    /**
     * 拉丝机台
     * @return
     */
    private String lsName;
    /**
     * 漆包机台
     * @return
     */
    private String qbName;
    /**
     * 备注
     * @return
     */
    private String remark;
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
     * 存放位置
     */
    private String moldArea;
    /**
     * 套模名称
     */
    private String moldName;
    /**
     * 材质
     */
    private String moldMaterial;
    /**
     * 模具孔径
     */
    private String moldAperture;

    public Long getMoldStorageId() {
        return moldStorageId;
    }

    public Date getStartDate() {
        return startDate;
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

    public void setMoldStorageId(Long moldStorageId) {
        this.moldStorageId = moldStorageId;
    }

    public List<MoldLedger> getMoldLedgerList() {
        return moldLedgerList;
    }

    public MoldNesting getMoldNesting() {
        return moldNesting;
    }

    public void setMoldNesting(MoldNesting moldNesting) {
        this.moldNesting = moldNesting;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setMoldLedgerList(List<MoldLedger> moldLedgerList) {
        this.moldLedgerList = moldLedgerList;
    }

    public MoldLedger getMoldLedger() {
        return moldLedger;
    }

    public void setMoldLedger(MoldLedger moldLedger) {
        this.moldLedger = moldLedger;
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

    public Long getMoldId() {
        return moldId;
    }

    public void setMoldId(Long moldId) {
        this.moldId = moldId;
    }

    public Long getNestingId() {
        return nestingId;
    }

    public void setNestingId(Long nestingId) {
        this.nestingId = nestingId;
    }

    public Long getLsId() {
        return lsId;
    }

    public void setLsId(Long lsId) {
        this.lsId = lsId;
    }

    public Long getQbId() {
        return qbId;
    }

    public void setQbId(Long qbId) {
        this.qbId = qbId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Date getMoldStorageTime() {
        return moldStorageTime;
    }

    public void setMoldStorageTime(Date moldStorageTime) {
        this.moldStorageTime = moldStorageTime;
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

    public String getMoldArea() {
        return moldArea;
    }

    public void setMoldArea(String moldArea) {
        this.moldArea = moldArea;
    }

    public String getMoldName() {
        return moldName;
    }

    public void setMoldName(String moldName) {
        this.moldName = moldName;
    }

    public String getMoldMaterial() {
        return moldMaterial;
    }

    public void setMoldMaterial(String moldMaterial) {
        this.moldMaterial = moldMaterial;
    }

    public String getMoldAperture() {
        return moldAperture;
    }

    public void setMoldAperture(String moldAperture) {
        this.moldAperture = moldAperture;
    }
}

