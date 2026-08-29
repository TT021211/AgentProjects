package com.cuixing.mes.controller;

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
import com.cuixing.mes.entity.MesOperationMethod;
import com.cuixing.mes.service.IMesOperationMethodService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 操作规范/作业指导书;(mes_operation_method)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-27
 */
@Api(tags = "操作规范(作业指导书)")
@RestController
@RequestMapping("/mesOperationMethod")
public class MesOperationMethodController extends BaseController{
    @Autowired
    private IMesOperationMethodService iMesOperationMethodService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:mes:mesOperationMethod:query")
    //@RequiresPermissions("mes:mesOperationMethod:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMesOperationMethodService.queryById(id));
    }

     /**
      * 通过机台id及类型查询列表
      *
      * @param enanellingWorkbenchId 主键
      * @return 实例对象
      */
     @ApiOperation(value = "通过机台id及类型查询列表")
     @GetMapping("getByEnanellingWorkbenchId/{enanellingWorkbenchId}/{operationType}")
     public AjaxResult getByEnanellingWorkbenchId(@PathVariable(value = "enanellingWorkbenchId") BigInteger enanellingWorkbenchId,@PathVariable(value = "operationType") String operationType){
         return AjaxResult.success(iMesOperationMethodService.getByEnanellingWorkbenchId(enanellingWorkbenchId,operationType));
     }
    
    /** 
     * 分页查询
     *
     * @param mesOperationMethod 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:mes:mesOperationMethod:list")
    //@RequiresPermissions("mes:mesOperationMethod:list")
    @GetMapping("/list")
    public TableDataInfo list(MesOperationMethod mesOperationMethod){
        startPage();
        List<MesOperationMethod> list =iMesOperationMethodService.selectMesOperationMethodList(mesOperationMethod);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param mesOperationMethod 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:mes:mesOperationMethod:add")
    //@RequiresPermissions("mes:mesOperationMethod:add")
    @Log(title = "操作规范(作业指导书)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesOperationMethod mesOperationMethod){
        return toAjax(iMesOperationMethodService.insert(mesOperationMethod));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mesOperationMethod 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:mes:mesOperationMethod:update")
    //@RequiresPermissions("mes:mesOperationMethod:update")
    @Log(title = "操作规范(作业指导书)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesOperationMethod mesOperationMethod){
        return toAjax(iMesOperationMethodService.update(mesOperationMethod));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:mes:mesOperationMethod:remove")
    //@RequiresPermissions("mes:mesOperationMethod:remove")
    @Log(title = "操作规范(作业指导书)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMesOperationMethodService.deleteByIds(ids));
    }



 }