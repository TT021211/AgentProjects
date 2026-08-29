package com.cuixing.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdWorkbenchType;
import com.cuixing.md.mapper.MdWorkbenchTypeMapper;
import com.cuixing.md.service.IMdWorkbenchTypeService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 机台类型;(md_workbench_type)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@Service
public class MdWorkbenchTypeServiceImpl implements IMdWorkbenchTypeService{
    @Autowired
    private MdWorkbenchTypeMapper mdWorkbenchTypeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdWorkbenchType queryById(BigInteger id){
        return mdWorkbenchTypeMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mdWorkbenchType 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdWorkbenchType> selectMdWorkbenchTypeList(MdWorkbenchType mdWorkbenchType){
       return mdWorkbenchTypeMapper.selectMdWorkbenchTypeList(mdWorkbenchType);
    }
    
    /** 
     * 新增数据
     *
     * @param mdWorkbenchType 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdWorkbenchType mdWorkbenchType){
    	mdWorkbenchType.setCreateTime(DateUtils.getNowDate());
		mdWorkbenchType.setCreateBy(SecurityUtils.getUsername());
        return mdWorkbenchTypeMapper.insert(mdWorkbenchType);
    }
    
    /** 
     * 更新数据
     *
     * @param mdWorkbenchType 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdWorkbenchType mdWorkbenchType){
        mdWorkbenchType.setUpdateTime(DateUtils.getNowDate());
		mdWorkbenchType.setUpdateBy(SecurityUtils.getUsername());
        return mdWorkbenchTypeMapper.update(mdWorkbenchType);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdWorkbenchTypeMapper.deleteByIds(ids);
    }
}