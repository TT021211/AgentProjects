package com.cuixing.md.entity;

import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MdPrintTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long templateId;
    private Long userId;
    private String userNickName;
    private Long clientId;
    private String clientCode;
    private String clientNick;

    @Excel(
            name = "客户名称"
    )
    private String clientName;
    @Excel(
            name = "模板编码"
    )
    private String templateCode;
    @Excel(
            name = "模板名称"
    )
    private String templateName;
    @Excel(
            name = "模板内容"
    )
    private String content;
    private String defaultPrinterName;

    private String status;

    public MdPrintTemplate() {
    }

    public String getClientNick() {
        return clientNick;
    }

    public void setClientNick(String clientNick) {
        this.clientNick = clientNick;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return this.templateId;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateCode() {
        return this.templateCode;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public String getDefaultPrinterName() {
        return this.defaultPrinterName;
    }

    public void setDefaultPrinterName(String defaultPrinterName) {
        this.defaultPrinterName = defaultPrinterName;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientCode() {
        return this.clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return this.userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String toString() {
        return (new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)).append("templateId", this.getTemplateId()).append("templateCode", this.getTemplateCode()).append("templateName", this.getTemplateName()).append("content", this.getContent()).append("createBy", this.getCreateBy()).append("createTime", this.getCreateTime()).append("updateBy", this.getUpdateBy()).append("updateTime", this.getUpdateTime()).toString();
    }

    public String getCode() {
        return this.templateCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
