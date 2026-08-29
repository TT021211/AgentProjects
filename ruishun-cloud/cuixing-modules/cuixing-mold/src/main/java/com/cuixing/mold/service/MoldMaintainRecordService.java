package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldMaintainRecord;

import java.util.List;


/**
 * 模具保养表--保养记录表(MoldMaintainRecord)表服务接口
 *
 * @author makejava
 * @since 2024-06-25 16:52:05
 */
public interface MoldMaintainRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param maintainRecordId 主键
     * @return 实例对象
     */
    MoldMaintainRecord queryById(Long maintainRecordId);

    /**
     * 分页查询
     *
     * @param moldMaintainRecord 筛选条件
     * @return 查询结果
     */
    List<MoldMaintainRecord> selectMoldMaintainRecordList(MoldMaintainRecord moldMaintainRecord);

    /**
     * 新增数据
     *
     * @param moldMaintainRecord 实例对象
     * @return 实例对象
     */
    MoldMaintainRecord insert(MoldMaintainRecord moldMaintainRecord);

    /**
     * 修改数据
     *
     * @param moldMaintainRecord 实例对象
     * @return 实例对象
     */
    MoldMaintainRecord update(MoldMaintainRecord moldMaintainRecord);

    /**
     * 通过主键删除数据
     *
     * @param maintainRecordId 主键
     * @return 是否成功
     */
    boolean deleteById(Long maintainRecordId);

}
