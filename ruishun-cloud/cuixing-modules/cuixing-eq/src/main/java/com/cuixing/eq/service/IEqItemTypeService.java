package com.cuixing.eq.service;

import java.math.BigInteger;
import com.cuixing.eq.entity.EqItemType;
import java.util.List;

 /**
 * 项目类型设置;(eq_item_type)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
public interface IEqItemTypeService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqItemType queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqItemType 筛选条件
     * @return 查询结果
     */
    List<EqItemType> selectEqItemTypeList(EqItemType eqItemType);
    /** 
     * 新增数据
     *
     * @param eqItemType 实例对象
     * @return 实例对象
     */
    int insert(EqItemType eqItemType);
    /** 
     * 更新数据
     *
     * @param eqItemType 实例对象
     * @return 实例对象
     */
    int update(EqItemType eqItemType);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}