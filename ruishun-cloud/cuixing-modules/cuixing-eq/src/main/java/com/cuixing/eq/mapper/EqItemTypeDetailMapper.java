package com.cuixing.eq.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqItemTypeDetail;

 /**
 * 项目类型详情;(eq_item_type_detail)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Mapper
public interface EqItemTypeDetailMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqItemTypeDetail queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqItemTypeDetail 查询条件
     * @return 对象列表
     */
    List<EqItemTypeDetail> selectEqItemTypeDetailList(EqItemTypeDetail eqItemTypeDetail);
    /** 
     * 新增数据
     *
     * @param eqItemTypeDetail 实例对象
     * @return 影响行数
     */
    int insert(EqItemTypeDetail eqItemTypeDetail);
    
    /** 
     * 更新数据
     *
     * @param eqItemTypeDetail 实例对象
     * @return 影响行数
     */
    int update(EqItemTypeDetail eqItemTypeDetail);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}