package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldSupplier;
import com.cuixing.mold.service.MoldSupplierService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具供应商表(MoldSupplier)表控制层
 *
 * @author makejava
 * @since 2024-06-25 14:06:51
 */
@Api(tags = "模具供应商表接口")
@RestController
@RequestMapping("/mold/moldSupplier")
public class MoldSupplierController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldSupplierService moldSupplierService;

    /**
     * 分页查询
     *
     * @param moldSupplier 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldSupplier moldSupplier) {
        this.startPage();
        List<MoldSupplier> list = this.moldSupplierService.selectMoldSupplierList(moldSupplier);
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
        return AjaxResult.success(this.moldSupplierService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldSupplier 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldSupplier moldSupplier) {
        return AjaxResult.success(this.moldSupplierService.insert(moldSupplier));
    }

    /**
     * 编辑数据
     *
     * @param moldSupplier 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldSupplier moldSupplier) {
        return AjaxResult.success(this.moldSupplierService.update(moldSupplier));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return toAjax(this.moldSupplierService.deleteById(id));
    }

}

