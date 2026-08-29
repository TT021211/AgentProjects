package com.cuixing.system.service.impl;

import com.cuixing.system.domain.SysAppVersion;
import com.cuixing.system.mapper.SysAppVersionMapper;
import com.cuixing.system.mapper.SysAutoCodeResultMapper;
import com.cuixing.system.service.ISysAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAppVersionServiceImpl implements ISysAppVersionService {

    @Autowired
    private SysAppVersionMapper sysAppVersionMapper;

    @Override
    public SysAppVersion getAppVersion() {
        return sysAppVersionMapper.getSysAppVersion();
    }
}
