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
import com.cuixing.md.entity.MdIngredientsTraders;
import com.cuixing.md.service.IMdIngredientsTradersService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 贸易商设置;(md_ingredients_traders)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@Api(tags = "贸易商设置对象功能接口")
@RestController
@RequestMapping("/mdIngredientsTraders")
public class MdIngredientsTradersController extends BaseController{
    @Autowired
    private IMdIngredientsTradersService iMdIngredientsTradersService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdIngredientsTradersService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param mdIngredientsTraders 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public TableDataInfo list(MdIngredientsTraders mdIngredientsTraders){
        startPage();
        List<MdIngredientsTraders> list =iMdIngredientsTradersService.selectMdIngredientsTradersList(mdIngredientsTraders);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param mdIngredientsTraders 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @Log(title = "贸易商设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdIngredientsTraders mdIngredientsTraders){
        return toAjax(iMdIngredientsTradersService.insert(mdIngredientsTraders));
    }
    
    
    /** 
     * 更新数据
     *
     * @param mdIngredientsTraders 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @Log(title = "贸易商设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdIngredientsTraders mdIngredientsTraders){
        return toAjax(iMdIngredientsTradersService.update(mdIngredientsTraders));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @Log(title = "贸易商设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdIngredientsTradersService.deleteByIds(ids));
    }
}
