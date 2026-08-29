package com.cuixing.system.controller;


import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.domain.SysAppVersion;
import com.cuixing.system.service.ISysAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appVersion")
public class SysAppVersionController {

    @Autowired
    private ISysAppVersionService sysAppVersionService;

    @PostMapping("/getAppVersion")
    public AjaxResult getAppVersion(){
        return AjaxResult.success(sysAppVersionService.getAppVersion());
    }
}
