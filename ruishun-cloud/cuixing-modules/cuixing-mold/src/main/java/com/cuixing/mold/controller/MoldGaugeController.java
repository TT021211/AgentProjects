package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldGauge;
import com.cuixing.mold.service.MoldGaugeService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具换规表(MoldGauge)表控制层
 *
 * @author makejava
 * @since 2024-06-25 14:06:45
 */
@Api(tags = "模具换规表接口")
@RestController
@RequestMapping("/mold/moldGauge")
public class MoldGaugeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldGaugeService moldGaugeService;

    /**
     * 分页查询
     *
     * @param moldGauge 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldGauge moldGauge) {
        this.startPage();
        List<MoldGauge> list = this.moldGaugeService.selectMoldGaugeList(moldGauge);
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
        return AjaxResult.success(this.moldGaugeService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldGauge 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldGauge moldGauge) {
        return AjaxResult.success(this.moldGaugeService.insert(moldGauge));
    }
    /**
     * 新增数据
     *
     * @param moldGauge 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public AjaxResult add2(@RequestBody MoldGauge moldGauge) {
        return AjaxResult.success(this.moldGaugeService.insert(moldGauge));
    }
    /**
     * 批量新增模具规格
     * @param moldGauges 模具规格列表
     * @return 操作结果
     */
    @PostMapping("/addBatch")
    public AjaxResult addBatch(@RequestBody List<MoldGauge> moldGauges) {
        if (moldGauges == null || moldGauges.isEmpty()) {
            return AjaxResult.error("数据不能为空");
        }
        boolean result = moldGaugeService.addMoldGaugeBatch(moldGauges);
        return result ? AjaxResult.success() : AjaxResult.error("批量新增失败");
    }

    /**
     * 编辑数据
     *
     * @param moldGauge 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody MoldGauge moldGauge) {
        return AjaxResult.success(this.moldGaugeService.update(moldGauge));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldGaugeService.deleteById(id));
    }

}

