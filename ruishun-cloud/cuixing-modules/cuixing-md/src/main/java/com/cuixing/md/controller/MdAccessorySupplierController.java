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
import com.cuixing.md.entity.MdAccessorySupplier;
import com.cuixing.md.service.IMdAccessorySupplierService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 辅料供应商;(md_accessory_supplier)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-10-29
 */
@Api(tags = "辅料供应商对象功能接口")
@RestController
@RequestMapping("/mdAccessorySupplier")
public class MdAccessorySupplierController extends BaseController{
    @Autowired
    private IMdAccessorySupplierService iMdAccessorySupplierService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:md:mdAccessorySupplier:query")
    //@RequiresPermissions("md:mdAccessorySupplier:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iMdAccessorySupplierService.queryById(id));
    }

    /**
     * 分页查询
     *
     * @param mdAccessorySupplier 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:md:mdAccessorySupplier:list")
    //@RequiresPermissions("md:mdAccessorySupplier:list")
    @GetMapping("/list")
    public TableDataInfo list(MdAccessorySupplier mdAccessorySupplier){
        startPage();
        List<MdAccessorySupplier> list =iMdAccessorySupplierService.selectMdAccessorySupplierList(mdAccessorySupplier);
        return getDataTable(list);
    }

    /**
     * 新增数据
     *
     * @param mdAccessorySupplier 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:md:mdAccessorySupplier:add")
    //@RequiresPermissions("md:mdAccessorySupplier:add")
    @Log(title = "辅料供应商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdAccessorySupplier mdAccessorySupplier){
        return toAjax(iMdAccessorySupplierService.insert(mdAccessorySupplier));
    }


    /**
     * 更新数据
     *
     * @param mdAccessorySupplier 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:md:mdAccessorySupplier:update")
    //@RequiresPermissions("md:mdAccessorySupplier:update")
    @Log(title = "辅料供应商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdAccessorySupplier mdAccessorySupplier){
        return toAjax(iMdAccessorySupplierService.update(mdAccessorySupplier));
    }


    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:md:mdAccessorySupplier:remove")
    //@RequiresPermissions("md:mdAccessorySupplier:remove")
    @Log(title = "辅料供应商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iMdAccessorySupplierService.deleteByIds(ids));
    }
}
