package com.cuixing.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdAccessorySupplier;
import com.cuixing.md.mapper.MdAccessorySupplierMapper;
import com.cuixing.md.service.IMdAccessorySupplierService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 辅料供应商;(md_accessory_supplier)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-10-29
 */
@Service
public class MdAccessorySupplierServiceImpl implements IMdAccessorySupplierService{
    @Autowired
    private MdAccessorySupplierMapper mdAccessorySupplierMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdAccessorySupplier queryById(BigInteger id){
        return mdAccessorySupplierMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdAccessorySupplier 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdAccessorySupplier> selectMdAccessorySupplierList(MdAccessorySupplier mdAccessorySupplier){
       return mdAccessorySupplierMapper.selectMdAccessorySupplierList(mdAccessorySupplier);
    }

    /**
     * 新增数据
     *
     * @param mdAccessorySupplier 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdAccessorySupplier mdAccessorySupplier){
    	mdAccessorySupplier.setCreateTime(DateUtils.getNowDate());
		mdAccessorySupplier.setCreateBy(SecurityUtils.getUsername());
        return mdAccessorySupplierMapper.insert(mdAccessorySupplier);
    }

    /**
     * 更新数据
     *
     * @param mdAccessorySupplier 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdAccessorySupplier mdAccessorySupplier){
        mdAccessorySupplier.setUpdateTime(DateUtils.getNowDate());
		mdAccessorySupplier.setUpdateBy(SecurityUtils.getUsername());
        return mdAccessorySupplierMapper.update(mdAccessorySupplier);
    }
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdAccessorySupplierMapper.deleteByIds(ids);
    }
}
