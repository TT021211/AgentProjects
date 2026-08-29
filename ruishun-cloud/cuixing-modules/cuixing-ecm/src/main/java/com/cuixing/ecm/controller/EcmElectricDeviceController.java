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
import com.cuixing.ecm.domain.EcmElectricDevice;
import com.cuixing.ecm.service.IEcmElectricDeviceService;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.page.TableDataInfo;

/**
 * 能耗设备Controller
 *
 * @author zhan
 * @date 2024-07-24
 */
@RestController
@RequestMapping("/ecmElectricDevice")
@Api(tags = "能耗设备接口")
public class EcmElectricDeviceController extends BaseController
{
    @Autowired
    private IEcmElectricDeviceService ecmElectricDeviceService;

    /**
     * 查询能耗设备列表
     */
    //@RequiresPermissions("ecm:ecmElectricDevice:list")
    @GetMapping("/list")
    @ApiOperation(value = "查询能耗设备列表",notes = "权限字符:ecm:ecmElectricDevice:list")
    public TableDataInfo list(EcmElectricDevice ecmElectricDevice)
    {
        startPage();
        List<EcmElectricDevice> list = ecmElectricDeviceService.selectEcmElectricDeviceList(ecmElectricDevice);
        return getDataTable(list);
    }

    /**
     * 导出能耗设备列表
     */
    //@RequiresPermissions("ecm:ecmElectricDevice:export")
    @Log(title = "能耗设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出能耗设备列表",notes = "权限字符:ecm:ecmElectricDevice:export")
    public void export(HttpServletResponse response, EcmElectricDevice ecmElectricDevice)
    {
        List<EcmElectricDevice> list = ecmElectricDeviceService.selectEcmElectricDeviceList(ecmElectricDevice);
        ExcelUtil<EcmElectricDevice> util = new ExcelUtil<EcmElectricDevice>(EcmElectricDevice.class);
        util.exportExcel(response, list, "能耗设备数据");
    }

    /**
     * 获取能耗设备详细信息
     */
    //@RequiresPermissions("ecm:ecmElectricDevice:query")
    @GetMapping(value = "/{electricDeviceId}")
    @ApiOperation(value = "获取能耗设备详细信息",notes = "权限字符:ecm:ecmElectricDevice:query")
    public AjaxResult getInfo(@PathVariable("electricDeviceId") Long electricDeviceId)
    {
        return success(ecmElectricDeviceService.selectEcmElectricDeviceByElectricDeviceId(electricDeviceId));
    }

    /**
     * 获取能耗设备详细信息
     */
    //@RequiresPermissions("ecm:ecmElectricDevice:query")
    @GetMapping(value = "/getDeviceListByLocation/{location}")
    @ApiOperation(value = "通过车间位置获取对应电表设备列表",notes = "权限字符:ecm:ecmElectricDevice:query")
    public AjaxResult getDeviceListByLocation(@PathVariable("location") String location)
    {
        return success(ecmElectricDeviceService.getDeviceListByLocation(location));
    }

    /**
     * 新增能耗设备
     */
    //@RequiresPermissions("ecm:ecmElectricDevice:add")
    @Log(title = "能耗设备", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增能耗设备",notes = "权限字符:ecm:ecmElectricDevice:add")
    public AjaxResult add(@RequestBody EcmElectricDevice ecmElectricDevice)
    {
        return toAjax(ecmElectricDeviceService.insertEcmElectricDevice(ecmElectricDevice));
    }

    /**
     * 修改能耗设备
     */
    //@RequiresPermissions("ecm:ecmElectricDevice:edit")
    @Log(title = "能耗设备", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改能耗设备",notes = "权限字符:ecm:ecmElectricDevice:edit")
    public AjaxResult edit(@RequestBody EcmElectricDevice ecmElectricDevice)
    {
        return toAjax(ecmElectricDeviceService.updateEcmElectricDevice(ecmElectricDevice));
    }

    /**
     * 删除能耗设备
     */
    //@RequiresPermissions("ecm:ecmElectricDevice:remove")
    @Log(title = "能耗设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{electricDeviceIds}")
    @ApiOperation(value = "删除能耗设备",notes = "权限字符:ecm:ecmElectricDevice:remove")
    public AjaxResult remove(@PathVariable Long[] electricDeviceIds)
    {
        return toAjax(ecmElectricDeviceService.deleteEcmElectricDeviceByElectricDeviceIds(electricDeviceIds));
    }



}
