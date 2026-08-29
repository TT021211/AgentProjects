package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcWireDiscRecord;
import com.cuixing.qc.service.QcWireDiscRecordService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-线盘检记录(QcWireDiscRecord)表控制层
 *
 * @author makejava
 * @since 2024-07-02 16:56:25
 */
@Api(tags = "质量管理-线盘检记录接口")
@RestController
@RequestMapping("/qcWireDiscRecord")
public class QcWireDiscRecordController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QcWireDiscRecordService qcWireDiscRecordService;

    /**
     * 分页查询
     *
     * @param qcWireDiscRecord 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcWireDiscRecord qcWireDiscRecord) {
        this.startPage();
        List<QcWireDiscRecord> list = this.qcWireDiscRecordService.selectQcWireDiscRecordList(qcWireDiscRecord);
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
        return AjaxResult.success(this.qcWireDiscRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcWireDiscRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcWireDiscRecord qcWireDiscRecord) {
        return AjaxResult.success(this.qcWireDiscRecordService.insert(qcWireDiscRecord));
    }

    /**
     * 编辑数据
     *
     * @param qcWireDiscRecord 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcWireDiscRecord qcWireDiscRecord) {
        return AjaxResult.success(this.qcWireDiscRecordService.update(qcWireDiscRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.qcWireDiscRecordService.deleteById(id));
    }

}

