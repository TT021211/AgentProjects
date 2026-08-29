package com.cuixing.wms.service;

import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsNoticeLnvoice;

import java.util.List;


/**
 * 发货通知单(WmsNoticeLnvoice)表服务接口
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
public interface WmsNoticeLnvoiceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsNoticeLnvoice queryById(Long id);

    /**
     * 分页查询
     *
     * @param wmsNoticeLnvoice 筛选条件
     * @return 查询结果
     */
    List<WmsNoticeLnvoice> selectWmsNoticeLnvoiceList(WmsNoticeLnvoice wmsNoticeLnvoice);

    /**
     * 新增数据
     *
     * @param wmsNoticeLnvoice 实例对象
     * @return 实例对象
     */
    WmsNoticeLnvoice insert(WmsNoticeLnvoice wmsNoticeLnvoice);

    /**
     * 修改数据
     *
     * @param wmsNoticeLnvoice 实例对象
     * @return 实例对象
     */
    WmsNoticeLnvoice update(WmsNoticeLnvoice wmsNoticeLnvoice);

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    boolean deleteById(List<Long> ids);

    WmsNoticeLnvoice updateStatus(List<WmsNoticeLnvoice> wmsNoticeLnvoices);

    boolean removeBox(List<WmsBox> wmsBoxs);

    int recall(List<WmsNoticeLnvoice> wmsNoticeLnvoice);

    boolean test();
}
