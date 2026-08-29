package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mold.entity.MoldPurchase;
import com.cuixing.mold.service.MoldPurchaseService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 模具采购(MoldPurchase)表控制层
 *
 * @author makejava
 * @since 2024-07-23 14:32:39
 */
@Api(tags = "模具采购接口")
@RestController
@RequestMapping("/mold/moldPurchase")
public class MoldPurchaseController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldPurchaseService moldPurchaseService;

    /**
     * 分页查询
     *
     * @param moldPurchase 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldPurchase moldPurchase) {
        this.startPage();
        List<MoldPurchase> list = this.moldPurchaseService.selectMoldPurchaseList(moldPurchase);
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
        return AjaxResult.success(this.moldPurchaseService.queryById(id));
    }

    /**
     * 入库
     * @param moldPurchase
     * @return
     */
    @PostMapping("/initiateStorage")
    public AjaxResult initiateStorage(@RequestBody MoldPurchase moldPurchase) {
        return moldPurchaseService.initiateStorage(moldPurchase);
    }

    /**
     * 临时审批按钮
     * @param moldPurchase
     * @return
     */
    @PostMapping("/initiateTemporaryApproval")
    public AjaxResult initiateTemporaryApproval(@RequestBody MoldPurchase moldPurchase) {
        return moldPurchaseService.initiateTemporaryApproval(moldPurchase);
    }

    /**
     * 发起审批接口
     * @param moldPurchase
     * @return
     */
    @PostMapping("/initiateApproval")
    public AjaxResult initiateApproval(@RequestBody MoldPurchase moldPurchase) {
        return moldPurchaseService.initiateApproval(moldPurchase);
    }
    /**
     * 新增数据
     *
     * @param moldPurchase 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(MoldPurchase moldPurchase) {
        if ("1".equals(this.moldPurchaseService.checkUniquePurchaseCode(moldPurchase))) {
            return AjaxResult.error("采购订单已存在！");
        } else {
            return AjaxResult.success(this.moldPurchaseService.insert(moldPurchase));
        }
    }

    /**
     * 批量插入
     * @param moldPurchases
     */
    @PostMapping("/insertBatch")
    public AjaxResult insertBatch(@RequestBody List<MoldPurchase> moldPurchases) {
       return AjaxResult.success(this.moldPurchaseService.insertBatch(moldPurchases));
    }

    /**
     * 编辑数据
     *
     * @param moldPurchase 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(MoldPurchase moldPurchase) {
        if ("1".equals(this.moldPurchaseService.checkUniquePurchaseCode(moldPurchase))) {
            return AjaxResult.error("采购订单已存在！");
        } else {
            return AjaxResult.success(this.moldPurchaseService.update(moldPurchase));
        }
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id")Long id) {
        return toAjax(this.moldPurchaseService.deleteById(id));
    }

}

