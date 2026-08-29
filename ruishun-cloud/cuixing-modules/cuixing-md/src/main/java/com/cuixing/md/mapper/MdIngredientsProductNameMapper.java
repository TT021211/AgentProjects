package com.cuixing.md.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdIngredientsProductName;

 /**
 * 品名;(md_ingredients_product_name)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@Mapper
public interface MdIngredientsProductNameMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdIngredientsProductName queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mdIngredientsProductName 查询条件
     * @return 对象列表
     */
    List<MdIngredientsProductName> selectMdIngredientsProductNameList(MdIngredientsProductName mdIngredientsProductName);
    /** 
     * 新增数据
     *
     * @param mdIngredientsProductName 实例对象
     * @return 影响行数
     */
    int insert(MdIngredientsProductName mdIngredientsProductName);
    
    /** 
     * 更新数据
     *
     * @param mdIngredientsProductName 实例对象
     * @return 影响行数
     */
    int update(MdIngredientsProductName mdIngredientsProductName);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

     List<MdIngredientsProductName> selectAll();
 }