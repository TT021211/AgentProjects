package com.cuixing.system.api;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.domain.SysJob;
import com.cuixing.system.api.factory.RemoteJobFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * 定时任务服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteJobService", value = ServiceNameConstants.JOB_SERVICE,fallbackFactory = RemoteJobFallbackFactory.class)
public interface RemoteJobService {

    //获取定时任务信息
    @GetMapping("/job/{jobId}")
    R<SysJob> getJob(@PathVariable("jobId") Long jobId, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //获取定时任务信息
    @GetMapping("/job/getByName/{jobName}")
    R<SysJob> getJobByName(@PathVariable("jobName") String jobName, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //新增定时任务
    @PostMapping("/job")
    R<AjaxResult> addJob(@RequestBody SysJob sysJob, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //修改定时任务
    @PutMapping("/job")
    R<AjaxResult> editJob(@RequestBody SysJob sysJob, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //删除定时任务
    @DeleteMapping("/job/{jobIds}")
    R<AjaxResult> deleteJob(@PathVariable("jobIds") Long[] jobIds, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);
}
