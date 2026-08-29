package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldNesting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拉丝套模表(MoldNesting)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-25 14:06:48
 */
public interface MoldNestingMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param nestingMoldId 主键
     * @return 实例对象
     */
    MoldNesting queryById(Long nestingMoldId);
    /**
     * 通过ID查询单条数据
     *
     * @param planId 主键
     * @return 实例对象
     */
    List<MoldNesting> queryByPlanId(Long planId);
    /**
     * 通过CodeD查询单条数据
     *
     * @param moldNesting 主键
     * @return 实例对象
     */
    MoldNesting checkUniqueNestingCode(MoldNesting moldNesting);

    /**
     * 查询指定行数据
     *
     * @param moldNesting 查询条件
     * @return 对象列表
     */
    List<MoldNesting> selectMoldNestingList(MoldNesting moldNesting);

    /**
     * 统计总行数
     *
     * @param moldNesting 查询条件
     * @return 总行数
     */
    long count(MoldNesting moldNesting);

    /**
     * 新增数据
     *
     * @param moldNesting 实例对象
     * @return 影响行数
     */
    int insert(MoldNesting moldNesting);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldNesting> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldNesting> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldNesting> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldNesting> entities);

    /**
     * 修改数据
     *
     * @param moldNesting 实例对象
     * @return 影响行数
     */
    int update(MoldNesting moldNesting);
    /**
     * 将孔径范围置空
     *
     * @param nestingMoldId 实例对象
     * @return 影响行数
     */
    int updateToScopeNull(Long nestingMoldId);
    /**
     * 将孔径范围置空
     *
     * @param nestingMoldId 实例对象
     * @return 影响行数
     */
    int updateToPlanNull(Long nestingMoldId);
    /**
     * 将孔径范围更新
     *
     * @param moldNesting 实例对象
     * @return 影响行数
     */
    int updateToScopeAndSpec(MoldNesting moldNesting);
    /**
     * 修改数据
     *
     * @param nestingMoldId 实例对象
     * @return 影响行数
     */
    int updatePlan(Long nestingMoldId);

    /**
     * 通过主键删除数据
     *
     * @param nestingMoldId 主键
     * @return 影响行数
     */
    int deleteById(Long nestingMoldId);

    List<MoldNesting> selectMoldNestingList2(MoldNesting moldNesting);
}

