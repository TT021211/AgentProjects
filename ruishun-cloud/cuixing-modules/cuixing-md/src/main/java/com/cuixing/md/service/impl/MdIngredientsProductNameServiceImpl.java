package com.cuixing.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdIngredientsProductName;
import com.cuixing.md.mapper.MdIngredientsProductNameMapper;
import com.cuixing.md.service.IMdIngredientsProductNameService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 品名;(md_ingredients_product_name)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@Service
public class MdIngredientsProductNameServiceImpl implements IMdIngredientsProductNameService{
    @Autowired
    private MdIngredientsProductNameMapper mdIngredientsProductNameMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdIngredientsProductName queryById(BigInteger id){
        return mdIngredientsProductNameMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mdIngredientsProductName 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdIngredientsProductName> selectMdIngredientsProductNameList(MdIngredientsProductName mdIngredientsProductName){
       return mdIngredientsProductNameMapper.selectMdIngredientsProductNameList(mdIngredientsProductName);
    }
     /**
      * 获取列表
      */
     @Override
     public List<MdIngredientsProductName> queryAll() {
         return mdIngredientsProductNameMapper.selectAll();
     }

     /**
     * 新增数据
     *
     * @param mdIngredientsProductName 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdIngredientsProductName mdIngredientsProductName){
    	mdIngredientsProductName.setCreateTime(DateUtils.getNowDate());
		mdIngredientsProductName.setCreateBy(SecurityUtils.getUsername());
        return mdIngredientsProductNameMapper.insert(mdIngredientsProductName);
    }
    
    /** 
     * 更新数据
     *
     * @param mdIngredientsProductName 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdIngredientsProductName mdIngredientsProductName){
        mdIngredientsProductName.setUpdateTime(DateUtils.getNowDate());
		mdIngredientsProductName.setUpdateBy(SecurityUtils.getUsername());
        return mdIngredientsProductNameMapper.update(mdIngredientsProductName);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdIngredientsProductNameMapper.deleteByIds(ids);
    }
}