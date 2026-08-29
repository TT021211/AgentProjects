package com.cuixing.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.md.entity.MdProductType;
import com.cuixing.md.mapper.MdProductTypeMapper;
import com.cuixing.md.service.IMdProductTypeService;
import java.math.BigInteger;
import java.util.List;

/**
 * 产品分类表;(md_product_type)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdProductTypeServiceImpl implements IMdProductTypeService{
    @Autowired
    private MdProductTypeMapper mdProductTypeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MdProductType queryById(BigInteger id){
        return mdProductTypeMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdProductType 筛选条件
     * @return 查询结果
     */
    public List<MdProductType> selectMdProductTypeList(MdProductType mdProductType)
    {
        return mdProductTypeMapper.selectMdProductTypeList(mdProductType);
    }

    /**
     * 新增数据
     *
     * @param mdProductType 实例对象
     * @return 影响行数
     */
    public int insert(MdProductType mdProductType){
        mdProductType.setCreateTime(DateUtils.getNowDate());
        return mdProductTypeMapper.insert(mdProductType);
    }

    /**
     * 批量新增数据
     *
     * @param mdProductTypes 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MdProductType> mdProductTypes){
        int rs = 0;
        for (MdProductType mdProductType : mdProductTypes) {
            mdProductType.setCreateTime(DateUtils.getNowDate());
            rs = mdProductTypeMapper.insert(mdProductType) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mdProductType 实例对象
     * @return 影响行数
     */
    public int update(MdProductType mdProductType){
        BigInteger id = mdProductType.getId();
        MdProductType theUpdatemdProductType = mdProductTypeMapper.queryById(id);
        Integer version = theUpdatemdProductType.getVersion();
        if(version != null) {
            mdProductType.setVersion(version);
        }
        mdProductType.setUpdateTime(DateUtils.getNowDate());
        return mdProductTypeMapper.update(mdProductType);
    }
    /**
     * 查询该分类是否有子分类
     * @param id
     * @return
     */
    @Override
    public Boolean checkHasChildren(BigInteger[] ids) {
        MdProductType mdProductType = new MdProductType();
        for(BigInteger id : ids)
        {
            mdProductType.setParentTypeId(id);
            List<MdProductType> mdProductTypes = mdProductTypeMapper.selectMdProductTypeList(mdProductType);
            if(mdProductTypes.size()>0)
            {
                return true;
            }

        }
        return false;




    }

    /**
     * 查询该分类是否有子分类
     * @param id
     * @return
     */
    public Boolean checkHasChildren(BigInteger id)
    {
        MdProductType mdProductType = new MdProductType();
        mdProductType.setParentTypeId(id);
        List<MdProductType> mdProductTypes = mdProductTypeMapper.selectMdProductTypeList(mdProductType);
        if(mdProductTypes.size()>0)
        {
            return true;
        }
        return false;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mdProductTypeMapper.deleteById(id);

        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mdProductTypeMapper.deleteByIds(ids);
        return total > 0;
    }

    @Override
    public MdProductType queryByTypeName(String productTypeName) {
        return mdProductTypeMapper.queryByTypeName(productTypeName);
    }
}
