package com.cuixing.mold.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.*;
import com.cuixing.mold.mapper.*;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.mold.service.MoldOutboundService;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.domain.md.MdWorkbenchEnamelling;
import com.cuixing.system.api.domain.md.MdWorkbenchWiredrawing;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 模具出库表(MoldOutbound)表服务实现类
 *
 * @author makejava
 * @since 2024-06-26 16:52:49
 */
@Service("moldOutboundService")
public class MoldOutboundServiceImpl implements MoldOutboundService {
    @Resource
    private MoldOutboundMapper moldOutboundMapper;
    @Resource
    private MoldLedgerService moldLedgerService;
    @Resource
    private MoldLifeCycleMapper moldLifeCycleMapper;
    @Resource
    private MoldReturnedMapper moldReturnedMapper;
    @Resource
    private MoldNestingMapper moldNestingMapper;
    @Resource
    private RemoteMdService remoteMdService;

    /**
     * 通过ID查询单条数据
     *
     * @param moldOutboundId 主键
     * @return 实例对象
     */
    @Override
    public MoldOutbound queryById(Long moldOutboundId) {
        return this.moldOutboundMapper.queryById(moldOutboundId);
    }

    /**
     * 分页查询
     *
     * @param moldOutbound 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldOutbound> selectMoldOutboundList(MoldOutbound moldOutbound) {
        List<MoldOutbound> moldOutbounds = this.moldOutboundMapper.selectMoldOutboundList(moldOutbound);
        if (!moldOutbounds.isEmpty()) {
            for (MoldOutbound curOutbound : moldOutbounds) {
                if (curOutbound.getLsId() != null) {
                    long l =System.currentTimeMillis();
                    MdWorkbenchWiredrawing data = this.remoteMdService.getWorkbenchWiredrawingById(BigInteger.valueOf(curOutbound.getLsId()), SecurityConstants.INNER).getData();
                    System.out.println("当前方法调用耗时："+(System.currentTimeMillis() - l+"ms"));
                    if (data!=null) {
                        curOutbound.setLsName(data.getEquipmentName());
                    }
                }
                if (curOutbound.getQbId() != null) {
                    MdWorkbenchEnamelling data = this.remoteMdService.getWorkbenchEnamellingById(BigInteger.valueOf(curOutbound.getQbId()), SecurityConstants.INNER).getData();
                    if (data!=null) {
                        curOutbound.setQbName(data.getEquipmentName());
                    }
                }
            }
        }
        return moldOutbounds;
    }

    /**
     * 新增数据
     *
     * @param moldOutbound 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MoldOutbound insert(MoldOutbound moldOutbound) {
        moldOutbound.setCreateTime(new Date());
        moldOutbound.setCreateBy(SecurityUtils.getUsername());
        List<MoldLedger> ledgerList = new ArrayList<>();
        List<MoldLedger> nestingLedgerList = new ArrayList<>();
        if (moldOutbound.getMoldLedgerList()!= null){
            for (MoldLedger ledger : moldOutbound.getMoldLedgerList()){
                MoldLedger moldLedger = this.moldLedgerService.queryById(ledger.getMoldId());
                Long moldNum = moldLedger.getMoldNum();
                Long moldNum1 = ledger.getMoldNum();
                if (moldNum < moldNum1){
                    throw new BussinessException("模具编号"+moldLedger.getMoldCode()+"数量不足，无法出库");
                }
                if (Objects.equals(ledger.getStatus(), "UNDER_REPAIR") || Objects.equals(ledger.getStatus(), "MAINTENANCE")){
                    throw new BussinessException("模具状态为维修中或保养状态，不能出库");
                }
                if (Objects.equals(ledger.getStatus(), "CHECKED_OUT")){
                    throw new BussinessException("模具状态为出库状态，不能重复出库");
                }
                if (ledger.getNestingMoldId()==null){
                    ledgerList.add(ledger);
                }else{
                    nestingLedgerList.add(ledger);
                }
            }
        }
        if (!ledgerList.isEmpty()) {
            for (MoldLedger ledger : ledgerList){
                moldOutbound.setMoldId(ledger.getMoldId());
                moldOutbound.setMoldCode(ledger.getMoldCode());
                moldOutbound.setMoldArea(ledger.getMoldArea());
                moldOutbound.setMoldMaterial(ledger.getMoldMaterial());
                moldOutbound.setEngineeringTolerance(ledger.getEngineeringTolerance());
                moldOutbound.setMoldSpec(ledger.getMoldSpec());
                moldOutbound.setMoldType(ledger.getMoldType());
                moldOutbound.setMoldAperture(ledger.getMoldAperture());
                moldOutbound.setAttr3(ledger.getMoldNum().intValue());
                this.moldOutboundMapper.insert(moldOutbound);
            }
        }
        if (moldOutbound.getMoldLedger()!=null){
            if (Objects.equals(moldOutbound.getMoldLedger().getStatus(), "UNDER_REPAIR") || Objects.equals(moldOutbound.getMoldLedger().getStatus(), "MAINTENANCE")){
                throw new BussinessException("模具状态为维修中或保养状态，不能出库");

            }
            if (Objects.equals(moldOutbound.getMoldLedger().getStatus(), "CHECKED_OUT")){
                throw new BussinessException("模具状态为出库状态，不能重复出库");
            }
            moldOutbound.setMoldId(moldOutbound.getMoldLedger().getMoldId());
            moldOutbound.setMoldCode(moldOutbound.getMoldLedger().getMoldCode());
            moldOutbound.setMoldArea(moldOutbound.getMoldLedger().getMoldArea());
            moldOutbound.setMoldMaterial(moldOutbound.getMoldLedger().getMoldMaterial());
            moldOutbound.setMoldSpec(moldOutbound.getMoldLedger().getMoldSpec());
            moldOutbound.setMoldType(moldOutbound.getMoldLedger().getMoldType());
            moldOutbound.setMoldAperture(moldOutbound.getMoldLedger().getMoldAperture());
            this.moldOutboundMapper.insert(moldOutbound);
        }
        if (!nestingLedgerList.isEmpty()) {
            for (MoldLedger ledger : nestingLedgerList){
                moldOutbound.setMoldId(ledger.getMoldId());
                moldOutbound.setNestingId(ledger.getNestingMoldId());
                moldOutbound.setMoldCode(ledger.getMoldCode());
                moldOutbound.setMoldArea(ledger.getMoldArea());
                moldOutbound.setMoldMaterial(ledger.getMoldMaterial());
                moldOutbound.setMoldSpec(ledger.getMoldSpec());
                moldOutbound.setMoldType(ledger.getMoldType());
                moldOutbound.setMoldAperture(ledger.getMoldAperture());
                MoldNesting moldNesting = this.moldNestingMapper.queryById(ledger.getNestingMoldId());
                moldOutbound.setMoldName(moldNesting.getNestingName());
                this.moldOutboundMapper.insert(moldOutbound);
            }
        }
        return moldOutbound;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(MoldOutbound moldOutbound) {
        if (moldOutbound != null) {
            //单个模
            List<MoldLedger> ledgerList = new ArrayList<>();
            List<MoldLedger> nestingledgerList = new ArrayList<>();
            if (moldOutbound.getMoldLedgerList() != null) {
                for (MoldLedger ledger : moldOutbound.getMoldLedgerList()) {
                    if (Objects.equals(ledger.getStatus(), "UNDER_REPAIR") || Objects.equals(ledger.getStatus(), "MAINTENANCE")){
                        throw new BussinessException("模具状态为维修中或保养状态，不能出库");

                    }
                    if (Objects.equals(ledger.getStatus(), "CHECKED_OUT")){
                        throw new BussinessException("模具状态为出库状态，不能重复出库");
                    }
                    if (ledger.getNestingMoldId() == null) {
                        ledgerList.add(ledger);
                    } else {
                        nestingledgerList.add(ledger);
                    }
                }
            }
            if (!ledgerList.isEmpty()) {
                for (MoldLedger ledger : ledgerList) {
                    MoldLedger moldLedger = this.moldLedgerService.queryById(ledger.getMoldId());
                    Long moldNum = moldLedger.getMoldNum();
                    Long moldNum1 = ledger.getMoldNum();
                    if (moldNum < moldNum1){
                        throw new BussinessException("模具编号"+moldLedger.getMoldCode()+"数量不足，无法出库");
                    }else{
                        moldLedger.setMoldNum(moldNum - moldNum1);
                        this.moldLedgerService.update(moldLedger);
                    }

                    //新增生命周期列表数据
                    MoldLifeCycle moldLifeCycle = getMoldLifeCycle(ledger);
                    this.moldLifeCycleMapper.insert(moldLifeCycle);

                    //修改模具出入库状态(根据传入的MoldLedger进行修改)
                    if (moldLedger.getMoldNum() == 0){
                        ledger.setStatus("CHECKED_OUT");
                    }
                    ledger.setUpdateTime(new Date());
                    ledger.setUpdateBy(SecurityUtils.getUsername());
                    this.moldLedgerService.updateStatus(ledger);
                    //新增待归还列表
                    MoldReturned moldReturned = getMoldReturned(ledger, moldOutbound);
                    this.moldReturnedMapper.insert(moldReturned);
                }
            }
            //套模
            //修改套模内的模具出入库状态(根据传入的MoldLedgerList进行修改)
            if (!nestingledgerList.isEmpty()) {
                for (MoldLedger curLedger : nestingledgerList) {

                    MoldLedger moldLedger = this.moldLedgerService.queryById(curLedger.getMoldId());
                    Long moldNum = moldLedger.getMoldNum();
                    Long moldNum1 = curLedger.getMoldNum();
                    if (moldNum < moldNum1){
                        throw new BussinessException("模具编号"+moldLedger.getMoldCode()+"数量不足，无法出库");
                    }else{
                        moldLedger.setMoldNum(moldNum - moldNum1);
                        this.moldLedgerService.update(moldLedger);
                    }

                    //新增生命周期列表数据
                    MoldLifeCycle moldLifeCycle = getMoldLifeCycle(curLedger);
                    this.moldLifeCycleMapper.insert(moldLifeCycle);

                    //修改模具出入库状态(根据传入的MoldLedger进行修改)
                    MoldLedger newLedger = new MoldLedger();
                    newLedger.setMoldId(curLedger.getMoldId());
                    newLedger.setStatus("CHECKED_OUT");
                    newLedger.setUpdateBy(SecurityUtils.getUsername());
                    newLedger.setUpdateTime(new Date());
                    this.moldLedgerService.updateStatus(newLedger);
                }
                if (moldOutbound.getMoldNesting() != null) {
                    //新增待归还列表
                    MoldReturned moldReturned = new MoldReturned();
                    moldReturned.setNestingMoldId(moldOutbound.getMoldNesting().getNestingMoldId());
                    moldReturned.setMoldOutArea(moldOutbound.getMoldNesting().getNestingArea());
                    moldReturned.setMoldName(moldOutbound.getMoldNesting().getNestingName());
                    if (moldOutbound.getMoldLedger() != null) {
                        if (Objects.equals(moldOutbound.getMoldLedger().getStatus(), "UNDER_REPAIR") || Objects.equals(moldOutbound.getMoldLedger().getStatus(), "MAINTENANCE")){
                            throw new BussinessException("模具状态为维修中或保养状态，不能出库");

                        }
                        if (Objects.equals(moldOutbound.getMoldLedger().getStatus(), "CHECKED_OUT")){
                            throw new BussinessException("模具状态为出库状态，不能重复出库");
                        }
                        moldReturned.setMoldOutCode(moldOutbound.getMoldLedger().getMoldCode());
                        moldReturned.setMoldOutAperture(moldOutbound.getMoldLedger().getMoldAperture());
                        //修改模具出入库状态(根据传入的MoldLedger进行修改)
                        MoldLedger newLedgers = new MoldLedger();
                        newLedgers.setMoldId(moldOutbound.getMoldLedger().getMoldId());
                        newLedgers.setStatus("CHECKED_OUT");
                        newLedgers.setUpdateBy(SecurityUtils.getUsername());
                        newLedgers.setUpdateTime(new Date());
                        //新加项
                        newLedgers.setMoldNum(0L);
                        this.moldLedgerService.updateStatusAndNum(newLedgers);
                        //新增待归还列表
                        MoldReturned moldReturned1 = getMoldReturned(moldOutbound.getMoldLedger(), moldOutbound);
                        this.moldReturnedMapper.insert(moldReturned1);
                    }
                    moldReturned.setQbId(moldOutbound.getQbId());
                    moldReturned.setLsId(moldOutbound.getLsId());
                    moldReturned.setCreateTime(new Date());
                    moldReturned.setCreateBy(SecurityUtils.getUsername());
                    this.moldReturnedMapper.insert(moldReturned);
                }
            }

        }
        return true;
    }

    private MoldLifeCycle getMoldLifeCycle(MoldLedger ledger) {
        MoldLifeCycle moldLifeCycle = new MoldLifeCycle();
        moldLifeCycle.setMoldCode(ledger.getMoldCode());
        moldLifeCycle.setMoldArea(ledger.getMoldArea());
        moldLifeCycle.setMoldMaterial(ledger.getMoldMaterial());
        moldLifeCycle.setMoldSpec(ledger.getMoldSpec());
        moldLifeCycle.setMoldType(ledger.getMoldType());
        moldLifeCycle.setMoldId(ledger.getMoldId());
        moldLifeCycle.setCreateTime(new Date());
        moldLifeCycle.setCreateBy(SecurityUtils.getUsername());
        moldLifeCycle.setStatus("CHECKED_OUT");
        if(ledger.getNestingMoldId()!=null){
            MoldNesting moldNesting = this.moldNestingMapper.queryById(ledger.getNestingMoldId());
            moldLifeCycle.setMoldName(moldNesting.getNestingName());
        }
        return moldLifeCycle;
    }

    private MoldReturned getMoldReturned(MoldLedger moldOutbound, MoldOutbound moldOutbound1) {
        MoldReturned moldReturned1 = new MoldReturned();
        moldReturned1.setMoldId(moldOutbound.getMoldId());
        moldReturned1.setMoldOutArea(moldOutbound.getMoldArea());
        moldReturned1.setQbId(moldOutbound1.getQbId());
        moldReturned1.setLsId(moldOutbound1.getLsId());
        moldReturned1.setCreateTime(new Date());
        moldReturned1.setCreateBy(SecurityUtils.getUsername());
        moldReturned1.setMoldCode(moldOutbound.getMoldCode());
        moldReturned1.setMoldArea(moldOutbound.getMoldArea());
        moldReturned1.setMoldMaterial(moldOutbound.getMoldMaterial());
        moldReturned1.setMoldSpec(moldOutbound.getMoldSpec());
        moldReturned1.setMoldType(moldOutbound.getMoldType());
        //新增项
        moldReturned1.setAttr3(moldOutbound.getMoldNum().intValue());
        return moldReturned1;
    }

    /**
     * 修改数据
     *
     * @param moldOutbound 实例对象
     * @return 实例对象
     */
    @Override
    public MoldOutbound update(MoldOutbound moldOutbound) {
        moldOutbound.setUpdateTime(new Date());
        moldOutbound.setUpdateBy(SecurityUtils.getUsername());
        this.moldOutboundMapper.update(moldOutbound);
        return this.queryById(moldOutbound.getMoldOutboundId());
    }

    /**
     * 通过主键删除数据
     *
     * @param moldOutboundId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long moldOutboundId) {
        return this.moldOutboundMapper.deleteById(moldOutboundId) > 0;
    }
}
