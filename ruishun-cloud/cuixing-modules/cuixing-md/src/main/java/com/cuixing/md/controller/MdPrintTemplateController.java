package com.cuixing.md.controller;


import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.md.entity.MdPrintTemplate;
import com.cuixing.md.entity.MdPrinterDefault;
import com.cuixing.md.service.IMdPrintTemplateService;
import com.cuixing.md.service.IMdPrinterDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping({"/printTemplate"})
public class MdPrintTemplateController extends BaseController {
    @Autowired
    private IMdPrintTemplateService mdPrintTemplateService;
    @Autowired
    private IMdPrinterDefaultService mdPrinterDefaultService;

    public MdPrintTemplateController() {
    }

    @GetMapping({"/list"})
    public TableDataInfo list(MdPrintTemplate mdPrintTemplate) {
        this.startPage();
        /*mdPrintTemplate.setCompanyId(SecurityUtils.getCompanyId());*/
        List<MdPrintTemplate> list = this.mdPrintTemplateService.selectMdPrintTemplateList(mdPrintTemplate);
        for (MdPrintTemplate md:list){
            System.out.println("content------------------"+md.getContent());
        }
        return this.getDataTable(list);
    }

    @PostMapping("/queryByCreateBy")
    public AjaxResult queryByCreateBy(@RequestBody String createByNumber){
        return AjaxResult.success(this.mdPrintTemplateService.queryByCreateBy(createByNumber));
    }
    @Log(
            title = "企业标签模板",
            businessType = BusinessType.EXPORT
    )
    @PostMapping({"/export"})
    public void export(HttpServletResponse response, MdPrintTemplate mdPrintTemplate) {
        //mdPrintTemplate.setCompanyId(SecurityUtils.getCompanyId());
        List<MdPrintTemplate> list = this.mdPrintTemplateService.selectMdPrintTemplateList(mdPrintTemplate);
        ExcelUtil<MdPrintTemplate> util = new ExcelUtil(MdPrintTemplate.class);
        util.exportExcel(response, list, "企业标签模板数据");
    }

    @GetMapping({"/{templateId}"})
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId) {
        MdPrintTemplate mdPrintTemplate = this.mdPrintTemplateService.selectMdPrintTemplateByTemplateId(templateId);
        MdPrinterDefault query = new MdPrinterDefault();
        query.setTemplateId(mdPrintTemplate.getTemplateId());
        /*query.setUserId(SecurityUtils.getUserId());*/
        List<MdPrinterDefault> defaultPrinterList = this.mdPrinterDefaultService.selectMdPrinterDefaultList(query);
        if (defaultPrinterList != null && !defaultPrinterList.isEmpty()) {
            mdPrintTemplate.setDefaultPrinterName(((MdPrinterDefault)defaultPrinterList.iterator().next()).getPrinterName());
        }

        return AjaxResult.success(mdPrintTemplate);
    }

    @GetMapping({"/getDefaultTemplateByCode/{templateCode}"})
    public AjaxResult getDefaultTemplateByCode(@PathVariable("templateCode") String templateCode) {
        MdPrintTemplate mdPrintTemplate = this.mdPrintTemplateService.selectMdPrintTemplateByTemplateCode(templateCode);
        if (mdPrintTemplate == null) {
            return AjaxResult.success(mdPrintTemplate);
        } else {
            MdPrinterDefault query = new MdPrinterDefault();
            query.setTemplateId(mdPrintTemplate.getTemplateId());
            query.setUserId(SecurityUtils.getUserId());
            List<MdPrinterDefault> defaultPrinterList = this.mdPrinterDefaultService.selectMdPrinterDefaultList(query);
            if (defaultPrinterList != null && !defaultPrinterList.isEmpty()) {
                mdPrintTemplate.setDefaultPrinterName(((MdPrinterDefault)defaultPrinterList.iterator().next()).getPrinterName());
            }

            return AjaxResult.success(mdPrintTemplate);
        }
    }

    @GetMapping({"/getDefaultTemplateByCodeAndClient"})
    public AjaxResult getDefaultTemplateByCodeAndClient(MdPrintTemplate mdPrintTemplate) {
        MdPrintTemplate entity = null;
        MdPrintTemplate query1 = new MdPrintTemplate();
        query1.setTemplateCode(mdPrintTemplate.getTemplateCode());
        //query1.setUserId(this.getUserId());
        MdPrintTemplate userEntity = this.mdPrintTemplateService.selectMdPrintTemplateByUserId(query1);
        if (userEntity != null) {
            entity = userEntity;
        } else {
            entity = this.mdPrintTemplateService.selectMdPrintTemplateByTemplateCode(mdPrintTemplate);
            if (entity == null && mdPrintTemplate.getClientId() != null) {
                mdPrintTemplate.setClientId((Long)null);
                entity = this.mdPrintTemplateService.selectMdPrintTemplateByTemplateCode(mdPrintTemplate);
            }
        }

        if (entity == null) {
            return AjaxResult.success(entity);
        } else {
            MdPrinterDefault query = new MdPrinterDefault();
            query.setTemplateId(entity.getTemplateId());
            query.setUserId(SecurityUtils.getUserId());
            List<MdPrinterDefault> defaultPrinterList = this.mdPrinterDefaultService.selectMdPrinterDefaultList(query);
            if (defaultPrinterList != null && !defaultPrinterList.isEmpty()) {
                entity.setDefaultPrinterName(((MdPrinterDefault)defaultPrinterList.iterator().next()).getPrinterName());
            }

            return AjaxResult.success(entity);
        }
    }

    @Log(
            title = "企业标签模板",
            businessType = BusinessType.INSERT
    )
    @PostMapping
    public AjaxResult add(@RequestBody MdPrintTemplate mdPrintTemplate) {
        return AjaxResult.success(this.mdPrintTemplateService.insertMdPrintTemplate(mdPrintTemplate));
    }

    @Log(
            title = "复制企业标签模板",
            businessType = BusinessType.UPDATE
    )
    @PostMapping({"/copy"})
    public AjaxResult copy(@RequestBody MdPrintTemplate mdPrintTemplate) {
        return this.toAjax(this.mdPrintTemplateService.copyMdPrintTemplate(mdPrintTemplate));
    }

    @Log(
            title = "企业标签模板",
            businessType = BusinessType.UPDATE
    )
    @PutMapping
    public AjaxResult edit(@RequestBody MdPrintTemplate mdPrintTemplate) {
        return this.toAjax(this.mdPrintTemplateService.updateMdPrintTemplate(mdPrintTemplate));
    }

    @Log(
            title = "设置企业标签模板为默认状态",
            businessType = BusinessType.UPDATE
    )
    @PutMapping({"/setDefault"})
    public AjaxResult setDefault(@RequestBody MdPrintTemplate mdPrintTemplate) {
        this.mdPrintTemplateService.setDefault(mdPrintTemplate);
        return AjaxResult.success();
    }

    @Log(
            title = "企业标签模板",
            businessType = BusinessType.DELETE
    )
    @DeleteMapping({"/{templateIds}"})
    public AjaxResult remove(@PathVariable Long[] templateIds) {
        return this.toAjax(this.mdPrintTemplateService.deleteMdPrintTemplateByTemplateIds(templateIds));
    }
}
