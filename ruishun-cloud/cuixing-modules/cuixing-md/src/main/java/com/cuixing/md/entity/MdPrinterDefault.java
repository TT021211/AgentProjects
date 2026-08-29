package com.cuixing.md.entity;

import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MdPrinterDefault extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long printerDefaultId;
    @Excel(
            name = "模板id"
    )
    private Long templateId;
    @Excel(
            name = "用户id"
    )
    private Long userId;
    @Excel(
            name = "打印机名称"
    )
    private String printerName;

    public MdPrinterDefault() {
    }

    public void setPrinterDefaultId(Long printerDefaultId) {
        this.printerDefaultId = printerDefaultId;
    }

    public Long getPrinterDefaultId() {
        return this.printerDefaultId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return this.templateId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterName() {
        return this.printerName;
    }

    public String toString() {
        return (new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)).append("printerDefaultId", this.getPrinterDefaultId()).append("templateId", this.getTemplateId()).append("userId", this.getUserId()).append("printerName", this.getPrinterName()).toString();
    }

    public String getCode() {
        return null;
    }
}
