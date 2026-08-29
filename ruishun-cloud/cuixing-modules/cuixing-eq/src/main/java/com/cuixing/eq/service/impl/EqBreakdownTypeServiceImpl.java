package com.cuixing.eq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.eq.entity.EqBreakdownType;
import com.cuixing.eq.mapper.EqBreakdownTypeMapper;
import com.cuixing.eq.service.IEqBreakdownTypeService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 设备故障类型;(eq_breakdown_type)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-7-3
 */
@Service
public class EqBreakdownTypeServiceImpl implements IEqBreakdownTypeService{
    @Autowired
    private EqBreakdownTypeMapper eqBreakdownTypeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqBreakdownType queryById(BigInteger id){
        return eqBreakdownTypeMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqBreakdownType 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqBreakdownType> selectEqBreakdownTypeList(EqBreakdownType eqBreakdownType){
       return eqBreakdownTypeMapper.selectEqBreakdownTypeList(eqBreakdownType);
    }
    
    /** 
     * 新增数据
     *
     * @param eqBreakdownType 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqBreakdownType eqBreakdownType){
    	eqBreakdownType.setCreateTime(DateUtils.getNowDate());
		eqBreakdownType.setCreateBy(SecurityUtils.getUsername());
        return eqBreakdownTypeMapper.insert(eqBreakdownType);
    }
    
    /** 
     * 更新数据
     *
     * @param eqBreakdownType 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqBreakdownType eqBreakdownType){
        eqBreakdownType.setUpdateTime(DateUtils.getNowDate());
		eqBreakdownType.setUpdateBy(SecurityUtils.getUsername());
        return eqBreakdownTypeMapper.update(eqBreakdownType);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return eqBreakdownTypeMapper.deleteByIds(ids);
    }
}