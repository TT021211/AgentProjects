package com.cuixing.md.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import com.cuixing.md.entity.MdPlantSupplier;
import com.cuixing.md.service.IMdPlantSupplierService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 油漆供应商;(md_plant_supplier)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@Api(tags = "油漆供应商对象功能接口")
@RestController
@RequestMapping("/mdPlantSupplier")
public class MdPlantSupplierController extends BaseController{
    @Autowired
    private IMdPlantSupplierService iMdPlantSupplierService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdPlantSupplierService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param mdPlantSupplier 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MdPlantSupplier mdPlantSupplier){
        startPage();
        List<MdPlantSupplier> list =iMdPlantSupplierService.selectMdPlantSupplierList(mdPlantSupplier);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param mdPlantSupplier 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "油漆供应商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdPlantSupplier mdPlantSupplier){
        return toAjax(iMdPlantSupplierService.insert(mdPlantSupplier));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mdPlantSupplier 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "油漆供应商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdPlantSupplier mdPlantSupplier){
        return toAjax(iMdPlantSupplierService.update(mdPlantSupplier));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "油漆供应商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdPlantSupplierService.deleteByIds(ids));
    }
}
