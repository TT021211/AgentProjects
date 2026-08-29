package com.cuixing.md.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.md.entity.MdWorkgroupMember;
import java.math.BigInteger;

/**
 * 班组成员表;(md_workgroup_member)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Mapper
public interface MdWorkgroupMemberMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkgroupMember queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mdWorkgroupMember 查询条件
     * @return 对象列表
     */
    List<MdWorkgroupMember> selectMdWorkgroupMemberList(MdWorkgroupMember mdWorkgroupMember);
    /**
     * 统计总行数
     *
     * @param mdWorkgroupMember 查询条件
     * @return 总行数
     */
    long count(MdWorkgroupMember mdWorkgroupMember);
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
     * @param entities List<MdWorkgroupMember> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MdWorkgroupMember> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MdWorkgroupMember> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MdWorkgroupMember> entities);
    /**
     * 更新数据
     *
     * @param mdWorkgroupMember 实例对象
     * @return 影响行数
     */
    int update(MdWorkgroupMember mdWorkgroupMember);
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
}