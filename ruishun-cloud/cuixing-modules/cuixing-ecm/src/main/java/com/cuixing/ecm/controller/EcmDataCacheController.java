package com.cuixing.ecm.controller;

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
import com.cuixing.ecm.domain.EcmDataCache;
import com.cuixing.ecm.service.IEcmDataCacheService;
import java.math.BigInteger;
import java.util.List;

 /**
 * 用电量数据缓存;(ecm_data_cache)表控制层
 * @author : http://www.chiner.pro
 * @date : 2025-2-13
 */
@Api(tags = "用电量数据缓存对象功能接口")
@RestController
@RequestMapping("/ecmDataCache")
public class EcmDataCacheController extends BaseController{
    @Autowired
    private IEcmDataCacheService iEcmDataCacheService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:ecm:ecmDataCache:query")
    @RequiresPermissions("ecm:ecmDataCache:query")
    @GetMapping("/{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
        return AjaxResult.success(iEcmDataCacheService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param ecmDataCache 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:ecm:ecmDataCache:list")
    @RequiresPermissions("ecm:ecmDataCache:list")
    @GetMapping("/list")
    public TableDataInfo list(EcmDataCache ecmDataCache){
        startPage();
        List<EcmDataCache> list =iEcmDataCacheService.selectEcmDataCacheList(ecmDataCache);
        return getDataTable(list);
    }
    
    /** 
     * 新增数据
     *
     * @param ecmDataCache 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:ecm:ecmDataCache:add")
    @RequiresPermissions("ecm:ecmDataCache:add")
    @Log(title = "用电量数据缓存", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EcmDataCache ecmDataCache){
        return toAjax(iEcmDataCacheService.insert(ecmDataCache));
    }
    
    
    /** 
     * 更新数据
     *
     * @param ecmDataCache 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:ecm:ecmDataCache:update")
    @RequiresPermissions("ecm:ecmDataCache:update")
    @Log(title = "用电量数据缓存", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EcmDataCache ecmDataCache){
        return toAjax(iEcmDataCacheService.update(ecmDataCache));
    }
    
    
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:ecm:ecmDataCache:remove")
    @RequiresPermissions("ecm:ecmDataCache:remove")
    @Log(title = "用电量数据缓存", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(iEcmDataCacheService.deleteByIds(ids));
    }
}