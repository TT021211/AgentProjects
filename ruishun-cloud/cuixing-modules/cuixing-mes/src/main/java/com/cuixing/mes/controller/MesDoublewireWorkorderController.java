package com.cuixing.mes.controller;

import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.mes.entity.MesAnnealingWorkorder;
import com.cuixing.mes.entity.MesDoublewireWorkorder;
import com.cuixing.mes.service.MesDoublewireWorkorderService;
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
 * 并丝工单(MesDoublewireWorkorder)表控制层
 *
 * @author 阿江
 * @since 2024-11-25 11:04:48
 */
@Api(tags = "并丝工单接口")
@RestController
@RequestMapping("/mesDoublewireWorkorder")
public class MesDoublewireWorkorderController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private MesDoublewireWorkorderService mesDoublewireWorkorderService;

    /**
     * 分页查询
     *
     * @param mesDoublewireWorkorder 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MesDoublewireWorkorder mesDoublewireWorkorder) {
        this.startPage();
        List<MesDoublewireWorkorder> list = this.mesDoublewireWorkorderService.selectMesDoublewireWorkorderList(mesDoublewireWorkorder);
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
        return AjaxResult.success(this.mesDoublewireWorkorderService.queryById(id));
    }

    /**
     * 修改排序
     *
     * @return 查询结果
     */
    @ApiOperation("修改排序")
    @PutMapping("/updateSort/{id}")
    public AjaxResult updateSort(@PathVariable(value = "id")BigInteger id){
        mesDoublewireWorkorderService.updateSortById(id);
        return AjaxResult.success();
    }

    /**
     * 新增数据
     *
     * @param mesDoublewireWorkorder 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MesDoublewireWorkorder mesDoublewireWorkorder) throws ParseException {
        return AjaxResult.success(this.mesDoublewireWorkorderService.insert(mesDoublewireWorkorder));
    }

    /**
     * 编辑数据
     *
     * @param mesDoublewireWorkorder 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MesDoublewireWorkorder mesDoublewireWorkorder) {
        return AjaxResult.success(this.mesDoublewireWorkorderService.update(mesDoublewireWorkorder));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteByIds(@PathVariable("id") BigInteger[] ids) {
        return toAjax(this.mesDoublewireWorkorderService.deleteByIds(ids));
    }
    /**
     * 通过工单id更改状态
     */
    @ApiOperation("通过工单id更改状态")
    @Log(title = "并丝工单", businessType = BusinessType.UPDATE)
    @PutMapping("updateStatus/{id}/{status}")
    public AjaxResult updateStatusById(@PathVariable(value = "id") BigInteger id , @PathVariable(value = "status")  String status){

        return AjaxResult.success(mesDoublewireWorkorderService.updateStatusById(id,status));
    }

    /**
     * 并丝插单
     */
    @ApiOperation("并丝插单")
    @Log(title = "并丝工单", businessType = BusinessType.UPDATE)
    @PutMapping("insertWorkorder")
    public AjaxResult insertWorkorder(@RequestBody MesDoublewireWorkorder mesDoublewireWorkorder){

        return toAjax(mesDoublewireWorkorderService.insertWorkorder(mesDoublewireWorkorder));
    }
    /**
     * 插单工单完工
     */
    @ApiOperation("插单完成")
    @Log(title = "并丝工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancelInsertWorkorder/{id}")
    public AjaxResult cancelInsertWorkorder(@PathVariable BigInteger id){

        return toAjax(mesDoublewireWorkorderService.cancelInsertWorkorder(id));
    }

    /**
     * 插单取消
     */
    @ApiOperation("插单取消")
    @Log(title = "并丝工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancelWorkorder/{id}")
    public AjaxResult cancelWorkorder(@PathVariable BigInteger id){

        return toAjax(mesDoublewireWorkorderService.cancelWorkorder(id));
    }

    /**
     * 并丝工单作废
     */
    @ApiOperation("并丝工单作废")
    @Log(title = "并丝工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancel/{id}")
    public AjaxResult  cancelById(@PathVariable BigInteger id){
        return toAjax(mesDoublewireWorkorderService.cancelById(id));
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
        List<MesDoublewireWorkorder> list =mesDoublewireWorkorderService.getListByEquipmentId(equipmentId,axleNumber);
        return AjaxResult.success(list);
    }


}

