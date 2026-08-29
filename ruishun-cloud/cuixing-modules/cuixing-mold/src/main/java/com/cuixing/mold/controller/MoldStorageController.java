package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldStorage;
import com.cuixing.mold.service.MoldStorageService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具入库表(MoldStorage)表控制层
 *
 * @author makejava
 * @since 2024-06-25 14:06:51
 */
@Api(tags = "模具入库表接口")
@RestController
@RequestMapping("/mold/moldStorage")
public class MoldStorageController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldStorageService moldStorageService;

    /**
     * 分页查询
     *
     * @param moldStorage 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldStorage moldStorage) {
        this.startPage();
        List<MoldStorage> list = this.moldStorageService.selectMoldStorageList(moldStorage);
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
        return AjaxResult.success(this.moldStorageService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldStorage 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldStorage moldStorage) {
        return AjaxResult.success(this.moldStorageService.insert(moldStorage));
    }
    /**
     * 新增数据
     *
     * @param moldStorage 实体
     * @return 新增结果
     */
    @PostMapping("/confirm")
    public AjaxResult confirm(@RequestBody MoldStorage moldStorage) {
        return AjaxResult.success(this.moldStorageService.confirm(moldStorage));
    }

    /**
     * 编辑数据
     *
     * @param moldStorage 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldStorage moldStorage) {
        return AjaxResult.success(this.moldStorageService.update(moldStorage));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldStorageService.deleteById(id));
    }

}

