package com.cuixing.wms.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.wms.entity.WmsAccessoryLog;

 /**
 * 辅料出入库记录;(wms_accessory_log)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-28
 */
@Mapper
public interface WmsAccessoryLogMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsAccessoryLog queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param wmsAccessoryLog 查询条件
     * @return 对象列表
     */
    List<WmsAccessoryLog> selectWmsAccessoryLogList(WmsAccessoryLog wmsAccessoryLog);
    /** 
     * 新增数据
     *
     * @param wmsAccessoryLog 实例对象
     * @return 影响行数
     */
    int insert(WmsAccessoryLog wmsAccessoryLog);
    
    /** 
     * 更新数据
     *
     * @param wmsAccessoryLog 实例对象
     * @return 影响行数
     */
    int update(WmsAccessoryLog wmsAccessoryLog);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}