

package com.cuixing.md.service.impl;

import com.cuixing.md.entity.MdPrintTemplate;
import com.cuixing.md.entity.MdPrinterDefault;
import com.cuixing.md.mapper.MdPrinterDefaultMapper;
import com.cuixing.md.service.IMdPrinterDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MdPrinterDefaultServiceImpl implements IMdPrinterDefaultService {
    @Autowired
    private MdPrinterDefaultMapper mdPrinterDefaultMapper;

    public MdPrinterDefaultServiceImpl() {
    }

    public MdPrinterDefault selectMdPrinterDefaultByPrinterDefaultId(Long printerDefaultId) {
        return this.mdPrinterDefaultMapper.selectMdPrinterDefaultByPrinterDefaultId(printerDefaultId);
    }

    public List<MdPrinterDefault> selectMdPrinterDefaultList(MdPrinterDefault mdPrinterDefault) {
        return this.mdPrinterDefaultMapper.selectMdPrinterDefaultList(mdPrinterDefault);
    }

    public int insertMdPrinterDefault(MdPrinterDefault mdPrinterDefault) {
        return this.mdPrinterDefaultMapper.insertMdPrinterDefault(mdPrinterDefault);
    }

    public int updateMdPrinterDefault(MdPrinterDefault mdPrinterDefault) {
        return this.mdPrinterDefaultMapper.updateMdPrinterDefault(mdPrinterDefault);
    }

    public int deleteMdPrinterDefaultByPrinterDefaultIds(Long[] printerDefaultIds) {
        return this.mdPrinterDefaultMapper.deleteMdPrinterDefaultByPrinterDefaultIds(printerDefaultIds);
    }

    public int deleteMdPrinterDefaultByPrinterDefaultId(Long printerDefaultId) {
        return this.mdPrinterDefaultMapper.deleteMdPrinterDefaultByPrinterDefaultId(printerDefaultId);
    }

    public void saveMyDefaultPrinter(MdPrintTemplate mdPrintTemplate) {
        MdPrinterDefault query = new MdPrinterDefault();
        query.setTemplateId(mdPrintTemplate.getTemplateId());
        query.setUserId(mdPrintTemplate.getUserId());
        List<MdPrinterDefault> defaultPrinterList = this.selectMdPrinterDefaultList(query);
        MdPrinterDefault entity;
        if (defaultPrinterList != null && !defaultPrinterList.isEmpty()) {
            entity = (MdPrinterDefault)defaultPrinterList.iterator().next();
            if (mdPrintTemplate.getDefaultPrinterName() != null) {
                entity.setPrinterName(mdPrintTemplate.getDefaultPrinterName());
                this.mdPrinterDefaultMapper.updateMdPrinterDefault(entity);
            }
        } else {
            entity = new MdPrinterDefault();
            entity.setTemplateId(mdPrintTemplate.getTemplateId());
            entity.setUserId(mdPrintTemplate.getUserId());
            entity.setPrinterName(mdPrintTemplate.getDefaultPrinterName());
            this.mdPrinterDefaultMapper.insertMdPrinterDefault(entity);
        }

    }
}
