package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcInspectionItems;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 质量管理-质检检测项(QcInspectionItems)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-02 16:56:23
 */
public interface QcInspectionItemsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcInspectionItems queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param qcInspectionItems 查询条件
     * @return 对象列表
     */
    List<QcInspectionItems> selectQcInspectionItemsList(QcInspectionItems qcInspectionItems);

    /**
     * 统计总行数
     *
     * @param qcInspectionItems 查询条件
     * @return 总行数
     */
    long count(QcInspectionItems qcInspectionItems);

    /**
     * 新增数据
     *
     * @param qcInspectionItems 实例对象
     * @return 影响行数
     */
    int insert(QcInspectionItems qcInspectionItems);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcInspectionItems> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcInspectionItems> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcInspectionItems> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcInspectionItems> entities);

    /**
     * 修改数据
     *
     * @param qcInspectionItems 实例对象
     * @return 影响行数
     */
    int update(QcInspectionItems qcInspectionItems);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

