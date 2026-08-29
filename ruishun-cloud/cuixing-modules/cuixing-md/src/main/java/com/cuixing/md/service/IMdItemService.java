package com.cuixing.md.service;

import com.cuixing.system.api.domain.md.MdItem;
import java.math.BigInteger;
import java.util.List;

/**
 * 物料表;(md_item)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdItemService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdItem queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdItem 筛选条件
     * @return 查询结果
     */
    List<MdItem> selectMdItemList(MdItem mdItem);
    /**
     * 新增数据
     *
     * @param mdItem 实例对象
     * @return 影响行数
     */
    int insert(MdItem mdItem);

    /**
     * 批量新增数据
     *
     * @param mdItems 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdItem> mdItems);
    /**
     * 更新数据
     *
     * @param mdItem 实例对象
     * @return 影响行数
     */
    int update(MdItem mdItem);
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


    MdItem getItemBySpecification(String specification);

    List<MdItem> getItemByWorkstageId(BigInteger workstageId);

    List<MdItem> getItemByWorkstageName(String workstageName);
}