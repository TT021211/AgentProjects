package com.cuixing.mold.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (MoldScheduledTask)实体类
 *
 * @author makejava
 * @since 2024-07-10 15:38:29
 */
@Entity
@Table(name = "mold_scheduled_task")
public class MoldScheduledTask implements Serializable {

    @Id
    @Column(name = "task_id", nullable = false, unique = true)
    private String taskId;

    @Column(name = "job_class", nullable = false)
    private String jobClass;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "trigger_name", nullable = false)
    private String triggerName;

    @Column(name = "cron_expression", nullable = false)
    private String cronExpression;

    @Column(name = "start_date_str", nullable = false)
    private String startDateStr;

    @Column(name = "end_date_str", nullable = false)
    private String endDateStr;

    @Column(name = "job_data_map_json", nullable = false)
    private String jobDataMapJson;
    @Column(name = "job_group_name", nullable = false)
    private String jobGroupName;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getJobDataMapJson() {
        return jobDataMapJson;
    }

    public void setJobDataMapJson(String jobDataMapJson) {
        this.jobDataMapJson = jobDataMapJson;
    }


    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }
}

