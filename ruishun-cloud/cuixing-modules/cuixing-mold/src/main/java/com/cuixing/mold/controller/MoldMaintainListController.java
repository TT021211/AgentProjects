package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldMaintainList;
import com.cuixing.mold.service.MoldMaintainListService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

/**
 * 待保养列表(MoldMaintainList)表控制层
 *
 * @author makejava
 * @since 2024-06-26 11:54:04
 */
@Api(tags = "待保养列表接口")
@RestController
@RequestMapping("/mold/moldMaintainList")
public class MoldMaintainListController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private MoldMaintainListService moldMaintainListService;

    /**
     * 分页查询
     *
     * @param moldMaintainList 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldMaintainList moldMaintainList) {
        this.startPage();
        List<MoldMaintainList> list = this.moldMaintainListService.selectMoldMaintainListList(moldMaintainList);
        return this.getDataTable(list);
    }
    /**
     * 保养
     *
     * @param moldMaintainList 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/maintain"})
    public AjaxResult maintain(MoldMaintainList moldMaintainList) {
        return AjaxResult.success(this.moldMaintainListService.updateStatus(moldMaintainList));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return AjaxResult.success(this.moldMaintainListService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldMaintainList 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldMaintainList moldMaintainList) {
        return AjaxResult.success(this.moldMaintainListService.insert(moldMaintainList));
    }

    /**
     * 编辑数据
     *
     * @param moldMaintainList 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldMaintainList moldMaintainList) {
        return AjaxResult.success(this.moldMaintainListService.update(moldMaintainList));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldMaintainListService.deleteById(id));
    }

}

