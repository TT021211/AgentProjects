package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldMaintainPlan;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模具保养表--保养计划表(MoldMaintainPlan)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-25 16:52:04
 */
public interface MoldMaintainPlanMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param maintainPlanId 主键
     * @return 实例对象
     */
    MoldMaintainPlan queryById(Long maintainPlanId);
    /**
     * 检查编码
     *
     * @param moldMaintainPlan 查询条件
     * @return 实例对象
     */
    MoldMaintainPlan checkUniquePlanCode(MoldMaintainPlan moldMaintainPlan);
    /**
     * 查询指定行数据
     *
     * @param moldMaintainPlan 查询条件
     * @return 对象列表
     */
    List<MoldMaintainPlan> selectMoldMaintainPlanList(MoldMaintainPlan moldMaintainPlan);

    /**
     * 统计总行数
     *
     * @param moldMaintainPlan 查询条件
     * @return 总行数
     */
    long count(MoldMaintainPlan moldMaintainPlan);

    /**
     * 新增数据
     *
     * @param moldMaintainPlan 实例对象
     * @return 影响行数
     */
    int insert(MoldMaintainPlan moldMaintainPlan);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldMaintainPlan> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldMaintainPlan> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldMaintainPlan> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldMaintainPlan> entities);

    /**
     * 修改数据
     *
     * @param moldMaintainPlan 实例对象
     * @return 影响行数
     */
    int update(MoldMaintainPlan moldMaintainPlan);
    /**
     * 更新任务数据
     *
     * @param moldMaintainPlan 实例对象
     * @return 影响行数
     */
    int updateJob(MoldMaintainPlan moldMaintainPlan);

    /**
     * 通过主键删除数据
     *
     * @param maintainPlanId 主键
     * @return 影响行数
     */
    int deleteById(Long maintainPlanId);

}

