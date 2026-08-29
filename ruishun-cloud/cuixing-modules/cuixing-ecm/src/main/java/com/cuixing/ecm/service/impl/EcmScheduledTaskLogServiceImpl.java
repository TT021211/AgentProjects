package com.cuixing.ecm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.ecm.mapper.EcmScheduledTaskLogMapper;
import com.cuixing.ecm.domain.EcmScheduledTaskLog;
import com.cuixing.ecm.service.IEcmScheduledTaskLogService;

/**
 * 能耗定时任务执行日志Service业务层处理
 * 
 * @author zhan
 * @date 2024-07-25
 */
@Service
public class EcmScheduledTaskLogServiceImpl implements IEcmScheduledTaskLogService 
{
    @Autowired
    private EcmScheduledTaskLogMapper ecmScheduledTaskLogMapper;

    /**
     * 查询能耗定时任务执行日志
     * 
     * @param id 能耗定时任务执行日志主键
     * @return 能耗定时任务执行日志
     */
    @Override
    public EcmScheduledTaskLog selectEcmScheduledTaskLogById(Long id)
    {
        return ecmScheduledTaskLogMapper.selectEcmScheduledTaskLogById(id);
    }

    /**
     * 查询能耗定时任务执行日志列表
     * 
     * @param ecmScheduledTaskLog 能耗定时任务执行日志
     * @return 能耗定时任务执行日志
     */
    @Override
    public List<EcmScheduledTaskLog> selectEcmScheduledTaskLogList(EcmScheduledTaskLog ecmScheduledTaskLog)
    {
        return ecmScheduledTaskLogMapper.selectEcmScheduledTaskLogList(ecmScheduledTaskLog);
    }

    /**
     * 新增能耗定时任务执行日志
     * 
     * @param ecmScheduledTaskLog 能耗定时任务执行日志
     * @return 结果
     */
    @Override
    public int insertEcmScheduledTaskLog(EcmScheduledTaskLog ecmScheduledTaskLog)
    {
        return ecmScheduledTaskLogMapper.insertEcmScheduledTaskLog(ecmScheduledTaskLog);
    }

    /**
     * 修改能耗定时任务执行日志
     * 
     * @param ecmScheduledTaskLog 能耗定时任务执行日志
     * @return 结果
     */
    @Override
    public int updateEcmScheduledTaskLog(EcmScheduledTaskLog ecmScheduledTaskLog)
    {
        return ecmScheduledTaskLogMapper.updateEcmScheduledTaskLog(ecmScheduledTaskLog);
    }

    /**
     * 批量删除能耗定时任务执行日志
     * 
     * @param ids 需要删除的能耗定时任务执行日志主键
     * @return 结果
     */
    @Override
    public int deleteEcmScheduledTaskLogByIds(Long[] ids)
    {
        return ecmScheduledTaskLogMapper.deleteEcmScheduledTaskLogByIds(ids);
    }

    /**
     * 删除能耗定时任务执行日志信息
     * 
     * @param id 能耗定时任务执行日志主键
     * @return 结果
     */
    @Override
    public int deleteEcmScheduledTaskLogById(Long id)
    {
        return ecmScheduledTaskLogMapper.deleteEcmScheduledTaskLogById(id);
    }
}
