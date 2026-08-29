package com.cuixing.md.controller;

import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.md.entity.MdPrinterDefault;
import com.cuixing.md.service.IMdPrinterDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping({"/printerDefault"})
public class MdPrinterDefaultController extends BaseController {
    @Autowired
    private IMdPrinterDefaultService mdPrinterDefaultService;

    public MdPrinterDefaultController() {
    }

    @GetMapping({"/list"})
    public TableDataInfo list(MdPrinterDefault mdPrinterDefault) {
        this.startPage();
        List<MdPrinterDefault> list = this.mdPrinterDefaultService.selectMdPrinterDefaultList(mdPrinterDefault);
        return this.getDataTable(list);
    }

    @Log(
            title = "默认打印机配置",
            businessType = BusinessType.EXPORT
    )
    @PostMapping({"/export"})
    public void export(HttpServletResponse response, MdPrinterDefault mdPrinterDefault) {
        List<MdPrinterDefault> list = this.mdPrinterDefaultService.selectMdPrinterDefaultList(mdPrinterDefault);
        ExcelUtil<MdPrinterDefault> util = new ExcelUtil(MdPrinterDefault.class);
        util.exportExcel(response, list, "默认打印机配置数据");
    }

    @GetMapping({"/{printerDefaultId}"})
    public AjaxResult getInfo(@PathVariable("printerDefaultId") Long printerDefaultId) {
        return AjaxResult.success(this.mdPrinterDefaultService.selectMdPrinterDefaultByPrinterDefaultId(printerDefaultId));
    }

    @Log(
            title = "默认打印机配置",
            businessType = BusinessType.INSERT
    )
    @PostMapping
    public AjaxResult add(@RequestBody MdPrinterDefault mdPrinterDefault) {
        return this.toAjax(this.mdPrinterDefaultService.insertMdPrinterDefault(mdPrinterDefault));
    }

    @Log(
            title = "默认打印机配置",
            businessType = BusinessType.UPDATE
    )
    @PutMapping
    public AjaxResult edit(@RequestBody MdPrinterDefault mdPrinterDefault) {
        return this.toAjax(this.mdPrinterDefaultService.updateMdPrinterDefault(mdPrinterDefault));
    }

    @Log(
            title = "默认打印机配置",
            businessType = BusinessType.DELETE
    )
    @DeleteMapping({"/{printerDefaultIds}"})
    public AjaxResult remove(@PathVariable Long[] printerDefaultIds) {
        return this.toAjax(this.mdPrinterDefaultService.deleteMdPrinterDefaultByPrinterDefaultIds(printerDefaultIds));
    }
}
