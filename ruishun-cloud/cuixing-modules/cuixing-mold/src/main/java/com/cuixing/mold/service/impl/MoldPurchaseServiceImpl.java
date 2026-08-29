package com.cuixing.mold.service.impl;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldLedger;
import com.cuixing.mold.entity.MoldPurchase;
import com.cuixing.mold.entity.MoldStorage;
import com.cuixing.mold.mapper.MoldPurchaseMapper;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.mold.service.MoldPurchaseService;
import com.cuixing.mold.service.MoldStorageService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 模具采购(MoldPurchase)表服务实现类
 *
 * @author makejava
 * @since 2024-07-23 14:32:41
 */
@Service("moldPurchaseService")
public class MoldPurchaseServiceImpl implements MoldPurchaseService {
    @Resource
    private MoldPurchaseMapper moldPurchaseMapper;
    @Resource
    private MoldLedgerService moldLedgerService;
    @Resource
    private MoldStorageService moldStorageService;

    @Override
    public AjaxResult initiateStorage(MoldPurchase moldPurchase) {
        // 这里可以添加入库的逻辑，例如更新数据库中的状态
        moldPurchase.setInboundStatus("In_Stock");
        moldPurchaseMapper.update(moldPurchase);
        // 新增台账
        MoldLedger moldLedger = getMoldLedger(moldPurchase);
        this.moldLedgerService.insert(moldLedger);
        return AjaxResult.success("入库已完成");
    }

    private static MoldLedger getMoldLedger(MoldPurchase moldPurchase) {
        MoldLedger moldLedger=new MoldLedger();
        moldLedger.setMoldCode(moldPurchase.getMoldCode());
        moldLedger.setMoldSupplierName(moldPurchase.getPurchaseFactory());
        moldLedger.setMoldAperture(moldPurchase.getPurchaseSpec());
        moldLedger.setMoldMaterial(moldPurchase.getPurchaseMaterial());
        moldLedger.setMoldType(moldPurchase.getPurchaseType());
        moldLedger.setMoldNum(1L);
        moldLedger.setEngineeringTolerance(moldPurchase.getPurchaseTolerance());
        return moldLedger;
    }

    @Override
    public AjaxResult initiateTemporaryApproval(MoldPurchase moldPurchase) {
        // 这里可以添加发起临时审批的逻辑，例如调用审批系统的API
        // 假设审批成功，返回成功结果
        // 审批成功
        moldPurchase.setStatus("Approved");
        return AjaxResult.success("临时审批已完成");
    }

    /**
     * 发起审批
     * @param moldPurchase
     * @return
     */
    @Override
    public AjaxResult initiateApproval(MoldPurchase moldPurchase) {
        // 这里可以添加发起审批的逻辑，例如调用审批系统的API
        // 假设审批成功，返回成功结果
        // 修改当前状态为审批中
        moldPurchase.setStatus("Under_Review");
        moldPurchase.setInboundStatus("Not_Storage");
        this.moldPurchaseMapper.update(moldPurchase);
        return AjaxResult.success("审批已发起");
    }

    /**
     * 批量插入
     * @param moldPurchases
     */
    @Override
    public int insertBatch(List<MoldPurchase> moldPurchases) {
        return this.moldPurchaseMapper.insertBatch(moldPurchases);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MoldPurchase queryById(Long id) {
        return this.moldPurchaseMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param moldPurchase 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldPurchase> selectMoldPurchaseList(MoldPurchase moldPurchase) {
        return this.moldPurchaseMapper.selectMoldPurchaseList(moldPurchase);
    }

    /**
     * 新增数据
     *
     * @param moldPurchase 实例对象
     * @return 实例对象
     */
    @Override
    public MoldPurchase insert(MoldPurchase moldPurchase) {
        moldPurchase.setCreateTime(new Date());
        moldPurchase.setCreateBy(SecurityUtils.getUsername());
        moldPurchase.setInboundStatus("Not_In_Stock");
        this.moldPurchaseMapper.insert(moldPurchase);
        return moldPurchase;
    }

    /**
     * 修改数据
     *
     * @param moldPurchase 实例对象
     * @return 实例对象
     */
    @Override
    public MoldPurchase update(MoldPurchase moldPurchase) {
        moldPurchase.setUpdateTime(new Date());
        moldPurchase.setUpdateBy(SecurityUtils.getUsername());
        this.moldPurchaseMapper.update(moldPurchase);
        return this.queryById(moldPurchase.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.moldPurchaseMapper.deleteById(id) > 0;
    }

    @Override
    public String checkUniquePurchaseCode(MoldPurchase moldPurchase) {
        MoldPurchase moldPurchase1 = this.moldPurchaseMapper.checkUniquePurchaseCode(moldPurchase);
        Long id = moldPurchase1.getId() == null ? -1L : moldPurchase.getId();

        // 如果查询结果不为空且itemId不同，返回"1"表示不唯一，否则返回"0"表示唯一
        return (moldPurchase1 != null && !moldPurchase1.getId().equals(id)) ? "1" : "0";
    }
}
