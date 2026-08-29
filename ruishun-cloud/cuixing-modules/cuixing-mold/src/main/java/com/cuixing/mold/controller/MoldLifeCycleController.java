package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldLifeCycle;
import com.cuixing.mold.service.MoldLifeCycleService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具生命周期表(MoldLifeCycle)表控制层
 *
 * @author makejava
 * @since 2024-06-27 10:46:36
 */
@Api(tags = "模具生命周期表接口")
@RestController
@RequestMapping("/mold/moldLifeCycle")
public class MoldLifeCycleController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldLifeCycleService moldLifeCycleService;

    /**
     * 分页查询
     *
     * @param moldLifeCycle 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldLifeCycle moldLifeCycle) {
        this.startPage();
        List<MoldLifeCycle> list = this.moldLifeCycleService.selectMoldLifeCycleList(moldLifeCycle);
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
        return AjaxResult.success(this.moldLifeCycleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldLifeCycle 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldLifeCycle moldLifeCycle) {
        return AjaxResult.success(this.moldLifeCycleService.insert(moldLifeCycle));
    }

    /**
     * 编辑数据
     *
     * @param moldLifeCycle 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldLifeCycle moldLifeCycle) {
        return AjaxResult.success(this.moldLifeCycleService.update(moldLifeCycle));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldLifeCycleService.deleteById(id));
    }

}

