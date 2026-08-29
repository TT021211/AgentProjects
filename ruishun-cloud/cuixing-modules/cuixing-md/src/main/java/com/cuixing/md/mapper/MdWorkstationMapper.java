package com.cuixing.md.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.md.entity.MdWorkstation;
import java.math.BigInteger;

/**
 * 机台表;(md_workstation)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Mapper
public interface MdWorkstationMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkstation queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mdWorkstation 查询条件
     * @return 对象列表
     */
    List<MdWorkstation> selectMdWorkstationList(MdWorkstation mdWorkstation);
    /**
     * 统计总行数
     *
     * @param mdWorkstation 查询条件
     * @return 总行数
     */
    long count(MdWorkstation mdWorkstation);
    /**
     * 新增数据
     *
     * @param mdWorkstation 实例对象
     * @return 影响行数
     */
    int insert(MdWorkstation mdWorkstation);
    /**
     * 批量新增数据
     *
     * @param entities List<MdWorkstation> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MdWorkstation> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MdWorkstation> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MdWorkstation> entities);
    /**
     * 更新数据
     *
     * @param mdWorkstation 实例对象
     * @return 影响行数
     */
    int update(MdWorkstation mdWorkstation);
    /**
     * 通过主键逻辑删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(BigInteger id);
    /**
     * 通过主键批量逻辑删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
    /**
     * 通过主键物理删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int removeById(BigInteger id);
}