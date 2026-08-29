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
import com.cuixing.md.entity.MdIngredientsManufacturer;
import com.cuixing.md.service.IMdIngredientsManufacturerService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 材料厂家;(md_ingredients_manufacturer)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-4
 */
@Api(tags = "材料厂家对象功能接口")
@RestController
@RequestMapping("/mdIngredientsManufacturer")
public class MdIngredientsManufacturerController extends BaseController{
    @Autowired
    private IMdIngredientsManufacturerService iMdIngredientsManufacturerService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据",notes = "权限字符:md:manufacturer:query")
    //@RequiresPermissions("md:manufacturer:query")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdIngredientsManufacturerService.queryById(id));
    }

    /**
     * 分页查询
     *
     * @param mdIngredientsManufacturer 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询",notes = "权限字符:md:manufacturer:list")
    //@RequiresPermissions("md:manufacturer:list")
    @GetMapping("/list")
    public TableDataInfo list(MdIngredientsManufacturer mdIngredientsManufacturer){
        startPage();
        List<MdIngredientsManufacturer> list =iMdIngredientsManufacturerService.selectMdIngredientsManufacturerList(mdIngredientsManufacturer);
        return getDataTable(list);
    }
     /**
      * 通过ID查询单条数据
      *
      * @return 实例对象
      */
     @ApiOperation(value = "查询列表",notes = "权限字符:md:manufacturer:list")
     //@RequiresPermissions("md:manufacturer:list")
     @GetMapping("/selectAll")
     public AjaxResult selectall(){
         return AjaxResult.success(iMdIngredientsManufacturerService.queryAll());
     }
    /**
     * 新增数据
     *
     * @param mdIngredientsManufacturer 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据",notes = "权限字符:md:manufacturer:add")
    //@RequiresPermissions("md:manufacturer:add")
    @Log(title = "材料厂家", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdIngredientsManufacturer mdIngredientsManufacturer){
        return toAjax(iMdIngredientsManufacturerService.insert(mdIngredientsManufacturer));
    }


    /**
     * 更新数据
     *
     * @param mdIngredientsManufacturer 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据",notes = "权限字符:md:manufacturer:update")
    //@RequiresPermissions("md:manufacturer:update")
    @Log(title = "材料厂家", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdIngredientsManufacturer mdIngredientsManufacturer){
        return toAjax(iMdIngredientsManufacturerService.update(mdIngredientsManufacturer));
    }


    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据",notes = "权限字符:md:manufacturer:remove")
    //@RequiresPermissions("md:manufacturer:remove")
    @Log(title = "材料厂家", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdIngredientsManufacturerService.deleteByIds(ids));
    }
}
