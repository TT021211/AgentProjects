package com.cuixing.md.controller;

import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.md.entity.MdPlantChangeLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.md.entity.MdPlantOutinLog;
import com.cuixing.md.service.IMdPlantOutinLogService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;

 /**
 * 油漆出入库记录;(md_plant_outin_log)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@Api(tags = "油漆出入库记录对象功能接口")
@RestController
@RequestMapping("/mdPlantOutinLog")
public class MdPlantOutinLogController extends BaseController{
    @Autowired
    private IMdPlantOutinLogService iMdPlantOutinLogService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdPlantOutinLogService.queryById(id));
    }

     /**
      * 通过油漆条码查询单条数据
      *
      * @param plantCode 油漆条码
      * @return 实例对象
      */
     @ApiOperation("通过油漆条码查询单条数据")
     @GetMapping("selectByPlantCode/{plantCode}")
     public AjaxResult selectByPlantCode(@PathVariable(value = "plantCode") String plantCode){
         return AjaxResult.success(iMdPlantOutinLogService.selectByPlantCode(plantCode));
     }
    
    /** 
     * 分页查询
     *
     * @param mdPlantOutinLog 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MdPlantOutinLog mdPlantOutinLog){
        startPage();
        List<MdPlantOutinLog> list =iMdPlantOutinLogService.selectMdPlantOutinLogList(mdPlantOutinLog);
        return getDataTable(list);
    }
     /**
      * 导出数据
      *
      * @return 实例对象
      */
     @ApiOperation("导出数据")
     @Log(title = "产品分类表", businessType = BusinessType.EXPORT)
     @PostMapping("/export")
     public void export(HttpServletResponse response, MdPlantOutinLog mdPlantOutinLog) {
         List<MdPlantOutinLog> list = iMdPlantOutinLogService.selectMdPlantOutinLogList(mdPlantOutinLog);
         ExcelUtil<MdPlantOutinLog> util = new ExcelUtil<>(MdPlantOutinLog.class);
         util.exportExcel(response, list, "产品分类表数据");
     }
    
    /** 
     * 新增数据
     *
     * @param mdPlantOutinLogs 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "油漆出入库记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody List<MdPlantOutinLog> mdPlantOutinLogs){
        return AjaxResult.success(iMdPlantOutinLogService.insert(mdPlantOutinLogs));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mdPlantOutinLog 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "油漆出入库记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdPlantOutinLog mdPlantOutinLog){
        return toAjax(iMdPlantOutinLogService.update(mdPlantOutinLog));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "油漆出入库记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdPlantOutinLogService.deleteByIds(ids));
    }
}
