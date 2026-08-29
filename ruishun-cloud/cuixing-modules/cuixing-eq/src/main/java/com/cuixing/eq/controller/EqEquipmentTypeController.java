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
import com.cuixing.eq.entity.EqEquipmentType;
import com.cuixing.eq.service.IEqEquipmentTypeService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 设备类别设置;(eq_equipment_type)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-17
 */
@Api(tags = "设备类别设置对象功能接口")
@RestController
@RequestMapping("/eqEquipmentType")
public class EqEquipmentTypeController extends BaseController{
    @Autowired
    private IEqEquipmentTypeService iEqEquipmentTypeService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqEquipmentType:query")
    //@RequiresPermissions("eq:eqEquipmentType:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEqEquipmentTypeService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param eqEquipmentType 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqEquipmentType:list")
    //@RequiresPermissions("eq:eqEquipmentType:list")
    @GetMapping("/list")
    public TableDataInfo list(EqEquipmentType eqEquipmentType){
        startPage();
        List<EqEquipmentType> list =iEqEquipmentTypeService.selectEqEquipmentTypeList(eqEquipmentType);
        return getDataTable(list);
    }
     /**
      * 查询列表
      *
      * @param eqEquipmentType 筛选条件
      * @return 查询结果
      */
     @ApiOperation(value = "查询列表", notes = "权限字符:eq:eqEquipmentType:list")
     //@RequiresPermissions("eq:eqEquipmentType:list")
     @GetMapping("/selectall")
     public AjaxResult selectall(EqEquipmentType eqEquipmentType){
         List<EqEquipmentType> list =iEqEquipmentTypeService.selectEqEquipmentTypeList(eqEquipmentType);
         return AjaxResult.success(list);
     }
    
    /** 
     * 新增数据
     *
     * @param eqEquipmentType 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqEquipmentType:add")
    //@RequiresPermissions("eq:eqEquipmentType:add")
    @Log(title = "设备类别设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqEquipmentType eqEquipmentType){
        return toAjax(iEqEquipmentTypeService.insert(eqEquipmentType));
    }
    
    
    /** 
     * 更新数据
     *
     * @param eqEquipmentType 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqEquipmentType:update")
    //@RequiresPermissions("eq:eqEquipmentType:update")
    @Log(title = "设备类别设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqEquipmentType eqEquipmentType){
        return toAjax(iEqEquipmentTypeService.update(eqEquipmentType));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqEquipmentType:remove")
    //@RequiresPermissions("eq:eqEquipmentType:remove")
    @Log(title = "设备类别设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqEquipmentTypeService.deleteByIds(ids));
    }
}