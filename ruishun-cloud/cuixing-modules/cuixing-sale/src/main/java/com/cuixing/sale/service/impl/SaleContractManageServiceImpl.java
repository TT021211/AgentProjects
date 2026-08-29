package com.cuixing.sale.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.sale.entity.SaleContractManage;
import com.cuixing.sale.mapper.SaleContractManageMapper;
import com.cuixing.sale.service.SaleContractManageService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * (SaleContractManage)表服务实现类
 *
 * @author shrimp
 * @since 2025-02-26 10:51:35
 */
@Service("saleContractManageService")
public class SaleContractManageServiceImpl implements SaleContractManageService {
    @Resource
    private SaleContractManageMapper saleContractManageMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SaleContractManage queryById(Long id) {
        return this.saleContractManageMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param saleContractManage 筛选条件
     * @return 查询结果
     */
    @Override
    public List<SaleContractManage> selectSaleContractManageList(SaleContractManage saleContractManage) {
        return this.saleContractManageMapper.selectSaleContractManageList(saleContractManage);
    }

    /**
     * 新增数据
     *
     * @param saleContractManage 实例对象
     * @return 实例对象
     */
    @Override
    public SaleContractManage insert(SaleContractManage saleContractManage) {
        System.out.println(saleContractManage);
        saleContractManage.setCreateBy(SecurityUtils.getUsername());
        saleContractManage.setCreateTime(new Date());
        this.saleContractManageMapper.insert(saleContractManage);
        return saleContractManage;
    }

    /**
     * 修改数据
     *
     * @param saleContractManage 实例对象
     * @return 实例对象
     */
    @Override
    public SaleContractManage update(SaleContractManage saleContractManage) {
        saleContractManage.setUpdateBy(SecurityUtils.getUsername());
        saleContractManage.setUpdateTime(new Date());
        this.saleContractManageMapper.update(saleContractManage);
        return this.queryById(saleContractManage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.saleContractManageMapper.deleteById(id) > 0;
    }
}
