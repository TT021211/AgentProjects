package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcPinholeRecord;

import java.util.List;


/**
 * 质量管理-针孔送检记录(QcPinholeRecord)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:25
 */
public interface QcPinholeRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcPinholeRecord queryById(Long id);

    /**
     * 分页查询
     *
     * @param qcPinholeRecord 筛选条件
     * @return 查询结果
     */
    List<QcPinholeRecord> selectQcPinholeRecordList(QcPinholeRecord qcPinholeRecord);

    /**
     * 新增数据
     *
     * @param qcPinholeRecord 实例对象
     * @return 实例对象
     */
    QcPinholeRecord insert(QcPinholeRecord qcPinholeRecord);

    /**
     * 修改数据
     *
     * @param qcPinholeRecord 实例对象
     * @return 实例对象
     */
    QcPinholeRecord update(QcPinholeRecord qcPinholeRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
