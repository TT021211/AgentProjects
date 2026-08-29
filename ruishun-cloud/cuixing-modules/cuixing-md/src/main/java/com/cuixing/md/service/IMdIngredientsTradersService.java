package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdIngredientsTraders;
import java.util.List;

 /**
 * 贸易商设置;(md_ingredients_traders)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
public interface IMdIngredientsTradersService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdIngredientsTraders queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdIngredientsTraders 筛选条件
     * @return 查询结果
     */
    List<MdIngredientsTraders> selectMdIngredientsTradersList(MdIngredientsTraders mdIngredientsTraders);
    /** 
     * 新增数据
     *
     * @param mdIngredientsTraders 实例对象
     * @return 实例对象
     */
    int insert(MdIngredientsTraders mdIngredientsTraders);
    /** 
     * 更新数据
     *
     * @param mdIngredientsTraders 实例对象
     * @return 实例对象
     */
    int update(MdIngredientsTraders mdIngredientsTraders);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}