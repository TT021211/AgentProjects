package com.cuixing.sale.service;

import com.cuixing.sale.entity.SaleContractManage;
import java.util.List;


/**
 * (SaleContractManage)表服务接口
 *
 * @author shrimp
 * @since 2025-02-26 10:51:35
 */
public interface SaleContractManageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SaleContractManage queryById(Long id);

    /**
     * 分页查询
     *
     * @param saleContractManage 筛选条件
     * @return 查询结果
     */
    List<SaleContractManage> selectSaleContractManageList(SaleContractManage saleContractManage);

    /**
     * 新增数据
     *
     * @param saleContractManage 实例对象
     * @return 实例对象
     */
    SaleContractManage insert(SaleContractManage saleContractManage);

    /**
     * 修改数据
     *
     * @param saleContractManage 实例对象
     * @return 实例对象
     */
    SaleContractManage update(SaleContractManage saleContractManage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
