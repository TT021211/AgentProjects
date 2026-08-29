package com.cuixing.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdIngredientsTraders;
import com.cuixing.md.mapper.MdIngredientsTradersMapper;
import com.cuixing.md.service.IMdIngredientsTradersService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 贸易商设置;(md_ingredients_traders)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@Service
public class MdIngredientsTradersServiceImpl implements IMdIngredientsTradersService{
    @Autowired
    private MdIngredientsTradersMapper mdIngredientsTradersMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdIngredientsTraders queryById(BigInteger id){
        return mdIngredientsTradersMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mdIngredientsTraders 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdIngredientsTraders> selectMdIngredientsTradersList(MdIngredientsTraders mdIngredientsTraders){
       return mdIngredientsTradersMapper.selectMdIngredientsTradersList(mdIngredientsTraders);
    }
    
    /** 
     * 新增数据
     *
     * @param mdIngredientsTraders 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdIngredientsTraders mdIngredientsTraders){
    	mdIngredientsTraders.setCreateTime(DateUtils.getNowDate());
		mdIngredientsTraders.setCreateBy(SecurityUtils.getUsername());
        return mdIngredientsTradersMapper.insert(mdIngredientsTraders);
    }
    
    /** 
     * 更新数据
     *
     * @param mdIngredientsTraders 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdIngredientsTraders mdIngredientsTraders){
        mdIngredientsTraders.setUpdateTime(DateUtils.getNowDate());
		mdIngredientsTraders.setUpdateBy(SecurityUtils.getUsername());
        return mdIngredientsTradersMapper.update(mdIngredientsTraders);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdIngredientsTradersMapper.deleteByIds(ids);
    }
}