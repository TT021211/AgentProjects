package com.cuixing.mold.service.impl;

import com.cuixing.mold.entity.MoldScheduledTask;
import com.cuixing.mold.mapper.MoldScheduledTaskMapper;
import com.cuixing.mold.service.MoldScheduledTaskService;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;

/**
 * (MoldScheduledTask)表服务实现类
 *
 * @author makejava
 * @since 2024-07-10 15:38:29
 */
@Service("moldScheduledTaskService")
public class MoldScheduledTaskServiceImpl implements MoldScheduledTaskService {
    @Resource
    private MoldScheduledTaskMapper moldScheduledTaskMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param taskId 主键
     * @return 实例对象
     */
    @Override
    public MoldScheduledTask queryById(String taskId) {
        return this.moldScheduledTaskMapper.queryById(taskId);
    }

    /**
     * 分页查询
     *
     * @param moldScheduledTask 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldScheduledTask> selectMoldScheduledTaskList(MoldScheduledTask moldScheduledTask) {
        return this.moldScheduledTaskMapper.selectMoldScheduledTaskList(moldScheduledTask);
    }

    /**
     * 新增数据
     *
     * @param moldScheduledTask 实例对象
     * @return 实例对象
     */
    @Override
    public MoldScheduledTask insert(MoldScheduledTask moldScheduledTask) {
        this.moldScheduledTaskMapper.insert(moldScheduledTask);
        return moldScheduledTask;
    }

    /**
     * 修改数据
     *
     * @param moldScheduledTask 实例对象
     * @return 实例对象
     */
    @Override
    public MoldScheduledTask update(MoldScheduledTask moldScheduledTask) {
        this.moldScheduledTaskMapper.update(moldScheduledTask);
        return this.queryById(moldScheduledTask.getTaskId());
    }

    /**
     * 通过主键删除数据
     *
     * @param taskId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String taskId) {
        return this.moldScheduledTaskMapper.deleteById(taskId) > 0;
    }
}
