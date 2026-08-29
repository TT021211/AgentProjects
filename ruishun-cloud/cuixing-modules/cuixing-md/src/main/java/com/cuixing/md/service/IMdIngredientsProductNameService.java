package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdIngredientsProductName;
import java.util.List;

 /**
 * 品名;(md_ingredients_product_name)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
public interface IMdIngredientsProductNameService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdIngredientsProductName queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdIngredientsProductName 筛选条件
     * @return 查询结果
     */
    List<MdIngredientsProductName> selectMdIngredientsProductNameList(MdIngredientsProductName mdIngredientsProductName);
    /** 
     * 新增数据
     *
     * @param mdIngredientsProductName 实例对象
     * @return 实例对象
     */
    int insert(MdIngredientsProductName mdIngredientsProductName);
    /** 
     * 更新数据
     *
     * @param mdIngredientsProductName 实例对象
     * @return 实例对象
     */
    int update(MdIngredientsProductName mdIngredientsProductName);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

     List<MdIngredientsProductName> queryAll();
 }