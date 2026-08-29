package com.cuixing.system.api;

import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.system.api.domain.md.MdItem;
import com.cuixing.system.api.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Md服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteMdItemService", value = ServiceNameConstants.MD_SERVICE,fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteMdItemService
{

    //    @GetMapping(value = "/mes/md/mditem/{itemId}")
//    public AjaxResult getInfo(@PathVariable Long itemId);

    @GetMapping("/mes/md/mditem/list")
    public TableDataInfo list(@RequestParam("mditem") MdItem mdItem);

}
