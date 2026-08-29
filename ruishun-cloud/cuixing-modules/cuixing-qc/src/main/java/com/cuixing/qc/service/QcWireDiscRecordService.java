package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcWireDiscRecord;

import java.util.List;


/**
 * 质量管理-线盘检记录(QcWireDiscRecord)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:26
 */
public interface QcWireDiscRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcWireDiscRecord queryById(Long id);

    /**
     * 分页查询
     *
     * @param qcWireDiscRecord 筛选条件
     * @return 查询结果
     */
    List<QcWireDiscRecord> selectQcWireDiscRecordList(QcWireDiscRecord qcWireDiscRecord);

    /**
     * 新增数据
     *
     * @param qcWireDiscRecord 实例对象
     * @return 实例对象
     */
    QcWireDiscRecord insert(QcWireDiscRecord qcWireDiscRecord);

    /**
     * 修改数据
     *
     * @param qcWireDiscRecord 实例对象
     * @return 实例对象
     */
    QcWireDiscRecord update(QcWireDiscRecord qcWireDiscRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
