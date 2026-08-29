package com.cuixing.purchase.controller;

import com.cuixing.purchase.entity.ProcureSource;
import com.cuixing.purchase.service.ProcureSourceService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

/**
 * 采购货源管理(ProcureSource)表控制层
 *
 * @author 阿江
 * @since 2025-03-04 15:08:21
 */
@Api(tags = "采购货源管理接口")
@RestController
@RequestMapping("/procure/procureSource")
public class ProcureSourceController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private ProcureSourceService procureSourceService;

    /**
     * 分页查询
     *
     * @param procureSource 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(ProcureSource procureSource) {
        this.startPage();
        List<ProcureSource> list = this.procureSourceService.selectProcureSourceList(procureSource);
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
        return AjaxResult.success(this.procureSourceService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param procureSource 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody ProcureSource procureSource) {
        return AjaxResult.success(this.procureSourceService.insert(procureSource));
    }

    /**
     * 编辑数据
     *
     * @param procureSource 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ProcureSource procureSource) {
        return AjaxResult.success(this.procureSourceService.update(procureSource));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.procureSourceService.deleteById(id));
    }

}

