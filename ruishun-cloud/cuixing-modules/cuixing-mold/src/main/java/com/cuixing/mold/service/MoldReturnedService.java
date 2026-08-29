package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldReturned;

import java.util.List;


/**
 * 模具待归还表(MoldReturned)表服务接口
 *
 * @author makejava
 * @since 2024-06-26 17:26:10
 */
public interface MoldReturnedService {

    /**
     * 通过ID查询单条数据
     *
     * @param moldReturnedId 主键
     * @return 实例对象
     */
    MoldReturned queryById(Long moldReturnedId);

    /**
     * 分页查询
     *
     * @param moldReturned 筛选条件
     * @return 查询结果
     */
    List<MoldReturned> selectMoldReturnedList(MoldReturned moldReturned);

    /**
     * 新增数据
     *
     * @param moldReturned 实例对象
     * @return 实例对象
     */
    MoldReturned insert(MoldReturned moldReturned);
    /**
     * 确认归还
     *
     * @param moldReturned 实例对象
     * @return 实例对象
     */
    boolean confirm(MoldReturned moldReturned);

    /**
     * 修改数据
     *
     * @param moldReturned 实例对象
     * @return 实例对象
     */
    MoldReturned update(MoldReturned moldReturned);

    /**
     * 通过主键删除数据
     *
     * @param moldReturnedId 主键
     * @return 是否成功
     */
    boolean deleteById(Long moldReturnedId);

}
