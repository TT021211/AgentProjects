package com.cuixing.wms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 发货通知单审核记录表(WmsNoticeInvoiceRecord)实体类
 *
 * @author makejava
 * @since 2024-10-30 14:09:47
 */
public class WmsNoticeInvoiceRecord implements Serializable {
    private static final long serialVersionUID = 848700412754564600L;
/**
     * 发货通知单审核记录id
     */
    private Long id;
/**
     * 发货通知单id
     */
    private Long noticeInvoiceId;
/**
     * 发货通知单编码
     */
    private String noticeInvoiceCode;
/**
     * 执行前状态
     */
    private String beforeStatus;
/**
     * 执行后状态
     */
    private String afterStatus;
/**
     * 执行动作
     */
    private String action;
/**
     * 备注
     */
    private String remake;
/**
     * 创建人
     */
    private String createBy;
/**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
/**
     * 备用字段1
     */
    private String artt1;
/**
     * 备用字段2
     */
    private String artt2;
/**
     * 备用字段3
     */
    private Integer artt3;
/**
     * 备用字段4
     */
    private Integer artt4;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoticeInvoiceId() {
        return noticeInvoiceId;
    }

    public void setNoticeInvoiceId(Long noticeInvoiceId) {
        this.noticeInvoiceId = noticeInvoiceId;
    }

    public String getNoticeInvoiceCode() {
        return noticeInvoiceCode;
    }

    public void setNoticeInvoiceCode(String noticeInvoiceCode) {
        this.noticeInvoiceCode = noticeInvoiceCode;
    }

    public String getBeforeStatus() {
        return beforeStatus;
    }

    public void setBeforeStatus(String beforeStatus) {
        this.beforeStatus = beforeStatus;
    }

    public String getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(String afterStatus) {
        this.afterStatus = afterStatus;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getArtt1() {
        return artt1;
    }

    public void setArtt1(String artt1) {
        this.artt1 = artt1;
    }

    public String getArtt2() {
        return artt2;
    }

    public void setArtt2(String artt2) {
        this.artt2 = artt2;
    }

    public Integer getArtt3() {
        return artt3;
    }

    public void setArtt3(Integer artt3) {
        this.artt3 = artt3;
    }

    public Integer getArtt4() {
        return artt4;
    }

    public void setArtt4(Integer artt4) {
        this.artt4 = artt4;
    }

}

