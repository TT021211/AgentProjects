package com.cuixing.qc.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.qc.entity.QcDailyplan;
import com.cuixing.qc.service.QcDailyplanService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 日常送检计划(QcDailyplan)表控制层
 *
 * @author makejava
 * @since 2024-07-02 16:56:18
 */
@Api(tags = "日常送检计划接口")
@RestController
@RequestMapping("/qcDailyplan")
public class QcDailyplanController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QcDailyplanService qcDailyplanService;

    /**
     * 分页查询
     *
     * @param qcDailyplan 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(QcDailyplan qcDailyplan) {
        this.startPage();
        List<QcDailyplan> list = this.qcDailyplanService.selectQcDailyplanList(qcDailyplan);
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
        return AjaxResult.success(this.qcDailyplanService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qcDailyplan 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody QcDailyplan qcDailyplan) {
        return AjaxResult.success(this.qcDailyplanService.insert(qcDailyplan));
    }

    /**
     * 编辑数据
     *
     * @param qcDailyplan 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QcDailyplan qcDailyplan) {
        return AjaxResult.success(this.qcDailyplanService.update(qcDailyplan));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id")  Long id) {
        return toAjax(this.qcDailyplanService.deleteById(id));
    }

}

