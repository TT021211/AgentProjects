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
import com.cuixing.md.entity.MdUnitMeasure;
import com.cuixing.md.service.IMdUnitMeasureService;
import java.math.BigInteger;
import java.util.List;

/**
 * 单位表;(md_unit_measure)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "单位表对象功能接口")
@RestController
@RequestMapping("/mdUnitMeasure")
public class MdUnitMeasureController extends BaseController {

    @Autowired
    private IMdUnitMeasureService mdUnitMeasureService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:md:measure:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mdUnitMeasureService.checkMdUnitMeasureDataScope(id);
        return AjaxResult.success(mdUnitMeasureService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:md:measure:export")
    @Log(title = "单位表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdUnitMeasure mdUnitMeasure)
    {
        List<MdUnitMeasure> list = mdUnitMeasureService.selectMdUnitMeasureList(mdUnitMeasure);
        ExcelUtil<MdUnitMeasure> util = new ExcelUtil<MdUnitMeasure>(MdUnitMeasure.class);
        util.exportExcel(response, list, "单位表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:md:measure:list")
    @GetMapping("/list")
    public TableDataInfo list(MdUnitMeasure mdUnitMeasure)
    {
        startPage();
        List<MdUnitMeasure> list = mdUnitMeasureService.selectMdUnitMeasureList(mdUnitMeasure);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:md:measure:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MdUnitMeasure mdUnitMeasure){
        List<MdUnitMeasure> list = mdUnitMeasureService.selectMdUnitMeasureList(mdUnitMeasure);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdUnitMeasure 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    //@RequiresPermissions("mes:md:measure:add")
    @Log(title = "单位表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdUnitMeasure mdUnitMeasure){
        return toAjax(mdUnitMeasureService.insert(mdUnitMeasure));
    }
    /**
     * 批量新增数据
     *
     * @param mdUnitMeasures 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:md:measure:add")
    @Log(title = "单位表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MdUnitMeasure> mdUnitMeasures){
        return toAjax(mdUnitMeasureService.insertBatch(mdUnitMeasures));
    }

    /**
     * 更新数据
     *
     * @param mdUnitMeasure 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:md:measure:edit")
    @Log(title = "单位表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdUnitMeasure mdUnitMeasure){
        return toAjax(mdUnitMeasureService.update(mdUnitMeasure));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    //@RequiresPermissions("mes:md:measure:delete")
    @Log(title = "单位表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mdUnitMeasureService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    //@RequiresPermissions("mes:md:measure:delete")
    @Log(title = "单位表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mdUnitMeasureService.deleteByIds(ids));
    }
}
