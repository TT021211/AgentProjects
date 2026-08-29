package com.cuixing.qc.service.impl;

import com.cuixing.qc.entity.QcPinholeRecord;
import com.cuixing.qc.mapper.QcPinholeRecordMapper;
import com.cuixing.qc.service.QcPinholeRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-针孔送检记录(QcPinholeRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:25
 */
@Service("qcPinholeRecordService")
public class QcPinholeRecordServiceImpl implements QcPinholeRecordService {
    @Resource
    private QcPinholeRecordMapper qcPinholeRecordMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcPinholeRecord queryById(Long id) {
        return this.qcPinholeRecordMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qcPinholeRecord 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcPinholeRecord> selectQcPinholeRecordList(QcPinholeRecord qcPinholeRecord) {
        return this.qcPinholeRecordMapper.selectQcPinholeRecordList(qcPinholeRecord);
    }

    /**
     * 新增数据
     *
     * @param qcPinholeRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcPinholeRecord insert(QcPinholeRecord qcPinholeRecord) {
        this.qcPinholeRecordMapper.insert(qcPinholeRecord);
        return qcPinholeRecord;
    }

    /**
     * 修改数据
     *
     * @param qcPinholeRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcPinholeRecord update(QcPinholeRecord qcPinholeRecord) {
        this.qcPinholeRecordMapper.update(qcPinholeRecord);
        return this.queryById(qcPinholeRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.qcPinholeRecordMapper.deleteById(id) > 0;
    }
}
