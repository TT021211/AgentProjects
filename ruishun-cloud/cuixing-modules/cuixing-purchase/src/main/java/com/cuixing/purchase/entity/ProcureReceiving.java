package com.cuixing.purchase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;


/**
 * 采购收料管理(ProcureReceiving)表服务类
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Setter
@Getter
public class ProcureReceiving implements Serializable {
    private static final long serialVersionUID = -58780061862091217L;
    /**
     * 采购收料id
     */
    private Long id;
    /**
     * 收料类型
     */
    private String receivingType;
    /**
     * 采购合同编号
     */
    private String procureCode;
    /**
     * 采购重量
     */
    private Double procureWeight;
    /**
     * 采购数量
     */
    private Integer procureNumber;
    /**
     * 采购二维码
     */
    private String procureQrcode;
    /**
     * 采购名称
     */
    private String procureName;
    /**
     * 采购型号
     */
    private String procureModel;
    /**
     * 采购规格
     */
    private String procureSpec;
    /**
     * 采购供应商
     */
    private String procureClient;
    /**
     * 采购时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date procureDate;
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

