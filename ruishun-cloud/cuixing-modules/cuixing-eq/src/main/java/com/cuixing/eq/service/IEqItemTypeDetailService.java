package com.cuixing.eq.service;

import java.math.BigInteger;

import com.cuixing.eq.entity.EqItemType;
import com.cuixing.eq.entity.EqItemTypeDetail;
import java.util.List;

 /**
 * 项目类型详情;(eq_item_type_detail)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
public interface IEqItemTypeDetailService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqItemTypeDetail queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqItemTypeDetail 筛选条件
     * @return 查询结果
     */
    List<EqItemTypeDetail> selectEqItemTypeDetailList(EqItemTypeDetail eqItemTypeDetail);
    /** 
     * 新增数据
     *
     * @param eqItemTypeDetail 实例对象
     * @return 实例对象
     */
    int insert(EqItemTypeDetail eqItemTypeDetail);
    /** 
     * 更新数据
     *
     * @param eqItemTypeDetail 实例对象
     * @return 实例对象
     */
    int update(EqItemTypeDetail eqItemTypeDetail);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

     /**
      * * 批量新增数据
      * @param ids
      * @param eqItemType
      * @return
      */
    int allocatedUserByIds(Long[] ids, EqItemType eqItemType);
 }