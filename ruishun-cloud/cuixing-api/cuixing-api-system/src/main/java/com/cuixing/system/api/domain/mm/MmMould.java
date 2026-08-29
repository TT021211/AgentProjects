package com.cuixing.system.api.domain.mm;

import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;

/**
 * 模具明细对象 mm_mould
 *
 * @author duan
 * @date 2024-01-29
 */
public class MmMould extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 模具id
	 */
	private Long mouldId;

	/**
	 * 模具名称
	 */
	@Excel(name = "模具名称")
	private String mouldName;

	/**
	 * 模具分类id
	 */
	@Excel(name = "模具分类id")
	private Long mouldClassifyId;

	/**
	 * 模具分类
	 */
	@Excel(name = "模具分类")
	private String mouldClassify;

	/**
	 * 模具编码
	 */
	@Excel(name = "模具编码")
	private String mouldCode;

	/**
	 * 模具尺寸
	 */
	@Excel(name = "模具尺寸")
	private String mouldSize;

	/**
	 * 公差
	 */
	@Excel(name = "公差")
	private String mouldTole;

	/**
	 * 来源
	 */
	@Excel(name = "来源")
	private String source;

	/**
	 *  所属客户id
	 */
	@Excel(name = "客户id")
	private Long customerId;

	/**
	 *  所属客户id
	 */
	@Excel(name = "所属客户")
	private String customerName;

	/**
	 * 状态
	 */
	@Excel(name = "状态")
	private String status;

	/**
	 * 仓位类型
	 */
	@Excel(name = "仓位类型")
	private Integer positionClassify;

	/**
	 * 仓位
	 */
	@Excel(name = "仓位")
	private String positionName;



	private String positionJson;

	@Excel(name = "采购单id")
	private Long purchaseId;

	@Excel(name="采购单编码")
	private String purchaseCode;

	@Excel(name="采购详情Id")
	private Long detailId;

	@Excel(name = "采购产品")
	private String purchaseName;

	@Excel(name = "出库状态")
	private Integer outStatus;

	public Long getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getPurchaseName() {
		return purchaseName;
	}

	public void setPurchaseName(String purchaseName) {
		this.purchaseName = purchaseName;
	}

	public void setMouldId(Long mouldId) {
		this.mouldId = mouldId;
	}

	public Long getMouldId() {
		return mouldId;
	}

	public void setMouldName(String mouldName) {
		this.mouldName = mouldName;
	}

	public String getMouldName() {
		return mouldName;
	}

	public Long getMouldClassifyId() {
		return mouldClassifyId;
	}

	public void setMouldClassifyId(Long mouldClassifyId) {
		this.mouldClassifyId = mouldClassifyId;
	}

	public void setMouldClassify(String mouldClassify) {
		this.mouldClassify = mouldClassify;
	}

	public String getMouldClassify() {
		return mouldClassify;
	}

	public void setMouldCode(String mouldCode) {
		this.mouldCode = mouldCode;
	}

	public String getMouldCode() {
		return mouldCode;
	}

	public void setMouldSize(String mouldSize) {
		this.mouldSize = mouldSize;
	}

	public String getMouldSize() {
		return mouldSize;
	}

	public void setMouldTole(String mouldTole) {
		this.mouldTole = mouldTole;
	}

	public String getMouldTole() {
		return mouldTole;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setCustomerId(Long customerId) {this.customerId = customerId;}

	public Long getCustomerId() {return customerId;}

	public void setCustomerName(String customerName) {this.customerName = customerName;}

	public String getCustomerName() {return customerName;}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setPositionClassify(Integer positionClassify) {
		this.positionClassify = positionClassify;
	}

	public Integer getPositionClassify() {
		return positionClassify;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getPositionName() {
		return positionName;
	}

	public Integer getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(Integer outStatus) {
		this.outStatus = outStatus;
	}

	public String getPositionJson() {
		return positionJson;
	}

	public void setPositionJson(String positionJson) {
		this.positionJson = positionJson;
	}

	public String getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	@Override
	public String toString() {
		return "MmMould{" +
				"mouldId=" + mouldId +
				", mouldName='" + mouldName + '\'' +
				", mouldClassifyId=" + mouldClassifyId +
				", mouldClassify='" + mouldClassify + '\'' +
				", mouldCode='" + mouldCode + '\'' +
				", mouldSize='" + mouldSize + '\'' +
				", mouldTole='" + mouldTole + '\'' +
				", source='" + source + '\'' +
				", customerId=" + customerId +
				", customerName='" + customerName + '\'' +
				", status='" + status + '\'' +
				", positionClassify=" + positionClassify +
				", positionName='" + positionName + '\'' +
				", positionJson='" + positionJson + '\'' +
				", purchaseId=" + purchaseId +
				", purchaseCode='" + purchaseCode + '\'' +
				", detailId=" + detailId +
				", purchaseName='" + purchaseName + '\'' +
				", outStatus=" + outStatus +
				'}';
	}
}
