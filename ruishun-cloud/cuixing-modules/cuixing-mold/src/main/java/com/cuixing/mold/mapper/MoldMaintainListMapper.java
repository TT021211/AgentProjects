package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldMaintainList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 待保养列表(MoldMaintainList)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-26 11:54:04
 */
public interface MoldMaintainListMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MoldMaintainList queryById(Long id);
    /**
     * 通过ID查询单条数据
     *
     * @param planId 主键
     * @return 实例对象
     */
    List<MoldMaintainList> queryByPlanId(Long planId);

    /**
     * 查询指定行数据
     *
     * @param moldMaintainList 查询条件
     * @return 对象列表
     */
    List<MoldMaintainList> selectMoldMaintainListList(MoldMaintainList moldMaintainList);

    /**
     * 统计总行数
     *
     * @param moldMaintainList 查询条件
     * @return 总行数
     */
    long count(MoldMaintainList moldMaintainList);

    /**
     * 新增数据
     *
     * @param moldMaintainList 实例对象
     * @return 影响行数
     */
    int insert(MoldMaintainList moldMaintainList);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldMaintainList> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldMaintainList> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldMaintainList> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldMaintainList> entities);

    /**
     * 修改数据
     *
     * @param moldMaintainList 实例对象
     * @return 影响行数
     */
    int update(MoldMaintainList moldMaintainList);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);
    /**
     * 通过主键删除数据
     *
     * @param planId 主键
     * @return 影响行数
     */
    int deleteByPlanId(Long planId);

}

