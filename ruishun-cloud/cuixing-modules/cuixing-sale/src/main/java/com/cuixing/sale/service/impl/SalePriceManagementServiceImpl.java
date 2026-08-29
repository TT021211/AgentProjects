package com.cuixing.sale.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.sale.entity.SalePriceManagement;
import com.cuixing.sale.mapper.SalePriceManagementMapper;
import com.cuixing.sale.service.SalePriceManagementService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 销售价格管理(SalePriceManagement)表服务实现类
 *
 * @author 阿江
 * @since 2025-03-04 15:10:39
 */
@Service("salePriceManagementService")
public class SalePriceManagementServiceImpl implements SalePriceManagementService {
    @Resource
    private SalePriceManagementMapper salePriceManagementMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SalePriceManagement queryById(Long id) {
        return this.salePriceManagementMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param salePriceManagement 筛选条件
     * @return 查询结果
     */
    @Override
    public List<SalePriceManagement> selectSalePriceManagementList(SalePriceManagement salePriceManagement) {
        return this.salePriceManagementMapper.selectSalePriceManagementList(salePriceManagement);
    }

    /**
     * 新增数据
     *
     * @param salePriceManagement 实例对象
     * @return 实例对象
     */
    @Override
    public SalePriceManagement insert(SalePriceManagement salePriceManagement) {
        salePriceManagement.setCreateTime(new Date());
        salePriceManagement.setCreateBy(SecurityUtils.getUsername());
        this.salePriceManagementMapper.insert(salePriceManagement);
        return salePriceManagement;
    }

    /**
     * 修改数据
     *
     * @param salePriceManagement 实例对象
     * @return 实例对象
     */
    @Override
    public SalePriceManagement update(SalePriceManagement salePriceManagement) {
        salePriceManagement.setUpdateTime(new Date());
        salePriceManagement.setUpdateBy(SecurityUtils.getUsername());
        this.salePriceManagementMapper.update(salePriceManagement);
        return this.queryById(salePriceManagement.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.salePriceManagementMapper.deleteById(id) > 0;
    }
}
