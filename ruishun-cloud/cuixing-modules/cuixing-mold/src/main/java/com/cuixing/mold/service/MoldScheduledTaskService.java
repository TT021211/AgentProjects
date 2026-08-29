package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldScheduledTask;
import java.util.List;


/**
 * (MoldScheduledTask)表服务接口
 *
 * @author makejava
 * @since 2024-07-10 15:38:29
 */
public interface MoldScheduledTaskService {

    /**
     * 通过ID查询单条数据
     *
     * @param taskId 主键
     * @return 实例对象
     */
    MoldScheduledTask queryById(String taskId);

    /**
     * 分页查询
     *
     * @param moldScheduledTask 筛选条件
     * @return 查询结果
     */
    List<MoldScheduledTask> selectMoldScheduledTaskList(MoldScheduledTask moldScheduledTask);

    /**
     * 新增数据
     *
     * @param moldScheduledTask 实例对象
     * @return 实例对象
     */
    MoldScheduledTask insert(MoldScheduledTask moldScheduledTask);

    /**
     * 修改数据
     *
     * @param moldScheduledTask 实例对象
     * @return 实例对象
     */
    MoldScheduledTask update(MoldScheduledTask moldScheduledTask);

    /**
     * 通过主键删除数据
     *
     * @param taskId 主键
     * @return 是否成功
     */
    boolean deleteById(String taskId);

}
