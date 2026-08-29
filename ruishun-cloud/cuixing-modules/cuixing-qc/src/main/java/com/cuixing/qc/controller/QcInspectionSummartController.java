package com.cuixing.qc.controller;

import com.cuixing.qc.entity.QcInspectionSummart;
import com.cuixing.qc.service.QcInspectionSummartService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;

/**
 * 检验汇总表(QcInspectionSummart)表控制层
 *
 * @author 阿江
 * @since 2024-12-13 10:01:27
 */
@Api(tags = "检验汇总表接口")
@RestController
@RequestMapping("/qcInspectionSummart")
public class QcInspectionSummartController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private QcInspectionSummartService qcInspectionSummartService;

    /**
     * 分页查询
     *
     * @param qcInspectionSummart 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcInspectionSummart qcInspectionSummart) {
        this.startPage();
        List<QcInspectionSummart> list = this.qcInspectionSummartService.selectQcInspectionSummartList(qcInspectionSummart);
        return this.getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") BigInteger id) {
        return AjaxResult.success(this.qcInspectionSummartService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcInspectionSummart 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcInspectionSummart qcInspectionSummart) {
        return AjaxResult.success(this.qcInspectionSummartService.insert(qcInspectionSummart));
    }

    /**
     * 编辑数据
     *
     * @param qcInspectionSummart 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcInspectionSummart qcInspectionSummart) {
        return AjaxResult.success(this.qcInspectionSummartService.update(qcInspectionSummart));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") BigInteger id) {
        return toAjax(this.qcInspectionSummartService.deleteById(id));
    }

    /**
     * 质检看板接口
     */
    @GetMapping("/qcCountData")
    public AjaxResult getQcCountData() {
        return AjaxResult.success(this.qcInspectionSummartService.getQcCountData());
    }
}

