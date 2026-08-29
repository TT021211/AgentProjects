package com.cuixing.md.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdIngredientsTraders;

 /**
 * 贸易商设置;(md_ingredients_traders)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@Mapper
public interface MdIngredientsTradersMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdIngredientsTraders queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mdIngredientsTraders 查询条件
     * @return 对象列表
     */
    List<MdIngredientsTraders> selectMdIngredientsTradersList(MdIngredientsTraders mdIngredientsTraders);
    /** 
     * 新增数据
     *
     * @param mdIngredientsTraders 实例对象
     * @return 影响行数
     */
    int insert(MdIngredientsTraders mdIngredientsTraders);
    
    /** 
     * 更新数据
     *
     * @param mdIngredientsTraders 实例对象
     * @return 影响行数
     */
    int update(MdIngredientsTraders mdIngredientsTraders);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}