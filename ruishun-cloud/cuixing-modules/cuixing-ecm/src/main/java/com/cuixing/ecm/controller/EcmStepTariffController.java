package com.cuixing.ecm.controller;

import java.util.List;
import java.io.IOException;
import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.ecm.domain.EcmStepTariff;
import com.cuixing.ecm.service.IEcmStepTariffService;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.page.TableDataInfo;

/**
 * 阶梯电价Controller
 *
 * @author zhan
 * @date 2024-08-06
 */
@RestController
@RequestMapping("/ecmStepTariff")
@Api(tags = "阶梯电价接口")
public class EcmStepTariffController extends BaseController
{
    @Autowired
    private IEcmStepTariffService ecmStepTariffService;

    /**
     * 查询阶梯电价列表
     */
    //@RequiresPermissions("ecm:ecmStepTariff:list")
    @GetMapping("/list")
    @ApiOperation(value = "查询阶梯电价列表",notes = "权限字符:ecm:ecmStepTariff:list")
    public TableDataInfo list(EcmStepTariff ecmStepTariff)
    {
        startPage();
        List<EcmStepTariff> list = ecmStepTariffService.selectEcmStepTariffList(ecmStepTariff);
        return getDataTable(list);
    }

    /**
     * 导出阶梯电价列表
     */
    //@RequiresPermissions("ecm:ecmStepTariff:export")
    @Log(title = "阶梯电价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出阶梯电价列表",notes = "权限字符:ecm:ecmStepTariff:export")
    public void export(HttpServletResponse response, EcmStepTariff ecmStepTariff)
    {
        List<EcmStepTariff> list = ecmStepTariffService.selectEcmStepTariffList(ecmStepTariff);
        ExcelUtil<EcmStepTariff> util = new ExcelUtil<EcmStepTariff>(EcmStepTariff.class);
        util.exportExcel(response, list, "阶梯电价数据");
    }

    /**
     * 获取阶梯电价详细信息
     */
    //@RequiresPermissions("ecm:ecmStepTariff:query")
    @GetMapping(value = "/{stepTariffId}")
    @ApiOperation(value = "获取阶梯电价详细信息",notes = "权限字符:ecm:ecmStepTariff:query")
    public AjaxResult getInfo(@PathVariable("stepTariffId") Long stepTariffId)
    {
        return success(ecmStepTariffService.selectEcmStepTariffByStepTariffId(stepTariffId));
    }

    /**
     * 新增阶梯电价
     */
    //@RequiresPermissions("ecm:ecmStepTariff:add")
    @Log(title = "阶梯电价", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增阶梯电价",notes = "权限字符:ecm:ecmStepTariff:add")
    public AjaxResult add(@RequestBody EcmStepTariff ecmStepTariff)
    {
        return toAjax(ecmStepTariffService.insertEcmStepTariff(ecmStepTariff));
    }

    /**
     * 修改阶梯电价
     */
    //@RequiresPermissions("ecm:ecmStepTariff:edit")
    @Log(title = "阶梯电价", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改阶梯电价",notes = "权限字符:ecm:ecmStepTariff:edit")
    public AjaxResult edit(@RequestBody EcmStepTariff ecmStepTariff)
    {
        return toAjax(ecmStepTariffService.updateEcmStepTariff(ecmStepTariff));
    }

    /**
     * 删除阶梯电价
     */
    //@RequiresPermissions("ecm:ecmStepTariff:remove")
    @Log(title = "阶梯电价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{stepTariffIds}")
    @ApiOperation(value = "删除阶梯电价",notes = "权限字符:ecm:ecmStepTariff:remove")
    public AjaxResult remove(@PathVariable Long[] stepTariffIds)
    {
        return toAjax(ecmStepTariffService.deleteEcmStepTariffByStepTariffIds(stepTariffIds));
    }
}
