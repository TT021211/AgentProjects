package com.cuixing.system.api;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.domain.mold.MoldGauge;
import com.cuixing.system.api.factory.RemoteMoldFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 模具服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteMoldService",
        value = ServiceNameConstants.MOLD_SERVICE,
        fallbackFactory = RemoteMoldFallbackFactory.class)
public interface RemoteMoldService {
    /**
     * 新增换规单接口
     * @param moldGauge 模具规格信息
     * @param source 来源
     * @return 结果
     */
    @PostMapping("/mold/moldGauge/add")
    public R<AjaxResult> addMoldGauge(@RequestBody MoldGauge moldGauge, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
