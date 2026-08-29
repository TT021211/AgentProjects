package com.cuixing.eq.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.eq.entity.EqRepairReport;
import com.cuixing.eq.service.IEqRepairReportService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 设备报修单;(eq_repair_report)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-7-1
 */
@Api(tags = "设备报修单对象功能接口")
@RestController
@RequestMapping("/eqRepairReport")
public class EqRepairReportController extends BaseController{
    @Autowired
    private IEqRepairReportService iEqRepairReportService;


     /**
      * 通过ID查询报修单
      *
      * @param id 主键
      * @return 实例对象
      */
     @ApiOperation(value = "通过ID查询报修单",notes = "权限字符:eq:eqRepairReport:list")
     //@RequiresPermissions("eq:eqRepairReport:list")
     @GetMapping("{id}")
     public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
         return AjaxResult.success(iEqRepairReportService.queryById(id));
     }

     /**
     * 通过设备ID查询报修单
     *
     * @param workbenchEnamellingId 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过漆包机台ID查询报修单")
    @GetMapping("getByWorkbenchEnamellingId/{workbenchEnamellingId}")
    public AjaxResult getByWorkbenchEnamellingId(@PathVariable(value = "workbenchEnamellingId") BigInteger workbenchEnamellingId){
        return AjaxResult.success(iEqRepairReportService.getByWorkbenchEnamellingId(workbenchEnamellingId));
    }



    /**
     * 分页查询
     *
     * @param eqRepairReport 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqRepairReport:list")
    //@RequiresPermissions("eq:eqRepairReport:list")
    @GetMapping("/list")
    public TableDataInfo list(EqRepairReport eqRepairReport){
        startPage();
        List<EqRepairReport> list =iEqRepairReportService.selectEqRepairReportList(eqRepairReport);
        return getDataTable(list);
    }

     /**
      * 分页获取设备知识库列表
      *
      * @param eqRepairReport 筛选条件
      * @return 查询结果
      */
     @ApiOperation(value = "分页获取设备知识库列表", notes = "权限字符:eq:eqRepairReport:list")
     //@RequiresPermissions("eq:eqRepairReport:list")
     @GetMapping("/getKnowledgeList")
     public TableDataInfo knowledgeList(EqRepairReport eqRepairReport){
         startPage();
         List<EqRepairReport> list =iEqRepairReportService.knowledgeList(eqRepairReport);
         return getDataTable(list);
     }

    /** 
     * 新增数据
     *
     * @param eqRepairReport 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqRepairReport:add")
    //@RequiresPermissions("eq:eqRepairReport:add")
    @Log(title = "设备报修单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqRepairReport eqRepairReport){
        return toAjax(iEqRepairReportService.insert(eqRepairReport));
    }


    /** 
     * 更新数据
     *
     * @param eqRepairReport 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqRepairReport:update")
    //@RequiresPermissions("eq:eqRepairReport:update")
    @Log(title = "设备报修单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqRepairReport eqRepairReport){
        return toAjax(iEqRepairReportService.update(eqRepairReport));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqRepairReport:remove")
    //@RequiresPermissions("eq:eqRepairReport:remove")
    @Log(title = "设备报修单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqRepairReportService.deleteByIds(ids));
    }

     /**
      * 写入设备知识库
      *
      * @return 实例对象
      */
     @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqRepairReport:add")
     //@RequiresPermissions("eq:eqRepairReport:add")
     @Log(title = "设备知识库", businessType = BusinessType.INSERT)
     @PutMapping("/addKnowledge/{id}")
     public AjaxResult addKnowledge(@PathVariable(value = "id") BigInteger id){
         return toAjax(iEqRepairReportService.addKnowledge(id));
     }
     /**
      * 通过主键删除知识库数据
      *
      * @param ids 主键
      * @return 是否成功
      */
     @ApiOperation(value = "通过主键删除知识库数据", notes = "权限字符:eq:eqRepairReport:remove")
     //@RequiresPermissions("eq:eqRepairReport:remove")
     @Log(title = "设备报修单", businessType = BusinessType.DELETE)
     @DeleteMapping("/deleteKnowledgeByIds/{ids}")
     public AjaxResult deleteKnowledgeByIds(@PathVariable BigInteger[] ids){
         return toAjax(iEqRepairReportService.deleteKnowledgeByIds(ids));
     }


}