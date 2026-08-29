package com.cuixing.eq.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.eq.entity.EqItemGroup;
import com.cuixing.eq.entity.EqItemType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.eq.entity.EqItemGroupDetail;
import com.cuixing.eq.service.IEqItemGroupDetailService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 组别详情;(eq_item_group_detail)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-7-5
 */
@Api(tags = "组别详情对象功能接口")
@RestController
@RequestMapping("/eqItemGroupDetail")
public class EqItemGroupDetailController extends BaseController{
    @Autowired
    private IEqItemGroupDetailService iEqItemGroupDetailService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqItemGroupDetail:query")
    //@RequiresPermissions("eq:eqItemGroupDetail:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEqItemGroupDetailService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param eqItemGroupDetail 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqItemGroupDetail:list")
    //@RequiresPermissions("eq:eqItemGroupDetail:list")
    @GetMapping("/list")
    public TableDataInfo list(EqItemGroupDetail eqItemGroupDetail){
        startPage();
        List<EqItemGroupDetail> list =iEqItemGroupDetailService.selectEqItemGroupDetailList(eqItemGroupDetail);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param eqItemGroupDetail 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqItemGroupDetail:add")
    //@RequiresPermissions("eq:eqItemGroupDetail:add")
    @Log(title = "组别详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqItemGroupDetail eqItemGroupDetail){
        return toAjax(iEqItemGroupDetailService.insert(eqItemGroupDetail));
    }
    
    
    /** 
     * 更新数据
     *
     * @param eqItemGroupDetail 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqItemGroupDetail:update")
    //@RequiresPermissions("eq:eqItemGroupDetail:update")
    @Log(title = "组别详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqItemGroupDetail eqItemGroupDetail){
        return toAjax(iEqItemGroupDetailService.update(eqItemGroupDetail));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqItemGroupDetail:remove")
    //@RequiresPermissions("eq:eqItemGroupDetail:remove")
    @Log(title = "组别详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqItemGroupDetailService.deleteByIds(ids));
    }

     /**
      * * 批量新增项目类型
      * @param equipmentIds
      * @param eqItemGroup
      * @return
      */
     @ApiOperation(value = "批量新增项目类型", notes = "权限字符:eq:eqItemGroupDetail:add")
     //@RequiresPermissions("eq:eqItemGroupDetail:add")
     @PostMapping("/allocated/{equipmentIds}")
     public AjaxResult allocated(@PathVariable("equipmentIds") Long[] equipmentIds,@RequestBody EqItemGroup eqItemGroup)
     {
         return toAjax(iEqItemGroupDetailService.allocatedUserByIds(equipmentIds,eqItemGroup));
     }
}