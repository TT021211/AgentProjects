package com.cuixing.eq.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqItemType;

 /**
 * 项目类型设置;(eq_item_type)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Mapper
public interface EqItemTypeMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqItemType queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqItemType 查询条件
     * @return 对象列表
     */
    List<EqItemType> selectEqItemTypeList(EqItemType eqItemType);
    /** 
     * 新增数据
     *
     * @param eqItemType 实例对象
     * @return 影响行数
     */
    int insert(EqItemType eqItemType);
    
    /** 
     * 更新数据
     *
     * @param eqItemType 实例对象
     * @return 影响行数
     */
    int update(EqItemType eqItemType);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}