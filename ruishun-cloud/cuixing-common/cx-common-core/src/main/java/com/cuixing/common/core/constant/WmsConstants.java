package com.cuixing.common.core.constant;

/**
 * 仓储常量信息
 * 
 * @author cuixing
 */
public class WmsConstants
{
    /**
     * 库存不足
     */
    public static final int OUT_OF_STOCK = -2;

    /**
     * 物料不存在库存
     */
    public static final int NOT_EXIST = -1;

    public static final int STORAGE_CHECK_FLAG_IN = 1; // 入库
    public static final int STORAGE_CHECK_FLAG_OUT = -1; // 出库
    public static final String ITEM_RECPT_TYPE = "ITEM_RECPT"; // 入库单类型
    public static final String ITEM_ISSUE_OUT_TYPE = "ITEM_ISSUE_OUT"; // 入库单类型


}
