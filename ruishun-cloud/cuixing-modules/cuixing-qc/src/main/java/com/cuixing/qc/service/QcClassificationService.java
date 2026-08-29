package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcClassification;

import java.util.List;


/**
 * 质检分类表(QcClassification)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:18
 */
public interface QcClassificationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcClassification queryById(Long id);

    /**
     * 分页查询
     *
     * @param qcClassification 筛选条件
     * @return 查询结果
     */
    List<QcClassification> selectQcClassificationList(QcClassification qcClassification);

    /**
     * 新增数据
     *
     * @param qcClassification 实例对象
     * @return 实例对象
     */
    QcClassification insert(QcClassification qcClassification);

    /**
     * 修改数据
     *
     * @param qcClassification 实例对象
     * @return 实例对象
     */
    QcClassification update(QcClassification qcClassification);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
