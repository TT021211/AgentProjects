package com.cuixing.mold.service.impl;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldLedger;
import com.cuixing.mold.entity.MoldMaintainList;
import com.cuixing.mold.entity.MoldMaintainPlan;
import com.cuixing.mold.entity.MoldNesting;
import com.cuixing.mold.mapper.MoldLedgerMapper;
import com.cuixing.mold.mapper.MoldMaintainListMapper;
import com.cuixing.mold.mapper.MoldMaintainPlanMapper;
import com.cuixing.mold.mapper.MoldNestingMapper;
import com.cuixing.mold.service.MoldMaintainPlanService;
import com.cuixing.mold.utils.MaintainPlanUtils;
import com.cuixing.mold.utils.ScheduledTaskRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 * 模具保养表--保养计划表(MoldMaintainPlan)表服务实现类
 *
 * @author makejava
 * @since 2024-06-25 16:52:04
 */
@Service("moldMaintainPlanService")
public class MoldMaintainPlanServiceImpl implements MoldMaintainPlanService {
    @Resource
    private MoldMaintainPlanMapper moldMaintainPlanMapper;
    @Resource
    private MoldNestingMapper moldNestingMapper;
    @Resource
    private MoldLedgerMapper moldLedgerMapper;
    @Resource
    private MoldMaintainListMapper moldMaintainListMapper;
    @Autowired
    private ScheduledTaskRepository scheduledTaskRepository;
    @Autowired
    private Scheduler scheduler;

    /**
     * 通过ID查询单条数据
     *
     * @param maintainPlanId 主键
     * @return 实例对象
     */
    @Override
    public MoldMaintainPlan queryById(Long maintainPlanId) {
        return this.moldMaintainPlanMapper.queryById(maintainPlanId);
    }

    /**
     * 分页查询
     *
     * @param moldMaintainPlan 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldMaintainPlan> selectMoldMaintainPlanList(MoldMaintainPlan moldMaintainPlan) {
        List<MoldMaintainPlan> moldMaintainPlans = this.moldMaintainPlanMapper.selectMoldMaintainPlanList(moldMaintainPlan);
        if (moldMaintainPlans != null) {
            for (MoldMaintainPlan oldMaintainPlan : moldMaintainPlans) {
                List<MoldNesting> moldNestings = this.moldNestingMapper.queryByPlanId(oldMaintainPlan.getMaintainPlanId());
                if (moldNestings!=null && !moldNestings.isEmpty()) {
                    oldMaintainPlan.setMoldNesting(moldNestings);
                }
            }
        }
        return moldMaintainPlans;
    }

    /**
     * 新增数据
     *
     * @param moldMaintainPlan 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MoldMaintainPlan insert(MoldMaintainPlan moldMaintainPlan) {
        //判断时间之隔大于等于30天
        boolean b = DateCompare(moldMaintainPlan.getMaintainPlanStarttime(), moldMaintainPlan.getMaintainPlanEndtime());
        if (!b){
            throw new BussinessException("开始时间和结束时间之间的间隔必须大于30天！");
        }
        List<MoldNesting> moldNesting = moldMaintainPlan.getMoldNesting();
        for (MoldNesting moldNesting1:moldNesting){
            if (moldNesting1.getPlanId()!=null){
                throw new BussinessException("该套模已有对应的关联计划！");
            }
        }
        moldMaintainPlan.setCreateBy(SecurityUtils.getUsername());
        moldMaintainPlan.setCreateTime(new Date());
        this.moldMaintainPlanMapper.insert(moldMaintainPlan);
        return moldMaintainPlan;
    }

    /**
     * 修改数据
     *
     * @param moldMaintainPlan 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MoldMaintainPlan update(MoldMaintainPlan moldMaintainPlan) {
        //套模关联计划
        List<MoldNesting> moldNestings = this.moldNestingMapper.queryByPlanId(moldMaintainPlan.getMaintainPlanId());
        if (!moldNestings.isEmpty()){
            for (MoldNesting moldNesting:moldNestings){
                if (moldNesting.getPlanId()!=null){
                    throw new BussinessException("该套模已有对应的关联计划！");
                }
            }
        }
        moldMaintainPlan.setUpdateBy(SecurityUtils.getUsername());
        moldMaintainPlan.setUpdateTime(new Date());
        generateAndInsertInspectionDates(moldMaintainPlan);
        this.moldMaintainPlanMapper.update(moldMaintainPlan);
        return this.queryById(moldMaintainPlan.getMaintainPlanId());
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateAndInsertInspectionDates(MoldMaintainPlan plan) {
        //判断时间之隔大于等于30天
        boolean b = DateCompare(plan.getMaintainPlanStarttime(), plan.getMaintainPlanEndtime());
        if (!b){
            throw new BussinessException("开始时间和结束时间之间的间隔必须大于30天！");
        }
        List<MoldNesting> moldNestings = this.moldNestingMapper.queryByPlanId(plan.getMaintainPlanId());
        //套模关联计划
        if (!moldNestings.isEmpty()){
            for (MoldNesting moldNesting:moldNestings){
                if (moldNesting.getPlanId()!=null && !Objects.equals(moldNesting.getPlanId(), plan.getMaintainPlanId())){
                    throw new BussinessException("该套模已有对应的关联计划！");
                }
            }
        }
        // 将数据库中的数据转为集合
        Set<Long> moldNestingSet = moldNestings.stream()
                .map(MoldNesting::getNestingMoldId)
                .collect(Collectors.toSet());

        // 将传入的数据转为集合
        Set<Long> moldNesting_ttSet = plan.getMoldNesting().stream()
                .map(MoldNesting::getNestingMoldId)
                .collect(Collectors.toSet());

        // 找出多余的记录
        Set<Long> redundantRecords = new HashSet<>(moldNestingSet);
        redundantRecords.removeAll(moldNesting_ttSet);

        // 打印多余的记录
        for (Long redundantRecordId : redundantRecords) {
            this.moldNestingMapper.updatePlan(redundantRecordId);
        }
        //套模关联计划
        if (plan.getMoldNesting() != null) {
            if (!plan.getMoldNesting().isEmpty()) {
                for (MoldNesting oldNesting : plan.getMoldNesting()) {
                    //修改套模关联计划id
                    MoldNesting nesting = new MoldNesting();
                    nesting.setNestingMoldId(oldNesting.getNestingMoldId());
                    nesting.setPlanId(plan.getMaintainPlanId());
                    this.moldNestingMapper.update(nesting);
                    //修改套模下的模具状态
                    List<MoldLedger> moldLedgers = this.moldLedgerMapper.queryByNestingMoldId(oldNesting.getNestingMoldId());
                    for (MoldLedger ledger:moldLedgers){
                        ledger.setStatus("MAINTENANCE");
                        this.moldLedgerMapper.update(ledger);
                    }
                }
            }
        } else {
            throw new BussinessException("请先添加套模！");
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param maintainPlanId 主键
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long maintainPlanId) {
        //更新与计划关联的套模信息
        List<MoldNesting> moldNestings = this.moldNestingMapper.queryByPlanId(maintainPlanId);
        if (moldNestings!=null) {
            for (MoldNesting nesting : moldNestings) {
                this.moldNestingMapper.updatePlan(nesting.getNestingMoldId());
            }
        }
        MoldMaintainPlan moldMaintainPlan = this.moldMaintainPlanMapper.queryById(maintainPlanId);
        if (moldMaintainPlan != null) {
            if (moldMaintainPlan.getTriggerName()!=null){
                // 根据触发器名称获取现有的触发器
                TriggerKey triggerKey = TriggerKey.triggerKey(moldMaintainPlan.getTriggerName());
                Trigger oldTrigger = null;
                try {
                    oldTrigger = scheduler.getTrigger(triggerKey);
                    // 检查触发器是否存在
                    if (oldTrigger == null) {
                        throw new SchedulerException("Trigger not found with name: " + moldMaintainPlan.getTriggerName());
                    }
                    // 根据触发器获取关联的任务细节
                    JobKey jobKey = oldTrigger.getJobKey();
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    // 检查任务细节是否存在
                    if (jobDetail == null) {
                        throw new SchedulerException("JobDetail not found with key: " + jobKey);
                    }

                    // 暂停并删除现有任务，为重新调度做准备
                    // 暂停作业
                    scheduler.pauseJob(jobKey);
                    scheduler.deleteJob(jobKey);
                } catch (SchedulerException e) {
                    throw new RuntimeException(e);
                }
                //删除与保养计划关联的保养记录
                this.scheduledTaskRepository.deleteById(moldMaintainPlan.getTriggerName());
            }
        }
        // 删除保养计划数据
        return this.moldMaintainPlanMapper.deleteById(maintainPlanId) > 0;
    }

    @Override
    public String checkUniquePlanCode(MoldMaintainPlan moldMaintainPlan) {
        MoldMaintainPlan maintainPlans = this.moldMaintainPlanMapper.checkUniquePlanCode(moldMaintainPlan);
        Long maintainPlanId = moldMaintainPlan.getMaintainPlanId() == null ? -1L : moldMaintainPlan.getMaintainPlanId();

        // 如果查询结果不为空且itemId不同，返回"1"表示不唯一，否则返回"0"表示唯一
        return (maintainPlans != null && !maintainPlans.getMaintainPlanId().equals(maintainPlanId)) ? "1" : "0";
    }

    private static boolean DateCompare(Date date1, Date date2) {
        // Calculate the absolute difference in milliseconds
        long differenceInMilliseconds = Math.abs(date1.getTime() - date2.getTime());
        // Convert milliseconds to days
        long differenceInDays = differenceInMilliseconds / (24 * 60 * 60 * 1000);
        // Check if the difference is greater than 30 days
        return differenceInDays > 30;
    }
}
