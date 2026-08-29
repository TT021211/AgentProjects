package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcSurface;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 质检管理-表面检缺陷项设置(QcSurface)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-02 16:56:25
 */
public interface QcSurfaceMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcSurface queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param qcSurface 查询条件
     * @return 对象列表
     */
    List<QcSurface> selectQcSurfaceList(QcSurface qcSurface);

    /**
     * 统计总行数
     *
     * @param qcSurface 查询条件
     * @return 总行数
     */
    long count(QcSurface qcSurface);

    /**
     * 新增数据
     *
     * @param qcSurface 实例对象
     * @return 影响行数
     */
    int insert(QcSurface qcSurface);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcSurface> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcSurface> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcSurface> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcSurface> entities);

    /**
     * 修改数据
     *
     * @param qcSurface 实例对象
     * @return 影响行数
     */
    int update(QcSurface qcSurface);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

