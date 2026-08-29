package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdWorkbenchWeigh;
import com.cuixing.md.entity.vo.MdWorkbenchWeighVo;

import java.util.List;

 /**
 * 称重机台设置;(md_workbench_weigh)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-8-2
 */
public interface IMdWorkbenchWeighService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchWeigh queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdWorkbenchWeigh 筛选条件
     * @return 查询结果
     */
    List<MdWorkbenchWeigh> selectMdWorkbenchWeighList(MdWorkbenchWeigh mdWorkbenchWeigh);
    /** 
     * 新增数据
     *
     * @param mdWorkbenchWeigh 实例对象
     * @return 实例对象
     */
    int insert(MdWorkbenchWeigh mdWorkbenchWeigh);
    /** 
     * 更新数据
     *
     * @param mdWorkbenchWeigh 实例对象
     * @return 实例对象
     */
    int update(MdWorkbenchWeigh mdWorkbenchWeigh);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

    int updateEnable(MdWorkbenchWeigh mdWorkbenchWeigh);

     List<MdWorkbenchWeighVo> selectMdWorkbenchWeighAll();
 }