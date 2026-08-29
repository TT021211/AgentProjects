package com.cuixing.md.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.md.entity.MdWorkbenchType;
import com.cuixing.md.service.IMdWorkbenchTypeService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 机台类型;(md_workbench_type)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@Api(tags = "机台类型对象功能接口")
@RestController
@RequestMapping("/mdWorkbenchType")
public class MdWorkbenchTypeController extends BaseController{
    @Autowired
    private IMdWorkbenchTypeService iMdWorkbenchTypeService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdWorkbenchTypeService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param mdWorkbenchType 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MdWorkbenchType mdWorkbenchType){
        startPage();
        List<MdWorkbenchType> list =iMdWorkbenchTypeService.selectMdWorkbenchTypeList(mdWorkbenchType);
        return getDataTable(list);
    }
     /**
      * 查询列表
      *
      * @param mdWorkbenchType 筛选条件
      * @return 查询结果
      */
     @ApiOperation("分页查询")
     @GetMapping("/selectall")
     public AjaxResult getAll(MdWorkbenchType mdWorkbenchType){
         List<MdWorkbenchType> list =iMdWorkbenchTypeService.selectMdWorkbenchTypeList(mdWorkbenchType);
         return AjaxResult.success(list);
     }
    
    /** 
     * 新增数据
     *
     * @param mdWorkbenchType 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "机台类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkbenchType mdWorkbenchType){
        return toAjax(iMdWorkbenchTypeService.insert(mdWorkbenchType));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mdWorkbenchType 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "机台类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkbenchType mdWorkbenchType){
        return toAjax(iMdWorkbenchTypeService.update(mdWorkbenchType));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "机台类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdWorkbenchTypeService.deleteByIds(ids));
    }
}
