package com.cuixing.system.api.domain.sale;

import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigInteger;
import java.util.Date;

/**
 * 销售订单;
 *
 * @author : http://www.chiner.pro
 * @date : 2024-6-6
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "销售订单", description = "")
@Data
public class SaleOrder extends BaseEntity {
    /**
     * 自增主键
     */
    @ApiModelProperty(name = "自增主键", notes = "")
    private BigInteger id;
    /**
     * 订单类型(0：客户订单 1：备库订单)
     */
    @ApiModelProperty(name = "订单类型(0：客户订单 1：备库订单)", notes = "")
    private String orderType;
    /**
     * 客户id
     */
    @ApiModelProperty(name = "客户id", notes = "")
    private BigInteger customerId;
    /**
     * 客户名称
     */
    @ApiModelProperty(name = "客户名称", notes = "")
    private String customerName;
    /**
     * 订单编号
     */
    @ApiModelProperty(name = "订单编号", notes = "")
    private String orderCode;
    /**
     * 产品id
     */
    @ApiModelProperty(name = "产品id", notes = "")
    private BigInteger productId;
    /**
     * 产品规格
     */
    @ApiModelProperty(name = "产品规格", notes = "")
    private String specification;
    /**
     * 产品型号
     */
    @ApiModelProperty(name = "产品型号", notes = "")
    private String model;
    /**
     * 重量
     */
    @ApiModelProperty(name = "重量", notes = "")
    private Double weight;
    /**
     * 单位id（单位表）
     */
    @ApiModelProperty(name = "单位id（单位表）", notes = "")
    private BigInteger measureId;
    /**
     * 单位名称（单位表）
     */
    @ApiModelProperty(name = "单位名称（单位表）", notes = "")
    private String measureName;
    /**
     * 交付时间
     */
    @ApiModelProperty(name = "交付时间", notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payTime;
    /**
     * 订单状态
     */
    @ApiModelProperty(name = "订单状态", notes = "")
    private String status;
    /**
     * 逻辑删除
     */
    @ApiModelProperty(name = "逻辑删除", notes = "")
    private Integer isDeleted;
    /**
     * 乐观锁
     */
    @ApiModelProperty(name = "乐观锁", notes = "")
    private Integer version;
    /**
     * 创建人
     */
    @ApiModelProperty(name = "创建人", notes = "")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(name = "创建时间", notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(name = "更新人", notes = "")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(name = "更新时间", notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 预留字段1
     */
    @ApiModelProperty(name = "预留字段1", notes = "")
    private String attr1;
    /**
     * 预留字段2
     */
    @ApiModelProperty(name = "预留字段2", notes = "")
    private Integer attr2;
    /**
     * 预留字段3
     * -- GETTER --
     *  预留字段3

     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String attr3;
    /**
     * 预留字段4
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt4;
    /**
     * 预留字段5
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt5;
    /**
     * 预留字段6
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt6;
    /**
     * 预留字段7
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt7;
    /**
     * 预留字段8
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt8;
    /**
     * 预留字段9
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt9;
    /**
     * 预留字段10
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String artt10;
    /**
     * 预留字段3
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String technicalRequirements;
    /**
     * 预留字段3
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private BigInteger standardId;
    /**
     * 预留字段3
     */
    @ApiModelProperty(name = "预留字段3", notes = "")
    private String standardName;
}
