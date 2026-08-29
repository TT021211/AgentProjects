package com.cuixing.sale.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.sale.entity.ErpBalance;
import com.cuixing.sale.service.ErpBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 科目余额表(ErpBalance)表控制层
 *
 * @author 阿江
 * @since 2025-07-14 20:38:31
 */
@Api(tags = "科目余额表接口")
@RestController
@RequestMapping("/sale/erpBalance")
public class ErpBalanceController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private ErpBalanceService erpBalanceService;

    /**
     * 分页查询
     *
     * @param erpBalance 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(ErpBalance erpBalance) {
        this.startPage();
        List<ErpBalance> list = this.erpBalanceService.selectErpBalanceList(erpBalance);
        return this.getDataTable(list);
    }
    
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.erpBalanceService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param erpBalance 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpBalance erpBalance) {
        return AjaxResult.success(this.erpBalanceService.insert(erpBalance));
    }

    /**
     * 编辑数据
     *
     * @param erpBalance 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpBalance erpBalance) {
        return AjaxResult.success(this.erpBalanceService.update(erpBalance));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Integer id) {
        return toAjax(this.erpBalanceService.deleteById(id));
    }

}

