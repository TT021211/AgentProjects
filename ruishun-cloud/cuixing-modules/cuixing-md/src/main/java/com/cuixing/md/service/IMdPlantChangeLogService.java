package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdPlantChangeLog;
import java.util.List;

 /**
 * 油漆更换记录;(md_plant_change_log)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
public interface IMdPlantChangeLogService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdPlantChangeLog queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdPlantChangeLog 筛选条件
     * @return 查询结果
     */
    List<MdPlantChangeLog> selectMdPlantChangeLogList(MdPlantChangeLog mdPlantChangeLog);
    /** 
     * 新增数据
     *
     * @param mdPlantChangeLog 实例对象
     * @return 实例对象
     */
    int insert(MdPlantChangeLog mdPlantChangeLog);
    /** 
     * 更新数据
     *
     * @param mdPlantChangeLog 实例对象
     * @return 实例对象
     */
    int update(MdPlantChangeLog mdPlantChangeLog);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}