package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcDailyplan;

import java.util.List;


/**
 * 日常送检计划(QcDailyplan)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:19
 */
public interface QcDailyplanService {

    /**
     * 通过ID查询单条数据
     *
     * @param dailyplanId 主键
     * @return 实例对象
     */
    QcDailyplan queryById(Long dailyplanId);

    /**
     * 分页查询
     *
     * @param qcDailyplan 筛选条件
     * @return 查询结果
     */
    List<QcDailyplan> selectQcDailyplanList(QcDailyplan qcDailyplan);

    /**
     * 新增数据
     *
     * @param qcDailyplan 实例对象
     * @return 实例对象
     */
    QcDailyplan insert(QcDailyplan qcDailyplan);

    /**
     * 修改数据
     *
     * @param qcDailyplan 实例对象
     * @return 实例对象
     */
    QcDailyplan update(QcDailyplan qcDailyplan);

    /**
     * 通过主键删除数据
     *
     * @param dailyplanId 主键
     * @return 是否成功
     */
    boolean deleteById(Long dailyplanId);

}
