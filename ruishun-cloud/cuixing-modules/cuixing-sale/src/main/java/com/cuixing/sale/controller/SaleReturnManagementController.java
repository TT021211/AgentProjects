package com.cuixing.sale.controller;

import com.cuixing.sale.entity.SaleReturnManagement;
import com.cuixing.sale.service.SaleReturnManagementService;
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
 * 销售退货管理(SaleReturnManagement)表控制层
 *
 * @author 阿江
 * @since 2025-03-04 15:10:39
 */
@Api(tags = "销售退货管理接口")
@RestController
@RequestMapping("/salesaleReturnManagement")
public class SaleReturnManagementController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private SaleReturnManagementService saleReturnManagementService;

    /**
     * 分页查询
     *
     * @param saleReturnManagement 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(SaleReturnManagement saleReturnManagement) {
        this.startPage();
        List<SaleReturnManagement> list = this.saleReturnManagementService.selectSaleReturnManagementList(saleReturnManagement);
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
        return AjaxResult.success(this.saleReturnManagementService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param saleReturnManagement 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody SaleReturnManagement saleReturnManagement) {
        return AjaxResult.success(this.saleReturnManagementService.insert(saleReturnManagement));
    }

    /**
     * 编辑数据
     *
     * @param saleReturnManagement 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SaleReturnManagement saleReturnManagement) {
        return AjaxResult.success(this.saleReturnManagementService.update(saleReturnManagement));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.saleReturnManagementService.deleteById(id));
    }

}

