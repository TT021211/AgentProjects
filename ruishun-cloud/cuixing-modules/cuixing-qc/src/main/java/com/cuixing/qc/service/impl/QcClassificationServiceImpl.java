package com.cuixing.qc.service.impl;

import com.cuixing.qc.entity.QcClassification;
import com.cuixing.qc.mapper.QcClassificationMapper;
import com.cuixing.qc.service.QcClassificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质检分类表(QcClassification)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:18
 */
@Service("qcClassificationService")
public class QcClassificationServiceImpl implements QcClassificationService {
    @Resource
    private QcClassificationMapper qcClassificationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcClassification queryById(Long id) {
        return this.qcClassificationMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qcClassification 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcClassification> selectQcClassificationList(QcClassification qcClassification) {
        return this.qcClassificationMapper.selectQcClassificationList(qcClassification);
    }

    /**
     * 新增数据
     *
     * @param qcClassification 实例对象
     * @return 实例对象
     */
    @Override
    public QcClassification insert(QcClassification qcClassification) {
        this.qcClassificationMapper.insert(qcClassification);
        return qcClassification;
    }

    /**
     * 修改数据
     *
     * @param qcClassification 实例对象
     * @return 实例对象
     */
    @Override
    public QcClassification update(QcClassification qcClassification) {
        this.qcClassificationMapper.update(qcClassification);
        return this.queryById(qcClassification.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.qcClassificationMapper.deleteById(id) > 0;
    }
}
