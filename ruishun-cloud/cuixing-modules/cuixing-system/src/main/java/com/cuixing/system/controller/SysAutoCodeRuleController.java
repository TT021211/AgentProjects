package com.cuixing.system.controller;


import com.cuixing.common.core.constant.UserConstants;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.system.api.domain.SysAutoCodeRule;
import com.cuixing.system.service.IAutoCodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/autocode/rule")
public class SysAutoCodeRuleController extends BaseController {

    @Autowired
    private IAutoCodeRuleService iAutoCodeRuleService;

    //@RequiresPermissions({"system:autocode:rule:list"})
    @GetMapping("/list")
    public TableDataInfo list(SysAutoCodeRule sysAutoCodeRule){
        startPage();
        List<SysAutoCodeRule> rules = iAutoCodeRuleService.selectAutoCodeList(sysAutoCodeRule);
        return getDataTable(rules);
    }

    //@RequiresPermissions({"system:autocode:rule:query"})
    @GetMapping("/{ruleId}")
    public AjaxResult getInfo(@PathVariable Long ruleId){
        return AjaxResult.success(iAutoCodeRuleService.findById(ruleId));
    }

    //@RequiresPermissions({"system:autocode:rule:add"})
    @Log(title = "新增自动编码规则",businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysAutoCodeRule sysAutoCodeRule){
        if(UserConstants.NOT_UNIQUE_STR.equals(iAutoCodeRuleService.checkRuleCodeUnique(sysAutoCodeRule))){
            return AjaxResult.error("自动编码规则的编号重复");
        }
        if(UserConstants.NOT_UNIQUE_STR.equals(iAutoCodeRuleService.checkRuleNameUnique(sysAutoCodeRule))){
            return AjaxResult.error("自动编码规则的名称重复");
        }
        if("N".equals(sysAutoCodeRule.getIsPadded())){
            sysAutoCodeRule.setPaddedChar(null);
            sysAutoCodeRule.setPaddedMethod(null);
        }
        sysAutoCodeRule.setCreateBy(SecurityUtils.getUsername());
        return toAjax(iAutoCodeRuleService.insertInfo(sysAutoCodeRule));
    }

    //@RequiresPermissions({"system:autocode:rule:edit"})
    @Log(title = "更新自动编码规则",businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateInfo(@Validated @RequestBody SysAutoCodeRule sysAutoCodeRule){
        if(UserConstants.NOT_UNIQUE_STR.equals(iAutoCodeRuleService.checkRuleCodeUnique(sysAutoCodeRule))){
            return AjaxResult.error("自动编码规则的编号重复");
        }
        if(UserConstants.NOT_UNIQUE_STR.equals(iAutoCodeRuleService.checkRuleNameUnique(sysAutoCodeRule))){
            return AjaxResult.error("自动编码规则的名称重复");
        }
        sysAutoCodeRule.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(iAutoCodeRuleService.updateInfo(sysAutoCodeRule));
    }

    //@RequiresPermissions({"system:autocode:rule:remove"})
    @Log(title = "删除自动编码规则",businessType = BusinessType.DELETE)
    @DeleteMapping("/{ruleIds}")
    public AjaxResult remove(@PathVariable Long[] ruleIds){

        return toAjax(iAutoCodeRuleService.deleteByIds(ruleIds));
    }

}
