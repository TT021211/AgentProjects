package com.cuixing.mes.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.mes.entity.MesShiftSchedule;
import com.cuixing.mes.service.MesShiftScheduleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

/**
 * 生产交班表(MesShiftSchedule)表控制层
 *
 * @author 阿江
 * @since 2025-03-24 11:18:54
 */
@Api(tags = "生产交班表接口")
@RestController
@RequestMapping("/mesShiftSchedule")
public class MesShiftScheduleController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private MesShiftScheduleService mesShiftScheduleService;

    /**
     * 分页查询
     *
     * @param mesShiftSchedule 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MesShiftSchedule mesShiftSchedule) {
        this.startPage();
        List<MesShiftSchedule> list = this.mesShiftScheduleService.selectMesShiftScheduleList(mesShiftSchedule);
        return this.getDataTable(list);
    }

    /**
     * 查询最近工单、机台的报工记录
     */
    @ApiOperation("查询最近拉丝工单、机台的报工记录")
    @GetMapping("/getInfoByWorkstationCode/{workstationCode}/{workorderSn}")
    public AjaxResult getInfoByWorkstationCode(@PathVariable(value = "workstationCode") String workstationCode, @PathVariable(value = "workorderSn") String workorderSn) {
        MesShiftSchedule mesShiftSchedule = this.mesShiftScheduleService.getInfoByWorkstationCode(workstationCode,workorderSn);
        return AjaxResult.success(mesShiftSchedule);
    }

    @ApiOperation("查询最近工艺工单、机台的报工记录")
    @GetMapping("/getInfoByWorkstationCodeToGy/{workstationCode}/{workorderSn}/{alexPositionNumber}/{alexNumber}")
        public AjaxResult getInfoByWorkstationCodeToGy(@PathVariable(value = "workstationCode") String workstationCode, @PathVariable(value = "workorderSn") String workorderSn,@PathVariable(value = "alexPositionNumber") List<String> alexPositionNumber,@PathVariable(value = "alexNumber") String alexNumber) {
        return AjaxResult.success(this.mesShiftScheduleService.getInfoByWorkstationCodeToGy(workstationCode,workorderSn,alexPositionNumber,alexNumber));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return AjaxResult.success(this.mesShiftScheduleService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param mesShiftSchedule 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody List<MesShiftSchedule> mesShiftSchedule) {
        return AjaxResult.success(this.mesShiftScheduleService.insert(mesShiftSchedule));
    }

    /**
     * 编辑数据
     *
     * @param mesShiftSchedule 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MesShiftSchedule mesShiftSchedule) {
        return AjaxResult.success(this.mesShiftScheduleService.update(mesShiftSchedule));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.mesShiftScheduleService.deleteById(id));
    }

}

