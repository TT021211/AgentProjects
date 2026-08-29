package com.cuixing.ecm.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.ecm.domain.EcmDataCache;

 /**
 * 用电量数据缓存;(ecm_data_cache)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2025-2-13
 */
@Mapper
public interface EcmDataCacheMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EcmDataCache queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param ecmDataCache 查询条件
     * @return 对象列表
     */
    List<EcmDataCache> selectEcmDataCacheList(EcmDataCache ecmDataCache);
    /** 
     * 新增数据
     *
     * @param ecmDataCache 实例对象
     * @return 影响行数
     */
    int insert(EcmDataCache ecmDataCache);
    
    /** 
     * 更新数据
     *
     * @param ecmDataCache 实例对象
     * @return 影响行数
     */
    int update(EcmDataCache ecmDataCache);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}