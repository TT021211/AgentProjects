package com.cuixing.eq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.eq.entity.EqItemType;
import com.cuixing.eq.mapper.EqItemTypeMapper;
import com.cuixing.eq.service.IEqItemTypeService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 项目类型设置;(eq_item_type)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Service
public class EqItemTypeServiceImpl implements IEqItemTypeService{
    @Autowired
    private EqItemTypeMapper eqItemTypeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqItemType queryById(BigInteger id){
        return eqItemTypeMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqItemType 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqItemType> selectEqItemTypeList(EqItemType eqItemType){
       return eqItemTypeMapper.selectEqItemTypeList(eqItemType);
    }
    
    /** 
     * 新增数据
     *
     * @param eqItemType 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqItemType eqItemType){
    	eqItemType.setCreateTime(DateUtils.getNowDate());
		eqItemType.setCreateBy(SecurityUtils.getUsername());
        return eqItemTypeMapper.insert(eqItemType);
    }
    
    /** 
     * 更新数据
     *
     * @param eqItemType 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqItemType eqItemType){
        eqItemType.setUpdateTime(DateUtils.getNowDate());
		eqItemType.setUpdateBy(SecurityUtils.getUsername());
        return eqItemTypeMapper.update(eqItemType);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return eqItemTypeMapper.deleteByIds(ids);
    }
}