package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldGauge;

import java.util.List;


/**
 * 模具换规表(MoldGauge)表服务接口
 *
 * @author makejava
 * @since 2024-06-25 14:06:45
 */
public interface MoldGaugeService {

    /**
     * 通过ID查询单条数据
     *
     * @param moldGaugeId 主键
     * @return 实例对象
     */
    MoldGauge queryById(Long moldGaugeId);

    /**
     * 分页查询
     *
     * @param moldGauge 筛选条件
     * @return 查询结果
     */
    List<MoldGauge> selectMoldGaugeList(MoldGauge moldGauge);

    /**
     * 新增数据
     *
     * @param moldGauge 实例对象
     * @return 实例对象
     */
    MoldGauge insert(MoldGauge moldGauge);

    /**
     * 批量新增模具规格
     * @param moldGauges 模具规格列表
     * @return 操作结果
     */
    boolean addMoldGaugeBatch(List<MoldGauge> moldGauges);

    /**
     * 修改数据
     *
     * @param moldGauge 实例对象
     * @return 实例对象
     */
    MoldGauge update(MoldGauge moldGauge);

    /**
     * 通过主键删除数据
     *
     * @param moldGaugeId 主键
     * @return 是否成功
     */
    boolean deleteById(Long moldGaugeId);

}
