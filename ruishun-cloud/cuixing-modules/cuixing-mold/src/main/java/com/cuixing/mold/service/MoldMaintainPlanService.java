package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldMaintainPlan;

import java.util.List;


/**
 * 模具保养表--保养计划表(MoldMaintainPlan)表服务接口
 *
 * @author makejava
 * @since 2024-06-25 16:52:04
 */
public interface MoldMaintainPlanService {

    /**
     * 通过ID查询单条数据
     *
     * @param maintainPlanId 主键
     * @return 实例对象
     */
    MoldMaintainPlan queryById(Long maintainPlanId);

    /**
     * 分页查询
     *
     * @param moldMaintainPlan 筛选条件
     * @return 查询结果
     */
    List<MoldMaintainPlan> selectMoldMaintainPlanList(MoldMaintainPlan moldMaintainPlan);

    /**
     * 新增数据
     *
     * @param moldMaintainPlan 实例对象
     * @return 实例对象
     */
    MoldMaintainPlan insert(MoldMaintainPlan moldMaintainPlan);

    /**
     * 修改数据
     *
     * @param moldMaintainPlan 实例对象
     * @return 实例对象
     */
    MoldMaintainPlan update(MoldMaintainPlan moldMaintainPlan);

    void generateAndInsertInspectionDates(MoldMaintainPlan plan);

    /**
     * 通过主键删除数据
     *
     * @param maintainPlanId 主键
     * @return 是否成功
     */
    boolean deleteById(Long maintainPlanId);

    /**
     * 检查编码
     * @param moldMaintainPlan
     * @return
     */
    String checkUniquePlanCode(MoldMaintainPlan moldMaintainPlan);

}
