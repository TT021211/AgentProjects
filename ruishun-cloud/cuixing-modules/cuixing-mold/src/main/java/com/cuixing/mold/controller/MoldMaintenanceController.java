package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldMaintenance;
import com.cuixing.mold.service.MoldMaintenanceService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具维修保养表(MoldMaintenance)表控制层
 *
 * @author makejava
 * @since 2024-07-01 11:01:19
 */
@Api(tags = "模具维修保养表接口")
@RestController
@RequestMapping("/mold/moldMaintenance")
public class MoldMaintenanceController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldMaintenanceService moldMaintenanceService;

    /**
     * 分页查询
     *
     * @param moldMaintenance 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldMaintenance moldMaintenance) {
        this.startPage();
        List<MoldMaintenance> list = this.moldMaintenanceService.selectMoldMaintenanceList(moldMaintenance);
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
        return AjaxResult.success(this.moldMaintenanceService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldMaintenance 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldMaintenance moldMaintenance) {
        return AjaxResult.success(this.moldMaintenanceService.insert(moldMaintenance));
    }
    /**
     * 入库
     *
     * @param moldMaintenance 实体
     * @return 新增结果
     */
    @PostMapping("/warehousing")
    public AjaxResult warehousing(@RequestBody MoldMaintenance moldMaintenance) {
        return AjaxResult.success(this.moldMaintenanceService.warehousing(moldMaintenance));
    }

    /**
     * 编辑数据
     *
     * @param moldMaintenance 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldMaintenance moldMaintenance) {
        return AjaxResult.success(this.moldMaintenanceService.update(moldMaintenance));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldMaintenanceService.deleteById(id));
    }

}

