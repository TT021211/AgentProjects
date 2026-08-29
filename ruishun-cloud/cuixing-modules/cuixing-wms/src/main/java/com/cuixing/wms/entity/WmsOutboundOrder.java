package com.cuixing.wms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 成品出库单(WmsOutboundOrder)实体类
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
public class WmsOutboundOrder implements Serializable {
    private static final long serialVersionUID = -15052875928052382L;
    /**
     * 出库单id
     */
    private Long id;
    /**
     * 出库单号
     */
    private String outboundCode;
    /**
     * 出库类型
     */
    private String outboundName;
    /**
     * 出库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outboundDate;
    /**
     * 出库状态
     */
    private String outboundStatus;
    /**
     * 出库类型
     */
    private String outboundType;
    /**
     * 来源单据号
     */
    private String documentNumber;
    /**
     * 明细数量
     */
    private String detailedQuantity;
    /**
     * 明细信息
     */
    private String detailedInformation;
    /**
     * 明细重量
     */
    private String detailedWeight;
    /**
     * 明细类别
     */
    private String detailCategory;
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
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
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
    /**
     * 备用字段5
     */
    private String artt5;
    /**
     * 备用字段6
     */
    private String artt6;
    /**
     * 备用字段7
     */
    private String artt7;
    /**
     * 备用字段8
     */
    private String artt8;
    /**
     * 备用字段9
     */
    private String artt9;
    /**
     * 备用字段10
     */
    private String artt10;

    /**
     *
     */
    private List<WmsBox> wmsBoxes;

    public List<WmsBox> getWmsBoxes() {
        return wmsBoxes;
    }

    public void setWmsBoxes(List<WmsBox> wmsBoxes) {
        this.wmsBoxes = wmsBoxes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutboundCode() {
        return outboundCode;
    }

    public void setOutboundCode(String outboundCode) {
        this.outboundCode = outboundCode;
    }

    public String getOutboundName() {
        return outboundName;
    }

    public void setOutboundName(String outboundName) {
        this.outboundName = outboundName;
    }

    public Date getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(Date outboundDate) {
        this.outboundDate = outboundDate;
    }

    public String getOutboundStatus() {
        return outboundStatus;
    }

    public void setOutboundStatus(String outboundStatus) {
        this.outboundStatus = outboundStatus;
    }

    public String getOutboundType() {
        return outboundType;
    }

    public void setOutboundType(String outboundType) {
        this.outboundType = outboundType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDetailedQuantity() {
        return detailedQuantity;
    }

    public void setDetailedQuantity(String detailedQuantity) {
        this.detailedQuantity = detailedQuantity;
    }

    public String getDetailedInformation() {
        return detailedInformation;
    }

    public void setDetailedInformation(String detailedInformation) {
        this.detailedInformation = detailedInformation;
    }

    public String getDetailedWeight() {
        return detailedWeight;
    }

    public void setDetailedWeight(String detailedWeight) {
        this.detailedWeight = detailedWeight;
    }

    public String getDetailCategory() {
        return detailCategory;
    }

    public void setDetailCategory(String detailCategory) {
        this.detailCategory = detailCategory;
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

    public String getArtt5() {
        return artt5;
    }

    public void setArtt5(String artt5) {
        this.artt5 = artt5;
    }

    public String getArtt6() {
        return artt6;
    }

    public void setArtt6(String artt6) {
        this.artt6 = artt6;
    }

    public String getArtt7() {
        return artt7;
    }

    public void setArtt7(String artt7) {
        this.artt7 = artt7;
    }

    public String getArtt8() {
        return artt8;
    }

    public void setArtt8(String artt8) {
        this.artt8 = artt8;
    }

    public String getArtt9() {
        return artt9;
    }

    public void setArtt9(String artt9) {
        this.artt9 = artt9;
    }

    public String getArtt10() {
        return artt10;
    }

    public void setArtt10(String artt10) {
        this.artt10 = artt10;
    }

}

