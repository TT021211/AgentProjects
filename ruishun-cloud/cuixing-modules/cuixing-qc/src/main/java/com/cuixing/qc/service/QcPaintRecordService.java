package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcPaintRecord;
import java.util.List;


/**
 * 油漆检记录(QcPaintRecord)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 17:04:54
 */
public interface QcPaintRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcPaintRecord queryById(Long id);

    /**
     * 分页查询
     *
     * @param QcPaintRecord 筛选条件
     * @return 查询结果
     */
    List<QcPaintRecord> selectQcPaintRecordList(QcPaintRecord QcPaintRecord);

    /**
     * 新增数据
     *
     * @param QcPaintRecord 实例对象
     * @return 实例对象
     */
    QcPaintRecord insert(QcPaintRecord QcPaintRecord);

    /**
     * 修改数据
     *
     * @param QcPaintRecord 实例对象
     * @return 实例对象
     */
    QcPaintRecord update(QcPaintRecord QcPaintRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
