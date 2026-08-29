package com.cuixing.mes.controller;

import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.mes.entity.MesDoublewireWorkorder;
import com.cuixing.mes.entity.MesStrandedwireWorkorder;
import com.cuixing.mes.service.MesStrandedwireWorkorderService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;
import javax.annotation.Resource;

/**
 * 绞线工单(MesStrandedwireWorkorder)表控制层
 *
 * @author 阿江
 * @since 2024-11-25 11:04:48
 */
@Api(tags = "绞线工单接口")
@RestController
@RequestMapping("/mesStrandedwireWorkorder")
public class MesStrandedwireWorkorderController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private MesStrandedwireWorkorderService mesStrandedwireWorkorderService;

    /**
     * 分页查询
     *
     * @param mesStrandedwireWorkorder 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MesStrandedwireWorkorder mesStrandedwireWorkorder) {
        this.startPage();
        List<MesStrandedwireWorkorder> list = this.mesStrandedwireWorkorderService.selectMesStrandedwireWorkorderList(mesStrandedwireWorkorder);
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
        return AjaxResult.success(this.mesStrandedwireWorkorderService.queryById(id));
    }

    /**
     * 修改排序
     *
     * @return 查询结果
     */
    @ApiOperation("修改排序")
    @PutMapping("/updateSort/{id}")
    public AjaxResult updateSort(@PathVariable(value = "id")BigInteger id){
        mesStrandedwireWorkorderService.updateSortById(id);
        return AjaxResult.success();
    }

    /**
     * 新增数据
     *
     * @param mesStrandedwireWorkorder 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MesStrandedwireWorkorder mesStrandedwireWorkorder) throws ParseException {
        return AjaxResult.success(this.mesStrandedwireWorkorderService.insert(mesStrandedwireWorkorder));
    }

    /**
     * 编辑数据
     *
     * @param mesStrandedwireWorkorder 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MesStrandedwireWorkorder mesStrandedwireWorkorder) {
        return AjaxResult.success(this.mesStrandedwireWorkorderService.update(mesStrandedwireWorkorder));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteByIds(@PathVariable("id") BigInteger[] ids) {
        return toAjax(this.mesStrandedwireWorkorderService.deleteByIds(ids));
    }

    /**
     * 绞线插单
     */
    @ApiOperation("并丝插单")
    @Log(title = "绞线工单", businessType = BusinessType.UPDATE)
    @PutMapping("insertWorkorder")
    public AjaxResult insertWorkorder(@RequestBody MesStrandedwireWorkorder mesStrandedwireWorkorder){

        return toAjax(mesStrandedwireWorkorderService.insertWorkorder(mesStrandedwireWorkorder));
    }
    /**
     * 插单工单完工
     */
    @ApiOperation("插单完成")
    @Log(title = "绞线工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancelInsertWorkorder/{id}")
    public AjaxResult cancelInsertWorkorder(@PathVariable BigInteger id){

        return toAjax(mesStrandedwireWorkorderService.cancelInsertWorkorder(id));
    }

    /**
     * 插单取消
     */
    @ApiOperation("插单取消")
    @Log(title = "绞线工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancelWorkorder/{id}")
    public AjaxResult cancelWorkorder(@PathVariable BigInteger id){

        return toAjax(mesStrandedwireWorkorderService.cancelWorkorder(id));
    }

    /**
     * 通过工单id更改状态
     */
    @ApiOperation("通过工单id更改状态")
    @Log(title = "绞线工单", businessType = BusinessType.UPDATE)
    @PutMapping("updateStatus/{id}/{status}")
    public AjaxResult updateStatusById(@PathVariable(value = "id") BigInteger id , @PathVariable(value = "status")  String status){

        return AjaxResult.success(mesStrandedwireWorkorderService.updateStatusById(id,status));
    }

    /**
     * 并丝工单作废
     */
    @ApiOperation("绞线工单作废")
    @Log(title = "绞线工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancel/{id}")
    public AjaxResult  cancelById(@PathVariable BigInteger id){
        return toAjax(mesStrandedwireWorkorderService.cancelById(id));
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
        List<MesStrandedwireWorkorder> list =mesStrandedwireWorkorderService.getListByEquipmentId(equipmentId,axleNumber);
        return AjaxResult.success(list);
    }

}

