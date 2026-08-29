package com.cuixing.qc.controller;

import com.cuixing.qc.entity.QcMaterialInspection;
import com.cuixing.qc.service.QcMaterialInspectionService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

/**
 * 原料检验表(QcMaterialInspection)表控制层
 *
 * @author 阿江
 * @since 2024-12-13 10:01:26
 */
@Api(tags = "原料检验表接口")
@RestController
@RequestMapping("/qcMaterialInspection")
public class QcMaterialInspectionController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private QcMaterialInspectionService qcMaterialInspectionService;

    /**
     * 分页查询
     *
     * @param qcMaterialInspection 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcMaterialInspection qcMaterialInspection) {
        this.startPage();
        List<QcMaterialInspection> list = this.qcMaterialInspectionService.selectQcMaterialInspectionList(qcMaterialInspection);
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
        return AjaxResult.success(this.qcMaterialInspectionService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcMaterialInspection 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcMaterialInspection qcMaterialInspection) {
        return AjaxResult.success(this.qcMaterialInspectionService.insert(qcMaterialInspection));
    }

    /**
     * 编辑数据
     *
     * @param qcMaterialInspection 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcMaterialInspection qcMaterialInspection) {
        return AjaxResult.success(this.qcMaterialInspectionService.update(qcMaterialInspection));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.qcMaterialInspectionService.deleteById(id));
    }

}

