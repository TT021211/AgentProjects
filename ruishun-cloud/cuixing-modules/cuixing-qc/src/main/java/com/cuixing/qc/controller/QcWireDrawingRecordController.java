package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcWireDrawingRecord;
import com.cuixing.qc.service.QcWireDrawingRecordService;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.system.api.RemoteWmsService;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-拉丝过程检记录(QcWireDrawingRecord)表控制层
 *
 * @author makejava
 * @since 2024-07-02 16:56:26
 */
@Api(tags = "质量管理-拉丝过程检记录接口")
@RestController
@RequestMapping("/qcWireDrawingRecord")
public class QcWireDrawingRecordController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QcWireDrawingRecordService qcWireDrawingRecordService;

    /**
     * 分页查询
     *
     * @param qcWireDrawingRecord 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcWireDrawingRecord qcWireDrawingRecord) {
        this.startPage();
        List<QcWireDrawingRecord> list = this.qcWireDrawingRecordService.selectQcWireDrawingRecordList(qcWireDrawingRecord);
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
        return AjaxResult.success(this.qcWireDrawingRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcWireDrawingRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcWireDrawingRecord qcWireDrawingRecord) {
        return AjaxResult.success(this.qcWireDrawingRecordService.insert(qcWireDrawingRecord));
    }

    /**
     * 编辑数据
     *
     * @param qcWireDrawingRecord 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcWireDrawingRecord qcWireDrawingRecord) {
        return AjaxResult.success(this.qcWireDrawingRecordService.update(qcWireDrawingRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.qcWireDrawingRecordService.deleteById(id));
    }

}

