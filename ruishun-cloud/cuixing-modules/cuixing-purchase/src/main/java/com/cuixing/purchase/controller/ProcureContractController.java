package com.cuixing.purchase.controller;

import com.cuixing.purchase.entity.ProcureContract;
import com.cuixing.purchase.service.ProcureContractService;
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
 * 采购合同管理(ProcureContract)表控制层
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Api(tags = "采购合同管理接口")
@RestController
@RequestMapping("/procure/procureContract")
public class ProcureContractController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private ProcureContractService procureContractService;

    /**
     * 分页查询
     *
     * @param procureContract 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(ProcureContract procureContract) {
        this.startPage();
        List<ProcureContract> list = this.procureContractService.selectProcureContractList(procureContract);
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
        return AjaxResult.success(this.procureContractService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param procureContract 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody ProcureContract procureContract) {
        return AjaxResult.success(this.procureContractService.insert(procureContract));
    }

    /**
     * 编辑数据
     *
     * @param procureContract 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ProcureContract procureContract) {
        return AjaxResult.success(this.procureContractService.update(procureContract));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.procureContractService.deleteById(id));
    }

}

