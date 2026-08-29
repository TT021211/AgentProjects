package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcDailyplan;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 日常送检计划(QcDailyplan)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-02 16:56:19
 */
public interface QcDailyplanMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param dailyplanId 主键
     * @return 实例对象
     */
    QcDailyplan queryById(Long dailyplanId);

    /**
     * 查询指定行数据
     *
     * @param qcDailyplan 查询条件
     * @return 对象列表
     */
    List<QcDailyplan> selectQcDailyplanList(QcDailyplan qcDailyplan);

    /**
     * 统计总行数
     *
     * @param qcDailyplan 查询条件
     * @return 总行数
     */
    long count(QcDailyplan qcDailyplan);

    /**
     * 新增数据
     *
     * @param qcDailyplan 实例对象
     * @return 影响行数
     */
    int insert(QcDailyplan qcDailyplan);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcDailyplan> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcDailyplan> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcDailyplan> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcDailyplan> entities);

    /**
     * 修改数据
     *
     * @param qcDailyplan 实例对象
     * @return 影响行数
     */
    int update(QcDailyplan qcDailyplan);

    /**
     * 通过主键删除数据
     *
     * @param dailyplanId 主键
     * @return 影响行数
     */
    int deleteById(Long dailyplanId);

}

