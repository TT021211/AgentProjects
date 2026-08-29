package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcDetectionItems;
import com.cuixing.qc.service.QcDetectionItemsService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-检测项设置(QcDetectionItems)表控制层
 *
 * @author makejava
 * @since 2024-07-02 16:56:20
 */
@Api(tags = "质量管理-检测项设置接口")
@RestController
@RequestMapping("/qcDetectionItems")
public class QcDetectionItemsController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QcDetectionItemsService qcDetectionItemsService;

    /**
     * 分页查询
     *
     * @param qcDetectionItems 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcDetectionItems qcDetectionItems) {
        this.startPage();
        List<QcDetectionItems> list = this.qcDetectionItemsService.selectQcDetectionItemsList(qcDetectionItems);
        return this.getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.qcDetectionItemsService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcDetectionItems 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcDetectionItems qcDetectionItems) {
        return AjaxResult.success(this.qcDetectionItemsService.insert(qcDetectionItems));
    }

    /**
     * 编辑数据
     *
     * @param qcDetectionItems 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcDetectionItems qcDetectionItems) {
        return AjaxResult.success(this.qcDetectionItemsService.update(qcDetectionItems));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Integer id) {
        return toAjax(this.qcDetectionItemsService.deleteById(id));
    }

}

