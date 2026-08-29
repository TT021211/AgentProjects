package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.system.api.RemoteSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdPlantList;
import com.cuixing.md.mapper.MdPlantListMapper;
import com.cuixing.md.service.IMdPlantListService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 油漆清单;(md_plant_list)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-8-8
 */
@Service
public class MdPlantListServiceImpl implements IMdPlantListService{
    @Autowired
    private MdPlantListMapper mdPlantListMapper;


    @Autowired
    private RemoteSystemService remoteSystemService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdPlantList queryById(BigInteger id){
        return mdPlantListMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mdPlantList 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdPlantList> selectMdPlantListList(MdPlantList mdPlantList){
       return mdPlantListMapper.selectMdPlantListList(mdPlantList);
    }
    
    /** 
     * 新增数据
     *
     * @param mdPlantList 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdPlantList mdPlantList){
    	mdPlantList.setCreateTime(DateUtils.getNowDate());
		mdPlantList.setCreateBy(SecurityUtils.getUsername());
        String code = remoteSystemService.getAutoCode("PLANT_LIST_CODE", SecurityConstants.INNER);
        mdPlantList.setPlantCode(code);
        return mdPlantListMapper.insert(mdPlantList);
    }
    
    /** 
     * 更新数据
     *
     * @param mdPlantList 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdPlantList mdPlantList){
        mdPlantList.setUpdateTime(DateUtils.getNowDate());
		mdPlantList.setUpdateBy(SecurityUtils.getUsername());
        return mdPlantListMapper.update(mdPlantList);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdPlantListMapper.deleteByIds(ids);
    }
}