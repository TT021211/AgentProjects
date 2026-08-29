package com.cuixing.md.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.md.entity.MdVendor;
import com.cuixing.md.mapper.MdVendorMapper;
import com.cuixing.md.service.IMdVendorService;
import java.math.BigInteger;
import java.util.List;

/**
 * 供应商表;(md_vendor)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdVendorServiceImpl implements IMdVendorService{
    @Autowired
    private MdVendorMapper mdVendorMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MdVendor queryById(BigInteger id){
        return mdVendorMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdVendor 筛选条件
     * @return 查询结果
     */
    public List<MdVendor> selectMdVendorList(MdVendor mdVendor)
    {
        return mdVendorMapper.selectMdVendorList(mdVendor);
    }

    /**
     * 新增数据
     *
     * @param mdVendor 实例对象
     * @return 影响行数
     */
    public int insert(MdVendor mdVendor){
        mdVendor.setCreateTime(DateUtils.getNowDate());
        mdVendor.setCreateBy(SecurityUtils.getUsername());
        return mdVendorMapper.insert(mdVendor);
    }

    /**
     * 批量新增数据
     *
     * @param mdVendors 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MdVendor> mdVendors){
        int rs = 0;
        for (MdVendor mdVendor : mdVendors) {
            mdVendor.setCreateTime(DateUtils.getNowDate());
            mdVendor.setCreateBy(SecurityUtils.getUsername());
            rs = mdVendorMapper.insert(mdVendor) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mdVendor 实例对象
     * @return 影响行数
     */
    public int update(MdVendor mdVendor){
        BigInteger id = mdVendor.getId();
        MdVendor theUpdatemdVendor = mdVendorMapper.queryById(id);
        Integer version = theUpdatemdVendor.getVersion();
        if(version != null) {
            mdVendor.setVersion(version);
        }
        mdVendor.setUpdateTime(DateUtils.getNowDate());
        mdVendor.setUpdateBy(SecurityUtils.getUsername());
        return mdVendorMapper.update(mdVendor);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mdVendorMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mdVendorMapper.deleteByIds(ids);
        return total > 0;
    }
}