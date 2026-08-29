package com.cuixing.wms.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.core.web.page.WmsTableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.wms.entity.WmsIngredientsLog;
import com.cuixing.wms.entity.vo.WmsIngredientsLogVo;
import com.cuixing.wms.service.IWmsIngredientsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;

/**
 * 原料出入库日志;(wms_Ingredients_log)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Api(tags = "原料出入库日志对象功能接口")
@RestController
@RequestMapping("/wmsIngredientsLog")
public class WmsIngredientsLogController extends BaseController{
    @Autowired
    private IWmsIngredientsLogService iWmsIngredientsLogService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:wms:wmsIngredientsLog:query")
    //@RequiresPermissions("wms:wmsIngredientsLog:query")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iWmsIngredientsLogService.queryById(id));
    }

     /**
      * 通过物料唯一码获取入库记录
      *
      * @param codeSn
      * @return 实例对象
      */
     @ApiOperation(value = "通过物料唯一码获取入库记录", notes = "权限字符:wms:wmsIngredientsLog:query")
     //@RequiresPermissions("wms:wmsIngredientsLog:query")
     @GetMapping("{codeSn}")
     public AjaxResult queryByCodeSn(@PathVariable(value = "codeSn") String codeSn){
         return AjaxResult.success(iWmsIngredientsLogService.queryByCodeSn(codeSn));
     }
    
    /** 
     * 分页查询
     *
     * @param wmsIngredientsLog 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:wms:wmsIngredientsLog:list")
    //@RequiresPermissions("wms:wmsIngredientsLog:list")
    @GetMapping("/list")
    public WmsTableDataInfo list(WmsIngredientsLog wmsIngredientsLog){
        WmsIngredientsLogVo wmsIngredientsLogVo =iWmsIngredientsLogService.selectWmsIngredientsLogListSum(wmsIngredientsLog);
        startPage();
        List<WmsIngredientsLog> list = iWmsIngredientsLogService.selectWmsIngredientsLogList(wmsIngredientsLog);
        WmsTableDataInfo dataTable = getWmsDataTable(list);
        dataTable.setCount(wmsIngredientsLogVo.getCount());
        dataTable.setTotalNetWeight(wmsIngredientsLogVo.getTotalNetWeight());
        dataTable.setTotalTareWeight(wmsIngredientsLogVo.getTotalTareWeight());
        return dataTable;
    }
    
    /** 
     * 新增数据
     *
     * @param wmsIngredientsLog 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:wms:wmsIngredientsLog:add")
    //@RequiresPermissions("wms:wmsIngredientsLog:add")
    @Log(title = "原料出入库日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsIngredientsLog wmsIngredientsLog){
        return AjaxResult.success(iWmsIngredientsLogService.insert(wmsIngredientsLog));
    }
    
    
    /** 
     * 更新数据
     *
     * @param wmsIngredientsLog 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:wms:wmsIngredientsLog:update")
    //@RequiresPermissions("wms:wmsIngredientsLog:update")
    @Log(title = "原料出入库日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsIngredientsLog wmsIngredientsLog){
        return toAjax(iWmsIngredientsLogService.update(wmsIngredientsLog));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:wms:wmsIngredientsLog:remove")
    //@RequiresPermissions("wms:wmsIngredientsLog:remove")
    @Log(title = "原料错误入库执行删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iWmsIngredientsLogService.deleteByIds(ids));
    }

    /**
      * 原料错误入库执行出库
      */
     @ApiOperation(value = "原料错误入库执行出库", notes = "权限字符:wms:wmsIngredientsLog:add")
     //@RequiresPermissions("wms:wmsIngredientsLog:add")
     @Log(title = "原料错误入库执行出库", businessType = BusinessType.INSERT)
     @PostMapping( "/mistake/{ids}")
     public AjaxResult mistake(@PathVariable BigInteger[] ids){
         return toAjax(iWmsIngredientsLogService.mistakeByIds(ids));
     }

    /**
     * 通过贸易商查询当天最大的批次号和料号
     */
    @ApiOperation(value = "通过贸易商查询当天最大的批次号和料号", notes = "权限字符:wms:wmsIngredientsLog:query")
    //@RequiresPermissions("wms:wmsIngredientsLog:query")
    @GetMapping("/selectMaxBatch/{manufacturerName}")
    public AjaxResult selectMaxBatch(@PathVariable(value = "manufacturerName") String manufacturerName){
        return AjaxResult.success(iWmsIngredientsLogService.selectMaxBatch(manufacturerName));
    }
}