package com.cuixing.mold.service.impl;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.*;
import com.cuixing.mold.mapper.MoldLifeCycleMapper;
import com.cuixing.mold.mapper.MoldMaintenanceMapper;
import com.cuixing.mold.mapper.MoldNestingMapper;
import com.cuixing.mold.mapper.MoldStorageMapper;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.mold.service.MoldMaintenanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 模具维修保养表(MoldMaintenance)表服务实现类
 *
 * @author makejava
 * @since 2024-07-01 11:01:19
 */
@Service("moldMaintenanceService")
public class MoldMaintenanceServiceImpl implements MoldMaintenanceService {
    @Resource
    private MoldMaintenanceMapper moldMaintenanceMapper;
    @Resource
    private MoldLedgerService moldLedgerService;
    @Resource
    private MoldStorageMapper moldStorageMapper;
    @Resource
    private MoldNestingMapper moldNestingMapper;
    @Resource
    private MoldLifeCycleMapper moldLifeCycleMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MoldMaintenance queryById(Long id) {
        return this.moldMaintenanceMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param moldMaintenance 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldMaintenance> selectMoldMaintenanceList(MoldMaintenance moldMaintenance) {
        return this.moldMaintenanceMapper.selectMoldMaintenanceList(moldMaintenance);
    }

    /**
     * 新增数据
     *
     * @param moldMaintenance 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MoldMaintenance insert(MoldMaintenance moldMaintenance) {
        moldMaintenance.setCreateTime(new Date());
        moldMaintenance.setCreateBy(SecurityUtils.getUsername());
        if(moldMaintenance.getMoldLedgerList()!=null){
            if (!moldMaintenance.getMoldLedgerList().isEmpty()){
                for (MoldLedger ledger : moldMaintenance.getMoldLedgerList()) {
                    if (Objects.equals(ledger.getStatus(), "UNDER_REPAIR") || Objects.equals(ledger.getStatus(), "MAINTENANCE")){
                        throw new BussinessException("模具状态已经在进行维修中或保养状态，不能重复选择");

                    }
                    if (Objects.equals(ledger.getStatus(), "CHECKED_OUT")){
                        throw new BussinessException("模具状态为出库状态，不能选中进行保养或者维修");
                    }
                    //修改模具状态
                    MoldLedger moldLedger=new MoldLedger();
                    moldLedger.setMoldId(ledger.getMoldId());
                    moldLedger.setStatus(moldMaintenance.getStatus());
                    this.moldLedgerService.updateStatus(moldLedger);
                    //新增保养维修表
                    MoldMaintenance moldMaintenance1=new MoldMaintenance();
                    moldMaintenance1.setMoldId(ledger.getMoldId());
                    moldMaintenance1.setCreateTime(new Date());
                    moldMaintenance1.setCreateBy(SecurityUtils.getUsername());
                    moldMaintenance1.setStatus(moldMaintenance.getStatus());
                    if (ledger.getNestingMoldId()!=null){
                        MoldNesting moldNesting = this.moldNestingMapper.queryById(ledger.getNestingMoldId());
                        moldMaintenance1.setMoldName(moldNesting.getNestingName());
                    }
                    moldMaintenance1.setMoldArea(ledger.getMoldArea());
                    moldMaintenance1.setMoldCode(ledger.getMoldCode());
                    moldMaintenance1.setMoldSpec(ledger.getMoldSpec());
                    moldMaintenance1.setMoldMaterial(ledger.getMoldMaterial());
                    moldMaintenance1.setMoldType(ledger.getMoldType());
                    moldMaintenance1.setMoldAperture(ledger.getMoldAperture());
                    this.moldMaintenanceMapper.insert(moldMaintenance1);
                }
            }
        }else{
            throw new BussinessException("新增失败！模具台账为空");
        }
        return moldMaintenance;
    }

    /**
     * 入库操作
     * @param moldMaintenance 实例对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean warehousing(MoldMaintenance moldMaintenance) {
        if (moldMaintenance.getMoldLedger() != null) {

            MoldLifeCycle moldLifeCycle = new MoldLifeCycle();
            //新增一张入库单
            MoldStorage storage=new MoldStorage();
            storage.setCreateTime(new Date());
            storage.setMoldStorageTime(new Date());
            storage.setCreateBy(SecurityUtils.getUsername());
            storage.setRecipient(SecurityUtils.getUsername());
            storage.setMoldId(moldMaintenance.getMoldLedger().getMoldId());
            if (moldMaintenance.getMoldLedger().getNestingMoldId()!=null){
                MoldNesting moldNesting = this.moldNestingMapper.queryById(moldMaintenance.getMoldLedger().getNestingMoldId());
                storage.setMoldName(moldNesting.getNestingName());
                moldLifeCycle.setMoldName(moldNesting.getNestingName());
            }
            storage.setMoldArea(moldMaintenance.getMoldLedger().getMoldArea());
            storage.setMoldCode(moldMaintenance.getMoldLedger().getMoldCode());
            storage.setMoldSpec(moldMaintenance.getMoldLedger().getMoldSpec());
            storage.setMoldMaterial(moldMaintenance.getMoldLedger().getMoldMaterial());
            storage.setMoldType(moldMaintenance.getMoldLedger().getMoldType());
            storage.setMoldAperture(moldMaintenance.getMoldLedger().getMoldAperture());
            this.moldStorageMapper.insert(storage);

            //修改台账模具状态信息
            MoldLedger moldLedger = moldMaintenance.getMoldLedger();
            MoldLedger Ledger=new MoldLedger();
            Ledger.setMoldId(moldLedger.getMoldId());
            Ledger.setStatus("CHECKED_IN/OPERATIONAL");
            this.moldLedgerService.updateStatus(Ledger);

            //新增生命周期列表数据
            moldLifeCycle.setMoldId(moldLedger.getMoldId());
            moldLifeCycle.setCreateTime(new Date());
            moldLifeCycle.setCreateBy(SecurityUtils.getUsername());
            moldLifeCycle.setStatus("CHECKED_IN/OPERATIONAL");
            moldLifeCycle.setMoldArea(moldLedger.getMoldArea());
            moldLifeCycle.setMoldCode(moldLedger.getMoldCode());
            moldLifeCycle.setMoldSpec(moldLedger.getMoldSpec());
            moldLifeCycle.setMoldMaterial(moldLedger.getMoldMaterial());
            moldLifeCycle.setMoldType(moldLedger.getMoldType());
            moldLifeCycle.setMoldAperture(moldLedger.getMoldAperture());

            this.moldLifeCycleMapper.insert(moldLifeCycle);
            //删除维修保养记录
            this.moldMaintenanceMapper.deleteById(moldMaintenance.getId());
        }else{
            throw new BussinessException("数据有误");
        }
        return true;
    }

    /**
     * 修改数据
     *
     * @param moldMaintenance 实例对象
     * @return 实例对象
     */
    @Override
    public MoldMaintenance update(MoldMaintenance moldMaintenance) {
        moldMaintenance.setUpdateTime(new Date());
        moldMaintenance.setUpdateBy(SecurityUtils.getUsername());
        this.moldMaintenanceMapper.update(moldMaintenance);
        return this.queryById(moldMaintenance.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.moldMaintenanceMapper.deleteById(id) > 0;
    }
}
