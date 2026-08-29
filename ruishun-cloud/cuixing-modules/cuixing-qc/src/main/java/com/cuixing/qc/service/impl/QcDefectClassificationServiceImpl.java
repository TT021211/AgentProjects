package com.cuixing.qc.service.impl;

import com.cuixing.qc.entity.QcDefectClassification;
import com.cuixing.qc.mapper.QcDefectClassificationMapper;
import com.cuixing.qc.service.QcDefectClassificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量模块-缺陷项分类(QcDefectClassification)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:20
 */
@Service("qcDefectClassificationService")
public class QcDefectClassificationServiceImpl implements QcDefectClassificationService {
    @Resource
    private QcDefectClassificationMapper qcDefectClassificationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcDefectClassification queryById(Integer id) {
        return this.qcDefectClassificationMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qcDefectClassification 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcDefectClassification> selectQcDefectClassificationList(QcDefectClassification qcDefectClassification) {
        return this.qcDefectClassificationMapper.selectQcDefectClassificationList(qcDefectClassification);
    }

    /**
     * 新增数据
     *
     * @param qcDefectClassification 实例对象
     * @return 实例对象
     */
    @Override
    public QcDefectClassification insert(QcDefectClassification qcDefectClassification) {
        this.qcDefectClassificationMapper.insert(qcDefectClassification);
        return qcDefectClassification;
    }

    /**
     * 修改数据
     *
     * @param qcDefectClassification 实例对象
     * @return 实例对象
     */
    @Override
    public QcDefectClassification update(QcDefectClassification qcDefectClassification) {
        this.qcDefectClassificationMapper.update(qcDefectClassification);
        return this.queryById(qcDefectClassification.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.qcDefectClassificationMapper.deleteById(id) > 0;
    }
}
