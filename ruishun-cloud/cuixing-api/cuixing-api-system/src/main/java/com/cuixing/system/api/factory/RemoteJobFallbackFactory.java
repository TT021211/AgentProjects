package com.cuixing.system.api.factory;


import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.RemoteJobService;
import com.cuixing.system.api.domain.SysJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteJobFallbackFactory implements FallbackFactory<RemoteJobService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteJobFallbackFactory.class);

    @Override
    public RemoteJobService create(Throwable throwable)
    {
        log.error("定时任务服务调用失败:{}", throwable.getMessage());

        return new RemoteJobService()
        {
            @Override
            public R<SysJob> getJob(Long jobId, String source) {
                return R.fail("通过id获取定时任务信息失败：" + throwable.getMessage());
            }

            @Override
            public R<SysJob> getJobByName(String jobName, String source) {
                return R.fail("通过名称获取定时任务信息失败：" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> addJob(SysJob sysJob, String source) {
                return R.fail("新增定时任务失败：" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> editJob(SysJob sysJob, String source) {
                return R.fail("修改定时任务失败：" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> deleteJob(Long[] jobIds, String source) {
                return R.fail("删除定时任务失败：" + throwable.getMessage());
            }
        };
    }
}
