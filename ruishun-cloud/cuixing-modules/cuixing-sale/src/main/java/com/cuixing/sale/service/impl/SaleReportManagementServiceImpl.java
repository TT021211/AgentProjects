package com.cuixing.sale.service.impl;

import com.cuixing.sale.entity.SaleReportManagement;
import com.cuixing.sale.mapper.SaleReportManagementMapper;
import com.cuixing.sale.service.SaleReportManagementService;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;

/**
 * 销售报表分析(SaleReportManagement)表服务实现类
 *
 * @author 阿江
 * @since 2025-03-17 15:27:04
 */
@Service("saleReportManagementService")
public class SaleReportManagementServiceImpl implements SaleReportManagementService {
    @Resource
    private SaleReportManagementMapper saleReportManagementMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SaleReportManagement queryById(Long id) {
        return this.saleReportManagementMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param saleReportManagement 筛选条件
     * @return 查询结果
     */
    @Override
    public List<SaleReportManagement> selectSaleReportManagementList(SaleReportManagement saleReportManagement) {
        return this.saleReportManagementMapper.selectSaleReportManagementList(saleReportManagement);
    }

    /**
     * 新增数据
     *
     * @param saleReportManagement 实例对象
     * @return 实例对象
     */
    @Override
    public SaleReportManagement insert(SaleReportManagement saleReportManagement) {
        this.saleReportManagementMapper.insert(saleReportManagement);
        return saleReportManagement;
    }

    /**
     * 修改数据
     *
     * @param saleReportManagement 实例对象
     * @return 实例对象
     */
    @Override
    public SaleReportManagement update(SaleReportManagement saleReportManagement) {
        this.saleReportManagementMapper.update(saleReportManagement);
        return this.queryById(saleReportManagement.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.saleReportManagementMapper.deleteById(id) > 0;
    }
}
