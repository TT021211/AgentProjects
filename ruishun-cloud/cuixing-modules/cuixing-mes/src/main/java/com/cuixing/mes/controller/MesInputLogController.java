package com.cuixing.mes.controller;


import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.mes.service.IMesProductWorkorderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.log.annotation.Log;
import javax.servlet.http.HttpServletResponse;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.mes.entity.MesInputLog;
import com.cuixing.mes.service.IMesInputLogService;
import java.math.BigInteger;
import java.util.List;

/**
 * 投料记录表;(mes_input_log)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Api(tags = "投料记录表对象功能接口")
@RestController
@RequestMapping("/mesInputLog")
public class MesInputLogController extends BaseController {

    @Autowired
    private IMesInputLogService mesInputLogService;


    @Autowired
    private IMesProductWorkorderService mesProductWorkorderService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
//    @RequiresPermissions("mes:input_log:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mesInputLogService.checkMesInputLogDataScope(id);
        return AjaxResult.success(mesInputLogService.queryById(id));
    }

    /**
     * 拉丝通过机台Id查询最新投料记录
     *
     * @param workstationId 主键
     * @return 实例对象
     */
    @ApiOperation("拉丝通过机台Id查询最新投料记录")
//    @RequiresPermissions("mes:input_log:query")
    @GetMapping("/getByWorkstationId/{workstationId}")
    public AjaxResult getInfoByWorkstationId(@PathVariable(value = "workstationId") BigInteger workstationId){
        return AjaxResult.success(mesInputLogService.queryByWorkstationId(workstationId));
    }

    /**
     * 通过物料唯一码查询单条数据
     *
     * @param inputSn
     * @return 实例对象
     */
    @ApiOperation("通过物料唯一码查询单条数据")
    //@RequiresPermissions("mes:output:query")
    @GetMapping("/getInfoByInputSn/{inputSn}")
    public AjaxResult getInfoByInputSn(@PathVariable(value = "inputSn") String inputSn){
        return AjaxResult.success(mesInputLogService.getInfoByInputSn(inputSn));
    }

    /**
     * 漆包通过机台id、轴数查询该轴最新投料记录
     *
     * @param workstationId 主键
     * @return 实例对象
     */
    @ApiOperation("漆包通过机台id、轴数、轴号查询该轴最新投料记录")
//    @RequiresPermissions("mes:input_log:query")
    @GetMapping("/getInfoByWorkstationIdAndAlexPositionNumberAndAxleNumber/{workstationId}/{alexPositionNumber}/{axleNumber}")
    public AjaxResult getInfoByWorkstationIdAndAlexPositionNumberAndAxleNumber(@PathVariable(value = "workstationId") BigInteger workstationId,@PathVariable(value = "alexPositionNumber") Integer alexPositionNumber,@PathVariable(value = "axleNumber") String axleNumber){
        return AjaxResult.success(mesInputLogService.getInfoByWorkstationIdAndAlexPositionNumberAndAxleNumber(workstationId,alexPositionNumber,axleNumber));
    }

    /**
     * 漆包通过机台、轴号查询最新投料记录
     *
     * @param workstationId 主键
     * @return 实例对象
     */
    @ApiOperation("漆包通过机台、轴号查询最新投料记录")
//    @RequiresPermissions("mes:input_log:query")
    @GetMapping("/getByWorkstationIdAndAxleNumber/{workstationId}/{axleNumber}")
    public AjaxResult getInfoByWorkstationIdAndAxleNumber(@PathVariable(value = "workstationId") BigInteger workstationId,@PathVariable(value = "axleNumber") String axleNumber){
        return AjaxResult.success(mesInputLogService.queryByWorkstationIdAndAxleNumber(workstationId,axleNumber));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
//    @RequiresPermissions("mes:input_log:export")
    @Log(title = "投料记录表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MesInputLog mesInputLog)
    {
        List<MesInputLog> list = mesInputLogService.selectMesInputLogList(mesInputLog);
        ExcelUtil<MesInputLog> util = new ExcelUtil<MesInputLog>(MesInputLog.class);
        util.exportExcel(response, list, "投料记录表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
//    @RequiresPermissions("mes:input_log:list")
    @GetMapping("/list")
    public TableDataInfo list(MesInputLog mesInputLog)
    {
        startPage();
        List<MesInputLog> list = mesInputLogService.selectMesInputLogList(mesInputLog);
        return getDataTable(list);
    }

    /**
     * 更新机台报工数据
     */
    @ApiOperation("更新机台报工数据")
    @GetMapping("/updateWorkorderComplete/{id}")
    public AjaxResult updateWorkorderComplete(@PathVariable(value = "id") String id){
        return AjaxResult.success(mesInputLogService.updateWorkorderComplete(id));
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
//    @RequiresPermissions("mes:input_log:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MesInputLog mesInputLog){
        List<MesInputLog> list = mesInputLogService.selectMesInputLogList(mesInputLog);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mesInputLog 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
//    @RequiresPermissions("mes:input_log:add")
    @Log(title = "投料记录表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesInputLog mesInputLog){

        return toAjax(mesInputLogService.insert(mesInputLog));
    }

    /**
     * 查询锡锭投料记录
     *
     */
    @ApiOperation("查询锡锭投料记录")
    @GetMapping("/getWiringInputLog")
    public AjaxResult getWiringInputLog(){
        return AjaxResult.success(mesInputLogService.getWiringInputLog());
    }
    /**
     * 批量新增数据
     *
     * @param mesInputLogs 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
//    @RequiresPermissions("mes:input_log:add")
    @Log(title = "投料记录表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MesInputLog> mesInputLogs){
        return toAjax(mesInputLogService.insertBatch(mesInputLogs));
    }

    /**
     * 更新数据
     *
     * @param mesInputLog 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
//    @RequiresPermissions("mes:input_log:edit")
    @Log(title = "投料记录表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesInputLog mesInputLog){
        return toAjax(mesInputLogService.update(mesInputLog));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
//    @RequiresPermissions("mes:input_log:delete")
    @Log(title = "投料记录表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mesInputLogService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
//    @RequiresPermissions("mes:input_log:delete")
    @Log(title = "投料记录表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mesInputLogService.deleteByIds(ids));
    }



    @ApiOperation("分页查询")
//    @RequiresPermissions("mes:input_log:list")
    @PostMapping("/getWorkOrderListByMesInputLogs")
    //通过机台、轴位置数查询最新的投料信息
    public AjaxResult returnWorkOrder(@RequestBody List<MesInputLog> mesInputLogs){
        return AjaxResult.success(mesInputLogService.getWorkOdrerListByMesInputLogs(mesInputLogs));
    }
    @ApiOperation("退火查询")
//    @RequiresPermissions("mes:input_log:list")
    @PostMapping("/getWorkOrderListByMesInputLogsToA")
    //通过机台、轴位置数查询最新的投料信息
    public AjaxResult returnWorkOrderToA(@RequestBody List<MesInputLog> mesInputLogs){
        return AjaxResult.success(mesInputLogService.getWorkOdrerListByMesInputLogsToA(mesInputLogs));
    }
    @ApiOperation("镀锡查询")
//    @RequiresPermissions("mes:input_log:list")
    @PostMapping("/getWorkOrderListByMesInputLogsToT")
    //通过机台、轴位置数查询最新的投料信息
    public AjaxResult returnWorkOrderToT(@RequestBody List<MesInputLog> mesInputLogs){
        return AjaxResult.success(mesInputLogService.getWorkOdrerListByMesInputLogsToT(mesInputLogs));
    }
    @ApiOperation("并丝查询")
//    @RequiresPermissions("mes:input_log:list")
    @PostMapping("/getWorkOrderListByMesInputLogsToD")
    //通过机台、轴位置数查询最新的投料信息
    public AjaxResult returnWorkOrderToD(@RequestBody List<MesInputLog> mesInputLogs){
        return AjaxResult.success(mesInputLogService.getWorkOdrerListByMesInputLogsToD(mesInputLogs));
    }
    @ApiOperation("绞线查询")
//    @RequiresPermissions("mes:input_log:list")
    @PostMapping("/getWorkOrderListByMesInputLogsToS")
    //通过机台、轴位置数查询最新的投料信息
    public AjaxResult returnWorkOrderToS(@RequestBody List<MesInputLog> mesInputLogs){
        return AjaxResult.success(mesInputLogService.getWorkOdrerListByMesInputLogsToS(mesInputLogs));
    }
}
