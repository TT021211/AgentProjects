package com.cuixing.purchase.service.impl;

import com.cuixing.purchase.entity.ProcureReportManagement;
import com.cuixing.purchase.mapper.ProcureReportManagementMapper;
import com.cuixing.purchase.service.ProcureReportManagementService;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;

/**
 * 采购报表分析(ProcureReportManagement)表服务实现类
 *
 * @author 阿江
 * @since 2025-03-19 13:42:31
 */
@Service("procureReportManagementService")
public class ProcureReportManagementServiceImpl implements ProcureReportManagementService {
    @Resource
    private ProcureReportManagementMapper procureReportManagementMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProcureReportManagement queryById(Long id) {
        return this.procureReportManagementMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param procureReportManagement 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ProcureReportManagement> selectProcureReportManagementList(ProcureReportManagement procureReportManagement) {
        return this.procureReportManagementMapper.selectProcureReportManagementList(procureReportManagement);
    }

    /**
     * 新增数据
     *
     * @param procureReportManagement 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureReportManagement insert(ProcureReportManagement procureReportManagement) {
        this.procureReportManagementMapper.insert(procureReportManagement);
        return procureReportManagement;
    }

    /**
     * 修改数据
     *
     * @param procureReportManagement 实例对象
     * @return 实例对象
     */
    @Override
    public ProcureReportManagement update(ProcureReportManagement procureReportManagement) {
        this.procureReportManagementMapper.update(procureReportManagement);
        return this.queryById(procureReportManagement.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.procureReportManagementMapper.deleteById(id) > 0;
    }
}
