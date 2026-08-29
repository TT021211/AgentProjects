package com.cuixing.md.controller;

import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.md.entity.MdWorkbenchAnnealing;
import com.cuixing.md.entity.MdWorkbenchDoublewire;
import com.cuixing.md.service.MdWorkbenchDoublewireService;
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
 * 并丝机台设置(MdWorkbenchDoublewire)表控制层
 *
 * @author 阿江
 * @since 2024-11-25 16:46:09
 */
@Api(tags = "并丝机台设置接口")
@RestController
@RequestMapping("mdWorkbenchDoublewire")
public class MdWorkbenchDoublewireController extends BaseController{
    /**
     * 服务对象
     */
    @Autowired
    private MdWorkbenchDoublewireService mdWorkbenchDoublewireService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(mdWorkbenchDoublewireService.queryById(id));
    }


    /**
     * 分页查询
     *
     * @param mdWorkbenchDoublewire 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MdWorkbenchDoublewire mdWorkbenchDoublewire){
        startPage();
        List<MdWorkbenchDoublewire> list =mdWorkbenchDoublewireService.selectMdWorkbenchDoublewireList(mdWorkbenchDoublewire);
        return getDataTable(list);
    }

    /**
     * 获取漆包机台列表
     *
     * @param mdWorkbenchDoublewire 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/selectAll")
    public AjaxResult selectAll(MdWorkbenchDoublewire mdWorkbenchDoublewire){
        List<MdWorkbenchDoublewire> list =mdWorkbenchDoublewireService.selectMdWorkbenchDoublewireList(mdWorkbenchDoublewire);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdWorkbenchDoublewire 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "机台设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkbenchDoublewire mdWorkbenchDoublewire){
        if(mdWorkbenchDoublewire.getProductionLowerLimit() > mdWorkbenchDoublewire.getProductionUpperLimit()){
            throw new RuntimeException("生产下限不能大于上限");
        }
        return toAjax(mdWorkbenchDoublewireService.insert(mdWorkbenchDoublewire));
    }


    /**
     * 更新数据
     *
     * @param mdWorkbenchDoublewire 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "机台设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkbenchDoublewire mdWorkbenchDoublewire){
        if(mdWorkbenchDoublewire.getProductionLowerLimit() > mdWorkbenchDoublewire.getProductionUpperLimit()){
            throw new RuntimeException("生产下限不能大于上限");
        }
        return toAjax(mdWorkbenchDoublewireService.update(mdWorkbenchDoublewire));
    }

    /**
     * 启用禁用
     *
     * @param mdWorkbenchDoublewire 实例对象
     * @return 实例对象
     */
    @ApiOperation("启用禁用")
    @Log(title = "机台设置", businessType = BusinessType.UPDATE)
    @PutMapping("/updateEnable")
    public AjaxResult updateEnable(@RequestBody MdWorkbenchDoublewire mdWorkbenchDoublewire){
        return toAjax(mdWorkbenchDoublewireService.updateEnable(mdWorkbenchDoublewire));
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
        return toAjax(mdWorkbenchDoublewireService.deleteByIds(ids));
    }

}

