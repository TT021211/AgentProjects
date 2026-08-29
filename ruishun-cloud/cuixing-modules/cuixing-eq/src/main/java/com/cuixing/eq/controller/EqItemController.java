package com.cuixing.eq.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.eq.entity.EqList;
import com.cuixing.system.api.domain.md.MdWorkbenchEnamelling;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.eq.entity.EqItem;
import com.cuixing.eq.service.IEqItemService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 设备项目维护;(eq_item)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Api(tags = "设备项目维护对象功能接口")
@RestController
@RequestMapping("/eqItem")
public class EqItemController extends BaseController{
    @Autowired
    private IEqItemService iEqItemService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqItem:query")
    //@RequiresPermissions("eq:eqItem:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEqItemService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param eqItem 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqItem:list")
    //@RequiresPermissions("eq:eqItem:list")
    @GetMapping("/list")
    public TableDataInfo list(EqItem eqItem){
        startPage();
        List<EqItem> list =iEqItemService.selectEqItemList(eqItem);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param eqItem 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqItem:add")
    //@RequiresPermissions("eq:eqItem:add")
    @Log(title = "设备项目维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqItem eqItem){
        return toAjax(iEqItemService.insert(eqItem));
    }
    
    
    /** 
     * 更新数据
     *
     * @param eqItem 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqItem:update")
    //@RequiresPermissions("eq:eqItem:update")
    @Log(title = "设备项目维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqItem eqItem){
        return toAjax(iEqItemService.update(eqItem));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqItem:remove")
    //@RequiresPermissions("eq:eqItem:remove")
    @Log(title = "设备项目维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqItemService.deleteByIds(ids));
    }

 }