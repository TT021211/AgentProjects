package com.cuixing.purchase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;


/**
 * 采购货源管理(ProcureSource)表服务类
 *
 * @author 阿江
 * @since 2025-03-04 15:08:21
 */
@Setter
@Getter
public class ProcureSource implements Serializable {
    private static final long serialVersionUID = 984087891917129853L;
    /**
     * 采购货源id
     */
    private Long id;
    /**
     * 货源名称
     */
    private String sourceName;
    /**
     * 货源简称
     */
    private String abbreviation;
    /**
     * 货源编码
     */
    private String sourceCode;
    /**
     * 统一社会信用代码
     */
    private String sourceUniqueCode;
    /**
     * 业务员
     */
    private String salesman;
    /**
     * 业务员电话
     */
    private String phoneNumber;
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

