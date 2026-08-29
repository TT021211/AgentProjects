package com.cuixing.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdPlantType;
import com.cuixing.md.mapper.MdPlantTypeMapper;
import com.cuixing.md.service.IMdPlantTypeService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 油漆类型;(md_plant_type)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@Service
public class MdPlantTypeServiceImpl implements IMdPlantTypeService{
    @Autowired
    private MdPlantTypeMapper mdPlantTypeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdPlantType queryById(BigInteger id){
        return mdPlantTypeMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mdPlantType 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdPlantType> selectMdPlantTypeList(MdPlantType mdPlantType){
       return mdPlantTypeMapper.selectMdPlantTypeList(mdPlantType);
    }
    
    /** 
     * 新增数据
     *
     * @param mdPlantType 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdPlantType mdPlantType){
    	mdPlantType.setCreateTime(DateUtils.getNowDate());
		mdPlantType.setCreateBy(SecurityUtils.getUsername());
        return mdPlantTypeMapper.insert(mdPlantType);
    }
    
    /** 
     * 更新数据
     *
     * @param mdPlantType 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdPlantType mdPlantType){
        mdPlantType.setUpdateTime(DateUtils.getNowDate());
		mdPlantType.setUpdateBy(SecurityUtils.getUsername());
        return mdPlantTypeMapper.update(mdPlantType);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdPlantTypeMapper.deleteByIds(ids);
    }
}