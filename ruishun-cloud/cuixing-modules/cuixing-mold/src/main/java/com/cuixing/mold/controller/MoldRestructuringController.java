package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldRestructuring;
import com.cuixing.mold.service.MoldRestructuringService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具改制表(MoldRestructuring)表控制层
 *
 * @author makejava
 * @since 2024-06-25 14:06:49
 */
@Api(tags = "模具改制表接口")
@RestController
@RequestMapping("/mold/moldRestructuring")
public class MoldRestructuringController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldRestructuringService moldRestructuringService;

    /**
     * 分页查询
     *
     * @param moldRestructuring 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldRestructuring moldRestructuring) {
        this.startPage();
        List<MoldRestructuring> list = this.moldRestructuringService.selectMoldRestructuringList(moldRestructuring);
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
        return AjaxResult.success(this.moldRestructuringService.queryById(id));
    }

    /**
     * 确认
     *
     * @param moldRestructuring 实体
     * @return 新增结果
     */
    @PostMapping("/confirm")
    public AjaxResult confirm(@RequestBody MoldRestructuring moldRestructuring) {
        return AjaxResult.success(this.moldRestructuringService.confirm(moldRestructuring));
    }

    /**
     * 新增数据
     *
     * @param moldRestructuring 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldRestructuring moldRestructuring) {
        return AjaxResult.success(this.moldRestructuringService.insert(moldRestructuring));
    }

    /**
     * 编辑数据
     *
     * @param moldRestructuring 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldRestructuring moldRestructuring) {
        return AjaxResult.success(this.moldRestructuringService.update(moldRestructuring));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldRestructuringService.deleteById(id));
    }

}

