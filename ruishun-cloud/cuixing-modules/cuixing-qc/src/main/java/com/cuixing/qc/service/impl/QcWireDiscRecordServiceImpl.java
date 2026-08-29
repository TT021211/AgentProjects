package com.cuixing.qc.service.impl;

import com.cuixing.qc.entity.QcDefect;
import com.cuixing.qc.entity.QcInspectionItems;
import com.cuixing.qc.entity.QcWireDiscRecord;
import com.cuixing.qc.mapper.QcWireDiscRecordMapper;
import com.cuixing.qc.service.QcDefectService;
import com.cuixing.qc.service.QcInspectionItemsService;
import com.cuixing.qc.service.QcWireDiscRecordService;
import com.cuixing.qc.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-线盘检记录(QcWireDiscRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:26
 */
@Service("qcWireDiscRecordService")
public class QcWireDiscRecordServiceImpl implements QcWireDiscRecordService {
    @Resource
    private QcWireDiscRecordMapper qcWireDiscRecordMapper;
    @Resource
    private QcInspectionItemsService qcInspectionItemsService;
    @Resource
    private QcDefectService qcDefectService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcWireDiscRecord queryById(Long id) {
        return this.qcWireDiscRecordMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qcWireDiscRecord 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcWireDiscRecord> selectQcWireDiscRecordList(QcWireDiscRecord qcWireDiscRecord) {
        List<QcWireDiscRecord> qcWireDiscRecords = this.qcWireDiscRecordMapper.selectQcWireDiscRecordList(qcWireDiscRecord);
        if (!qcWireDiscRecords.isEmpty()){
            for (QcWireDiscRecord qcWireDiscRecord1:qcWireDiscRecords) {
                if (qcWireDiscRecord1.getInspectionId() != null) {
                    // 获取相关数据
                    QcInspectionItems qcInspectionItems = this.qcInspectionItemsService.queryById(qcWireDiscRecord1.getInspectionId());
                    if (qcInspectionItems != null) {
                        qcWireDiscRecord1.setQcInspectionItems(qcInspectionItems);
                    }
                }
                List<QcDefect> items = new ArrayList<>();

                if (qcWireDiscRecord1.getDefectId() != null && !"".equals(qcWireDiscRecord1.getDefectId())) {

                    String defectIds = qcWireDiscRecord1.getDefectId();
                    Long[] longArray = StringUtils.stringToLongArray(defectIds);
                    // 输出结果以验证转换成功
                    for (Long id : longArray) {
                        QcDefect qcDefect = this.qcDefectService.queryById(id);
                        if (qcDefect != null) {
                            items.add(qcDefect);
                        }
                    }
                    qcWireDiscRecord1.setQcDefectList(items);
                }
            }
        }
        return qcWireDiscRecords;
    }

    /**
     * 新增数据
     *
     * @param qcWireDiscRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcWireDiscRecord insert(QcWireDiscRecord qcWireDiscRecord) {
        this.qcWireDiscRecordMapper.insert(qcWireDiscRecord);
        return qcWireDiscRecord;
    }

    /**
     * 修改数据
     *
     * @param qcWireDiscRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcWireDiscRecord update(QcWireDiscRecord qcWireDiscRecord) {
        this.qcWireDiscRecordMapper.update(qcWireDiscRecord);
        return this.queryById(qcWireDiscRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.qcWireDiscRecordMapper.deleteById(id) > 0;
    }
}
