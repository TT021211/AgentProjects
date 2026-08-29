package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcDetectionItems;

import java.util.List;


/**
 * 质量管理-检测项设置(QcDetectionItems)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:21
 */
public interface QcDetectionItemsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcDetectionItems queryById(Integer id);

    /**
     * 分页查询
     *
     * @param qcDetectionItems 筛选条件
     * @return 查询结果
     */
    List<QcDetectionItems> selectQcDetectionItemsList(QcDetectionItems qcDetectionItems);

    /**
     * 新增数据
     *
     * @param qcDetectionItems 实例对象
     * @return 实例对象
     */
    QcDetectionItems insert(QcDetectionItems qcDetectionItems);

    /**
     * 修改数据
     *
     * @param qcDetectionItems 实例对象
     * @return 实例对象
     */
    QcDetectionItems update(QcDetectionItems qcDetectionItems);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
