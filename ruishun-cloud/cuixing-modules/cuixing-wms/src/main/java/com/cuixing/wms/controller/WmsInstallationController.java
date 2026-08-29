package com.cuixing.wms.controller;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsInstallation;
import com.cuixing.wms.entity.WmsWeighRecord;
import com.cuixing.wms.service.WmsBoxService;
import com.cuixing.wms.service.WmsInstallationService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.wms.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * (WmsInstallation)表控制层
 *
 * @author 阿江
 * @since 2024-10-21 11:44:28
 */
@Api(tags = "装托管理接口")
@RestController
@RequestMapping("/wmsInstallation")
public class WmsInstallationController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private WmsInstallationService wmsInstallationService;
    @Autowired
    private WmsBoxService wmsBoxService;

    /**
     * 分页查询
     *
     * @param wmsInstallation 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(WmsInstallation wmsInstallation) {
        this.startPage();
        List<WmsInstallation> list = this.wmsInstallationService.selectWmsInstallationList(wmsInstallation);
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
        return AjaxResult.success(this.wmsInstallationService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param wmsInstallation 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody WmsInstallation wmsInstallation) {
        WmsInstallation insert = this.wmsInstallationService.insert(wmsInstallation);
        WmsWeighRecord entity = null;

        try {
            wmsInstallationService.uploadSecondaryNode(wmsInstallation);
        } catch (Exception var4) {
            throw new BussinessException(var4);
        }

        return AjaxResult.success(insert);
    }

    /**
     * 编辑数据
     *
     * @param wmsInstallation 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WmsInstallation wmsInstallation) {
        return AjaxResult.success(this.wmsInstallationService.update(wmsInstallation));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id")Long id) {
        return toAjax(this.wmsInstallationService.deleteById(id));
    }

    /**
     * 删除关联信息
     * @param wmsBoxes
     * @return
     */
    @PostMapping("/removeForBoxAndWeighRecord")
    public AjaxResult removeForBoxAndWeighRecord(@RequestBody List<WmsBox> wmsBoxes) {
        return AjaxResult.success(this.wmsInstallationService.removeForBoxAndWeighRecord(wmsBoxes));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    @PostMapping(value = "/export", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.ALL_VALUE})
    public void export(HttpServletResponse response, @RequestBody(required = false) List<WmsInstallation> wmsInstallations) {
            try {
                List<WmsBox> wmsBoxes = null;
                if (wmsInstallations != null && !wmsInstallations.isEmpty()) {
                    List<String> codes = wmsInstallations.stream()
                            .map(WmsInstallation::getInstallationCode)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    wmsBoxes = codes.isEmpty() ? Collections.emptyList() : wmsBoxService.selectInstallation(codes);
                }
                //System.out.println("Exporting " + wmsBoxes.size() + " WmsBox records.");
                ExcelUtil<WmsBox> util = new ExcelUtil<>(WmsBox.class);
                util.exportExcel(response, wmsBoxes, "装托表数据");
                System.out.println("Excel export completed.");
            } catch (Exception e) {
                System.err.println("Export failed: " + e.getMessage());
                throw new RuntimeException("导出装托表数据失败: " + e.getMessage());
            }
        }

}

