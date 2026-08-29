package com.cuixing.eq.controller;

import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.eq.entity.EqEquipment;
import com.cuixing.eq.service.IEqEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;

/**
* 设备台账;(eq_equipment)表控制层
* @author : http://www.chiner.pro
* @date : 2024-6-17
*/
@Api(tags = "设备台账对象功能接口")
@RestController
@RequestMapping("/eqEquipment")
public class EqEquipmentController extends BaseController{
   @Autowired
   private IEqEquipmentService iEqEquipmentService;

   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqEquipment:query")
   //@RequiresPermissions("eq:eqEquipment:query")
   @GetMapping("/{id}")
   public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
       return AjaxResult.success(iEqEquipmentService.queryById(id));
   }

   /**
    * 分页查询
    *
    * @param eqEquipment 筛选条件
    * @return 查询结果
    */
   @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqEquipment:list")
   //@RequiresPermissions("eq:eqEquipment:list")
   @GetMapping("/list")
   public TableDataInfo list(EqEquipment eqEquipment){
       startPage();
       List<EqEquipment> list =iEqEquipmentService.selectEqEquipmentList(eqEquipment);
       return getDataTable(list);
   }

    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqEquipment:list")
    //@RequiresPermissions("eq:eqEquipment:list")
    @GetMapping("/list2")
    public List<EqEquipment> list2(){
        List<EqEquipment> list =iEqEquipmentService.selectEqEquipmentList2();
        return list;
    }

    /**
     * 获取设备列表
     *
     * @param eqEquipment 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "获取设备列表", notes = "权限字符:eq:eqEquipment:list")
    //@RequiresPermissions("eq:eqEquipment:list")
    @GetMapping("/selectall")
    public AjaxResult selectall(EqEquipment eqEquipment){
        List<EqEquipment> list =iEqEquipmentService.selectEqEquipmentList(eqEquipment);
        return AjaxResult.success(list);
    }

    /**
     * 获取所有未关联设备列表
     *
     * @return 查询结果
     */
    @ApiOperation(value = "获取所有未关联设备列表", notes = "权限字符:eq:eqEquipment:list")
    //@RequiresPermissions("eq:eqEquipment:list")
    @GetMapping("/selectAllNoRelevance")
    public AjaxResult selectAllNoRelevance(){
        List<EqEquipment> list = iEqEquipmentService.selectEquipmentList();
        return AjaxResult.success(list);
    }

   /**
    * 新增数据
    *
    * @param eqEquipment 实例对象
    * @return 实例对象
    */
   @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqEquipment:add")
   //@RequiresPermissions("eq:eqEquipment:add")
   @Log(title = "设备台账", businessType = BusinessType.INSERT)
   @PostMapping
   public AjaxResult add(@RequestBody EqEquipment eqEquipment){
       return toAjax(iEqEquipmentService.insert(eqEquipment));
   }
    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    @Log(title = "设备台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EqEquipment eqEquipment) {
        List<EqEquipment> list = iEqEquipmentService.selectEqEquipmentList(eqEquipment);
        ExcelUtil<EqEquipment> util = new ExcelUtil<>(EqEquipment.class);
        util.exportExcel(response, list, "产品分类表数据");
    }


   /**
    * 更新数据
    *
    * @param eqEquipment 实例对象
    * @return 实例对象
    */
   @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqEquipment:update")
   //@RequiresPermissions("eq:eqEquipment:update")
   @Log(title = "设备台账", businessType = BusinessType.UPDATE)
   @PutMapping
   public AjaxResult edit(@RequestBody EqEquipment eqEquipment){
       return toAjax(iEqEquipmentService.update(eqEquipment));
   }


   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 是否成功
    */
   @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqEquipment:remove")
   //@RequiresPermissions("eq:eqEquipment:remove")
   @Log(title = "设备台账", businessType = BusinessType.DELETE)
   @DeleteMapping("/{ids}")
   public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
       return toAjax(iEqEquipmentService.deleteByIds(ids));
   }

    /**
     * 综合看板-设备
     */
    /**
     * 漆包看板综合汇总数据
     */
    @ApiOperation("获取漆包看板综合汇总数据")
    @GetMapping("/getEquipmentData")
    public AjaxResult getEquipmentData(){
        return AjaxResult.success(iEqEquipmentService.getEquipmentData());
    }

}