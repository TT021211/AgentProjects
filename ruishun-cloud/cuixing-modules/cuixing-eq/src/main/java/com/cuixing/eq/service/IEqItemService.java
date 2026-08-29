package com.cuixing.eq.service;

import java.math.BigInteger;
import com.cuixing.eq.entity.EqItem;
import com.cuixing.eq.entity.EqList;

import java.util.List;

 /**
 * 设备项目维护;(eq_item)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
public interface IEqItemService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqItem queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqItem 筛选条件
     * @return 查询结果
     */
    List<EqItem> selectEqItemList(EqItem eqItem);
    /** 
     * 新增数据
     *
     * @param eqItem 实例对象
     * @return 实例对象
     */
    int insert(EqItem eqItem);
    /** 
     * 更新数据
     *
     * @param eqItem 实例对象
     * @return 实例对象
     */
    int update(EqItem eqItem);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

 }