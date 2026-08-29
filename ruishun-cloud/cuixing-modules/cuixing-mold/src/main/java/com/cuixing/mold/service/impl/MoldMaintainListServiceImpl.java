package com.cuixing.mold.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.*;
import com.cuixing.mold.mapper.*;
import com.cuixing.mold.service.MoldMaintainListService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 待保养列表(MoldMaintainList)表服务实现类
 *
 * @author makejava
 * @since 2024-06-26 11:54:04
 */
@Service("moldMaintainListService")
public class MoldMaintainListServiceImpl implements MoldMaintainListService {
    @Resource
    private MoldMaintainListMapper moldMaintainListMapper;
    @Resource
    private MoldNestingMapper moldNestingMapper;
    @Resource
    private MoldLedgerMapper moldLedgerMapper;
    @Resource
    private MoldMaintainPlanMapper moldMaintainPlanMapper;
    @Resource
    private MoldMaintainRecordMapper moldMaintainRecordMapper;
    @Resource
    private MoldSupplierMapper moldSupplierMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MoldMaintainList queryById(Long id) {
        return this.moldMaintainListMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param moldMaintainList 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldMaintainList> selectMoldMaintainListList(MoldMaintainList moldMaintainList) {
        List<MoldMaintainList> moldMaintainLists = this.moldMaintainListMapper.selectMoldMaintainListList(moldMaintainList);
        if (!moldMaintainLists.isEmpty()) {
            for (MoldMaintainList oldMaintainList : moldMaintainLists){
                if (oldMaintainList.getNestingId()!=null){
                    MoldNesting nesting = this.moldNestingMapper.queryById(oldMaintainList.getNestingId());
                    oldMaintainList.setMoldNesting(nesting);
                    MoldMaintainPlan maintainPlan = this.moldMaintainPlanMapper.queryById(oldMaintainList.getPlanId());
                    oldMaintainList.setMoldMaintainPlan(maintainPlan);
                }
            }
        }
        return moldMaintainLists;
    }

    /**
     * 新增数据
     *
     * @param moldMaintainList 实例对象
     * @return 实例对象
     */
    @Override
    public MoldMaintainList insert(MoldMaintainList moldMaintainList) {
        moldMaintainList.setCreateTime(new Date());
        moldMaintainList.setCreateBy(SecurityUtils.getUsername());
        this.moldMaintainListMapper.insert(moldMaintainList);
        return moldMaintainList;
    }

    /**
     * 修改数据
     *
     * @param moldMaintainList 实例对象
     * @return 实例对象
     */
    @Override
    public MoldMaintainList update(MoldMaintainList moldMaintainList) {
        moldMaintainList.setUpdateTime(new Date());
        moldMaintainList.setUpdateBy(SecurityUtils.getUsername());
        this.moldMaintainListMapper.update(moldMaintainList);
        return this.queryById(moldMaintainList.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.moldMaintainListMapper.deleteById(id) > 0;
    }

    @Override
    public MoldMaintainList updateStatus(MoldMaintainList moldMaintainList) {
        //查询计划
        MoldMaintainPlan moldMaintainPlan = this.moldMaintainPlanMapper.queryById(moldMaintainList.getPlanId());
        //更新套模保养状态
        MoldNesting moldNesting=new MoldNesting();
        moldNesting.setNestingMoldId(moldMaintainList.getNestingId());
        moldNesting.setStatus(moldMaintainList.getStatus());
        //更新套模下的模具状态
        List<MoldLedger> moldLedgers = this.moldLedgerMapper.queryByNestingMoldId(moldMaintainList.getNestingId());
        for (MoldLedger ledger:moldLedgers){
            ledger.setStatus("OPERATIONAL");
            this.moldLedgerMapper.update(ledger);
        }
        this.moldNestingMapper.update(moldNesting);
        MoldNesting moldNesting1 = this.moldNestingMapper.queryById(moldMaintainList.getNestingId());
        //新增供应商
        MoldSupplier moldSupplier = this.moldSupplierMapper.queryById(moldMaintainList.getClientId());
        //新增保养记录
        MoldMaintainRecord record = getMoldMaintainRecord(moldMaintainList, moldMaintainPlan,moldNesting1,moldSupplier);
        this.moldMaintainRecordMapper.insert(record);
        //删除待保养信息
        this.moldMaintainListMapper.deleteById(moldMaintainList.getId());

        return null;
    }

    private static MoldMaintainRecord getMoldMaintainRecord(MoldMaintainList moldMaintainList, MoldMaintainPlan moldMaintainPlan,MoldNesting nesting,MoldSupplier moldSupplier) {
        MoldMaintainRecord record=new MoldMaintainRecord();
        record.setMaintainPlanId(moldMaintainList.getPlanId());
        record.setNestingMoldId(moldMaintainList.getNestingId());
        record.setNestingName(nesting.getNestingName());
        record.setNestingScope(nesting.getNestingScope());
        record.setNestingArea(nesting.getNestingArea());
        record.setStatus(moldMaintainList.getStatus());
        record.setCreateBy(SecurityUtils.getUsername());
        record.setMaintainPlanName(moldMaintainPlan.getMaintainPlanName());
        record.setMaintainPlanType(moldMaintainPlan.getMaintainPlanType());
        record.setCreateTime(new Date());
        record.setMaintainItemId(moldMaintainList.getItemsId());
        record.setClientId(moldSupplier.getMoldSupplierId());
        record.setClientName(moldSupplier.getSupplierName());
        return record;
    }
}
