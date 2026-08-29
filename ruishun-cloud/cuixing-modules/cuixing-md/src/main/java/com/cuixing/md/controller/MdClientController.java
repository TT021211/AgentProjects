package com.cuixing.md.controller;

import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.log.annotation.Log;
import javax.servlet.http.HttpServletResponse;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.system.api.domain.md.MdClient;
import com.cuixing.md.service.IMdClientService;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * 客户表;(md_client)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "客户表对象功能接口")
@RestController
@RequestMapping("/mdClient")
public class MdClientController extends BaseController {

    @Autowired
    private IMdClientService mdClientService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据",notes = "权限字符:md:client:query")
    //@RequiresPermissions("md:client:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mdClientService.checkMdClientDataScope(id);
        return AjaxResult.success(mdClientService.queryById(id));
    }

    @GetMapping("/queryByClientNick")
    public AjaxResult getInfoByClientNick(@RequestParam Set<String> clientNick){
        return AjaxResult.success(mdClientService.getInfoByClientNick(clientNick));
    }
    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation(value = "导出数据",notes = "权限字符:md:client:export")
    //@RequiresPermissions("md:client:export")
    @Log(title = "客户表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdClient mdClient)
    {
        List<MdClient> list = mdClientService.selectMdClientList(mdClient);
        ExcelUtil<MdClient> util = new ExcelUtil<MdClient>(MdClient.class);
        util.exportExcel(response, list, "客户表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询",notes = "权限字符:md:client:list")
    //@RequiresPermissions("md:client:list")
    @GetMapping("/list")
    public TableDataInfo list(MdClient mdClient)
    {
        startPage();
        List<MdClient> list = mdClientService.selectMdClientList(mdClient);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation(value = "查询所有数据",notes = "权限字符:md:client:list")
    //@RequiresPermissions("md:client:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MdClient mdClient){
        List<MdClient> list = mdClientService.selectMdClientList(mdClient);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdClient 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据",notes = "权限字符:md:client:add")
    //@RequiresPermissions("md:client:add")
    @Log(title = "客户表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdClient mdClient){
        return toAjax(mdClientService.insert(mdClient));
    }
    /**
     * 批量新增数据
     *
     * @param mdClients 实例对象数组
     * @return 实例对象
     */
    @ApiOperation(value = "批量新增数据",notes = "权限字符:md:client:add")
    //@RequiresPermissions("md:client:add")
    @Log(title = "客户表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MdClient> mdClients){
        return toAjax(mdClientService.insertBatch(mdClients));
    }

    /**
     * 更新数据
     *
     * @param mdClient 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据",notes = "权限字符:md:client:update")
    //@RequiresPermissions("md:client:update")
    @Log(title = "客户表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdClient mdClient){
        return toAjax(mdClientService.update(mdClient));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键删除数据",notes = "权限字符:md:client:remove")
    //@RequiresPermissions("md:client:remove")
    @Log(title = "客户表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mdClientService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据",notes = "权限字符:md:mdAccessory:remove")
    //@RequiresPermissions("md:client:remove")
    @Log(title = "客户表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mdClientService.deleteByIds(ids));
    }
}
