package com.cuixing.crm.controller;

import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.crm.entity.CrmClient;
import com.cuixing.crm.entity.vo.CrmClientAnalyze;
import com.cuixing.crm.service.ICrmClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.log.annotation.Log;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * 客户表;(crm_client)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "客户表对象功能接口")
@RestController
@RequestMapping("crmClient")
public class CrmClientController extends BaseController {

    @Autowired
    private ICrmClientService crmClientService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据",notes = "权限字符:crm:client:query")
    //@RequiresPermissions("crm:client:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // crmClientService.checkCrmClientDataScope(id);
        return AjaxResult.success(crmClientService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation(value = "导出数据",notes = "权限字符:crm:client:export")
    //@RequiresPermissions("crm:client:export")
    @Log(title = "客户表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrmClient crmClient)
    {
        List<CrmClient> list = crmClientService.selectCrmClientList(crmClient);
        ExcelUtil<CrmClient> util = new ExcelUtil<CrmClient>(CrmClient.class);
        util.exportExcel(response, list, "客户表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询",notes = "权限字符:crm:client:list")
    //@RequiresPermissions("crm:client:list")
    @GetMapping("/list")
    public TableDataInfo list(CrmClient crmClient)
    {
        startPage();
        List<CrmClient> list = crmClientService.selectCrmClientList(crmClient);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation(value = "查询所有数据",notes = "权限字符:crm:client:list")
    //@RequiresPermissions("crm:client:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(CrmClient crmClient){
        List<CrmClient> list = crmClientService.selectCrmClientList(crmClient);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param crmClient 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据",notes = "权限字符:crm:client:add")
    //@RequiresPermissions("crm:client:add")
    @Log(title = "客户表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmClient crmClient){
        return toAjax(crmClientService.insert(crmClient));
    }
    /**
     * 批量新增数据
     *
     * @param crmClients 实例对象数组
     * @return 实例对象
     */
    @ApiOperation(value = "批量新增数据",notes = "权限字符:crm:client:add")
    //@RequiresPermissions("crm:client:add")
    @Log(title = "客户表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<CrmClient> crmClients){
        return AjaxResult.success(crmClientService.insertBatch(crmClients));
    }

    /**
     * 更新数据
     *
     * @param crmClient 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据",notes = "权限字符:crm:client:update")
    //@RequiresPermissions("crm:client:update")
    @Log(title = "客户表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrmClient crmClient){
        return toAjax(crmClientService.update(crmClient));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键删除数据",notes = "权限字符:crm:client:remove")
    //@RequiresPermissions("crm:client:remove")
    @Log(title = "客户表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(crmClientService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据",notes = "权限字符:crm:crmAccessory:remove")
    //@RequiresPermissions("crm:client:remove")
    @Log(title = "客户表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(crmClientService.deleteByIds(ids));
    }


    /**
     * 客户分析接口
     *
     * @return 查询结果
     */
    @ApiOperation(value = "客户分析接口",notes = "权限字符:crm:client:list")
    //@RequiresPermissions("crm:client:list")
    @GetMapping("/getClientAnalyze")
    public AjaxResult getClientAnalyze()
    {
        CrmClientAnalyze clientAnalyze = crmClientService.getClientAnalyze();
        return AjaxResult.success(clientAnalyze);
    }

    /**
     * 查询id列表
     *
     * @return 查询结果
     */
    @ApiOperation(value = "查询id列表",notes = "权限字符:crm:client:list")
    //@RequiresPermissions("crm:client:list")
    @GetMapping("/selectIds")
    public AjaxResult selectIds()
    {
        Set<BigInteger> ids = crmClientService.selectIds();
        return AjaxResult.success(ids);
    }

}