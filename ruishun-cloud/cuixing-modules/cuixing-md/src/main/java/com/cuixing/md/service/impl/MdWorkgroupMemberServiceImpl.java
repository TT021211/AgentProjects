package com.cuixing.md.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.md.entity.MdWorkgroup;
import com.cuixing.md.mapper.MdWorkgroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.md.entity.MdWorkgroupMember;
import com.cuixing.md.mapper.MdWorkgroupMemberMapper;
import com.cuixing.md.service.IMdWorkgroupMemberService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * 班组成员表;(md_workgroup_member)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdWorkgroupMemberServiceImpl implements IMdWorkgroupMemberService{
    @Autowired
    private MdWorkgroupMemberMapper mdWorkgroupMemberMapper;
    @Autowired
    private MdWorkgroupMapper mdWorkgroupMapper;




    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MdWorkgroupMember queryById(BigInteger id){
        return mdWorkgroupMemberMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdWorkgroupMember 筛选条件
     * @return 查询结果
     */
    public List<MdWorkgroupMember> selectMdWorkgroupMemberList(MdWorkgroupMember mdWorkgroupMember)
    {
        return mdWorkgroupMemberMapper.selectMdWorkgroupMemberList(mdWorkgroupMember);
    }

    /**
     * 新增数据
     *
     * @param mdWorkgroupMember 实例对象
     * @return 影响行数
     */
    public int insert(MdWorkgroupMember mdWorkgroupMember){
        mdWorkgroupMember.setCreateTime(DateUtils.getNowDate());
        mdWorkgroupMember.setCreateBy(SecurityUtils.getUsername());
        return mdWorkgroupMemberMapper.insert(mdWorkgroupMember);
    }

    /**
     * 批量新增数据
     *
     * @param mdWorkgroupMembers 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MdWorkgroupMember> mdWorkgroupMembers){
        int rs = 0;
        for (MdWorkgroupMember mdWorkgroupMember : mdWorkgroupMembers) {
            mdWorkgroupMember.setCreateTime(DateUtils.getNowDate());
            mdWorkgroupMember.setCreateBy(SecurityUtils.getUsername());
            rs = mdWorkgroupMemberMapper.insert(mdWorkgroupMember) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mdWorkgroupMember 实例对象
     * @return 影响行数
     */
    public int update(MdWorkgroupMember mdWorkgroupMember){
        BigInteger id = mdWorkgroupMember.getId();
        MdWorkgroupMember theUpdatemdWorkgroupMember = mdWorkgroupMemberMapper.queryById(id);
        Integer version = theUpdatemdWorkgroupMember.getVersion();
        if(version != null) {
            mdWorkgroupMember.setVersion(version);
        }
        mdWorkgroupMember.setUpdateTime(DateUtils.getNowDate());
        mdWorkgroupMember.setUpdateBy(SecurityUtils.getUsername());
        return mdWorkgroupMemberMapper.update(mdWorkgroupMember);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){

        MdWorkgroupMember mdWorkgroupMember = mdWorkgroupMemberMapper.queryById(id);

        MdWorkgroup mdWorkgroup = mdWorkgroupMapper.queryById(mdWorkgroupMember.getWorkgroupId());

        int total = mdWorkgroupMemberMapper.deleteById(id);

        mdWorkgroup.setTotalMembers(mdWorkgroup.getTotalMembers()-total);

        mdWorkgroupMapper.update(mdWorkgroup);

        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    @Transactional
    public boolean deleteByIds(BigInteger[] ids){

        MdWorkgroupMember mdWorkgroupMember = mdWorkgroupMemberMapper.queryById(ids[0]);
        MdWorkgroup mdWorkgroup = mdWorkgroupMapper.queryById(mdWorkgroupMember.getWorkgroupId());

        mdWorkgroup.setTotalMembers(mdWorkgroup.getTotalMembers()-ids.length);
        mdWorkgroupMapper.update(mdWorkgroup);
        int total = mdWorkgroupMemberMapper.deleteByIds(ids);



        return total > 0;
    }
}