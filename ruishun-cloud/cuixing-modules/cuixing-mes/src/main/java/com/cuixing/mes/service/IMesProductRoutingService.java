package com.cuixing.mes.service;

import com.cuixing.system.api.domain.mes.MesProductRouting;
import java.math.BigInteger;
import java.util.List;

/**
 * 产品工艺路线关联表;(mes_product_routing)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
public interface IMesProductRoutingService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProductRouting queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mesProductRouting 筛选条件
     * @return 查询结果
     */
    List<MesProductRouting> selectMesProductRoutingList(MesProductRouting mesProductRouting);
    /**
     * 新增数据
     *
     * @param mesProductRouting 实例对象
     * @return 影响行数
     */
    BigInteger insert(MesProductRouting mesProductRouting);

    /**
     * 批量新增数据
     *
     * @param mesProductRoutings 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MesProductRouting> mesProductRoutings);
    /**
     * 更新数据
     *
     * @param mesProductRouting 实例对象
     * @return 影响行数
     */
    int update(MesProductRouting mesProductRouting);
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