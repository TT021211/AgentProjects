package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcDefect;
import com.cuixing.qc.service.QcDefectService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

/**
 * 质检管理-缺陷项设置(QcDefect)表控制层
 *
 * @author makejava
 * @since 2024-07-02 17:22:36
 */
@Api(tags = "质检管理-缺陷项设置接口")
@RestController
@RequestMapping("/qcDefect")
public class QcDefectController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private QcDefectService qcDefectService;

    /**
     * 分页查询
     *
     * @param qcDefect 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcDefect qcDefect) {
        this.startPage();
        List<QcDefect> list = this.qcDefectService.selectQcDefectList(qcDefect);
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
        return AjaxResult.success(this.qcDefectService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcDefect 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcDefect qcDefect) {
        return AjaxResult.success(this.qcDefectService.insert(qcDefect));
    }

    /**
     * 编辑数据
     *
     * @param qcDefect 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcDefect qcDefect) {
        return AjaxResult.success(this.qcDefectService.update(qcDefect));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.qcDefectService.deleteById(id));
    }

}

