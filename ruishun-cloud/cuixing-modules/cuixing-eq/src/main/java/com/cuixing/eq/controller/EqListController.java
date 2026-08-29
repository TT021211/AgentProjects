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
import com.cuixing.eq.entity.EqList;
import com.cuixing.eq.service.IEqListService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 待点检(保养)列表;(eq_list)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-7-9
 */
@Api(tags = "待点检(保养)列表对象功能接口")
@RestController
@RequestMapping("/eqList")
public class EqListController extends BaseController{
    @Autowired
    private IEqListService iEqListService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqList:query")
    //@RequiresPermissions("eq:eqList:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEqListService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param eqList 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqList:list")
    //@RequiresPermissions("eq:eqList:list")
    @GetMapping("/list")
    public TableDataInfo list(EqList eqList){
        startPage();
        List<EqList> list =iEqListService.selectEqListList(eqList);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param eqList 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqList:add")
    //@RequiresPermissions("eq:eqList:add")
    @Log(title = "待点检(保养)列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqList eqList){
        return toAjax(iEqListService.insert(eqList));
    }
    
    
    /** 
     * 更新数据
     *
     * @param eqList 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqList:update")
    //@RequiresPermissions("eq:eqList:update")
    @Log(title = "待点检(保养)列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqList eqList){
        return toAjax(iEqListService.update(eqList));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqList:remove")
    //@RequiresPermissions("eq:eqList:remove")
    @Log(title = "待点检(保养)列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqListService.deleteByIds(ids));
    }


     /**
      * 根据计划id删除列表
      *
      * @param planId 主键
      * @return 是否成功
      */
     @ApiOperation(value = "根据计划id删除列表", notes = "权限字符:eq:eqList:remove")
     //@RequiresPermissions("eq:eqList:remove")
     @Log(title = "待点检(保养)列表", businessType = BusinessType.DELETE)
     @DeleteMapping("/deleteByPlanId/{planId}")
     public AjaxResult deleteByPlanIds(@PathVariable BigInteger planId){
         return toAjax(iEqListService.deleteByPlanId(planId));
     }
}