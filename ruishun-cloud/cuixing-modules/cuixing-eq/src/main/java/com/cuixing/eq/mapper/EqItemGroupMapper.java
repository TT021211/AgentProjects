package com.cuixing.eq.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqItemGroup;

 /**
 * 项目组别设置;(eq_item_group)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-7-5
 */
@Mapper
public interface EqItemGroupMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqItemGroup queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqItemGroup 查询条件
     * @return 对象列表
     */
    List<EqItemGroup> selectEqItemGroupList(EqItemGroup eqItemGroup);
    /** 
     * 新增数据
     *
     * @param eqItemGroup 实例对象
     * @return 影响行数
     */
    int insert(EqItemGroup eqItemGroup);
    
    /** 
     * 更新数据
     *
     * @param eqItemGroup 实例对象
     * @return 影响行数
     */
    int update(EqItemGroup eqItemGroup);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}