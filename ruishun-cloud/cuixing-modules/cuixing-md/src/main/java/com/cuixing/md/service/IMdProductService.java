package com.cuixing.md.service;

import com.cuixing.md.entity.MdProduct;
import com.cuixing.md.entity.vo.MdProductVo;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * 产品表;(md_product)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdProductService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdProduct queryById(BigInteger id);

    /**
     * 查询品名下的所有规格型号
     */
    MdProductVo queryByName(String name);
    /**
     * 分页查询
     *
     * @param mdProduct 筛选条件
     * @return 查询结果
     */
    List<MdProduct> selectMdProductList(MdProduct mdProduct);
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
     * @param mdProducts 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdProduct> mdProducts);
    /**
     * 更新数据
     *
     * @param mdProduct 实例对象
     * @return 影响行数
     */
    int update(MdProduct mdProduct);
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

    List<List<MdProduct>> getProductList(MdProduct mdProduct);

    List<MdProduct> selectByParam(MdProduct mdProduct);

    boolean isProductDuplicate(String productTypeName,  String specification, String batchNumber);

    List<MdProduct> getProductListBySpec(List<String> productSize);

    List<MdProduct> queryByproductCode(Set<String> productCode);
}
