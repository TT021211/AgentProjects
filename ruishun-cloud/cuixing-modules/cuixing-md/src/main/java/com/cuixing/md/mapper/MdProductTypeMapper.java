package com.cuixing.md.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.md.entity.MdProductType;
import java.math.BigInteger;

/**
 * 产品分类表;(md_product_type)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Mapper
public interface MdProductTypeMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdProductType queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mdProductType 查询条件
     * @return 对象列表
     */
    List<MdProductType> selectMdProductTypeList(MdProductType mdProductType);
    /**
     * 统计总行数
     *
     * @param mdProductType 查询条件
     * @return 总行数
     */
    long count(MdProductType mdProductType);
    /**
     * 新增数据
     *
     * @param mdProductType 实例对象
     * @return 影响行数
     */
    int insert(MdProductType mdProductType);
    /**
     * 批量新增数据
     *
     * @param entities List<MdProductType> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MdProductType> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MdProductType> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MdProductType> entities);
    /**
     * 更新数据
     *
     * @param mdProductType 实例对象
     * @return 影响行数
     */
    int update(MdProductType mdProductType);
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

    MdProductType queryByTypeName(@Param("name") String name);
}
