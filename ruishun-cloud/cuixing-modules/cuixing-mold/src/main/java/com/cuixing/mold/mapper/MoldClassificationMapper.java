package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldClassification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模具分类表(MoldClassification)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-26 11:04:32
 */
public interface MoldClassificationMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param classificationId 主键
     * @return 实例对象
     */
    MoldClassification queryById(Long classificationId);

    MoldClassification queryclassificationType(@Param("classificationType")String classificationType,@Param("classificationName")String classificationName);

    /**
     * 查询指定行数据
     *
     * @param moldClassification 查询条件
     * @return 对象列表
     */
    List<MoldClassification> selectMoldClassificationList(MoldClassification moldClassification);

    /**
     * 统计总行数
     *
     * @param moldClassification 查询条件
     * @return 总行数
     */
    long count(MoldClassification moldClassification);

    /**
     * 新增数据
     *
     * @param moldClassification 实例对象
     * @return 影响行数
     */
    int insert(MoldClassification moldClassification);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldClassification> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldClassification> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldClassification> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldClassification> entities);

    /**
     * 修改数据
     *
     * @param moldClassification 实例对象
     * @return 影响行数
     */
    int update(MoldClassification moldClassification);

    /**
     * 通过主键删除数据
     *
     * @param classificationId 主键
     * @return 影响行数
     */
    int deleteById(Long classificationId);

    List<MoldClassification> selectMoldClassificationList2(MoldClassification moldClassification);
}

