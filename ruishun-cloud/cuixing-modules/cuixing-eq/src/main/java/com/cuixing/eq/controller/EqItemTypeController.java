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
import com.cuixing.eq.entity.EqItemType;
import com.cuixing.eq.service.IEqItemTypeService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 项目类型设置;(eq_item_type)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Api(tags = "项目类型设置对象功能接口")
@RestController
@RequestMapping("/eqItemType")
public class EqItemTypeController extends BaseController{
    @Autowired
    private IEqItemTypeService iEqItemTypeService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqItemType:query")
    //@RequiresPermissions("eq:eqItemType:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEqItemTypeService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param eqItemType 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqItemType:list")
    //@RequiresPermissions("eq:eqItemType:list")
    @GetMapping("/list")
    public TableDataInfo list(EqItemType eqItemType){
        startPage();
        List<EqItemType> list =iEqItemTypeService.selectEqItemTypeList(eqItemType);
        return getDataTable(list);
    }

     /**
      * 查询所有
      *
      * @param eqItemType 筛选条件
      * @return 查询结果
      */
     @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqItemType:list")
     //@RequiresPermissions("eq:eqItemType:list")
     @GetMapping("/selectall")
     public AjaxResult selectall(EqItemType eqItemType){
         List<EqItemType> list =iEqItemTypeService.selectEqItemTypeList(eqItemType);
         return AjaxResult.success(list);
     }
    
    /** 
     * 新增数据
     *
     * @param eqItemType 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqItemType:add")
    //@RequiresPermissions("eq:eqItemType:add")
    @Log(title = "项目类型设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqItemType eqItemType){
        return toAjax(iEqItemTypeService.insert(eqItemType));
    }
    
    
    /** 
     * 更新数据
     *
     * @param eqItemType 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqItemType:update")
    //@RequiresPermissions("eq:eqItemType:update")
    @Log(title = "项目类型设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqItemType eqItemType){
        return toAjax(iEqItemTypeService.update(eqItemType));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqItemType:remove")
    //@RequiresPermissions("eq:eqItemType:remove")
    @Log(title = "项目类型设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqItemTypeService.deleteByIds(ids));
    }

}