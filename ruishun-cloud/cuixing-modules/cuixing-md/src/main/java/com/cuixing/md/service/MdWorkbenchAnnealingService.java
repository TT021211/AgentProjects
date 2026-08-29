package com.cuixing.md.service;

import com.cuixing.md.entity.MdWorkbenchAnnealing;

import java.math.BigInteger;
import java.util.List;


/**
 * 机台设置(MdWorkbenchAnnealing)表服务接口
 *
 * @author makejava
 * @since 2024-11-25 16:46:09
 */
public interface MdWorkbenchAnnealingService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchAnnealing queryById(BigInteger id);

    /**
     * 分页查询
     *
     * @param mdWorkbenchAnnealing 筛选条件
     * @return 查询结果
     */
    List<MdWorkbenchAnnealing> selectMdWorkbenchAnnealingList(MdWorkbenchAnnealing mdWorkbenchAnnealing);

    /**
     * 新增数据
     *
     * @param mdWorkbenchAnnealing 实例对象
     * @return 实例对象
     */
    int insert(MdWorkbenchAnnealing mdWorkbenchAnnealing);

    /**
     * 修改数据
     *
     * @param mdWorkbenchAnnealing 实例对象
     * @return 实例对象
     */
    int update(MdWorkbenchAnnealing mdWorkbenchAnnealing);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigInteger id);

    int updateEnable(MdWorkbenchAnnealing mdWorkbenchAnnealing);

    int deleteByIds(BigInteger[] ids);
}
