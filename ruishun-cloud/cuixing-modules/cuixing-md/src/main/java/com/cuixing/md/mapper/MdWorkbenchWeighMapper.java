package com.cuixing.md.mapper;

import com.cuixing.md.entity.vo.MdWorkbenchWeighVo;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdWorkbenchWeigh;

 /**
 * 称重机台设置;(md_workbench_weigh)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-8-2
 */
@Mapper
public interface MdWorkbenchWeighMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchWeigh queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mdWorkbenchWeigh 查询条件
     * @return 对象列表
     */
    List<MdWorkbenchWeigh> selectMdWorkbenchWeighList(MdWorkbenchWeigh mdWorkbenchWeigh);
    /** 
     * 新增数据
     *
     * @param mdWorkbenchWeigh 实例对象
     * @return 影响行数
     */
    int insert(MdWorkbenchWeigh mdWorkbenchWeigh);
    
    /** 
     * 更新数据
     *
     * @param mdWorkbenchWeigh 实例对象
     * @return 影响行数
     */
    int update(MdWorkbenchWeigh mdWorkbenchWeigh);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

     List<MdWorkbenchWeighVo> selectMdWorkbenchWeighAll();
 }