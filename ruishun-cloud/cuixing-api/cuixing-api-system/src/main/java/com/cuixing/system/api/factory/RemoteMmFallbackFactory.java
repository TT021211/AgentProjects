package com.cuixing.system.api.factory;

import com.cuixing.common.core.domain.R;
import com.cuixing.system.api.RemoteMmService;
import com.cuixing.system.api.domain.mm.MmMould;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

public class RemoteMmFallbackFactory implements FallbackFactory<RemoteMmService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteMdFallbackFactory.class);

    @Override
    public RemoteMmService create(Throwable throwable)
    {
        log.error("模具服务调用失败:{}", throwable.getMessage());
        return new RemoteMmService()
        {
            @Override
            public R<MmMould> getMmMould(Integer id, String source) {
                return R.fail("获取模具信息失败:" + throwable.getMessage());
            }
        };
    }
}
