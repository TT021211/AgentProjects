package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldOutbound;
import com.cuixing.mold.service.MoldOutboundService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具出库表(MoldOutbound)表控制层
 *
 * @author makejava
 * @since 2024-06-26 16:52:49
 */
@Api(tags = "模具出库表接口")
@RestController
@RequestMapping("/mold/moldOutbound")
public class MoldOutboundController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldOutboundService moldOutboundService;

    /**
     * 分页查询
     *
     * @param moldOutbound 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldOutbound moldOutbound) {
        this.startPage();
        List<MoldOutbound> list = this.moldOutboundService.selectMoldOutboundList(moldOutbound);
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
        return AjaxResult.success(this.moldOutboundService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldOutbound 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldOutbound moldOutbound) {
        return AjaxResult.success(this.moldOutboundService.insert(moldOutbound));
    }
    /**
     * 确认出库
     *
     * @param moldOutbound 实体
     * @return 新增结果
     */
    @PostMapping("/confirm")
    public AjaxResult confirm(@RequestBody MoldOutbound moldOutbound) {
        return AjaxResult.success(this.moldOutboundService.confirm(moldOutbound));
    }

    /**
     * 编辑数据
     *
     * @param moldOutbound 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldOutbound moldOutbound) {
        return AjaxResult.success(this.moldOutboundService.update(moldOutbound));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldOutboundService.deleteById(id));
    }

}

