package com.cuixing.crm.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.crm.entity.CrmCustomer;
import com.cuixing.crm.service.CrmCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客诉表(CrmCustomer)表控制层
 *
 * @author 阿江
 * @since 2025-07-14 20:37:17
 */
@Api(tags = "客诉表接口")
@RestController
@RequestMapping("/crmCustomer")
public class CrmCustomerController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private CrmCustomerService crmCustomerService;

    /**
     * 分页查询
     *
     * @param crmCustomer 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(CrmCustomer crmCustomer) {
        this.startPage();
        List<CrmCustomer> list = this.crmCustomerService.selectCrmCustomerList(crmCustomer);
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
        return AjaxResult.success(this.crmCustomerService.queryById(id));
    }
    
    /**
     * 新增数据
     *
     * @param crmCustomer 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody CrmCustomer crmCustomer) {
        return AjaxResult.success(this.crmCustomerService.insert(crmCustomer));
    }

    /**
     * 编辑数据
     *
     * @param crmCustomer 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody CrmCustomer crmCustomer) {
        return AjaxResult.success(this.crmCustomerService.update(crmCustomer));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.crmCustomerService.deleteById(id));
    }

}

