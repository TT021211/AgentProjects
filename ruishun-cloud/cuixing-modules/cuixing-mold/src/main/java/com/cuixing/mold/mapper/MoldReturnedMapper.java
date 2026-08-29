package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldReturned;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模具待归还表(MoldReturned)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-26 17:26:10
 */
public interface MoldReturnedMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param moldReturnedId 主键
     * @return 实例对象
     */
    MoldReturned queryById(Long moldReturnedId);

    /**
     * 查询指定行数据
     *
     * @param moldReturned 查询条件
     * @return 对象列表
     */
    List<MoldReturned> selectMoldReturnedList(MoldReturned moldReturned);

    /**
     * 统计总行数
     *
     * @param moldReturned 查询条件
     * @return 总行数
     */
    long count(MoldReturned moldReturned);

    /**
     * 新增数据
     *
     * @param moldReturned 实例对象
     * @return 影响行数
     */
    int insert(MoldReturned moldReturned);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldReturned> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldReturned> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldReturned> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldReturned> entities);

    /**
     * 修改数据
     *
     * @param moldReturned 实例对象
     * @return 影响行数
     */
    int update(MoldReturned moldReturned);

    /**
     * 通过主键删除数据
     *
     * @param moldReturnedId 主键
     * @return 影响行数
     */
    int deleteById(Long moldReturnedId);

    int updateNum(@Param("moldReturnedId") Long moldReturnedId, @Param("attr3") Long attr3);
}

