package com.cuixing.purchase.service;

import com.cuixing.purchase.entity.ProcureOrder;
import java.util.List;


/**
 * 采购订单(ProcureOrder)表服务接口
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
public interface ProcureOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureOrder queryById(Long id);

    /**
     * 分页查询
     *
     * @param procureOrder 筛选条件
     * @return 查询结果
     */
    List<ProcureOrder> selectProcureOrderList(ProcureOrder procureOrder);

    /**
     * 新增数据
     *
     * @param procureOrder 实例对象
     * @return 实例对象
     */
    ProcureOrder insert(ProcureOrder procureOrder);

    /**
     * 修改数据
     *
     * @param procureOrder 实例对象
     * @return 实例对象
     */
    ProcureOrder update(ProcureOrder procureOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    int approval(ProcureOrder procureOrder);
}
