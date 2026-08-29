package com.cuixing.purchase.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.purchase.entity.ProcureSource;
import com.cuixing.purchase.mapper.ProcureSourceMapper;
import com.cuixing.purchase.service.ProcureSourceService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 采购货源管理(ProcureSource)表服务实现类
 *
 * @author 阿江
 * @since 2025-03-04 15:08:21
 */
@Service("procureSourceService")
public class ProcureSourceServiceImpl implements ProcureSourceService {
    @Resource
    private ProcureSourceMapper procureSourceMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProcureSource queryById(Long id) {
        return this.procureSourceMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param procureSource 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ProcureSource> selectProcureSourceList(ProcureSource procureSource) {
        return this.procureSourceMapper.selectProcureSourceList(procureSource);
    }

    /**
     * 新增数据
     *
     * @param procureSource 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureSource insert(ProcureSource procureSource) {
        procureSource.setCreateTime(new Date());
        procureSource.setCreateBy(SecurityUtils.getUsername());
        this.procureSourceMapper.insert(procureSource);
        return procureSource;
    }

    /**
     * 修改数据
     *
     * @param procureSource 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureSource update(ProcureSource procureSource) {
        procureSource.setUpdateTime(new Date());
        procureSource.setUpdateBy(SecurityUtils.getUsername());
        this.procureSourceMapper.update(procureSource);
        return this.queryById(procureSource.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.procureSourceMapper.deleteById(id) > 0;
    }
}
