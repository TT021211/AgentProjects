package com.cuixing.md.controller;

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
import com.cuixing.md.entity.MdIngredientsProductName;
import com.cuixing.md.service.IMdIngredientsProductNameService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 品名;(md_ingredients_product_name)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@Api(tags = "品名对象功能接口")
@RestController
@RequestMapping("/mdIngredientsProductName")
public class MdIngredientsProductNameController extends BaseController{
    @Autowired
    private IMdIngredientsProductNameService iMdIngredientsProductNameService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "分页查询",notes = "权限字符:md:productName:query")
    //@RequiresPermissions("md:productName:query")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdIngredientsProductNameService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param mdIngredientsProductName 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询",notes = "权限字符:md:productName:list")
    //@RequiresPermissions("md:productName:list")
    @GetMapping("/list")
    public TableDataInfo list(MdIngredientsProductName mdIngredientsProductName){
        startPage();
        List<MdIngredientsProductName> list =iMdIngredientsProductNameService.selectMdIngredientsProductNameList(mdIngredientsProductName);
        return getDataTable(list);
    }


     /**
      * 查询所有
      *
      * @return 实例对象
      */
     @ApiOperation(value = "查询所有",notes = "权限字符:md:productName:list")
     //@RequiresPermissions("md:productName:list")
     @GetMapping("/selectAll")
     public AjaxResult selectall(){
         return AjaxResult.success(iMdIngredientsProductNameService.queryAll());
     }

    /** 
     * 新增数据
     *
     * @param mdIngredientsProductName 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据",notes = "权限字符:md:productName:add")
    //@RequiresPermissions("md:productName:add")
    @Log(title = "品名", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdIngredientsProductName mdIngredientsProductName){
        return toAjax(iMdIngredientsProductNameService.insert(mdIngredientsProductName));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mdIngredientsProductName 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据",notes = "权限字符:md:productName:update")
    //@RequiresPermissions("md:productName:update")
    @Log(title = "品名", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdIngredientsProductName mdIngredientsProductName){
        return toAjax(iMdIngredientsProductNameService.update(mdIngredientsProductName));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据",notes = "权限字符:md:productName:remove")
    //@RequiresPermissions("md:productName:remove")
    @Log(title = "品名", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdIngredientsProductNameService.deleteByIds(ids));
    }
}
