package com.cuixing.purchase.controller;

import com.cuixing.purchase.entity.ProcureReceiving;
import com.cuixing.purchase.service.ProcureReceivingService;
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
 * 采购收料管理(ProcureReceiving)表控制层
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Api(tags = "采购收料管理接口")
@RestController
@RequestMapping("/procure/procureReceiving")
public class ProcureReceivingController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private ProcureReceivingService procureReceivingService;

    /**
     * 分页查询
     *
     * @param procureReceiving 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(ProcureReceiving procureReceiving) {
        this.startPage();
        List<ProcureReceiving> list = this.procureReceivingService.selectProcureReceivingList(procureReceiving);
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
        return AjaxResult.success(this.procureReceivingService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param procureReceiving 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody ProcureReceiving procureReceiving) {
        return AjaxResult.success(this.procureReceivingService.insert(procureReceiving));
    }

    /**
     * 编辑数据
     *
     * @param procureReceiving 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ProcureReceiving procureReceiving) {
        return AjaxResult.success(this.procureReceivingService.update(procureReceiving));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.procureReceivingService.deleteById(id));
    }

}

