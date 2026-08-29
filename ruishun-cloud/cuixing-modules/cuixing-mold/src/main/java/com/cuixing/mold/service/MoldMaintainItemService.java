package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldMaintainItem;

import java.util.List;


/**
 * 模具保养表--保养设置表(MoldMaintainItem)表服务接口
 *
 * @author makejava
 * @since 2024-06-25 16:52:04
 */
public interface MoldMaintainItemService {

    /**
     * 通过ID查询单条数据
     *
     * @param maintainItemId 主键
     * @return 实例对象
     */
    MoldMaintainItem queryById(Long maintainItemId);

    /**
     * 分页查询
     *
     * @param moldMaintainItem 筛选条件
     * @return 查询结果
     */
    List<MoldMaintainItem> selectMoldMaintainItemList(MoldMaintainItem moldMaintainItem);

    /**
     * 新增数据
     *
     * @param moldMaintainItem 实例对象
     * @return 实例对象
     */
    MoldMaintainItem insert(MoldMaintainItem moldMaintainItem);

    /**
     * 修改数据
     *
     * @param moldMaintainItem 实例对象
     * @return 实例对象
     */
    MoldMaintainItem update(MoldMaintainItem moldMaintainItem);

    /**
     * 通过主键删除数据
     *
     * @param maintainItemId 主键
     * @return 是否成功
     */
    boolean deleteById(Long maintainItemId);

    List<MoldMaintainItem> selectMoldMaintainItemList2(MoldMaintainItem moldMaintainItem);
}
