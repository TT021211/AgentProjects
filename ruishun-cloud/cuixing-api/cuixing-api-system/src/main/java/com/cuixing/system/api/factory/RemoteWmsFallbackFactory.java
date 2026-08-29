package com.cuixing.system.api.factory;


import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.RemoteWmsService;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class RemoteWmsFallbackFactory implements FallbackFactory<RemoteWmsService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteWmsFallbackFactory.class);

    @Override
    public RemoteWmsService create(Throwable throwable)
    {
        log.error("WMS服务调用失败:{}", throwable.getMessage());

        return new RemoteWmsService()
        {
            @Override
            public R<AjaxResult> addRealtimeStock(WmsRealtimeStock wmsRealtimeStock, String source)
            {
                return R.fail(AjaxResult.error("调用WMS增加库存变更服务失败"));
            }

            @Override
            public R<AjaxResult> subtractRealtimeStock(WmsRealtimeStock wmsRealtimeStock, String source)
            {
                return R.fail(AjaxResult.error("调用WMS扣减库存变更服务失败"));
            }
            @Override
            public R<WmsWarehouse> getinfoByWorkstationId(Long workstationId, String source)
            {
                return R.fail("调用WMS仓库查询服务失败");
            }

            @Override
            public R<WmsWarehouse> getinfoByWorkstageId(BigInteger workstationId, String source) {
                return R.fail("调用WMS服务通过工序查询仓库失败");
            }

            @Override
            public R<WmsRealtimeStock> getInfoByItemIdentifier(String itemIdentifier, String source) {
                return R.fail("调用WMS服务通过物料唯一码获取物料信息失败");
            }

            @Override
            public R<WmsWarehouse> getInfoByType(String itemIdentifier, String source) {
                return R.fail("调用WMS服务通过字典键值获取仓库信息");
            }

            @Override
            public R<AjaxResult> updateDetectionStatus(String itemIdentifier, String source) {
                return R.fail("调用WMS服务更改在制品状态失败");
            }

            @Override
            public R<AjaxResult> updateDetectionStatus2(List<String> itemIdentifiers, String type,String taskType, String source) {
                return R.fail("调用WMS服务更改在制品状态失败");
            }

            @Override
            public R<AjaxResult> updateWmsRealtimeStock(WmsRealtimeStock wmsRealtimeStock, String source) {
                return R.fail("调用WMS服务修改库存信息失败");
            }
        };
    }
}
