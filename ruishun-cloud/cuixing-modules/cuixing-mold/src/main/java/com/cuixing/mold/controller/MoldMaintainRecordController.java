package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldMaintainRecord;
import com.cuixing.mold.service.MoldMaintainRecordService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具保养表--保养记录表(MoldMaintainRecord)表控制层
 *
 * @author makejava
 * @since 2024-06-25 16:52:04
 */
@Api(tags = "模具保养表--保养记录表接口")
@RestController
@RequestMapping("/mold/moldMaintainRecord")
public class MoldMaintainRecordController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldMaintainRecordService moldMaintainRecordService;

    /**
     * 分页查询
     *
     * @param moldMaintainRecord 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldMaintainRecord moldMaintainRecord) {
        this.startPage();
        List<MoldMaintainRecord> list = this.moldMaintainRecordService.selectMoldMaintainRecordList(moldMaintainRecord);
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
        return AjaxResult.success(this.moldMaintainRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldMaintainRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldMaintainRecord moldMaintainRecord) {
        return AjaxResult.success(this.moldMaintainRecordService.insert(moldMaintainRecord));
    }

    /**
     * 编辑数据
     *
     * @param moldMaintainRecord 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldMaintainRecord moldMaintainRecord) {
        return AjaxResult.success(this.moldMaintainRecordService.update(moldMaintainRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return AjaxResult.success(this.moldMaintainRecordService.deleteById(id));
    }

}

