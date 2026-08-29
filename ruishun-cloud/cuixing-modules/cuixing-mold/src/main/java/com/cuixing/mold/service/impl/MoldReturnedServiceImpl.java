package com.cuixing.mold.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.*;
import com.cuixing.mold.mapper.*;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.mold.service.MoldReturnedService;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.domain.md.MdWorkbenchEnamelling;
import com.cuixing.system.api.domain.md.MdWorkbenchWiredrawing;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 模具待归还表(MoldReturned)表服务实现类
 *
 * @author makejava
 * @since 2024-06-26 17:26:10
 */
@Service("moldReturnedService")
public class MoldReturnedServiceImpl implements MoldReturnedService {
    @Resource
    private MoldReturnedMapper moldReturnedMapper;
    @Resource
    private MoldLedgerService moldLedgerService;
    @Resource
    private MoldStorageMapper moldStorageMapper;
    @Resource
    private MoldLifeCycleMapper moldLifeCycleMapper;
    @Resource
    private MoldNestingMapper moldNestingMapper;
    @Resource
    private RemoteMdService remoteMdService;

    /**
     * 通过ID查询单条数据
     *
     * @param moldReturnedId 主键
     * @return 实例对象
     */
    @Override
    public MoldReturned queryById(Long moldReturnedId) {
        return this.moldReturnedMapper.queryById(moldReturnedId);
    }

    /**
     * 分页查询
     *
     * @param moldReturned 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldReturned> selectMoldReturnedList(MoldReturned moldReturned) {
        List<MoldReturned> moldReturneds = this.moldReturnedMapper.selectMoldReturnedList(moldReturned);
        if (!moldReturneds.isEmpty()) {
            for (MoldReturned curOutbound : moldReturneds) {
                // 填充外键数据
                //...
                if(curOutbound.getNestingMoldId()!=null){
                    List<MoldLedger> moldLedgers = this.moldLedgerService.queryByNestingMoldId(curOutbound.getNestingMoldId());
                    if (!moldLedgers.isEmpty()) {
                        curOutbound.setMoldLedgerList(moldLedgers);
                    }
                    MoldNesting moldNesting = this.moldNestingMapper.queryById(curOutbound.getNestingMoldId());
                    if (moldNesting!=null) {
                        curOutbound.setMoldNesting(moldNesting);
                    }

                }
                if (curOutbound.getMoldId()!=null && curOutbound.getNestingMoldId()==null){
                    MoldLedger ledger = this.moldLedgerService.queryById(curOutbound.getMoldId());
                    if(ledger!=null){
                        curOutbound.setMoldLedger(ledger);
                    }
                }
                if (curOutbound.getLsId() != null) {
                    MdWorkbenchWiredrawing data = this.remoteMdService.getWorkbenchWiredrawingById(BigInteger.valueOf(curOutbound.getLsId()), SecurityConstants.INNER).getData();
                    if (data!=null) {
                        curOutbound.setLsName(data.getEquipmentName());
                    }
                }
                if (curOutbound.getNestingMoldId()!=null){
                    List<MoldLedger> moldLedgers = this.moldLedgerService.queryByNestingMoldId(curOutbound.getNestingMoldId());
                    if (!moldLedgers.isEmpty()){
                        moldLedgers.removeIf(ledger -> Objects.equals(ledger.getStatus(), "CHECKED_IN"));
                        curOutbound.setMoldLedgerList(moldLedgers);
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
        return moldReturneds;
    }

    /**
     * 新增数据
     *
     * @param moldReturned 实例对象
     * @return 实例对象
     */
    @Override
    public MoldReturned insert(MoldReturned moldReturned) {
        moldReturned.setCreateTime(new Date());
        moldReturned.setCreateBy(SecurityUtils.getUsername());
        this.moldReturnedMapper.insert(moldReturned);
        return moldReturned;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(MoldReturned moldReturned) {
        if(moldReturned.getMoldId()!=null&&moldReturned.getNestingMoldId()!=null){
            throw new BussinessException("数据有误，同时传入了个模与套模ID");
        }
        //单个模对象
        if (moldReturned.getMoldId()!=null) {
            //新增一张入库单
            MoldStorage storage = getMoldStorage(moldReturned);
            this.moldStorageMapper.insert(storage);
            long returnNum = 0;
            if (moldReturned.getMoldLedger()!=null) {
                returnNum = moldReturned.getMoldLedger().getMoldNum();
                //新增生命周期列表数据
                MoldLifeCycle moldLifeCycle = getMoldLifeCycle(moldReturned);
                this.moldLifeCycleMapper.insert(moldLifeCycle);

                MoldLedger moldLedger = moldReturned.getMoldLedger();
                MoldLedger moldLedgers = this.moldLedgerService.queryById(moldLedger.getMoldId());
                Long moldNum = moldLedgers.getMoldNum();
                Long moldNum1 = moldLedger.getMoldNum();
                moldLedger.setMoldNum(moldNum + moldNum1);
                moldLedger.setUpdateTime(new Date());
                moldLedger.setStatus("CHECKED_IN");
                moldLedger.setUpdateBy(SecurityUtils.getUsername());
                moldLedger.setMoldId(moldReturned.getMoldId());
                //this.moldLedgerService.updateStatus(moldLedger);
                this.moldLedgerService.update(moldLedger);
            }
            //归还成功删除待归还记录
            if (moldReturned.getAttr3() == null || moldReturned.getAttr3() == returnNum){
                this.moldReturnedMapper.deleteById(moldReturned.getMoldReturnedId());
            }else{
                Long endAttr3 = moldReturned.getAttr3() - returnNum;
                this.moldReturnedMapper.updateNum(moldReturned.getMoldReturnedId(),endAttr3);
            }
            return true;
        }
        //套模对象
        if (moldReturned.getNestingMoldId()!=null) {
            //修改套模所含的模具状态
            List<MoldLedger> moldLedgerList = moldReturned.getMoldLedgerList();
            if (!moldLedgerList.isEmpty()){
                //批量修改模具出入库状态
                for (MoldLedger ledger : moldLedgerList){
                    //新增一张入库单
                    MoldStorage storage = getMoldStorage(moldReturned, ledger);
                    this.moldStorageMapper.insert(storage);

                        //更新状态
                        MoldLedger moldLedger=new MoldLedger();
                        MoldLedger moldLedgers = this.moldLedgerService.queryById(ledger.getMoldId());
                        Long moldNum = moldLedgers.getMoldNum();
                        Long moldNum1 = ledger.getMoldNum();
                        moldLedger.setMoldNum(moldNum + moldNum1);
                        moldLedger.setMoldId(ledger.getMoldId());
                        moldLedger.setStatus("CHECKED_IN");
                        moldLedger.setUpdateTime(new Date());
                        moldLedger.setUpdateBy(SecurityUtils.getUsername());
                        //this.moldLedgerService.updateStatus(moldLedger);
                        this.moldLedgerService.update(moldLedger);

                        //新增生命周期列表数据
                    MoldLifeCycle moldLifeCycle = getMoldLifeCycle(ledger);
                    this.moldLifeCycleMapper.insert(moldLifeCycle);
                    }
                //判断归还个数，如全部归还删除该归还记录
                List<MoldLedger> moldLedgerList1 = this.moldLedgerService.selectMoldLedgerList(new MoldLedger("CHECKED_OUT",moldReturned.getNestingMoldId()));
                if (moldLedgerList1.isEmpty()){
                    this.moldReturnedMapper.deleteById(moldReturned.getMoldReturnedId());
                }
            }
            return true;
        }
        return false;
    }

    private MoldLifeCycle getMoldLifeCycle(MoldLedger ledger) {
        MoldLifeCycle moldLifeCycle = new MoldLifeCycle();
        moldLifeCycle.setMoldId(ledger.getMoldId());
        moldLifeCycle.setCreateTime(new Date());
        moldLifeCycle.setStatus("CHECKED_IN");
        moldLifeCycle.setCreateTime(new Date());
        moldLifeCycle.setCreateBy(SecurityUtils.getUsername());
        moldLifeCycle.setMoldCode(ledger.getMoldCode());
        moldLifeCycle.setMoldArea(ledger.getMoldArea());
        moldLifeCycle.setMoldMaterial(ledger.getMoldMaterial());
        moldLifeCycle.setMoldSpec(ledger.getMoldSpec());
        moldLifeCycle.setMoldType(ledger.getMoldType());
        if (ledger.getNestingMoldId()!=null){
            MoldNesting moldNesting = this.moldNestingMapper.queryById(ledger.getNestingMoldId());
            moldLifeCycle.setMoldName(moldNesting.getNestingName());
        }
        return moldLifeCycle;
    }

    private MoldLifeCycle getMoldLifeCycle(MoldReturned moldReturned) {
        MoldLifeCycle moldLifeCycle = new MoldLifeCycle();
        moldLifeCycle.setMoldCode(moldReturned.getMoldLedger().getMoldCode());
        moldLifeCycle.setMoldArea(moldReturned.getMoldLedger().getMoldArea());
        moldLifeCycle.setMoldMaterial(moldReturned.getMoldLedger().getMoldMaterial());
        moldLifeCycle.setMoldSpec(moldReturned.getMoldLedger().getMoldSpec());
        moldLifeCycle.setMoldType(moldReturned.getMoldLedger().getMoldType());
        moldLifeCycle.setMoldId(moldReturned.getMoldLedger().getMoldId());
        if (moldReturned.getMoldLedger().getNestingMoldId()!=null){
            MoldNesting moldNesting = this.moldNestingMapper.queryById(moldReturned.getMoldLedger().getNestingMoldId());
            moldLifeCycle.setMoldName(moldNesting.getNestingName());
        }
        moldLifeCycle.setCreateTime(new Date());
        moldLifeCycle.setCreateBy(SecurityUtils.getUsername());
        moldLifeCycle.setStatus("CHECKED_IN");
        return moldLifeCycle;
    }

    private MoldStorage getMoldStorage(MoldReturned moldReturned, MoldLedger ledger) {
        MoldStorage storage=new MoldStorage();
        storage.setMoldId(ledger.getMoldId());
        storage.setCreateTime(new Date());
        storage.setCreateBy(SecurityUtils.getUsername());
        storage.setQbId(moldReturned.getQbId());
        storage.setLsId(moldReturned.getLsId());
        storage.setRecipient(moldReturned.getInBy());
        storage.setRemark(moldReturned.getRemark());
        storage.setCreateTime(new Date());
        storage.setCreateBy(SecurityUtils.getUsername());
        storage.setMoldStorageTime(new Date());
        storage.setNestingId(moldReturned.getNestingMoldId());
        storage.setMoldCode(ledger.getMoldCode());
        storage.setMoldArea(ledger.getMoldArea());
        storage.setMoldMaterial(ledger.getMoldMaterial());
        storage.setMoldSpec(ledger.getMoldSpec());
        storage.setMoldType(ledger.getMoldType());
        storage.setMoldAperture(ledger.getMoldAperture());
        if (moldReturned.getNestingMoldId()!=null){
            MoldNesting moldNesting = this.moldNestingMapper.queryById(moldReturned.getNestingMoldId());
            storage.setMoldName(moldNesting.getNestingName());
        }
        return storage;
    }

    private MoldStorage getMoldStorage(MoldReturned moldReturned) {
        MoldStorage storage=new MoldStorage();
        storage.setCreateTime(new Date());
        storage.setMoldId(moldReturned.getMoldId());
        storage.setCreateBy(SecurityUtils.getUsername());
        storage.setQbId(moldReturned.getQbId());
        storage.setLsId(moldReturned.getLsId());
        storage.setRemark(moldReturned.getRemark());
        storage.setRecipient(moldReturned.getInBy());
        storage.setMoldId(moldReturned.getMoldId());
        storage.setCreateTime(new Date());
        storage.setMoldStorageTime(new Date());
        storage.setCreateBy(SecurityUtils.getUsername());
        storage.setMoldCode(moldReturned.getMoldLedger().getMoldCode());
        storage.setMoldArea(moldReturned.getMoldLedger().getMoldArea());
        storage.setMoldMaterial(moldReturned.getMoldLedger().getMoldMaterial());
        storage.setMoldSpec(moldReturned.getMoldLedger().getMoldSpec());
        storage.setMoldType(moldReturned.getMoldLedger().getMoldType());
        storage.setMoldAperture(moldReturned.getMoldLedger().getMoldAperture());
        if (moldReturned.getNestingMoldId()!=null){
            MoldNesting moldNesting = this.moldNestingMapper.queryById(moldReturned.getNestingMoldId());
            storage.setMoldName(moldNesting.getNestingName());
        }
        return storage;
    }

    /**
     * 修改数据
     *
     * @param moldReturned 实例对象
     * @return 实例对象
     */
    @Override
    public MoldReturned update(MoldReturned moldReturned) {
        moldReturned.setUpdateTime(new Date());
        moldReturned.setUpdateBy(SecurityUtils.getUsername());
        this.moldReturnedMapper.update(moldReturned);
        return this.queryById(moldReturned.getMoldReturnedId());
    }

    /**
     * 通过主键删除数据
     *
     * @param moldReturnedId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long moldReturnedId) {
        return this.moldReturnedMapper.deleteById(moldReturnedId) > 0;
    }
}
