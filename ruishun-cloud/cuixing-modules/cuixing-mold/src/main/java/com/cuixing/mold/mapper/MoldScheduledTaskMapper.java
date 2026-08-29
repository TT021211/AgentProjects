package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldScheduledTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (MoldScheduledTask)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-10 15:38:29
 */
public interface MoldScheduledTaskMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param taskId 主键
     * @return 实例对象
     */
    MoldScheduledTask queryById(String taskId);

    /**
     * 查询指定行数据
     *
     * @param moldScheduledTask 查询条件
     * @return 对象列表
     */
    List<MoldScheduledTask> selectMoldScheduledTaskList(MoldScheduledTask moldScheduledTask);

    /**
     * 统计总行数
     *
     * @param moldScheduledTask 查询条件
     * @return 总行数
     */
    long count(MoldScheduledTask moldScheduledTask);

    /**
     * 新增数据
     *
     * @param moldScheduledTask 实例对象
     * @return 影响行数
     */
    int insert(MoldScheduledTask moldScheduledTask);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldScheduledTask> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldScheduledTask> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldScheduledTask> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldScheduledTask> entities);

    /**
     * 修改数据
     *
     * @param moldScheduledTask 实例对象
     * @return 影响行数
     */
    int update(MoldScheduledTask moldScheduledTask);

    /**
     * 通过主键删除数据
     *
     * @param taskId 主键
     * @return 影响行数
     */
    int deleteById(String taskId);

}

