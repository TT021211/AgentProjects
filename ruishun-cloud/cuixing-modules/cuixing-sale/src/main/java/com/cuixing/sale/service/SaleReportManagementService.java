package com.cuixing.sale.service;

import com.cuixing.sale.entity.SaleReportManagement;
import java.util.List;


/**
 * 销售报表分析(SaleReportManagement)表服务接口
 *
 * @author 阿江
 * @since 2025-03-17 15:27:04
 */
public interface SaleReportManagementService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SaleReportManagement queryById(Long id);

    /**
     * 分页查询
     *
     * @param saleReportManagement 筛选条件
     * @return 查询结果
     */
    List<SaleReportManagement> selectSaleReportManagementList(SaleReportManagement saleReportManagement);

    /**
     * 新增数据
     *
     * @param saleReportManagement 实例对象
     * @return 实例对象
     */
    SaleReportManagement insert(SaleReportManagement saleReportManagement);

    /**
     * 修改数据
     *
     * @param saleReportManagement 实例对象
     * @return 实例对象
     */
    SaleReportManagement update(SaleReportManagement saleReportManagement);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
