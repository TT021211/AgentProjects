package com.cuixing.eq.mapper;

import com.cuixing.eq.entity.EqList;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqPlan;
import org.apache.ibatis.annotations.Param;

/**
 * 点检保养计划;(eq_plan)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-7-8
 */
@Mapper
public interface EqPlanMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqPlan queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqPlan 查询条件
     * @return 对象列表
     */
    List<EqPlan> selectEqPlanList(EqPlan eqPlan);
    /** 
     * 新增数据
     *
     * @param eqPlan 实例对象
     * @return 影响行数
     */
    int insert(EqPlan eqPlan);
    
    /** 
     * 更新数据
     *
     * @param eqPlan 实例对象
     * @return 影响行数
     */
    int update(EqPlan eqPlan);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

     /**
      * * 通过类型id获取设备项目列表
      */
     List<EqList> getEqListByItemTypeId(@Param("itemTypeId") BigInteger itemTypeId);
 }