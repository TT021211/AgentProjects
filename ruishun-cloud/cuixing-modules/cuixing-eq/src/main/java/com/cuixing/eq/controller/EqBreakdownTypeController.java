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
import com.cuixing.eq.entity.EqBreakdownType;
import com.cuixing.eq.service.IEqBreakdownTypeService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 设备故障类型;(eq_breakdown_type)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-7-3
 */
@Api(tags = "设备故障类型对象功能接口")
@RestController
@RequestMapping("/eqBreakdownType")
public class EqBreakdownTypeController extends BaseController{
    @Autowired
    private IEqBreakdownTypeService iEqBreakdownTypeService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqBreakdownType:query")
    //@RequiresPermissions("eq:eqBreakdownType:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEqBreakdownTypeService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param eqBreakdownType 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqBreakdownType:list")
    //@RequiresPermissions("eq:eqBreakdownType:list")
    @GetMapping("/list")
    public TableDataInfo list(EqBreakdownType eqBreakdownType){
        startPage();
        List<EqBreakdownType> list =iEqBreakdownTypeService.selectEqBreakdownTypeList(eqBreakdownType);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param eqBreakdownType 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqBreakdownType:add")
    //@RequiresPermissions("eq:eqBreakdownType:add")
    @Log(title = "设备故障类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqBreakdownType eqBreakdownType){
        return toAjax(iEqBreakdownTypeService.insert(eqBreakdownType));
    }
    
    
    /** 
     * 更新数据
     *
     * @param eqBreakdownType 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqBreakdownType:update")
    //@RequiresPermissions("eq:eqBreakdownType:update")
    @Log(title = "设备故障类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqBreakdownType eqBreakdownType){
        return toAjax(iEqBreakdownTypeService.update(eqBreakdownType));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqBreakdownType:remove")
    //@RequiresPermissions("eq:eqBreakdownType:remove")
    @Log(title = "设备故障类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqBreakdownTypeService.deleteByIds(ids));
    }
}