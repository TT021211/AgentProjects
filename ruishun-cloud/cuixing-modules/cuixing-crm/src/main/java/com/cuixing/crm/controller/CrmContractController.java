package com.cuixing.crm.controller;

import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.crm.entity.CrmContract;
import com.cuixing.crm.service.ICrmContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 销售合同Controller
 *
 * @author zhan
 * @date 2024-04-17
 */
@RestController
@RequestMapping("/Contract")
@Api(tags = "销售合同主表对象接口")
public class CrmContractController extends BaseController
{
    @Autowired
    private ICrmContractService crmContractService;

    /**
     * 查询销售合同列表
     */
    //@RequiresPermissions("crm:Contract:list")
    @GetMapping("/list")
    @ApiOperation(value = "销售合同列表", notes = "权限字符:crm:Contract:list")
    public TableDataInfo list(CrmContract crmContract)
    {
        startPage();
        List<CrmContract> list = crmContractService.selectCrmContractList(crmContract);
        return getDataTable(list);
    }



    /**
     * 导出销售合同列表
     */
    //@RequiresPermissions("crm:Contract:export")
    @Log(title = "销售合同", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "销售合同导出", notes = "权限字符:crm:Contract:export")
    public void export(HttpServletResponse response, CrmContract crmContract)
    {
        List<CrmContract> list = crmContractService.selectCrmContractList(crmContract);
        ExcelUtil<CrmContract> util = new ExcelUtil<CrmContract>(CrmContract.class);
        util.exportExcel(response, list, "销售合同数据");
    }

    /**
     * 获取销售合同主表详细信息
     */
    //@RequiresPermissions("crm:Contract:query")
    @GetMapping(value = "/{contractId}")
    @ApiOperation(value = "销售合同详细信息",notes = "权限字符:crm:contract:query")
    public AjaxResult getInfo(@PathVariable("contractId") Long contractId)
    {
        return success(crmContractService.selectCrmContractByContractId(contractId))  ;
    }


    /**
     * 新增销售合同
     */
    //@RequiresPermissions("crm:Contract:add")
    @Log(title = "销售合同", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmContract crmContract)
    {
        return toAjax(crmContractService.insertCrmContract(crmContract));
    }

    /**
     * 修改销售合同
     */
    //@RequiresPermissions("crm:Contract:edit")
    @Log(title = "销售合同", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "销售合同修改", notes = "权限字符:crm:Contract:edit")
    public AjaxResult edit(@RequestBody CrmContract crmContract)
    {
        return toAjax(crmContractService.updateCrmContract(crmContract));
    }

    /**
     * 删除销售合同
     */
    //@RequiresPermissions("crm:Contract:remove")
    @Log(title = "销售合同", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contractIds}")
    @ApiOperation(value = "销售合同删除", notes = "权限字符:crm:Contract:remove")
    public AjaxResult remove(@PathVariable Long[] contractIds)
    {
        return toAjax(crmContractService.deleteCrmContractByContractIds(contractIds));
    }

    /**
     * 自动生成销售合同编码
     */
    //@RequiresPermissions("crm:Contract:query")
    @GetMapping("/autoGenerateContractCode")
    @ApiOperation(value = "自动生成销售合同编号", notes = "权限字符:crm:Contract:query")
    public AjaxResult autoGenerateContractCode()
    {
        return success(crmContractService.autoGenerateContractCode());
    }





}
