package com.cuixing.mes.controller;

import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.mes.entity.MesProcessStandard;
import com.cuixing.mes.entity.vo.MesProcessEnterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.mes.entity.MesProcessEnter;
import com.cuixing.mes.service.IMesProcessEnterService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;

 /**
 * 工艺录入;(mes_process_enter)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-6
 */
@Api(tags = "工艺录入对象功能接口")
@RestController
@RequestMapping("/mesProcessEnter")
public class MesProcessEnterController extends BaseController{
    @Autowired
    private IMesProcessEnterService iMesProcessEnterService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMesProcessEnterService.queryById(id));
    }

     /**
      * 通过机台id查询单条数据
      *
      * @param equipmentId 主键
      * @return 实例对象
      */
     @ApiOperation("通过机台id查询单条数据")
     @GetMapping("getInfoByEquipmentId/{equipmentId}/{productSpecification}")
     public AjaxResult getInfoByEquipmentId(@PathVariable(value = "equipmentId") BigInteger equipmentId,@PathVariable(value = "productSpecification") String productSpecification){
         return AjaxResult.success(iMesProcessEnterService.getInfoByEquipmentIdAndSpecification(equipmentId,productSpecification));
     }

     /**
      * 通过机台、规格、产品型号查询单条数据
      *
      * @return 实例对象
      */
     @ApiOperation("通过机台、规格、产品型号查询单条数据")
     @PostMapping("/getInfoByEquipmentCodeAndOther")
     public AjaxResult getInfoByEquipmentCodeAndOther(@RequestBody MesProcessEnterVo mesProcessEnterVo){
         return AjaxResult.success(iMesProcessEnterService.getInfoByEquipmentCodeAndOther(mesProcessEnterVo.getEquipmentCode(),mesProcessEnterVo.getProductSpecification(),mesProcessEnterVo.getProductModel(),mesProcessEnterVo.getAttr1()));
     }
    
    /** 
     * 分页查询
     *
     * @param mesProcessEnter 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MesProcessEnter mesProcessEnter){
        startPage();
        List<MesProcessEnter> list =iMesProcessEnterService.selectMesProcessEnterList(mesProcessEnter);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param mesProcessEnter 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "工艺录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProcessEnter mesProcessEnter){
        return toAjax(iMesProcessEnterService.insert(mesProcessEnter));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mesProcessEnter 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "工艺录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProcessEnter mesProcessEnter){
        return toAjax(iMesProcessEnterService.update(mesProcessEnter));
    }

     /**
      * 导出数据
      *
      * @return 实例对象
      */
     @ApiOperation("导出数据")
     @Log(title = "工艺标准", businessType = BusinessType.EXPORT)
     @PostMapping("/export")
     public void export(HttpServletResponse response, MesProcessEnter mesProcessEnter) {
         List<MesProcessEnter> list = iMesProcessEnterService.selectMesProcessEnterList(mesProcessEnter);
         ExcelUtil<MesProcessEnter> util = new ExcelUtil<>(MesProcessEnter.class);
         util.exportExcel(response, list, "工艺标准数据");
     }

     /**
      * 导入数据
      * @return 实例对象
      */
     @ApiOperation("导入数据")
     @Log(title = "工艺标准", businessType = BusinessType.EXPORT)
     @PostMapping("/import")
     public AjaxResult importExcel(@RequestParam("file") MultipartFile file) throws Exception {
         ExcelUtil<MesProcessEnter> util = new ExcelUtil<>(MesProcessEnter.class);
         List<MesProcessEnter> mesProcessEnters = util.importExcel(file.getInputStream());
         int resutl = 0;
         for (MesProcessEnter mesProcessEnter : mesProcessEnters) {
             resutl += iMesProcessEnterService.insert(mesProcessEnter);
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
         ExcelUtil<MesProcessEnter> util = new ExcelUtil<MesProcessEnter>(MesProcessEnter.class);
         util.importTemplateExcel(response,"标准模板");
     }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "工艺录入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMesProcessEnterService.deleteByIds(ids));
    }


}