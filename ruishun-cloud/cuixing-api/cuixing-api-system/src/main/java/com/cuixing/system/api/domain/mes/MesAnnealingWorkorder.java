package com.cuixing.system.api.domain.mes;

import com.cuixing.common.core.web.domain.BaseEntity;
import com.cuixing.system.api.domain.mes.dto.Message;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;
import java.util.Date;

/**
 * 退火工单(MesAnnealingWorkorder)实体类
 *
 * @author 阿江
 * @since 2024-11-25 10:30:34
 */
public class MesAnnealingWorkorder extends BaseEntity {
    /**
     * 自增主键
     */
    private BigInteger id;
    /**
     * 生产工单id
     */
    private BigInteger workorderId;
    /**
     * 订单类型(0：客户订单 1：备库订单)
     */
    private String orderType;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 生产工单号
     */
    private String workorderCode;
    /**
     * 退火工单号
     */
    private String annealingCode;
    /**
     * 设备id
     */
    private BigInteger equipmentId;
    /**
     * 设备编号
     */
    private String equipmentCode;
    /**
     * 轴号
     */
    private String axleNumber;
    /**
     * 线盘
     */
    private String wireDisc;
    /**
     * 产品id
     */
    private BigInteger productId;
    /**
     * 产品规格
     */
    private String specification;
    /**
     * 产品型号
     */
    private String model;
    /**
     * 重量
     */
    private Double weight;
    /**
     * 单位id（单位表）
     */
    private BigInteger measureId;
    /**
     * 单位名称（单位表）
     */
    private String measureName;
    /**
     * 盘数
     */
    private Integer discNumber;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;
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
     * 状态( 0:已排产 1:生产中 2:已完成3：已作废)
     */
    private String status;
    /**
     * 预留字段1（拉丝是否排产 ： Y ： 已排产， N :未排除）
     */
    private String attr1;
    /**
     * 预留字段2(标准)
     */
    private Integer attr2;
    /**
     * 预留字段3
     */
    private String attr3;
    /**
     * 插单id/被插单id
     */
    private BigInteger insertId;
    /**
     * 插单工单号
     */
    private String insertWorkorder;
    /**
     * 插单型号
     */
    private String insertModel;
    /**
     * 插单状态(0：正常 1：插单中 2：被插单)
     */
    private String insertStatus;
    /**
     * 线盘数量
     */
    private Integer wireDiscNumber;
    /**
     * 完成度
     */
    private Double degreeOfCompletion;
    /**
     * 已完成重量
     */
    private Double wightCompletion;
    /**
     * 标准名称
     */
    private String standardName;
    /**
     * 标准值
     */
    private Integer standardValue;
    /**
     * 线盘id
     */
    private BigInteger wireDiscId;
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
     * 消息体
     * @return
     */
    private Message message;
    /**
     * 机台id
     */
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(BigInteger workorderId) {
        this.workorderId = workorderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getWorkorderCode() {
        return workorderCode;
    }

    public void setWorkorderCode(String workorderCode) {
        this.workorderCode = workorderCode;
    }

    public String getAnnealingCode() {
        return annealingCode;
    }

    public void setAnnealingCode(String annealingCode) {
        this.annealingCode = annealingCode;
    }

    public BigInteger getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(BigInteger equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getAxleNumber() {
        return axleNumber;
    }

    public void setAxleNumber(String axleNumber) {
        this.axleNumber = axleNumber;
    }

    public String getWireDisc() {
        return wireDisc;
    }

    public void setWireDisc(String wireDisc) {
        this.wireDisc = wireDisc;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public BigInteger getMeasureId() {
        return measureId;
    }

    public void setMeasureId(BigInteger measureId) {
        this.measureId = measureId;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public Integer getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(Integer discNumber) {
        this.discNumber = discNumber;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public BigInteger getInsertId() {
        return insertId;
    }

    public void setInsertId(BigInteger insertId) {
        this.insertId = insertId;
    }

    public String getInsertWorkorder() {
        return insertWorkorder;
    }

    public void setInsertWorkorder(String insertWorkorder) {
        this.insertWorkorder = insertWorkorder;
    }

    public String getInsertModel() {
        return insertModel;
    }

    public void setInsertModel(String insertModel) {
        this.insertModel = insertModel;
    }

    public String getInsertStatus() {
        return insertStatus;
    }

    public void setInsertStatus(String insertStatus) {
        this.insertStatus = insertStatus;
    }

    public Integer getWireDiscNumber() {
        return wireDiscNumber;
    }

    public void setWireDiscNumber(Integer wireDiscNumber) {
        this.wireDiscNumber = wireDiscNumber;
    }

    public Double getDegreeOfCompletion() {
        return degreeOfCompletion;
    }

    public void setDegreeOfCompletion(Double degreeOfCompletion) {
        this.degreeOfCompletion = degreeOfCompletion;
    }

    public Double getWightCompletion() {
        return wightCompletion;
    }

    public void setWightCompletion(Double wightCompletion) {
        this.wightCompletion = wightCompletion;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public Integer getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Integer standardValue) {
        this.standardValue = standardValue;
    }

    public BigInteger getWireDiscId() {
        return wireDiscId;
    }

    public void setWireDiscId(BigInteger wireDiscId) {
        this.wireDiscId = wireDiscId;
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

