package com.cuixing.wms.service;

import com.cuixing.wms.entity.WmsOutboundOrder;

import java.util.List;


/**
 * 成品出库单(WmsOutboundOrder)表服务接口
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
public interface WmsOutboundOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsOutboundOrder queryById(Long id);

    /**
     * 分页查询
     *
     * @param wmsOutboundOrder 筛选条件
     * @return 查询结果
     */
    List<WmsOutboundOrder> selectWmsOutboundOrderList(WmsOutboundOrder wmsOutboundOrder);

    /**
     * 新增数据
     *
     * @param wmsOutboundOrder 实例对象
     * @return 实例对象
     */
    WmsOutboundOrder insert(WmsOutboundOrder wmsOutboundOrder);

    /**
     * 修改数据
     *
     * @param wmsOutboundOrder 实例对象
     * @return 实例对象
     */
    WmsOutboundOrder update(WmsOutboundOrder wmsOutboundOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    boolean updateStatus(WmsOutboundOrder wmsOutboundOrder);
}
