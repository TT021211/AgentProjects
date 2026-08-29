package com.cuixing.purchase.mapper;

import com.cuixing.purchase.entity.ProcureMaterialReturn;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 采购退货管理(ProcureMaterialReturn)表数据库访问层
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
public interface ProcureMaterialReturnMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureMaterialReturn queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param procureMaterialReturn 查询条件
     * @return 对象列表
     */
    List<ProcureMaterialReturn> selectProcureMaterialReturnList(ProcureMaterialReturn procureMaterialReturn);

    /**
     * 统计总行数
     *
     * @param procureMaterialReturn 查询条件
     * @return 总行数
     */
    long count(ProcureMaterialReturn procureMaterialReturn);

    /**
     * 新增数据
     *
     * @param procureMaterialReturn 实例对象
     * @return 影响行数
     */
    int insert(ProcureMaterialReturn procureMaterialReturn);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProcureMaterialReturn> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ProcureMaterialReturn> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProcureMaterialReturn> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ProcureMaterialReturn> entities);

    /**
     * 修改数据
     *
     * @param procureMaterialReturn 实例对象
     * @return 影响行数
     */
    int update(ProcureMaterialReturn procureMaterialReturn);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

