package com.cuixing.sale.mapper;

import com.cuixing.sale.entity.SaleContractManage;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (SaleContractManage)表数据库访问层
 *
 * @author shrimp
 * @since 2025-02-26 10:51:35
 */
public interface SaleContractManageMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SaleContractManage queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param saleContractManage 查询条件
     * @return 对象列表
     */
    List<SaleContractManage> selectSaleContractManageList(SaleContractManage saleContractManage);

    /**
     * 统计总行数
     *
     * @param saleContractManage 查询条件
     * @return 总行数
     */
    long count(SaleContractManage saleContractManage);

    /**
     * 新增数据
     *
     * @param saleContractManage 实例对象
     * @return 影响行数
     */
    int insert(SaleContractManage saleContractManage);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SaleContractManage> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SaleContractManage> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SaleContractManage> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SaleContractManage> entities);

    /**
     * 修改数据
     *
     * @param saleContractManage 实例对象
     * @return 影响行数
     */
    int update(SaleContractManage saleContractManage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

