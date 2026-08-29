package com.cuixing.md.service;

import com.cuixing.md.entity.MdWorkshop;
import java.math.BigInteger;
import java.util.List;

/**
 * 车间表;(md_workshop)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdWorkshopService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkshop queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdWorkshop 筛选条件
     * @return 查询结果
     */
    List<MdWorkshop> selectMdWorkshopList(MdWorkshop mdWorkshop);
    /**
     * 新增数据
     *
     * @param mdWorkshop 实例对象
     * @return 影响行数
     */
    int insert(MdWorkshop mdWorkshop);

    /**
     * 批量新增数据
     *
     * @param mdWorkshops 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdWorkshop> mdWorkshops);
    /**
     * 更新数据
     *
     * @param mdWorkshop 实例对象
     * @return 影响行数
     */
    int update(MdWorkshop mdWorkshop);
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