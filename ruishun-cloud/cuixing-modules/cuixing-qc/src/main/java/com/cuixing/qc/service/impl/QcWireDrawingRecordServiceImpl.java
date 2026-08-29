package com.cuixing.qc.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.qc.entity.QcDefect;
import com.cuixing.qc.entity.QcWireDrawingRecord;
import com.cuixing.qc.mapper.QcDefectMapper;
import com.cuixing.qc.mapper.QcWireDrawingRecordMapper;
import com.cuixing.qc.service.QcWireDrawingRecordService;
import com.cuixing.qc.utils.StringUtils;
import com.cuixing.system.api.RemoteWmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-拉丝过程检记录(QcWireDrawingRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:26
 */
@Service("qcWireDrawingRecordService")
public class QcWireDrawingRecordServiceImpl implements QcWireDrawingRecordService {
    private static final Logger log = LoggerFactory.getLogger(QcWireDrawingRecordServiceImpl.class);

    @Resource
    private QcWireDrawingRecordMapper qcWireDrawingRecordMapper;
    @Resource
    private QcDefectMapper qcDefectMapper;
    @Resource
    private RemoteWmsService remoteWmsService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcWireDrawingRecord queryById(Long id) {
        return this.qcWireDrawingRecordMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qcWireDrawingRecord 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcWireDrawingRecord> selectQcWireDrawingRecordList(QcWireDrawingRecord qcWireDrawingRecord) {
        List<QcWireDrawingRecord> qcWireDrawingRecords = this.qcWireDrawingRecordMapper.selectQcWireDrawingRecordList(qcWireDrawingRecord);
        if (!qcWireDrawingRecords.isEmpty()){
            for (QcWireDrawingRecord qcWireDrawingRecord1 : qcWireDrawingRecords) {
                List<QcDefect> items = new ArrayList<>();
                if (qcWireDrawingRecord1.getDefectId() != null && !"".equals(qcWireDrawingRecord1.getDefectId())) {
                    String defectIds = qcWireDrawingRecord1.getDefectId();
                    Long[] longArray = StringUtils.stringToLongArray(defectIds);
                    // 输出结果以验证转换成功
                    for (Long id : longArray) {
                        QcDefect qcDefect = this.qcDefectMapper.queryById(id);
                        if (qcDefect!=null){
                            items.add(qcDefect);
                        }
                    }
                    qcWireDrawingRecord1.setQcDefectList(items);
                }
            }
        }
        return qcWireDrawingRecords;
    }

    /**
     * 新增数据
     *
     * @param qcWireDrawingRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcWireDrawingRecord insert(QcWireDrawingRecord qcWireDrawingRecord) {
        qcWireDrawingRecord.setCreateBy(SecurityUtils.getUsername());
        qcWireDrawingRecord.setCreateTime(new Date());
        int insert = this.qcWireDrawingRecordMapper.insert(qcWireDrawingRecord);
        if (insert>0){
            try {
                this.remoteWmsService.updateDetectionStatus(qcWireDrawingRecord.getAttr2(), SecurityConstants.INNER);
            }catch (Exception e){
                log.error(e.toString());
            }
        }
        return qcWireDrawingRecord;
    }

    /**
     * 修改数据
     *
     * @param qcWireDrawingRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcWireDrawingRecord update(QcWireDrawingRecord qcWireDrawingRecord) {
        this.qcWireDrawingRecordMapper.update(qcWireDrawingRecord);
        return this.queryById(qcWireDrawingRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.qcWireDrawingRecordMapper.deleteById(id) > 0;
    }
}
