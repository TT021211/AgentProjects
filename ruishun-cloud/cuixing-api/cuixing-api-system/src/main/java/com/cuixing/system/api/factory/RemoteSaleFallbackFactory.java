package com.cuixing.system.api.factory;


import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.domain.sale.SaleOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.math.BigInteger;
import java.util.List;


public class RemoteSaleFallbackFactory implements FallbackFactory<RemoteSaleService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteSaleFallbackFactory.class);
    @Override
    public RemoteSaleService create(Throwable throwable) {
        log.error("系统服务调用失败:{}", throwable.getMessage());

        return new RemoteSaleService() {
            @Override
            public R<SaleOrder> getOrder(BigInteger id, String source) {
                return R.fail("获取销售订单失败:" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> updateOrder(SaleOrder saleOrder, String source) {
                return R.fail("更新销售订单信息失败:" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> addSaleOrderList(List<SaleOrder> saleOrder, String source) {
                return R.fail("新增销售订单信息失败:" + throwable.getMessage());
            }

        };
    }
}
