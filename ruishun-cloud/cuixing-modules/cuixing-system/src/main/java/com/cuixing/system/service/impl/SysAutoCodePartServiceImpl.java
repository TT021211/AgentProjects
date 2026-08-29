package com.cuixing.system.service.impl;

import com.cuixing.common.core.constant.UserConstants;
import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.system.api.domain.SysAutoCodePart;
import com.cuixing.system.mapper.SysAutoCodePartMapper;
import com.cuixing.system.service.IAutoCodePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAutoCodePartServiceImpl implements IAutoCodePartService {

    @Autowired
    private SysAutoCodePartMapper sysAutoCodePartMapper;

    @Override
    public List<SysAutoCodePart> listPart(SysAutoCodePart sysAutoCodePart) {
        return sysAutoCodePartMapper.selectSysAutoCodePartList(sysAutoCodePart);
    }

    @Override
    public SysAutoCodePart findById(Long partId) {
        return sysAutoCodePartMapper.findById(partId);
    }

    @Override
    public String checkPartUnique(SysAutoCodePart sysAutoCodePart) {
        SysAutoCodePart part = sysAutoCodePartMapper.checkPartUnique(sysAutoCodePart);
        Long partId = StringUtils.isNull(sysAutoCodePart.getPartId())?-1L:sysAutoCodePart.getPartId();
        if(StringUtils.isNotNull(part) && partId.longValue() != part.getPartId().longValue()){
            return UserConstants.NOT_UNIQUE_STR;
        }
        return UserConstants.UNIQUE_STR;
    }

    @Override
    public int insertPart(SysAutoCodePart sysAutoCodePart) {
        return sysAutoCodePartMapper.add(sysAutoCodePart);
    }

    @Override
    public int updatePart(SysAutoCodePart sysAutoCodePart) {
        return sysAutoCodePartMapper.updateSysAutoCodePart(sysAutoCodePart);
    }

    @Override
    public int deleteByIds(Long[] partIds) {
        for (Long partId:partIds
             ) {
            sysAutoCodePartMapper.deleteById(partId);
        }
        return 1;
    }


}
