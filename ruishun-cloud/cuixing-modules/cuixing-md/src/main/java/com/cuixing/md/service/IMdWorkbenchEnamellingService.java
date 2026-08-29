package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdWorkbenchEnamelling;
import java.util.List;

 /**
 * 机台设置;(md_workbench_enamelling)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
public interface IMdWorkbenchEnamellingService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchEnamelling queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdWorkbenchEnamelling 筛选条件
     * @return 查询结果
     */
    List<MdWorkbenchEnamelling> selectMdWorkbenchEnamellingList(MdWorkbenchEnamelling mdWorkbenchEnamelling);
    /** 
     * 新增数据
     *
     * @param mdWorkbenchEnamelling 实例对象
     * @return 实例对象
     */
    int insert(MdWorkbenchEnamelling mdWorkbenchEnamelling);
    /** 
     * 更新数据
     *
     * @param mdWorkbenchEnamelling 实例对象
     * @return 实例对象
     */
    int update(MdWorkbenchEnamelling mdWorkbenchEnamelling);
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
      * @param mdWorkbenchEnamelling
      * @return 是否成功
      */
    int updateEnable(MdWorkbenchEnamelling mdWorkbenchEnamelling);
 }