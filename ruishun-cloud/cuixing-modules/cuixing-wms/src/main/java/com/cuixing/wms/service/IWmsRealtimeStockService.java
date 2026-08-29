package com.cuixing.wms.service;

import com.cuixing.system.api.domain.mes.MesOuputLog;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;

import java.util.List;

/**
 * 库存Service接口
 *
 * @author ruoyi
 * @date 2024-05-29
 */
public interface IWmsRealtimeStockService
{
    /**
     * 查询库存
     *
     * @param materialStockId 库存主键
     * @return 库存
     */
    public WmsRealtimeStock selectWmsRealtimeStockByMaterialStockId(Long materialStockId);

    /**
     * 查询库存列表
     *
     * @param wmsRealtimeStock 库存
     * @return 库存集合
     */
    public List<WmsRealtimeStock> selectWmsRealtimeStockList(WmsRealtimeStock wmsRealtimeStock);

    /**
     * 新增库存
     *
     * @param wmsRealtimeStock 库存
     * @return 结果
     */
    public int insertWmsRealtimeStock(WmsRealtimeStock wmsRealtimeStock, String type);

    /**
     * 修改库存
     *
     * @param wmsRealtimeStock 库存
     * @return 结果
     */
    public int updateWmsRealtimeStock(WmsRealtimeStock wmsRealtimeStock);

    /**
     * 批量删除库存
     *
     * @param materialStockIds 需要删除的库存主键集合
     * @return 结果
     */
    public int deleteWmsRealtimeStockByMaterialStockIds(String materialStockIds);

    /**
     * 删除库存信息
     *
     * @param materialStockId 库存主键
     * @return 结果
     */
    public int deleteWmsRealtimeStockByMaterialStockId(Long materialStockId);

    WmsRealtimeStock getInfoByItemIdentifier(String itemIdentifier);

    WmsRealtimeStock updateDetectionStatus(String itemIdentifier);

    boolean updateDetectionStatus2(List<String> itemIdentifier,String type,String taskType);

    MesOuputLog WriteBackWight(WmsRealtimeStock wmsRealtimeStock);
}
