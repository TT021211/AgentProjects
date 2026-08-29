package com.cuixing.md.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.md.entity.MdWorkshop;
import java.math.BigInteger;

/**
 * 车间表;(md_workshop)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Mapper
public interface MdWorkshopMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkshop queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mdWorkshop 查询条件
     * @return 对象列表
     */
    List<MdWorkshop> selectMdWorkshopList(MdWorkshop mdWorkshop);
    /**
     * 统计总行数
     *
     * @param mdWorkshop 查询条件
     * @return 总行数
     */
    long count(MdWorkshop mdWorkshop);
    /**
     * 新增数据
     *
     * @param mdWorkshop 实例对象
     * @return 影响行数
     */
    int insert(MdWorkshop mdWorkshop);
    /**
     * 批量新增数据
     *
     * @param entities List<MdWorkshop> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MdWorkshop> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MdWorkshop> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MdWorkshop> entities);
    /**
     * 更新数据
     *
     * @param mdWorkshop 实例对象
     * @return 影响行数
     */
    int update(MdWorkshop mdWorkshop);
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