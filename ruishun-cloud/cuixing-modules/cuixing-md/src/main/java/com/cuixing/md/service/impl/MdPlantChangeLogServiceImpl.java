package com.cuixing.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdPlantChangeLog;
import com.cuixing.md.mapper.MdPlantChangeLogMapper;
import com.cuixing.md.service.IMdPlantChangeLogService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 油漆更换记录;(md_plant_change_log)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@Service
public class MdPlantChangeLogServiceImpl implements IMdPlantChangeLogService{
    @Autowired
    private MdPlantChangeLogMapper mdPlantChangeLogMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdPlantChangeLog queryById(BigInteger id){
        return mdPlantChangeLogMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mdPlantChangeLog 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdPlantChangeLog> selectMdPlantChangeLogList(MdPlantChangeLog mdPlantChangeLog){
       return mdPlantChangeLogMapper.selectMdPlantChangeLogList(mdPlantChangeLog);
    }
    
    /** 
     * 新增数据
     *
     * @param mdPlantChangeLog 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdPlantChangeLog mdPlantChangeLog){
    	mdPlantChangeLog.setCreateTime(DateUtils.getNowDate());
		mdPlantChangeLog.setCreateBy(SecurityUtils.getUsername());
        return mdPlantChangeLogMapper.insert(mdPlantChangeLog);
    }
    
    /** 
     * 更新数据
     *
     * @param mdPlantChangeLog 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdPlantChangeLog mdPlantChangeLog){
        mdPlantChangeLog.setUpdateTime(DateUtils.getNowDate());
		mdPlantChangeLog.setUpdateBy(SecurityUtils.getUsername());
        return mdPlantChangeLogMapper.update(mdPlantChangeLog);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdPlantChangeLogMapper.deleteByIds(ids);
    }
}