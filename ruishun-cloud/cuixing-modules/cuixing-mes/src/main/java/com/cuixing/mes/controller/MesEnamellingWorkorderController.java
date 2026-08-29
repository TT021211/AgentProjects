package com.cuixing.mes.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.mes.entity.MesEnamellingWorkorder;
import com.cuixing.mes.service.IMesEnamellingWorkorderService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 漆包工单;(mes_enamelling_workorder)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Api(tags = "漆包工单对象功能接口")
@RestController
@RequestMapping("/mesEnamellingWorkorder")
public class MesEnamellingWorkorderController extends BaseController{
    @Autowired
    private IMesEnamellingWorkorderService iMesEnamellingWorkorderService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMesEnamellingWorkorderService.queryById(id));
    }

     /**
      * 通过生产工单id查询单条数据
      *
      * @param workorderId 主键
      * @return 实例对象
      */
     @ApiOperation("通过ID查询单条数据")
     @GetMapping("queryByWorkorderId/{workorderId}")
     public AjaxResult queryByWorkorderId(@PathVariable(value = "workorderId") BigInteger workorderId){
         return AjaxResult.success(iMesEnamellingWorkorderService.queryByWorkorderId(workorderId));
     }


     /**
     * 分页查询
     *
     * @param mesEnamellingWorkorder 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MesEnamellingWorkorder mesEnamellingWorkorder){
        startPage();
        List<MesEnamellingWorkorder> list =iMesEnamellingWorkorderService.selectMesEnamellingWorkorderList(mesEnamellingWorkorder);
        return getDataTable(list);
    }

    /**
     * 新增数据
     *
     * @param mesEnamellingWorkorder 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "漆包工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesEnamellingWorkorder mesEnamellingWorkorder){
        return toAjax(iMesEnamellingWorkorderService.insert(mesEnamellingWorkorder));
    }


    /**
     * 更新数据
     *
     * @param mesEnamellingWorkorder 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "漆包工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesEnamellingWorkorder mesEnamellingWorkorder){
        return toAjax(iMesEnamellingWorkorderService.update(mesEnamellingWorkorder));
    }

     /**
      * 修改排序
      *
      * @return 查询结果
      */
     @ApiOperation("修改排序")
     @PutMapping("/updateSort/{id}")
     public AjaxResult updateSort(@PathVariable(value = "id")BigInteger id){
         iMesEnamellingWorkorderService.updateSortById(id);
         return AjaxResult.success();
     }


    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "漆包工单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMesEnamellingWorkorderService.deleteByIds(ids));
    }

     /**
      * 漆包工单作废
      */
     @ApiOperation("漆包工单作废")
     @Log(title = "漆包工单", businessType = BusinessType.UPDATE)
     @PutMapping("cancel/{id}")
     public AjaxResult cancelById(@PathVariable BigInteger id){
         return toAjax(iMesEnamellingWorkorderService.cancelById(id));
     }

     /**
      * 漆包插单
      */
     @ApiOperation("漆包插单")
     @Log(title = "漆包工单", businessType = BusinessType.UPDATE)
     @PutMapping("insertWorkorder")
     public AjaxResult insertWorkorder(@RequestBody MesEnamellingWorkorder mesEnamellingWorkorder){

         return toAjax(iMesEnamellingWorkorderService.insertWorkorder(mesEnamellingWorkorder));
     }
     /**
      * 插单工单完工
      */
     @ApiOperation("插单完成")
     @Log(title = "漆包工单", businessType = BusinessType.UPDATE)
     @PutMapping("cancelInsertWorkorder/{id}")
     public AjaxResult cancelInsertWorkorder(@PathVariable BigInteger id){

         return toAjax(iMesEnamellingWorkorderService.cancelInsertWorkorder(id));
     }

     /**
      * 插单取消
      */
     @ApiOperation("插单取消")
     @Log(title = "漆包工单", businessType = BusinessType.UPDATE)
     @PutMapping("cancelWorkorder/{id}")
     public AjaxResult cancelWorkorder(@PathVariable BigInteger id){

         return toAjax(iMesEnamellingWorkorderService.cancelWorkorder(id));
     }

     /**
      * 通过工单id更改状态
      */
     @ApiOperation("通过工单id更改状态")
     @Log(title = "漆包工单", businessType = BusinessType.UPDATE)
     @PutMapping("updateStatus/{id}/{status}")
     public AjaxResult updateStatusById(@PathVariable(value = "id")  BigInteger id ,@PathVariable(value = "status")  String status){

         return AjaxResult.success(iMesEnamellingWorkorderService.updateStatusById(id,status));
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
         List<MesEnamellingWorkorder> list =iMesEnamellingWorkorderService.getListByEquipmentId(equipmentId,axleNumber);
         return AjaxResult.success(list);
     }

     /**
      * 漆包看板综合汇总数据
      */
     @ApiOperation("获取漆包看板综合汇总数据")
     @GetMapping("/getEnamellingSummaryData")
     public AjaxResult getEnamellingSummaryData(){
         return AjaxResult.success(iMesEnamellingWorkorderService.getEnamellingSummaryData());
     }




 }
