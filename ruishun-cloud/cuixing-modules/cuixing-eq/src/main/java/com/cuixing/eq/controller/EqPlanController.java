package com.cuixing.eq.controller;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.eq.entity.EqList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.eq.entity.EqPlan;
import com.cuixing.eq.service.IEqPlanService;
import java.math.BigInteger;
import java.util.List;

/**
 * 点检保养计划;(eq_plan)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-7-8
 */
@Api(tags = "点检保养计划对象功能接口")
@RestController
@RequestMapping("/eqPlan")
public class EqPlanController extends BaseController{
    @Autowired
    private IEqPlanService iEqPlanService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqPlan:query")
    //@RequiresPermissions("eq:eqPlan:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEqPlanService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param eqPlan 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqPlan:list")
    //@RequiresPermissions("eq:eqPlan:list")
    @GetMapping("/list")
    public TableDataInfo list(EqPlan eqPlan){
        startPage();
        List<EqPlan> list =iEqPlanService.selectEqPlanList(eqPlan);
        return getDataTable(list);
    }

    /**
     * 查询列表
     *
     * @param eqPlan 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "查询列表", notes = "权限字符:eq:eqPlan:list")
    //@RequiresPermissions("eq:eqPlan:list")
    @PostMapping("/getAll")
    public AjaxResult getAll(@RequestBody EqPlan eqPlan){
        List<EqPlan> list =iEqPlanService.selectEqPlanList(eqPlan);
        return AjaxResult.success(list);
    }
    
    /** 
     * 新增数据
     *
     * @param eqPlan 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqPlan:add")
    //@RequiresPermissions("eq:eqPlan:add")
    @Log(title = "点检保养计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqPlan eqPlan){
        return toAjax(iEqPlanService.insert(eqPlan));
    }
    
    
    /** 
     * 更新数据
     *
     * @param eqPlan 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqPlan:update")
    //@RequiresPermissions("eq:eqPlan:update")
    @Log(title = "点检保养计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqPlan eqPlan){
        return toAjax(iEqPlanService.update(eqPlan));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqPlan:remove")
    //@RequiresPermissions("eq:eqPlan:remove")
    @Log(title = "点检保养计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqPlanService.deleteByIds(ids));
    }

    /**
     * * 通过类型id获取设备项目列表
     */
    @ApiOperation(value = "通过类型id获取设备项目列表", notes = "权限字符:eq:eqList:list")
    //@RequiresPermissions("eq:eqList:list")
    @GetMapping("/getEqListByItemTypeId/{itemTypeId}")
    public AjaxResult getEqListByItemTypeId(@PathVariable BigInteger itemTypeId){

        List<EqList> list =iEqPlanService.getEqListByItemTypeId(itemTypeId);

        return AjaxResult.success(list);
    }

    /**
     * 根据计划id生成待点检列表
     */
    @ApiOperation(value = "根据计划id生成待点检列表", notes = "权限字符:eq:eqList:insert")
    //@RequiresPermissions("eq:eqList:insert")
    @Log(title = "根据计划id生成待点检列表", businessType = BusinessType.DELETE)
    @PostMapping("/insertEqList/{id}")
    public AjaxResult insertEqList(@PathVariable BigInteger id){
       return toAjax(iEqPlanService.insertEqList(id));
    }
}