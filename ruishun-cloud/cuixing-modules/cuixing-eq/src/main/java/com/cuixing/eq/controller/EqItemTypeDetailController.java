package com.cuixing.eq.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.eq.entity.EqItemType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.eq.entity.EqItemTypeDetail;
import com.cuixing.eq.service.IEqItemTypeDetailService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 项目类型详情;(eq_item_type_detail)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Api(tags = "项目类型详情对象功能接口")
@RestController
@RequestMapping("/eqItemTypeDetail")
public class EqItemTypeDetailController extends BaseController{
    @Autowired
    private IEqItemTypeDetailService iEqItemTypeDetailService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqItemTypeDetail:query")
    //@RequiresPermissions("eq:eqItemTypeDetail:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEqItemTypeDetailService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param eqItemTypeDetail 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqItemTypeDetail:list")
    //@RequiresPermissions("eq:eqItemTypeDetail:list")
    @GetMapping("/list")
    public TableDataInfo list(EqItemTypeDetail eqItemTypeDetail){
        startPage();
        List<EqItemTypeDetail> list =iEqItemTypeDetailService.selectEqItemTypeDetailList(eqItemTypeDetail);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param eqItemTypeDetail 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqItemTypeDetail:add")
    //@RequiresPermissions("eq:eqItemTypeDetail:add")
    @Log(title = "项目类型详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqItemTypeDetail eqItemTypeDetail){
        return toAjax(iEqItemTypeDetailService.insert(eqItemTypeDetail));
    }
    
    
    /** 
     * 更新数据
     *
     * @param eqItemTypeDetail 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqItemTypeDetail:update")
    //@RequiresPermissions("eq:eqItemTypeDetail:update")
    @Log(title = "项目类型详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqItemTypeDetail eqItemTypeDetail){
        return toAjax(iEqItemTypeDetailService.update(eqItemTypeDetail));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqItemTypeDetail:remove")
    //@RequiresPermissions("eq:eqItemTypeDetail:remove")
    @Log(title = "项目类型详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqItemTypeDetailService.deleteByIds(ids));
    }

     /**
      * * 批量新增项目类型
      * @param itemIds
      * @param eqItemType
      * @return
      */
     @PostMapping("/allocated/{itemIds}")
     @ApiOperation(value = "批量新增项目类型", notes = "权限字符:eq:eqItemTypeDetail:remove")
     //@RequiresPermissions("eq:eqItemTypeDetail:add")
     public AjaxResult allocated(@PathVariable("itemIds") Long[] itemIds,@RequestBody EqItemType eqItemType)
     {
         return toAjax(iEqItemTypeDetailService.allocatedUserByIds(itemIds,eqItemType));
     }
}