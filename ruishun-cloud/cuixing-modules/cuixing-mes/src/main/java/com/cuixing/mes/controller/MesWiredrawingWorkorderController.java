package com.cuixing.mes.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.mes.entity.vo.MoldGaugeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.mes.entity.MesWiredrawingWorkorder;
import com.cuixing.mes.service.IMesWiredrawingWorkorderService;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

 /**
 * 生产工单/拉丝工单;(mes_wiredrawing_workorder)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Api(tags = "拉丝工单对象功能接口")
@RestController
@RequestMapping("/mesWiredrawingWorkorder")
public class MesWiredrawingWorkorderController extends BaseController{
    @Autowired
    private IMesWiredrawingWorkorderService iMesWiredrawingWorkorderService;


     /**
      * 通过id查询单条数据
      *
      * @param id 主键
      * @return 实例对象
      */
     @ApiOperation("通过ID查询单条数据")
     @GetMapping("{id}")
     public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
         return AjaxResult.success(iMesWiredrawingWorkorderService.queryById(id));
     }

    /**
     * 分页查询
     *
     * @param mesWiredrawingWorkorder 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MesWiredrawingWorkorder mesWiredrawingWorkorder){
        startPage();
        List<MesWiredrawingWorkorder> list =iMesWiredrawingWorkorderService.selectMesWiredrawingWorkorderList(mesWiredrawingWorkorder);
        return getDataTable(list);
    }

     /**
      * 根据拉丝生产号查询最新的报工进度、以及对应线盘的满盘预估重量、生产工单号的订单重量
      */
     @ApiOperation("根据拉丝生产号查询最新的报工进度、以及对应线盘的满盘预估重量、生产工单号的订单重量")
     @GetMapping("/getInfoByWiredrawingCode/{wiredrawingCode}")
     public AjaxResult getInfoByWiredrawingCode(@PathVariable(value = "wiredrawingCode") String wiredrawingCode){
         return AjaxResult.success(iMesWiredrawingWorkorderService.getInfoByWiredrawingCode(wiredrawingCode));
     }

    /**
     * 新增数据
     *
     * @param mesWiredrawingWorkorder 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "拉丝工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesWiredrawingWorkorder mesWiredrawingWorkorder) throws ParseException {
        return toAjax(iMesWiredrawingWorkorderService.insert(mesWiredrawingWorkorder));
    }


    /**
     * 更新数据
     *
     * @param mesWiredrawingWorkorder 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "拉丝工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesWiredrawingWorkorder mesWiredrawingWorkorder){
        return toAjax(iMesWiredrawingWorkorderService.update(mesWiredrawingWorkorder));
    }

     /**
      * 修改排序
      *
      * @return 查询结果
      */
     @ApiOperation("修改排序")
     @PutMapping("/updateSort/{id}")
     public AjaxResult updateSort(@PathVariable(value = "id")BigInteger id){
         iMesWiredrawingWorkorderService.updateSortById(id);
         return AjaxResult.success();
     }


    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "拉丝工单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMesWiredrawingWorkorderService.deleteByIds(ids));
    }

     /**
      * 拉丝工单作废
      */
     @ApiOperation("拉丝工单作废")
     @Log(title = "拉丝工单", businessType = BusinessType.UPDATE)
     @PutMapping("cancel/{id}")
     public AjaxResult cancelById(@PathVariable BigInteger id){
         return toAjax(iMesWiredrawingWorkorderService.cancelById(id));
     }

     /**
      * 通过工单id更改状态
      */
     @ApiOperation("拉丝工单状态变更")
     @Log(title = "拉丝工单", businessType = BusinessType.UPDATE)
     @PutMapping("updateStatus/{id}/{status}")
     public AjaxResult updateStatusById(@PathVariable(value = "id")  BigInteger id ,@PathVariable(value = "status")  String status){
         return AjaxResult.success(iMesWiredrawingWorkorderService.updateStatusById(id,status));
     }


 }
