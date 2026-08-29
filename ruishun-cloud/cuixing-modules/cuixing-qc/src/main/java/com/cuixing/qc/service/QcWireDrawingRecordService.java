package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcWireDrawingRecord;

import java.util.List;


/**
 * 质量管理-拉丝过程检记录(QcWireDrawingRecord)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:26
 */
public interface QcWireDrawingRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcWireDrawingRecord queryById(Long id);

    /**
     * 分页查询
     *
     * @param qcWireDrawingRecord 筛选条件
     * @return 查询结果
     */
    List<QcWireDrawingRecord> selectQcWireDrawingRecordList(QcWireDrawingRecord qcWireDrawingRecord);

    /**
     * 新增数据
     *
     * @param qcWireDrawingRecord 实例对象
     * @return 实例对象
     */
    QcWireDrawingRecord insert(QcWireDrawingRecord qcWireDrawingRecord);

    /**
     * 修改数据
     *
     * @param qcWireDrawingRecord 实例对象
     * @return 实例对象
     */
    QcWireDrawingRecord update(QcWireDrawingRecord qcWireDrawingRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
