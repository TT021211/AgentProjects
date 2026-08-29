package com.cuixing.md.service;

import com.cuixing.md.entity.MdWorkstation;
import java.math.BigInteger;
import java.util.List;

/**
 * 机台表;(md_workstation)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdWorkstationService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkstation queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdWorkstation 筛选条件
     * @return 查询结果
     */
    List<MdWorkstation> selectMdWorkstationList(MdWorkstation mdWorkstation);
    /**
     * 新增数据
     *
     * @param mdWorkstation 实例对象
     * @return 影响行数
     */
    int insert(MdWorkstation mdWorkstation);

    /**
     * 批量新增数据
     *
     * @param mdWorkstations 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdWorkstation> mdWorkstations);
    /**
     * 更新数据
     *
     * @param mdWorkstation 实例对象
     * @return 影响行数
     */
    int update(MdWorkstation mdWorkstation);
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