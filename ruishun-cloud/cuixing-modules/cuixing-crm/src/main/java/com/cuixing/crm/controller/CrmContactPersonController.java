package com.cuixing.crm.controller;

import com.cuixing.common.core.utils.poi.ExcelUtil;
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
import com.cuixing.crm.entity.CrmContactPerson;
import com.cuixing.crm.service.ICrmContactPersonService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;

 /**
 * 客户联系人;(crm_contact_person)表控制层
 * @author : http://www.chiner.pro
 * @date : 2025-2-8
 */
@Api(tags = "客户联系人对象功能接口")
@RestController
@RequestMapping("/crmContactPerson")
public class CrmContactPersonController extends BaseController{
    @Autowired
    private ICrmContactPersonService iCrmContactPersonService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:crm:crmContactPerson:query")
    //@RequiresPermissions("crm:crmContactPerson:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iCrmContactPersonService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param crmContactPerson 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:crm:crmContactPerson:list")
    //@RequiresPermissions("crm:crmContactPerson:list")
    @GetMapping("/list")
    public TableDataInfo list(CrmContactPerson crmContactPerson){
        startPage();
        List<CrmContactPerson> list =iCrmContactPersonService.selectCrmContactPersonList(crmContactPerson);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param crmContactPerson 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:crm:crmContactPerson:add")
    //@RequiresPermissions("crm:crmContactPerson:add")
    @Log(title = "客户联系人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmContactPerson crmContactPerson){
        return toAjax(iCrmContactPersonService.insert(crmContactPerson));
    }
    
    
    /** 
     * 更新数据
     *
     * @param crmContactPerson 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:crm:crmContactPerson:update")
    //@RequiresPermissions("crm:crmContactPerson:update")
    @Log(title = "客户联系人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrmContactPerson crmContactPerson){
        return toAjax(iCrmContactPersonService.update(crmContactPerson));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:crm:crmContactPerson:remove")
    //@RequiresPermissions("crm:crmContactPerson:remove")
    @Log(title = "客户联系人", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iCrmContactPersonService.deleteByIds(ids));
    }
     /**
      * 导出数据
      *
      * @return 实例对象
      */
     @ApiOperation("导出数据")
     @Log(title = "客户联系人", businessType = BusinessType.EXPORT)
     @PostMapping("/export")
     public void export(HttpServletResponse response, CrmContactPerson crmContactPerson) {
         List<CrmContactPerson> list = iCrmContactPersonService.selectCrmContactPersonList(crmContactPerson);
         ExcelUtil<CrmContactPerson> util = new ExcelUtil<>(CrmContactPerson.class);
         util.exportExcel(response, list, "客户联系人数据");
     }

     /**
      * 导入数据
      * @return 实例对象
      */
     @ApiOperation("导入数据")
     @Log(title = "客户联系人", businessType = BusinessType.EXPORT)
     @PostMapping("/import")
     public AjaxResult importExcel(@RequestParam("file") MultipartFile file) throws Exception {
         ExcelUtil<CrmContactPerson> util = new ExcelUtil<>(CrmContactPerson.class);
         List<CrmContactPerson> crmContactPersons = util.importExcel(file.getInputStream());
         int resutl = 0;
         for (CrmContactPerson crmContactPerson : crmContactPersons) {
             resutl += iCrmContactPersonService.insert(crmContactPerson);
         }
         if (resutl > 0){
             return AjaxResult.success(resutl);
         }else{
             return AjaxResult.error("未导入数据");
         }

     }
}