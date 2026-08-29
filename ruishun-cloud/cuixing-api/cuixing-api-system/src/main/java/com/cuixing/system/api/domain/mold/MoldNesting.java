package com.cuixing.system.api.domain.mold;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 拉丝套模表(MoldNesting)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:01
 */
public class MoldNesting implements Serializable {
    private static final long serialVersionUID = 807550536227120190L;
    /**
     * 拉丝套模id
     */
    private Long nestingMoldId;
    /**
     * 模具编号
     */
    private String nestingName;
    /**
     * 存放位置
     */
    private String nestingArea;
    /**
     * 当前在用规格
     */
    private String nestingSpec;
    /**
     * 套模范围
     */
    private String nestingScope;
    /**
     * 保养状态
     */
    private String status;
    /**
     * 计划id
     */
    private Long planId;
    /**
     * 是否启用
     */
    private String enable;
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
     * 台账列表
     */
    private List<MoldLedger> moldLedgers;
    /**
     * 计划名称
     */
    private String planName;

    public MoldNesting(Long nestingMoldId, String nestingSpec, String nestingScope) {
        this.nestingMoldId = nestingMoldId;
        this.nestingSpec = nestingSpec;
        this.nestingScope = nestingScope;
    }

    public MoldNesting() {
    }

    public Long getNestingMoldId() {
        return nestingMoldId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void setNestingMoldId(Long nestingMoldId) {
        this.nestingMoldId = nestingMoldId;
    }

    public String getNestingName() {
        return nestingName;
    }

    public List<MoldLedger> getMoldLedgers() {
        return moldLedgers;
    }

    public void setMoldLedgers(List<MoldLedger> moldLedgers) {
        this.moldLedgers = moldLedgers;
    }

    public void setNestingName(String nestingName) {
        this.nestingName = nestingName;
    }

    public String getNestingArea() {
        return nestingArea;
    }

    public void setNestingArea(String nestingArea) {
        this.nestingArea = nestingArea;
    }

    public String getNestingSpec() {
        return nestingSpec;
    }

    public void setNestingSpec(String nestingSpec) {
        this.nestingSpec = nestingSpec;
    }

    public String getNestingScope() {
        return nestingScope;
    }

    public void setNestingScope(String nestingScope) {
        this.nestingScope = nestingScope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
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

