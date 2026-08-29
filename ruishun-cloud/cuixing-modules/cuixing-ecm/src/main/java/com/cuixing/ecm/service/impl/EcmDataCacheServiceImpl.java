package com.cuixing.ecm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.ecm.domain.EcmDataCache;
import com.cuixing.ecm.mapper.EcmDataCacheMapper;
import com.cuixing.ecm.service.IEcmDataCacheService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 用电量数据缓存;(ecm_data_cache)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2025-2-13
 */
@Service
public class EcmDataCacheServiceImpl implements IEcmDataCacheService{
    @Autowired
    private EcmDataCacheMapper ecmDataCacheMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EcmDataCache queryById(BigInteger id){
        return ecmDataCacheMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param ecmDataCache 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EcmDataCache> selectEcmDataCacheList(EcmDataCache ecmDataCache){
       return ecmDataCacheMapper.selectEcmDataCacheList(ecmDataCache);
    }
    
    /** 
     * 新增数据
     *
     * @param ecmDataCache 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EcmDataCache ecmDataCache){
    	ecmDataCache.setCreateTime(DateUtils.getNowDate());
		ecmDataCache.setCreateBy(SecurityUtils.getUsername());
        return ecmDataCacheMapper.insert(ecmDataCache);
    }
    
    /** 
     * 更新数据
     *
     * @param ecmDataCache 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EcmDataCache ecmDataCache){
        ecmDataCache.setUpdateTime(DateUtils.getNowDate());
		ecmDataCache.setUpdateBy(SecurityUtils.getUsername());
        return ecmDataCacheMapper.update(ecmDataCache);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return ecmDataCacheMapper.deleteByIds(ids);
    }
}