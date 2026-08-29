package com.cuixing.mes.service;

import com.cuixing.mes.entity.MesProcessRouting;
import java.math.BigInteger;
import java.util.List;

/**
 * 工艺路线表;(mes_process_routing)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
public interface IMesProcessRoutingService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProcessRouting queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mesProcessRouting 筛选条件
     * @return 查询结果
     */
    List<MesProcessRouting> selectMesProcessRoutingList(MesProcessRouting mesProcessRouting);
    /**
     * 新增数据
     *
     * @param mesProcessRouting 实例对象
     * @return 影响行数
     */
    BigInteger insert(MesProcessRouting mesProcessRouting);

    /**
     * 批量新增数据
     *
     * @param mesProcessRoutings 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MesProcessRouting> mesProcessRoutings);
    /**
     * 更新数据
     *
     * @param mesProcessRouting 实例对象
     * @return 影响行数
     */
    int update(MesProcessRouting mesProcessRouting);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigInteger id);
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    boolean deleteByIds(BigInteger[] ids);
}