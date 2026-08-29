package com.cuixing.mold.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldLedger;
import com.cuixing.mold.entity.MoldLifeCycle;
import com.cuixing.mold.mapper.MoldLifeCycleMapper;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.mold.service.MoldLifeCycleService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 模具生命周期表(MoldLifeCycle)表服务实现类
 *
 * @author makejava
 * @since 2024-06-27 10:46:37
 */
@Service("moldLifeCycleService")
public class MoldLifeCycleServiceImpl implements MoldLifeCycleService {
    @Resource
    private MoldLifeCycleMapper moldLifeCycleMapper;
    @Resource
    private MoldLedgerService moldLedgerService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MoldLifeCycle queryById(Long id) {
        return this.moldLifeCycleMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param moldLifeCycle 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldLifeCycle> selectMoldLifeCycleList(MoldLifeCycle moldLifeCycle) {
        return this.moldLifeCycleMapper.selectMoldLifeCycleList(moldLifeCycle);
    }

    /**
     * 新增数据
     *
     * @param moldLifeCycle 实例对象
     * @return 实例对象
     */
    @Override
    public MoldLifeCycle insert(MoldLifeCycle moldLifeCycle) {
        moldLifeCycle.setCreateTime(new Date());
        moldLifeCycle.setCreateBy(SecurityUtils.getUsername());
        this.moldLifeCycleMapper.insert(moldLifeCycle);
        return moldLifeCycle;
    }

    /**
     * 修改数据
     *
     * @param moldLifeCycle 实例对象
     * @return 实例对象
     */
    @Override
    public MoldLifeCycle update(MoldLifeCycle moldLifeCycle) {
        moldLifeCycle.setUpdateTime(new Date());
        moldLifeCycle.setUpdateBy(SecurityUtils.getUsername());
        this.moldLifeCycleMapper.update(moldLifeCycle);
        return this.queryById(moldLifeCycle.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.moldLifeCycleMapper.deleteById(id) > 0;
    }
}
