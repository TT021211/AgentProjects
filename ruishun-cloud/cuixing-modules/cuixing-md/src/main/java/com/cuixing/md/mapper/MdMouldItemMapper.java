package com.cuixing.md.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.system.api.domain.md.MdMouldItem;
import com.cuixing.system.api.domain.md.MdItem;
import java.math.BigInteger;

/**
 * 模具产出物料关联表;(md_mould_item)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-5-21
 */
@Mapper
public interface MdMouldItemMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdMouldItem queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mdMouldItem 查询条件
     * @return 对象列表
     */
    List<MdMouldItem> selectMdMouldItemList(MdMouldItem mdMouldItem);
    /**
     * 统计总行数
     *
     * @param mdMouldItem 查询条件
     * @return 总行数
     */
    long count(MdMouldItem mdMouldItem);
    /**
     * 新增数据
     *
     * @param mdMouldItem 实例对象
     * @return 影响行数
     */
    int insert(MdMouldItem mdMouldItem);
    /**
     * 批量新增数据
     *
     * @param entities List<MdMouldItem> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MdMouldItem> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MdMouldItem> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MdMouldItem> entities);
    /**
     * 更新数据
     *
     * @param mdMouldItem 实例对象
     * @return 影响行数
     */
    int update(MdMouldItem mdMouldItem);
    /**
     * 通过主键逻辑删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(BigInteger id);
    /**
     * 通过主键批量逻辑删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
    /**
     * 通过主键物理删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int removeById(BigInteger id);

    /**
     * 通过模具id取物料数据
     *
     * @param mouldId 主键
     * @return 对象列表
     */
    List<MdItem> selectItemListByMouldId(Integer mouldId);

}