package com.cuixing.ecm.controller;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.cuixing.ecm.domain.VO.ConsumptionValueAndCharge24;
import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.cuixing.ecm.domain.EcmElectricityConsumption;
import com.cuixing.ecm.service.IEcmElectricityConsumptionService;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.page.TableDataInfo;

/**
 * 电能耗数据Controller
 *
 * @author zhan
 * @date 2024-07-24
 */
@RestController
@RequestMapping("/ecmElectricityConsumption")
@Api(tags = "电能耗数据接口")
public class EcmElectricityConsumptionController extends BaseController {
    @Autowired
    private IEcmElectricityConsumptionService ecmElectricityConsumptionService;

    /**
     * 查询电能耗数据列表
     */
    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/list")
    @ApiOperation(value = "查询电能耗数据列表", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public TableDataInfo list(EcmElectricityConsumption ecmElectricityConsumption) {
        startPage();
        List<EcmElectricityConsumption> list = ecmElectricityConsumptionService.selectEcmElectricityConsumptionList(ecmElectricityConsumption);
        return getDataTable(list);
    }

    /**
     * 查询各个车间24小时电耗
     */
    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/getDeviceConsumptionValue24/{location}/{date}")
    @ApiOperation(value = "查询电能耗数据列表", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult getDeviceConsumptionValue24(@PathVariable("location") String location, @PathVariable("date")  @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        List<ConsumptionValueAndCharge24> list = ecmElectricityConsumptionService.getDeviceConsumptionValue24(location,date);
        return AjaxResult.success(list);
    }

    /**
     * 根据安装线路所属总表分表查询24小时电耗
     */
    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/getDeviceConsumptionValue24ByLine/{parentId}/{date}")
    @ApiOperation(value = "查询电能耗数据列表", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult getDeviceConsumptionValue24ByLine(@PathVariable("parentId") Integer parentId,@PathVariable("date")  @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        List<ConsumptionValueAndCharge24> list = ecmElectricityConsumptionService.getDeviceConsumptionValue24ByLine(parentId,date);
        return AjaxResult.success(list);
    }
    /**
     * 导出电能耗数据列表
     */
    //@RequiresPermissions("ecm:ecmElectricityConsumption:export")
    @Log(title = "电能耗数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出电能耗数据列表", notes = "权限字符:ecm:ecmElectricityConsumption:export")
    public void export(HttpServletResponse response, EcmElectricityConsumption ecmElectricityConsumption) {
        List<EcmElectricityConsumption> list = ecmElectricityConsumptionService.selectEcmElectricityConsumptionList(ecmElectricityConsumption);
        ExcelUtil<EcmElectricityConsumption> util = new ExcelUtil<EcmElectricityConsumption>(EcmElectricityConsumption.class);
        util.exportExcel(response, list, "电能耗数据数据");
    }

    /**
     * 获取电能耗数据详细信息
     */
    //@RequiresPermissions("ecm:ecmElectricityConsumption:query")
    @GetMapping(value = "/{consumptionId}")
    @ApiOperation(value = "获取电能耗数据详细信息", notes = "权限字符:ecm:ecmElectricityConsumption:query")
    public AjaxResult getInfo(@PathVariable("consumptionId") Long consumptionId) {
        return success(ecmElectricityConsumptionService.selectEcmElectricityConsumptionByConsumptionId(consumptionId));
    }

    /**
     * 新增电能耗数据
     */
    //@RequiresPermissions("ecm:ecmElectricityConsumption:add")
    @Log(title = "电能耗数据", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增电能耗数据", notes = "权限字符:ecm:ecmElectricityConsumption:add")
    public AjaxResult add(@RequestBody EcmElectricityConsumption ecmElectricityConsumption) {
        return toAjax(ecmElectricityConsumptionService.insertEcmElectricityConsumption(ecmElectricityConsumption));
    }

    /**
     * 修改电能耗数据
     */
    //@RequiresPermissions("ecm:ecmElectricityConsumption:edit")
    @Log(title = "电能耗数据", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改电能耗数据", notes = "权限字符:ecm:ecmElectricityConsumption:edit")
    public AjaxResult edit(@RequestBody EcmElectricityConsumption ecmElectricityConsumption) {
        return toAjax(ecmElectricityConsumptionService.updateEcmElectricityConsumption(ecmElectricityConsumption));
    }

    /**
     * 删除电能耗数据
     */
    //@RequiresPermissions("ecm:ecmElectricityConsumption:remove")
    @Log(title = "电能耗数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{consumptionIds}")
    @ApiOperation(value = "删除电能耗数据", notes = "权限字符:ecm:ecmElectricityConsumption:remove")
    public AjaxResult remove(@PathVariable String[] consumptionIds) {
        return toAjax(ecmElectricityConsumptionService.deleteEcmElectricityConsumptionByConsumptionIds(consumptionIds));
    }

    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/calculateHourlyConsumption/{electricDeviceCode}/{date}")
    @ApiOperation(value = "计算每小时的消耗量", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult calculateHourlyConsumption(@PathVariable("electricDeviceCode") String electricDeviceCode, @PathVariable("date")  @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        return success(ecmElectricityConsumptionService.calculateHourlyConsumption(electricDeviceCode, date));
    }

    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/calculateHourlyConsumption24/{electricDeviceCode}/{date}")
    @ApiOperation(value = "计算每小时的消耗量", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult calculateHourlyConsumption24(@PathVariable("electricDeviceCode") String electricDeviceCode, @PathVariable("date")  @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        return success(ecmElectricityConsumptionService.calculateHourlyConsumption24(electricDeviceCode, date));
    }

    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/calculateDailyConsumption/{electricDeviceCode}/{month}")
    @ApiOperation(value = "计算每天的消耗量", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult calculateDailyConsumption(@PathVariable("electricDeviceCode") String electricDeviceCode, @PathVariable("month") String month) {
        month += "-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newMonth = LocalDate.parse(month, formatter);
        return success(ecmElectricityConsumptionService.calculateDailyConsumption(electricDeviceCode, newMonth));
    }

    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/calculateMonthlyConsumption/{electricDeviceCode}/{year}")
    @ApiOperation(value = "计算每月的消耗量", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult calculateMonthlyConsumption(@PathVariable("electricDeviceCode") String electricDeviceCode, @PathVariable("year") int year) {
        return success(ecmElectricityConsumptionService.calculateMonthlyConsumption(electricDeviceCode, year));
    }

    //计算各个车间年季月日用电量
    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/calculateTotalElectricityConsumption/{location}/{type}/{date}")
    @ApiOperation(value = "计算各个车间年季月日用电量", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult calculateTotalElectricityConsumption(@PathVariable("location") String location, @PathVariable("type") String type,@PathVariable("date") String date) {
        return success(ecmElectricityConsumptionService.calculateTotalElectricityConsumption(location, type,date));
    }

    //计算各个总表年季月日用电量
    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/calculateTotalSummaryTable/{type}/{date}")
    @ApiOperation(value = "计算各个总表年季月日用电量", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult calculateTotalSummaryTable(@PathVariable("type") String type,@PathVariable("date") String date) {
        return success(ecmElectricityConsumptionService.calculateTotalSummaryTable( type,date));
    }

    //计算各个总表时间范围内用电量
    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/calculateTotalSummaryTableDate/{beginTime}/{endTime}")
    @ApiOperation(value = "计算各个总表时间范围内用电量", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult calculateTotalSummaryTableDate(@PathVariable("beginTime") String beginTime, @PathVariable("endTime") String endTime) {
        return success(ecmElectricityConsumptionService.calculateTotalSummaryTableDate(beginTime,endTime));
    }

    //计算各个车间时间范围内用电量
    //@RequiresPermissions("ecm:ecmElectricityConsumption:list")
    @GetMapping("/calculateTotalSummaryTableDateByLocation/{beginTime}/{endTime}")
    @ApiOperation(value = "计算各个总表时间范围内用电量", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult calculateTotalSummaryTableDateByLocation(@PathVariable("beginTime") String beginTime, @PathVariable("endTime") String endTime) {
        return success(ecmElectricityConsumptionService.calculateTotalSummaryTableDateByLocation(beginTime,endTime));
    }

    //查看每个车间的周月年电耗
    @GetMapping("/getKanban")
    @ApiOperation(value = "查看每个车间的周月年电耗", notes = "权限字符:ecm:ecmElectricityConsumption:list")
    public AjaxResult getKanban() {
        return success(ecmElectricityConsumptionService.getKanban());
    }

}
