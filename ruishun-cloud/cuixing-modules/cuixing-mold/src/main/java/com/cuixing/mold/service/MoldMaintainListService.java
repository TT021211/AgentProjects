package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldMaintainList;
import java.util.List;


/**
 * 待保养列表(MoldMaintainList)表服务接口
 *
 * @author makejava
 * @since 2024-06-26 11:54:04
 */
public interface MoldMaintainListService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MoldMaintainList queryById(Long id);

    /**
     * 分页查询
     *
     * @param moldMaintainList 筛选条件
     * @return 查询结果
     */
    List<MoldMaintainList> selectMoldMaintainListList(MoldMaintainList moldMaintainList);

    /**
     * 新增数据
     *
     * @param moldMaintainList 实例对象
     * @return 实例对象
     */
    MoldMaintainList insert(MoldMaintainList moldMaintainList);

    /**
     * 修改数据
     *
     * @param moldMaintainList 实例对象
     * @return 实例对象
     */
    MoldMaintainList update(MoldMaintainList moldMaintainList);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);
    /**
     * 更新状态
     *
     * @param moldMaintainList 对象
     * @return 是否成功
     */
    MoldMaintainList updateStatus(MoldMaintainList moldMaintainList);

}
