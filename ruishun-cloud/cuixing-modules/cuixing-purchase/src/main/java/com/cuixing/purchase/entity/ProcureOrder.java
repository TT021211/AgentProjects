package com.cuixing.purchase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;


/**
 * 采购订单(ProcureOrder)表服务类
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Setter
@Getter
public class ProcureOrder implements Serializable {
    private static final long serialVersionUID = -54511489435052009L;
    /**
     * 自增主键
     */
    private Long id;
    /**
     * 采购单号
     */
    private String procureCode;
    /**
     * 品名id
     */
    private Long productNameId;
    /**
     * 品名
     */
    private String productName;
    /**
     * 规格
     */
    private String productSpec;
    /**
     * 重量
     */
    private Double procureWeight;
    /**
     * 数量
     */
    private Long procureNumber;
    /**
     * 用途
     */
    private String procurePurpose;
    /**
     * 要求
     */
    private String procureRequire;
    /**
     * 单位id
     */
    private Long measureId;
    /**
     * 单位名称
     */
    private String measureName;
    /**
     * 供应商id
     */
    private Long manufacturerId;
    /**
     * 供应商
     */
    private String manufacturer;
    /**
     * 采购人
     */
    private String procurePerson;
    /**
     * 采购时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date procureTime;
    /**
     * 到货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date arriveTime;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
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

