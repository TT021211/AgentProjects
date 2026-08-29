package com.cuixing.system.api;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.system.api.domain.mm.MmMould;
import com.cuixing.system.api.factory.RemoteMdFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 主数据服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteMmService", value = ServiceNameConstants.MM_SERVICE,fallbackFactory = RemoteMdFallbackFactory.class)
public interface RemoteMmService {
    /**
     * 通过id查询模具信息
     *
     * @param id 模具id
     * @return 结果
     */
    @GetMapping("/mould/{id}")
    public R<MmMould> getMmMould(@PathVariable("id") Integer id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
