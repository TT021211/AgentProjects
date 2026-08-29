package com.cuixing.sale.mapper;

import com.cuixing.sale.entity.ErpBalance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科目余额表(ErpBalance)表数据库访问层
 *
 * @author 阿江
 * @since 2025-07-14 20:38:31
 */
public interface ErpBalanceMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param fyear 主键
     * @return 实例对象
     */
    ErpBalance queryById(Integer fyear);

    /**
     * 查询指定行数据
     *
     * @param erpBalance 查询条件
     * @return 对象列表
     */
    List<ErpBalance> selectErpBalanceList(ErpBalance erpBalance);

    /**
     * 统计总行数
     *
     * @param erpBalance 查询条件
     * @return 总行数
     */
    long count(ErpBalance erpBalance);

    /**
     * 新增数据
     *
     * @param erpBalance 实例对象
     * @return 影响行数
     */
    int insert(ErpBalance erpBalance);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ErpBalance> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ErpBalance> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ErpBalance> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ErpBalance> entities);

    /**
     * 修改数据
     *
     * @param erpBalance 实例对象
     * @return 影响行数
     */
    int update(ErpBalance erpBalance);

    /**
     * 通过主键删除数据
     *
     * @param fyear 主键
     * @return 影响行数
     */
    int deleteById(Integer fyear);

}

