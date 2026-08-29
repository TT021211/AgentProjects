package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcDefect;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 质检管理-缺陷项设置(QcDefect)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-02 17:22:41
 */
public interface QcDefectMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param defectId 主键
     * @return 实例对象
     */
    QcDefect queryById(Long defectId);
    QcDefect queryBydefectClassificationId(Long defectClassificationId);

    /**
     * 查询指定行数据
     *
     * @param qcDefect 查询条件
     * @return 对象列表
     */
    List<QcDefect> selectQcDefectList(QcDefect qcDefect);

    /**
     * 统计总行数
     *
     * @param qcDefect 查询条件
     * @return 总行数
     */
    long count(QcDefect qcDefect);

    /**
     * 新增数据
     *
     * @param qcDefect 实例对象
     * @return 影响行数
     */
    int insert(QcDefect qcDefect);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcDefect> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcDefect> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcDefect> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcDefect> entities);

    /**
     * 修改数据
     *
     * @param qcDefect 实例对象
     * @return 影响行数
     */
    int update(QcDefect qcDefect);

    /**
     * 通过主键删除数据
     *
     * @param defectId 主键
     * @return 影响行数
     */
    int deleteById(Long defectId);

}

