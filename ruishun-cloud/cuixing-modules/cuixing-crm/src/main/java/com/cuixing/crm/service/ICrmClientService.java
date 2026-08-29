package com.cuixing.crm.service;

import com.cuixing.crm.entity.CrmClient;
import com.cuixing.crm.entity.vo.CrmClientAnalyze;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * 客户表;(crm_client)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface ICrmClientService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CrmClient queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param crmClient 筛选条件
     * @return 查询结果
     */
    List<CrmClient> selectCrmClientList(CrmClient crmClient);

    /**
     * 新增数据
     *
     * @param crmClient 实例对象
     * @return 影响行数
     */
    int insert(CrmClient crmClient);

    /**
     * 批量新增数据
     *
     * @param crmClients 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<CrmClient> crmClients);
    /**
     * 更新数据
     *
     * @param crmClient 实例对象
     * @return 影响行数
     */
    int update(CrmClient crmClient);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigInteger id);
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    boolean deleteByIds(BigInteger[] ids);

    CrmClientAnalyze getClientAnalyze();

    Set<BigInteger> selectIds();
}