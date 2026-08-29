package com.cuixing.ecm.service;

import java.util.List;
import com.cuixing.ecm.domain.EcmScheduledTaskLog;

/**
 * 能耗定时任务执行日志Service接口
 * 
 * @author zhan
 * @date 2024-07-25
 */
public interface IEcmScheduledTaskLogService 
{
    /**
     * 查询能耗定时任务执行日志
     * 
     * @param id 能耗定时任务执行日志主键
     * @return 能耗定时任务执行日志
     */
    public EcmScheduledTaskLog selectEcmScheduledTaskLogById(Long id);

    /**
     * 查询能耗定时任务执行日志列表
     * 
     * @param ecmScheduledTaskLog 能耗定时任务执行日志
     * @return 能耗定时任务执行日志集合
     */
    public List<EcmScheduledTaskLog> selectEcmScheduledTaskLogList(EcmScheduledTaskLog ecmScheduledTaskLog);

    /**
     * 新增能耗定时任务执行日志
     * 
     * @param ecmScheduledTaskLog 能耗定时任务执行日志
     * @return 结果
     */
    public int insertEcmScheduledTaskLog(EcmScheduledTaskLog ecmScheduledTaskLog);

    /**
     * 修改能耗定时任务执行日志
     * 
     * @param ecmScheduledTaskLog 能耗定时任务执行日志
     * @return 结果
     */
    public int updateEcmScheduledTaskLog(EcmScheduledTaskLog ecmScheduledTaskLog);

    /**
     * 批量删除能耗定时任务执行日志
     * 
     * @param ids 需要删除的能耗定时任务执行日志主键集合
     * @return 结果
     */
    public int deleteEcmScheduledTaskLogByIds(Long[] ids);

    /**
     * 删除能耗定时任务执行日志信息
     * 
     * @param id 能耗定时任务执行日志主键
     * @return 结果
     */
    public int deleteEcmScheduledTaskLogById(Long id);
}
