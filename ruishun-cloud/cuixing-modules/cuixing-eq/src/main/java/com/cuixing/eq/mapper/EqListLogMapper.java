package com.cuixing.eq.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqListLog;

 /**
 * 点检(保养)记录;(eq_list_log)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-7-9
 */
@Mapper
public interface EqListLogMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqListLog queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqListLog 查询条件
     * @return 对象列表
     */
    List<EqListLog> selectEqListLogList(EqListLog eqListLog);
    /** 
     * 新增数据
     *
     * @param eqListLog 实例对象
     * @return 影响行数
     */
    int insert(EqListLog eqListLog);
    
    /** 
     * 更新数据
     *
     * @param eqListLog 实例对象
     * @return 影响行数
     */
    int update(EqListLog eqListLog);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}