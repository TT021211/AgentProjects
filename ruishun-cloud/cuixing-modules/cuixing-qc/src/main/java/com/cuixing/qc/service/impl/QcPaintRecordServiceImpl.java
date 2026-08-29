package com.cuixing.qc.service.impl;

import com.cuixing.qc.entity.QcPaintRecord;
import com.cuixing.qc.mapper.QcPaintRecordMapper;
import com.cuixing.qc.service.QcPaintRecordService;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;

/**
 * 油漆检记录(QcPaintRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 17:04:54
 */
@Service("QcPaintRecordService")
public class QcPaintRecordServiceImpl implements QcPaintRecordService {
    @Resource
    private QcPaintRecordMapper QcPaintRecordMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcPaintRecord queryById(Long id) {
        return this.QcPaintRecordMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param QcPaintRecord 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcPaintRecord> selectQcPaintRecordList(QcPaintRecord QcPaintRecord) {
        return this.QcPaintRecordMapper.selectQcPaintRecordList(QcPaintRecord);
    }

    /**
     * 新增数据
     *
     * @param QcPaintRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcPaintRecord insert(QcPaintRecord QcPaintRecord) {
        this.QcPaintRecordMapper.insert(QcPaintRecord);
        return QcPaintRecord;
    }

    /**
     * 修改数据
     *
     * @param QcPaintRecord 实例对象
     * @return 实例对象
     */
    @Override
    public QcPaintRecord update(QcPaintRecord QcPaintRecord) {
        this.QcPaintRecordMapper.update(QcPaintRecord);
        return this.queryById(QcPaintRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.QcPaintRecordMapper.deleteById(id) > 0;
    }
}
