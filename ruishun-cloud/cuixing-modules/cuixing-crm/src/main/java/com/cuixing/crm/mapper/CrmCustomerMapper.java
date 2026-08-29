package com.cuixing.crm.mapper;

import com.cuixing.crm.entity.CrmCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客诉表(CrmCustomer)表数据库访问层
 *
 * @author 阿江
 * @since 2025-07-14 20:37:17
 */
public interface CrmCustomerMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CrmCustomer queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param crmCustomer 查询条件
     * @return 对象列表
     */
    List<CrmCustomer> selectCrmCustomerList(CrmCustomer crmCustomer);

    /**
     * 统计总行数
     *
     * @param crmCustomer 查询条件
     * @return 总行数
     */
    long count(CrmCustomer crmCustomer);

    /**
     * 新增数据
     *
     * @param crmCustomer 实例对象
     * @return 影响行数
     */
    int insert(CrmCustomer crmCustomer);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<CrmCustomer> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<CrmCustomer> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<CrmCustomer> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<CrmCustomer> entities);

    /**
     * 修改数据
     *
     * @param crmCustomer 实例对象
     * @return 影响行数
     */
    int update(CrmCustomer crmCustomer);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

