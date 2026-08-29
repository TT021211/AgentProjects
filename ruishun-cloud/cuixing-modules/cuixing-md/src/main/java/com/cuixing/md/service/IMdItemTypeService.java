package com.cuixing.md.service;

import com.cuixing.md.entity.MdItemType;
import java.math.BigInteger;
import java.util.List;

/**
 * 物料分类表;(md_item_type)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdItemTypeService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdItemType queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdItemType 筛选条件
     * @return 查询结果
     */
    List<MdItemType> selectMdItemTypeList(MdItemType mdItemType);
    /**
     * 新增数据
     *
     * @param mdItemType 实例对象
     * @return 影响行数
     */
    int insert(MdItemType mdItemType);

    /**
     * 批量新增数据
     *
     * @param mdItemTypes 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdItemType> mdItemTypes);
    /**
     * 更新数据
     *
     * @param mdItemType 实例对象
     * @return 影响行数
     */
    int update(MdItemType mdItemType);
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

    /**
     * 判断是否有子节点
     * @param id
     * @return
     */
    Boolean checkHasChildren(BigInteger[] id);
    Boolean checkHasChildren(BigInteger id);
}