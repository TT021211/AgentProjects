package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcInspectionItems;
import com.cuixing.qc.service.QcInspectionItemsService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-质检检测项(QcInspectionItems)表控制层
 *
 * @author makejava
 * @since 2024-07-02 16:56:22
 */
@Api(tags = "质量管理-质检检测项接口")
@RestController
@RequestMapping("/qcInspectionItems")
public class QcInspectionItemsController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QcInspectionItemsService qcInspectionItemsService;

    /**
     * 分页查询
     *
     * @param qcInspectionItems 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcInspectionItems qcInspectionItems) {
        this.startPage();
        List<QcInspectionItems> list = this.qcInspectionItemsService.selectQcInspectionItemsList(qcInspectionItems);
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
        return AjaxResult.success(this.qcInspectionItemsService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcInspectionItems 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcInspectionItems qcInspectionItems) {
        return AjaxResult.success(this.qcInspectionItemsService.insert(qcInspectionItems));
    }

    /**
     * 编辑数据
     *
     * @param qcInspectionItems 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcInspectionItems qcInspectionItems) {
        return AjaxResult.success(this.qcInspectionItemsService.update(qcInspectionItems));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.qcInspectionItemsService.deleteById(id));
    }

}

