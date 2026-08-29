package com.cuixing.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdIngredientsManufacturer;
import com.cuixing.md.mapper.MdIngredientsManufacturerMapper;
import com.cuixing.md.service.IMdIngredientsManufacturerService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 材料厂家;(md_ingredients_manufacturer)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-4
 */
@Service
public class MdIngredientsManufacturerServiceImpl implements IMdIngredientsManufacturerService{
    @Autowired
    private MdIngredientsManufacturerMapper mdIngredientsManufacturerMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdIngredientsManufacturer queryById(BigInteger id){
        return mdIngredientsManufacturerMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mdIngredientsManufacturer 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdIngredientsManufacturer> selectMdIngredientsManufacturerList(MdIngredientsManufacturer mdIngredientsManufacturer){
       return mdIngredientsManufacturerMapper.selectMdIngredientsManufacturerList(mdIngredientsManufacturer);
    }

     @Override
     public List<MdIngredientsManufacturer> queryAll() {
         return mdIngredientsManufacturerMapper.selectAll();
     }

     /**
     * 新增数据
     *
     * @param mdIngredientsManufacturer 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdIngredientsManufacturer mdIngredientsManufacturer){
    	mdIngredientsManufacturer.setCreateTime(DateUtils.getNowDate());
		mdIngredientsManufacturer.setCreateBy(SecurityUtils.getUsername());
        return mdIngredientsManufacturerMapper.insert(mdIngredientsManufacturer);
    }
    
    /** 
     * 更新数据
     *
     * @param mdIngredientsManufacturer 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdIngredientsManufacturer mdIngredientsManufacturer){
        mdIngredientsManufacturer.setUpdateTime(DateUtils.getNowDate());
		mdIngredientsManufacturer.setUpdateBy(SecurityUtils.getUsername());
        return mdIngredientsManufacturerMapper.update(mdIngredientsManufacturer);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdIngredientsManufacturerMapper.deleteByIds(ids);
    }
}