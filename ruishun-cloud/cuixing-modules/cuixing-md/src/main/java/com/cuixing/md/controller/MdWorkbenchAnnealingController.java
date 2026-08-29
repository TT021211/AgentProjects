package com.cuixing.md.controller;

import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.md.entity.MdWorkbenchAnnealing;
import com.cuixing.md.entity.MdWorkbenchEnamelling;
import com.cuixing.md.service.MdWorkbenchAnnealingService;
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
 * 退火机台设置(MdWorkbenchAnnealing)表控制层
 *
 * @author 阿江
 * @since 2024-11-25 16:46:09
 */
@Api(tags = "退火机台设置接口")
@RestController
@RequestMapping("/mdWorkbenchAnnealing")
public class MdWorkbenchAnnealingController extends BaseController{
    /**
     * 服务对象
     */
    @Autowired
    private MdWorkbenchAnnealingService mdWorkbenchAnnealingService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(mdWorkbenchAnnealingService.queryById(id));
    }


    /**
     * 分页查询
     *
     * @param mdWorkbenchAnnealing 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MdWorkbenchAnnealing mdWorkbenchAnnealing){
        startPage();
        List<MdWorkbenchAnnealing> list =mdWorkbenchAnnealingService.selectMdWorkbenchAnnealingList(mdWorkbenchAnnealing);
        return getDataTable(list);
    }

    /**
     * 获取漆包机台列表
     *
     * @param mdWorkbenchAnnealing 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/selectAll")
    public AjaxResult selectAll(MdWorkbenchAnnealing mdWorkbenchAnnealing){
        List<MdWorkbenchAnnealing> list =mdWorkbenchAnnealingService.selectMdWorkbenchAnnealingList(mdWorkbenchAnnealing);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdWorkbenchAnnealing 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "机台设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkbenchAnnealing mdWorkbenchAnnealing){
        if(mdWorkbenchAnnealing.getProductionLowerLimit() > mdWorkbenchAnnealing.getProductionUpperLimit()){
            throw new RuntimeException("生产下限不能大于上限");
        }
        return toAjax(mdWorkbenchAnnealingService.insert(mdWorkbenchAnnealing));
    }


    /**
     * 更新数据
     *
     * @param mdWorkbenchAnnealing 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "机台设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkbenchAnnealing mdWorkbenchAnnealing){
        if(mdWorkbenchAnnealing.getProductionLowerLimit() > mdWorkbenchAnnealing.getProductionUpperLimit()){
            throw new RuntimeException("生产下限不能大于上限");
        }
        return toAjax(mdWorkbenchAnnealingService.update(mdWorkbenchAnnealing));
    }

    /**
     * 启用禁用
     *
     * @param mdWorkbenchAnnealing 实例对象
     * @return 实例对象
     */
    @ApiOperation("启用禁用")
    @Log(title = "机台设置", businessType = BusinessType.UPDATE)
    @PutMapping("/updateEnable")
    public AjaxResult updateEnable(@RequestBody MdWorkbenchAnnealing mdWorkbenchAnnealing){
        return toAjax(mdWorkbenchAnnealingService.updateEnable(mdWorkbenchAnnealing));
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
        return toAjax(mdWorkbenchAnnealingService.deleteByIds(ids));
    }

}

