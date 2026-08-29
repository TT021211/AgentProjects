package com.cuixing.eq.service;

import java.math.BigInteger;

import com.cuixing.eq.entity.EqList;
import com.cuixing.eq.entity.EqPlan;
import java.util.List;

 /**
 * 点检保养计划;(eq_plan)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-7-8
 */
public interface IEqPlanService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqPlan queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqPlan 筛选条件
     * @return 查询结果
     */
    List<EqPlan> selectEqPlanList(EqPlan eqPlan);
    /** 
     * 新增数据
     *
     * @param eqPlan 实例对象
     * @return 实例对象
     */
    int insert(EqPlan eqPlan);
    /** 
     * 更新数据
     *
     * @param eqPlan 实例对象
     * @return 实例对象
     */
    int update(EqPlan eqPlan);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
     /**
      * * 通过类型id获取设备项目列表
      */
     List<EqList> getEqListByItemTypeId(BigInteger id);

     /**
      * * 根据计划id生成待点检(保养)列表
      * @param id
      * @return
      */
    int insertEqList(BigInteger id);
 }