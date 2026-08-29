package com.cuixing.md.service;

import com.cuixing.md.entity.MdWorkgroup;
import com.cuixing.system.api.domain.SysUser;

import java.math.BigInteger;
import java.util.List;

/**
 * 班组表;(md_workgroup)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdWorkgroupService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkgroup queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdWorkgroup 筛选条件
     * @return 查询结果
     */
    List<MdWorkgroup> selectMdWorkgroupList(MdWorkgroup mdWorkgroup);

    /**
     * 批量分配班组成员
     * @param userIds
     * @return
     */
    int allocatedUserByuserIds(Long[] userIds,MdWorkgroup mdWorkgroup);


    /**
     * 新增数据
     *
     * @param mdWorkgroup 实例对象
     * @return 影响行数
     */
    int insert(MdWorkgroup mdWorkgroup);

    /**
     * 批量新增数据
     *
     * @param mdWorkgroups 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdWorkgroup> mdWorkgroups);
    /**
     * 更新数据
     *
     * @param mdWorkgroup 实例对象
     * @return 影响行数
     */
    int update(MdWorkgroup mdWorkgroup);
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