package com.cuixing.md.controller;

import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.md.entity.MdWorkbenchAnnealing;
import com.cuixing.md.entity.MdWorkbenchTinning;
import com.cuixing.md.service.MdWorkbenchTinningService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;

/**
 * 镀锡机台设置(MdWorkbenchTinning)表控制层
 *
 * @author 阿江
 * @since 2024-11-25 16:46:10
 */
@Api(tags = "镀锡机台设置接口")
@RestController
@RequestMapping("mdWorkbenchTinning")
public class MdWorkbenchTinningController extends BaseController{
    /**
     * 服务对象
     */
    @Autowired
    private MdWorkbenchTinningService mdWorkbenchTinningService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(mdWorkbenchTinningService.queryById(id));
    }


    /**
     * 分页查询
     *
     * @param mdWorkbenchTinning 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MdWorkbenchTinning mdWorkbenchTinning){
        startPage();
        List<MdWorkbenchTinning> list =mdWorkbenchTinningService.selectMdWorkbenchTinningList(mdWorkbenchTinning);
        return getDataTable(list);
    }

    /**
     * 获取漆包机台列表
     *
     * @param mdWorkbenchTinning 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/selectAll")
    public AjaxResult selectAll(MdWorkbenchTinning mdWorkbenchTinning){
        List<MdWorkbenchTinning> list =mdWorkbenchTinningService.selectMdWorkbenchTinningList(mdWorkbenchTinning);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdWorkbenchTinning 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "机台设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkbenchTinning mdWorkbenchTinning){
        if(mdWorkbenchTinning.getProductionLowerLimit() > mdWorkbenchTinning.getProductionUpperLimit()){
            throw new RuntimeException("生产下限不能大于上限");
        }
        return toAjax(mdWorkbenchTinningService.insert(mdWorkbenchTinning));
    }


    /**
     * 更新数据
     *
     * @param mdWorkbenchTinning 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "机台设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkbenchTinning mdWorkbenchTinning){
        if(mdWorkbenchTinning.getProductionLowerLimit() > mdWorkbenchTinning.getProductionUpperLimit()){
            throw new RuntimeException("生产下限不能大于上限");
        }
        return toAjax(mdWorkbenchTinningService.update(mdWorkbenchTinning));
    }

    /**
     * 启用禁用
     *
     * @param mdWorkbenchTinning 实例对象
     * @return 实例对象
     */
    @ApiOperation("启用禁用")
    @Log(title = "机台设置", businessType = BusinessType.UPDATE)
    @PutMapping("/updateEnable")
    public AjaxResult updateEnable(@RequestBody MdWorkbenchTinning mdWorkbenchTinning){
        return toAjax(mdWorkbenchTinningService.updateEnable(mdWorkbenchTinning));
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
        return toAjax(mdWorkbenchTinningService.deleteByIds(ids));
    }

}

