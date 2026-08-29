package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcSurface;
import com.cuixing.qc.service.QcSurfaceService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质检管理-表面检缺陷项设置(QcSurface)表控制层
 *
 * @author makejava
 * @since 2024-07-02 16:56:25
 */
@Api(tags = "质检管理-表面检缺陷项设置接口")
@RestController
@RequestMapping("/qcSurface")
public class QcSurfaceController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QcSurfaceService qcSurfaceService;

    /**
     * 分页查询
     *
     * @param qcSurface 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcSurface qcSurface) {
        this.startPage();
        List<QcSurface> list = this.qcSurfaceService.selectQcSurfaceList(qcSurface);
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
        return AjaxResult.success(this.qcSurfaceService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcSurface 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcSurface qcSurface) {
        return AjaxResult.success(this.qcSurfaceService.insert(qcSurface));
    }

    /**
     * 编辑数据
     *
     * @param qcSurface 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcSurface qcSurface) {
        return AjaxResult.success(this.qcSurfaceService.update(qcSurface));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Integer id) {
        return toAjax(this.qcSurfaceService.deleteById(id));
    }

}

