package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldLedger;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * 模具台账表(MoldLedger)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-25 14:06:46
 */
public interface MoldLedgerMapper {

    /**
     * 通过ID查询拉丝套模详情
     *
     * @param nestingMoldId 主键
     * @return 实例对象
     */
    List<MoldLedger> queryByNestingMoldId(Long nestingMoldId);

    /**
     * 通过ID查询单条数据
     *
     * @param moldId 主键
     * @return 实例对象
     */
    MoldLedger queryById(Long moldId);
    /**
     * 通过分类id查询单条数据
     *
     * @param classificationId 主键
     * @return 实例对象
     */
    List<MoldLedger> queryByClassificationId(Long classificationId);

    /**
     * 查询指定行数据
     *
     * @param moldLedger 查询条件
     * @return 对象列表
     */
    List<MoldLedger> selectMoldLedgerList(MoldLedger moldLedger);
    /**
     * 查询指定数据
     *
     * @param moldLedger 查询条件
     * @return 对象列表
     */
    List<MoldLedger> selectMoldLedgerTypeList(MoldLedger moldLedger);

    /**
     * 统计总行数
     *
     * @param moldLedger 查询条件
     * @return 总行数
     */
    long count(MoldLedger moldLedger);

    /**
     * 新增数据
     *
     * @param moldLedger 实例对象
     * @return 影响行数
     */
    int insert(MoldLedger moldLedger);

    /**
     * 检测唯一编码
     *
     * @param moldLedger 实例对象
     * @return 影响行数
     */
    MoldLedger checkUniqueMoldLedgerCode(MoldLedger moldLedger);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldLedger> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldLedger> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldLedger> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldLedger> entities);

    /**
     * 修改数据
     *
     * @param moldLedger 实例对象
     * @return 影响行数
     */
    int update(MoldLedger moldLedger);
    /**
     * 修改出入库状态
     *
     * @param moldLedger 实例对象
     * @return 影响行数
     */
    int updateStatus(MoldLedger moldLedger);
    /**
     * 修改出入库状态
     *
     * @param moldId 实例对象
     * @return 影响行数
     */
    int updateNestingIdNull(Long moldId);

    /**
     * 通过主键删除数据
     *
     * @param moldId 主键
     * @return 影响行数
     */
    int deleteById(Long moldId);

    int deleteByIds(BigInteger[] ids);

    /**
     * 查询数据
     */
    List<MoldLedger> selectMoldLedgerListNew2(MoldLedger moldLedger);

    List<MoldLedger> selectMoldLedgerListNew1(MoldLedger moldLedger);

    int updateStatusAndNum(MoldLedger moldLedger);
}

