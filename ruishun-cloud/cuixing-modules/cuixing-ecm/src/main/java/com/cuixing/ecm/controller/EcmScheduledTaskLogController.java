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
import com.cuixing.ecm.domain.EcmScheduledTaskLog;
import com.cuixing.ecm.service.IEcmScheduledTaskLogService;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.page.TableDataInfo;

/**
 * 能耗定时任务执行日志Controller
 *
 * @author zhan
 * @date 2024-07-25
 */
@RestController
@RequestMapping("/ecmScheduledTaskLog")
@Api(tags = "能耗定时任务执行日志接口")
public class EcmScheduledTaskLogController extends BaseController
{
    @Autowired
    private IEcmScheduledTaskLogService ecmScheduledTaskLogService;

    /**
     * 查询能耗定时任务执行日志列表
     */
    //@RequiresPermissions("ecm:ecmScheduledTaskLog:list")
    @GetMapping("/list")
    @ApiOperation(value = "查询能耗定时任务执行日志列表",notes = "权限字符:ecm:ecmScheduledTaskLog:list")
    public TableDataInfo list(EcmScheduledTaskLog ecmScheduledTaskLog)
    {
        startPage();
        List<EcmScheduledTaskLog> list = ecmScheduledTaskLogService.selectEcmScheduledTaskLogList(ecmScheduledTaskLog);
        return getDataTable(list);
    }

    /**
     * 导出能耗定时任务执行日志列表
     */
    //@RequiresPermissions("ecm:ecmScheduledTaskLog:export")
    @Log(title = "能耗定时任务执行日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出能耗定时任务执行日志列表",notes = "权限字符:ecm:ecmScheduledTaskLog:export")
    public void export(HttpServletResponse response, EcmScheduledTaskLog ecmScheduledTaskLog)
    {
        List<EcmScheduledTaskLog> list = ecmScheduledTaskLogService.selectEcmScheduledTaskLogList(ecmScheduledTaskLog);
        ExcelUtil<EcmScheduledTaskLog> util = new ExcelUtil<EcmScheduledTaskLog>(EcmScheduledTaskLog.class);
        util.exportExcel(response, list, "能耗定时任务执行日志数据");
    }

    /**
     * 获取能耗定时任务执行日志详细信息
     */
    //@RequiresPermissions("ecm:ecmScheduledTaskLog:query")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取能耗定时任务执行日志详细信息",notes = "权限字符:ecm:ecmScheduledTaskLog:query")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ecmScheduledTaskLogService.selectEcmScheduledTaskLogById(id));
    }

    /**
     * 新增能耗定时任务执行日志
     */
    //@RequiresPermissions("ecm:ecmScheduledTaskLog:add")
    @Log(title = "能耗定时任务执行日志", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增能耗定时任务执行日志",notes = "权限字符:ecm:ecmScheduledTaskLog:add")
    public AjaxResult add(@RequestBody EcmScheduledTaskLog ecmScheduledTaskLog)
    {
        return toAjax(ecmScheduledTaskLogService.insertEcmScheduledTaskLog(ecmScheduledTaskLog));
    }

    /**
     * 修改能耗定时任务执行日志
     */
    //@RequiresPermissions("ecm:ecmScheduledTaskLog:edit")
    @Log(title = "能耗定时任务执行日志", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改能耗定时任务执行日志",notes = "权限字符:ecm:ecmScheduledTaskLog:edit")
    public AjaxResult edit(@RequestBody EcmScheduledTaskLog ecmScheduledTaskLog)
    {
        return toAjax(ecmScheduledTaskLogService.updateEcmScheduledTaskLog(ecmScheduledTaskLog));
    }

    /**
     * 删除能耗定时任务执行日志
     */
    //@RequiresPermissions("ecm:ecmScheduledTaskLog:remove")
    @Log(title = "能耗定时任务执行日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value = "删除能耗定时任务执行日志",notes = "权限字符:ecm:ecmScheduledTaskLog:remove")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ecmScheduledTaskLogService.deleteEcmScheduledTaskLogByIds(ids));
    }
}
