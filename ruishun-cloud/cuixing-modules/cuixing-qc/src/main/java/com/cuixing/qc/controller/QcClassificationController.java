package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcClassification;
import com.cuixing.qc.service.QcClassificationService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质检分类表(QcClassification)表控制层
 *
 * @author makejava
 * @since 2024-07-02 16:56:17
 */
@Api(tags = "质检分类表接口")
@RestController
@RequestMapping("/qcClassification")
public class QcClassificationController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QcClassificationService qcClassificationService;

    /**
     * 分页查询
     *
     * @param qcClassification 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcClassification qcClassification) {
        this.startPage();
        List<QcClassification> list = this.qcClassificationService.selectQcClassificationList(qcClassification);
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
        return AjaxResult.success(this.qcClassificationService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcClassification 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcClassification qcClassification) {
        return AjaxResult.success(this.qcClassificationService.insert(qcClassification));
    }

    /**
     * 编辑数据
     *
     * @param qcClassification 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcClassification qcClassification) {
        return AjaxResult.success(this.qcClassificationService.update(qcClassification));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.qcClassificationService.deleteById(id));
    }

}

