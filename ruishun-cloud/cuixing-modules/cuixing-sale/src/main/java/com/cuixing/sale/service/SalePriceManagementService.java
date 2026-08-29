package com.cuixing.sale.service;

import com.cuixing.sale.entity.SalePriceManagement;
import java.util.List;


/**
 * 销售价格管理(SalePriceManagement)表服务接口
 *
 * @author 阿江
 * @since 2025-03-04 15:10:39
 */
public interface SalePriceManagementService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SalePriceManagement queryById(Long id);

    /**
     * 分页查询
     *
     * @param salePriceManagement 筛选条件
     * @return 查询结果
     */
    List<SalePriceManagement> selectSalePriceManagementList(SalePriceManagement salePriceManagement);

    /**
     * 新增数据
     *
     * @param salePriceManagement 实例对象
     * @return 实例对象
     */
    SalePriceManagement insert(SalePriceManagement salePriceManagement);

    /**
     * 修改数据
     *
     * @param salePriceManagement 实例对象
     * @return 实例对象
     */
    SalePriceManagement update(SalePriceManagement salePriceManagement);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
