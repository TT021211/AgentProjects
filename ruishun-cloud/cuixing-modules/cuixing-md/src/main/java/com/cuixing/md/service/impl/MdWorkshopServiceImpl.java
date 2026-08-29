package com.cuixing.md.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.md.entity.MdWorkshop;
import com.cuixing.md.mapper.MdWorkshopMapper;
import com.cuixing.md.service.IMdWorkshopService;
import java.math.BigInteger;
import java.util.List;

/**
 * 车间表;(md_workshop)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdWorkshopServiceImpl implements IMdWorkshopService{
    @Autowired
    private MdWorkshopMapper mdWorkshopMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MdWorkshop queryById(BigInteger id){
        return mdWorkshopMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdWorkshop 筛选条件
     * @return 查询结果
     */
    public List<MdWorkshop> selectMdWorkshopList(MdWorkshop mdWorkshop)
    {
        return mdWorkshopMapper.selectMdWorkshopList(mdWorkshop);
    }

    /**
     * 新增数据
     *
     * @param mdWorkshop 实例对象
     * @return 影响行数
     */
    public int insert(MdWorkshop mdWorkshop){
        mdWorkshop.setCreateTime(DateUtils.getNowDate());
        mdWorkshop.setCreateBy(SecurityUtils.getUsername());
        return mdWorkshopMapper.insert(mdWorkshop);
    }

    /**
     * 批量新增数据
     *
     * @param mdWorkshops 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MdWorkshop> mdWorkshops){
        int rs = 0;
        for (MdWorkshop mdWorkshop : mdWorkshops) {
            mdWorkshop.setCreateTime(DateUtils.getNowDate());
            mdWorkshop.setCreateBy(SecurityUtils.getUsername());
            rs = mdWorkshopMapper.insert(mdWorkshop) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mdWorkshop 实例对象
     * @return 影响行数
     */
    public int update(MdWorkshop mdWorkshop){
        BigInteger id = mdWorkshop.getId();
        MdWorkshop theUpdatemdWorkshop = mdWorkshopMapper.queryById(id);
        Integer version = theUpdatemdWorkshop.getVersion();
        if(version != null) {
            mdWorkshop.setVersion(version);
        }
        mdWorkshop.setUpdateTime(DateUtils.getNowDate());
        mdWorkshop.setUpdateBy(SecurityUtils.getUsername());
        return mdWorkshopMapper.update(mdWorkshop);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mdWorkshopMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mdWorkshopMapper.deleteByIds(ids);
        return total > 0;
    }
}