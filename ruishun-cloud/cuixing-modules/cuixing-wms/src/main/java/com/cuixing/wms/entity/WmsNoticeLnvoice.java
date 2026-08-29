package com.cuixing.wms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 发货通知单(WmsNoticeLnvoice)实体类
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
public class WmsNoticeLnvoice implements Serializable {
    private static final long serialVersionUID = 933368349118275944L;
    /**
     * 发货通知单id
     */
    private Long id;
    /**
     * 单据编号
     */
    private String documentNumber;
    /**
     * 单据类型
     */
    private String documentType;
    /**
     * 客户名称
     */
    private String trader;
    /**
     * 单据状态
     */
    private String documentStatus;
    /**
     * 发货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date shipmentDate;
    /**
     * 明细信息
     */
    private String detailedInformation;
    /**
     * 明细重量
     */
    private String detailedWeight;
    /**
     * 明细数量
     */
    private String detailedQuantity;
    /**
     * 出库仓库
     */
    private String outboundWarehouse;
    /**
     * 送货方式
     */
    private String deliveryMethod;
    /**
     * 付款方式
     */
    private String paymentMethod;
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

    private List<WmsProductInout> wmsProductInoutList;

    private List<WmsBox> wmsBoxList;

    public List<WmsBox> getWmsBoxList() {
        return wmsBoxList;
    }

    public void setWmsBoxList(List<WmsBox> wmsBoxList) {
        this.wmsBoxList = wmsBoxList;
    }

    public List<WmsProductInout> getWmsProductInoutList() {
        return wmsProductInoutList;
    }

    public void setWmsProductInoutList(List<WmsProductInout> wmsProductInoutList) {
        this.wmsProductInoutList = wmsProductInoutList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
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

    public String getDetailedQuantity() {
        return detailedQuantity;
    }

    public void setDetailedQuantity(String detailedQuantity) {
        this.detailedQuantity = detailedQuantity;
    }

    public String getOutboundWarehouse() {
        return outboundWarehouse;
    }

    public void setOutboundWarehouse(String outboundWarehouse) {
        this.outboundWarehouse = outboundWarehouse;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

