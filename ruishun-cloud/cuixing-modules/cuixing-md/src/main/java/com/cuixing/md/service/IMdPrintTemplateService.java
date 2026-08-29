

package com.cuixing.md.service;

import com.cuixing.md.entity.MdPrintTemplate;

import java.util.List;

public interface IMdPrintTemplateService {
    MdPrintTemplate selectMdPrintTemplateByTemplateId(Long var1);

    MdPrintTemplate selectMdPrintTemplateByTemplateCode(MdPrintTemplate var1);

    MdPrintTemplate selectMdPrintTemplateByUserId(MdPrintTemplate var1);

    MdPrintTemplate selectMdPrintTemplateByTemplateCode(String var1);

    List<MdPrintTemplate> selectMdPrintTemplateList(MdPrintTemplate var1);

    MdPrintTemplate insertMdPrintTemplate(MdPrintTemplate var1);

    int updateMdPrintTemplate(MdPrintTemplate var1);

    int copyMdPrintTemplate(MdPrintTemplate var1);

    void setDefault(MdPrintTemplate var1);

    int deleteMdPrintTemplateByTemplateIds(Long[] var1);

    int deleteMdPrintTemplateByTemplateId(Long var1);

    MdPrintTemplate queryByCreateBy(String createByNumber);
}
