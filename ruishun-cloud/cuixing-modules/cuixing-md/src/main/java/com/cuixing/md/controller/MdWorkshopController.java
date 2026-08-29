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
import com.cuixing.md.entity.MdWorkshop;
import com.cuixing.md.service.IMdWorkshopService;
import java.math.BigInteger;
import java.util.List;

/**
 * 车间表;(md_workshop)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "车间表对象功能接口")
@RestController
@RequestMapping("/mdWorkshop")
public class MdWorkshopController extends BaseController {

    @Autowired
    private IMdWorkshopService mdWorkshopService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:md:workshop:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mdWorkshopService.checkMdWorkshopDataScope(id);
        return AjaxResult.success(mdWorkshopService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:md:workshop:export")
    @Log(title = "车间表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdWorkshop mdWorkshop)
    {
        List<MdWorkshop> list = mdWorkshopService.selectMdWorkshopList(mdWorkshop);
        ExcelUtil<MdWorkshop> util = new ExcelUtil<MdWorkshop>(MdWorkshop.class);
        util.exportExcel(response, list, "车间表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:md:workshop:list")
    @GetMapping("/list")
    public TableDataInfo list(MdWorkshop mdWorkshop)
    {
        startPage();
        List<MdWorkshop> list = mdWorkshopService.selectMdWorkshopList(mdWorkshop);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:md:workshop:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MdWorkshop mdWorkshop){
        List<MdWorkshop> list = mdWorkshopService.selectMdWorkshopList(mdWorkshop);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdWorkshop 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    //@RequiresPermissions("mes:md:workshop:add")
    @Log(title = "车间表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkshop mdWorkshop){
        return toAjax(mdWorkshopService.insert(mdWorkshop));
    }
    /**
     * 批量新增数据
     *
     * @param mdWorkshops 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:md:workshop:add")
    @Log(title = "车间表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MdWorkshop> mdWorkshops){
        return toAjax(mdWorkshopService.insertBatch(mdWorkshops));
    }

    /**
     * 更新数据
     *
     * @param mdWorkshop 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:md:workshop:edit")
    @Log(title = "车间表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkshop mdWorkshop){
        return toAjax(mdWorkshopService.update(mdWorkshop));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    //@RequiresPermissions("mes:md:workshop:delete")
    @Log(title = "车间表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mdWorkshopService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    //@RequiresPermissions("mes:md:workshop:delete")
    @Log(title = "车间表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mdWorkshopService.deleteByIds(ids));
    }
}
