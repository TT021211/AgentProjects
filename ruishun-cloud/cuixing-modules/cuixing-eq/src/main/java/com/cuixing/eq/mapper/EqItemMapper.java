package com.cuixing.eq.mapper;

import com.cuixing.eq.entity.EqList;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqItem;

 /**
 * 设备项目维护;(eq_item)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Mapper
public interface EqItemMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqItem queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqItem 查询条件
     * @return 对象列表
     */
    List<EqItem> selectEqItemList(EqItem eqItem);
    /** 
     * 新增数据
     *
     * @param eqItem 实例对象
     * @return 影响行数
     */
    int insert(EqItem eqItem);
    
    /** 
     * 更新数据
     *
     * @param eqItem 实例对象
     * @return 影响行数
     */
    int update(EqItem eqItem);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);


 }