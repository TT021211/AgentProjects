package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcEnamelProcessRecord;
import com.cuixing.qc.service.QcEnamelProcessRecordService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-漆包过程检记录(QcEnamelProcessRecord)表控制层
 *
 * @author makejava
 * @since 2024-07-02 16:56:22
 */
@Api(tags = "质量管理-漆包过程检记录接口")
@RestController
@RequestMapping("/qcEnamelProcessRecord")
public class QcEnamelProcessRecordController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QcEnamelProcessRecordService qcEnamelProcessRecordService;

    /**
     * 分页查询
     *
     * @param qcEnamelProcessRecord 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcEnamelProcessRecord qcEnamelProcessRecord) {
        this.startPage();
        List<QcEnamelProcessRecord> list = this.qcEnamelProcessRecordService.selectQcEnamelProcessRecordList(qcEnamelProcessRecord);
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
        return AjaxResult.success(this.qcEnamelProcessRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcEnamelProcessRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcEnamelProcessRecord qcEnamelProcessRecord) {
        return AjaxResult.success(this.qcEnamelProcessRecordService.insert(qcEnamelProcessRecord));
    }

    /**
     * 编辑数据
     *
     * @param qcEnamelProcessRecord 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcEnamelProcessRecord qcEnamelProcessRecord) {
        return AjaxResult.success(this.qcEnamelProcessRecordService.update(qcEnamelProcessRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.qcEnamelProcessRecordService.deleteById(id));
    }

}

