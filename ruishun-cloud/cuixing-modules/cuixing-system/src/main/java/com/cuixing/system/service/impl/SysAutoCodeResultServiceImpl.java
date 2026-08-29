package com.cuixing.system.service.impl;

import com.cuixing.system.api.domain.SysAutoCodeResult;
import com.cuixing.system.mapper.SysAutoCodeResultMapper;
import com.cuixing.system.service.IAutoCodeResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAutoCodeResultServiceImpl implements IAutoCodeResultService {

    @Autowired
    private SysAutoCodeResultMapper sysAutoCodeResultMapper;

    @Override
    public List<SysAutoCodeResult> list(SysAutoCodeResult sysAutoCodeResult) {
        return sysAutoCodeResultMapper.selectSysAutoCodeResultList(sysAutoCodeResult);
    }

    @Override
    public void saveAutoCodeResult(SysAutoCodeResult sysAutoCodeResult) {
        sysAutoCodeResultMapper.add(sysAutoCodeResult);
    }

    @Override
    public void updateAutoCodeResult(SysAutoCodeResult sysAutoCodeResult) {
        sysAutoCodeResultMapper.updateAutoCodeResult(sysAutoCodeResult);
    }
}
