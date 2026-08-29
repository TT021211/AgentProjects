package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldOutbound;
import com.cuixing.mold.entity.MoldStorage;

import java.util.List;


/**
 * 模具入库表(MoldStorage)表服务接口
 *
 * @author makejava
 * @since 2024-06-25 14:06:51
 */
public interface MoldStorageService {

    /**
     * 通过ID查询单条数据
     *
     * @param moldStorageId 主键
     * @return 实例对象
     */
    MoldStorage queryById(Long moldStorageId);

    /**
     * 分页查询
     *
     * @param moldStorage 筛选条件
     * @return 查询结果
     */
    List<MoldStorage> selectMoldStorageList(MoldStorage moldStorage);

    /**
     * 新增数据
     *
     * @param moldStorage 实例对象
     * @return 实例对象
     */
    MoldStorage insert(MoldStorage moldStorage);

    /**
     * 确认入库
     * @param moldStorage
     * @return
     */
    boolean confirm(MoldStorage moldStorage);

    /**
     * 修改数据
     *
     * @param moldStorage 实例对象
     * @return 实例对象
     */
    MoldStorage update(MoldStorage moldStorage);

    /**
     * 通过主键删除数据
     *
     * @param moldStorageId 主键
     * @return 是否成功
     */
    boolean deleteById(Long moldStorageId);

}
