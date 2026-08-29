package com.cuixing.purchase.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.purchase.entity.ProcureReceiving;
import com.cuixing.purchase.mapper.ProcureReceivingMapper;
import com.cuixing.purchase.service.ProcureReceivingService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 采购收料管理(ProcureReceiving)表服务实现类
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Service("procureReceivingService")
public class ProcureReceivingServiceImpl implements ProcureReceivingService {
    @Resource
    private ProcureReceivingMapper procureReceivingMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProcureReceiving queryById(Long id) {
        return this.procureReceivingMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param procureReceiving 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ProcureReceiving> selectProcureReceivingList(ProcureReceiving procureReceiving) {
        return this.procureReceivingMapper.selectProcureReceivingList(procureReceiving);
    }

    /**
     * 新增数据
     *
     * @param procureReceiving 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureReceiving insert(ProcureReceiving procureReceiving) {
        procureReceiving.setCreateTime(new Date());
        procureReceiving.setCreateBy(SecurityUtils.getUsername());
        this.procureReceivingMapper.insert(procureReceiving);
        return procureReceiving;
    }

    /**
     * 修改数据
     *
     * @param procureReceiving 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureReceiving update(ProcureReceiving procureReceiving) {
        procureReceiving.setUpdateTime(new Date());
        procureReceiving.setUpdateBy(SecurityUtils.getUsername());
        this.procureReceivingMapper.update(procureReceiving);
        return this.queryById(procureReceiving.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.procureReceivingMapper.deleteById(id) > 0;
    }
}
