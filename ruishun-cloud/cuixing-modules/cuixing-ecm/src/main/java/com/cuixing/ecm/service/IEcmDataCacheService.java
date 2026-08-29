package com.cuixing.ecm.service;

import java.math.BigInteger;
import com.cuixing.ecm.domain.EcmDataCache;
import java.util.List;

 /**
 * 用电量数据缓存;(ecm_data_cache)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2025-2-13
 */
public interface IEcmDataCacheService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EcmDataCache queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param ecmDataCache 筛选条件
     * @return 查询结果
     */
    List<EcmDataCache> selectEcmDataCacheList(EcmDataCache ecmDataCache);
    /** 
     * 新增数据
     *
     * @param ecmDataCache 实例对象
     * @return 实例对象
     */
    int insert(EcmDataCache ecmDataCache);
    /** 
     * 更新数据
     *
     * @param ecmDataCache 实例对象
     * @return 实例对象
     */
    int update(EcmDataCache ecmDataCache);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}