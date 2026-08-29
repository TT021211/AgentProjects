package com.cuixing.mes.mapper;

import com.cuixing.mes.entity.MesErrorLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 生产异常表(MesErrorLog)表数据库访问层
 *
 * @author 阿江
 * @since 2025-07-14 23:27:49
 */
public interface MesErrorLogMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesErrorLog queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param mesErrorLog 查询条件
     * @return 对象列表
     */
    List<MesErrorLog> selectMesErrorLogList(MesErrorLog mesErrorLog);

    /**
     * 统计总行数
     *
     * @param mesErrorLog 查询条件
     * @return 总行数
     */
    long count(MesErrorLog mesErrorLog);

    /**
     * 新增数据
     *
     * @param mesErrorLog 实例对象
     * @return 影响行数
     */
    int insert(MesErrorLog mesErrorLog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MesErrorLog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MesErrorLog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MesErrorLog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MesErrorLog> entities);

    /**
     * 修改数据
     *
     * @param mesErrorLog 实例对象
     * @return 影响行数
     */
    int update(MesErrorLog mesErrorLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

