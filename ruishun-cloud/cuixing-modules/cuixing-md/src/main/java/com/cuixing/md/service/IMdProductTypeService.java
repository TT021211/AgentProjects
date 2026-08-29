package com.cuixing.md.service;

import com.cuixing.md.entity.MdProductType;
import java.math.BigInteger;
import java.util.List;

/**
 * 产品分类表;(md_product_type)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdProductTypeService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdProductType queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdProductType 筛选条件
     * @return 查询结果
     */
    List<MdProductType> selectMdProductTypeList(MdProductType mdProductType);
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
     * @param mdProductTypes 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdProductType> mdProductTypes);
    /**
     * 更新数据
     *
     * @param mdProductType 实例对象
     * @return 影响行数
     */
    int update(MdProductType mdProductType);

    Boolean checkHasChildren(BigInteger[] id);
    Boolean checkHasChildren(BigInteger id);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigInteger id);
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    boolean deleteByIds(BigInteger[] ids);

    MdProductType queryByTypeName(String productTypeName);
}
