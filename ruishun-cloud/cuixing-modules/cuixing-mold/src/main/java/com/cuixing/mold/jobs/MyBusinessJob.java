package com.cuixing.mold.jobs;

import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldLedger;
import com.cuixing.mold.entity.MoldMaintainList;
import com.cuixing.mold.entity.MoldMaintainPlan;
import com.cuixing.mold.entity.MoldNesting;
import com.cuixing.mold.mapper.MoldLedgerMapper;
import com.cuixing.mold.mapper.MoldMaintainListMapper;
import com.cuixing.mold.mapper.MoldMaintainPlanMapper;
import com.cuixing.mold.mapper.MoldNestingMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 定时任务执行业务层
 */
@Component
public class MyBusinessJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(MyBusinessJob.class);
    @Autowired
    private MoldMaintainListMapper moldMaintainListMapper;
    @Autowired
    private MoldMaintainPlanMapper moldMaintainPlanMapper;
    @Autowired
    private MoldLedgerMapper moldLedgerMapper;
    @Autowired
    private MoldNestingMapper moldNestingMapper;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        JobKey jobKey = context.getJobDetail().getKey();
        TriggerKey triggerKey = context.getTrigger().getKey();
        MoldMaintainPlan plan = (MoldMaintainPlan)context.getMergedJobDataMap().get("moldMaintainPlan");


        // 获取作业数据
        String cronExpression = context.getJobDetail().getJobDataMap().getString("cronExpression");
        String endDateStr = context.getJobDetail().getJobDataMap().getString("endDateStr");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        // 解析cron表达式
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
        Cron cron = parser.parse(cronExpression);
        cron.validate();

        // 获取下一次执行时间
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        ZonedDateTime now = ZonedDateTime.now();
        Optional<ZonedDateTime> nextExecution = executionTime.nextExecution(now);

        // 解析结束时间
        ZonedDateTime endDate = ZonedDateTime.parse(endDateStr, formatter);

        // 判断是否为最后一次执行
        if (nextExecution.isPresent() && nextExecution.get().isBefore(endDate)) {
            // 这是最后一次执行之前的逻辑，不在这里更新计划状态
        }else{
            // 更新计划状态的逻辑
            MoldMaintainPlan moldMaintainPlan=new MoldMaintainPlan();
            moldMaintainPlan.setMaintainPlanId(plan.getMaintainPlanId());
            moldMaintainPlan.setMaintainPlanStatus("FINISH");
            this.moldMaintainPlanMapper.update(moldMaintainPlan);
            //清除该计划关联套模
            List<MoldNesting> moldNestings = this.moldNestingMapper.queryByPlanId(plan.getMaintainPlanId());
            for (MoldNesting moldNesting:moldNestings){
                if (moldNesting.getPlanId()!=null&& Objects.equals(moldNesting.getPlanId(), plan.getMaintainPlanId())){
                    this.moldNestingMapper.updateToPlanNull(moldNesting.getNestingMoldId());
                }
            }
        }

        if (plan==null){
            throw new RuntimeException("对象为空！");
        }
        try {
            logger.info("Deserialized MoldMaintainPlan: {}", plan);
            // 现在可以使用 plan 对象进行业务逻辑处理
            if (plan.getMoldNesting() != null) {
                if (!plan.getMoldNesting().isEmpty()) {
                    for (MoldNesting oldNesting : plan.getMoldNesting()) {
                        //修改套模下的模具状态
                        List<MoldLedger> moldLedgers = this.moldLedgerMapper.queryByNestingMoldId(oldNesting.getNestingMoldId());
                        for (MoldLedger ledger : moldLedgers){
                            if (!Objects.equals(ledger.getStatus(), "MAINTENANCE")){
                                ledger.setStatus("MAINTENANCE");
                                this.moldLedgerMapper.update(ledger);
                            }
                        }
                        //新增待保养计划列表
                        MoldMaintainList maintainList = getMoldMaintainList(oldNesting, plan);

                        this.moldMaintainListMapper.insert(maintainList);
                        //给当前计划加上任务标识
                        MoldMaintainPlan moldMaintainPlan=new MoldMaintainPlan();
                        moldMaintainPlan.setMaintainPlanId(plan.getMaintainPlanId());
                        moldMaintainPlan.setJobName(jobKey.getName());
                        moldMaintainPlan.setTriggerName(triggerKey.getName());
                        this.moldMaintainPlanMapper.update(moldMaintainPlan);
                    }
                }
                // 处理逻辑
            } else {
                throw new BussinessException("请先添加套模！");
            }
        } catch (Exception e) {
            throw new JobExecutionException("执行作业失败", e);
        }
    }

    private static MoldMaintainList getMoldMaintainList(MoldNesting oldNesting, MoldMaintainPlan plan) {
        MoldMaintainList maintainList = new MoldMaintainList();
        maintainList.setPlanId(plan.getMaintainPlanId());
        maintainList.setNestingName(oldNesting.getNestingName());
        maintainList.setNestingScope(oldNesting.getNestingScope());
        maintainList.setNestingArea(oldNesting.getNestingArea());
        maintainList.setNestingId(oldNesting.getNestingMoldId());
        maintainList.setPlanName(plan.getMaintainPlanName());
        maintainList.setPlanType(plan.getMaintainPlanType());
        maintainList.setInspectionDate(new Date());
        maintainList.setCreateTime(new Date());
        maintainList.setCreateBy(SecurityUtils.getUsername());
        maintainList.setStatus("MAINTENANCE");
        return maintainList;
    }
}
