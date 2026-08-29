package com.cuixing.wms.controller;


import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.wms.entity.WmsTransaction;
import com.cuixing.wms.service.IWmsTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 库存流水Controller
 * @author wupujian
 * @date 2024-06-03
 */
@RestController
@RequestMapping({"/wms/transaction"})
public class WmsTransactionController extends BaseController {
    @Autowired
    private IWmsTransactionService wmsTransactionService;

    public WmsTransactionController() {
    }

    //@RequiresPermissions({"wms:transaction:list"})
    @GetMapping({"/list"})
    public TableDataInfo list(WmsTransaction wmTransaction) {
        this.startPage();
        List<WmsTransaction> list = this.wmsTransactionService.selectWmsTransactionGroupList(wmTransaction);
        return this.getDataTable(list);
    }

    //@RequiresPermissions({"wms:transaction:list"})
    @GetMapping({"/selectWaringValue"})
    public TableDataInfo selectWaringValue(WmsTransaction wmTransaction) {
        this.startPage();
        List<WmsTransaction> list = this.wmsTransactionService.selectWaringValueList(wmTransaction);
        return this.getDataTable(list);
    }

    //@RequiresPermissions({"wms:transaction:list"})
    @GetMapping({"/selectAll"})
    public TableDataInfo selectAll(WmsTransaction wmTransaction) {
        this.startPage();
        List<WmsTransaction> list = this.wmsTransactionService.selectWmsTransactionList(wmTransaction);
        return this.getDataTable(list);
    }

    //@RequiresPermissions({"wms:transaction:export"})
    @Log(
            title = "库存流水",
            businessType = BusinessType.EXPORT
    )
    @PostMapping({"/export"})
    public void export(HttpServletResponse response, WmsTransaction wmTransaction) {
        List<WmsTransaction> list = this.wmsTransactionService.selectWmsTransactionList(wmTransaction);
        ExcelUtil<WmsTransaction> util = new ExcelUtil(WmsTransaction.class);
        util.exportExcel(response, list, "库存流水数据");
    }

    //@RequiresPermissions({"mes/wm:transaction:query"})
    @GetMapping({"/{transactionId}"})
    public AjaxResult getInfo(@PathVariable("transactionId") Long transactionId) {
        return AjaxResult.success(this.wmsTransactionService.selectWmsTransactionByTransactionId(transactionId));
    }

    /**
     * 修改保存库存
     */
    //@RequiresPermissions("wms:transaction:edit")
    @Log(title = "设置预警值", businessType = BusinessType.UPDATE)
    @PostMapping("/editSave")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editSave(@RequestBody WmsTransaction wmTransaction)
    {
        int i = wmsTransactionService.updateWmsTransaction(wmTransaction);
        if(i != 1){
            return AjaxResult.error("更新库存失败");
        }
        return toAjax(i);
    }

}

