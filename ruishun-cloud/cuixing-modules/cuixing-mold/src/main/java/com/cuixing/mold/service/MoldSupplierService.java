package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldSupplier;

import java.util.List;


/**
 * 模具供应商表(MoldSupplier)表服务接口
 *
 * @author makejava
 * @since 2024-06-25 14:06:52
 */
public interface MoldSupplierService {

    /**
     * 通过ID查询单条数据
     *
     * @param moldSupplierId 主键
     * @return 实例对象
     */
    MoldSupplier queryById(Long moldSupplierId);

    /**
     * 分页查询
     *
     * @param moldSupplier 筛选条件
     * @return 查询结果
     */
    List<MoldSupplier> selectMoldSupplierList(MoldSupplier moldSupplier);

    /**
     * 新增数据
     *
     * @param moldSupplier 实例对象
     * @return 实例对象
     */
    MoldSupplier insert(MoldSupplier moldSupplier);

    /**
     * 修改数据
     *
     * @param moldSupplier 实例对象
     * @return 实例对象
     */
    MoldSupplier update(MoldSupplier moldSupplier);

    /**
     * 通过主键删除数据
     *
     * @param moldSupplierId 主键
     * @return 是否成功
     */
    boolean deleteById(Long moldSupplierId);

}
