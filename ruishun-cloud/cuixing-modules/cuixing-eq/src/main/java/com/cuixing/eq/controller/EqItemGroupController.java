package com.cuixing.eq.controller;

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
import com.cuixing.eq.entity.EqItemGroup;
import com.cuixing.eq.service.IEqItemGroupService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 项目组别设置;(eq_item_group)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-7-5
 */
@Api(tags = "项目组别设置对象功能接口")
@RestController
@RequestMapping("/eqItemGroup")
public class EqItemGroupController extends BaseController{
    @Autowired
    private IEqItemGroupService iEqItemGroupService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqItemGroup:query")
    //@RequiresPermissions("eq:eqItemGroup:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEqItemGroupService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param eqItemGroup 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqItemGroup:list")
    //@RequiresPermissions("eq:eqItemGroup:list")
    @GetMapping("/list")
    public TableDataInfo list(EqItemGroup eqItemGroup){
        startPage();
        List<EqItemGroup> list =iEqItemGroupService.selectEqItemGroupList(eqItemGroup);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param eqItemGroup 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqItemGroup:add")
    //@RequiresPermissions("eq:eqItemGroup:add")
    @Log(title = "项目组别设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqItemGroup eqItemGroup){
        return toAjax(iEqItemGroupService.insert(eqItemGroup));
    }
    
    
    /** 
     * 更新数据
     *
     * @param eqItemGroup 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqItemGroup:update")
    //@RequiresPermissions("eq:eqItemGroup:update")
    @Log(title = "项目组别设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqItemGroup eqItemGroup){
        return toAjax(iEqItemGroupService.update(eqItemGroup));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqItemGroup:remove")
    //@RequiresPermissions("eq:eqItemGroup:remove")
    @Log(title = "项目组别设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqItemGroupService.deleteByIds(ids));
    }
}