package com.cuixing.mes.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.mes.entity.vo.MesCarryLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.mes.entity.MesCarryLog;
import com.cuixing.mes.service.IMesCarryLogService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 搬运日志;(mes_carry_log)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-10-10
 */
@Api(tags = "搬运日志对象功能接口")
@RestController
@RequestMapping("/mesCarryLog")
public class MesCarryLogController extends BaseController{
    @Autowired
    private IMesCarryLogService iMesCarryLogService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:mes:mesCarryLog:query")
//    @RequiresPermissions("mes:mesCarryLog:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMesCarryLogService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param mesCarryLog 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:mes:mesCarryLog:list")
//    @RequiresPermissions("mes:mesCarryLog:list")
    @GetMapping("/list")
    public TableDataInfo list(MesCarryLog mesCarryLog){
        startPage();
        List<MesCarryLog> list =iMesCarryLogService.selectMesCarryLogList(mesCarryLog);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param mesCarryLogVo 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:mes:mesCarryLog:add")
//    @RequiresPermissions("mes:mesCarryLog:add")
    @Log(title = "搬运日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesCarryLogVo mesCarryLogVo){
        return toAjax(iMesCarryLogService.insert(mesCarryLogVo));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mesCarryLog 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:mes:mesCarryLog:update")
//    @RequiresPermissions("mes:mesCarryLog:update")
    @Log(title = "搬运日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesCarryLog mesCarryLog){
        return toAjax(iMesCarryLogService.update(mesCarryLog));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:mes:mesCarryLog:remove")
//    @RequiresPermissions("mes:mesCarryLog:remove")
    @Log(title = "搬运日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMesCarryLogService.deleteByIds(ids));
    }
}