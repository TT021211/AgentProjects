package com.cuixing.purchase.controller;

import com.cuixing.purchase.entity.ProcureMaterialReturn;
import com.cuixing.purchase.service.ProcureMaterialReturnService;
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
 * 采购退货管理(ProcureMaterialReturn)表控制层
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Api(tags = "采购退货管理接口")
@RestController
@RequestMapping("/procure/procureMaterialReturn")
public class ProcureMaterialReturnController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private ProcureMaterialReturnService procureMaterialReturnService;

    /**
     * 分页查询
     *
     * @param procureMaterialReturn 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(ProcureMaterialReturn procureMaterialReturn) {
        this.startPage();
        List<ProcureMaterialReturn> list = this.procureMaterialReturnService.selectProcureMaterialReturnList(procureMaterialReturn);
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
        return AjaxResult.success(this.procureMaterialReturnService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param procureMaterialReturn 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody ProcureMaterialReturn procureMaterialReturn) {
        return AjaxResult.success(this.procureMaterialReturnService.insert(procureMaterialReturn));
    }

    /**
     * 编辑数据
     *
     * @param procureMaterialReturn 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ProcureMaterialReturn procureMaterialReturn) {
        return AjaxResult.success(this.procureMaterialReturnService.update(procureMaterialReturn));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.procureMaterialReturnService.deleteById(id));
    }

}

