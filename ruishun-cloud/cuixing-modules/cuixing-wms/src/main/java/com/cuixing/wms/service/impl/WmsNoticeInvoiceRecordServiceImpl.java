package com.cuixing.wms.service.impl;

import com.cuixing.wms.entity.WmsNoticeInvoiceRecord;
import com.cuixing.wms.mapper.WmsNoticeInvoiceRecordMapper;
import com.cuixing.wms.service.WmsNoticeInvoiceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;

/**
 * 发货通知单审核记录表(WmsNoticeInvoiceRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-10-30 14:09:47
 */
@Service("wmsNoticeInvoiceRecordService")
public class WmsNoticeInvoiceRecordServiceImpl implements WmsNoticeInvoiceRecordService {
    @Autowired
    private WmsNoticeInvoiceRecordMapper wmsNoticeInvoiceRecordMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsNoticeInvoiceRecord queryById(Long id) {
        return this.wmsNoticeInvoiceRecordMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param wmsNoticeInvoiceRecord 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WmsNoticeInvoiceRecord> selectWmsNoticeInvoiceRecordList(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord) {
        return this.wmsNoticeInvoiceRecordMapper.selectWmsNoticeInvoiceRecordList(wmsNoticeInvoiceRecord);
    }

    /**
     * 新增数据
     *
     * @param wmsNoticeInvoiceRecord 实例对象
     * @return 实例对象
     */
    @Override
    public WmsNoticeInvoiceRecord insert(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord) {
        this.wmsNoticeInvoiceRecordMapper.insert(wmsNoticeInvoiceRecord);
        return wmsNoticeInvoiceRecord;
    }

    /**
     * 修改数据
     *
     * @param wmsNoticeInvoiceRecord 实例对象
     * @return 实例对象
     */
    @Override
    public WmsNoticeInvoiceRecord update(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord) {
        this.wmsNoticeInvoiceRecordMapper.update(wmsNoticeInvoiceRecord);
        return this.queryById(wmsNoticeInvoiceRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.wmsNoticeInvoiceRecordMapper.deleteById(id) > 0;
    }
}
