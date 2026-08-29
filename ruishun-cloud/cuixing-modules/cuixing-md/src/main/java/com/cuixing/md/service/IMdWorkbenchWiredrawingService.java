package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdWorkbenchWiredrawing;
import java.util.List;

 /**
 * 机台设置;(md_workbench_wiredrawing)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-4
 */
public interface IMdWorkbenchWiredrawingService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchWiredrawing queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdWorkbenchWiredrawing 筛选条件
     * @return 查询结果
     */
    List<MdWorkbenchWiredrawing> selectMdWorkbenchWiredrawingList(MdWorkbenchWiredrawing mdWorkbenchWiredrawing);
    /** 
     * 新增数据
     *
     * @param mdWorkbenchWiredrawing 实例对象
     * @return 实例对象
     */
    int insert(MdWorkbenchWiredrawing mdWorkbenchWiredrawing);
    /** 
     * 更新数据
     *
     * @param mdWorkbenchWiredrawing 实例对象
     * @return 实例对象
     */
    int update(MdWorkbenchWiredrawing mdWorkbenchWiredrawing);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

     /**
      * 启用禁用
      *
      * @param mdWorkbenchWiredrawing
      * @return 是否成功
      */
    int updateEnable(MdWorkbenchWiredrawing mdWorkbenchWiredrawing);
 }