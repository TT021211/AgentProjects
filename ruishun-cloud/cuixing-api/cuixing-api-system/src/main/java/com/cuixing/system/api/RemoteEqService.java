package com.cuixing.system.api;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.domain.eq.EqEquipment;
import com.cuixing.system.api.domain.eq.EqList;
import com.cuixing.system.api.domain.eq.EqPlan;
import com.cuixing.system.api.factory.RemoteEqFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


/**
 * 设备服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteEqService", value = ServiceNameConstants.EQ_SERVICE,fallbackFactory = RemoteEqFallbackFactory.class)
public interface RemoteEqService {

    //通过id获取物料信息
    @GetMapping("/eqEquipment/{id}")
    R<EqEquipment> queryById(@PathVariable(value = "id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //修改设备信息
    @PutMapping("/eqEquipment")
    R<AjaxResult> updateEqStatus(@RequestBody EqEquipment eqEquipment, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //获取计划列表(点检/保养)
    @PostMapping("/eqPlan/getAll")
    R<List<EqPlan>> getEqPlan(EqPlan eqPlan, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //获取计划id查询单条数据
    @GetMapping("/eqPlan/{id}")
    R<EqPlan> getEqPlanById(@PathVariable(value = "id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //新增待点检/保养计划列表
    @PostMapping("/eqList")
    R<AjaxResult> insertEqList(@RequestBody EqList eqList, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //根据计划id删除列表（生成日志）
    @DeleteMapping("/eqList/deleteByPlanId/{planId}")
    R<AjaxResult> deleteByPlanIds(@PathVariable("planId") BigInteger planId, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //根据计划id生成待点检列表
    @PostMapping("/eqPlan/insertEqList/{id}")
    R<AjaxResult> insertEqListAuto(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE)String source);

    //查询设备台账信息
    @GetMapping("/eqEquipment/list2")
    List<EqEquipment> eqEquipmentList();

}
