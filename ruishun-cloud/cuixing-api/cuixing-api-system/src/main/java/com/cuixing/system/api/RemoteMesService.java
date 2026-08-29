package com.cuixing.system.api;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.domain.mes.*;
import com.cuixing.system.api.factory.RemoteMesFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * 主数据服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteMesService", value = ServiceNameConstants.MES_SERVICE,fallbackFactory = RemoteMesFallbackFactory.class)
public interface RemoteMesService {

    @GetMapping("/mesProductRouting/getProductsByRoutingId")
    public R<List<MesProductRouting>> getRoutingProducts(@RequestParam("routing_id") BigInteger routingId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    //通过生产工单号查询生产工单
    @GetMapping("/mesProductWorkorder/queryByWorkorderCode/{workorderCode}")
    public R<MesProductWorkorder> queryByWorkorderCode(@RequestParam("workorderCode") String workorderCode, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    //通过物料唯一码获取报工记录
    @GetMapping("/mesOutputLog/getInfoByOutputSn/{outputSn}")
    public R<MesOuputLog> queryByOutputSn(@RequestParam("outputSn") String outputSn, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    //通过物料唯一码获取投料记录
    @GetMapping("/mesInputLog/getInfoByInputSn/{inputSn}")
    public R<MesInputLog> getInfoByInputSn(@RequestParam("inputSn") String inputSn, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    //更新报工记录
    @PutMapping("/mesOutputLog")
    public R<AjaxResult> updateMesOutputLog(@RequestBody MesOuputLog mesOuputLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    @GetMapping("/mesOutputLog/getSpecificationByWorkstationCode/{workstationCode}")
    public R<MesOuputLog> getSpecification(@RequestParam("workstationCode") String workstationCode, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    //更新退火工单
    @PutMapping("/mesTinningWorkorder")
    public R<AjaxResult> updateMesTinningWorkorder(@RequestBody MesTinningWorkorder mesTinningWorkorder, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    //更新镀锡工单
    @PutMapping("/mesAnnealingWorkorder")
    public R<AjaxResult> updateMesAnnealingWorkorder(@RequestBody MesAnnealingWorkorder mesAnnealingWorkorder, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    //通过生产工单号查询退火生产工单
    @GetMapping("/mesAnnealingWorkorder/queryByMesAnnealingWorkorderCode/{workorderCode}")
    public R<MesAnnealingWorkorder> queryByMesAnnealingWorkorderCode(@RequestParam("workorderCode") String workorderCode, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    //通过生产工单号查询镀锡生产工单
    @GetMapping("/mesTinningWorkorder/queryByTinningWorkorderCode/{workorderCode}")
    public R<MesTinningWorkorder> queryByTinningWorkorderCode(@RequestParam("workorderCode") String workorderCode, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
