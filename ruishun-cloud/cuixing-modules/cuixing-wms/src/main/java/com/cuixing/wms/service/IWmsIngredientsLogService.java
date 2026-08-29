package com.cuixing.wms.service;



import com.cuixing.wms.entity.WmsIngredientsLog;
import com.cuixing.wms.entity.vo.WmsIngredientsLogVo;

import java.math.BigInteger;
import java.util.List;

/**
 * 原料出入库日志;(wms_Ingredients_log)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
public interface IWmsIngredientsLogService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsIngredientsLog queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param wmsIngredientsLog 筛选条件
     * @return 查询结果
     */
    List<WmsIngredientsLog> selectWmsIngredientsLogList(WmsIngredientsLog wmsIngredientsLog);

     /**
      * 分页查询（获取计算重量）
      *
      * @param wmsIngredientsLog 筛选条件
      * @return 查询结果
      */
     WmsIngredientsLogVo selectWmsIngredientsLogListSum(WmsIngredientsLog wmsIngredientsLog);
    /** 
     * 新增数据
     *
     * @param wmsIngredientsLog 实例对象
     * @return 实例对象
     */
    WmsIngredientsLog insert(WmsIngredientsLog wmsIngredientsLog);
    /** 
     * 更新数据
     *
     * @param wmsIngredientsLog 实例对象
     * @return 实例对象
     */
    int update(WmsIngredientsLog wmsIngredientsLog);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

    WmsIngredientsLog queryByCodeSn(String codeSn);

    int mistakeByIds(BigInteger[] ids);

    List<WmsIngredientsLog> selectMaxBatch(String manufacturerName);
}