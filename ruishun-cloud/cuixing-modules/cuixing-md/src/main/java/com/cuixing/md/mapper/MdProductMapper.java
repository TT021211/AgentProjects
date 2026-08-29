package com.cuixing.md.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.md.entity.MdProduct;
import java.math.BigInteger;
import java.util.Set;

/**
 * 产品表;(md_product)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Mapper
public interface MdProductMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdProduct queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mdProduct 查询条件
     * @return 对象列表
     */
    List<MdProduct> selectMdProductList(MdProduct mdProduct);
    /**
     * 统计总行数
     *
     * @param mdProduct 查询条件
     * @return 总行数
     */
    long count(MdProduct mdProduct);
    /**
     * 新增数据
     *
     * @param mdProduct 实例对象
     * @return 影响行数
     */
    int insert(MdProduct mdProduct);
    /**
     * 批量新增数据
     *
     * @param entities List<MdProduct> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MdProduct> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MdProduct> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MdProduct> entities);
    /**
     * 更新数据
     *
     * @param mdProduct 实例对象
     * @return 影响行数
     */
    int update(MdProduct mdProduct);
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

    List<List<MdProduct>> getProductList(MdProduct mdProduct);

    List<MdProduct> queryBypTypeId(@Param("productTypeId") BigInteger id);

    List<MdProduct> selectByParam(MdProduct mdProduct);

    List<MdProduct> getProductListBySpec(@Param("productSize")List<String> productSize);

    List<MdProduct> queryByproductCode(@Param("productCode") Set<String> productCode);
}
