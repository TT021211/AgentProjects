package com.cuixing.mold.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 模具保养表--保养设置表(MoldMaintainItem)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:01
 */
public class MoldMaintainItem implements Serializable {
    private static final long serialVersionUID = -41765237915374602L;
    /**
     * 保养项id
     */
    private Long maintainItemId;
    /**
     * 项目名称
     */
    private String maintainItemName;
    /**
     * 项目内容
     */
    private String maintainItemContent;
    /**
     * 项目标准
     */
    private String maintainItemStandard;
    /**
     * 是否启用
     */
    private String maintainItemEnable;
    /**
     * 项目备注
     */
    private String maintainItemRemark;
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


    public Long getMaintainItemId() {
        return maintainItemId;
    }

    public void setMaintainItemId(Long maintainItemId) {
        this.maintainItemId = maintainItemId;
    }

    public String getMaintainItemName() {
        return maintainItemName;
    }

    public void setMaintainItemName(String maintainItemName) {
        this.maintainItemName = maintainItemName;
    }

    public String getMaintainItemContent() {
        return maintainItemContent;
    }

    public void setMaintainItemContent(String maintainItemContent) {
        this.maintainItemContent = maintainItemContent;
    }

    public String getMaintainItemStandard() {
        return maintainItemStandard;
    }

    public void setMaintainItemStandard(String maintainItemStandard) {
        this.maintainItemStandard = maintainItemStandard;
    }

    public String getMaintainItemEnable() {
        return maintainItemEnable;
    }

    public void setMaintainItemEnable(String maintainItemEnable) {
        this.maintainItemEnable = maintainItemEnable;
    }

    public String getMaintainItemRemark() {
        return maintainItemRemark;
    }

    public void setMaintainItemRemark(String maintainItemRemark) {
        this.maintainItemRemark = maintainItemRemark;
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

