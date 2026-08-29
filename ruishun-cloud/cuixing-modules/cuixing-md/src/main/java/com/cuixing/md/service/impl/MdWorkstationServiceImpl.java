package com.cuixing.md.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.md.entity.MdWorkstation;
import com.cuixing.md.mapper.MdWorkstationMapper;
import com.cuixing.md.service.IMdWorkstationService;
import java.math.BigInteger;
import java.util.List;

/**
 * 机台表;(md_workstation)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdWorkstationServiceImpl implements IMdWorkstationService{
    @Autowired
    private MdWorkstationMapper mdWorkstationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MdWorkstation queryById(BigInteger id){
        return mdWorkstationMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdWorkstation 筛选条件
     * @return 查询结果
     */
    public List<MdWorkstation> selectMdWorkstationList(MdWorkstation mdWorkstation)
    {
        return mdWorkstationMapper.selectMdWorkstationList(mdWorkstation);
    }

    /**
     * 新增数据
     *
     * @param mdWorkstation 实例对象
     * @return 影响行数
     */
    public int insert(MdWorkstation mdWorkstation){
        mdWorkstation.setCreateTime(DateUtils.getNowDate());
        mdWorkstation.setCreateBy(SecurityUtils.getUsername());
        return mdWorkstationMapper.insert(mdWorkstation);
    }

    /**
     * 批量新增数据
     *
     * @param mdWorkstations 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MdWorkstation> mdWorkstations){
        int rs = 0;
        for (MdWorkstation mdWorkstation : mdWorkstations) {
            mdWorkstation.setCreateTime(DateUtils.getNowDate());
            mdWorkstation.setCreateBy(SecurityUtils.getUsername());
            rs = mdWorkstationMapper.insert(mdWorkstation) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mdWorkstation 实例对象
     * @return 影响行数
     */
    public int update(MdWorkstation mdWorkstation){
        BigInteger id = mdWorkstation.getId();
        MdWorkstation theUpdatemdWorkstation = mdWorkstationMapper.queryById(id);
        Integer version = theUpdatemdWorkstation.getVersion();
        if(version != null) {
            mdWorkstation.setVersion(version);
        }
        mdWorkstation.setUpdateTime(DateUtils.getNowDate());
        mdWorkstation.setUpdateBy(SecurityUtils.getUsername());
        return mdWorkstationMapper.update(mdWorkstation);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mdWorkstationMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mdWorkstationMapper.deleteByIds(ids);
        return total > 0;
    }
}