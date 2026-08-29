package com.cuixing.mes.controller;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.system.api.RemoteSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.cuixing.mes.entity.MesProductWorkorder;
import com.cuixing.mes.service.IMesProductWorkorderService;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 计划工单;(mes_product_workorder)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Api(tags = "生产工单对象功能接口")
@RestController
@RequestMapping("/mesProductWorkorder")
public class MesProductWorkorderController extends BaseController{
    @Autowired
    private IMesProductWorkorderService iMesProductWorkorderService;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMesProductWorkorderService.queryById(id));
    }

    /**
     * 通过生产工单号查询单条数据
     *
     * @param workorderCode
     * @return 实例对象
     */
    @ApiOperation("通过生产工单号查询单条数据")
    @GetMapping("queryByWorkorderCode/{workorderCode}")
    public AjaxResult queryByWorkorderCode(@PathVariable(value = "workorderCode") String workorderCode){
        return AjaxResult.success(iMesProductWorkorderService.queryByWorkorderCode(workorderCode));
    }

     /**
      * 查询所有数据
      *
      * @return 查询结果
      */
     @ApiOperation("查询所有数据")
//    @RequiresPermissions("mes:workorder:list")
     @GetMapping("/selectAll")
     public AjaxResult selectAll(MesProductWorkorder mesProductWorkorder){
         List<MesProductWorkorder> list = iMesProductWorkorderService.selectMesProductWorkorderList(mesProductWorkorder);
         return AjaxResult.success(list);
     }


    /**
     * 分页查询
     *
     * @param mesProductWorkorder 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MesProductWorkorder mesProductWorkorder){
        startPage();
        List<MesProductWorkorder> list =iMesProductWorkorderService.selectMesProductWorkorderList(mesProductWorkorder);
        return getDataTable(list);
    }



    /**
     * 新增数据
     *
     * @param mesProductWorkorders 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "生产工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody List<MesProductWorkorder> mesProductWorkorders) throws ParseException {
        int result = 0;
        for (MesProductWorkorder mesProductWorkorder : mesProductWorkorders) {
            result += iMesProductWorkorderService.insert(mesProductWorkorder);
        }
        return toAjax(result);
    }


    /**
     * 更新数据
     *
     * @param mesProductWorkorder 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "生产工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductWorkorder mesProductWorkorder){
        return toAjax(iMesProductWorkorderService.update(mesProductWorkorder));
    }

    /**
     * 根据id进行状态变更
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID变更状态")
    @PutMapping("/updateStatus/{id}/{status}")
    public AjaxResult updateStatusById(@PathVariable(value = "id") BigInteger id,@PathVariable(value = "status") String status){
        return AjaxResult.success(iMesProductWorkorderService.updateStatus(id,status));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "生产工单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMesProductWorkorderService.deleteByIds(ids));
    }
    /**
     * 生产工单作废
     */
    @ApiOperation("生产工单作废")
    @Log(title = "生产工单", businessType = BusinessType.UPDATE)
    @PutMapping("cancel/{id}")
    public AjaxResult cancelById(@PathVariable BigInteger id){
        return toAjax(iMesProductWorkorderService.cancelById(id));
    }

    /**
     * 生产工单看板数据接口
     */
    @ApiOperation("生产工单看板数据接口")
    @GetMapping("/getMesWorkOrdersData")
    public AjaxResult getMesWorkOrdersData(){
        return AjaxResult.success(iMesProductWorkorderService.getMesWorkOrdersData());
    }

    /**
     * 查询一周内排单数量以及完成数量
     *
     * @return 实例对象
     */
    @ApiOperation("页面看板")
    @GetMapping("/getkanbanPage")
    public AjaxResult getKanbanPage(){
        return AjaxResult.success(iMesProductWorkorderService.getKanbanPage());
    }
}
