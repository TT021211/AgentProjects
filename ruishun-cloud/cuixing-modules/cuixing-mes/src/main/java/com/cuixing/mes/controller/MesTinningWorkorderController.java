package com.cuixing.mes.controller;

import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.mes.entity.MesDoublewireWorkorder;
import com.cuixing.mes.entity.MesStrandedwireWorkorder;
import com.cuixing.mes.entity.MesTinningWorkorder;
import com.cuixing.mes.service.MesTinningWorkorderService;
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
 * 镀锡工单(MesTinningWorkorder)表控制层
 *
 * @author 阿江
 * @since 2024-11-25 11:04:48
 */
@Api(tags = "镀锡工单接口")
@RestController
@RequestMapping("/mesTinningWorkorder")
public class MesTinningWorkorderController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private MesTinningWorkorderService mesTinningWorkorderService;

    /**
     * 通过生产工单号查询单条数据
     *
     * @param workorderCode
     * @return 实例对象
     */
    @ApiOperation("通过生产工单号查询单条数据")
    @GetMapping("queryByTinningWorkorderCode/{workorderCode}")
    public AjaxResult queryByTinningWorkorderCode(@PathVariable(value = "workorderCode") String workorderCode){
        return AjaxResult.success(mesTinningWorkorderService.queryByTinningWorkorderCode(workorderCode));
    }

    /**
     * 分页查询
     *
     * @param mesTinningWorkorder 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MesTinningWorkorder mesTinningWorkorder) {
        this.startPage();
        List<MesTinningWorkorder> list = this.mesTinningWorkorderService.selectMesTinningWorkorderList(mesTinningWorkorder);
        return this.getDataTable(list);
    }

    /**
     * 修改排序
     *
     * @return 查询结果
     */
    @ApiOperation("修改排序")
    @PutMapping("/updateSort/{id}")
    public AjaxResult updateSort(@PathVariable(value = "id")BigInteger id){
        mesTinningWorkorderService.updateSortById(id);
        return AjaxResult.success();
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") BigInteger id) {
        return AjaxResult.success(this.mesTinningWorkorderService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param mesTinningWorkorder 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MesTinningWorkorder mesTinningWorkorder) throws ParseException {
        return AjaxResult.success(this.mesTinningWorkorderService.insert(mesTinningWorkorder));
    }

    /**
     * 编辑数据
     *
     * @param mesTinningWorkorder 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MesTinningWorkorder mesTinningWorkorder) {
        return AjaxResult.success(this.mesTinningWorkorderService.update(mesTinningWorkorder));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteByIds(@PathVariable("id") BigInteger[] ids) {
        return toAjax(this.mesTinningWorkorderService.deleteByIds(ids));
    }
    /**
     * 通过工单id更改状态
     */
    @ApiOperation("通过工单id更改状态")
    @Log(title = "镀锡工单", businessType = BusinessType.UPDATE)
    @PutMapping("updateStatus/{id}/{status}")
    public AjaxResult updateStatusById(@PathVariable(value = "id") BigInteger id , @PathVariable(value = "status")  String status){

        return AjaxResult.success(mesTinningWorkorderService.updateStatusById(id,status));
    }


    /**
     * 镀锡插单
     */
    @ApiOperation("并丝插单")
    @Log(title = "镀锡工单", businessType = BusinessType.UPDATE)
    @PutMapping("insertWorkorder")
    public AjaxResult insertWorkorder(@RequestBody MesTinningWorkorder mesTinningWorkorder){

        return toAjax(mesTinningWorkorderService.insertWorkorder(mesTinningWorkorder));
    }

    /**
     * 插单工单完工
     */
    @ApiOperation("插单完成")
    @Log(title = "镀锡工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancelInsertWorkorder/{id}")
    public AjaxResult cancelInsertWorkorder(@PathVariable BigInteger id){

        return toAjax(mesTinningWorkorderService.cancelInsertWorkorder(id));
    }

    /**
     * 插单取消
     */
    @ApiOperation("插单取消")
    @Log(title = "镀锡工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancelWorkorder/{id}")
    public AjaxResult cancelWorkorder(@PathVariable BigInteger id){

        return toAjax(mesTinningWorkorderService.cancelWorkorder(id));
    }

    /**
     * 并丝工单作废
     */
    @ApiOperation("并丝工单作废")
    @Log(title = "并丝工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancel/{id}")
    public AjaxResult  cancelById(@PathVariable BigInteger id){
        return toAjax(mesTinningWorkorderService.cancelById(id));
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
        List<MesTinningWorkorder> list =mesTinningWorkorderService.getListByEquipmentId(equipmentId,axleNumber);
        return AjaxResult.success(list);
    }

    /**
     *
     */

}

