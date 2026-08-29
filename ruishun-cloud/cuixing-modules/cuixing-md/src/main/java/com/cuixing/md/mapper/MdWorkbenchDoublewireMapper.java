package com.cuixing.md.mapper;

import com.cuixing.md.entity.MdWorkbenchDoublewire;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

/**
 * 机台设置(MdWorkbenchDoublewire)表数据库访问层
 *
 * @author makejava
 * @since 2024-11-25 16:46:09
 */
public interface MdWorkbenchDoublewireMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchDoublewire queryById(BigInteger id);

    /**
     * 查询指定行数据
     *
     * @param mdWorkbenchDoublewire 查询条件
     * @return 对象列表
     */
    List<MdWorkbenchDoublewire> selectMdWorkbenchDoublewireList(MdWorkbenchDoublewire mdWorkbenchDoublewire);

    /**
     * 统计总行数
     *
     * @param mdWorkbenchDoublewire 查询条件
     * @return 总行数
     */
    long count(MdWorkbenchDoublewire mdWorkbenchDoublewire);

    /**
     * 新增数据
     *
     * @param mdWorkbenchDoublewire 实例对象
     * @return 影响行数
     */
    int insert(MdWorkbenchDoublewire mdWorkbenchDoublewire);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MdWorkbenchDoublewire> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MdWorkbenchDoublewire> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MdWorkbenchDoublewire> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MdWorkbenchDoublewire> entities);

    /**
     * 修改数据
     *
     * @param mdWorkbenchDoublewire 实例对象
     * @return 影响行数
     */
    int update(MdWorkbenchDoublewire mdWorkbenchDoublewire);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(BigInteger id);

    int deleteByIds(BigInteger[] ids);

}

