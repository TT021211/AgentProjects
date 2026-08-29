package com.cuixing.md.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.md.entity.MdWorkbenchWiredrawing;
import com.cuixing.md.entity.vo.MdWorkbenchWeighVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.md.entity.MdWorkbenchWeigh;
import com.cuixing.md.service.IMdWorkbenchWeighService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 称重机台设置;(md_workbench_weigh)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-8-2
 */
@Api(tags = "称重机台设置对象功能接口")
@RestController
@RequestMapping("/mdWorkbenchWeigh")
public class MdWorkbenchWeighController extends BaseController{
    @Autowired
    private IMdWorkbenchWeighService iMdWorkbenchWeighService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:md:mdWorkbenchWeigh:query")
    //@RequiresPermissions("md:mdWorkbenchWeigh:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdWorkbenchWeighService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param mdWorkbenchWeigh 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:md:mdWorkbenchWeigh:list")
    //@RequiresPermissions("md:mdWorkbenchWeigh:list")
    @GetMapping("/list")
    public TableDataInfo list(MdWorkbenchWeigh mdWorkbenchWeigh){
        startPage();
        List<MdWorkbenchWeigh> list =iMdWorkbenchWeighService.selectMdWorkbenchWeighList(mdWorkbenchWeigh);
        return getDataTable(list);
    }

     /**
      * 查询列表（添加content）
      *
      * @return 查询结果
      */
     @ApiOperation(value = "查询列表", notes = "权限字符:md:mdWorkbenchWeigh:list")
     //@RequiresPermissions("md:mdWorkbenchWeigh:list")
     @GetMapping("/selectAll")
     public AjaxResult list(){
         List<MdWorkbenchWeighVo> list = iMdWorkbenchWeighService.selectMdWorkbenchWeighAll();
         return AjaxResult.success(list);
     }
    
    /** 
     * 新增数据
     *
     * @param mdWorkbenchWeigh 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:md:mdWorkbenchWeigh:add")
    //@RequiresPermissions("md:mdWorkbenchWeigh:add")
    @Log(title = "称重机台设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkbenchWeigh mdWorkbenchWeigh){
        return toAjax(iMdWorkbenchWeighService.insert(mdWorkbenchWeigh));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mdWorkbenchWeigh 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:md:mdWorkbenchWeigh:update")
    //@RequiresPermissions("md:mdWorkbenchWeigh:update")
    @Log(title = "称重机台设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkbenchWeigh mdWorkbenchWeigh){
        return toAjax(iMdWorkbenchWeighService.update(mdWorkbenchWeigh));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:md:mdWorkbenchWeigh:remove")
    //@RequiresPermissions("md:mdWorkbenchWeigh:remove")
    @Log(title = "称重机台设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdWorkbenchWeighService.deleteByIds(ids));
    }

     /**
      * 启用禁用
      *
      * @param mdWorkbenchWeigh 实例对象
      * @return 实例对象
      */
     @ApiOperation("启用禁用")
     @Log(title = "称重机台设置", businessType = BusinessType.UPDATE)
     @PutMapping("/updateEnable")
     public AjaxResult updateEnable(@RequestBody MdWorkbenchWeigh mdWorkbenchWeigh){
         return toAjax(iMdWorkbenchWeighService.updateEnable(mdWorkbenchWeigh));
     }
}
