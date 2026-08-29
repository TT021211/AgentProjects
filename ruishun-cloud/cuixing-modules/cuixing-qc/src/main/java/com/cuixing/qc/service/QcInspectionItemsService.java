package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcInspectionItems;

import java.util.List;


/**
 * 质量管理-质检检测项(QcInspectionItems)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:23
 */
public interface QcInspectionItemsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcInspectionItems queryById(Long id);

    /**
     * 分页查询
     *
     * @param qcInspectionItems 筛选条件
     * @return 查询结果
     */
    List<QcInspectionItems> selectQcInspectionItemsList(QcInspectionItems qcInspectionItems);

    /**
     * 新增数据
     *
     * @param qcInspectionItems 实例对象
     * @return 实例对象
     */
    QcInspectionItems insert(QcInspectionItems qcInspectionItems);

    /**
     * 修改数据
     *
     * @param qcInspectionItems 实例对象
     * @return 实例对象
     */
    QcInspectionItems update(QcInspectionItems qcInspectionItems);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
