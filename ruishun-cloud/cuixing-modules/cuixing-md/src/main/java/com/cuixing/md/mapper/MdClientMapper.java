package com.cuixing.md.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.system.api.domain.md.MdClient;
import java.math.BigInteger;
import java.util.Set;

/**
 * 客户表;(md_client)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Mapper
public interface MdClientMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdClient queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mdClient 查询条件
     * @return 对象列表
     */
    List<MdClient> selectMdClientList(MdClient mdClient);
    /**
     * 统计总行数
     *
     * @param mdClient 查询条件
     * @return 总行数
     */
    long count(MdClient mdClient);
    /**
     * 新增数据
     *
     * @param mdClient 实例对象
     * @return 影响行数
     */
    int insert(MdClient mdClient);
    /**
     * 批量新增数据
     *
     * @param entities List<MdClient> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MdClient> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MdClient> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MdClient> entities);
    /**
     * 更新数据
     *
     * @param mdClient 实例对象
     * @return 影响行数
     */
    int update(MdClient mdClient);
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

    List<MdClient> getInfoByClientNick(@Param("clientNick") Set<String> clientNick);
}