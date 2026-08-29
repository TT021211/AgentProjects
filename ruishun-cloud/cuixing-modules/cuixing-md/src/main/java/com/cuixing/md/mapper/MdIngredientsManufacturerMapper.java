package com.cuixing.md.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdIngredientsManufacturer;

 /**
 * 材料厂家;(md_ingredients_manufacturer)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-4
 */
@Mapper
public interface MdIngredientsManufacturerMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdIngredientsManufacturer queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mdIngredientsManufacturer 查询条件
     * @return 对象列表
     */
    List<MdIngredientsManufacturer> selectMdIngredientsManufacturerList(MdIngredientsManufacturer mdIngredientsManufacturer);
    /** 
     * 新增数据
     *
     * @param mdIngredientsManufacturer 实例对象
     * @return 影响行数
     */
    int insert(MdIngredientsManufacturer mdIngredientsManufacturer);
    
    /** 
     * 更新数据
     *
     * @param mdIngredientsManufacturer 实例对象
     * @return 影响行数
     */
    int update(MdIngredientsManufacturer mdIngredientsManufacturer);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

     List<MdIngredientsManufacturer> selectAll();
 }