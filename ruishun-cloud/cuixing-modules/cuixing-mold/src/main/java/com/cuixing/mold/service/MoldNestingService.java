package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldLedger;
import com.cuixing.mold.entity.MoldNesting;

import java.util.List;


/**
 * 拉丝套模表(MoldNesting)表服务接口
 *
 * @author makejava
 * @since 2024-06-25 14:06:48
 */
public interface MoldNestingService {

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
     * @param nestingMoldId 主键
     * @return 实例对象
     */
    MoldNesting queryById(Long nestingMoldId);
    /**
     * 通过编码查询单条数据
     *
     * @param moldNesting 主键
     * @return 实例对象
     */
    String checkUniqueMoldNestingCode(MoldNesting moldNesting);

    /**
     * 分页查询
     *
     * @param moldNesting 筛选条件
     * @return 查询结果
     */
    List<MoldNesting> selectMoldNestingList(MoldNesting moldNesting);

    /**
     * 新增数据
     *
     * @param moldNesting 实例对象
     * @return 实例对象
     */
    MoldNesting insert(MoldNesting moldNesting);

    /**
     * 修改数据
     *
     * @param moldNesting 实例对象
     * @return 实例对象
     */
    MoldNesting update(MoldNesting moldNesting);

    /**
     * 通过主键删除数据
     *
     * @param nestingMoldId 主键
     * @return 是否成功
     */
    int deleteByIds(Long nestingMoldId);

    List<MoldNesting> selectMoldNestingList2(MoldNesting moldNesting);
    List<MoldNesting> selectMoldNestingList3(MoldNesting moldNesting);

    int updateToScopeAndSpec(MoldNesting moldNesting);
}
