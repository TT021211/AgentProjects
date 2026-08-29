package com.cuixing.mes.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.page.WmsTableDataInfo;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.mes.entity.vo.MesOutputLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.log.annotation.Log;
import javax.servlet.http.HttpServletResponse;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.mes.entity.MesOuputLog;
import com.cuixing.mes.service.IMesOuputLogService;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * 报工记录表;(mes_ouput_log)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Api(tags = "报工记录表对象功能接口")
@RestController
@RequestMapping("/mesOutputLog")
public class MesOutputLogController extends BaseController {

    @Autowired
    private IMesOuputLogService mesOuputLogService;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:output:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mesOuputLogService.checkMesOuputLogDataScope(id);
        return AjaxResult.success(mesOuputLogService.queryById(id));
    }


    /**
     * 通过机台查询最新的报工规格
     *
     */
    @ApiOperation("通过机台查询最新的报工规格")
    //@RequiresPermissions("mes:output:query")
    @GetMapping("/getSpecificationByWorkstationCode/{workstationCode}")
    public AjaxResult getSpecification(@PathVariable(value = "workstationCode") String workstationCode){
        return AjaxResult.success(mesOuputLogService.getSpecificationByWorkstationCode(workstationCode));
    }

    /**
     * 通过物料唯一码查询单条数据
     *
     * @param outputSn
     * @return 实例对象
     */
    @ApiOperation("通过物料唯一码查询单条数据")
    //@RequiresPermissions("mes:output:query")
    @GetMapping("/getInfoByOutputSn/{outputSn}")
    public AjaxResult getInfoByOutputSn(@PathVariable(value = "outputSn") String outputSn){
        return AjaxResult.success(mesOuputLogService.getInfoByOutputSn(outputSn));
    }


    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:output:export")
    @Log(title = "报工记录表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MesOuputLog mesOuputLog)
    {
        List<MesOuputLog> list = mesOuputLogService.selectMesOuputLogList(mesOuputLog);
        ExcelUtil<MesOuputLog> util = new ExcelUtil<MesOuputLog>(MesOuputLog.class);
        util.exportExcel(response, list, "报工记录表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:output:list")
    @GetMapping("/list")
    public TableDataInfo list(MesOuputLog mesOuputLog)
    {
        startPage();
        List<MesOuputLog> list = mesOuputLogService.selectMesOuputLogList(mesOuputLog);
        return getDataTable(list);
    }

    /**
     * 分页查询半成品出入库
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:output:list")
    @GetMapping("/wmsList")
    public WmsTableDataInfo wmsList(MesOuputLog mesOuputLog)
    {
        MesOutputLogVo mesOutputLogVo = mesOuputLogService.selectWmsmesOutputLogListSum(mesOuputLog);
        startPage();
        List<MesOuputLog> list = mesOuputLogService.selectMesOuputLogWmsList(mesOuputLog);
        WmsTableDataInfo dataTable = getWmsDataTable(list);
        dataTable.setCount(mesOutputLogVo.getCount());
        dataTable.setTotalNetWeight(mesOutputLogVo.getTotalNetWeight());
        dataTable.setTotalTareWeight(mesOutputLogVo.getTotalTareWeight());
        return dataTable;
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:output:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MesOuputLog mesOuputLog){
        List<MesOuputLog> list = mesOuputLogService.selectMesOuputLogList(mesOuputLog);
        return AjaxResult.success(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:output:list")
    @GetMapping("/selectByTime/{taskType}")
    public AjaxResult selectByTime(@PathVariable("taskType") String taskType){
        return AjaxResult.success(mesOuputLogService.selectByTime(taskType));
    }

    /**
     * 新增数据
     *
     * @param mesOuputLogs 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    //@RequiresPermissions("mes:output:add")
    @Log(title = "报工记录表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody List<MesOuputLog> mesOuputLogs){
        return AjaxResult.success(mesOuputLogService.insert(mesOuputLogs));
    }

    /**
     * 工单完成
     *
     * @param mesOuputLogs 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据(报工完成)")
    //@RequiresPermissions("mes:output:add")
    @Log(title = "报工记录表", businessType = BusinessType.INSERT)
    @PostMapping("/WorkorderComplete")
    public AjaxResult WorkorderComplete(@RequestBody List<MesOuputLog> mesOuputLogs){

        return AjaxResult.success(mesOuputLogService.WorkorderComplete(mesOuputLogs));
    }

    /**
     * 批量新增数据
     *
     * @param mesOuputLogs 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:output:add")
    @Log(title = "报工记录表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MesOuputLog> mesOuputLogs){
        return toAjax(mesOuputLogService.insertBatch(mesOuputLogs));
    }

    /**
     * 更新数据
     *
     * @param mesOuputLog 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:output:edit")
    @Log(title = "报工记录表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesOuputLog mesOuputLog){
        return toAjax(mesOuputLogService.update(mesOuputLog));
    }

    @ApiOperation("修改数据")
    //@RequiresPermissions("mes:output:edit")
    @Log(title = "报工记录表", businessType = BusinessType.UPDATE)
    @PutMapping("/updateWmsInfo")
    public AjaxResult updateInfo(@RequestBody MesOuputLog mesOuputLog){
        return toAjax(mesOuputLogService.updateInfo(mesOuputLog));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    //@RequiresPermissions("mes:output_log:delete")
    @Log(title = "报工记录表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mesOuputLogService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
//    //@RequiresPermissions("mes:output_log:delete")
    @Log(title = "报工记录表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mesOuputLogService.deleteByIds(ids));
    }

    /**
     * 放行
     */
    @ApiOperation("放行")
    @PostMapping("/release")
    public AjaxResult release(@RequestBody MesOuputLog mesOuputLog){
        mesOuputLog.setWmsStatus(3);
        return AjaxResult.success(mesOuputLogService.update(mesOuputLog));
    }

    /**
     * 报废
     */
    @ApiOperation("报废")
    @PostMapping("/scrapped")
    public AjaxResult scrapped(@RequestBody MesOuputLog mesOuputLog){
        return AjaxResult.success(mesOuputLogService.scrapped(mesOuputLog));
    }

}
