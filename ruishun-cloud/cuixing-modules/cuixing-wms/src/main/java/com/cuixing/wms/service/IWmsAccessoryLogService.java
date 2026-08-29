package com.cuixing.wms.service;

import java.math.BigInteger;
import com.cuixing.wms.entity.WmsAccessoryLog;
import java.util.List;

 /**
 * 辅料出入库记录;(wms_accessory_log)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-28
 */
public interface IWmsAccessoryLogService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsAccessoryLog queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param wmsAccessoryLog 筛选条件
     * @return 查询结果
     */
    List<WmsAccessoryLog> selectWmsAccessoryLogList(WmsAccessoryLog wmsAccessoryLog);
    /** 
     * 新增数据
     *
     * @param wmsAccessoryLog 实例对象
     * @return 实例对象
     */
    int insert(WmsAccessoryLog wmsAccessoryLog);
    /** 
     * 更新数据
     *
     * @param wmsAccessoryLog 实例对象
     * @return 实例对象
     */
    int update(WmsAccessoryLog wmsAccessoryLog);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}