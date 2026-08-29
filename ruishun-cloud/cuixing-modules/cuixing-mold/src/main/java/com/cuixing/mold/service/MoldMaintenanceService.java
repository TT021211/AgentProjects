package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldMaintenance;

import java.util.List;


/**
 * 模具维修保养表(MoldMaintenance)表服务接口
 *
 * @author makejava
 * @since 2024-07-01 11:01:19
 */
public interface MoldMaintenanceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MoldMaintenance queryById(Long id);

    /**
     * 分页查询
     *
     * @param moldMaintenance 筛选条件
     * @return 查询结果
     */
    List<MoldMaintenance> selectMoldMaintenanceList(MoldMaintenance moldMaintenance);

    /**
     * 新增数据
     *
     * @param moldMaintenance 实例对象
     * @return 实例对象
     */
    MoldMaintenance insert(MoldMaintenance moldMaintenance);
    /**
     * 入库
     *
     * @param moldMaintenance 实例对象
     * @return 实例对象
     */
    boolean warehousing(MoldMaintenance moldMaintenance);

    /**
     * 修改数据
     *
     * @param moldMaintenance 实例对象
     * @return 实例对象
     */
    MoldMaintenance update(MoldMaintenance moldMaintenance);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
