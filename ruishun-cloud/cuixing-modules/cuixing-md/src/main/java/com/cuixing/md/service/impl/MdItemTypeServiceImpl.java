package com.cuixing.md.service.impl;

import com.cuixing.md.entity.MdProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.md.entity.MdItemType;
import com.cuixing.md.mapper.MdItemTypeMapper;
import com.cuixing.md.service.IMdItemTypeService;
import java.math.BigInteger;
import java.util.List;

/**
 * 物料分类表;(md_item_type)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdItemTypeServiceImpl implements IMdItemTypeService{
    @Autowired
    private MdItemTypeMapper mdItemTypeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MdItemType queryById(BigInteger id){
        return mdItemTypeMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdItemType 筛选条件
     * @return 查询结果
     */
    public List<MdItemType> selectMdItemTypeList(MdItemType mdItemType)
    {
        return mdItemTypeMapper.selectMdItemTypeList(mdItemType);
    }

    /**
     * 新增数据
     *
     * @param mdItemType 实例对象
     * @return 影响行数
     */
    public int insert(MdItemType mdItemType){
        mdItemType.setCreateTime(DateUtils.getNowDate());
        return mdItemTypeMapper.insert(mdItemType);
    }

    /**
     * 批量新增数据
     *
     * @param mdItemTypes 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MdItemType> mdItemTypes){
        int rs = 0;
        for (MdItemType mdItemType : mdItemTypes) {
            mdItemType.setCreateTime(DateUtils.getNowDate());
            rs = mdItemTypeMapper.insert(mdItemType) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mdItemType 实例对象
     * @return 影响行数
     */
    public int update(MdItemType mdItemType){
        BigInteger id = mdItemType.getId();
        MdItemType theUpdatemdItemType = mdItemTypeMapper.queryById(id);
        Integer version = theUpdatemdItemType.getVersion();
        if(version != null) {
            mdItemType.setVersion(version);
        }
        mdItemType.setUpdateTime(DateUtils.getNowDate());
        return mdItemTypeMapper.update(mdItemType);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mdItemTypeMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mdItemTypeMapper.deleteByIds(ids);
        return total > 0;
    }

    @Override
    public Boolean checkHasChildren(BigInteger[] ids) {
        MdItemType mdItemType = new MdItemType();
        for(BigInteger id : ids)
        {
            mdItemType.setParentTypeId(id);
            List<MdItemType> mdItemTypes = mdItemTypeMapper.selectMdItemTypeList(mdItemType);
            if(mdItemTypes.size()>0)
            {
                return true;
            }

        }
        return false;
    }

    @Override
    public Boolean checkHasChildren(BigInteger id) {
        MdItemType mdItemType = new MdItemType();
        mdItemType.setParentTypeId(id);
        List<MdItemType> mdItemTypes = mdItemTypeMapper.selectMdItemTypeList(mdItemType);
        if(mdItemTypes.size()>0)
        {
            return true;
        }
        return false;
    }

}