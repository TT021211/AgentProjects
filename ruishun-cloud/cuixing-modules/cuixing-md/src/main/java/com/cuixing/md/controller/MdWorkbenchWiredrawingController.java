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
import com.cuixing.md.entity.MdWorkbenchWiredrawing;
import com.cuixing.md.service.IMdWorkbenchWiredrawingService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 机台设置;(md_workbench_wiredrawing)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-4
 */
@Api(tags = "机台设置对象功能接口")
@RestController
@RequestMapping("/mdWorkbenchWiredrawing")
public class MdWorkbenchWiredrawingController extends BaseController{
    @Autowired
    private IMdWorkbenchWiredrawingService iMdWorkbenchWiredrawingService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdWorkbenchWiredrawingService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param mdWorkbenchWiredrawing 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MdWorkbenchWiredrawing mdWorkbenchWiredrawing){
        startPage();
        List<MdWorkbenchWiredrawing> list =iMdWorkbenchWiredrawingService.selectMdWorkbenchWiredrawingList(mdWorkbenchWiredrawing);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param mdWorkbenchWiredrawing 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "机台设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkbenchWiredrawing mdWorkbenchWiredrawing){
        if(mdWorkbenchWiredrawing.getProductionLowerLimit() > mdWorkbenchWiredrawing.getProductionUpperLimit()){
            throw new RuntimeException("生产下限不能大于上限");
        }
        return toAjax(iMdWorkbenchWiredrawingService.insert(mdWorkbenchWiredrawing));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mdWorkbenchWiredrawing 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "机台设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkbenchWiredrawing mdWorkbenchWiredrawing){
        if(mdWorkbenchWiredrawing.getProductionLowerLimit() > mdWorkbenchWiredrawing.getProductionUpperLimit()){
            throw new RuntimeException("生产下限不能大于上限");
        }
        return toAjax(iMdWorkbenchWiredrawingService.update(mdWorkbenchWiredrawing));
    }

     /**
      * 启用禁用
      *
      * @param mdWorkbenchWiredrawing 实例对象
      * @return 实例对象
      */
     @ApiOperation("启用禁用")
     @Log(title = "机台设置", businessType = BusinessType.UPDATE)
     @PutMapping("/updateEnable")
     public AjaxResult updateEnable(@RequestBody MdWorkbenchWiredrawing mdWorkbenchWiredrawing){
         return toAjax(iMdWorkbenchWiredrawingService.updateEnable(mdWorkbenchWiredrawing));
     }


     /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "机台设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdWorkbenchWiredrawingService.deleteByIds(ids));
    }
}
