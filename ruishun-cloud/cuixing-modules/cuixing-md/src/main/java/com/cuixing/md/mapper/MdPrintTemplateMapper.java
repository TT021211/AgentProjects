

package com.cuixing.md.mapper;

import com.cuixing.md.entity.MdPrintTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MdPrintTemplateMapper {
    MdPrintTemplate selectMdPrintTemplateByTemplateId(Long var1);

    MdPrintTemplate selectMdPrintTemplateByTemplateCode(MdPrintTemplate var1);

    MdPrintTemplate selectMdPrintTemplateByUserId(MdPrintTemplate var1);

    List<MdPrintTemplate> selectMdPrintTemplateList(MdPrintTemplate var1);

    int insertMdPrintTemplate(MdPrintTemplate var1);

    int updateMdPrintTemplate(MdPrintTemplate var1);

    int updateStatus(MdPrintTemplate var1);

    int deleteMdPrintTemplateByTemplateId(Long var1);

    int deleteMdPrintTemplateByTemplateIds(Long[] var1);

    MdPrintTemplate queryByCreateBy(@Param("createByNumber") String createByNumber);
}
