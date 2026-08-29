package com.cuixing.eq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.eq.entity.EqEquipmentType;
import com.cuixing.eq.mapper.EqEquipmentTypeMapper;
import com.cuixing.eq.service.IEqEquipmentTypeService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 设备类别设置;(eq_equipment_type)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-17
 */
@Service
public class EqEquipmentTypeServiceImpl implements IEqEquipmentTypeService{
    @Autowired
    private EqEquipmentTypeMapper eqEquipmentTypeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqEquipmentType queryById(BigInteger id){
        return eqEquipmentTypeMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqEquipmentType 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqEquipmentType> selectEqEquipmentTypeList(EqEquipmentType eqEquipmentType){
       return eqEquipmentTypeMapper.selectEqEquipmentTypeList(eqEquipmentType);
    }
    
    /** 
     * 新增数据
     *
     * @param eqEquipmentType 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqEquipmentType eqEquipmentType){
    	eqEquipmentType.setCreateTime(DateUtils.getNowDate());
		eqEquipmentType.setCreateBy(SecurityUtils.getUsername());
        return eqEquipmentTypeMapper.insert(eqEquipmentType);
    }
    
    /** 
     * 更新数据
     *
     * @param eqEquipmentType 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqEquipmentType eqEquipmentType){
        eqEquipmentType.setUpdateTime(DateUtils.getNowDate());
		eqEquipmentType.setUpdateBy(SecurityUtils.getUsername());
        return eqEquipmentTypeMapper.update(eqEquipmentType);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return eqEquipmentTypeMapper.deleteByIds(ids);
    }
}