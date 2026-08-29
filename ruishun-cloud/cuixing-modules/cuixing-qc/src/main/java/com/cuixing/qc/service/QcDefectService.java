package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcDefect;
import java.util.List;


/**
 * 质检管理-缺陷项设置(QcDefect)表服务接口
 *
 * @author makejava
 * @since 2024-07-02 17:22:41
 */
public interface QcDefectService {

    /**
     * 通过ID查询单条数据
     *
     * @param defectId 主键
     * @return 实例对象
     */
    QcDefect queryById(Long defectId);

    /**
     * 分页查询
     *
     * @param qcDefect 筛选条件
     * @return 查询结果
     */
    List<QcDefect> selectQcDefectList(QcDefect qcDefect);

    /**
     * 新增数据
     *
     * @param qcDefect 实例对象
     * @return 实例对象
     */
    QcDefect insert(QcDefect qcDefect);

    /**
     * 修改数据
     *
     * @param qcDefect 实例对象
     * @return 实例对象
     */
    QcDefect update(QcDefect qcDefect);

    /**
     * 通过主键删除数据
     *
     * @param defectId 主键
     * @return 是否成功
     */
    boolean deleteById(Long defectId);

}
