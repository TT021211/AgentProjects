package com.cuixing.sale.controller;

import com.cuixing.sale.entity.SaleContractManage;
import com.cuixing.sale.service.SaleContractManageService;
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
 * 销售合同;(SaleContractManage)表控制层
 *
 * @author shrimp
 * @since 2025-02-26 10:51:35
 */
@Api(tags = "销售合同接口")
@RestController
@RequestMapping("/sale/saleContractManage")
public class SaleContractManageController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private SaleContractManageService saleContractManageService;

    /**
     * 分页查询
     *
     * @param saleContractManage 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping({"/list"})
    public TableDataInfo list(SaleContractManage saleContractManage) {
        this.startPage();
        List<SaleContractManage> list = this.saleContractManageService.selectSaleContractManageList(saleContractManage);
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
        return AjaxResult.success(this.saleContractManageService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param saleContractManage 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody SaleContractManage saleContractManage) {
        return AjaxResult.success(this.saleContractManageService.insert(saleContractManage));
    }

    /**
     * 编辑数据
     *
     * @param saleContractManage 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SaleContractManage saleContractManage) {
        return AjaxResult.success(this.saleContractManageService.update(saleContractManage));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.saleContractManageService.deleteById(id));
    }

}

