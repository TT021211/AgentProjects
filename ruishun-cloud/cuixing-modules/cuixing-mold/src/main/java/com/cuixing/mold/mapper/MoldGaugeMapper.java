package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldGauge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模具换规表(MoldGauge)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-25 14:06:45
 */
public interface MoldGaugeMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param moldGaugeId 主键
     * @return 实例对象
     */
    MoldGauge queryById(Long moldGaugeId);

    /**
     * 查询指定行数据
     *
     * @param moldGauge 查询条件
     * @return 对象列表
     */
    List<MoldGauge> selectMoldGaugeList(MoldGauge moldGauge);

    /**
     * 统计总行数
     *
     * @param moldGauge 查询条件
     * @return 总行数
     */
    long count(MoldGauge moldGauge);

    /**
     * 新增数据
     *
     * @param moldGauge 实例对象
     * @return 影响行数
     */
    int insert(MoldGauge moldGauge);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldGauge> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldGauge> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldGauge> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldGauge> entities);

    /**
     * 修改数据
     *
     * @param moldGauge 实例对象
     * @return 影响行数
     */
    int update(MoldGauge moldGauge);

    /**
     * 通过主键删除数据
     *
     * @param moldGaugeId 主键
     * @return 影响行数
     */
    int deleteById(Long moldGaugeId);

}

