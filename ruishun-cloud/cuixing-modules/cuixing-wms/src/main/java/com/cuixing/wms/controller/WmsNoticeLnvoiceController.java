package com.cuixing.wms.controller;

import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsNoticeLnvoice;
import com.cuixing.wms.service.WmsNoticeLnvoiceService;
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
 * 发货通知单(WmsNoticeLnvoice)表控制层
 *
 * @author 阿江
 * @since 2024-10-25 09:39:26
 */
@Api(tags = "发货通知单接口")
@RestController
@RequestMapping("/wmsNoticeLnvoice")
public class WmsNoticeLnvoiceController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private WmsNoticeLnvoiceService wmsNoticeLnvoiceService;

    /**
     * 分页查询
     *
     * @param wmsNoticeLnvoice 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(WmsNoticeLnvoice wmsNoticeLnvoice) {
        this.startPage();
        List<WmsNoticeLnvoice> list = this.wmsNoticeLnvoiceService.selectWmsNoticeLnvoiceList(wmsNoticeLnvoice);
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
        return AjaxResult.success(this.wmsNoticeLnvoiceService.queryById(id));
    }

    /**
     * 取消关联
     */
    @ApiOperation("取消关联")
    @PostMapping("/removeBox")
    public AjaxResult removeBox(@RequestBody List<WmsBox> wmsBoxes){
        return AjaxResult.success(this.wmsNoticeLnvoiceService.removeBox(wmsBoxes));
    }
    /**
     * 审核
     * @param wmsNoticeLnvoices
     * @return
     */
    @PostMapping("/audit")
    public AjaxResult updateStatus(@RequestBody List<WmsNoticeLnvoice> wmsNoticeLnvoices){
        return AjaxResult.success(this.wmsNoticeLnvoiceService.updateStatus(wmsNoticeLnvoices));
    }
    /**
     * 新增数据
     *
     * @param wmsNoticeLnvoice 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody WmsNoticeLnvoice wmsNoticeLnvoice) {
        return AjaxResult.success(this.wmsNoticeLnvoiceService.insert(wmsNoticeLnvoice));
    }

    /**
     * 编辑数据
     *
     * @param wmsNoticeLnvoice 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WmsNoticeLnvoice wmsNoticeLnvoice) {
        return AjaxResult.success(this.wmsNoticeLnvoiceService.update(wmsNoticeLnvoice));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    public AjaxResult deleteByIds(@RequestParam("ids") List<Long> ids) {
        return toAjax(this.wmsNoticeLnvoiceService.deleteById(ids));
    }

    /**
     * 取回（删除出库单，状态为待出库状态的出库单）
     */
    @ApiOperation("取回")
    @PostMapping("/recall")
    public AjaxResult recall(@RequestBody List<WmsNoticeLnvoice> wmsNoticeLnvoice){
        return AjaxResult.success(this.wmsNoticeLnvoiceService.recall(wmsNoticeLnvoice));
    }

    @ApiOperation("测试")
    @GetMapping("/test")
    public AjaxResult test(){
        return AjaxResult.success(this.wmsNoticeLnvoiceService.test());
    }

}

