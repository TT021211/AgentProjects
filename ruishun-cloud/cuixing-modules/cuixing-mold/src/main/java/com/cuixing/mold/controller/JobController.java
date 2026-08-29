package com.cuixing.mold.controller;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.mold.entity.MoldMaintainPlan;
import com.cuixing.mold.entity.MoldNesting;
import com.cuixing.mold.entity.MoldScheduledTask;
import com.cuixing.mold.mapper.MoldMaintainListMapper;
import com.cuixing.mold.mapper.MoldMaintainPlanMapper;
import com.cuixing.mold.mapper.MoldNestingMapper;
import com.cuixing.mold.mapper.MoldScheduledTaskMapper;
import com.cuixing.mold.scheduler.SchedulerService;
import com.cuixing.mold.jobs.MyBusinessJob;
import io.swagger.annotations.Api;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Api(tags = "模具定时接口")
@RestController
@RequestMapping("/mold/scheduleJob")
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private SchedulerService schedulerService;

    /**
     * 通过POST请求安排任务。
     * 接收一个MoldMaintainPlan对象作为请求体，其中包含维护计划的详细信息。
     * 使用这些信息来生成cron表达式，并根据此表达式安排一个定时任务。
     * 如果安排任务过程中出现异常，将返回错误信息；否则，返回成功信息。
     *
     * @param moldMaintainPlan 维护计划的详细信息，包括计划的开始和结束时间等。
     * @return 定时任务安排成功或失败的消息。
     */
    @PostMapping("/task")
    public String scheduleJob(@RequestBody MoldMaintainPlan moldMaintainPlan) {
        //判断时间之隔大于等于30天
        boolean b = DateCompare(moldMaintainPlan.getMaintainPlanStarttime(), moldMaintainPlan.getMaintainPlanEndtime());
        if (!b){
            throw new BussinessException("开始时间和结束时间之间的间隔必须大于30天！");
        }
        // 根据维护计划的属性生成cron表达式
        String cronExpression = generateCronExpression(moldMaintainPlan.getAttr4());
        try {
            // 格式化维护计划的开始和结束时间
            Date maintainPlanStarttime = moldMaintainPlan.getMaintainPlanStarttime();
            Date maintainPlanEndtime = moldMaintainPlan.getMaintainPlanEndtime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startDateStr = dateFormat.format(maintainPlanStarttime);
            String endDateStr = dateFormat.format(maintainPlanEndtime);

            // 生成唯一的任务和触发器名称
            String jobNames = "Job_" + UUID.randomUUID().toString();
            String triggerNames = "Trigger_" + UUID.randomUUID().toString();
            String groupName = "MyJob_" + UUID.randomUUID().toString();

            // 调用调度服务安排任务
            schedulerService.scheduleJob(MyBusinessJob.class, jobNames, triggerNames,groupName,
                    startDateStr, endDateStr, cronExpression, moldMaintainPlan);

        } catch (ParseException | SchedulerException e) {
            log.error("调度异常：", e);
            return "定时任务启动失败！";
        }
        return "定时任务启动成功！";
    }

    /**
     * 通过POST请求重新安排任务调度。
     * 此方法用于接收来自前端的维护计划信息，并根据这些信息重新配置任务的调度。
     * 它首先根据传入的计划信息生成一个cron表达式，然后使用这个表达式来调整任务的调度时间。
     *
     * @param moldMaintainPlan 维护计划对象，包含任务调度的相关信息。
     * @return 返回一个字符串消息，指示任务调度更新是否成功。
     */
    @PostMapping("/editTask")
    public String rescheduleJob(@RequestBody MoldMaintainPlan moldMaintainPlan) {
        //判断时间之隔大于等于30天
        boolean b = DateCompare(moldMaintainPlan.getMaintainPlanStarttime(), moldMaintainPlan.getMaintainPlanEndtime());
        if (!b){
            throw new BussinessException("开始时间和结束时间之间的间隔必须大于30天！");
        }
        // 根据维护计划的属性4生成cron表达式
        String cronExpression = generateCronExpression(moldMaintainPlan.getAttr4());
        List<MoldNesting> moldNesting = moldMaintainPlan.getMoldNesting();
        if (!moldNesting.isEmpty()) {
            for (MoldNesting nesting : moldNesting) {
                if (nesting.getPlanId() != null && !Objects.equals(nesting.getPlanId(), moldMaintainPlan.getMaintainPlanId())) {
                    throw new BussinessException("请先删除关联计划！");
                }
            }
        }
        try {
            // 提取维护计划的触发器名称、开始时间和结束时间
            String triggerName = moldMaintainPlan.getTriggerName();
            Date maintainPlanStarttime = moldMaintainPlan.getMaintainPlanStarttime();
            Date maintainPlanEndtime = moldMaintainPlan.getMaintainPlanEndtime();
            // 使用SimpleDateFormat格式化日期时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 将开始时间和结束时间转换为字符串格式
            String startDateStr = dateFormat.format(maintainPlanStarttime);
            String endDateStr = dateFormat.format(maintainPlanEndtime);
            String groupName = "MyJob_" + UUID.randomUUID().toString();
            // 调用调度服务重新安排任务
            schedulerService.rescheduleJob(MyBusinessJob.class, triggerName, groupName, cronExpression, startDateStr, endDateStr, moldMaintainPlan);

        } catch (SchedulerException e) {
            // 处理调度异常，打印堆栈跟踪并返回失败消息
            log.error("调度异常：", e);
            return "更新定时任务失败！";
        } catch (ParseException e) {
            // 处理日期解析异常，抛出运行时异常
            throw new RuntimeException(e);
        }
        // 更新成功，返回成功消息
        return "更新定时任务成功！";
    }

    @PostMapping("/pauseTask")
    public String pauseJob(@RequestParam String jobName, @RequestParam String groupName) {
        try {
            schedulerService.pauseJob(jobName, groupName);
        } catch (SchedulerException e) {
            log.error("调度异常：", e);
            return "暂停任务失败！";
        }
        return "暂停任务成功！";
    }
    @PostMapping("/resumeTask")
    public String resumeJob(@RequestParam String jobName, @RequestParam String groupName) {
        try {
            schedulerService.resumeJob(jobName, groupName);
        } catch (SchedulerException e) {
            log.error("调度异常：", e);
            return "恢复任务失败！";
        }
        return "恢复任务成功！";
    }
    @PostMapping("/deleteTask")
    public String deleteJob(@RequestParam String jobName, @RequestParam String groupName,@RequestParam String taskId) {
        try {
            schedulerService.deleteJob(jobName, groupName,taskId);
        } catch (SchedulerException e) {
            log.error("调度异常：", e);
            return "删除任务失败！";
        }
        return "删除任务成功！";
    }
    private String generateCronExpression(int frequency) {
        // 一个月执行一次
        if (frequency == 1) {
            return "0 0 0 1 * ?"; // 每月1号0点0分0秒执行
        } else if (frequency == 2) {
            return "0 0 1 1,4,7,10 *"; // 如果你想在每个季度的第一天的凌晨 0 点运行任务
        } else if (frequency == 3) {
            return "0 0 1 1 *"; // 在每年的 1 月 1 日的凌晨 0 点运行任务
        } else {
            throw new RuntimeException("传递值有误！");
        }
    }

    private static boolean DateCompare(Date date1, Date date2) {
        // Calculate the absolute difference in milliseconds
        long differenceInMilliseconds = Math.abs(date1.getTime() - date2.getTime());
        // Convert milliseconds to days
        long differenceInDays = differenceInMilliseconds / (24 * 60 * 60 * 1000);
        // Check if the difference is greater than 30 days
        return differenceInDays > 30;
    }
}
