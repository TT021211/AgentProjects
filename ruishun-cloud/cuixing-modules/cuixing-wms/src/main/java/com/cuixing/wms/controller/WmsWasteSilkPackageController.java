package com.cuixing.wms.controller;

import com.cuixing.wms.entity.WmsWasteSilkPackage;
import com.cuixing.wms.service.WmsWasteSilkPackageService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

/**
 * 废丝包主表(WmsWasteSilkPackage)表控制层
 *
 * @author 阿江
 * @since 2025-04-18 14:16:23
 */
@Api(tags = "废丝包主表接口")
@RestController
@RequestMapping("/wmsWasteSilkPackage")
public class WmsWasteSilkPackageController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private WmsWasteSilkPackageService wmsWasteSilkPackageService;

    /**
     * 分页查询
     *
     * @param wmsWasteSilkPackage 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(WmsWasteSilkPackage wmsWasteSilkPackage) {
        this.startPage();
        List<WmsWasteSilkPackage> list = this.wmsWasteSilkPackageService.selectWmsWasteSilkPackageList(wmsWasteSilkPackage);
        return this.getDataTable(list);
    }
    
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return AjaxResult.success(this.wmsWasteSilkPackageService.queryById(id));
    }

    /**
     * 通过时间、状态查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/queryByTimeAndStatus/{type}")
    public AjaxResult queryByTimeAndStatus(@PathVariable("type")String type) {
        return AjaxResult.success(this.wmsWasteSilkPackageService.queryByTimeAndStatus(type));
    }
    
    /**
     * 新增数据
     *
     * @param wmsWasteSilkPackage 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody WmsWasteSilkPackage wmsWasteSilkPackage) {
        return AjaxResult.success(this.wmsWasteSilkPackageService.insert(wmsWasteSilkPackage));
    }

    /**
     * 编辑数据
     *
     * @param wmsWasteSilkPackage 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWasteSilkPackage wmsWasteSilkPackage) {
        return AjaxResult.success(this.wmsWasteSilkPackageService.update(wmsWasteSilkPackage));
    }

    /**
     * 更新状态
     */
    @PutMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody WmsWasteSilkPackage wmsWasteSilkPackage) {
        return AjaxResult.success(this.wmsWasteSilkPackageService.updateStatus(wmsWasteSilkPackage));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.wmsWasteSilkPackageService.deleteById(id));
    }

}

