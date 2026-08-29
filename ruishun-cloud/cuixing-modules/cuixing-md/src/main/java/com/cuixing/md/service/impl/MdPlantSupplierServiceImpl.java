package com.cuixing.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdPlantSupplier;
import com.cuixing.md.mapper.MdPlantSupplierMapper;
import com.cuixing.md.service.IMdPlantSupplierService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 油漆供应商;(md_plant_supplier)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@Service
public class MdPlantSupplierServiceImpl implements IMdPlantSupplierService{
    @Autowired
    private MdPlantSupplierMapper mdPlantSupplierMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdPlantSupplier queryById(BigInteger id){
        return mdPlantSupplierMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mdPlantSupplier 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdPlantSupplier> selectMdPlantSupplierList(MdPlantSupplier mdPlantSupplier){
       return mdPlantSupplierMapper.selectMdPlantSupplierList(mdPlantSupplier);
    }
    
    /** 
     * 新增数据
     *
     * @param mdPlantSupplier 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdPlantSupplier mdPlantSupplier){
    	mdPlantSupplier.setCreateTime(DateUtils.getNowDate());
		mdPlantSupplier.setCreateBy(SecurityUtils.getUsername());
        return mdPlantSupplierMapper.insert(mdPlantSupplier);
    }
    
    /** 
     * 更新数据
     *
     * @param mdPlantSupplier 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdPlantSupplier mdPlantSupplier){
        mdPlantSupplier.setUpdateTime(DateUtils.getNowDate());
		mdPlantSupplier.setUpdateBy(SecurityUtils.getUsername());
        return mdPlantSupplierMapper.update(mdPlantSupplier);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdPlantSupplierMapper.deleteByIds(ids);
    }
}