package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldMaintainPlan;
import com.cuixing.mold.mapper.MoldMaintainListMapper;
import com.cuixing.mold.mapper.MoldNestingMapper;
import com.cuixing.mold.service.MoldMaintainPlanService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具保养表--保养计划表(MoldMaintainPlan)表控制层
 *
 * @author makejava
 * @since 2024-06-25 16:52:04
 */
@Api(tags = "模具保养表--保养计划表接口")
@RestController
@RequestMapping("/mold/moldMaintainPlan")
public class MoldMaintainPlanController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldMaintainPlanService moldMaintainPlanService;

    /**
     * 分页查询
     *
     * @param moldMaintainPlan 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldMaintainPlan moldMaintainPlan) {
        this.startPage();
        List<MoldMaintainPlan> list = this.moldMaintainPlanService.selectMoldMaintainPlanList(moldMaintainPlan);
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
        return AjaxResult.success(this.moldMaintainPlanService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldMaintainPlan 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldMaintainPlan moldMaintainPlan) {
        if ("1".equals(this.moldMaintainPlanService.checkUniquePlanCode(moldMaintainPlan))) {
            return AjaxResult.error("计划编号已存在！");
        } else {
            MoldMaintainPlan maintainPlan = this.moldMaintainPlanService.insert(moldMaintainPlan);
            MoldMaintainPlan mp = new MoldMaintainPlan();
            mp = moldMaintainPlan;
            mp.setMaintainPlanId(maintainPlan.getMaintainPlanId());
            this.moldMaintainPlanService.generateAndInsertInspectionDates(mp);
            return AjaxResult.success(maintainPlan);
        }
    }

    /**
     * 编辑数据
     *
     * @param moldMaintainPlan 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldMaintainPlan moldMaintainPlan) {
        if ("1".equals(this.moldMaintainPlanService.checkUniquePlanCode(moldMaintainPlan))) {
            return AjaxResult.error("计划编号已存在！");
        } else {
            return AjaxResult.success(this.moldMaintainPlanService.update(moldMaintainPlan));
        }
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return AjaxResult.success(this.moldMaintainPlanService.deleteById(id));
    }

}

