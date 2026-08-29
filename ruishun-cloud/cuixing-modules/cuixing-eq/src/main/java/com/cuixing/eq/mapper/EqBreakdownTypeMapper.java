package com.cuixing.eq.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqBreakdownType;

 /**
 * 设备故障类型;(eq_breakdown_type)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-7-3
 */
@Mapper
public interface EqBreakdownTypeMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqBreakdownType queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqBreakdownType 查询条件
     * @return 对象列表
     */
    List<EqBreakdownType> selectEqBreakdownTypeList(EqBreakdownType eqBreakdownType);
    /** 
     * 新增数据
     *
     * @param eqBreakdownType 实例对象
     * @return 影响行数
     */
    int insert(EqBreakdownType eqBreakdownType);
    
    /** 
     * 更新数据
     *
     * @param eqBreakdownType 实例对象
     * @return 影响行数
     */
    int update(EqBreakdownType eqBreakdownType);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}