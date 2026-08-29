package com.cuixing.md.controller;

import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.md.entity.MdProductType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.md.entity.MdPlantChangeLog;
import com.cuixing.md.service.IMdPlantChangeLogService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

 /**
 * 油漆更换记录;(md_plant_change_log)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@Api(tags = "油漆更换记录对象功能接口")
@RestController
@RequestMapping("/mdPlantChangeLog")
public class MdPlantChangeLogController extends BaseController{
    @Autowired
    private IMdPlantChangeLogService iMdPlantChangeLogService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdPlantChangeLogService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param mdPlantChangeLog 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MdPlantChangeLog mdPlantChangeLog){
        startPage();
        List<MdPlantChangeLog> list =iMdPlantChangeLogService.selectMdPlantChangeLogList(mdPlantChangeLog);
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
     public void export(HttpServletResponse response, MdPlantChangeLog mdPlantChangeLog) {
         List<MdPlantChangeLog> list = iMdPlantChangeLogService.selectMdPlantChangeLogList(mdPlantChangeLog);
         ExcelUtil<MdPlantChangeLog> util = new ExcelUtil<>(MdPlantChangeLog.class);
         util.exportExcel(response, list, "产品分类表数据");
     }


     /**
     * 新增数据
     *
     * @param mdPlantChangeLog 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "油漆更换记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdPlantChangeLog mdPlantChangeLog){
        return toAjax(iMdPlantChangeLogService.insert(mdPlantChangeLog));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mdPlantChangeLog 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "油漆更换记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdPlantChangeLog mdPlantChangeLog){
        return toAjax(iMdPlantChangeLogService.update(mdPlantChangeLog));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "油漆更换记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdPlantChangeLogService.deleteByIds(ids));
    }
}
