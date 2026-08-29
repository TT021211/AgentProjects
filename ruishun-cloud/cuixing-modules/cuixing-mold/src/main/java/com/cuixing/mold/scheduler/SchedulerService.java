package com.cuixing.mold.scheduler;

import com.cuixing.mold.entity.MoldMaintainPlan;
import com.cuixing.mold.entity.MoldScheduledTask;
import com.cuixing.mold.jobs.MyBusinessJob;
import com.cuixing.mold.mapper.MoldMaintainListMapper;
import com.cuixing.mold.mapper.MoldMaintainPlanMapper;
import com.cuixing.mold.mapper.MoldNestingMapper;
import com.cuixing.mold.mapper.MoldScheduledTaskMapper;
import com.cuixing.mold.utils.ScheduledTaskRepository;
import com.cuixing.system.api.factory.RemoteMdItemFallbackFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 定时任务管理服务
 */
@Service
public class SchedulerService implements DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ObjectMapper objectMapper; // 自动装配 ObjectMapper 用于 JSON 处理
    @Autowired
    private ScheduledTaskRepository scheduledTaskRepository;

    /**
     * 定义一个方法，用于安排新的作业（Job）。
     * 作业是根据特定的触发器定时执行的。
     *
     * @param jobClass 作业的类，必须继承自Job。
     * @param jobName 作业的名称。
     * @param triggerName 触发器的名称。
     * @param startDateStr 作业开始执行的日期时间字符串。
     * @param endDateStr 作业停止执行的日期时间字符串。
     * @param cronExpression cron表达式，用于定义触发器的执行周期。
     * @param moldMaintainPlan 传递给作业的数据对象，用于在作业执行时访问。
     * @throws ParseException 如果日期时间字符串格式不正确。
     * @throws SchedulerException 如果调度器在安排作业时遇到错误。
     */
    public void scheduleJob(Class<? extends Job> jobClass, String jobName, String triggerName,String groupName,
                            String startDateStr, String endDateStr, String cronExpression,
                            MoldMaintainPlan moldMaintainPlan)
            throws ParseException, SchedulerException {
        // 根据日期时间字符串格式化作业的开始和结束时间。
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);

        // 创建一个JobDataMap，用于存储作业执行时需要的数据。
        insertJob(jobClass,triggerName,groupName,cronExpression,startDate,endDate,moldMaintainPlan,jobName,triggerName,startDateStr,endDateStr);
    }


    /**
     * 重新安排任务，根据新的调度需求调整已存在的任务的执行计划。
     * 此方法用于更新任务的触发器和相关配置，以实现任务的重新调度。
     *
     * @param jobClass 任务类，定义了任务的执行逻辑。
     * @param triggerName 触发器名称，用于唯一标识触发器。
     * @param cronExpression CRON表达式，用于定义触发器的执行时间。
     * @param startDateStr 开始日期字符串，指定任务开始执行的时间。
     * @param endDateStr 结束日期字符串，指定任务停止执行的时间。
     * @param moldMaintainPlan 模具维护计划对象，包含任务执行所需的数据。
     * @throws ParseException 当解析日期字符串出错时抛出。
     * @throws SchedulerException 当操作调度器出错时抛出。
     */
    public void rescheduleJob(Class<? extends Job> jobClass, String triggerName,String groupName, String cronExpression, String startDateStr, String endDateStr,
                              MoldMaintainPlan moldMaintainPlan) throws ParseException, SchedulerException {
        // 格式化日期字符串为Date对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);

        // 生成新的任务和触发器名称，以避免名称冲突
        String jobNames = "Job_" + UUID.randomUUID().toString();
        String triggerNames = "Trigger_" + UUID.randomUUID().toString();

        // 根据触发器名称获取现有的触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);
        Trigger oldTrigger = scheduler.getTrigger(triggerKey);

        // 检查触发器是否存在
        if (oldTrigger == null) {
            throw new SchedulerException("Trigger not found with name: " + triggerName);
        }

        // 根据触发器获取关联的任务细节
        JobKey jobKey = oldTrigger.getJobKey();
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        // 检查任务细节是否存在
        if (jobDetail == null) {
            throw new SchedulerException("JobDetail not found with key: " + jobKey);
        }

        // 暂停并删除现有任务，为重新调度做准备
        // 暂停作业
        scheduler.pauseJob(jobKey);
        scheduler.deleteJob(jobKey);
        //删除数据库老数据
        List<MoldScheduledTask> scheduledTasks = scheduledTaskRepository.findAll();
        if (!scheduledTasks.isEmpty()){
            for (MoldScheduledTask scheduledTask:scheduledTasks){
                if (Objects.equals(scheduledTask.getTaskId(), triggerName)){
                    scheduledTaskRepository.deleteById(triggerName);
                }
            }
        }
        scheduledTaskRepository.deleteById(triggerName);

        // 创建新的JobDataMap，用于存储任务执行所需的数据
       insertJob(jobClass,triggerNames,groupName,cronExpression,startDate,endDate,moldMaintainPlan,jobNames,triggerName,startDateStr,endDateStr);
    }

    public void insertJob(Class<? extends Job> jobClass, String triggerNames,String groupName, String cronExpression, Date startDate, Date endDate,
                              MoldMaintainPlan moldMaintainPlan,String jobNames,String triggerName,String startDateStr, String endDateStr) throws ParseException, SchedulerException {

        // 将 MoldMaintainPlan 对象直接存入 JobDataMap
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("moldMaintainPlan", moldMaintainPlan);
        jobDataMap.put("cronExpression", cronExpression);
        jobDataMap.put("endDateStr", endDateStr);

        // 构建新的任务细节
        JobDetail newjobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobNames,groupName)
                .usingJobData(jobDataMap) // 直接传递 JobDataMap
                .build();

        // 构建新的触发器，指定开始时间、结束时间及CRON表达式
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerNames)
                .startAt(startDate)
                .endAt(endDate)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        // 在调度器中安排新的任务和触发器
        scheduler.scheduleJob(newjobDetail, trigger);

        // 保存任务调度信息到数据库
        MoldScheduledTask scheduledTask = new MoldScheduledTask();
        scheduledTask.setTaskId(triggerName); // or any unique ID
        scheduledTask.setJobClass(jobClass.getName());
        scheduledTask.setJobName(jobNames);
        scheduledTask.setJobGroupName(groupName);
        scheduledTask.setTriggerName(triggerName);
        scheduledTask.setCronExpression(cronExpression);
        scheduledTask.setStartDateStr(startDateStr);
        scheduledTask.setEndDateStr(endDateStr);
        try {
            scheduledTask.setJobDataMapJson(objectMapper.writeValueAsString(jobDataMap));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        scheduledTaskRepository.save(scheduledTask);
    }

    /**
     * 初始化方法，在应用程序启动后调用。
     * 该方法的目的是恢复之前配置的定时任务，这些任务可能由于应用程序重启或其他原因需要重新安排。
     * 它通过查询数据库中的所有定时任务配置，并根据这些配置重新安排相应的任务。
     * 如果任务相关的MoldMaintainPlan（模具维护计划）不存在，则记录警告日志。
     * 如果在恢复任务过程中发生任何其他异常，则抛出运行时异常。
     *
     * @throws RuntimeException 如果恢复任务过程中发生未预期的异常。
     */
    @PostConstruct
    public void initialize() {
        try {
            // 获取当前时间
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 加载所有已配置的定时任务
            List<MoldScheduledTask> scheduledTasks = scheduledTaskRepository.findAll();
            for (MoldScheduledTask scheduledTask : scheduledTasks) {
                // 解析任务的结束时间
                Date endDate = dateFormat.parse(scheduledTask.getEndDateStr());
                // 检查当前时间是否在任务的结束时间之前或等于结束时间
                if (!now.after(endDate)) {
                    // 根据任务类名动态加载任务类
                    Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(scheduledTask.getJobClass());
                    // 从数据库中读取并反序列化任务数据映射
                    JobDataMap jobDataMap = objectMapper.readValue(scheduledTask.getJobDataMapJson(), JobDataMap.class);

                    // 尝试从任务数据映射中获取MoldMaintainPlan对象
                    // 使用ObjectMapper直接反序列化为MoldMaintainPlan对象.
                    MoldMaintainPlan moldMaintainPlan = objectMapper.convertValue(jobDataMap.get("moldMaintainPlan"), MoldMaintainPlan.class);

                    // 如果MoldMaintainPlan存在，则安排任务运行
                    if (moldMaintainPlan != null) {
                        scheduleJobRun(jobClass, scheduledTask.getJobName(), scheduledTask.getTriggerName(),
                                scheduledTask.getStartDateStr(), scheduledTask.getEndDateStr(), scheduledTask.getCronExpression(),
                                moldMaintainPlan);
                    } else {
                        // 如果MoldMaintainPlan不存在，则记录警告日志
                        // 处理 moldMaintainPlan 为 null 的情况
                        log.warn("MoldMaintainPlan is null for task: {}", scheduledTask.getJobName());
                    }
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            // 记录加载任务类或读取任务数据映射时的错误
            log.error("Error initializing scheduled tasks: {}", e.getMessage());
        } catch (Exception e) {
            // 对于其他异常，抛出运行时异常，以便上层处理
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据指定的参数安排作业执行。
     * 该方法用于将一个作业与触发器关联起来，以便按照指定的cron表达式定时执行。
     * <p>
     * 参数说明：
     * jobClass：作业类，实现了Job接口的类，表示作业的具体执行逻辑。
     * jobName：作业的名称，用于唯一标识作业。
     * triggerName：触发器的名称，用于唯一标识触发器。
     * startDateStr：触发器开始生效的日期时间字符串。
     * endDateStr：触发器结束生效的日期时间字符串。
     * cronExpression：cron表达式，用于指定触发器的执行周期。
     * moldMaintainPlan：模具维护计划对象，封装了作业需要的数据，通过JobDataMap传递给作业。
     * <p>
     * 抛出的异常：
     * ParseException：如果日期时间字符串格式不正确。
     * SchedulerException：如果调度器操作失败。
     */
    public void scheduleJobRun(Class<? extends Job> jobClass, String jobName, String triggerName,
                            String startDateStr, String endDateStr, String cronExpression,
                            MoldMaintainPlan moldMaintainPlan)
            throws ParseException, SchedulerException {

        // 格式化日期字符串为Date对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);

        // 创建JobDataMap，用于传递作业执行所需的参数
        // 将 MoldMaintainPlan 对象直接存入 JobDataMap
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("moldMaintainPlan", moldMaintainPlan);
        jobDataMap.put("cronExpression", cronExpression);
        jobDataMap.put("endDateStr", endDateStr);

        // 构建作业细节对象，指定作业类、作业名称和传递给作业的数据
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName)
                .usingJobData(jobDataMap) // 直接传递 JobDataMap
                .build();

        // 构建触发器，指定触发器名称、开始时间、结束时间和cron表达式
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName)
                .startAt(startDate)
                .endAt(endDate)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        // 将作业和触发器注册到调度器中，安排作业的执行
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void destroy() throws Exception {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    /**
     * 暂停任务
     * @param jobName
     * @param groupName
     * @throws SchedulerException
     */
    public void pauseJob(String jobName, String groupName) throws SchedulerException{
        scheduler.pauseJob(JobKey.jobKey(jobName, groupName));
    }

    /**
     * 恢复任务
     * @param jobName
     * @param groupName
     * @throws SchedulerException
     */
    public void resumeJob(String jobName, String groupName) throws SchedulerException{
        scheduler.resumeJob(JobKey.jobKey(jobName, groupName));
    }

    /**
     * 删除任务
     * @param jobName
     * @param groupName
     * @throws SchedulerException
     */
    public void deleteJob(String jobName, String groupName,String taskId) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(jobName, groupName));
        scheduledTaskRepository.deleteById(taskId);
    }
}
