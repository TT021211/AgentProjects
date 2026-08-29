package com.cuixing.qc.service.impl;

import com.cuixing.qc.entity.QcEnamelInspectionRecord;
import com.cuixing.qc.entity.QcInspectionItems;
import com.cuixing.qc.mapper.QcEnamelInspectionRecordMapper;
import com.cuixing.qc.service.QcEnamelInspectionRecordService;
import com.cuixing.qc.service.QcInspectionItemsService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量模块-漆包巡检记录(QcEnamelInspectionRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:21
 */
@Service("qcEnamelInspectionRecordService")
public class QcEnamelInspectionRecordServiceImpl implements QcEnamelInspectionRecordService {
    @Resource
    private QcEnamelInspectionRecordMapper qcEnamelInspectionRecordMapper;
    @Resource
    private QcInspectionItemsService qcInspectionItemsService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcEnamelInspectionRecord queryById(Long id) {
        QcEnamelInspectionRecord qcEnamelInspectionRecord = this.qcEnamelInspectionRecordMapper.queryById(id);
        if (qcEnamelInspectionRecord.getInspectionId() != null){
            // 获取相关数据
            QcInspectionItems qcInspectionItems = this.qcInspectionItemsService.queryById(qcEnamelInspectionRecord.getInspectionId());
            if (qcInspectionItems != null) {
                qcEnamelInspectionRecord.setQcInspectionItems(qcInspectionItems);
            }
        }
        return qcEnamelInspectionRecord;
    }

    /**
     * 分页查询
     *
     * @param qcEnamelInspectionRecord 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcEnamelInspectionRecord> selectQcEnamelInspectionRecordList(QcEnamelInspectionRecord qcEnamelInspectionRecord) {
        List<QcEnamelInspectionRecord> qcEnamelInspectionRecords = this.qcEnamelInspectionRecordMapper.selectQcEnamelInspectionRecordList(qcEnamelInspectionRecord);
        if (qcEnamelInspectionRecords.size() >0){
            for (QcEnamelInspectionRecord qcEnamelInspection : qcEnamelInspectionRecords){
                // 填充相关数据
                if (qcEnamelInspection.getInspectionId() != null){
                    // 获取相关数据
                    QcInspectionItems qcInspectionItems = this.qcInspectionItemsService.queryById(qcEnamelInspection.getInspectionId());
                    if (qcInspectionItems != null) {
                        qcEnamelInspection.setQcInspectionItems(qcInspectionItems);
                    }
                }
            }
        }
        return qcEnamelInspectionRecords;
    }

    /**
     * 新增数据
     *
     * @param qcEnamelInspectionRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcEnamelInspectionRecord insert(QcEnamelInspectionRecord qcEnamelInspectionRecord) {
        this.qcEnamelInspectionRecordMapper.insert(qcEnamelInspectionRecord);
        return qcEnamelInspectionRecord;
    }

    /**
     * 修改数据
     *
     * @param qcEnamelInspectionRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcEnamelInspectionRecord update(QcEnamelInspectionRecord qcEnamelInspectionRecord) {
        this.qcEnamelInspectionRecordMapper.update(qcEnamelInspectionRecord);
        return this.queryById(qcEnamelInspectionRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.qcEnamelInspectionRecordMapper.deleteById(id) > 0;
    }
}
