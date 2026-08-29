package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldScheduledTask;
import com.cuixing.mold.service.MoldScheduledTaskService;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

/**
 * (MoldScheduledTask)表控制层
 *
 * @author makejava
 * @since 2024-07-10 15:38:29
 */
@Api(tags = "接口")
@RestController
@RequestMapping("moldScheduledTask")
public class MoldScheduledTaskController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private MoldScheduledTaskService moldScheduledTaskService;

    /**
     * 分页查询
     *
     * @param moldScheduledTask 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldScheduledTask moldScheduledTask) {
        this.startPage();
        List<MoldScheduledTask> list = this.moldScheduledTaskService.selectMoldScheduledTaskList(moldScheduledTask);
        return this.getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") String id) {
        return AjaxResult.success(this.moldScheduledTaskService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldScheduledTask 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(MoldScheduledTask moldScheduledTask) {
        return AjaxResult.success(this.moldScheduledTaskService.insert(moldScheduledTask));
    }

    /**
     * 编辑数据
     *
     * @param moldScheduledTask 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(MoldScheduledTask moldScheduledTask) {
        return AjaxResult.success(this.moldScheduledTaskService.update(moldScheduledTask));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(String id) {
        return toAjax(this.moldScheduledTaskService.deleteById(id));
    }

}

