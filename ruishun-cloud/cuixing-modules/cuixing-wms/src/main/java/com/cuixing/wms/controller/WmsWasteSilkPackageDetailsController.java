package com.cuixing.wms.controller;

import com.cuixing.wms.entity.WmsWasteSilkPackageDetails;
import com.cuixing.wms.service.WmsWasteSilkPackageDetailsService;
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
 * 废丝包详情表(WmsWasteSilkPackageDetails)表控制层
 *
 * @author 阿江
 * @since 2025-04-18 14:20:55
 */
@Api(tags = "废丝包详情表接口")
@RestController
@RequestMapping("/wmsWasteSilkPackageDetails")
public class WmsWasteSilkPackageDetailsController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private WmsWasteSilkPackageDetailsService wmsWasteSilkPackageDetailsService;

    /**
     * 分页查询
     *
     * @param wmsWasteSilkPackageDetails 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails) {
        this.startPage();
        List<WmsWasteSilkPackageDetails> list = this.wmsWasteSilkPackageDetailsService.selectWmsWasteSilkPackageDetailsList(wmsWasteSilkPackageDetails);
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
        return AjaxResult.success(this.wmsWasteSilkPackageDetailsService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param wmsWasteSilkPackageDetails 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails) {
        return AjaxResult.success(this.wmsWasteSilkPackageDetailsService.insert(wmsWasteSilkPackageDetails));
    }

    /**
     * 编辑数据
     *
     * @param wmsWasteSilkPackageDetails 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails) {
        return AjaxResult.success(this.wmsWasteSilkPackageDetailsService.update(wmsWasteSilkPackageDetails));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.wmsWasteSilkPackageDetailsService.deleteById(id));
    }

}

