package com.cuixing.eq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.eq.entity.EqItemGroup;
import com.cuixing.eq.mapper.EqItemGroupMapper;
import com.cuixing.eq.service.IEqItemGroupService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 项目组别设置;(eq_item_group)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-7-5
 */
@Service
public class EqItemGroupServiceImpl implements IEqItemGroupService{
    @Autowired
    private EqItemGroupMapper eqItemGroupMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqItemGroup queryById(BigInteger id){
        return eqItemGroupMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqItemGroup 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqItemGroup> selectEqItemGroupList(EqItemGroup eqItemGroup){
       return eqItemGroupMapper.selectEqItemGroupList(eqItemGroup);
    }
    
    /** 
     * 新增数据
     *
     * @param eqItemGroup 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqItemGroup eqItemGroup){
    	eqItemGroup.setCreateTime(DateUtils.getNowDate());
		eqItemGroup.setCreateBy(SecurityUtils.getUsername());
        return eqItemGroupMapper.insert(eqItemGroup);
    }
    
    /** 
     * 更新数据
     *
     * @param eqItemGroup 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqItemGroup eqItemGroup){
        eqItemGroup.setUpdateTime(DateUtils.getNowDate());
		eqItemGroup.setUpdateBy(SecurityUtils.getUsername());
        return eqItemGroupMapper.update(eqItemGroup);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return eqItemGroupMapper.deleteByIds(ids);
    }
}