package com.cuixing.purchase.service;

import com.cuixing.purchase.entity.ProcureSource;
import java.util.List;


/**
 * 采购货源管理(ProcureSource)表服务接口
 *
 * @author 阿江
 * @since 2025-03-04 15:08:21
 */
public interface ProcureSourceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureSource queryById(Long id);

    /**
     * 分页查询
     *
     * @param procureSource 筛选条件
     * @return 查询结果
     */
    List<ProcureSource> selectProcureSourceList(ProcureSource procureSource);

    /**
     * 新增数据
     *
     * @param procureSource 实例对象
     * @return 实例对象
     */
    ProcureSource insert(ProcureSource procureSource);

    /**
     * 修改数据
     *
     * @param procureSource 实例对象
     * @return 实例对象
     */
    ProcureSource update(ProcureSource procureSource);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
