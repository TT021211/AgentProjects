package com.cuixing.purchase.mapper;

import com.cuixing.purchase.entity.ProcureSource;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 采购货源管理(ProcureSource)表数据库访问层
 *
 * @author 阿江
 * @since 2025-03-04 15:08:21
 */
public interface ProcureSourceMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureSource queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param procureSource 查询条件
     * @return 对象列表
     */
    List<ProcureSource> selectProcureSourceList(ProcureSource procureSource);

    /**
     * 统计总行数
     *
     * @param procureSource 查询条件
     * @return 总行数
     */
    long count(ProcureSource procureSource);

    /**
     * 新增数据
     *
     * @param procureSource 实例对象
     * @return 影响行数
     */
    int insert(ProcureSource procureSource);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProcureSource> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ProcureSource> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProcureSource> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ProcureSource> entities);

    /**
     * 修改数据
     *
     * @param procureSource 实例对象
     * @return 影响行数
     */
    int update(ProcureSource procureSource);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

