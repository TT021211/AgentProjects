package com.cuixing.sale.service;

import com.cuixing.sale.entity.ErpBalance;

import java.util.List;


/**
 * 科目余额表(ErpBalance)表服务接口
 *
 * @author 阿江
 * @since 2025-07-14 20:38:31
 */
public interface ErpBalanceService {

    /**
     * 通过ID查询单条数据
     *
     * @param fyear 主键
     * @return 实例对象
     */
    ErpBalance queryById(Integer fyear);

    /**
     * 分页查询
     *
     * @param erpBalance 筛选条件
     * @return 查询结果
     */
    List<ErpBalance> selectErpBalanceList(ErpBalance erpBalance);

    /**
     * 新增数据
     *
     * @param erpBalance 实例对象
     * @return 实例对象
     */
    ErpBalance insert(ErpBalance erpBalance);

    /**
     * 修改数据
     *
     * @param erpBalance 实例对象
     * @return 实例对象
     */
    ErpBalance update(ErpBalance erpBalance);

    /**
     * 通过主键删除数据
     *
     * @param fyear 主键
     * @return 是否成功
     */
    boolean deleteById(Integer fyear);

}
