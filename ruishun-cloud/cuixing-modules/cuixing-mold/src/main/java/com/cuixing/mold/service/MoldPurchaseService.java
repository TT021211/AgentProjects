package com.cuixing.mold.service;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldNesting;
import com.cuixing.mold.entity.MoldPurchase;

import java.util.List;


/**
 * 模具采购(MoldPurchase)表服务接口
 *
 * @author makejava
 * @since 2024-07-23 14:32:40
 */
public interface MoldPurchaseService {

    /**
     * 入库
     * @param moldPurchase
     * @return
     */
    AjaxResult initiateStorage(MoldPurchase moldPurchase);

    /**
     * 审批
     * @param moldPurchase
     * @return
     */
    AjaxResult initiateTemporaryApproval(MoldPurchase moldPurchase);

    /**
     * 发起审批
     * @param moldPurchase
     * @return
     */
    AjaxResult initiateApproval(MoldPurchase moldPurchase);

    /**
     * 批量插入
     * @param moldPurchases
     */
    int insertBatch(List<MoldPurchase> moldPurchases);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MoldPurchase queryById(Long id);

    /**
     * 分页查询
     *
     * @param moldPurchase 筛选条件
     * @return 查询结果
     */
    List<MoldPurchase> selectMoldPurchaseList(MoldPurchase moldPurchase);

    /**
     * 新增数据
     *
     * @param moldPurchase 实例对象
     * @return 实例对象
     */
    MoldPurchase insert(MoldPurchase moldPurchase);

    /**
     * 修改数据
     *
     * @param moldPurchase 实例对象
     * @return 实例对象
     */
    MoldPurchase update(MoldPurchase moldPurchase);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过编码查询单条数据
     *
     * @param moldPurchase 主键
     * @return 实例对象
     */
    String checkUniquePurchaseCode(MoldPurchase moldPurchase);

}
