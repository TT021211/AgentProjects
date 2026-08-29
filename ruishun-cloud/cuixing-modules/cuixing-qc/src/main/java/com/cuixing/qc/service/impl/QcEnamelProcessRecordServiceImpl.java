package com.cuixing.qc.service.impl;

import com.cuixing.qc.entity.QcDefect;
import com.cuixing.qc.entity.QcEnamelProcessRecord;
import com.cuixing.qc.mapper.QcDefectMapper;
import com.cuixing.qc.mapper.QcEnamelProcessRecordMapper;
import com.cuixing.qc.service.QcDefectService;
import com.cuixing.qc.service.QcEnamelProcessRecordService;
import com.cuixing.qc.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-漆包过程检记录(QcEnamelProcessRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:22
 */
@Service("qcEnamelProcessRecordService")
public class QcEnamelProcessRecordServiceImpl implements QcEnamelProcessRecordService {
    @Resource
    private QcEnamelProcessRecordMapper qcEnamelProcessRecordMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcEnamelProcessRecord queryById(Long id) {
        return this.qcEnamelProcessRecordMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qcEnamelProcessRecord 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcEnamelProcessRecord> selectQcEnamelProcessRecordList(QcEnamelProcessRecord qcEnamelProcessRecord) {
        List<QcEnamelProcessRecord> qcEnamelProcessRecords = this.qcEnamelProcessRecordMapper.selectQcEnamelProcessRecordList(qcEnamelProcessRecord);
        if (qcEnamelProcessRecords.size() >0 ){
            for (QcEnamelProcessRecord qcEnamelProcess: qcEnamelProcessRecords){
                if (qcEnamelProcess.getDefectId() != null){
                    List<QcDefect> qcDefects = StringUtils.queryDefectItem(qcEnamelProcess.getDefectId());
                    if (qcDefects.size()>0){
                        qcEnamelProcess.setQcDefectList(qcDefects);
                    }
                }
            }
        }
        return qcEnamelProcessRecords;
    }

    /**
     * 新增数据
     *
     * @param qcEnamelProcessRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcEnamelProcessRecord insert(QcEnamelProcessRecord qcEnamelProcessRecord) {
        this.qcEnamelProcessRecordMapper.insert(qcEnamelProcessRecord);
        return qcEnamelProcessRecord;
    }

    /**
     * 修改数据
     *
     * @param qcEnamelProcessRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcEnamelProcessRecord update(QcEnamelProcessRecord qcEnamelProcessRecord) {
        this.qcEnamelProcessRecordMapper.update(qcEnamelProcessRecord);
        return this.queryById(qcEnamelProcessRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.qcEnamelProcessRecordMapper.deleteById(id) > 0;
    }
}
