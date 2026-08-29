package com.cuixing.mold.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldSupplier;
import com.cuixing.mold.mapper.MoldSupplierMapper;
import com.cuixing.mold.service.MoldSupplierService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 模具供应商表(MoldSupplier)表服务实现类
 *
 * @author makejava
 * @since 2024-06-25 14:06:52
 */
@Service("moldSupplierService")
public class MoldSupplierServiceImpl implements MoldSupplierService {
    @Resource
    private MoldSupplierMapper moldSupplierMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param moldSupplierId 主键
     * @return 实例对象
     */
    @Override
    public MoldSupplier queryById(Long moldSupplierId) {
        return this.moldSupplierMapper.queryById(moldSupplierId);
    }

    /**
     * 分页查询
     *
     * @param moldSupplier 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldSupplier> selectMoldSupplierList(MoldSupplier moldSupplier) {
        return this.moldSupplierMapper.selectMoldSupplierList(moldSupplier);
    }

    /**
     * 新增数据
     *
     * @param moldSupplier 实例对象
     * @return 实例对象
     */
    @Override
    public MoldSupplier insert(MoldSupplier moldSupplier) {
        moldSupplier.setCreateTime(new Date());
        moldSupplier.setCreateBy(SecurityUtils.getUsername());
        this.moldSupplierMapper.insert(moldSupplier);
        return moldSupplier;
    }

    /**
     * 修改数据
     *
     * @param moldSupplier 实例对象
     * @return 实例对象
     */
    @Override
    public MoldSupplier update(MoldSupplier moldSupplier) {
        moldSupplier.setUpdateTime(new Date());
        moldSupplier.setUpdateBy(SecurityUtils.getUsername());
        this.moldSupplierMapper.update(moldSupplier);
        return this.queryById(moldSupplier.getMoldSupplierId());
    }

    /**
     * 通过主键删除数据
     *
     * @param moldSupplierId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long moldSupplierId) {
        return this.moldSupplierMapper.deleteById(moldSupplierId) > 0;
    }
}
