package com.cuixing.eq.scheduler;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.eq.entity.EqAttachment;
import com.cuixing.eq.mapper.EqAttachmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class EmsAttachmentScheduler {

    @Autowired
    private EqAttachmentMapper eqAttachmentMapper;
    /**
     * 更新已使用时间(每天晚上凌晨执行)
     */
//    @Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateUsedTime() {
        // 查询所有未更换的配件
        List<EqAttachment> emsAttachments = eqAttachmentMapper.selectEmsAttachmentList(null);
        //过滤掉所有状态为已更换的配件
        emsAttachments.stream().filter(emsAttachmentResult -> !emsAttachmentResult.getStatus().equals("已更换")).forEach(emsAttachmentResult -> {
            //获取当前时间
            Date currentTime = DateUtils.getNowDate();
            //获取创建时间
            Date createTime = emsAttachmentResult.getCreateTime();
            //计算已使用时间
            long diffInMillie = Math.abs(currentTime.getTime() - createTime.getTime());
            int usedTime = (int) TimeUnit.DAYS.convert(diffInMillie, TimeUnit.MILLISECONDS);
            //设置已使用时间
            emsAttachmentResult.setUsedTime(usedTime);
            if (emsAttachmentResult.getMaxServiceTime() != 0 && usedTime >= emsAttachmentResult.getMaxServiceTime()) {
                emsAttachmentResult.setStatus("预警");
                //发送警报
            }
            //更新数据
            eqAttachmentMapper.update(emsAttachmentResult);
        });
    }

}
