package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldClassification;
import com.cuixing.mold.service.MoldClassificationService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具分类表(MoldClassification)表控制层
 *
 * @author makejava
 * @since 2024-06-26 11:04:31
 */
@Api(tags = "模具分类表接口")
@RestController
@RequestMapping("/mold/moldClassification")
public class MoldClassificationController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldClassificationService moldClassificationService;

    /**
     * 分页查询
     *
     * @param moldClassification 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldClassification moldClassification) {
        this.startPage();
        List<MoldClassification> list = this.moldClassificationService.selectMoldClassificationList(moldClassification);
        return this.getDataTable(list);
    }
    /**
     * 分页查询
     *
     * @param moldClassification 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list2"})
    public TableDataInfo list2(MoldClassification moldClassification) {
        this.startPage();
        List<MoldClassification> list = this.moldClassificationService.selectMoldClassificationList2(moldClassification);
        return this.getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return AjaxResult.success(this.moldClassificationService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldClassification 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldClassification moldClassification) {
        return AjaxResult.success(this.moldClassificationService.insert(moldClassification));
    }

    /**
     * 编辑数据
     *
     * @param moldClassification 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldClassification moldClassification) {
        return AjaxResult.success(this.moldClassificationService.update(moldClassification));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldClassificationService.deleteById(id));
    }

}

