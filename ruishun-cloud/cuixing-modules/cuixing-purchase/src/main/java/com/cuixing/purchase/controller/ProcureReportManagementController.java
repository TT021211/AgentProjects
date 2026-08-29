package com.cuixing.purchase.controller;

import com.cuixing.purchase.entity.ProcureReportManagement;
import com.cuixing.purchase.service.ProcureReportManagementService;
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
 * 采购报表分析(ProcureReportManagement)表控制层
 *
 * @author 阿江
 * @since 2025-03-19 13:42:31
 */
@Api(tags = "采购报表分析接口")
@RestController
@RequestMapping("procureReportManagement")
public class ProcureReportManagementController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private ProcureReportManagementService procureReportManagementService;

    /**
     * 分页查询
     *
     * @param procureReportManagement 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(ProcureReportManagement procureReportManagement) {
        this.startPage();
        List<ProcureReportManagement> list = this.procureReportManagementService.selectProcureReportManagementList(procureReportManagement);
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
        return AjaxResult.success(this.procureReportManagementService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param procureReportManagement 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody ProcureReportManagement procureReportManagement) {
        return AjaxResult.success(this.procureReportManagementService.insert(procureReportManagement));
    }

    /**
     * 编辑数据
     *
     * @param procureReportManagement 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ProcureReportManagement procureReportManagement) {
        return AjaxResult.success(this.procureReportManagementService.update(procureReportManagement));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.procureReportManagementService.deleteById(id));
    }

}

