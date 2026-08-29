package com.cuixing.system.api;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.domain.sale.SaleOrder;
import com.cuixing.system.api.factory.RemoteSaleFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * 主数据服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteSaleService", value = ServiceNameConstants.SALE_SERVICE,fallbackFactory = RemoteSaleFallbackFactory.class)
public interface RemoteSaleService {

    /**
     * 通过id销售订单状态
     *
     * @param id 客户id
     * @return 结果
     */
    @GetMapping("/sale/SaleOrder/{id}")
    public R<SaleOrder> getOrder(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    /**
     * 修改销售订单状态
     *
     * @return 结果
     */
    @PutMapping("/sale/SaleOrder")
    public R<AjaxResult> updateOrder(@RequestBody SaleOrder saleOrder, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostMapping("/sale/SaleOrder/addSaleOrderList")
    public R<AjaxResult> addSaleOrderList(@RequestBody List<SaleOrder> saleOrder, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    }
