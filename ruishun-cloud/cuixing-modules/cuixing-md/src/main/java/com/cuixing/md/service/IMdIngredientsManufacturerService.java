package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdIngredientsManufacturer;
import java.util.List;

 /**
 * 材料厂家;(md_ingredients_manufacturer)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-4
 */
public interface IMdIngredientsManufacturerService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdIngredientsManufacturer queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdIngredientsManufacturer 筛选条件
     * @return 查询结果
     */
    List<MdIngredientsManufacturer> selectMdIngredientsManufacturerList(MdIngredientsManufacturer mdIngredientsManufacturer);
    /** 
     * 新增数据
     *
     * @param mdIngredientsManufacturer 实例对象
     * @return 实例对象
     */
    int insert(MdIngredientsManufacturer mdIngredientsManufacturer);
    /** 
     * 更新数据
     *
     * @param mdIngredientsManufacturer 实例对象
     * @return 实例对象
     */
    int update(MdIngredientsManufacturer mdIngredientsManufacturer);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

     /**
      * * 获取列表
      * @return
      */
     List<MdIngredientsManufacturer> queryAll();
 }