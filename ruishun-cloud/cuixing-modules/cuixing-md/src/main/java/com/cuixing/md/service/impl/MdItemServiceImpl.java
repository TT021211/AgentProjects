package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.md.entity.MdUnitMeasure;
import com.cuixing.md.mapper.MdUnitMeasureMapper;
import com.cuixing.system.api.RemoteSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.system.api.domain.md.MdItem;
import com.cuixing.md.mapper.MdItemMapper;
import com.cuixing.md.service.IMdItemService;

import java.math.BigInteger;
import java.util.List;

/**
 * 物料表;(md_item)表服务实现类
 *
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdItemServiceImpl implements IMdItemService {

	@Autowired
	private MdItemMapper mdItemMapper;

	@Autowired
	private MdUnitMeasureMapper mdUnitMeasureMapper;

	@Autowired
	private RemoteSystemService remoteSystemService;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	public MdItem queryById(BigInteger id) {
		return mdItemMapper.queryById(id);
	}

	/**
	 * 分页查询
	 *
	 * @param mdItem 筛选条件
	 * @return 查询结果
	 */
	public List<MdItem> selectMdItemList(MdItem mdItem) {
		return mdItemMapper.selectMdItemList(mdItem);
	}

	/**
	 * 新增数据
	 *
	 * @param mdItem 实例对象
	 * @return 影响行数
	 */
	public int insert(MdItem mdItem) {
		mdItem.setCreateTime(DateUtils.getNowDate());
		mdItem.setCreateBy(SecurityUtils.getUsername());
		if(mdItem.getWeightMeasureId()!=null)
		{
			MdUnitMeasure weightUnitMeasure = mdUnitMeasureMapper.queryById(mdItem.getWeightMeasureId());
			mdItem.setWeightMeasureName(weightUnitMeasure.getMeasureName());
		}else if(mdItem.getNumMeasureId()!=null)
		{
			MdUnitMeasure numUnitMeasure = mdUnitMeasureMapper.queryById(mdItem.getNumMeasureId());
			mdItem.setNumMeasureName(numUnitMeasure.getMeasureName());
		}else if(mdItem.getWeightMeasureId()==null && mdItem.getNumMeasureId()==null)
		{
			throw new RuntimeException("请选择单位名称");
		}
		String code = remoteSystemService.getAutoCode("ITEM_LIST_CODE", SecurityConstants.INNER);
		mdItem.setItemCode(code);

		return mdItemMapper.insert(mdItem);
	}

	/**
	 * 批量新增数据
	 *
	 * @param mdItems 实例对象数组
	 * @return 影响行数
	 */
	public int insertBatch(List<MdItem> mdItems) {
		int rs = 0;
		for (MdItem mdItem : mdItems) {
			mdItem.setCreateTime(DateUtils.getNowDate());
			mdItem.setCreateBy(SecurityUtils.getUsername());
			if(mdItem.getWeightMeasureId()!=null)
			{
				MdUnitMeasure weightUnitMeasure = mdUnitMeasureMapper.queryById(mdItem.getWeightMeasureId());
				mdItem.setWeightMeasureName(weightUnitMeasure.getMeasureName());
			}else if(mdItem.getNumMeasureId()!=null)
			{
				MdUnitMeasure numUnitMeasure = mdUnitMeasureMapper.queryById(mdItem.getNumMeasureId());
				mdItem.setNumMeasureName(numUnitMeasure.getMeasureName());
			}else if(mdItem.getWeightMeasureId()==null && mdItem.getNumMeasureId()==null)
			{
				throw new RuntimeException("请选择单位名称");
			}
			rs = mdItemMapper.insert(mdItem) + 1;
		}
		return rs;
	}

	/**
	 * 更新数据
	 *
	 * @param mdItem 实例对象
	 * @return 影响行数
	 */
	public int update(MdItem mdItem) {
		BigInteger id = mdItem.getId();
		MdItem theUpdatemdItem = mdItemMapper.queryById(id);

		Integer version = theUpdatemdItem.getVersion();
		if (version != null) {
			mdItem.setVersion(version);
		}
		MdUnitMeasure weightUnitMeasure = mdUnitMeasureMapper.queryById(mdItem.getWeightMeasureId());
		mdItem.setWeightMeasureName(weightUnitMeasure.getMeasureName());
		mdItem.setUpdateTime(DateUtils.getNowDate());
		mdItem.setUpdateBy(SecurityUtils.getUsername());
		return mdItemMapper.update(mdItem);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	public boolean deleteById(BigInteger id) {
		int total = mdItemMapper.deleteById(id);
		return total > 0;
	}

	/**
	 * 通过主键批量删除数据
	 *
	 * @param ids 主键数组
	 * @return 是否成功
	 */
	public boolean deleteByIds(BigInteger[] ids) {
		int total = mdItemMapper.deleteByIds(ids);
		return total > 0;
	}

	/**
	 * *通过规格查询对应物料
	 * @param specification
	 * @return
	 */
	@Override
	public MdItem getItemBySpecification(String specification) {
		return mdItemMapper.getItemBySpecification(specification);
	}

	/**
	 * 通过工序id查询物料列表
	 * @param workstageId
	 * @return
	 */
	@Override
	public List<MdItem> getItemByWorkstageId(BigInteger workstageId) {
		return mdItemMapper.getItemByWorkstageId(workstageId);
	}

	@Override
	public List<MdItem> getItemByWorkstageName(String workstageName) {
		return mdItemMapper.getItemByWorkstageName(workstageName);
	}
}