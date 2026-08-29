package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdPlantOutinLog;
import java.util.List;

 /**
 * 油漆出入库记录;(md_plant_outin_log)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
public interface IMdPlantOutinLogService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdPlantOutinLog queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdPlantOutinLog 筛选条件
     * @return 查询结果
     */
    List<MdPlantOutinLog> selectMdPlantOutinLogList(MdPlantOutinLog mdPlantOutinLog);
    /** 
     * 新增数据
     *
     * @param mdPlantOutinLogs 实例对象
     * @return 实例对象
     */
    List<MdPlantOutinLog> insert(List<MdPlantOutinLog> mdPlantOutinLogs);
    /** 
     * 更新数据
     *
     * @param mdPlantOutinLog 实例对象
     * @return 实例对象
     */
    int update(MdPlantOutinLog mdPlantOutinLog);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

     /**
      * * 通过油漆二维码编号查询数据
      * @param plantCode
      * @return
      */
     MdPlantOutinLog selectByPlantCode(String plantCode);
 }