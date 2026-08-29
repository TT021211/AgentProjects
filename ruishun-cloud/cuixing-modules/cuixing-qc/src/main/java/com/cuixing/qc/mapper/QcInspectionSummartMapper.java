package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcInspectionSummart;
import com.cuixing.qc.entity.vo.QcCountData;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

/**
 * 检验汇总表(QcInspectionSummart)表数据库访问层
 *
 * @author 阿江
 * @since 2024-12-13 10:01:27
 */
public interface QcInspectionSummartMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcInspectionSummart queryById(BigInteger id);

    /**
     * 查询指定行数据
     *
     * @param qcInspectionSummart 查询条件
     * @return 对象列表
     */
    List<QcInspectionSummart> selectQcInspectionSummartList(QcInspectionSummart qcInspectionSummart);

    /**
     * 统计总行数
     *
     * @param qcInspectionSummart 查询条件
     * @return 总行数
     */
    long count(QcInspectionSummart qcInspectionSummart);

    /**
     * 新增数据
     *
     * @param qcInspectionSummart 实例对象
     * @return 影响行数
     */
    int insert(QcInspectionSummart qcInspectionSummart);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcInspectionSummart> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcInspectionSummart> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcInspectionSummart> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcInspectionSummart> entities);

    /**
     * 修改数据
     *
     * @param qcInspectionSummart 实例对象
     * @return 影响行数
     */
    int update(QcInspectionSummart qcInspectionSummart);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(BigInteger id);

    QcInspectionSummart queryByItemIdentifier(String itemIdentifier);

    List<QcCountData> getQcCountData();
}

