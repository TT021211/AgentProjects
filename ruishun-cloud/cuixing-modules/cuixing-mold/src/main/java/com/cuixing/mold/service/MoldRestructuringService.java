package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldRestructuring;

import java.util.List;


/**
 * 模具改制表(MoldRestructuring)表服务接口
 *
 * @author makejava
 * @since 2024-06-25 14:06:50
 */
public interface MoldRestructuringService {

    /**
     * 通过ID查询单条数据
     *
     * @param moldRestructuringId 主键
     * @return 实例对象
     */
    MoldRestructuring queryById(Long moldRestructuringId);

    /**
     * 分页查询
     *
     * @param moldRestructuring 筛选条件
     * @return 查询结果
     */
    List<MoldRestructuring> selectMoldRestructuringList(MoldRestructuring moldRestructuring);

    /**
     * 却
     *
     * @param moldRestructuring 实例对象
     * @return 实例对象
     */
    MoldRestructuring confirm(MoldRestructuring moldRestructuring);
    /**
     * 新增数据
     *
     * @param moldRestructuring 实例对象
     * @return 实例对象
     */
    MoldRestructuring insert(MoldRestructuring moldRestructuring);

    /**
     * 修改数据
     *
     * @param moldRestructuring 实例对象
     * @return 实例对象
     */
    MoldRestructuring update(MoldRestructuring moldRestructuring);

    /**
     * 通过主键删除数据
     *
     * @param moldRestructuringId 主键
     * @return 是否成功
     */
    boolean deleteById(Long moldRestructuringId);

}
