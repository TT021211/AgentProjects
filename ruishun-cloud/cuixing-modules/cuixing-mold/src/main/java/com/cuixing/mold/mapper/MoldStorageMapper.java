package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldStorage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模具入库表(MoldStorage)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-25 14:06:51
 */
public interface MoldStorageMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param moldStorageId 主键
     * @return 实例对象
     */
    MoldStorage queryById(Long moldStorageId);

    /**
     * 查询指定行数据
     *
     * @param moldStorage 查询条件
     * @return 对象列表
     */
    List<MoldStorage> selectMoldStorageList(MoldStorage moldStorage);

    /**
     * 统计总行数
     *
     * @param moldStorage 查询条件
     * @return 总行数
     */
    long count(MoldStorage moldStorage);

    /**
     * 新增数据
     *
     * @param moldStorage 实例对象
     * @return 影响行数
     */
    int insert(MoldStorage moldStorage);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldStorage> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldStorage> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldStorage> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldStorage> entities);

    /**
     * 修改数据
     *
     * @param moldStorage 实例对象
     * @return 影响行数
     */
    int update(MoldStorage moldStorage);

    /**
     * 通过主键删除数据
     *
     * @param moldStorageId 主键
     * @return 影响行数
     */
    int deleteById(Long moldStorageId);

}

