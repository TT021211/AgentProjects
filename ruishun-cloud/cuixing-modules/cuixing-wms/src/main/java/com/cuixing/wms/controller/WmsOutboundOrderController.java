package com.cuixing.wms.controller;

import com.cuixing.wms.entity.WmsOutboundOrder;
import com.cuixing.wms.service.WmsOutboundOrderService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 成品出库单(WmsOutboundOrder)表控制层
 *
 * @author 阿江
 * @since 2024-10-25 09:39:26
 */
@Api(tags = "成品出库单接口")
@RestController
@RequestMapping("/wmsOutboundOrder")
public class WmsOutboundOrderController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private WmsOutboundOrderService wmsOutboundOrderService;

    /**
     * 分页查询
     *
     * @param wmsOutboundOrder 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(WmsOutboundOrder wmsOutboundOrder) {
        this.startPage();
        List<WmsOutboundOrder> list = this.wmsOutboundOrderService.selectWmsOutboundOrderList(wmsOutboundOrder);
        return this.getDataTable(list);
    }

    /**
     * 出库or作废
     * @param wmsOutboundOrder
     * @return
     */
    @ApiOperation("出库or作废")
    @PostMapping("/outboundOrCancel")
    public AjaxResult outboundOrCancel(@RequestBody WmsOutboundOrder wmsOutboundOrder) {
        return AjaxResult.success(this.wmsOutboundOrderService.updateStatus(wmsOutboundOrder));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return AjaxResult.success(this.wmsOutboundOrderService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param wmsOutboundOrder 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody WmsOutboundOrder wmsOutboundOrder) {
        return AjaxResult.success(this.wmsOutboundOrderService.insert(wmsOutboundOrder));
    }

    /**
     * 编辑数据
     *
     * @param wmsOutboundOrder 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WmsOutboundOrder wmsOutboundOrder) {
        return AjaxResult.success(this.wmsOutboundOrderService.update(wmsOutboundOrder));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id")Long id) {
        return toAjax(this.wmsOutboundOrderService.deleteById(id));
    }

}

