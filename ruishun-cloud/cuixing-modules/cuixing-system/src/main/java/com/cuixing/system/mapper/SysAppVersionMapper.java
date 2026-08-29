package com.cuixing.system.mapper;

import com.cuixing.system.api.domain.SysAutoCodePart;
import com.cuixing.system.domain.SysAppVersion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysAppVersionMapper {

    public SysAppVersion getSysAppVersion();
}