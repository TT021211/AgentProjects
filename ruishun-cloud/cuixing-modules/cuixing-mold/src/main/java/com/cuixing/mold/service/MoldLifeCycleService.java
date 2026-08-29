package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldLifeCycle;

import java.util.List;


/**
 * 模具生命周期表(MoldLifeCycle)表服务接口
 *
 * @author makejava
 * @since 2024-06-27 10:46:37
 */
public interface MoldLifeCycleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MoldLifeCycle queryById(Long id);

    /**
     * 分页查询
     *
     * @param moldLifeCycle 筛选条件
     * @return 查询结果
     */
    List<MoldLifeCycle> selectMoldLifeCycleList(MoldLifeCycle moldLifeCycle);

    /**
     * 新增数据
     *
     * @param moldLifeCycle 实例对象
     * @return 实例对象
     */
    MoldLifeCycle insert(MoldLifeCycle moldLifeCycle);

    /**
     * 修改数据
     *
     * @param moldLifeCycle 实例对象
     * @return 实例对象
     */
    MoldLifeCycle update(MoldLifeCycle moldLifeCycle);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
