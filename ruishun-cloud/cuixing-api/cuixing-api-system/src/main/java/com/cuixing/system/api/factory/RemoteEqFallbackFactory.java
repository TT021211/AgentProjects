package com.cuixing.system.api.factory;


import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.RemoteEqService;
import com.cuixing.system.api.domain.eq.EqEquipment;
import com.cuixing.system.api.domain.eq.EqList;
import com.cuixing.system.api.domain.eq.EqPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Component
public class RemoteEqFallbackFactory implements FallbackFactory<RemoteEqService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteEqFallbackFactory.class);

    @Override
    public RemoteEqService create(Throwable throwable)
    {
        log.error("设备服务调用失败:{}", throwable.getMessage());

        return new RemoteEqService()
        {
            @Override
            public R<AjaxResult> updateEqStatus(EqEquipment eqEquipment, String source) {
                return R.fail("修改设备信息失败：" + throwable.getMessage());
            }

            @Override
            public R<EqEquipment> queryById(BigInteger id, String source) {
                return R.fail("获取设备信息失败：" + throwable.getMessage());
            }

            @Override
            public R<List<EqPlan>> getEqPlan(EqPlan eqPlan, String source) {
                return R.fail("获取计划列表失败：" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> insertEqList(EqList eqList, String source) {
                return R.fail("生成待点检(保养)列表失败：" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> deleteByPlanIds(BigInteger planId, String source) {
                return R.fail("删除待点检(保养)列表失败：" + throwable.getMessage());
            }

            @Override
            public R<AjaxResult> insertEqListAuto(BigInteger id, String source) {
                return R.fail("自动生成待点检(保养)列表失败：" + throwable.getMessage());
            }

            @Override
            public List<EqEquipment> eqEquipmentList() {
                return null; // 回退逻辑
            }

            @Override
            public R<EqPlan> getEqPlanById(BigInteger id, String source) {
                return R.fail("远程调用获取计划信息失败：" + throwable.getMessage());
            }
        };
    }
}
