package com.cuixing.crm.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.crm.entity.CrmClientInterview;
import com.cuixing.crm.service.ICrmClientInterviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;

/**
* 客户访问记录;(crm_client_interview)表控制层
* @author : http://www.chiner.pro
* @date : 2025-1-7
*/
@Api(tags = "客户访问记录对象功能接口")
@RestController
@RequestMapping("/crmClientInterview")
public class CrmClientInterviewController extends BaseController{
   @Autowired
   private ICrmClientInterviewService iCrmClientInterviewService;

   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:crm:crmClientInterview:query")
   //@RequiresPermissions("crm:crmClientInterview:query")
   @GetMapping("/{id}")
   public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
       return AjaxResult.success(iCrmClientInterviewService.queryById(id));
   }

   /**
    * 分页查询
    *
    * @param crmClientInterview 筛选条件
    * @return 查询结果
    */
   @ApiOperation(value = "分页查询", notes = "权限字符:crm:crmClientInterview:list")
   //@RequiresPermissions("crm:crmClientInterview:list")
   @GetMapping("/list")
   public TableDataInfo list(CrmClientInterview crmClientInterview){
       startPage();
       List<CrmClientInterview> list =iCrmClientInterviewService.selectCrmClientInterviewList(crmClientInterview);
       return getDataTable(list);
   }

   /**
    * 新增数据
    *
    * @param crmClientInterview 实例对象
    * @return 实例对象
    */
   @ApiOperation(value = "新增数据", notes = "权限字符:crm:crmClientInterview:add")
   //@RequiresPermissions("crm:crmClientInterview:add")
   @Log(title = "客户访问记录", businessType = BusinessType.INSERT)
   @PostMapping
   public AjaxResult add(@RequestBody CrmClientInterview crmClientInterview){
       return toAjax(iCrmClientInterviewService.insert(crmClientInterview));
   }


   /**
    * 更新数据
    *
    * @param crmClientInterview 实例对象
    * @return 实例对象
    */
   @ApiOperation(value = "更新数据", notes = "权限字符:crm:crmClientInterview:update")
   //@RequiresPermissions("crm:crmClientInterview:update")
   @Log(title = "客户访问记录", businessType = BusinessType.UPDATE)
   @PutMapping
   public AjaxResult edit(@RequestBody CrmClientInterview crmClientInterview){
       return toAjax(iCrmClientInterviewService.update(crmClientInterview));
   }


   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 是否成功
    */
   @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:crm:crmClientInterview:remove")
   //@RequiresPermissions("crm:crmClientInterview:remove")
   @Log(title = "客户访问记录", businessType = BusinessType.DELETE)
   @DeleteMapping("/{ids}")
   public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
       return toAjax(iCrmClientInterviewService.deleteByIds(ids));
   }
}