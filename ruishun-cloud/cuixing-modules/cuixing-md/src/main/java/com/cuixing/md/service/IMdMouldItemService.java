package com.cuixing.md.service;

import com.cuixing.system.api.domain.md.MdMouldItem;
import java.math.BigInteger;
import java.util.List;

/**
 * 模具产出物料关联表;(md_mould_item)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-5-21
 */
public interface IMdMouldItemService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdMouldItem queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdMouldItem 筛选条件
     * @return 查询结果
     */
    List<MdMouldItem> selectMdMouldItemList(MdMouldItem mdMouldItem);
    /**
     * 新增数据
     *
     * @param mdMouldItem 实例对象
     * @return 影响行数
     */
    BigInteger insert(MdMouldItem mdMouldItem);

    /**
     * 批量新增数据
     *
     * @param mdMouldItems 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdMouldItem> mdMouldItems);
    /**
     * 更新数据
     *
     * @param mdMouldItem 实例对象
     * @return 影响行数
     */
    int update(MdMouldItem mdMouldItem);
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