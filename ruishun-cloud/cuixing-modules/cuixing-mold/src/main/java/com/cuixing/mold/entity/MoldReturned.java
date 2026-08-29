package com.cuixing.mold.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 模具待归还表(MoldReturned)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:02
 */
public class MoldReturned implements Serializable {
    private static final long serialVersionUID = -48587295801500502L;
    /**
     * 待归还id
     */
    private Long moldReturnedId;
    /**
     * 拉丝套模id
     */
    private Long nestingMoldId;
    /**
     * 入库ls机台id
     */
    private Long lsId;
    /**
     * 出口模孔径
     */
    private String moldOutAperture;
    /**
     * 出口模编号
     */
    private String moldOutCode;
    /**
     * 存放位置
     */
    private String moldOutArea;
    /**
     * 模具id
     */
    private Long moldId;
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
     * 入库qb机台id
     */
    private Long qbId;
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
     * lsName
     */
    private String lsName;
    /**
     * qbName
     */
    private String qbName;
    /**
     * 归还人
     */
    private String inBy;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 模具集合
     * @return
     */
    private List<MoldLedger> moldLedgerList;
    /**
     * 模具集合
     * @return
     */
    private MoldLedger moldLedger;
    /**
     * 套模对象
     * @return
     */
    private MoldNesting moldNesting;

    private String remark;

    private String nestingName;

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

    public Long getMoldReturnedId() {
        return moldReturnedId;
    }

    public String getInBy() {
        return inBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public MoldLedger getMoldLedger() {
        return moldLedger;
    }

    public void setMoldLedger(MoldLedger moldLedger) {
        this.moldLedger = moldLedger;
    }

    public void setInBy(String inBy) {
        this.inBy = inBy;
    }

    public void setMoldReturnedId(Long moldReturnedId) {
        this.moldReturnedId = moldReturnedId;
    }

    public Long getNestingMoldId() {
        return nestingMoldId;
    }

    public void setNestingMoldId(Long nestingMoldId) {
        this.nestingMoldId = nestingMoldId;
    }

    public List<MoldLedger> getMoldLedgerList() {
        return moldLedgerList;
    }

    public void setMoldLedgerList(List<MoldLedger> moldLedgerList) {
        this.moldLedgerList = moldLedgerList;
    }

    public Long getLsId() {
        return lsId;
    }

    public void setLsId(Long lsId) {
        this.lsId = lsId;
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

    public String getMoldOutAperture() {
        return moldOutAperture;
    }

    public void setMoldOutAperture(String moldOutAperture) {
        this.moldOutAperture = moldOutAperture;
    }

    public String getMoldOutCode() {
        return moldOutCode;
    }

    public void setMoldOutCode(String moldOutCode) {
        this.moldOutCode = moldOutCode;
    }

    public String getMoldOutArea() {
        return moldOutArea;
    }

    public void setMoldOutArea(String moldOutArea) {
        this.moldOutArea = moldOutArea;
    }

    public Long getMoldId() {
        return moldId;
    }

    public MoldNesting getMoldNesting() {
        return moldNesting;
    }

    public void setMoldNesting(MoldNesting moldNesting) {
        this.moldNesting = moldNesting;
    }

    public void setMoldId(Long moldId) {
        this.moldId = moldId;
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

    public Long getQbId() {
        return qbId;
    }

    public void setQbId(Long qbId) {
        this.qbId = qbId;
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

    public String getNestingName() {
        return nestingName;
    }

    public void setNestingName(String nestingName) {
        this.nestingName = nestingName;
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

    public String getMoldArea() {
        return moldArea;
    }

    public void setMoldArea(String moldArea) {
        this.moldArea = moldArea;
    }

    public String getMoldSpec() {
        return moldSpec;
    }

    public void setMoldSpec(String moldSpec) {
        this.moldSpec = moldSpec;
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

