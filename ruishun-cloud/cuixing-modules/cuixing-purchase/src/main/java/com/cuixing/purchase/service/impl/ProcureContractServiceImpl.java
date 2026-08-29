package com.cuixing.purchase.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.purchase.entity.ProcureContract;
import com.cuixing.purchase.mapper.ProcureContractMapper;
import com.cuixing.purchase.service.ProcureContractService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 采购合同管理(ProcureContract)表服务实现类
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Service("procureContractService")
public class ProcureContractServiceImpl implements ProcureContractService {
    @Resource
    private ProcureContractMapper procureContractMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProcureContract queryById(Long id) {
        return this.procureContractMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param procureContract 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ProcureContract> selectProcureContractList(ProcureContract procureContract) {
        return this.procureContractMapper.selectProcureContractList(procureContract);
    }

    /**
     * 新增数据
     *
     * @param procureContract 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureContract insert(ProcureContract procureContract) {
        procureContract.setCreateTime(new Date());
        procureContract.setCreateBy(SecurityUtils.getUsername());
        this.procureContractMapper.insert(procureContract);
        return procureContract;
    }

    /**
     * 修改数据
     *
     * @param procureContract 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureContract update(ProcureContract procureContract) {
        procureContract.setUpdateTime(new Date());
        procureContract.setUpdateBy(SecurityUtils.getUsername());
        this.procureContractMapper.update(procureContract);
        return this.queryById(procureContract.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.procureContractMapper.deleteById(id) > 0;
    }
}
