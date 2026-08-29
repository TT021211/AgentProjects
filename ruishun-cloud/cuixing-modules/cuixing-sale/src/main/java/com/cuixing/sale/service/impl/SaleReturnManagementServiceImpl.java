package com.cuixing.sale.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.sale.entity.SaleReturnManagement;
import com.cuixing.sale.mapper.SaleReturnManagementMapper;
import com.cuixing.sale.service.SaleReturnManagementService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 销售退货管理(SaleReturnManagement)表服务实现类
 *
 * @author 阿江
 * @since 2025-03-04 15:10:39
 */
@Service("saleReturnManagementService")
public class SaleReturnManagementServiceImpl implements SaleReturnManagementService {
    @Resource
    private SaleReturnManagementMapper saleReturnManagementMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SaleReturnManagement queryById(Long id) {
        return this.saleReturnManagementMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param saleReturnManagement 筛选条件
     * @return 查询结果
     */
    @Override
    public List<SaleReturnManagement> selectSaleReturnManagementList(SaleReturnManagement saleReturnManagement) {
        return this.saleReturnManagementMapper.selectSaleReturnManagementList(saleReturnManagement);
    }

    /**
     * 新增数据
     *
     * @param saleReturnManagement 实例对象
     * @return 实例对象
     */
    @Override
    public SaleReturnManagement insert(SaleReturnManagement saleReturnManagement) {
        saleReturnManagement.setCreateTime(new Date());
        saleReturnManagement.setCreateBy(SecurityUtils.getUsername());
        this.saleReturnManagementMapper.insert(saleReturnManagement);
        return saleReturnManagement;
    }

    /**
     * 修改数据
     *
     * @param saleReturnManagement 实例对象
     * @return 实例对象
     */
    @Override
    public SaleReturnManagement update(SaleReturnManagement saleReturnManagement) {
        saleReturnManagement.setUpdateTime(new Date());
        saleReturnManagement.setUpdateBy(SecurityUtils.getUsername());
        this.saleReturnManagementMapper.update(saleReturnManagement);
        return this.queryById(saleReturnManagement.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.saleReturnManagementMapper.deleteById(id) > 0;
    }
}
