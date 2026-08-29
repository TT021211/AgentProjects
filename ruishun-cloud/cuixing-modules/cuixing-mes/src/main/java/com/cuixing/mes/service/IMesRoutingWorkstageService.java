package com.cuixing.mes.service;

import com.cuixing.mes.entity.MesRoutingWorkstage;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * 工艺路线与工序关联表;(mes_routing_workstage)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
public interface IMesRoutingWorkstageService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesRoutingWorkstage queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mesRoutingWorkstage 筛选条件
     * @return 查询结果
     */
    List<MesRoutingWorkstage> selectMesRoutingWorkstageList(MesRoutingWorkstage mesRoutingWorkstage);
    /**
     * 新增数据
     *
     * @param mesRoutingWorkstage 实例对象
     * @return 影响行数
     */
    BigInteger insert(MesRoutingWorkstage mesRoutingWorkstage);

    /**
     * 批量新增数据
     *
     * @param mesRoutingWorkstages 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MesRoutingWorkstage> mesRoutingWorkstages);
    /**
     * 更新数据
     *
     * @param mesRoutingWorkstage 实例对象
     * @return 影响行数
     */
    int update(MesRoutingWorkstage mesRoutingWorkstage);
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
    /**
     * 根据工艺路线Id查询工序列表
     *
     * @param routingId 工艺路线Id
     * @return 查询结果
     */
    List<MesRoutingWorkstage> selectMesRoutingWorkstageListByRoutingId(BigInteger routingId);
}