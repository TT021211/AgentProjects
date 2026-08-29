package com.cuixing.sale.mapper;

import com.cuixing.sale.entity.SaleReportManagement;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 销售报表分析(SaleReportManagement)表数据库访问层
 *
 * @author 阿江
 * @since 2025-03-17 15:27:04
 */
public interface SaleReportManagementMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SaleReportManagement queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param saleReportManagement 查询条件
     * @return 对象列表
     */
    List<SaleReportManagement> selectSaleReportManagementList(SaleReportManagement saleReportManagement);

    /**
     * 统计总行数
     *
     * @param saleReportManagement 查询条件
     * @return 总行数
     */
    long count(SaleReportManagement saleReportManagement);

    /**
     * 新增数据
     *
     * @param saleReportManagement 实例对象
     * @return 影响行数
     */
    int insert(SaleReportManagement saleReportManagement);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SaleReportManagement> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SaleReportManagement> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SaleReportManagement> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SaleReportManagement> entities);

    /**
     * 修改数据
     *
     * @param saleReportManagement 实例对象
     * @return 影响行数
     */
    int update(SaleReportManagement saleReportManagement);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

