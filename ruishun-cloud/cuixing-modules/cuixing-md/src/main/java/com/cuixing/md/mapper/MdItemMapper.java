package com.cuixing.md.mapper;

import java.util.List;

import com.cuixing.system.api.domain.md.MdMouldItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.system.api.domain.md.MdItem;
import java.math.BigInteger;

/**
 * 物料表;(md_item)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Mapper
public interface MdItemMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdItem queryById(@Param("id") BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mdItem 查询条件
     * @return 对象列表
     */
    List<MdItem> selectMdItemList(MdItem mdItem);
    /**
     * 统计总行数
     *
     * @param mdItem 查询条件
     * @return 总行数
     */
    long count(MdItem mdItem);
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
     * @param entities List<MdItem> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MdItem> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MdItem> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MdItem> entities);
    /**
     * 更新数据
     *
     * @param mdItem 实例对象
     * @return 影响行数
     */
    int update(MdItem mdItem);
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
     * * 通过规格查询物料
     * @param specification
     * @return
     */
    MdItem getItemBySpecification(@Param("specification") String specification);

    /**
     * * 通过工序id查询物料列表
     * @param workstageId
     * @return 结果
     */
    List<MdItem> getItemByWorkstageId(@Param("workstageId") BigInteger workstageId);

    /**
     * * 通过工序名称查询物料列表
     * @param workstageName
     * @return 结果
     */
    List<MdItem> getItemByWorkstageName(@Param("workstageName") String workstageName);
}