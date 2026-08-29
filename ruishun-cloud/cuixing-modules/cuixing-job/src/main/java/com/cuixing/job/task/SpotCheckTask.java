package com.cuixing.job.task;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.system.api.RemoteEqService;
import com.cuixing.system.api.domain.eq.EqPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Component("spotCheckTask")
public class SpotCheckTask {

    @Autowired
    private RemoteEqService remoteEqService;

    //定时执行点检计划
    public void executeSpotCheckPlanByPlanId(Integer planId){
        BigInteger planIdBigInteger = new BigInteger(planId.toString());
        //获取点检计划列表
        Date nowDate = DateUtils.getNowDate();
        EqPlan eqPlan = remoteEqService.getEqPlanById(planIdBigInteger, SecurityConstants.INNER).getData();
        Date startTime = eqPlan.getStartTime();
        Date endTime = eqPlan.getEndTime();
        //当前时间在计划时间段内
        if(nowDate.after(startTime) && nowDate.before(endTime)){
            R<AjaxResult> ajaxResultR = remoteEqService.deleteByPlanIds(planIdBigInteger, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("删除待点检列表失败!!!");
            }else{
                R<AjaxResult> ajaxResultR1 = remoteEqService.insertEqListAuto(planIdBigInteger, SecurityConstants.INNER);
                if (ajaxResultR1.getCode() != 200){
                    throw new EqException("生成待点检列表失败，请查看是否分配!!!");
                }
            }
        }else{
            System.out.println("当前时间不在计划时间内");
        }
    }

}
