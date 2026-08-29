package com.cuixing.crm.service;

import com.cuixing.crm.entity.CrmCustomer;

import java.util.List;


/**
 * 客诉表(CrmCustomer)表服务接口
 *
 * @author 阿江
 * @since 2025-07-14 20:37:17
 */
public interface CrmCustomerService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CrmCustomer queryById(Long id);

    /**
     * 分页查询
     *
     * @param crmCustomer 筛选条件
     * @return 查询结果
     */
    List<CrmCustomer> selectCrmCustomerList(CrmCustomer crmCustomer);

    /**
     * 新增数据
     *
     * @param crmCustomer 实例对象
     * @return 实例对象
     */
    CrmCustomer insert(CrmCustomer crmCustomer);

    /**
     * 修改数据
     *
     * @param crmCustomer 实例对象
     * @return 实例对象
     */
    CrmCustomer update(CrmCustomer crmCustomer);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
