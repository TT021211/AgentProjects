package com.cuixing.wms.controller;

import java.util.List;


import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.WmsConstants;
import com.cuixing.common.core.exception.md.ItemException;
import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.mes.MesOuputLog;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.wms.service.IWmsRealtimeStockService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 库存Controller
 *
 * @author wupujian
 * @date 2024-05-29
 */
@Api(tags = "库存功能接口")
@Slf4j
@RestController
@RequestMapping("/wms/stock")
public class WmsRealtimeStockController extends BaseController
{

    @Autowired
    private IWmsRealtimeStockService wmsRealtimeStockService;
    @Autowired
    private RemoteSystemService remoteSystemService;


    /**
     * 查询库存列表
     */
    //@RequiresPermissions("wms:stock:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public TableDataInfo list(WmsRealtimeStock wmsRealtimeStock)
    {
        startPage();
        List<WmsRealtimeStock> list = wmsRealtimeStockService.selectWmsRealtimeStockList(wmsRealtimeStock);
        return getDataTable(list);
    }

    /**
     * 查询库存
     */
    //@RequiresPermissions("wms:stock:getByIdentifier")
    @RequestMapping(value = "/getByIdentifier/{itemIdentifier}")
    @ResponseBody
    public AjaxResult getInfoByItemIdentifier(@PathVariable("itemIdentifier") String itemIdentifier)
    {
        return AjaxResult.success(wmsRealtimeStockService.getInfoByItemIdentifier(itemIdentifier));
    }


    /**
     * 导出库存列表
     */
    //@RequiresPermissions("wms:stock:export")
    @Log(title = "库存", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response, WmsRealtimeStock wmsRealtimeStock)
    {
        List<WmsRealtimeStock> list = wmsRealtimeStockService.selectWmsRealtimeStockList(wmsRealtimeStock);
        ExcelUtil<WmsRealtimeStock> util = new ExcelUtil<WmsRealtimeStock>(WmsRealtimeStock.class);
        util.exportExcel(response,list, "库存数据");
    }


    /**
     * 新增保存库存
     */
    @ApiOperation("feign新增数据")
    //@RequiresPermissions("wms:stock:add")
    @Log(title = "库存", businessType = BusinessType.INSERT)
    @PostMapping("/updateByFeign")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateByFeign(@RequestBody WmsRealtimeStock wmsRealtimeStock, String type)
    {
        int i = wmsRealtimeStockService.insertWmsRealtimeStock(wmsRealtimeStock, type);
        if(i != 1){
            if(i == WmsConstants.OUT_OF_STOCK){
                return AjaxResult.error("更新库存失败,库存不足");
            }
            if(i == WmsConstants.NOT_EXIST){
                return AjaxResult.error("更新库存失败,货物不存在");
            }
            return AjaxResult.error("更新库存失败");
        }
        return toAjax(i);
    }

    @ApiOperation("feign新增库存")
    //@RequiresPermissions("wms:stock:add")
    @Log(title = "添加库存", businessType = BusinessType.INSERT)
    @PostMapping("/addRealtimeStock")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addRealtimeStock(@RequestBody WmsRealtimeStock wmsRealtimeStock){
        log.info("开始updateByFeign,加库存");
        if(StringUtils.isEmpty(wmsRealtimeStock.getItemIdentifier()) ){
            return AjaxResult.error("货品识别码不能为空");
        }
        return updateByFeign(wmsRealtimeStock, "add");
    }

    @ApiOperation("feign新增库存")
    //@RequiresPermissions("wms:stock:add")
    @Log(title = "添加库存", businessType = BusinessType.INSERT)
    @PostMapping("/addRealtimeStock2")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addRealtimeStock2(@RequestBody WmsRealtimeStock wmsRealtimeStock){
        log.info("开始updateByFeign,加库存");
        if(StringUtils.isEmpty(wmsRealtimeStock.getItemIdentifier()) ){
            // 获取自动生成的二维码
            String code = remoteSystemService.getAutoCode("PRODUCT_SOLE_CODE", SecurityConstants.INNER);
            if (code == null) {
                throw new ItemException("生成产品唯一码失败");
            }
            wmsRealtimeStock.setItemIdentifier(code);
        }
        int i = wmsRealtimeStockService.insertWmsRealtimeStock(wmsRealtimeStock, "add");
        if(i != 1){
            if(i == WmsConstants.OUT_OF_STOCK){
                return AjaxResult.error("更新库存失败,库存不足");
            }
            if(i == WmsConstants.NOT_EXIST){
                return AjaxResult.error("更新库存失败,货物不存在");
            }
            return AjaxResult.error("更新库存失败");
        }
        return AjaxResult.success(wmsRealtimeStock);
    }


    @ApiOperation("feign扣减数据")
    //@RequiresPermissions("wms:stock:add")
    @Log(title = "扣减库存", businessType = BusinessType.UPDATE)
    @PostMapping("/subtractRealtimeStock")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult subtractRealtimeStock(@RequestBody WmsRealtimeStock wmsRealtimeStock){
        log.info("开始updateByFeign,扣减库存");
        if(StringUtils.isEmpty(wmsRealtimeStock.getItemIdentifier()) ){
            return AjaxResult.error("货品识别码不能为空");
        }
        return updateByFeign(wmsRealtimeStock, "subtract");
    }

    /**
     * 修改保存库存
     */
    //@RequiresPermissions("wms:stock:edit")
    @Log(title = "修改库存", businessType = BusinessType.UPDATE)
    @PostMapping("/editSave")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editSave(@RequestBody WmsRealtimeStock wmsRealtimeStock)
    {
        if(StringUtils.isEmpty(wmsRealtimeStock.getItemIdentifier()) ){
            return AjaxResult.error("货品识别码不能为空");
        }
        int i = wmsRealtimeStockService.updateWmsRealtimeStock(wmsRealtimeStock);
        if(i != 1){
            return AjaxResult.error("更新库存失败");
        }
        return toAjax(i);
    }

    /**
     * 回写库存重量
     */
    //@RequiresPermissions("wms:stock:edit")
    @Log(title = "回写库存重量", businessType = BusinessType.UPDATE)
    @PutMapping("/WriteBackWight")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editWight(@RequestBody WmsRealtimeStock wmsRealtimeStock)
    {
        if(StringUtils.isEmpty(wmsRealtimeStock.getItemIdentifier()) ){
            return AjaxResult.error("物料识别码不能为空");
        }
        MesOuputLog mesOuputLog = wmsRealtimeStockService.WriteBackWight(wmsRealtimeStock);
        return AjaxResult.success(mesOuputLog);
    }

    /**
     * 修改在制品状态
     */
    //@RequiresPermissions("wms:stock:edit")
    @Log(title = "修改在制品状态", businessType = BusinessType.UPDATE)
    @PutMapping("/updateDetectionStatus/{itemIdentifier}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateDetectionStatus(@PathVariable("itemIdentifier") String itemIdentifier)
    {
        return AjaxResult.success(wmsRealtimeStockService.updateDetectionStatus(itemIdentifier));
    }
    /**
     * 修改在制品状态
     */
    //@RequiresPermissions("wms:stock:edit")
    @Log(title = "修改在制品状态", businessType = BusinessType.UPDATE)
    @PutMapping("/updateDetectionStatus2/{itemIdentifiers}/{type}/{taskType}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateDetectionStatus2(@PathVariable("itemIdentifiers") List<String> itemIdentifiers,@PathVariable("type") String type,@PathVariable("taskType") String taskType)
    {
        return toAjax(wmsRealtimeStockService.updateDetectionStatus2(itemIdentifiers,type,taskType));
    }

    /**
     * 删除库存
     */
    //@RequiresPermissions("wms:stock:remove")
    @Log(title = "库存", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(wmsRealtimeStockService.deleteWmsRealtimeStockByMaterialStockIds(ids));
    }




}
