package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcMaterialInspection;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 原料检验表(QcMaterialInspection)表数据库访问层
 *
 * @author 阿江
 * @since 2024-12-13 10:01:27
 */
public interface QcMaterialInspectionMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcMaterialInspection queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param qcMaterialInspection 查询条件
     * @return 对象列表
     */
    List<QcMaterialInspection> selectQcMaterialInspectionList(QcMaterialInspection qcMaterialInspection);

    /**
     * 统计总行数
     *
     * @param qcMaterialInspection 查询条件
     * @return 总行数
     */
    long count(QcMaterialInspection qcMaterialInspection);

    /**
     * 新增数据
     *
     * @param qcMaterialInspection 实例对象
     * @return 影响行数
     */
    int insert(QcMaterialInspection qcMaterialInspection);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcMaterialInspection> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcMaterialInspection> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcMaterialInspection> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcMaterialInspection> entities);

    /**
     * 修改数据
     *
     * @param qcMaterialInspection 实例对象
     * @return 影响行数
     */
    int update(QcMaterialInspection qcMaterialInspection);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

