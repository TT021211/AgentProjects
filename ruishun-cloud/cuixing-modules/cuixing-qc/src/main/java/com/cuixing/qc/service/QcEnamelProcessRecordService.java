package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcEnamelProcessRecord;

import java.util.List;


/**
 * 质量管理-漆包过程检记录(QcEnamelProcessRecord)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:22
 */
public interface QcEnamelProcessRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcEnamelProcessRecord queryById(Long id);

    /**
     * 分页查询
     *
     * @param qcEnamelProcessRecord 筛选条件
     * @return 查询结果
     */
    List<QcEnamelProcessRecord> selectQcEnamelProcessRecordList(QcEnamelProcessRecord qcEnamelProcessRecord);

    /**
     * 新增数据
     *
     * @param qcEnamelProcessRecord 实例对象
     * @return 实例对象
     */
    QcEnamelProcessRecord insert(QcEnamelProcessRecord qcEnamelProcessRecord);

    /**
     * 修改数据
     *
     * @param qcEnamelProcessRecord 实例对象
     * @return 实例对象
     */
    QcEnamelProcessRecord update(QcEnamelProcessRecord qcEnamelProcessRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
