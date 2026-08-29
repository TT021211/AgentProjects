package com.cuixing.system.api.factory;


import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.RemoteMoldService;
import com.cuixing.system.api.domain.mold.MoldGauge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteMoldFallbackFactory implements FallbackFactory<RemoteMoldService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteMoldFallbackFactory.class);


    @Override
    public RemoteMoldService create(Throwable throwable)
    {
        log.error("WMS服务调用失败:{}", throwable.getMessage());

        return new RemoteMoldService(){
            @Override
            public R<AjaxResult> addMoldGauge(MoldGauge moldGauge, String source) {
                return R.fail(AjaxResult.error("调用MOLD新增换规服务失败"));
            }
        };
    }
}
