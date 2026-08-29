package com.cuixing.mes.service;

import java.math.BigInteger;
import com.cuixing.mes.entity.MesCarryLog;
import com.cuixing.mes.entity.vo.MesCarryLogVo;

import java.util.List;

 /**
 * 搬运日志;(mes_carry_log)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-10-10
 */
public interface IMesCarryLogService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesCarryLog queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mesCarryLog 筛选条件
     * @return 查询结果
     */
    List<MesCarryLog> selectMesCarryLogList(MesCarryLog mesCarryLog);
    /** 
     * 批量新增数据
     *
     * @param mesCarryLogVo 实例对象
     * @return 实例对象
     */
    int insert(MesCarryLogVo mesCarryLogVo);
    /** 
     * 更新数据
     *
     * @param mesCarryLog 实例对象
     * @return 实例对象
     */
    int update(MesCarryLog mesCarryLog);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}