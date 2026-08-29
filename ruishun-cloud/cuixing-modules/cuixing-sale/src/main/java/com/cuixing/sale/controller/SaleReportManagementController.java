package com.cuixing.sale.controller;

import com.cuixing.sale.entity.SaleReportManagement;
import com.cuixing.sale.service.SaleReportManagementService;
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
 * 销售报表分析(SaleReportManagement)表控制层
 *
 * @author 阿江
 * @since 2025-03-17 15:27:04
 */
@Api(tags = "销售报表分析接口")
@RestController
@RequestMapping("/sale/saleReportManagement")
public class SaleReportManagementController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private SaleReportManagementService saleReportManagementService;

    /**
     * 分页查询
     *
     * @param saleReportManagement 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(SaleReportManagement saleReportManagement) {
        this.startPage();
        List<SaleReportManagement> list = this.saleReportManagementService.selectSaleReportManagementList(saleReportManagement);
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
        return AjaxResult.success(this.saleReportManagementService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param saleReportManagement 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody SaleReportManagement saleReportManagement) {
        return AjaxResult.success(this.saleReportManagementService.insert(saleReportManagement));
    }

    /**
     * 编辑数据
     *
     * @param saleReportManagement 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SaleReportManagement saleReportManagement) {
        return AjaxResult.success(this.saleReportManagementService.update(saleReportManagement));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.saleReportManagementService.deleteById(id));
    }

}

