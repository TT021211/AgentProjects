package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldLedger;

import java.math.BigInteger;
import java.util.List;


/**
 * 模具台账表(MoldLedger)表服务接口
 *
 * @author makejava
 * @since 2024-06-25 14:06:46
 */
public interface MoldLedgerService {

    /**
     * 通过ID查询单条数据
     *
     * @param moldId 主键
     * @return 实例对象
     */
    MoldLedger queryById(Long moldId);

    /**
     * 分页查询
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    List<MoldLedger> selectMoldLedgerList(MoldLedger moldLedger);

    /**
     * 分页查询
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    List<MoldLedger> queryByClassificationId(MoldLedger moldLedger);
    /**
     * 分页查询
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    List<MoldLedger> queryByClassificationId2(MoldLedger moldLedger);
    /**
     * 过滤分页查询
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    List<MoldLedger> selectMoldLedgerList2(MoldLedger moldLedger);

    /**
     * 更新状态
     * @param moldLedger
     * @return
     */
    int updateStatus(MoldLedger moldLedger);

    /**
     * 更新状态和数量
     * @param moldLedger
     * @return
     */
    int updateStatusAndNum(MoldLedger moldLedger);

    /**
     * 更新状态
     * @param moldId
     * @return
     */
    int updateNestingIdNull(Long moldId);

    /**
     * 查询详情
     * @param nestingMoldId
     * @return
     */
    List<MoldLedger> queryByNestingMoldId(Long nestingMoldId);

    /**
     * 新增数据
     *
     * @param moldLedger 实例对象
     * @return 实例对象
     */
    MoldLedger insert(MoldLedger moldLedger);

    /**
     * 修改数据
     *
     * @param moldLedger 实例对象
     * @return 实例对象
     */
    MoldLedger update(MoldLedger moldLedger);

    /**
     * 通过主键删除数据
     *
     * @param moldId 主键
     * @return 是否成功
     */
    boolean deleteById(Long moldId);

    String checkUniqueMoldLedgerCode(MoldLedger moldLedger);

    void saveMoldLedgers(List<MoldLedger> moldLedgers);

    boolean deleteByIds(BigInteger[] ids);


}
