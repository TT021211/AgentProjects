package com.cuixing.md.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdWorkbenchType;

 /**
 * 机台类型;(md_workbench_type)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@Mapper
public interface MdWorkbenchTypeMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchType queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mdWorkbenchType 查询条件
     * @return 对象列表
     */
    List<MdWorkbenchType> selectMdWorkbenchTypeList(MdWorkbenchType mdWorkbenchType);
    /** 
     * 新增数据
     *
     * @param mdWorkbenchType 实例对象
     * @return 影响行数
     */
    int insert(MdWorkbenchType mdWorkbenchType);
    
    /** 
     * 更新数据
     *
     * @param mdWorkbenchType 实例对象
     * @return 影响行数
     */
    int update(MdWorkbenchType mdWorkbenchType);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}