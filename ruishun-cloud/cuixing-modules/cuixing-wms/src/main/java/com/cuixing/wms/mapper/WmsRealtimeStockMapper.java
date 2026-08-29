package com.cuixing.wms.mapper;

import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存Mapper接口
 *
 * @author ruoyi
 * @date 2024-05-29
 */
@Mapper
public interface WmsRealtimeStockMapper
{
    /**
     * 查询库存
     *
     * @param materialStockId 库存主键
     * @return 库存
     */
    public WmsRealtimeStock selectWmsRealtimeStockByMaterialStockId(Long materialStockId);
    public WmsRealtimeStock selectWmsRealtimeStockByItemIdentifier(@Param("itemIdentifier") String itemIdentifier, @Param("warehouseId") Long warehouseId);

    /**
     * 查询库存列表
     *
     * @param wmsRealtimeStock 库存
     * @return 库存集合
     */
    public List<WmsRealtimeStock> selectWmsRealtimeStockList(WmsRealtimeStock wmsRealtimeStock);

    WmsRealtimeStock selectByItemIdentifier(@Param("itemIdentifier") String itemIdentifier);

    /**
     * 新增库存
     *
     * @param wmsRealtimeStock 库存
     * @return 结果
     */
    public int insertWmsRealtimeStock(WmsRealtimeStock wmsRealtimeStock);

    /**
     * 修改库存
     *
     * @param wmsRealtimeStock 库存
     * @return 结果
     */
    public int updateWmsRealtimeStock(WmsRealtimeStock wmsRealtimeStock);

    /**
     * 删除库存
     *
     * @param materialStockId 库存主键
     * @return 结果
     */
    public int deleteWmsRealtimeStockByMaterialStockId(Long materialStockId);
    public int deleteWmsRealtimeStockByItemIdentifier(String itemIdentifier);

    /**
     * 批量删除库存
     *
     * @param materialStockIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsRealtimeStockByMaterialStockIds(String[] materialStockIds);

    WmsRealtimeStock selectWmMaterialStockByMaterialStockId(Long var1);

    WmsRealtimeStock loadMaterialStock(WmsRealtimeStock wmsRealtimeStock);

    List<WmsRealtimeStock> getInfoByItemIdentifier2(@Param("itemIdentifiers") List<String> itemIdentifiers);

    boolean updateProductStatus(@Param("i")Integer i,@Param("itemIdentifier")String itemIdentifier);
}
