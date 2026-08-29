package com.cuixing.purchase.service;

import com.cuixing.purchase.entity.ProcureReceiving;
import java.util.List;


/**
 * 采购收料管理(ProcureReceiving)表服务接口
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
public interface ProcureReceivingService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureReceiving queryById(Long id);

    /**
     * 分页查询
     *
     * @param procureReceiving 筛选条件
     * @return 查询结果
     */
    List<ProcureReceiving> selectProcureReceivingList(ProcureReceiving procureReceiving);

    /**
     * 新增数据
     *
     * @param procureReceiving 实例对象
     * @return 实例对象
     */
    ProcureReceiving insert(ProcureReceiving procureReceiving);

    /**
     * 修改数据
     *
     * @param procureReceiving 实例对象
     * @return 实例对象
     */
    ProcureReceiving update(ProcureReceiving procureReceiving);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
