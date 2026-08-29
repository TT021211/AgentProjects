package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldOutbound;

import java.util.List;


/**
 * 模具出库表(MoldOutbound)表服务接口
 *
 * @author makejava
 * @since 2024-06-26 16:52:49
 */
public interface MoldOutboundService {

    /**
     * 通过ID查询单条数据
     *
     * @param moldOutboundId 主键
     * @return 实例对象
     */
    MoldOutbound queryById(Long moldOutboundId);

    /**
     * 分页查询
     *
     * @param moldOutbound 筛选条件
     * @return 查询结果
     */
    List<MoldOutbound> selectMoldOutboundList(MoldOutbound moldOutbound);

    /**
     * 新增数据
     *
     * @param moldOutbound 实例对象
     * @return 实例对象
     */
    MoldOutbound insert(MoldOutbound moldOutbound);
    /**
     * 确认出库
     *
     * @param moldOutbound 实例对象
     * @return 实例对象
     */
    boolean confirm(MoldOutbound moldOutbound);

    /**
     * 修改数据
     *
     * @param moldOutbound 实例对象
     * @return 实例对象
     */
    MoldOutbound update(MoldOutbound moldOutbound);

    /**
     * 通过主键删除数据
     *
     * @param moldOutboundId 主键
     * @return 是否成功
     */
    boolean deleteById(Long moldOutboundId);

}
