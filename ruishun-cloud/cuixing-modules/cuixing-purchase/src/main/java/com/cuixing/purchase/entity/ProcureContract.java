package com.cuixing.purchase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;


/**
 * 采购合同管理(ProcureContract)表服务类
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Setter
@Getter
public class ProcureContract implements Serializable {
    private static final long serialVersionUID = -19847575636548880L;
    /**
     * 采购合同id
     */
    private Long id;
    /**
     * 供应商名称
     */
    private String clientName;
    /**
     * 供应商电话
     */
    private String clientIphone;
    /**
     * 采购合同编号
     */
    private String procureContractCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品型号
     */
    private String productCode;
    /**
     * 采购数量
     */
    private Integer procureNumber;
    /**
     * 单位
     */
    private String prucureUnit;
    /**
     * 单价
     */
    private Double procurePrice;
    /**
     * 采购金额
     */
    private Double procureMoney;
    /**
     * 需求日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date procureDate;
    /**
     * 备注
     */
    private String remake;
    /**
     * 合同说明
     */
    private String contractNote;
    /**
     * 本厂名称
     */
    private String demandName;
    /**
     * 本厂地址
     */
    private String demandAddress;
    /**
     * 本厂代表
     */
    private String demandLegal;
    /**
     * 本厂电话
     */
    private String demandPhone;
    /**
     * 本厂传真号
     */
    private String demandFax;
    /**
     * 本厂开户银行
     */
    private String demandBank;
    /**
     * 本厂账户
     */
    private String demandAccount;
    /**
     * 本厂税号
     */
    private String demandTax;
    /**
     * 客户名称
     */
    private String userName;
    /**
     * 客户地址
     */
    private String userAddress;
    /**
     * 客户代表
     */
    private String userLegal;
    /**
     * 客户电话
     */
    private String userPhone;
    /**
     * 客户传真号
     */
    private String userFax;
    /**
     * 客户开户银行
     */
    private String userBank;
    /**
     * 客户账户
     */
    private String userAccount;
    /**
     * 客户税号
     */
    private String userTax;
    /**
     * 客户id
     */
    private Long userId;
    /**
     * 逻辑删除
     */
    private Integer isDeleted;
    /**
     * 乐观锁
     */
    private String version;
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
     * 备用字段4
     */
    private Double attr4;
    /**
     * 备用字段5
     */
    private String attr5;
    /**
     * 备用字段6
     */
    private String attr6;
    /**
     * 备用字段7
     */
    private String attr7;
    /**
     * 备用字段8
     */
    private String attr8;
    /**
     * 备用字段9
     */
    private String attr9;


}

