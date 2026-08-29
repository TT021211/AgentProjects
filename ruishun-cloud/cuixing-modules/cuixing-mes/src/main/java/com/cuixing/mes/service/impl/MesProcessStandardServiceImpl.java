package com.cuixing.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.mes.entity.MesProcessStandard;
import com.cuixing.mes.mapper.MesProcessStandardMapper;
import com.cuixing.mes.service.IMesProcessStandardService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 工艺标准;(mes_process_standard)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-7-23
 */
@Service
public class MesProcessStandardServiceImpl implements IMesProcessStandardService{
    @Autowired
    private MesProcessStandardMapper mesProcessStandardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MesProcessStandard queryById(BigInteger id){
        return mesProcessStandardMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mesProcessStandard 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesProcessStandard> selectMesProcessStandardList(MesProcessStandard mesProcessStandard){
       return mesProcessStandardMapper.selectMesProcessStandardList(mesProcessStandard);
    }
    
    /** 
     * 新增数据
     *
     * @param mesProcessStandard 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MesProcessStandard mesProcessStandard){
    	mesProcessStandard.setCreateTime(DateUtils.getNowDate());
		mesProcessStandard.setCreateBy(SecurityUtils.getUsername());
        return mesProcessStandardMapper.insert(mesProcessStandard);
    }
    
    /** 
     * 更新数据
     *
     * @param mesProcessStandard 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MesProcessStandard mesProcessStandard){
        mesProcessStandard.setUpdateTime(DateUtils.getNowDate());
		mesProcessStandard.setUpdateBy(SecurityUtils.getUsername());
        return mesProcessStandardMapper.update(mesProcessStandard);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mesProcessStandardMapper.deleteByIds(ids);
    }

     @Override
     public List<MesProcessStandard> getMesProcessStandardList(String standardCode) {
         return mesProcessStandardMapper.getMesProcessStandardList(standardCode);
     }
 }