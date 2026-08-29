package com.cuixing.purchase.service;

import com.cuixing.purchase.entity.ProcureReportManagement;
import java.util.List;


/**
 * 采购报表分析(ProcureReportManagement)表服务接口
 *
 * @author 阿江
 * @since 2025-03-19 13:42:31
 */
public interface ProcureReportManagementService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureReportManagement queryById(Long id);

    /**
     * 分页查询
     *
     * @param procureReportManagement 筛选条件
     * @return 查询结果
     */
    List<ProcureReportManagement> selectProcureReportManagementList(ProcureReportManagement procureReportManagement);

    /**
     * 新增数据
     *
     * @param procureReportManagement 实例对象
     * @return 实例对象
     */
    ProcureReportManagement insert(ProcureReportManagement procureReportManagement);

    /**
     * 修改数据
     *
     * @param procureReportManagement 实例对象
     * @return 实例对象
     */
    ProcureReportManagement update(ProcureReportManagement procureReportManagement);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
