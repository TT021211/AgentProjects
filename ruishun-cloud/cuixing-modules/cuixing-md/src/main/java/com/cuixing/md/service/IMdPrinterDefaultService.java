

package com.cuixing.md.service;

import com.cuixing.md.entity.MdPrintTemplate;
import com.cuixing.md.entity.MdPrinterDefault;

import java.util.List;

public interface IMdPrinterDefaultService {
    MdPrinterDefault selectMdPrinterDefaultByPrinterDefaultId(Long var1);

    List<MdPrinterDefault> selectMdPrinterDefaultList(MdPrinterDefault var1);

    int insertMdPrinterDefault(MdPrinterDefault var1);

    int updateMdPrinterDefault(MdPrinterDefault var1);

    int deleteMdPrinterDefaultByPrinterDefaultIds(Long[] var1);

    int deleteMdPrinterDefaultByPrinterDefaultId(Long var1);

    void saveMyDefaultPrinter(MdPrintTemplate var1);
}
