package com.cuixing.sale.controller;

import com.cuixing.sale.entity.SalePriceManagement;
import com.cuixing.sale.service.SalePriceManagementService;
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
 * 销售价格管理(SalePriceManagement)表控制层
 *
 * @author 阿江
 * @since 2025-03-04 15:10:39
 */
@Api(tags = "销售价格管理接口")
@RestController
@RequestMapping("/sale/salePriceManagement")
public class SalePriceManagementController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private SalePriceManagementService salePriceManagementService;

    /**
     * 分页查询
     *
     * @param salePriceManagement 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(SalePriceManagement salePriceManagement) {
        this.startPage();
        List<SalePriceManagement> list = this.salePriceManagementService.selectSalePriceManagementList(salePriceManagement);
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
        return AjaxResult.success(this.salePriceManagementService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param salePriceManagement 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody SalePriceManagement salePriceManagement) {
        return AjaxResult.success(this.salePriceManagementService.insert(salePriceManagement));
    }

    /**
     * 编辑数据
     *
     * @param salePriceManagement 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SalePriceManagement salePriceManagement) {
        return AjaxResult.success(this.salePriceManagementService.update(salePriceManagement));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.salePriceManagementService.deleteById(id));
    }

}

