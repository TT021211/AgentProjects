package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcDetectionItems;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 质量管理-检测项设置(QcDetectionItems)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-02 16:56:21
 */
public interface QcDetectionItemsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcDetectionItems queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param qcDetectionItems 查询条件
     * @return 对象列表
     */
    List<QcDetectionItems> selectQcDetectionItemsList(QcDetectionItems qcDetectionItems);

    /**
     * 统计总行数
     *
     * @param qcDetectionItems 查询条件
     * @return 总行数
     */
    long count(QcDetectionItems qcDetectionItems);

    /**
     * 新增数据
     *
     * @param qcDetectionItems 实例对象
     * @return 影响行数
     */
    int insert(QcDetectionItems qcDetectionItems);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcDetectionItems> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcDetectionItems> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcDetectionItems> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcDetectionItems> entities);

    /**
     * 修改数据
     *
     * @param qcDetectionItems 实例对象
     * @return 影响行数
     */
    int update(QcDetectionItems qcDetectionItems);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

