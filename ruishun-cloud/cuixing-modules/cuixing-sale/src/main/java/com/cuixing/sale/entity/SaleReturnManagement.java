package com.cuixing.sale.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;


/**
 * 销售退货管理(SaleReturnManagement)表服务类
 *
 * @author 阿江
 * @since 2025-03-04 15:10:39
 */
public class SaleReturnManagement implements Serializable {
    private static final long serialVersionUID = -41061413353396323L;
    /**
     * 退货id
     */
    private Long id;
    /**
     * 类型
     */
    private String type;
    /**
     * 销售编号
     */
    private String saleCode;
    /**
     * 退货重量
     */
    private Double returnWeight;
    /**
     * 退货数量
     */
    private Integer returnNumber;
    /**
     * 单位id（单位表）
     */
    private Long measureId;
    /**
     * 单位名称（单位表）
     */
    private String measureName;
    /**
     * 退货二维码
     */
    private String returnQrcode;
    /**
     * 退货名称
     */
    private String returnName;
    /**
     * 退货型号
     */
    private String returnModel;
    /**
     * 退货规格
     */
    private String returnSpec;
    /**
     * 退货供应商
     */
    private String returnClient;
    /**
     * 退货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnTime;
    /**
     * 逻辑删除
     */
    private Integer isDeleted;
    /**
     * 乐观锁
     */
    private Integer version;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 备用字段1
     */
    private String attr1;
    /**
     * 预留字段2
     */
    private Integer attr2;
    /**
     * 预留字段3
     */
    private Object attr3;
    /**
     * 预留字段4
     */
    private String artt4;
    /**
     * 预留字段5
     */
    private String artt5;
    /**
     * 预留字段6
     */
    private String artt6;
    /**
     * 预留字段7
     */
    private String artt7;
    /**
     * 预留字段8
     */
    private String artt8;
    /**
     * 预留字段9
     */
    private String artt9;
    /**
     * 预留字段10
     */
    private String artt10;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public Double getReturnWeight() {
        return returnWeight;
    }

    public void setReturnWeight(Double returnWeight) {
        this.returnWeight = returnWeight;
    }

    public Integer getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(Integer returnNumber) {
        this.returnNumber = returnNumber;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public String getReturnQrcode() {
        return returnQrcode;
    }

    public void setReturnQrcode(String returnQrcode) {
        this.returnQrcode = returnQrcode;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public String getReturnModel() {
        return returnModel;
    }

    public void setReturnModel(String returnModel) {
        this.returnModel = returnModel;
    }

    public String getReturnSpec() {
        return returnSpec;
    }

    public void setReturnSpec(String returnSpec) {
        this.returnSpec = returnSpec;
    }

    public String getReturnClient() {
        return returnClient;
    }

    public void setReturnClient(String returnClient) {
        this.returnClient = returnClient;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public String getRemark() {
        return remark;
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

    public Integer getAttr2() {
        return attr2;
    }

    public void setAttr2(Integer attr2) {
        this.attr2 = attr2;
    }

    public Object getAttr3() {
        return attr3;
    }

    public void setAttr3(Object attr3) {
        this.attr3 = attr3;
    }

    public String getArtt4() {
        return artt4;
    }

    public void setArtt4(String artt4) {
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

