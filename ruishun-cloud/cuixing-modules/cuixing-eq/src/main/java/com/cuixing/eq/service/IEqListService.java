package com.cuixing.eq.service;

import java.math.BigInteger;
import com.cuixing.eq.entity.EqList;
import java.util.List;

 /**
 * 待点检(保养)列表;(eq_list)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-7-9
 */
public interface IEqListService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqList queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqList 筛选条件
     * @return 查询结果
     */
    List<EqList> selectEqListList(EqList eqList);
    /** 
     * 新增数据
     *
     * @param eqList 实例对象
     * @return 实例对象
     */
    int insert(EqList eqList);
    /** 
     * 更新数据
     *
     * @param eqList 实例对象
     * @return 实例对象
     */
    int update(EqList eqList);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

    /**
      * 根据计划id删除待点检(保养)列表
      * @param planId
      * @return
      */
     int deleteByPlanId(BigInteger planId);

     /**
      * 获取待点检、待保养列表数量
      */
     int getListByPlanType(String planType);
 }