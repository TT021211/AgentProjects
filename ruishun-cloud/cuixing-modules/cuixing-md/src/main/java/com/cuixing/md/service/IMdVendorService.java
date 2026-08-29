package com.cuixing.md.service;

import com.cuixing.md.entity.MdVendor;
import java.math.BigInteger;
import java.util.List;

/**
 * 供应商表;(md_vendor)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdVendorService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdVendor queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdVendor 筛选条件
     * @return 查询结果
     */
    List<MdVendor> selectMdVendorList(MdVendor mdVendor);
    /**
     * 新增数据
     *
     * @param mdVendor 实例对象
     * @return 影响行数
     */
    int insert(MdVendor mdVendor);

    /**
     * 批量新增数据
     *
     * @param mdVendors 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdVendor> mdVendors);
    /**
     * 更新数据
     *
     * @param mdVendor 实例对象
     * @return 影响行数
     */
    int update(MdVendor mdVendor);
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