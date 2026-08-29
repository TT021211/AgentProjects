package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.md.entity.MdUnitMeasure;
import com.cuixing.md.service.IMdUnitMeasureService;
import com.cuixing.system.api.RemoteSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdAccessory;
import com.cuixing.md.mapper.MdAccessoryMapper;
import com.cuixing.md.service.IMdAccessoryService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 辅料列表;(md_accessory)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Service
public class MdAccessoryServiceImpl implements IMdAccessoryService{
    @Autowired
    private MdAccessoryMapper mdAccessoryMapper;

    @Autowired
    private IMdUnitMeasureService iMdUnitMeasureService;

    @Autowired
    private RemoteSystemService remoteSystemService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdAccessory queryById(BigInteger id){
        return mdAccessoryMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mdAccessory 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdAccessory> selectMdAccessoryList(MdAccessory mdAccessory){
       return mdAccessoryMapper.selectMdAccessoryList(mdAccessory);
    }
    
    /** 
     * 新增数据
     *
     * @param mdAccessory 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdAccessory mdAccessory){
        if (mdAccessory.getMeasureId() != null){
            MdUnitMeasure mdUnitMeasure = iMdUnitMeasureService.queryById(mdAccessory.getMeasureId());
            mdAccessory.setMeasureName(mdUnitMeasure.getMeasureName());
        }
        String code = remoteSystemService.getAutoCode("ACCESSORY_LIST_CODE", SecurityConstants.INNER);
        mdAccessory.setAttr1(code);
        mdAccessory.setCreateTime(DateUtils.getNowDate());
		mdAccessory.setCreateBy(SecurityUtils.getUsername());
        return mdAccessoryMapper.insert(mdAccessory);
    }
    
    /** 
     * 更新数据
     *
     * @param mdAccessory 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdAccessory mdAccessory){
        if (mdAccessory.getMeasureId() != null){
            MdUnitMeasure mdUnitMeasure = iMdUnitMeasureService.queryById(mdAccessory.getMeasureId());
            mdAccessory.setMeasureName(mdUnitMeasure.getMeasureName());
        }
        mdAccessory.setUpdateTime(DateUtils.getNowDate());
		mdAccessory.setUpdateBy(SecurityUtils.getUsername());
        return mdAccessoryMapper.update(mdAccessory);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdAccessoryMapper.deleteByIds(ids);
    }
}