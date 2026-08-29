package com.cuixing.system.api;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import com.cuixing.system.api.factory.RemoteWmsFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


/**
 * 仓储服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteWmsService", value = ServiceNameConstants.WMS_SERVICE,fallbackFactory = RemoteWmsFallbackFactory.class)
public interface RemoteWmsService {

    /**
     * 新增实时库存
     * @param wmsRealtimeStock 实时库存
     * @param source 来源
     * @return 结果
     */
    @PostMapping("/wms/stock/addRealtimeStock")
    public R<AjaxResult> addRealtimeStock(@RequestBody WmsRealtimeStock wmsRealtimeStock,  @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 扣减实时库存
     * @param wmsRealtimeStock 实时库存
     * @param source 来源
     * @return 结果
     */
    @PostMapping("/wms/stock/subtractRealtimeStock")
    public R<AjaxResult> subtractRealtimeStock(@RequestBody WmsRealtimeStock wmsRealtimeStock,  @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    @GetMapping("/wms/warehouse/getinfoByWorkstationId/{workstationId}")
    R<WmsWarehouse> getinfoByWorkstationId(@PathVariable("workstationId") Long workstationId, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //通过工序id获取仓库信息
    @GetMapping("/wms/warehouse/getinfoByWorkstageId/{workstageId}")
    R<WmsWarehouse> getinfoByWorkstageId(@PathVariable("workstageId") BigInteger workstationId, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //通过字典键值查询仓库
    @GetMapping("/wms/warehouse/getByType/{type}")
    R<WmsWarehouse> getInfoByType(@PathVariable("type") String itemIdentifier, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //通过物料唯一码获取物料信息
    @GetMapping("/wms/stock/getByIdentifier/{itemIdentifier}")
    R<WmsRealtimeStock> getInfoByItemIdentifier(@PathVariable("itemIdentifier") String itemIdentifier, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //通过物料唯一码更改为漆包在制品
    @PutMapping("/wms/stock/updateDetectionStatus/{itemIdentifier}")
    R<AjaxResult> updateDetectionStatus(@PathVariable("itemIdentifier") String itemIdentifier, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);
    //通过物料唯一码更改为漆包在制品
    @PutMapping("/wms/stock/updateDetectionStatus2/{itemIdentifiers}/{type}/{taskType}")
    R<AjaxResult> updateDetectionStatus2(@PathVariable("itemIdentifiers") List<String> itemIdentifiers, @PathVariable("type") String type,@PathVariable("taskType") String taskType, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //通过物料唯一码修改信息
    @PostMapping("/wms/stock/editSave")
    R<AjaxResult> updateWmsRealtimeStock(@RequestBody WmsRealtimeStock wmsRealtimeStock, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);
}
