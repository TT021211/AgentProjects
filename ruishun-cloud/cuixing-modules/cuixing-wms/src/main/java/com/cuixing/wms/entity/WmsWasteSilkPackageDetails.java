package com.cuixing.wms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;


/**
 * 废丝包详情表(WmsWasteSilkPackageDetails)表服务类
 *
 * @author 阿江
 * @since 2025-04-18 14:20:55
 */
public class WmsWasteSilkPackageDetails implements Serializable {
    private static final long serialVersionUID = 766174519996466207L;
    /**
     * 废丝副表
     */
    private Long id;
    /**
     * 废丝包id
     */
    private Integer wasteSilkPackageId;
    /**
     * 废丝包编码
     */
    private String wasteSilkPackageCode;
    /**
     * 废丝包名称
     */
    private String wasteSilkPackageName;
    /**
     * 废丝包类型
     */
    private String wasteSilkPackageType;
    /**
     * 机台负责人
     */
    private String userName;
    /**
     * 机台负责人工号
     */
    private String jobNumber;
    /**
     * 工序
     */
    private String mesProcess;
    /**
     * 废丝重量
     */
    private BigDecimal weight;
    /**
     * 状态（1：未装包、2：已装包、3：已出库）
     */
    private String status;
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
     * 预留字段1
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
    private Integer artt4;
    /**
     * 备用字段5
     */
    private String artt5;
    /**
     * 备用字段6
     */
    private String artt6;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWasteSilkPackageId() {
        return wasteSilkPackageId;
    }

    public void setWasteSilkPackageId(Integer wasteSilkPackageId) {
        this.wasteSilkPackageId = wasteSilkPackageId;
    }

    public String getWasteSilkPackageCode() {
        return wasteSilkPackageCode;
    }

    public void setWasteSilkPackageCode(String wasteSilkPackageCode) {
        this.wasteSilkPackageCode = wasteSilkPackageCode;
    }

    public String getWasteSilkPackageName() {
        return wasteSilkPackageName;
    }

    public void setWasteSilkPackageName(String wasteSilkPackageName) {
        this.wasteSilkPackageName = wasteSilkPackageName;
    }

    public String getWasteSilkPackageType() {
        return wasteSilkPackageType;
    }

    public void setWasteSilkPackageType(String wasteSilkPackageType) {
        this.wasteSilkPackageType = wasteSilkPackageType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getMesProcess() {
        return mesProcess;
    }

    public void setMesProcess(String mesProcess) {
        this.mesProcess = mesProcess;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}

