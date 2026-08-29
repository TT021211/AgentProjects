package com.cuixing.md.service;

import com.cuixing.system.api.domain.md.MdClient;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * 客户表;(md_client)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdClientService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdClient queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdClient 筛选条件
     * @return 查询结果
     */
    List<MdClient> selectMdClientList(MdClient mdClient);
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
     * @param mdClients 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdClient> mdClients);
    /**
     * 更新数据
     *
     * @param mdClient 实例对象
     * @return 影响行数
     */
    int update(MdClient mdClient);
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

    List<MdClient> getInfoByClientNick(Set<String> clientNick);
}