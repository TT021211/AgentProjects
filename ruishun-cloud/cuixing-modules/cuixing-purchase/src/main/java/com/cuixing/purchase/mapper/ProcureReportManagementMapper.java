package com.cuixing.purchase.mapper;

import com.cuixing.purchase.entity.ProcureReportManagement;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 采购报表分析(ProcureReportManagement)表数据库访问层
 *
 * @author 阿江
 * @since 2025-03-19 13:42:31
 */
public interface ProcureReportManagementMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureReportManagement queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param procureReportManagement 查询条件
     * @return 对象列表
     */
    List<ProcureReportManagement> selectProcureReportManagementList(ProcureReportManagement procureReportManagement);

    /**
     * 统计总行数
     *
     * @param procureReportManagement 查询条件
     * @return 总行数
     */
    long count(ProcureReportManagement procureReportManagement);

    /**
     * 新增数据
     *
     * @param procureReportManagement 实例对象
     * @return 影响行数
     */
    int insert(ProcureReportManagement procureReportManagement);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProcureReportManagement> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ProcureReportManagement> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProcureReportManagement> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ProcureReportManagement> entities);

    /**
     * 修改数据
     *
     * @param procureReportManagement 实例对象
     * @return 影响行数
     */
    int update(ProcureReportManagement procureReportManagement);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

