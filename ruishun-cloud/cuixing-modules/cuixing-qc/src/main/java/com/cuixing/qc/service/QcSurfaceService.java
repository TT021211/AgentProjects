package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcSurface;

import java.util.List;


/**
 * 质检管理-表面检缺陷项设置(QcSurface)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 16:56:25
 */
public interface QcSurfaceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcSurface queryById(Integer id);

    /**
     * 分页查询
     *
     * @param qcSurface 筛选条件
     * @return 查询结果
     */
    List<QcSurface> selectQcSurfaceList(QcSurface qcSurface);

    /**
     * 新增数据
     *
     * @param qcSurface 实例对象
     * @return 实例对象
     */
    QcSurface insert(QcSurface qcSurface);

    /**
     * 修改数据
     *
     * @param qcSurface 实例对象
     * @return 实例对象
     */
    QcSurface update(QcSurface qcSurface);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
