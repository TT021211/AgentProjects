package com.cuixing.eq.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqItemGroupDetail;

 /**
 * 组别详情;(eq_item_group_detail)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-7-5
 */
@Mapper
public interface EqItemGroupDetailMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqItemGroupDetail queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqItemGroupDetail 查询条件
     * @return 对象列表
     */
    List<EqItemGroupDetail> selectEqItemGroupDetailList(EqItemGroupDetail eqItemGroupDetail);
    /** 
     * 新增数据
     *
     * @param eqItemGroupDetail 实例对象
     * @return 影响行数
     */
    int insert(EqItemGroupDetail eqItemGroupDetail);
    
    /** 
     * 更新数据
     *
     * @param eqItemGroupDetail 实例对象
     * @return 影响行数
     */
    int update(EqItemGroupDetail eqItemGroupDetail);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}