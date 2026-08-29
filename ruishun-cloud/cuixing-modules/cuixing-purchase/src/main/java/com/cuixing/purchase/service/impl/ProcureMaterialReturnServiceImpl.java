package com.cuixing.purchase.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.purchase.entity.ProcureMaterialReturn;
import com.cuixing.purchase.mapper.ProcureMaterialReturnMapper;
import com.cuixing.purchase.service.ProcureMaterialReturnService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 采购退货管理(ProcureMaterialReturn)表服务实现类
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
@Service("procureMaterialReturnService")
public class ProcureMaterialReturnServiceImpl implements ProcureMaterialReturnService {
    @Resource
    private ProcureMaterialReturnMapper procureMaterialReturnMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProcureMaterialReturn queryById(Long id) {
        return this.procureMaterialReturnMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param procureMaterialReturn 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ProcureMaterialReturn> selectProcureMaterialReturnList(ProcureMaterialReturn procureMaterialReturn) {
        return this.procureMaterialReturnMapper.selectProcureMaterialReturnList(procureMaterialReturn);
    }

    /**
     * 新增数据
     *
     * @param procureMaterialReturn 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureMaterialReturn insert(ProcureMaterialReturn procureMaterialReturn) {
        procureMaterialReturn.setCreateTime(new Date());
        procureMaterialReturn.setCreateBy(SecurityUtils.getUsername());
        this.procureMaterialReturnMapper.insert(procureMaterialReturn);
        return procureMaterialReturn;
    }

    /**
     * 修改数据
     *
     * @param procureMaterialReturn 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureMaterialReturn update(ProcureMaterialReturn procureMaterialReturn) {
        procureMaterialReturn.setUpdateTime(new Date());
        procureMaterialReturn.setUpdateBy(SecurityUtils.getUsername());
        this.procureMaterialReturnMapper.update(procureMaterialReturn);
        return this.queryById(procureMaterialReturn.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.procureMaterialReturnMapper.deleteById(id) > 0;
    }
}
