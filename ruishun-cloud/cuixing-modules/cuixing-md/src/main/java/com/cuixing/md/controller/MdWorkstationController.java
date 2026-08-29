package com.cuixing.md.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.md.entity.vo.treeSelect;
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
import com.cuixing.md.entity.MdWorkstation;
import com.cuixing.md.service.IMdWorkstationService;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 机台表;(md_workstation)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "机台表对象功能接口")
@RestController
@RequestMapping("/mdWorkstation")
public class MdWorkstationController extends BaseController {

    @Autowired
    private IMdWorkstationService mdWorkstationService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:md:workstation:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mdWorkstationService.checkMdWorkstationDataScope(id);
        return AjaxResult.success(mdWorkstationService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:md:workstation:export")
    @Log(title = "机台表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdWorkstation mdWorkstation)
    {
        List<MdWorkstation> list = mdWorkstationService.selectMdWorkstationList(mdWorkstation);
        ExcelUtil<MdWorkstation> util = new ExcelUtil<MdWorkstation>(MdWorkstation.class);
        util.exportExcel(response, list, "机台表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:md:workstation:list")
    @GetMapping("/list")
    public TableDataInfo list(MdWorkstation mdWorkstation)
    {
        startPage();
        List<MdWorkstation> list = mdWorkstationService.selectMdWorkstationList(mdWorkstation);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:md:workstation:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MdWorkstation mdWorkstation){
        List<MdWorkstation> list = mdWorkstationService.selectMdWorkstationList(mdWorkstation);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdWorkstation 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    //@RequiresPermissions("mes:md:workstation:add")
    @Log(title = "机台表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkstation mdWorkstation){
        return toAjax(mdWorkstationService.insert(mdWorkstation));
    }
    /**
     * 批量新增数据
     *
     * @param mdWorkstations 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:md:workstation:add")
    @Log(title = "机台表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MdWorkstation> mdWorkstations){
        return toAjax(mdWorkstationService.insertBatch(mdWorkstations));
    }

    /**
     * 更新数据
     *
     * @param mdWorkstation 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:md:workstation:edit")
    @Log(title = "机台表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkstation mdWorkstation){
        return toAjax(mdWorkstationService.update(mdWorkstation));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    //@RequiresPermissions("mes:md:workstation:delete")
    @Log(title = "机台表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mdWorkstationService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    //@RequiresPermissions("mes:md:workstation:delete")
    @Log(title = "机台表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids) {
        return toAjax(mdWorkstationService.deleteByIds(ids));
    }

    /**
     * 构造前端使用的下拉列表
     *
     * @return 查询结果
     */
    @ApiOperation("构造下拉列表")
    @GetMapping("/tree")
    public TableDataInfo getTree()
    {
        List<treeSelect> workstationTree = mdWorkstationService.selectMdWorkstationList(null).stream().map(treeSelect::new).collect(Collectors.toList());
        return getDataTable(workstationTree);
    }
}
