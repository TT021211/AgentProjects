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
import com.cuixing.md.entity.MdPlantType;
import com.cuixing.md.service.IMdPlantTypeService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 油漆类型;(md_plant_type)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@Api(tags = "油漆类型对象功能接口")
@RestController
@RequestMapping("/mdPlantType")
public class MdPlantTypeController extends BaseController{
    @Autowired
    private IMdPlantTypeService iMdPlantTypeService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdPlantTypeService.queryById(id));
    }

     /**
      * 查询所有
      *
      * @param mdPlantType 筛选条件
      * @return 查询结果
      */
     @ApiOperation("查询所有")
     @GetMapping("/selectall")
     public AjaxResult selectall(MdPlantType mdPlantType){
         List<MdPlantType> list =iMdPlantTypeService.selectMdPlantTypeList(mdPlantType);
         return AjaxResult.success(list);
     }
    /** 
     * 分页查询
     *
     * @param mdPlantType 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MdPlantType mdPlantType){
        startPage();
        List<MdPlantType> list =iMdPlantTypeService.selectMdPlantTypeList(mdPlantType);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param mdPlantType 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "油漆类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdPlantType mdPlantType){
        return toAjax(iMdPlantTypeService.insert(mdPlantType));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mdPlantType 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "油漆类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdPlantType mdPlantType){
        return toAjax(iMdPlantTypeService.update(mdPlantType));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "油漆类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdPlantTypeService.deleteByIds(ids));
    }
}
