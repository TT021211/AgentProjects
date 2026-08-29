package com.cuixing.crm.service.impl;

import com.cuixing.crm.entity.CrmCustomer;
import com.cuixing.crm.mapper.CrmCustomerMapper;
import com.cuixing.crm.service.CrmCustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客诉表(CrmCustomer)表服务实现类
 *
 * @author 阿江
 * @since 2025-07-14 20:37:17
 */
@Service("crmCustomerService")
public class CrmCustomerServiceImpl implements CrmCustomerService {
    @Resource
    private CrmCustomerMapper crmCustomerMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CrmCustomer queryById(Long id) {
        return this.crmCustomerMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param crmCustomer 筛选条件
     * @return 查询结果
     */
    @Override
    public List<CrmCustomer> selectCrmCustomerList(CrmCustomer crmCustomer) {
        return this.crmCustomerMapper.selectCrmCustomerList(crmCustomer);
    }

    /**
     * 新增数据
     *
     * @param crmCustomer 实例对象
     * @return 实例对象
     */
    @Override
    public CrmCustomer insert(CrmCustomer crmCustomer) {
        this.crmCustomerMapper.insert(crmCustomer);
        return crmCustomer;
    }

    /**
     * 修改数据
     *
     * @param crmCustomer 实例对象
     * @return 实例对象
     */
    @Override
    public CrmCustomer update(CrmCustomer crmCustomer) {
        this.crmCustomerMapper.update(crmCustomer);
        return this.queryById(crmCustomer.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.crmCustomerMapper.deleteById(id) > 0;
    }
}
