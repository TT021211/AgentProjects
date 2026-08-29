package com.cuixing.md.service;

import com.cuixing.md.entity.MdWorkgroupMember;
import java.math.BigInteger;
import java.util.List;

/**
 * 班组成员表;(md_workgroup_member)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
public interface IMdWorkgroupMemberService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkgroupMember queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdWorkgroupMember 筛选条件
     * @return 查询结果
     */
    List<MdWorkgroupMember> selectMdWorkgroupMemberList(MdWorkgroupMember mdWorkgroupMember);
    /**
     * 新增数据
     *
     * @param mdWorkgroupMember 实例对象
     * @return 影响行数
     */
    int insert(MdWorkgroupMember mdWorkgroupMember);

    /**
     * 批量新增数据
     *
     * @param mdWorkgroupMembers 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MdWorkgroupMember> mdWorkgroupMembers);
    /**
     * 更新数据
     *
     * @param mdWorkgroupMember 实例对象
     * @return 影响行数
     */
    int update(MdWorkgroupMember mdWorkgroupMember);
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