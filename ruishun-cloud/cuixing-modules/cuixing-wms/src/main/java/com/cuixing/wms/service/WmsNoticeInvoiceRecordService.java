package com.cuixing.wms.service;

import com.cuixing.wms.entity.WmsNoticeInvoiceRecord;
import java.util.List;


/**
 * 发货通知单审核记录表(WmsNoticeInvoiceRecord)表服务接口
 *
 * @author makejava
 * @since 2024-10-30 14:09:47
 */
public interface WmsNoticeInvoiceRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsNoticeInvoiceRecord queryById(Long id);

    /**
     * 分页查询
     *
     * @param wmsNoticeInvoiceRecord 筛选条件
     * @return 查询结果
     */
    List<WmsNoticeInvoiceRecord> selectWmsNoticeInvoiceRecordList(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord);

    /**
     * 新增数据
     *
     * @param wmsNoticeInvoiceRecord 实例对象
     * @return 实例对象
     */
    WmsNoticeInvoiceRecord insert(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord);

    /**
     * 修改数据
     *
     * @param wmsNoticeInvoiceRecord 实例对象
     * @return 实例对象
     */
    WmsNoticeInvoiceRecord update(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
