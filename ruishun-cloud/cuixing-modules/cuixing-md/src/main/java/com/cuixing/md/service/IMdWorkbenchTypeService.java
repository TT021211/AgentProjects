package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdWorkbenchType;
import java.util.List;

 /**
 * 机台类型;(md_workbench_type)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
public interface IMdWorkbenchTypeService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchType queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdWorkbenchType 筛选条件
     * @return 查询结果
     */
    List<MdWorkbenchType> selectMdWorkbenchTypeList(MdWorkbenchType mdWorkbenchType);
    /** 
     * 新增数据
     *
     * @param mdWorkbenchType 实例对象
     * @return 实例对象
     */
    int insert(MdWorkbenchType mdWorkbenchType);
    /** 
     * 更新数据
     *
     * @param mdWorkbenchType 实例对象
     * @return 实例对象
     */
    int update(MdWorkbenchType mdWorkbenchType);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}