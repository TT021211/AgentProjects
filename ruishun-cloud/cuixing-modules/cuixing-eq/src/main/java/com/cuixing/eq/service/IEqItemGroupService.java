package com.cuixing.eq.service;

import java.math.BigInteger;
import com.cuixing.eq.entity.EqItemGroup;
import java.util.List;

 /**
 * 项目组别设置;(eq_item_group)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-7-5
 */
public interface IEqItemGroupService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqItemGroup queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqItemGroup 筛选条件
     * @return 查询结果
     */
    List<EqItemGroup> selectEqItemGroupList(EqItemGroup eqItemGroup);
    /** 
     * 新增数据
     *
     * @param eqItemGroup 实例对象
     * @return 实例对象
     */
    int insert(EqItemGroup eqItemGroup);
    /** 
     * 更新数据
     *
     * @param eqItemGroup 实例对象
     * @return 实例对象
     */
    int update(EqItemGroup eqItemGroup);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}