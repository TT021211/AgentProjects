package com.cuixing.sale.service;

import com.cuixing.sale.entity.SaleReturnManagement;
import java.util.List;


/**
 * 销售退货管理(SaleReturnManagement)表服务接口
 *
 * @author 阿江
 * @since 2025-03-04 15:10:39
 */
public interface SaleReturnManagementService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SaleReturnManagement queryById(Long id);

    /**
     * 分页查询
     *
     * @param saleReturnManagement 筛选条件
     * @return 查询结果
     */
    List<SaleReturnManagement> selectSaleReturnManagementList(SaleReturnManagement saleReturnManagement);

    /**
     * 新增数据
     *
     * @param saleReturnManagement 实例对象
     * @return 实例对象
     */
    SaleReturnManagement insert(SaleReturnManagement saleReturnManagement);

    /**
     * 修改数据
     *
     * @param saleReturnManagement 实例对象
     * @return 实例对象
     */
    SaleReturnManagement update(SaleReturnManagement saleReturnManagement);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
