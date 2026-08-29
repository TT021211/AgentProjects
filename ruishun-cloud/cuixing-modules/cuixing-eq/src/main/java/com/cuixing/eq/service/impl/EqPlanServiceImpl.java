package com.cuixing.eq.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.eq.entity.EqList;
import com.cuixing.eq.mapper.EqListMapper;
import com.cuixing.eq.service.IEqListService;
import com.cuixing.system.api.RemoteJobService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.SysJob;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.eq.entity.EqPlan;
import com.cuixing.eq.mapper.EqPlanMapper;
import com.cuixing.eq.service.IEqPlanService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 点检保养计划;(eq_plan)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-7-8
 */
@Service
public class EqPlanServiceImpl implements IEqPlanService{
    @Autowired
    private EqPlanMapper eqPlanMapper;
    @Autowired
    private EqListMapper eqListMapper;

    @Autowired
    private RemoteSystemService remoteSystemService;
    @Autowired
    private RemoteJobService remoteJobService;

    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqPlan queryById(BigInteger id){
        return eqPlanMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqPlan 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqPlan> selectEqPlanList(EqPlan eqPlan){
       return eqPlanMapper.selectEqPlanList(eqPlan);
    }
    
    /** 
     * 新增数据
     *
     * @param eqPlan 实例对象
     * @return 实例对象
     */
    @Override
//    @GlobalTransactional
    @Transactional
    public int insert(EqPlan eqPlan) {
        eqPlan.setCreateTime(DateUtils.getNowDate());
        eqPlan.setCreateBy(SecurityUtils.getUsername());
        //新增定时任务
        if (eqPlan.getPlanType().equals("0")) {
            String code = remoteSystemService.getAutoCode("EQUIPMENT_PLAN_SPOT_CHECK", SecurityConstants.INNER);
            eqPlan.setPlanCode(code);
        } else if (eqPlan.getPlanType().equals("1")) {
            String code = remoteSystemService.getAutoCode("EQUIPMENT_PLAN_UPKEEP", SecurityConstants.INNER);
            eqPlan.setPlanCode(code);
        } else {
            throw new EqException("点检保养类型出错！");
        }
        eqPlanMapper.insert(eqPlan);
        SysJob sysJob = new SysJob();
        if (eqPlan.getPlanType().equals("0")) {
            sysJob.setJobName("点检" + "(" + eqPlan.getPlanCode() + ")");
            sysJob.setJobGroup("DEFAULT");
            sysJob.setInvokeTarget("spotCheckTask.executeSpotCheckPlanByPlanId" + '(' + eqPlan.getId() + ')');
        } else if (eqPlan.getPlanType().equals("1")) {
            sysJob.setJobName("保养" + "(" + eqPlan.getPlanCode() + ")");
            sysJob.setJobGroup("DEFAULT");
            sysJob.setInvokeTarget("upkeepTask.executeUpkeepPlanByPlanId" + '(' + eqPlan.getId() + ')');
        }
        String cron = cronChange(eqPlan.getMeasureName());
        sysJob.setCronExpression(cron);
        sysJob.setMisfirePolicy("1");
        sysJob.setConcurrent("1");
        if (eqPlan.getIfEnable() == 1){
            sysJob.setStatus("0");
        }else if (eqPlan.getIfEnable() == 0){
            sysJob.setStatus("1");
        }
        R<AjaxResult> ajaxResultR = remoteJobService.addJob(sysJob, SecurityConstants.INNER);
        if (ajaxResultR.getCode() != 200){
            throw new RuntimeException("定时任务服务调用失败{"+ajaxResultR.getMsg()+"}");
        }else{
            SysJob data = remoteJobService.getJobByName(sysJob.getJobName(), SecurityConstants.INNER).getData();
            if (data != null){
                EqPlan result = eqPlanMapper.queryById(eqPlan.getId());
                result.setJobId(new BigInteger(data.getJobId().toString()));
                result.setJobName(data.getJobName());
                return eqPlanMapper.update(result);
            }else{
                throw new RuntimeException("定时任务服务调用失败{"+ajaxResultR.getMsg()+"}");
            }
        }
    }
    
    /** 
     * 更新数据
     *
     * @param eqPlan 实例对象
     * @return 实例对象
     */
    @Override
//    @GlobalTransactional
    @Transactional
    public int update(EqPlan eqPlan){
        eqPlan.setUpdateTime(DateUtils.getNowDate());
		eqPlan.setUpdateBy(SecurityUtils.getUsername());
        SysJob sysJob = remoteJobService.getJob(eqPlan.getJobId().longValue(), SecurityConstants.INNER).getData();
        if (null == sysJob){
            throw new EqException("获取定时任务服务调用失败");
        }
        if (eqPlan.getIfEnable() == 1){
            sysJob.setStatus("0");
        }else if (eqPlan.getIfEnable() == 0){
            sysJob.setStatus("1");
        }
        String cron = cronChange(eqPlan.getMeasureName());
        sysJob.setCronExpression(cron);
        R<AjaxResult> ajaxResultR = remoteJobService.editJob(sysJob, SecurityConstants.INNER);
        if (ajaxResultR.getCode() != 200){
            throw new RuntimeException("定时任务服务调用失败{"+ajaxResultR.getMsg()+"}");
        }
        return eqPlanMapper.update(eqPlan);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
//    @GlobalTransactional
    @Transactional
    public int deleteByIds(BigInteger[] ids){
        Long[] jobIds = new Long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            EqPlan eqPlan = eqPlanMapper.queryById(ids[i]);
            jobIds[i] = eqPlan.getJobId().longValue();
        }
        R<AjaxResult> ajaxResultR = remoteJobService.deleteJob(jobIds, SecurityConstants.INNER);
        if (ajaxResultR.getCode() != 200){
            throw new RuntimeException("定时任务服务调用失败{"+ajaxResultR.getMsg()+"}");
        }
        return eqPlanMapper.deleteByIds(ids);
    }


     private String cronChange(String unit) {
         switch (unit) {
             case "天":
                 //每周天 0点执行
                 return "0 0 0 * * ?";
             case "周":
                 //每周一 0点执行
                 return "0 0 0 ? * MON";
             case "月":
                 //每月1号 0点执行
                 return "0 0 0 1 * ? ";
             case "季":
                 //每月1号 0点执行
                 return "0 0 0 1 1/3 ?";
             case "年":
                 return "0 0 0 1 1 ?";
             default:
                 throw new IllegalArgumentException("无效的时间单位，请选择天、周、月、季、年");
         }
     }

    /**
     * * 通过类型id获取设备项目列表
     */
    @Override
    public List<EqList> getEqListByItemTypeId(BigInteger itemTypeId) {
        return eqPlanMapper.getEqListByItemTypeId(itemTypeId);
    }
    @Override
    public int insertEqList(BigInteger id) {
        EqPlan eqPlan = eqPlanMapper.queryById(id);
        //通过项目类型id获取所有的设备列表
        int result = 0;
        List<EqList> eqListByItemTypeId = eqPlanMapper.getEqListByItemTypeId(eqPlan.getItemTypeId());
        for (int i = 0; i < eqListByItemTypeId.size(); i++) {
            EqList eqList = eqListByItemTypeId.get(i);
            result += eqListMapper.insert(eqList);
        }
        return result;
    }


}