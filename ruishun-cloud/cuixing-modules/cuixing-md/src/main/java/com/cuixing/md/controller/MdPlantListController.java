package com.cuixing.md.controller;

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
import com.cuixing.md.entity.MdPlantList;
import com.cuixing.md.service.IMdPlantListService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 油漆清单;(md_plant_list)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-8-8
 */
@Api(tags = "油漆清单对象功能接口")
@RestController
@RequestMapping("/mdPlantList")
public class MdPlantListController extends BaseController{
    @Autowired
    private IMdPlantListService iMdPlantListService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:md:mdPlantList:query")
    //@RequiresPermissions("md:mdPlantList:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdPlantListService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param mdPlantList 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:md:mdPlantList:list")
    //@RequiresPermissions("md:mdPlantList:list")
    @GetMapping("/list")
    public TableDataInfo list(MdPlantList mdPlantList){
        startPage();
        List<MdPlantList> list =iMdPlantListService.selectMdPlantListList(mdPlantList);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param mdPlantList 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:md:mdPlantList:add")
    //@RequiresPermissions("md:mdPlantList:add")
    @Log(title = "油漆清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdPlantList mdPlantList){
        return toAjax(iMdPlantListService.insert(mdPlantList));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mdPlantList 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:md:mdPlantList:update")
    //@RequiresPermissions("md:mdPlantList:update")
    @Log(title = "油漆清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdPlantList mdPlantList){
        return toAjax(iMdPlantListService.update(mdPlantList));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:md:mdPlantList:remove")
    //@RequiresPermissions("md:mdPlantList:remove")
    @Log(title = "油漆清单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdPlantListService.deleteByIds(ids));
    }
}
