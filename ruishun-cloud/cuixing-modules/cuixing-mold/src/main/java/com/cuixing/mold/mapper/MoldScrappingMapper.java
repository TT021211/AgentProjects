package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldScrapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模具报废表(MoldScrapping)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-25 14:06:51
 */
public interface MoldScrappingMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param moldScrappingId 主键
     * @return 实例对象
     */
    MoldScrapping queryById(Long moldScrappingId);

    /**
     * 查询指定行数据
     *
     * @param moldScrapping 查询条件
     * @return 对象列表
     */
    List<MoldScrapping> selectMoldScrappingList(MoldScrapping moldScrapping);

    /**
     * 统计总行数
     *
     * @param moldScrapping 查询条件
     * @return 总行数
     */
    long count(MoldScrapping moldScrapping);

    /**
     * 新增数据
     *
     * @param moldScrapping 实例对象
     * @return 影响行数
     */
    int insert(MoldScrapping moldScrapping);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldScrapping> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldScrapping> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldScrapping> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldScrapping> entities);

    /**
     * 修改数据
     *
     * @param moldScrapping 实例对象
     * @return 影响行数
     */
    int update(MoldScrapping moldScrapping);

    /**
     * 通过主键删除数据
     *
     * @param moldScrappingId 主键
     * @return 影响行数
     */
    int deleteById(Long moldScrappingId);

}

