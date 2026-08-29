package com.cuixing.wms.mapper;

import com.cuixing.wms.entity.WmsIngredientsLog;
import com.cuixing.wms.entity.vo.WmsIngredientsLog12Vo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


/**
 * 原料出入库日志;(wms_Ingredients_log)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Mapper
public interface WmsIngredientsLogMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsIngredientsLog queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param wmsIngredientsLog 查询条件
     * @return 对象列表
     */
    List<WmsIngredientsLog> selectWmsIngredientsLogList(WmsIngredientsLog wmsIngredientsLog);
    /** 
     * 新增数据
     *
     * @param wmsIngredientsLog 实例对象
     * @return 影响行数
     */
    int insert(WmsIngredientsLog wmsIngredientsLog);
    
    /** 
     * 更新数据
     *
     * @param wmsIngredientsLog 实例对象
     * @return 影响行数
     */
    int update(WmsIngredientsLog wmsIngredientsLog);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

    WmsIngredientsLog queryByCodeSn(@Param("codeSn") String codeSn);

    BigDecimal getCountIngredientsLogWeight(@Param("status") String status);

    List<WmsIngredientsLog12Vo> getCountBoxDataToYear();

    List<WmsIngredientsLog> selectMaxBatch(String manufacturerName);
}