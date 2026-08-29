package com.cuixing.md.service;

import com.cuixing.md.entity.MdWorkbenchDoublewire;

import java.math.BigInteger;
import java.util.List;


/**
 * 机台设置(MdWorkbenchDoublewire)表服务接口
 *
 * @author makejava
 * @since 2024-11-25 16:46:09
 */
public interface MdWorkbenchDoublewireService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchDoublewire queryById(BigInteger id);

    /**
     * 分页查询
     *
     * @param mdWorkbenchDoublewire 筛选条件
     * @return 查询结果
     */
    List<MdWorkbenchDoublewire> selectMdWorkbenchDoublewireList(MdWorkbenchDoublewire mdWorkbenchDoublewire);

    /**
     * 新增数据
     *
     * @param mdWorkbenchDoublewire 实例对象
     * @return 实例对象
     */
    int insert(MdWorkbenchDoublewire mdWorkbenchDoublewire);

    /**
     * 修改数据
     *
     * @param mdWorkbenchDoublewire 实例对象
     * @return 实例对象
     */
    int update(MdWorkbenchDoublewire mdWorkbenchDoublewire);

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

    int updateEnable(MdWorkbenchDoublewire mdWorkbenchDoublewire);
}
