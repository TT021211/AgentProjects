package com.cuixing.mes.controller;

import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.mes.entity.MesAnnealingWorkorder;
import com.cuixing.mes.service.MesAnnealingWorkorderService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 * 退火工单(MesAnnealingWorkorder)表控制层
 *
 * @author 阿江
 * @since 2024-11-25 10:30:34
 */
@Api(tags = "退火工单接口")
@RestController
@RequestMapping("/mesAnnealingWorkorder")
public class MesAnnealingWorkorderController extends BaseController{
    /**
     * 服务对象
     */
    @Autowired
    private MesAnnealingWorkorderService mesAnnealingWorkorderService;

    /**
     * 通过生产工单号查询单条数据
     *
     * @param workorderCode
     * @return 实例对象
     */
    @ApiOperation("通过生产工单号查询单条数据")
    @GetMapping("queryByMesAnnealingWorkorderCode/{workorderCode}")
    public AjaxResult queryByMesAnnealingWorkorderCode(@PathVariable(value = "workorderCode") String workorderCode){
        return AjaxResult.success(mesAnnealingWorkorderService.queryByMesAnnealingWorkorderCode(workorderCode));
    }

    /**
     * 分页查询
     *
     * @param mesAnnealingWorkorder 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MesAnnealingWorkorder mesAnnealingWorkorder) {
        this.startPage();
        List<MesAnnealingWorkorder> list = this.mesAnnealingWorkorderService.selectMesAnnealingWorkorderList(mesAnnealingWorkorder);
        return this.getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") BigInteger id) {
        return AjaxResult.success(this.mesAnnealingWorkorderService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param mesAnnealingWorkorder 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MesAnnealingWorkorder mesAnnealingWorkorder) throws ParseException {
        return AjaxResult.success(this.mesAnnealingWorkorderService.insert(mesAnnealingWorkorder));
    }

    /**
     * 编辑数据
     *
     * @param mesAnnealingWorkorder 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MesAnnealingWorkorder mesAnnealingWorkorder) {
        return AjaxResult.success(this.mesAnnealingWorkorderService.update(mesAnnealingWorkorder));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteByIds(@PathVariable("id") BigInteger[] ids) {
        return toAjax(this.mesAnnealingWorkorderService.deleteByIds(ids));
    }

    /**
     * 修改排序
     *
     * @return 查询结果
     */
    @ApiOperation("修改排序")
    @PutMapping("/updateSort/{id}")
    public AjaxResult updateSort(@PathVariable(value = "id")BigInteger id){
        mesAnnealingWorkorderService.updateSortById(id);
        return AjaxResult.success();
    }

    /**
     * 通过工单id更改状态
     */
    @ApiOperation("通过工单id更改状态")
    @Log(title = "退火工单", businessType = BusinessType.UPDATE)
    @PutMapping("updateStatus/{id}/{status}")
    public AjaxResult updateStatusById(@PathVariable(value = "id") BigInteger id , @PathVariable(value = "status")  String status){

        return AjaxResult.success(mesAnnealingWorkorderService.updateStatusById(id,status));
    }

    /**
     * 退火插单
     */
    @ApiOperation("退火插单")
    @Log(title = "退火工单", businessType = BusinessType.UPDATE)
    @PutMapping("insertWorkorder")
    public AjaxResult insertWorkorder(@RequestBody MesAnnealingWorkorder mesAnnealingWorkorder){

        return toAjax(mesAnnealingWorkorderService.insertWorkorder(mesAnnealingWorkorder));
    }
    /**
     * 插单工单完工
     */
    @ApiOperation("插单完成")
    @Log(title = "退火工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancelInsertWorkorder/{id}")
    public AjaxResult cancelInsertWorkorder(@PathVariable BigInteger id){

        return toAjax(mesAnnealingWorkorderService.cancelInsertWorkorder(id));
    }

    /**
     * 插单取消
     */
    @ApiOperation("插单取消")
    @Log(title = "退火工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancelWorkorder/{id}")
    public AjaxResult cancelWorkorder(@PathVariable BigInteger id){

        return toAjax(mesAnnealingWorkorderService.cancelWorkorder(id));
    }

    /**
     * 退火工单作废
     */
    @ApiOperation("退火工单作废")
    @Log(title = "退火工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancel/{id}")
    public AjaxResult  cancelById(@PathVariable BigInteger id){
        return toAjax(mesAnnealingWorkorderService.cancelById(id));
    }

    /**
     * 通过机台id和位置查询该机台所有已排产、生产中列表
     *
     * @param equipmentId 筛选条件
     * @return 查询结果
     */
    @ApiOperation("通过机台id和位置查询该机台工单列表")
    @GetMapping("/getListByEquipmentId/{equipmentId}/{axleNumber}")
    public AjaxResult getListByEquipmentId(@PathVariable(value = "equipmentId")  BigInteger equipmentId,@PathVariable(value = "axleNumber") String axleNumber){
        List<MesAnnealingWorkorder> list =mesAnnealingWorkorderService.getListByEquipmentId(equipmentId,axleNumber);
        return AjaxResult.success(list);
    }

}

