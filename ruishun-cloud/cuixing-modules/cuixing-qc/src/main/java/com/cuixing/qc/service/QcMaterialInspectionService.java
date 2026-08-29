package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcMaterialInspection;
import java.util.List;


/**
 * 原料检验表(QcMaterialInspection)表服务接口
 *
 * @author 阿江
 * @since 2024-12-13 10:01:27
 */
public interface QcMaterialInspectionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcMaterialInspection queryById(Long id);

    /**
     * 分页查询
     *
     * @param qcMaterialInspection 筛选条件
     * @return 查询结果
     */
    List<QcMaterialInspection> selectQcMaterialInspectionList(QcMaterialInspection qcMaterialInspection);

    /**
     * 新增数据
     *
     * @param qcMaterialInspection 实例对象
     * @return 实例对象
     */
    QcMaterialInspection insert(QcMaterialInspection qcMaterialInspection);

    /**
     * 修改数据
     *
     * @param qcMaterialInspection 实例对象
     * @return 实例对象
     */
    QcMaterialInspection update(QcMaterialInspection qcMaterialInspection);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
