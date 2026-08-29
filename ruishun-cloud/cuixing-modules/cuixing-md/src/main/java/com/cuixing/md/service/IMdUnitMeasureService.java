package com.cuixing.md.service;

import com.cuixing.md.entity.MdUnitMeasure;
import java.math.BigInteger;
import java.util.List;

/**
 * 单位表;(md_unit_measure)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdUnitMeasureService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdUnitMeasure queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdUnitMeasure 筛选条件
     * @return 查询结果
     */
    List<MdUnitMeasure> selectMdUnitMeasureList(MdUnitMeasure mdUnitMeasure);
    /**
     * 新增数据
     *
     * @param mdUnitMeasure 实例对象
     * @return 影响行数
     */
    int insert(MdUnitMeasure mdUnitMeasure);

    /**
     * 批量新增数据
     *
     * @param mdUnitMeasures 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdUnitMeasure> mdUnitMeasures);
    /**
     * 更新数据
     *
     * @param mdUnitMeasure 实例对象
     * @return 影响行数
     */
    int update(MdUnitMeasure mdUnitMeasure);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigInteger id);
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    boolean deleteByIds(BigInteger[] ids);
}