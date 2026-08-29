package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldNesting;
import com.cuixing.mold.entity.MoldPurchase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模具采购(MoldPurchase)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-23 14:32:41
 */
public interface MoldPurchaseMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MoldPurchase queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param moldPurchase 查询条件
     * @return 对象列表
     */
    List<MoldPurchase> selectMoldPurchaseList(MoldPurchase moldPurchase);

    /**
     * 统计总行数
     *
     * @param moldPurchase 查询条件
     * @return 总行数
     */
    long count(MoldPurchase moldPurchase);

    /**
     * 新增数据
     *
     * @param moldPurchase 实例对象
     * @return 影响行数
     */
    int insert(MoldPurchase moldPurchase);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldPurchase> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldPurchase> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldPurchase> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldPurchase> entities);

    /**
     * 修改数据
     *
     * @param moldPurchase 实例对象
     * @return 影响行数
     */
    int update(MoldPurchase moldPurchase);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 检查唯一订单号
     * @param moldPurchase
     * @return
     */
    MoldPurchase checkUniquePurchaseCode(MoldPurchase moldPurchase);

}

