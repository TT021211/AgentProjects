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
import com.cuixing.md.entity.MdAccessory;
import com.cuixing.md.service.IMdAccessoryService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 辅料列表;(md_accessory)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Api(tags = "辅料列表对象功能接口")
@RestController
@RequestMapping("/mdAccessory")
public class MdAccessoryController extends BaseController{
    @Autowired
    private IMdAccessoryService iMdAccessoryService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据",notes = "权限字符:md:mdAccessory:query")
    //@RequiresPermissions("md:mdAccessory:query")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdAccessoryService.queryById(id));
    }

    /**
     * 分页查询
     *
     * @param mdAccessory 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询",notes = "权限字符:md:mdAccessory:list")
    //@RequiresPermissions("md:mdAccessory:query")
    @GetMapping("/list")
    public TableDataInfo list(MdAccessory mdAccessory){
        startPage();
        List<MdAccessory> list =iMdAccessoryService.selectMdAccessoryList(mdAccessory);
        return getDataTable(list);
    }

    /**
     * 新增数据
     *
     * @param mdAccessory 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "分页查询",notes = "权限字符:md:mdAccessory:add")
    //@RequiresPermissions("md:mdAccessory:add")
    @Log(title = "辅料列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdAccessory mdAccessory){
        return toAjax(iMdAccessoryService.insert(mdAccessory));
    }


    /**
     * 更新数据
     *
     * @param mdAccessory 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "分页查询",notes = "权限字符:md:mdAccessory:update")
    //@RequiresPermissions("md:mdAccessory:update")
    @Log(title = "辅料列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdAccessory mdAccessory){
        return toAjax(iMdAccessoryService.update(mdAccessory));
    }


    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "分页查询",notes = "权限字符:md:mdAccessory:remove")
    //@RequiresPermissions("md:mdAccessory:remove")
    @Log(title = "辅料列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdAccessoryService.deleteByIds(ids));
    }
}
