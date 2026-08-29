package com.cuixing.system.api.domain.mold;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 模具台账表(MoldLedger)实体类
 *
 * @author makejava
 * @since 2024-06-28 10:44:01
 */
public class MoldLedger implements Serializable {
    private static final long serialVersionUID = 895373630633335523L;
    /**
     * 模具id
     */
    private Long moldId;
    /**
     * 类型
     */
    private String moldType;
    /**
     * 型号
     */
    private String moldSpec;
    /**
     * 材质
     */
    private String moldMaterial;
    /**
     * 模具孔径
     */
    private String moldAperture;
    /**
     * 公差
     */
    private BigDecimal engineeringTolerance;
    /**
     * 模具数量
     */
    private Long moldNum;
    /**
     * 存放位置
     */
    private String moldArea;
    /**
     * 套模名称
     */
    private String moldName;
    /**
     * 维修保养状态
     */
    private String maintenanceStatus;
    /**
     * 套模id
     */
    private Long nestingMoldId;
    /**
     * 分类id
     */
    private Long classificationId;
    /**
     * 模具编码
     */
    private String moldCode;
    /**
     * 供应商id
     */
    private Long moldSupplierId;
    /**
     * 供应商名称
     */
    private String moldSupplierName;
    /**
     * 状态
     */
    private String status;
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
     * 报废状态
     */
    private String restructutingStatus;
    /**
     * 出入库状态
     */
    private String inOutStatus;
    /**
     * 是否删除(0否，1是)
     */
    private String isDelete;
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

    public MoldLedger() {
    }

    public MoldLedger(String status,Long nestingMoldId) {
        this.status = status;
        this.nestingMoldId = nestingMoldId;
    }

    public BigDecimal getEngineeringTolerance() {
        return engineeringTolerance;
    }

    public void setEngineeringTolerance(BigDecimal engineeringTolerance) {
        this.engineeringTolerance = engineeringTolerance;
    }

    public Long getMoldId() {
        return moldId;
    }

    public void setMoldId(Long moldId) {
        this.moldId = moldId;
    }

    public String getMoldSpec() {
        return moldSpec;
    }

    public void setMoldSpec(String moldSpec) {
        this.moldSpec = moldSpec;
    }

    public String getMoldType() {
        return moldType;
    }

    public void setMoldType(String moldType) {
        this.moldType = moldType;
    }

    public void setRestructutingStatus(String restructutingStatus) {
        this.restructutingStatus = restructutingStatus;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public String getRestructutingStatus() {
        return restructutingStatus;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getMoldSupplierName() {
        return moldSupplierName;
    }

    public void setMoldSupplierName(String moldSupplierName) {
        this.moldSupplierName = moldSupplierName;
    }

    public String getMoldMaterial() {
        return moldMaterial;
    }

    public void setMoldMaterial(String moldMaterial) {
        this.moldMaterial = moldMaterial;
    }

    public String getMoldAperture() {
        return moldAperture;
    }

    public void setMoldAperture(String moldAperture) {
        this.moldAperture = moldAperture;
    }

    public Long getMoldNum() {
        return moldNum;
    }

    public void setMoldNum(Long moldNum) {
        this.moldNum = moldNum;
    }

    public String getMoldArea() {
        return moldArea;
    }

    public void setMoldArea(String moldArea) {
        this.moldArea = moldArea;
    }

    public String getMoldName() {
        return moldName;
    }

    public void setMoldName(String moldName) {
        this.moldName = moldName;
    }

    public String getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(String maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

    public Long getNestingMoldId() {
        return nestingMoldId;
    }

    public void setNestingMoldId(Long nestingMoldId) {
        this.nestingMoldId = nestingMoldId;
    }

    public Long getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Long classificationId) {
        this.classificationId = classificationId;
    }

    public String getMoldCode() {
        return moldCode;
    }

    public void setMoldCode(String moldCode) {
        this.moldCode = moldCode;
    }

    public Long getMoldSupplierId() {
        return moldSupplierId;
    }

    public void setMoldSupplierId(Long moldSupplierId) {
        this.moldSupplierId = moldSupplierId;
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

    public String getInOutStatus() {
        return inOutStatus;
    }

    public void setInOutStatus(String inOutStatus) {
        this.inOutStatus = inOutStatus;
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

