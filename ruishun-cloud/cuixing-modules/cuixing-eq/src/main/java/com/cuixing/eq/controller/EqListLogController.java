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
import com.cuixing.eq.entity.EqListLog;
import com.cuixing.eq.service.IEqListLogService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 点检(保养)记录;(eq_list_log)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-7-9
 */
@Api(tags = "点检(保养)记录对象功能接口")
@RestController
@RequestMapping("/eqListLog")
public class EqListLogController extends BaseController{
    @Autowired
    private IEqListLogService iEqListLogService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqListLog:query")
    //@RequiresPermissions("eq:eqListLog:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEqListLogService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param eqListLog 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqListLog:list")
    //@RequiresPermissions("eq:eqListLog:list")
    @GetMapping("/list")
    public TableDataInfo list(EqListLog eqListLog){
        startPage();
        List<EqListLog> list =iEqListLogService.selectEqListLogList(eqListLog);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param eqListLog 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqListLog:add")
    //@RequiresPermissions("eq:eqListLog:add")
    @Log(title = "点检(保养)记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqListLog eqListLog){
        return toAjax(iEqListLogService.insert(eqListLog));
    }
    
    
    /** 
     * 更新数据
     *
     * @param eqListLog 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqListLog:update")
    //@RequiresPermissions("eq:eqListLog:update")
    @Log(title = "点检(保养)记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqListLog eqListLog){
        return toAjax(iEqListLogService.update(eqListLog));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqListLog:remove")
    //@RequiresPermissions("eq:eqListLog:remove")
    @Log(title = "点检(保养)记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEqListLogService.deleteByIds(ids));
    }
}