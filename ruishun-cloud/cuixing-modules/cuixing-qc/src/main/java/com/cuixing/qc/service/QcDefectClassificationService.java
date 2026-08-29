package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcDefectClassification;

import java.util.List;


/**
 * 质量模块-缺陷项分类(QcDefectClassification)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:20
 */
public interface QcDefectClassificationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcDefectClassification queryById(Integer id);

    /**
     * 分页查询
     *
     * @param qcDefectClassification 筛选条件
     * @return 查询结果
     */
    List<QcDefectClassification> selectQcDefectClassificationList(QcDefectClassification qcDefectClassification);

    /**
     * 新增数据
     *
     * @param qcDefectClassification 实例对象
     * @return 实例对象
     */
    QcDefectClassification insert(QcDefectClassification qcDefectClassification);

    /**
     * 修改数据
     *
     * @param qcDefectClassification 实例对象
     * @return 实例对象
     */
    QcDefectClassification update(QcDefectClassification qcDefectClassification);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
