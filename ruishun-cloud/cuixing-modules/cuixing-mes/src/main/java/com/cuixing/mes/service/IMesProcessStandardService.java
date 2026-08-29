package com.cuixing.mes.service;

import java.math.BigInteger;
import com.cuixing.mes.entity.MesProcessStandard;
import java.util.List;

 /**
 * 工艺标准;(mes_process_standard)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-7-23
 */
public interface IMesProcessStandardService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProcessStandard queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mesProcessStandard 筛选条件
     * @return 查询结果
     */
    List<MesProcessStandard> selectMesProcessStandardList(MesProcessStandard mesProcessStandard);
    /** 
     * 新增数据
     *
     * @param mesProcessStandard 实例对象
     * @return 实例对象
     */
    int insert(MesProcessStandard mesProcessStandard);
    /** 
     * 更新数据
     *
     * @param mesProcessStandard 实例对象
     * @return 实例对象
     */
    int update(MesProcessStandard mesProcessStandard);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

     List<MesProcessStandard> getMesProcessStandardList(String standardCode);
 }