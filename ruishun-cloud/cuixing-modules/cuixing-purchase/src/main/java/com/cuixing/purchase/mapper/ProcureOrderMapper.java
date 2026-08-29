package com.cuixing.purchase.mapper;

import com.cuixing.purchase.entity.ProcureOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购订单(ProcureOrder)表数据库访问层
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
public interface ProcureOrderMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureOrder queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param procureOrder 查询条件
     * @return 对象列表
     */
    List<ProcureOrder> selectProcureOrderList(ProcureOrder procureOrder);

    /**
     * 统计总行数
     *
     * @param procureOrder 查询条件
     * @return 总行数
     */
    long count(ProcureOrder procureOrder);

    /**
     * 新增数据
     *
     * @param procureOrder 实例对象
     * @return 影响行数
     */
    int insert(ProcureOrder procureOrder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProcureOrder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ProcureOrder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProcureOrder> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ProcureOrder> entities);

    /**
     * 修改数据
     *
     * @param procureOrder 实例对象
     * @return 影响行数
     */
    int update(ProcureOrder procureOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    int approval(ProcureOrder procureOrder);
}

