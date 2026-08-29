package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcClassification;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 质检分类表(QcClassification)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-02 16:56:18
 */
public interface QcClassificationMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcClassification queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param qcClassification 查询条件
     * @return 对象列表
     */
    List<QcClassification> selectQcClassificationList(QcClassification qcClassification);

    /**
     * 统计总行数
     *
     * @param qcClassification 查询条件
     * @return 总行数
     */
    long count(QcClassification qcClassification);

    /**
     * 新增数据
     *
     * @param qcClassification 实例对象
     * @return 影响行数
     */
    int insert(QcClassification qcClassification);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcClassification> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcClassification> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcClassification> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcClassification> entities);

    /**
     * 修改数据
     *
     * @param qcClassification 实例对象
     * @return 影响行数
     */
    int update(QcClassification qcClassification);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

