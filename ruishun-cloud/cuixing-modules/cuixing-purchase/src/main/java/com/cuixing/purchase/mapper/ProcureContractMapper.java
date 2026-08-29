package com.cuixing.purchase.mapper;

import com.cuixing.purchase.entity.ProcureContract;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 采购合同管理(ProcureContract)表数据库访问层
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
public interface ProcureContractMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureContract queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param procureContract 查询条件
     * @return 对象列表
     */
    List<ProcureContract> selectProcureContractList(ProcureContract procureContract);

    /**
     * 统计总行数
     *
     * @param procureContract 查询条件
     * @return 总行数
     */
    long count(ProcureContract procureContract);

    /**
     * 新增数据
     *
     * @param procureContract 实例对象
     * @return 影响行数
     */
    int insert(ProcureContract procureContract);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProcureContract> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ProcureContract> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProcureContract> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ProcureContract> entities);

    /**
     * 修改数据
     *
     * @param procureContract 实例对象
     * @return 影响行数
     */
    int update(ProcureContract procureContract);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

