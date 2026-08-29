package com.cuixing.system.strategy;


import com.cuixing.system.api.domain.SysAutoCodePart;

public interface PartTypeTemplate {
    /**
     * 分段的处理规则
     * @param sysAutoCodePart
     * @return
     */
    String partHandle(SysAutoCodePart sysAutoCodePart);
}
