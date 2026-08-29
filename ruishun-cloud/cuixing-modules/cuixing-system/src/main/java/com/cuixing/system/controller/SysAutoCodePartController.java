package com.cuixing.system.controller;

import com.cuixing.common.core.constant.UserConstants;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.system.api.domain.SysAutoCodePart;
import com.cuixing.system.service.IAutoCodePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/autocode/part")
public class SysAutoCodePartController extends BaseController {

    @Autowired
    private IAutoCodePartService iAutoCodePartService;

    //@RequiresPermissions({"system:autocode:part:list"})
    @GetMapping("/list")
    public TableDataInfo list(SysAutoCodePart sysAutoCodePart){
        startPage();
        List<SysAutoCodePart> parts = iAutoCodePartService.listPart(sysAutoCodePart);
        return getDataTable(parts);
    }

    //@RequiresPermissions({"system:autocode:part:query"})
    @GetMapping("/{partId}")
    public AjaxResult getInfo(@PathVariable Long partId){
        return AjaxResult.success(iAutoCodePartService.findById(partId));
    }


    //@RequiresPermissions({"system:autocode:part:insert"})
    @Log(title = "新增编码生产规则组成部分",businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysAutoCodePart part){
        if(UserConstants.NOT_UNIQUE_STR.equals(iAutoCodePartService.checkPartUnique(part))){
            return AjaxResult.error("规则组成不唯一，请检查组成编码、组成名称、组成序号");
        }
        part.setCreateBy(SecurityUtils.getUsername());
        return toAjax(iAutoCodePartService.insertPart(part));
    }

    //@RequiresPermissions({"system:autocode:part:update"})
    @Log(title = "更新物料编码",businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@Validated @RequestBody SysAutoCodePart sysAutoCodePart){
        if(UserConstants.NOT_UNIQUE_STR.equals(iAutoCodePartService.checkPartUnique(sysAutoCodePart))){
            return AjaxResult.error("规则组成不唯一，请检查组成编码、组成名称、组成序号");
        }
        sysAutoCodePart.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(iAutoCodePartService.updatePart(sysAutoCodePart));
    }


    //@RequiresPermissions({"system:autocode:part:remove"})
    @Log(title = "删除物料编码",businessType = BusinessType.DELETE)
    @DeleteMapping("/{partIds}")
    public AjaxResult delete(@PathVariable Long[] partIds){
        return toAjax(iAutoCodePartService.deleteByIds(partIds));
    }

}
