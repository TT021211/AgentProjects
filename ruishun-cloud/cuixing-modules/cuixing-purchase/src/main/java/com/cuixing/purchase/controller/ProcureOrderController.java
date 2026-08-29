package com.cuixing.purchase.controller;

import com.cuixing.purchase.entity.ProcureOrder;
import com.cuixing.purchase.service.ProcureOrderService;
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
 * 采购订单(ProcureOrder)表控制层
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Api(tags = "采购订单接口")
@RestController
@RequestMapping("/procure/procureOrder")
public class ProcureOrderController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private ProcureOrderService procureOrderService;

    /**
     * 分页查询
     *
     * @param procureOrder 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(ProcureOrder procureOrder) {
        this.startPage();
        List<ProcureOrder> list = this.procureOrderService.selectProcureOrderList(procureOrder);
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
        return AjaxResult.success(this.procureOrderService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param procureOrder 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody ProcureOrder procureOrder) {
        return AjaxResult.success(this.procureOrderService.insert(procureOrder));
    }

    /**
     * 编辑数据
     *
     * @param procureOrder 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ProcureOrder procureOrder) {
        return AjaxResult.success(this.procureOrderService.update(procureOrder));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.procureOrderService.deleteById(id));
    }

    /**
     * 审批
     */
    @PostMapping("/approval")
    public AjaxResult approval(@RequestBody ProcureOrder procureOrder) {
        return toAjax(this.procureOrderService.approval(procureOrder));
    }


}

