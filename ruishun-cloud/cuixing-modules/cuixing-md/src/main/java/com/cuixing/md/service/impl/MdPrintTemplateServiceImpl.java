package com.cuixing.md.service.impl;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.md.entity.MdPrintTemplate;
import com.cuixing.md.mapper.MdPrintTemplateMapper;
import com.cuixing.md.service.IMdPrintTemplateService;
import com.cuixing.md.service.IMdPrinterDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MdPrintTemplateServiceImpl implements IMdPrintTemplateService {
    @Autowired
    private MdPrintTemplateMapper mdPrintTemplateMapper;
    @Autowired
    private IMdPrinterDefaultService mdPrinterDefaultService;

    public MdPrintTemplateServiceImpl() {
    }

    public MdPrintTemplate selectMdPrintTemplateByTemplateId(Long templateId) {
        return this.mdPrintTemplateMapper.selectMdPrintTemplateByTemplateId(templateId);
    }

    public MdPrintTemplate selectMdPrintTemplateByTemplateCode(MdPrintTemplate mdPrintTemplate) {
        return this.mdPrintTemplateMapper.selectMdPrintTemplateByTemplateCode(mdPrintTemplate);
    }

    public MdPrintTemplate selectMdPrintTemplateByTemplateCode(String code) {
        MdPrintTemplate mdPrintTemplate = new MdPrintTemplate();
        mdPrintTemplate.setTemplateCode(code);
        MdPrintTemplate mdPrintTemplate1 = this.mdPrintTemplateMapper.selectMdPrintTemplateByTemplateCode(mdPrintTemplate);
        return mdPrintTemplate1;
    }

    public List<MdPrintTemplate> selectMdPrintTemplateList(MdPrintTemplate mdPrintTemplate) {
        return this.mdPrintTemplateMapper.selectMdPrintTemplateList(mdPrintTemplate);
    }

    public MdPrintTemplate insertMdPrintTemplate(MdPrintTemplate mdPrintTemplate) {
        if (mdPrintTemplate.getTemplateId()!=null){
            this.mdPrintTemplateMapper.updateMdPrintTemplate(mdPrintTemplate);
            this.mdPrinterDefaultService.saveMyDefaultPrinter(mdPrintTemplate);
        }else{
            mdPrintTemplate.setCreateTime(DateUtils.getNowDate());
            if (!"1".equals(mdPrintTemplate.getStatus())) {
                MdPrintTemplate query = new MdPrintTemplate();
                query.setTemplateCode(mdPrintTemplate.getTemplateCode());
                query.setClientId(mdPrintTemplate.getClientId());
                MdPrintTemplate defaultTemplate = this.selectMdPrintTemplateByTemplateCode(query);
                if (defaultTemplate == null) {
                    mdPrintTemplate.setStatus("1");
                }
            }
            this.mdPrintTemplateMapper.insertMdPrintTemplate(mdPrintTemplate);
            this.mdPrinterDefaultService.saveMyDefaultPrinter(mdPrintTemplate);
        }
        return mdPrintTemplate;
    }

    @Transactional
    public int updateMdPrintTemplate(MdPrintTemplate mdPrintTemplate) {
        mdPrintTemplate.setUpdateTime(DateUtils.getNowDate());
        mdPrintTemplate.setUpdateBy(mdPrintTemplate.getUserNickName());

        try {
            this.mdPrinterDefaultService.saveMyDefaultPrinter(mdPrintTemplate);
        } catch (Exception var3) {
            System.err.println(var3.getMessage());
        }

        return this.mdPrintTemplateMapper.updateMdPrintTemplate(mdPrintTemplate);
    }

    public int copyMdPrintTemplate(MdPrintTemplate mdPrintTemplate) {
        MdPrintTemplate sourceTemplate = this.selectMdPrintTemplateByTemplateId(mdPrintTemplate.getTemplateId());
        MdPrintTemplate newTemplate = new MdPrintTemplate();
        newTemplate.setTemplateCode(sourceTemplate.getTemplateCode());
        newTemplate.setTemplateName(sourceTemplate.getTemplateName() + "-副本");
        newTemplate.setClientId(sourceTemplate.getClientId());
        newTemplate.setClientCode(sourceTemplate.getClientCode());
        newTemplate.setClientName(sourceTemplate.getClientName());
        newTemplate.setContent(sourceTemplate.getContent());
        newTemplate.setStatus("0");
        newTemplate.setCreateBy(mdPrintTemplate.getCreateBy());
        newTemplate.setUserNickName(mdPrintTemplate.getUserNickName());
        newTemplate.setCreateTime(new Date());
        return this.mdPrintTemplateMapper.insertMdPrintTemplate(newTemplate);
    }

    public int deleteMdPrintTemplateByTemplateIds(Long[] templateIds) {
        int rowNum = 0;
        Long[] var3 = templateIds;
        int var4 = templateIds.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Long id = var3[var5];
            MdPrintTemplate mdPrintTemplate = this.selectMdPrintTemplateByTemplateId(id);
            if ("1".equals(mdPrintTemplate.getStatus())) {
                throw new BussinessException("默认模板不允许删除！");
            }

            rowNum += this.deleteMdPrintTemplateByTemplateId(id);
        }

        return rowNum;
    }

    public int deleteMdPrintTemplateByTemplateId(Long templateId) {
        return this.mdPrintTemplateMapper.deleteMdPrintTemplateByTemplateId(templateId);
    }

    @Override
    public MdPrintTemplate queryByCreateBy(String createByNumber) {
        return this.mdPrintTemplateMapper.queryByCreateBy(createByNumber);
    }

    @Transactional
    public void setDefault(MdPrintTemplate mdPrintTemplate) {
        MdPrintTemplate updateOtherEntity = new MdPrintTemplate();
        updateOtherEntity.setStatus("0");
        updateOtherEntity.setTemplateCode(mdPrintTemplate.getTemplateCode());
        updateOtherEntity.setClientId(mdPrintTemplate.getClientId());
        this.mdPrintTemplateMapper.updateStatus(updateOtherEntity);
        if ("1".equals(mdPrintTemplate.getStatus())) {
            MdPrintTemplate updateCurrentEntity = new MdPrintTemplate();
            updateCurrentEntity.setTemplateId(mdPrintTemplate.getTemplateId());
            updateCurrentEntity.setStatus("1");
            this.mdPrintTemplateMapper.updateMdPrintTemplate(updateCurrentEntity);
        }
    }

    public MdPrintTemplate selectMdPrintTemplateByUserId(MdPrintTemplate mdPrintTemplate) {
        return this.mdPrintTemplateMapper.selectMdPrintTemplateByUserId(mdPrintTemplate);
    }
}
