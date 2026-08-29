package com.cuixing.mes.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.mes.entity.MesErrorLog;
import com.cuixing.mes.service.MesErrorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 生产异常表(MesErrorLog)表控制层
 *
 * @author 阿江
 * @since 2025-07-14 23:27:49
 */
@Api(tags = "生产异常表接口")
@RestController
@RequestMapping("/mesErrorLog")
public class MesErrorLogController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private MesErrorLogService mesErrorLogService;

    /**
     * 分页查询
     *
     * @param mesErrorLog 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MesErrorLog mesErrorLog) {
        this.startPage();
        List<MesErrorLog> list = this.mesErrorLogService.selectMesErrorLogList(mesErrorLog);
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
        return AjaxResult.success(this.mesErrorLogService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param mesErrorLog 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MesErrorLog mesErrorLog) {
        return AjaxResult.success(this.mesErrorLogService.insert(mesErrorLog));
    }

    /**
     * 编辑数据
     *
     * @param mesErrorLog 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MesErrorLog mesErrorLog) {
        return AjaxResult.success(this.mesErrorLogService.update(mesErrorLog));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.mesErrorLogService.deleteById(id));
    }

}

