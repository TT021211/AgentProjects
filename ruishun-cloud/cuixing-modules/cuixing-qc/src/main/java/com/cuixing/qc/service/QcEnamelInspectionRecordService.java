package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcEnamelInspectionRecord;

import java.util.List;


/**
 * 质量模块-漆包巡检记录(QcEnamelInspectionRecord)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:21
 */
public interface QcEnamelInspectionRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcEnamelInspectionRecord queryById(Long id);

    /**
     * 分页查询
     *
     * @param qcEnamelInspectionRecord 筛选条件
     * @return 查询结果
     */
    List<QcEnamelInspectionRecord> selectQcEnamelInspectionRecordList(QcEnamelInspectionRecord qcEnamelInspectionRecord);

    /**
     * 新增数据
     *
     * @param qcEnamelInspectionRecord 实例对象
     * @return 实例对象
     */
    QcEnamelInspectionRecord insert(QcEnamelInspectionRecord qcEnamelInspectionRecord);

    /**
     * 修改数据
     *
     * @param qcEnamelInspectionRecord 实例对象
     * @return 实例对象
     */
    QcEnamelInspectionRecord update(QcEnamelInspectionRecord qcEnamelInspectionRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
