package com.cuixing.wms.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.wms.entity.WmsAccessoryLog;
import com.cuixing.wms.service.IWmsAccessoryLogService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 辅料出入库记录;(wms_accessory_log)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-28
 */
@Api(tags = "辅料出入库记录对象功能接口")
@RestController
@RequestMapping("/wmsAccessoryLog")
public class WmsAccessoryLogController extends BaseController{
    @Autowired
    private IWmsAccessoryLogService iWmsAccessoryLogService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:wms:wmsAccessoryLog:query")
    ////@RequiresPermissions("wms:wmsAccessoryLog:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iWmsAccessoryLogService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param wmsAccessoryLog 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:wms:wmsAccessoryLog:list")
    ////@RequiresPermissions("wms:wmsAccessoryLog:list")
    @GetMapping("/list")
    public TableDataInfo list(WmsAccessoryLog wmsAccessoryLog){
        startPage();
        List<WmsAccessoryLog> list =iWmsAccessoryLogService.selectWmsAccessoryLogList(wmsAccessoryLog);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param wmsAccessoryLog 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:wms:wmsAccessoryLog:add")
    ////@RequiresPermissions("wms:wmsAccessoryLog:add")
    @Log(title = "辅料出入库记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsAccessoryLog wmsAccessoryLog){
        return toAjax(iWmsAccessoryLogService.insert(wmsAccessoryLog));
    }
    
    
    /** 
     * 更新数据
     *
     * @param wmsAccessoryLog 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:wms:wmsAccessoryLog:update")
    ////@RequiresPermissions("wms:wmsAccessoryLog:update")
    @Log(title = "辅料出入库记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsAccessoryLog wmsAccessoryLog){
        return toAjax(iWmsAccessoryLogService.update(wmsAccessoryLog));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:wms:wmsAccessoryLog:remove")
    ////@RequiresPermissions("wms:wmsAccessoryLog:remove")
    @Log(title = "辅料出入库记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iWmsAccessoryLogService.deleteByIds(ids));
    }
}