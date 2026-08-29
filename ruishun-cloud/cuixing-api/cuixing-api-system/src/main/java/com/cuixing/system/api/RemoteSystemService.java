package com.cuixing.system.api;

import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.system.api.factory.RemoteSystemFallbackFactory;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteSystemService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteSystemFallbackFactory.class)
public interface RemoteSystemService {

    @ApiOperation("获取一个自动生成的编码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleCode", value = "规则编码", required = true, dataType = "String", paramType = "path", dataTypeClass = String.class),
            @ApiImplicitParam(name = "inputCharacter", value = "输入字符", required = false,dataType = "String", paramType = "path", dataTypeClass = String.class)
    })
    @GetMapping("/system/autocode/get/{ruleCode}/{inputCharacter}")
    public String getAutoCode(@PathVariable("ruleCode") String ruleCode, @PathVariable("inputCharacter") String inputCharacter);


}
