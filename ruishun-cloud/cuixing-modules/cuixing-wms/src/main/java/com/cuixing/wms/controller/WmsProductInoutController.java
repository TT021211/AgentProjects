package com.cuixing.wms.controller;

import com.cuixing.wms.entity.WmsProductInout;
import com.cuixing.wms.service.WmsProductInoutService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 成品出入库(WmsProductInout)表控制层
 *
 * @author 阿江
 * @since 2024-10-25 09:39:26
 */
@Api(tags = "成品出入库接口")
@RestController
@RequestMapping("/wmsProductInout")
public class WmsProductInoutController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private WmsProductInoutService wmsProductInoutService;

    /**
     * 分页查询
     *
     * @param wmsProductInout 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(WmsProductInout wmsProductInout) {
        this.startPage();
        List<WmsProductInout> list = this.wmsProductInoutService.selectWmsProductInoutList(wmsProductInout);
        return this.getDataTable(list);
    }

     @ApiOperation("")
     @GetMapping("/list2")
     public TableDataInfo list2(WmsProductInout wmsProductInout) {
        this.startPage();
        List<WmsProductInout> list = this.wmsProductInoutService.selectCountWmsProductInoutList(wmsProductInout);
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
        return AjaxResult.success(this.wmsProductInoutService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param wmsProductInout 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody WmsProductInout wmsProductInout) {
        return AjaxResult.success(this.wmsProductInoutService.insert(wmsProductInout));
    }

    /**
     * 编辑数据
     *
     * @param wmsProductInout 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WmsProductInout wmsProductInout) {
        return AjaxResult.success(this.wmsProductInoutService.update(wmsProductInout));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id")Long id) {
        return toAjax(this.wmsProductInoutService.deleteById(id));
    }
    /**
     * 根据箱编码删除数据
     *
     * @param boxCode 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteByBoxCode/{boxCode}")
    public AjaxResult deleteByBoxCode(@PathVariable("boxCode")String boxCode) {
        return toAjax(this.wmsProductInoutService.deleteByBoxCode(boxCode));
    }

}

