package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldMaintainItem;
import com.cuixing.mold.service.MoldMaintainItemService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具保养表--保养设置表(MoldMaintainItem)表控制层
 *
 * @author makejava
 * @since 2024-06-25 16:52:04
 */
@Api(tags = "模具保养表--保养设置表接口")
@RestController
@RequestMapping("/mold/moldMaintainItem")
public class MoldMaintainItemController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldMaintainItemService moldMaintainItemService;

    /**
     * 分页查询
     *
     * @param moldMaintainItem 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldMaintainItem moldMaintainItem) {
        this.startPage();
        List<MoldMaintainItem> list = this.moldMaintainItemService.selectMoldMaintainItemList(moldMaintainItem);
        return this.getDataTable(list);
    }
    /**
     * 分页查询
     *
     * @param moldMaintainItem 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list2"})
    public TableDataInfo list2(MoldMaintainItem moldMaintainItem) {
        this.startPage();
        List<MoldMaintainItem> list = this.moldMaintainItemService.selectMoldMaintainItemList2(moldMaintainItem);
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
        return AjaxResult.success(this.moldMaintainItemService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldMaintainItem 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldMaintainItem moldMaintainItem) {
        return AjaxResult.success(this.moldMaintainItemService.insert(moldMaintainItem));
    }

    /**
     * 编辑数据
     *
     * @param moldMaintainItem 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldMaintainItem moldMaintainItem) {
        return AjaxResult.success(this.moldMaintainItemService.update(moldMaintainItem));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldMaintainItemService.deleteById(id));
    }

}

