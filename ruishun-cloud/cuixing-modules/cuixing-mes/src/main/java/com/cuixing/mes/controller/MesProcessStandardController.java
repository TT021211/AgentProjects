package com.cuixing.mes.controller;

import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.system.api.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.mes.entity.MesProcessStandard;
import com.cuixing.mes.service.IMesProcessStandardService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

 /**
 * 工艺标准;(mes_process_standard)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-7-23
 */
@Api(tags = "工艺标准对象功能接口")
@RestController
@RequestMapping("/mesProcessStandard")
public class MesProcessStandardController extends BaseController{
    @Autowired
    private IMesProcessStandardService iMesProcessStandardService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:mes:mesProcessStandard:query")
    //@RequiresPermissions("mes:mesProcessStandard:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMesProcessStandardService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param mesProcessStandard 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:mes:mesProcessStandard:list")
    //@RequiresPermissions("mes:mesProcessStandard:list")
    @GetMapping("/list")
    public TableDataInfo list(MesProcessStandard mesProcessStandard){
        startPage();
        List<MesProcessStandard> list =iMesProcessStandardService.selectMesProcessStandardList(mesProcessStandard);
        return getDataTable(list);
    }

     /**
      * 查询标准数据
      *
      * @return 查询结果
      */
     @ApiOperation("查询内控标准数据")
     //@RequiresPermissions("")
     @GetMapping("/selectAll/{standardCode}")
     public AjaxResult selectAll(@PathVariable(value = "standardCode") String standardCode){
         List<MesProcessStandard> list = iMesProcessStandardService.getMesProcessStandardList(standardCode);
         return AjaxResult.success(list);
     }
     /**
      * 导出数据
      *
      * @return 实例对象
      */
     @ApiOperation("导出数据")
     @Log(title = "工艺标准", businessType = BusinessType.EXPORT)
     @PostMapping("/export")
     public void export(HttpServletResponse response, MesProcessStandard mesProcessStandard) {
         List<MesProcessStandard> list = iMesProcessStandardService.selectMesProcessStandardList(mesProcessStandard);
         ExcelUtil<MesProcessStandard> util = new ExcelUtil<>(MesProcessStandard.class);
         util.exportExcel(response, list, "工艺标准数据");
     }

     /**
      * 导入数据
      *
      * @return 实例对象
      */
     @ApiOperation("导入数据")
     @Log(title = "工艺标准", businessType = BusinessType.EXPORT)
     @PostMapping("/import/{standardCode}")
     public AjaxResult importExcel(@PathVariable("standardCode")String standardCode,@RequestParam("file") MultipartFile file) throws Exception {
         ExcelUtil<MesProcessStandard> util = new ExcelUtil<>(MesProcessStandard.class);
         List<MesProcessStandard> mesProcessStandards = util.importExcel(file.getInputStream());
         int resutl = 0;
         for (MesProcessStandard mesProcessStandard : mesProcessStandards) {
             mesProcessStandard.setStandardCode(standardCode);
             resutl += iMesProcessStandardService.insert(mesProcessStandard);
         }
         if (resutl > 0){
             return AjaxResult.success(resutl);
         }else{
             return AjaxResult.error("未导入数据");
         }

     }

     /**
      * 下载模板
      * @return
      */
     @PostMapping("/importTemplate")
     public void importTemplate(HttpServletResponse response)
     {
         ExcelUtil<MesProcessStandard> util = new ExcelUtil<MesProcessStandard>(MesProcessStandard.class);
         util.importTemplateExcel(response,"标准模板");
     }
    
    /** 
     * 新增数据
     *
     * @param mesProcessStandard 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:mes:mesProcessStandard:add")
    //@RequiresPermissions("mes:mesProcessStandard:add")
    @Log(title = "工艺标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProcessStandard mesProcessStandard){
        return toAjax(iMesProcessStandardService.insert(mesProcessStandard));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mesProcessStandard 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:mes:mesProcessStandard:update")
    //@RequiresPermissions("mes:mesProcessStandard:update")
    @Log(title = "工艺标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProcessStandard mesProcessStandard){
        return toAjax(iMesProcessStandardService.update(mesProcessStandard));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:mes:mesProcessStandard:remove")
    //@RequiresPermissions("mes:mesProcessStandard:remove")
    @Log(title = "工艺标准", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMesProcessStandardService.deleteByIds(ids));
    }
}