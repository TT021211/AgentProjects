package com.cuixing.md.controller;

import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.log.annotation.Log;
import javax.servlet.http.HttpServletResponse;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.md.entity.MdItemType;
import com.cuixing.md.service.IMdItemTypeService;
import java.math.BigInteger;
import java.util.List;

/**
 * 物料分类表;(md_item_type)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "物料分类表对象功能接口")
@RestController
@RequestMapping("/mdItemType")
public class MdItemTypeController extends BaseController {

    @Autowired
    private IMdItemTypeService mdItemTypeService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:md:item_type:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mdItemTypeService.checkMdItemTypeDataScope(id);
        return AjaxResult.success(mdItemTypeService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:md:item_type:export")
    @Log(title = "物料分类表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdItemType mdItemType)
    {
        List<MdItemType> list = mdItemTypeService.selectMdItemTypeList(mdItemType);
        ExcelUtil<MdItemType> util = new ExcelUtil<MdItemType>(MdItemType.class);
        util.exportExcel(response, list, "物料分类表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:md:item_type:list")
    @GetMapping("/list")
    public TableDataInfo list(MdItemType mdItemType)
    {
        startPage();
        List<MdItemType> list = mdItemTypeService.selectMdItemTypeList(mdItemType);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:md:item_type:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MdItemType mdItemType){
        List<MdItemType> list = mdItemTypeService.selectMdItemTypeList(mdItemType);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdItemType 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    //@RequiresPermissions("mes:md:item_type:add")
    @Log(title = "物料分类表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdItemType mdItemType){
        return toAjax(mdItemTypeService.insert(mdItemType));
    }
    /**
     * 批量新增数据
     *
     * @param mdItemTypes 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:md:item_type:add")
    @Log(title = "物料分类表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MdItemType> mdItemTypes){
        return toAjax(mdItemTypeService.insertBatch(mdItemTypes));
    }

    /**
     * 更新数据
     *
     * @param mdItemType 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:md:item_type:edit")
    @Log(title = "物料分类表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdItemType mdItemType){
        return toAjax(mdItemTypeService.update(mdItemType));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    //@RequiresPermissions("mes:md:item_type:delete")
    @Log(title = "物料分类表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        if(mdItemTypeService.checkHasChildren(id))
        {
            return error("该分类下含有子节点，不允许删除");
        }
        return toAjax(mdItemTypeService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    //@RequiresPermissions("mes:md:item_type:delete")
    @Log(title = "物料分类表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        if(mdItemTypeService.checkHasChildren(ids))
        {
            return error("该分类下含有子节点，不允许删除");
        }
        return toAjax(mdItemTypeService.deleteByIds(ids));
    }
}
