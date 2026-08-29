package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcEnamelInspectionRecord;
import com.cuixing.qc.service.QcEnamelInspectionRecordService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量模块-漆包巡检记录(QcEnamelInspectionRecord)表控制层
 *
 * @author makejava
 * @since 2024-07-02 16:56:21
 */
@Api(tags = "质量模块-漆包巡检记录接口")
@RestController
@RequestMapping("/qcEnamelInspectionRecord")
public class QcEnamelInspectionRecordController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QcEnamelInspectionRecordService qcEnamelInspectionRecordService;

    /**
     * 分页查询
     *
     * @param qcEnamelInspectionRecord 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcEnamelInspectionRecord qcEnamelInspectionRecord) {
        this.startPage();
        List<QcEnamelInspectionRecord> list = this.qcEnamelInspectionRecordService.selectQcEnamelInspectionRecordList(qcEnamelInspectionRecord);
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
        return AjaxResult.success(this.qcEnamelInspectionRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcEnamelInspectionRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcEnamelInspectionRecord qcEnamelInspectionRecord) {
        return AjaxResult.success(this.qcEnamelInspectionRecordService.insert(qcEnamelInspectionRecord));
    }

    /**
     * 编辑数据
     *
     * @param qcEnamelInspectionRecord 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcEnamelInspectionRecord qcEnamelInspectionRecord) {
        return AjaxResult.success(this.qcEnamelInspectionRecordService.update(qcEnamelInspectionRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.qcEnamelInspectionRecordService.deleteById(id));
    }

}

