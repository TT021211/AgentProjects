package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldReturned;
import com.cuixing.mold.service.MoldReturnedService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

/**
 * 模具待归还表(MoldReturned)表控制层
 *
 * @author makejava
 * @since 2024-06-26 17:26:10
 */
@Api(tags = "模具待归还表接口")
@RestController
@RequestMapping("/mold/moldReturned")
public class MoldReturnedController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private MoldReturnedService moldReturnedService;

    /**
     * 分页查询
     *
     * @param moldReturned 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldReturned moldReturned) {
        this.startPage();
        List<MoldReturned> list = this.moldReturnedService.selectMoldReturnedList(moldReturned);
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
        return AjaxResult.success(this.moldReturnedService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldReturned 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldReturned moldReturned) {
        return AjaxResult.success(this.moldReturnedService.insert(moldReturned));
    }
    /**
     * 确认归还
     *
     * @param moldReturned 实体
     * @return 新增结果
     */
    @PostMapping("/confirm")
    public AjaxResult confirm(@RequestBody MoldReturned moldReturned) {

        return AjaxResult.success(this.moldReturnedService.confirm(moldReturned));
    }

    /**
     * 编辑数据
     *
     * @param moldReturned 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldReturned moldReturned) {
        return AjaxResult.success(this.moldReturnedService.update(moldReturned));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldReturnedService.deleteById(id));
    }

}

