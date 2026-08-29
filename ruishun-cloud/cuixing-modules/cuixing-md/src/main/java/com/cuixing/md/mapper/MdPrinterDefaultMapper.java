

package com.cuixing.md.mapper;

import com.cuixing.md.entity.MdPrinterDefault;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MdPrinterDefaultMapper {
    MdPrinterDefault selectMdPrinterDefaultByPrinterDefaultId(Long var1);

    List<MdPrinterDefault> selectMdPrinterDefaultList(MdPrinterDefault var1);

    int insertMdPrinterDefault(MdPrinterDefault var1);

    int updateMdPrinterDefault(MdPrinterDefault var1);

    int deleteMdPrinterDefaultByPrinterDefaultId(Long var1);

    int deleteMdPrinterDefaultByPrinterDefaultIds(Long[] var1);
}
