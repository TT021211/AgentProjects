package com.cuixing.md.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdWorkbenchWiredrawing;

 /**
 * 机台设置;(md_workbench_wiredrawing)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-4
 */
@Mapper
public interface MdWorkbenchWiredrawingMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchWiredrawing queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mdWorkbenchWiredrawing 查询条件
     * @return 对象列表
     */
    List<MdWorkbenchWiredrawing> selectMdWorkbenchWiredrawingList(MdWorkbenchWiredrawing mdWorkbenchWiredrawing);
    /** 
     * 新增数据
     *
     * @param mdWorkbenchWiredrawing 实例对象
     * @return 影响行数
     */
    int insert(MdWorkbenchWiredrawing mdWorkbenchWiredrawing);
    
    /** 
     * 更新数据
     *
     * @param mdWorkbenchWiredrawing 实例对象
     * @return 影响行数
     */
    int update(MdWorkbenchWiredrawing mdWorkbenchWiredrawing);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}