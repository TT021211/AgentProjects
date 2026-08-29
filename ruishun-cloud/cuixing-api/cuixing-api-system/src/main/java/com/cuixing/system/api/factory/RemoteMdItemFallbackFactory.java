package com.cuixing.system.api.factory;

import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.system.api.RemoteMdItemService;
import com.cuixing.system.api.domain.md.MdItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 文件服务降级处理
 * 
 * @author cuixing
 */
@Component
public class RemoteMdItemFallbackFactory implements FallbackFactory<RemoteMdItemService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteMdItemFallbackFactory.class);

    @Override
    public RemoteMdItemService create(Throwable throwable)
    {
        log.error("Md服务调用失败:{}", throwable.getMessage());
        return new RemoteMdItemService()
        {
            @Override
            public TableDataInfo list(MdItem mdItem) {
                TableDataInfo tableDataInfo = new TableDataInfo();
                tableDataInfo.setMsg("失败");
                return tableDataInfo;
            }

        };
    }
}
