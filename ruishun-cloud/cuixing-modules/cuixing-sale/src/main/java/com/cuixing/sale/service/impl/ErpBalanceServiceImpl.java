package com.cuixing.sale.service.impl;

import com.cuixing.sale.entity.ErpBalance;
import com.cuixing.sale.mapper.ErpBalanceMapper;
import com.cuixing.sale.service.ErpBalanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 科目余额表(ErpBalance)表服务实现类
 *
 * @author 阿江
 * @since 2025-07-14 20:38:31
 */
@Service("erpBalanceService")
public class ErpBalanceServiceImpl implements ErpBalanceService {
    @Resource
    private ErpBalanceMapper erpBalanceMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param fyear 主键
     * @return 实例对象
     */
    @Override
    public ErpBalance queryById(Integer fyear) {
        return this.erpBalanceMapper.queryById(fyear);
    }

    /**
     * 分页查询
     *
     * @param erpBalance 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ErpBalance> selectErpBalanceList(ErpBalance erpBalance) {
        return this.erpBalanceMapper.selectErpBalanceList(erpBalance);
    }

    /**
     * 新增数据
     *
     * @param erpBalance 实例对象
     * @return 实例对象
     */
    @Override
    public ErpBalance insert(ErpBalance erpBalance) {
        this.erpBalanceMapper.insert(erpBalance);
        return erpBalance;
    }

    /**
     * 修改数据
     *
     * @param erpBalance 实例对象
     * @return 实例对象
     */
    @Override
    public ErpBalance update(ErpBalance erpBalance) {
        this.erpBalanceMapper.update(erpBalance);
        return this.queryById(erpBalance.getFyear());
    }

    /**
     * 通过主键删除数据
     *
     * @param fyear 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer fyear) {
        return this.erpBalanceMapper.deleteById(fyear) > 0;
    }
}
