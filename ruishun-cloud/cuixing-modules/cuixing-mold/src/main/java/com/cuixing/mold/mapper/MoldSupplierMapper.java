package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldSupplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模具供应商表(MoldSupplier)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-25 14:06:52
 */
public interface MoldSupplierMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param moldSupplierId 主键
     * @return 实例对象
     */
    MoldSupplier queryById(Long moldSupplierId);

    /**
     * 查询指定行数据
     *
     * @param moldSupplier 查询条件
     * @return 对象列表
     */
    List<MoldSupplier> selectMoldSupplierList(MoldSupplier moldSupplier);

    /**
     * 统计总行数
     *
     * @param moldSupplier 查询条件
     * @return 总行数
     */
    long count(MoldSupplier moldSupplier);

    /**
     * 新增数据
     *
     * @param moldSupplier 实例对象
     * @return 影响行数
     */
    int insert(MoldSupplier moldSupplier);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldSupplier> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldSupplier> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldSupplier> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldSupplier> entities);

    /**
     * 修改数据
     *
     * @param moldSupplier 实例对象
     * @return 影响行数
     */
    int update(MoldSupplier moldSupplier);

    /**
     * 通过主键删除数据
     *
     * @param moldSupplierId 主键
     * @return 影响行数
     */
    int deleteById(Long moldSupplierId);

}

