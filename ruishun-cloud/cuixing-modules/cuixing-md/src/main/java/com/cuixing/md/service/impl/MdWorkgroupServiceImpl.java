package com.cuixing.md.service.impl;

import com.cuixing.common.core.domain.R;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.md.entity.MdWorkgroupMember;
import com.cuixing.md.service.IMdWorkgroupMemberService;
import com.cuixing.system.api.RemoteUserService;
import com.cuixing.system.api.domain.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.md.entity.MdWorkgroup;
import com.cuixing.md.mapper.MdWorkgroupMapper;
import com.cuixing.md.service.IMdWorkgroupService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * 班组表;(md_workgroup)表服务实现类
 *
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdWorkgroupServiceImpl implements IMdWorkgroupService {

	@Autowired
	private MdWorkgroupMapper mdWorkgroupMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private IMdWorkgroupMemberService mdWorkgroupMemberService;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	public MdWorkgroup queryById(BigInteger id) {
		return mdWorkgroupMapper.queryById(id);
	}

	/**
	 * 分页查询
	 *
	 * @param mdWorkgroup 筛选条件
	 * @return 查询结果
	 */
	public List<MdWorkgroup> selectMdWorkgroupList(MdWorkgroup mdWorkgroup) {
		return mdWorkgroupMapper.selectMdWorkgroupList(mdWorkgroup);
	}

	@Override
	@Transactional
	public int allocatedUserByuserIds(Long[] userIds, MdWorkgroup mdWorkgroup) {

		R<List<SysUser>> userList = remoteUserService.getUserByIds(userIds);
		userList.getData().forEach(item -> {
			MdWorkgroupMember mdWorkgroupMember = new MdWorkgroupMember();
			BeanUtils.copyProperties(mdWorkgroup, mdWorkgroupMember);
			mdWorkgroupMember.setId(null);
			mdWorkgroupMember.setWorkgroupId(mdWorkgroup.getId());
			mdWorkgroupMember.setMemberId(BigInteger.valueOf(item.getUserId()));
			mdWorkgroupMember.setMemberName(item.getUserName());

			mdWorkgroupMemberService.insert(mdWorkgroupMember);
		});
		mdWorkgroup.setTotalMembers(mdWorkgroup.getTotalMembers() + userIds.length);
		mdWorkgroup.setVersion(mdWorkgroup.getVersion() - 1);
		mdWorkgroupMapper.update(mdWorkgroup);
		return userIds.length;

	}

	/**
	 * 新增数据
	 *
	 * @param mdWorkgroup 实例对象
	 * @return 影响行数
	 */
	public int insert(MdWorkgroup mdWorkgroup) {
		mdWorkgroup.setCreateTime(DateUtils.getNowDate());
		mdWorkgroup.setCreateBy(SecurityUtils.getUsername());
		return mdWorkgroupMapper.insert(mdWorkgroup);
	}

	/**
	 * 批量新增数据
	 *
	 * @param mdWorkgroups 实例对象数组
	 * @return 影响行数
	 */
	public int insertBatch(List<MdWorkgroup> mdWorkgroups) {
		int rs = 0;
		for (MdWorkgroup mdWorkgroup : mdWorkgroups) {
			mdWorkgroup.setCreateTime(DateUtils.getNowDate());
			mdWorkgroup.setCreateBy(SecurityUtils.getUsername());
			rs = mdWorkgroupMapper.insert(mdWorkgroup) + 1;
		}
		return rs;
	}

	/**
	 * 更新数据
	 *
	 * @param mdWorkgroup 实例对象
	 * @return 影响行数
	 */
	public int update(MdWorkgroup mdWorkgroup) {
		BigInteger id = mdWorkgroup.getId();
		MdWorkgroup theUpdatemdWorkgroup = mdWorkgroupMapper.queryById(id);
		Integer version = theUpdatemdWorkgroup.getVersion();
		if (version != null) {
			mdWorkgroup.setVersion(version);
		}
		mdWorkgroup.setUpdateTime(DateUtils.getNowDate());
		mdWorkgroup.setUpdateBy(SecurityUtils.getUsername());
		return mdWorkgroupMapper.update(mdWorkgroup);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	public boolean deleteById(BigInteger id) {
		int total = mdWorkgroupMapper.deleteById(id);
		return total > 0;
	}

	/**
	 * 通过主键批量删除数据
	 *
	 * @param ids 主键数组
	 * @return 是否成功
	 */
	public boolean deleteByIds(BigInteger[] ids) {

		int total = mdWorkgroupMapper.deleteByIds(ids);
		return total > 0;
	}

}