package com.cuixing.purchase.service.impl;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.purchase.entity.ProcureOrder;
import com.cuixing.purchase.mapper.ProcureOrderMapper;
import com.cuixing.purchase.service.ProcureOrderService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 采购订单(ProcureOrder)表服务实现类
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Service("procureOrderService")
public class ProcureOrderServiceImpl implements ProcureOrderService {
    @Resource
    private ProcureOrderMapper procureOrderMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProcureOrder queryById(Long id) {
        return this.procureOrderMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param procureOrder 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ProcureOrder> selectProcureOrderList(ProcureOrder procureOrder) {
        return this.procureOrderMapper.selectProcureOrderList(procureOrder);
    }

    /**
     * 新增数据
     *
     * @param procureOrder 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureOrder insert(ProcureOrder procureOrder) {
        if(procureOrder.getCreateTime()==null){
            procureOrder.setCreateTime(DateUtils.getNowDate());
        }else{
            procureOrder.setCreateTime(procureOrder.getCreateTime());
        }
        procureOrder.setCreateBy(SecurityUtils.getUsername());
        this.procureOrderMapper.insert(procureOrder);
        return procureOrder;
    }

    /**
     * 修改数据
     *
     * @param procureOrder 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureOrder update(ProcureOrder procureOrder) {
        procureOrder.setUpdateTime(new Date());
        procureOrder.setUpdateBy(SecurityUtils.getUsername());
        this.procureOrderMapper.update(procureOrder);
        return this.queryById(procureOrder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.procureOrderMapper.deleteById(id) > 0;
    }

    @Override
    public int approval(ProcureOrder procureOrder) {
        return procureOrderMapper.approval(procureOrder);
    }
}
