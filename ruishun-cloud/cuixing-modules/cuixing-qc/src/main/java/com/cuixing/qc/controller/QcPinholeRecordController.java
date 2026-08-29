package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcPinholeRecord;
import com.cuixing.qc.service.QcPinholeRecordService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-针孔送检记录(QcPinholeRecord)表控制层
 *
 * @author makejava
 * @since 2024-07-02 16:56:24
 */
@Api(tags = "质量管理-针孔送检记录接口")
@RestController
@RequestMapping("/qcPinholeRecord")
public class QcPinholeRecordController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QcPinholeRecordService qcPinholeRecordService;

    /**
     * 分页查询
     *
     * @param qcPinholeRecord 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcPinholeRecord qcPinholeRecord) {
        this.startPage();
        List<QcPinholeRecord> list = this.qcPinholeRecordService.selectQcPinholeRecordList(qcPinholeRecord);
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
        return AjaxResult.success(this.qcPinholeRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcPinholeRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcPinholeRecord qcPinholeRecord) {
        return AjaxResult.success(this.qcPinholeRecordService.insert(qcPinholeRecord));
    }

    /**
     * 编辑数据
     *
     * @param qcPinholeRecord 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcPinholeRecord qcPinholeRecord) {
        return AjaxResult.success(this.qcPinholeRecordService.update(qcPinholeRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.qcPinholeRecordService.deleteById(id));
    }

}

