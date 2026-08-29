package com.cuixing.ecm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;

/**
 * 能耗定时任务执行日志对象 ecm_scheduled_task_log
 * 
 * @author zhan
 * @date 2024-07-25
 */
@ApiModel( description = "能耗定时任务执行日志")
public class EcmScheduledTaskLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 定时日志id */
    @ApiModelProperty("定时日志id")
    private Long id;

    /** 定时任务名 */
    @ApiModelProperty("定时任务名")
    @Excel(name = "定时任务名")
    private String taskName;

    /** 最后执行时间 */
    @ApiModelProperty("最后执行时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastExecutionTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setLastExecutionTime(Date lastExecutionTime) 
    {
        this.lastExecutionTime = lastExecutionTime;
    }

    public Date getLastExecutionTime() 
    {
        return lastExecutionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskName", getTaskName())
            .append("lastExecutionTime", getLastExecutionTime())
            .toString();
    }
}
