package com.cuixing.wms.controller;

import com.cuixing.wms.entity.WmsNoticeInvoiceRecord;
import com.cuixing.wms.service.WmsNoticeInvoiceRecordService;
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
 * 发货通知单审核记录表(WmsNoticeInvoiceRecord)表控制层
 *
 * @author 阿江
 * @since 2024-10-30 14:09:47
 */
@Api(tags = "发货通知单审核记录表接口")
@RestController
@RequestMapping("/wmsNoticeInvoiceRecord")
public class WmsNoticeInvoiceRecordController extends BaseController{
    /**
     * 服务对象
     */
    @Autowired
    private WmsNoticeInvoiceRecordService wmsNoticeInvoiceRecordService;

    /**
     * 分页查询
     *
     * @param wmsNoticeInvoiceRecord 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord) {
        this.startPage();
        List<WmsNoticeInvoiceRecord> list = this.wmsNoticeInvoiceRecordService.selectWmsNoticeInvoiceRecordList(wmsNoticeInvoiceRecord);
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
        return AjaxResult.success(this.wmsNoticeInvoiceRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param wmsNoticeInvoiceRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord) {
        return AjaxResult.success(this.wmsNoticeInvoiceRecordService.insert(wmsNoticeInvoiceRecord));
    }

    /**
     * 编辑数据
     *
     * @param wmsNoticeInvoiceRecord 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord) {
        return AjaxResult.success(this.wmsNoticeInvoiceRecordService.update(wmsNoticeInvoiceRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.wmsNoticeInvoiceRecordService.deleteById(id));
    }

}

