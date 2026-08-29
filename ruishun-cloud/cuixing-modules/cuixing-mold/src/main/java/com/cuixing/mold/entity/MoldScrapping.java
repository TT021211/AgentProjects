package com.cuixing.mold.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 模具报废表(MoldScrapping)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:02
 */
public class MoldScrapping implements Serializable {
    private static final long serialVersionUID = -61822610611630065L;
    /**
     * 报废id
     */
    private Long moldScrappingId;
    /**
     * 模具id
     */
    private Long moldId;
    /**
     * 套模id
     */
    private Long nestingMoldId;
    /**
     * 报废时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date scrappingDate;
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
     * 报废原因
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
     * 预留字段4
     */
    private String status;
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
     * 模具对象
     *
     * @return
     */
    private MoldLedger moldLedger;
    /**
     * 模具集合
     *
     * @return
     */
    private List<MoldLedger> moldLedgerList;

    /**
     * 套模名称
     *
     * @return
     */
    private String nestingName;
    /**
     * 套模名称
     *
     * @return
     */
    private String moldType;
    /**
     * 套模名称
     *
     * @return
     */
    private String moldCode;
    /**
     * 套模名称
     *
     * @return
     */
    private String moldSpec;
    /**
     * 套模名称
     *
     * @return
     */
    private String moldMaterial;
    /**
     * 套模名称
     *
     * @return
     */
    private String area;

    public Long getMoldScrappingId() {
        return moldScrappingId;
    }

    public Date getStartDate() {
        return startDate;
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

    public void setMoldScrappingId(Long moldScrappingId) {
        this.moldScrappingId = moldScrappingId;
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

    public Long getMoldId() {
        return moldId;
    }

    public void setMoldId(Long moldId) {
        this.moldId = moldId;
    }

    public Long getNestingMoldId() {
        return nestingMoldId;
    }

    public void setNestingMoldId(Long nestingMoldId) {
        this.nestingMoldId = nestingMoldId;
    }

    public Date getScrappingDate() {
        return scrappingDate;
    }

    public void setScrappingDate(Date scrappingDate) {
        this.scrappingDate = scrappingDate;
    }

    public String getRemark() {
        return remark;
    }

    public MoldLedger getMoldLedger() {
        return moldLedger;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNestingName() {
        return nestingName;
    }

    public void setNestingName(String nestingName) {
        this.nestingName = nestingName;
    }

    public String getMoldType() {
        return moldType;
    }

    public void setMoldType(String moldType) {
        this.moldType = moldType;
    }

    public String getMoldCode() {
        return moldCode;
    }

    public void setMoldCode(String moldCode) {
        this.moldCode = moldCode;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}

