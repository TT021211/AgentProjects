package com.cuixing.mes.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.security.utils.SecurityUtils;
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
import com.cuixing.mes.entity.MesWorkstage;
import com.cuixing.mes.entity.vo.treeSelect;
import java.util.stream.Collectors;
import com.cuixing.mes.service.IMesWorkstageService;
import java.math.BigInteger;
import java.util.List;

/**
 * 工序表;(mes_workstage)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Api(tags = "工序表对象功能接口")
@RestController
@RequestMapping("/mesWorkstage")
public class MesWorkstageController extends BaseController {

    @Autowired
    private IMesWorkstageService mesWorkstageService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
//    @RequiresPermissions("mes:workstage:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mesWorkstageService.checkMesWorkstageDataScope(id);
        return AjaxResult.success(mesWorkstageService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
//    @RequiresPermissions("mes:workstage:export")
    @Log(title = "工序表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MesWorkstage mesWorkstage)
    {
        List<MesWorkstage> list = mesWorkstageService.selectMesWorkstageList(mesWorkstage);
        ExcelUtil<MesWorkstage> util = new ExcelUtil<MesWorkstage>(MesWorkstage.class);
        util.exportExcel(response, list, "工序表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
//    @RequiresPermissions("mes:workstage:list")
    @GetMapping("/list")
    public TableDataInfo list(MesWorkstage mesWorkstage)
    {
        startPage();
        List<MesWorkstage> list = mesWorkstageService.selectMesWorkstageList(mesWorkstage);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
//    @RequiresPermissions("mes:workstage:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MesWorkstage mesWorkstage){
        List<MesWorkstage> list = mesWorkstageService.selectMesWorkstageList(mesWorkstage);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mesWorkstage 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
//    @RequiresPermissions("mes:workstage:add")
    @Log(title = "工序表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesWorkstage mesWorkstage){
        mesWorkstage.setCreateBy(SecurityUtils.getUsername());
        BigInteger i = mesWorkstageService.insert(mesWorkstage);
        /** insert返回为last_insert_id，判断是否为正，
         * signum方法返回一个表示BigInteger值符号的int值：
         * 如果这个值为零返回0，如果这个值为正返回1，如果这个值为负返回-1。
         */
        int signum = i.signum();
        return toAjax(signum);
    }

    /**
     * 批量新增数据
     *
     * @param mesWorkstages 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
//    @RequiresPermissions("mes:workstage:add")
    @Log(title = "工序表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MesWorkstage> mesWorkstages){
        return toAjax(mesWorkstageService.insertBatch(mesWorkstages));
    }

    /**
     * 更新数据
     *
     * @param mesWorkstage 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
//    @RequiresPermissions("mes:workstage:edit")
    @Log(title = "工序表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesWorkstage mesWorkstage){
        return toAjax(mesWorkstageService.update(mesWorkstage));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
//    @RequiresPermissions("mes:workstage:delete")
    @Log(title = "工序表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mesWorkstageService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
//    @RequiresPermissions("mes:workstage:delete")
    @Log(title = "工序表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mesWorkstageService.deleteByIds(ids));
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
        List<treeSelect> mesWorkstageTree = mesWorkstageService.selectMesWorkstageList(null).stream().map(treeSelect::new).collect(Collectors.toList());
        return getDataTable(mesWorkstageTree);
    }
}