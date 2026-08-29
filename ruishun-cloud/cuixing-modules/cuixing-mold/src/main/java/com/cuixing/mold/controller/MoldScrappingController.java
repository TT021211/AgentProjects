package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldRestructuring;
import com.cuixing.mold.entity.MoldScrapping;
import com.cuixing.mold.service.MoldScrappingService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具报废表(MoldScrapping)表控制层
 *
 * @author makejava
 * @since 2024-06-25 14:06:50
 */
@Api(tags = "模具报废表接口")
@RestController
@RequestMapping("/mold/moldScrapping")
public class MoldScrappingController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldScrappingService moldScrappingService;

    /**
     * 分页查询
     *
     * @param moldScrapping 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldScrapping moldScrapping) {
        this.startPage();
        List<MoldScrapping> list = this.moldScrappingService.selectMoldScrappingList(moldScrapping);
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
        return AjaxResult.success(this.moldScrappingService.queryById(id));
    }

    /**
     * 确认
     *
     * @param moldScrapping 实体
     * @return 新增结果
     */
    @PostMapping("/confirm")
    public AjaxResult confirm(@RequestBody MoldScrapping moldScrapping) {
        return AjaxResult.success(this.moldScrappingService.confirm(moldScrapping));
    }
    /**
     * 新增数据
     *
     * @param moldScrapping 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldScrapping moldScrapping) {
        return AjaxResult.success(this.moldScrappingService.insert(moldScrapping));
    }

    /**
     * 编辑数据
     *
     * @param moldScrapping 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldScrapping moldScrapping) {
        return AjaxResult.success(this.moldScrappingService.update(moldScrapping));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldScrappingService.deleteById(id));
    }

}

