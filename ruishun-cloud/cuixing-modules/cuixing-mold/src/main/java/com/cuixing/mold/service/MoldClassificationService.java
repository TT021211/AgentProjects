package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldClassification;

import java.util.List;


/**
 * 模具分类表(MoldClassification)表服务接口
 *
 * @author makejava
 * @since 2024-06-26 11:04:32
 */
public interface MoldClassificationService {

    /**
     * 通过ID查询单条数据
     *
     * @param classificationId 主键
     * @return 实例对象
     */
    MoldClassification queryById(Long classificationId);

    MoldClassification queryclassificationType(String classificationType,String classificationName);

    /**
     * 分页查询
     *
     * @param moldClassification 筛选条件
     * @return 查询结果
     */
    List<MoldClassification> selectMoldClassificationList(MoldClassification moldClassification);

    /**
     * 新增数据
     *
     * @param moldClassification 实例对象
     * @return 实例对象
     */
    MoldClassification insert(MoldClassification moldClassification);

    /**
     * 修改数据
     *
     * @param moldClassification 实例对象
     * @return 实例对象
     */
    MoldClassification update(MoldClassification moldClassification);

    /**
     * 通过主键删除数据
     *
     * @param classificationId 主键
     * @return 是否成功
     */
    boolean deleteById(Long classificationId);

    List<MoldClassification> selectMoldClassificationList2(MoldClassification moldClassification);
}
