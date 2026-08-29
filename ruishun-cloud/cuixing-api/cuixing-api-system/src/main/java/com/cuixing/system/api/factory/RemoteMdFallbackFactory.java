package com.cuixing.system.api.factory;


import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.domain.md.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RemoteMdFallbackFactory implements FallbackFactory<RemoteMdService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteMdFallbackFactory.class);

    @Override
    public RemoteMdService create(Throwable throwable)
    {
        log.error("主数据服务调用失败:{}", throwable.getMessage());
        return new RemoteMdService()
        {
            @Override
            public R<MdProduct> getProduct(BigInteger id, String source) {
                return R.fail("获取产品信息失败:" + throwable.getMessage());
            }

            @Override
            public R<List<MdProduct>> getProductsByCodes(Set<String> productCode, String source) {
                return R.fail("获取产品信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MdItem> getItem(BigInteger id, String source) {
                return R.fail("获取物料信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MdAccessory> getAccessory(BigInteger id, String source) {
                return R.fail("获取辅料信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MdClient> getInfo(BigInteger id, String source) {
                return R.fail("获取客户信息失败:" + throwable.getMessage());
            }

            public R<MdUnitMeasure> getMeasure(BigInteger id, String source) {
                return R.fail("获取单位信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MdItem> getItemBySpecification(String specification, String source) {
                return R.fail("获取物料信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MdWorkbenchWiredrawing> getWorkbenchWiredrawingById(BigInteger id, String source) {
                return R.fail("获取拉丝机台信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MdWorkbenchEnamelling> getWorkbenchEnamellingById(BigInteger id, String source) {
                return R.fail("获取漆包机台信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MdWorkbenchAnnealing> getWorkbenchAnnealingById(BigInteger id, String source) {
                return R.fail("获取退火机台信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MdWorkbenchDoublewire> getWorkbenchDoublewireById(BigInteger id, String source) {
                return R.fail("获取并丝机台信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MdWorkbenchStrandedwire> getWorkbenchStrandedwireById(BigInteger id, String source) {
                return R.fail("获取绞线机台信息失败:" + throwable.getMessage());
            }

            @Override
            public R<MdWorkbenchTinning> getWorkbenchTinningById(BigInteger id, String source) {
                return R.fail("获取镀锡机台信息失败:" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> insertReturnGoods(MdReturnGoods mdReturnGoods, String source) {
                return R.fail("添加退货记录失败:" + throwable.getMessage());
            }

            @Override
            public R<List<MdClient>> queryByClientNicks(Set<String> clientNick, String source) {
                return R.fail("获取客户记录失败:" + throwable.getMessage());
            }
        };
    }
}
