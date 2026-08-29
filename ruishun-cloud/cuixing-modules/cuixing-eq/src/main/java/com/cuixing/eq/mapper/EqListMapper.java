package com.cuixing.eq.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqList;
import org.apache.ibatis.annotations.Param;

/**
 * 待点检(保养)列表;(eq_list)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-7-9
 */
@Mapper
public interface EqListMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqList queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqList 查询条件
     * @return 对象列表
     */
    List<EqList> selectEqListList(EqList eqList);
    /** 
     * 新增数据
     *
     * @param eqList 实例对象
     * @return 影响行数
     */
    int insert(EqList eqList);
    
    /** 
     * 更新数据
     *
     * @param eqList 实例对象
     * @return 影响行数
     */
    int update(EqList eqList);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

    int getListByPlanType(@Param("planType") String planType);
 }