package com.cuixing.eq.service;

import java.math.BigInteger;

import com.cuixing.eq.entity.EqItemGroup;
import com.cuixing.eq.entity.EqItemGroupDetail;
import java.util.List;

 /**
 * 组别详情;(eq_item_group_detail)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-7-5
 */
public interface IEqItemGroupDetailService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqItemGroupDetail queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqItemGroupDetail 筛选条件
     * @return 查询结果
     */
    List<EqItemGroupDetail> selectEqItemGroupDetailList(EqItemGroupDetail eqItemGroupDetail);
    /** 
     * 新增数据
     *
     * @param eqItemGroupDetail 实例对象
     * @return 实例对象
     */
    int insert(EqItemGroupDetail eqItemGroupDetail);
    /** 
     * 更新数据
     *
     * @param eqItemGroupDetail 实例对象
     * @return 实例对象
     */
    int update(EqItemGroupDetail eqItemGroupDetail);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

    int allocatedUserByIds(Long[] equipmentIds, EqItemGroup eqItemGroup);
 }