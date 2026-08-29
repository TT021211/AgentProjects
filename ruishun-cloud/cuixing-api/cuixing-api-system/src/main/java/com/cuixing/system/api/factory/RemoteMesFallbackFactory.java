package com.cuixing.system.api.factory;


import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.RemoteMesService;
import com.cuixing.system.api.domain.mes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class RemoteMesFallbackFactory implements FallbackFactory<RemoteMesService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteMdFallbackFactory.class);

    @Override
    public RemoteMesService create(Throwable throwable)
    {
        log.error("Mes服务调用失败:{}", throwable.getMessage());
        return new RemoteMesService()
        {
            @Override
            public R<List<MesProductRouting>> getRoutingProducts(BigInteger routingId, String source) {
                return R.fail("调用Mes服务获取物料信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MesProductWorkorder> queryByWorkorderCode(String workorderCode, String source) {
                return R.fail("调用Mes服务获取生产工单信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MesOuputLog> queryByOutputSn(String outputSn, String source) {
                return R.fail("调用Mes服务获取报工记录信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MesInputLog> getInfoByInputSn(String inputSn, String source) {
                return R.fail("调用Mes服务获取投料记录信息失败:" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> updateMesOutputLog(MesOuputLog mesOuputLog, String source) {
                return R.fail("调用Mes服务修改报工记录信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MesOuputLog> getSpecification(String workstationCode, String source) {
                return R.fail("调用Mes服务查询报工信息失败:" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> updateMesTinningWorkorder(MesTinningWorkorder mesTinningWorkorder, String source) {
                return R.fail("调用Mes服务更新镀锡工单信息失败:" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> updateMesAnnealingWorkorder(MesAnnealingWorkorder mesAnnealingWorkorder, String source) {
                return R.fail("调用Mes服务更新退火工单信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MesAnnealingWorkorder> queryByMesAnnealingWorkorderCode(String workorderCode, String source) {
                return R.fail("调用Mes服务查询退火工单信息失败:" + throwable.getMessage());            }

            @Override
            public R<MesTinningWorkorder> queryByTinningWorkorderCode(String workorderCode, String source) {
                return R.fail("调用Mes服务查询退火工单信息失败:" + throwable.getMessage());            }
        };
    }
}
