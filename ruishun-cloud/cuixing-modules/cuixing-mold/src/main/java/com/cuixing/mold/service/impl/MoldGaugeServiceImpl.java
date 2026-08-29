package com.cuixing.mold.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.*;
import com.cuixing.mold.mapper.*;
import com.cuixing.mold.service.MoldGaugeService;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteMesService;
import com.cuixing.system.api.domain.md.MdClient;
import com.cuixing.system.api.domain.md.MdWorkbenchEnamelling;
import com.cuixing.system.api.domain.md.MdWorkbenchWiredrawing;
import com.cuixing.system.api.domain.mes.MesOuputLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 模具换规表(MoldGauge)表服务实现类
 *
 * @author makejava
 * @since 2024-06-25 14:06:45
 */
@Service("moldGaugeService")
public class MoldGaugeServiceImpl implements MoldGaugeService {
    @Resource
    private MoldGaugeMapper moldGaugeMapper;
    @Resource
    private RemoteMdService remoteMdService;
    @Resource
    private RemoteMesService remoteMesService;
    @Resource
    private MoldLedgerService moldLedgerService;
    @Resource
    private MoldLifeCycleMapper moldLifeCycleMapper;
    @Resource
    private MoldReturnedMapper moldReturnedMapper;
    @Resource
    private MoldNestingMapper moldNestingMapper;
    @Resource
    private MoldOutboundMapper moldOutboundMapper;
    /**
     * 通过ID查询单条数据
     *
     * @param moldGaugeId 主键
     * @return 实例对象
     */
    @Override
    public MoldGauge queryById(Long moldGaugeId) {
        return this.moldGaugeMapper.queryById(moldGaugeId);
    }

    /**
     * 分页查询
     *
     * @param moldGauge 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldGauge> selectMoldGaugeList(MoldGauge moldGauge) {
        List<MoldGauge> moldGauges = this.moldGaugeMapper.selectMoldGaugeList(moldGauge);
        if (!moldGauges.isEmpty()){
            for (MoldGauge curOutbound : moldGauges){
                if (curOutbound.getLsDeviceId()!=null){
                    MdWorkbenchWiredrawing data = this.remoteMdService.getWorkbenchWiredrawingById(BigInteger.valueOf(curOutbound.getLsDeviceId()), SecurityConstants.INNER).getData();
                    if (data != null){
                        curOutbound.setLsName(data.getEquipmentName());
                    }
                }
                if (curOutbound.getQbDeviceId()!=null){
                    MdWorkbenchEnamelling data = this.remoteMdService.getWorkbenchEnamellingById(BigInteger.valueOf(curOutbound.getQbDeviceId()), SecurityConstants.INNER).getData();
                    if (data != null){
                        curOutbound.setQbName(data.getEquipmentName());
                    }
                }
                if (curOutbound.getClientId()!=null){
                    MdClient data = this.remoteMdService.getInfo(BigInteger.valueOf(curOutbound.getClientId()), SecurityConstants.INNER).getData();
                    if (data != null){
                        curOutbound.setClientName(data.getClientName());
                    }
                }
            }
        }
        return moldGauges;
    }

    /**
     * 新增数据
     *
     * @param moldGauge 实例对象
     * @return 实例对象
     */
    @Override
    public MoldGauge insert(MoldGauge moldGauge) {
        moldGauge.setCreateTime(new Date());
        moldGauge.setCreateBy(SecurityUtils.getUsername());
        this.moldGaugeMapper.insert(moldGauge);
        return moldGauge;
    }

    /**
     * 批量新增模具规格
     * @param moldGauges 模具规格列表
     * @return 操作结果
     */
    @Override
    public boolean addMoldGaugeBatch(List<MoldGauge> moldGauges) {
        for (MoldGauge moldGauge : moldGauges) {
            MesOuputLog mesOuputLog = this.remoteMesService.getSpecification(moldGauge.getAttr1(), SecurityConstants.INNER).getData();
            moldGauge.setMoldAfterSpec(moldGauge.getMoldAfterSpec());
            moldGauge.setSpec(moldGauge.getSpec());
            if (mesOuputLog != null){
                moldGauge.setMoldBeforSpec(mesOuputLog.getItemSpecification());
            }else{
                moldGauge.setMoldBeforSpec(null);
            }
        }
        return moldGaugeMapper.insertBatch(moldGauges) > 0;
    }

    /**
     * 修改数据
     *
     * @param moldGauge 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MoldGauge update(MoldGauge moldGauge) {
        if (moldGauge != null) {
            //单个模
            List<MoldLedger> ledgerList = new ArrayList<>();
            List<MoldLedger> nestingledgerList = new ArrayList<>();
            if (moldGauge.getMoldOutbound().getMoldLedgerList() != null) {
                for (MoldLedger ledger : moldGauge.getMoldOutbound().getMoldLedgerList()) {
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

                    //新增出库记录
                    MoldOutbound moldOutbound = getMoldOutbound(ledger, moldGauge.getMoldOutbound(),moldGauge);
                    this.moldOutboundMapper.insert(moldOutbound);

                    //新增生命周期列表数据
                    MoldLifeCycle moldLifeCycle = getMoldLifeCycle(ledger);
                    this.moldLifeCycleMapper.insert(moldLifeCycle);

                    //修改模具出入库状态(根据传入的MoldLedger进行修改)
                    ledger.setStatus("CHECKED_OUT");
                    ledger.setUpdateTime(new Date());
                    ledger.setUpdateBy(SecurityUtils.getUsername());
                    this.moldLedgerService.updateStatus(ledger);
                    //新增待归还列表
                    MoldReturned moldReturned = getMoldReturned(ledger, moldGauge.getMoldOutbound());
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

                    //新增出库记录
                    MoldOutbound moldOutbound = new MoldOutbound();
                    moldOutbound.setMoldId(curLedger.getMoldId());
                    moldOutbound.setNestingId(curLedger.getNestingMoldId());
                    moldOutbound.setMoldCode(curLedger.getMoldCode());
                    moldOutbound.setMoldArea(curLedger.getMoldArea());
                    moldOutbound.setMoldMaterial(curLedger.getMoldMaterial());
                    moldOutbound.setMoldSpec(curLedger.getMoldSpec());
                    moldOutbound.setMoldType(curLedger.getMoldType());
                    moldOutbound.setMoldAperture(curLedger.getMoldAperture());
                    MoldNesting moldNesting = this.moldNestingMapper.queryById(curLedger.getNestingMoldId());
                    moldOutbound.setMoldName(moldNesting.getNestingName());
                    moldOutbound.setRecipient(moldGauge.getMoldOutbound().getRecipient());
                    moldOutbound.setAttr1(moldGauge.getMoldOutbound().getAttr1());
                    moldOutbound.setMoldOutboundTime(moldGauge.getMoldOutbound().getMoldOutboundTime());
                    if(moldGauge.getQbDeviceId()!=null){
                        moldOutbound.setQbId(moldGauge.getQbDeviceId());
                    }
                    if (moldGauge.getLsDeviceId()!=null) {
                        moldOutbound.setLsId(moldGauge.getLsDeviceId());
                    }
                    this.moldOutboundMapper.insert(moldOutbound);
                }
                if (moldGauge.getMoldOutbound().getMoldNesting() != null) {
                    //新增待归还列表
                    MoldReturned moldReturned = new MoldReturned();
                    moldReturned.setNestingMoldId(moldGauge.getMoldOutbound().getMoldNesting().getNestingMoldId());
                    moldReturned.setMoldOutArea(moldGauge.getMoldOutbound().getMoldNesting().getNestingArea());
                    moldReturned.setMoldName(moldGauge.getMoldOutbound().getMoldNesting().getNestingName());
                    if (moldGauge.getMoldOutbound().getMoldLedger() != null) {
                        if (Objects.equals(moldGauge.getMoldOutbound().getMoldLedger().getStatus(), "UNDER_REPAIR") || Objects.equals(moldGauge.getMoldOutbound().getMoldLedger().getStatus(), "MAINTENANCE")){
                            throw new BussinessException("模具状态为维修中或保养状态，不能出库");

                        }
                        if (Objects.equals(moldGauge.getMoldOutbound().getMoldLedger().getStatus(), "CHECKED_OUT")){
                            throw new BussinessException("模具状态为出库状态，不能重复出库");
                        }
                        moldReturned.setMoldOutCode(moldGauge.getMoldOutbound().getMoldLedger().getMoldCode());
                        moldReturned.setMoldOutAperture(moldGauge.getMoldOutbound().getMoldLedger().getMoldAperture());
                        //修改模具出入库状态(根据传入的MoldLedger进行修改)
                        MoldLedger newLedgers = new MoldLedger();
                        newLedgers.setMoldId(moldGauge.getMoldOutbound().getMoldLedger().getMoldId());
                        newLedgers.setStatus("CHECKED_OUT");
                        newLedgers.setUpdateBy(SecurityUtils.getUsername());
                        newLedgers.setUpdateTime(new Date());
                        //新加项
                        newLedgers.setMoldNum(0L);
                        this.moldLedgerService.updateStatusAndNum(newLedgers);
                        //新增待归还列表
                        MoldReturned moldReturned1 = getMoldReturned(moldGauge.getMoldOutbound().getMoldLedger(), moldGauge.getMoldOutbound());
                        this.moldReturnedMapper.insert(moldReturned1);
                    }
                    moldReturned.setQbId(moldGauge.getMoldOutbound().getQbId());
                    moldReturned.setLsId(moldGauge.getMoldOutbound().getLsId());
                    moldReturned.setCreateTime(new Date());
                    moldReturned.setCreateBy(SecurityUtils.getUsername());
                    this.moldReturnedMapper.insert(moldReturned);
                }
            }
            moldGauge.setUpdateTime(new Date());
            moldGauge.setUpdateBy(SecurityUtils.getUsername());
            this.moldGaugeMapper.update(moldGauge);
        }
        assert moldGauge != null;
        return this.queryById(moldGauge.getMoldGaugeId());
    }

    private static MoldOutbound getMoldOutbound(MoldLedger curLedger,MoldOutbound cmoldOutbound,MoldGauge moldGauge) {
        MoldOutbound moldOutbound = new MoldOutbound();
        moldOutbound.setMoldId(curLedger.getMoldId());
        moldOutbound.setMoldCode(curLedger.getMoldCode());
        moldOutbound.setMoldArea(curLedger.getMoldArea());
        moldOutbound.setMoldMaterial(curLedger.getMoldMaterial());
        moldOutbound.setEngineeringTolerance(curLedger.getEngineeringTolerance());
        moldOutbound.setMoldSpec(curLedger.getMoldSpec());
        moldOutbound.setMoldType(curLedger.getMoldType());
        moldOutbound.setMoldAperture(curLedger.getMoldAperture());
        moldOutbound.setRecipient(cmoldOutbound.getRecipient());
        moldOutbound.setAttr1(cmoldOutbound.getAttr1());
        moldOutbound.setMoldOutboundTime(cmoldOutbound.getMoldOutboundTime());
        if(moldGauge.getQbDeviceId()!=null){
            moldOutbound.setQbId(moldGauge.getQbDeviceId());
        }
        if (moldGauge.getLsDeviceId()!=null) {
            moldOutbound.setLsId(moldGauge.getLsDeviceId());
        }
        return moldOutbound;
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
        return moldReturned1;
    }
    /**
     * 通过主键删除数据
     *
     * @param moldGaugeId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long moldGaugeId) {
        return this.moldGaugeMapper.deleteById(moldGaugeId) > 0;
    }
}
