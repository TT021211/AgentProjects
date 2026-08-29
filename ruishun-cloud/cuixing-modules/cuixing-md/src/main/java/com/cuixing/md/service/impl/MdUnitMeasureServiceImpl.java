package com.cuixing.md.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.md.entity.MdUnitMeasure;
import com.cuixing.md.mapper.MdUnitMeasureMapper;
import com.cuixing.md.service.IMdUnitMeasureService;
import java.math.BigInteger;
import java.util.List;

/**
 * 单位表;(md_unit_measure)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdUnitMeasureServiceImpl implements IMdUnitMeasureService{
    @Autowired
    private MdUnitMeasureMapper mdUnitMeasureMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MdUnitMeasure queryById(BigInteger id){
        return mdUnitMeasureMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdUnitMeasure 筛选条件
     * @return 查询结果
     */
    public List<MdUnitMeasure> selectMdUnitMeasureList(MdUnitMeasure mdUnitMeasure)
    {
        return mdUnitMeasureMapper.selectMdUnitMeasureList(mdUnitMeasure);
    }

    /**
     * 新增数据
     *
     * @param mdUnitMeasure 实例对象
     * @return 影响行数
     */
    public int insert(MdUnitMeasure mdUnitMeasure){
        mdUnitMeasure.setCreateTime(DateUtils.getNowDate());
        mdUnitMeasure.setCreateBy(SecurityUtils.getUsername());
        return mdUnitMeasureMapper.insert(mdUnitMeasure);
    }

    /**
     * 批量新增数据
     *
     * @param mdUnitMeasures 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MdUnitMeasure> mdUnitMeasures){
        int rs = 0;
        for (MdUnitMeasure mdUnitMeasure : mdUnitMeasures) {
            mdUnitMeasure.setCreateTime(DateUtils.getNowDate());
            mdUnitMeasure.setCreateBy(SecurityUtils.getUsername());
            rs = mdUnitMeasureMapper.insert(mdUnitMeasure) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mdUnitMeasure 实例对象
     * @return 影响行数
     */
    public int update(MdUnitMeasure mdUnitMeasure){
        BigInteger id = mdUnitMeasure.getId();
        MdUnitMeasure theUpdatemdUnitMeasure = mdUnitMeasureMapper.queryById(id);
        Integer version = theUpdatemdUnitMeasure.getVersion();
        if(version != null) {
            mdUnitMeasure.setVersion(version);
        }
        mdUnitMeasure.setUpdateTime(DateUtils.getNowDate());
        mdUnitMeasure.setUpdateBy(SecurityUtils.getUsername());
        return mdUnitMeasureMapper.update(mdUnitMeasure);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mdUnitMeasureMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mdUnitMeasureMapper.deleteByIds(ids);
        return total > 0;
    }
}