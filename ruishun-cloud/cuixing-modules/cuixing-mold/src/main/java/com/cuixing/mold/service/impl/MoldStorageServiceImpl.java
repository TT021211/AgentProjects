package com.cuixing.mold.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.*;
import com.cuixing.mold.mapper.*;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.mold.service.MoldStorageService;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.domain.md.MdWorkbenchEnamelling;
import com.cuixing.system.api.domain.md.MdWorkbenchWiredrawing;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 模具入库表(MoldStorage)表服务实现类
 *
 * @author makejava
 * @since 2024-06-25 14:06:51
 */
@Service("moldStorageService")
public class MoldStorageServiceImpl implements MoldStorageService {
    @Resource
    private MoldStorageMapper moldStorageMapper;
    @Resource
    private MoldLedgerService moldLedgerService;
    @Resource
    private RemoteMdService remoteMdService;
    @Resource
    private MoldLifeCycleMapper moldLifeCycleMapper;
    @Resource
    private MoldNestingMapper moldNestingMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param moldStorageId 主键
     * @return 实例对象
     */
    @Override
    public MoldStorage queryById(Long moldStorageId) {
        return this.moldStorageMapper.queryById(moldStorageId);
    }

    /**
     * 分页查询
     *
     * @param moldStorage 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldStorage> selectMoldStorageList(MoldStorage moldStorage) {
        List<MoldStorage> moldStorages = this.moldStorageMapper.selectMoldStorageList(moldStorage);
        if (!moldStorages.isEmpty()) {
            for (MoldStorage curOutbound : moldStorages){
                if (curOutbound.getLsId()!=null){
                    MdWorkbenchWiredrawing data = this.remoteMdService.getWorkbenchWiredrawingById(BigInteger.valueOf(curOutbound.getLsId()), SecurityConstants.INNER).getData();
                    if (data !=null){
                        curOutbound.setLsName(data.getEquipmentName());
                    }
                }
                if (curOutbound.getQbId() != null) {
                    MdWorkbenchEnamelling data = this.remoteMdService.getWorkbenchEnamellingById(BigInteger.valueOf(curOutbound.getQbId()), SecurityConstants.INNER).getData();
                    if (data != null) {
                        curOutbound.setQbName(data.getEquipmentName());
                    }
                }
            }
        }
        return moldStorages;
    }

    /**
     * 新增数据
     *
     * @param moldStorage 实例对象
     * @return 实例对象
     */
    @Override
    public MoldStorage insert(MoldStorage moldStorage) {
        moldStorage.setCreateTime(new Date());
        moldStorage.setCreateBy(SecurityUtils.getUsername());
        List<MoldLedger> ledgerList = new ArrayList<>();
        List<MoldLedger> nestingledgerList = new ArrayList<>();
        if (moldStorage.getMoldLedgerList()!= null){
            for (MoldLedger ledger : moldStorage.getMoldLedgerList()){
                if (ledger.getNestingMoldId()==null){
                    ledgerList.add(ledger);
                }else{
                    nestingledgerList.add(ledger);
                }
            }
        }
        if (!ledgerList.isEmpty()) {
            for (MoldLedger ledger : ledgerList){
                moldStorage.setMoldId(ledger.getMoldId());
                moldStorage.setMoldCode(ledger.getMoldCode());
                moldStorage.setMoldArea(ledger.getMoldArea());
                moldStorage.setMoldMaterial(ledger.getMoldMaterial());
                moldStorage.setMoldSpec(ledger.getMoldSpec());
                moldStorage.setMoldType(ledger.getMoldType());
                moldStorage.setMoldAperture(ledger.getMoldAperture());
                this.moldStorageMapper.insert(moldStorage);
            }
        }
        if (!nestingledgerList.isEmpty()){
            for (MoldLedger ledger : nestingledgerList){
                moldStorage.setMoldId(ledger.getMoldId());
                moldStorage.setNestingId(ledger.getNestingMoldId());
                moldStorage.setMoldCode(ledger.getMoldCode());
                moldStorage.setMoldArea(ledger.getMoldArea());
                moldStorage.setMoldMaterial(ledger.getMoldMaterial());
                moldStorage.setMoldSpec(ledger.getMoldSpec());
                moldStorage.setMoldType(ledger.getMoldType());
                moldStorage.setMoldAperture(ledger.getMoldAperture());
                MoldNesting moldNesting = this.moldNestingMapper.queryById(ledger.getNestingMoldId());
                moldStorage.setMoldName(moldNesting.getNestingName());
                this.moldStorageMapper.insert(moldStorage);
            }
        }
        return moldStorage;
    }

    /**
     * 确认入库
     * @param moldStorage
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(MoldStorage moldStorage) {
        if (moldStorage != null) {
            //单个模
            List<MoldLedger> ledgerList = new ArrayList<>();
            List<MoldLedger> nestingledgerList = new ArrayList<>();
            if (moldStorage.getMoldLedgerList() != null) {
                for (MoldLedger ledger : moldStorage.getMoldLedgerList()) {
                    if (ledger.getNestingMoldId() == null) {
                        ledgerList.add(ledger);
                    } else {
                        nestingledgerList.add(ledger);
                    }
                }
            }
            if (!ledgerList.isEmpty()) {
                for (MoldLedger ledger : ledgerList) {
                    //新增生命周期列表数据
                    MoldLifeCycle moldLifeCycle = getLifeCycle(ledger);
                    this.moldLifeCycleMapper.insert(moldLifeCycle);

                    //修改模具出入库状态(根据传入的MoldLedger进行修改)
                    ledger.setInOutStatus("CHECKED_IN");
                    this.moldLedgerService.updateStatus(ledger);

                    return true;
                }
            }
            //套模
            //修改套模内的模具出入库状态(根据传入的MoldLedgerList进行修改)
            if (!nestingledgerList.isEmpty()) {
                for (MoldLedger curLedger : nestingledgerList) {
                    MoldLifeCycle moldLifeCycle = getLifeCycle(curLedger);
                    this.moldLifeCycleMapper.insert(moldLifeCycle);

                    MoldLedger newLedger = new MoldLedger();
                    newLedger.setMoldId(curLedger.getMoldId());
                    newLedger.setInOutStatus("CHECKED_IN");
                    this.moldLedgerService.updateStatus(newLedger);

                    return true;
                }
            } else {
                throw new BussinessException("传入的数组为空或者无数据");
            }
        }
        return false;
    }

    private static MoldLifeCycle getLifeCycle(MoldLedger ledger) {
        MoldLifeCycle moldLifeCycle = new MoldLifeCycle();
        moldLifeCycle.setMoldId(ledger.getMoldId());
        moldLifeCycle.setMoldCode(ledger.getMoldCode());
        moldLifeCycle.setMoldArea(ledger.getMoldArea());
        moldLifeCycle.setMoldMaterial(ledger.getMoldMaterial());
        moldLifeCycle.setMoldSpec(ledger.getMoldSpec());
        moldLifeCycle.setMoldType(ledger.getMoldType());
        moldLifeCycle.setCreateTime(new Date());
        moldLifeCycle.setStatus("CHECKED_IN");
        return moldLifeCycle;
    }

    /**
     * 修改数据
     *
     * @param moldStorage 实例对象
     * @return 实例对象
     */
    @Override
    public MoldStorage update(MoldStorage moldStorage) {
        moldStorage.setUpdateTime(new Date());
        moldStorage.setUpdateBy(SecurityUtils.getUsername());
        this.moldStorageMapper.update(moldStorage);
        return this.queryById(moldStorage.getMoldStorageId());
    }

    /**
     * 通过主键删除数据
     *
     * @param moldStorageId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long moldStorageId) {
        return this.moldStorageMapper.deleteById(moldStorageId) > 0;
    }
}
