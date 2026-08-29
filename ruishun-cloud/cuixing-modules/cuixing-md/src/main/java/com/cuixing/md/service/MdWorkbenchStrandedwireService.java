package com.cuixing.md.service;

import com.cuixing.md.entity.MdWorkbenchDoublewire;
import com.cuixing.md.entity.MdWorkbenchStrandedwire;

import java.math.BigInteger;
import java.util.List;


/**
 * 机台设置(MdWorkbenchStrandedwire)表服务接口
 *
 * @author makejava
 * @since 2024-11-25 16:46:10
 */
public interface MdWorkbenchStrandedwireService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchStrandedwire queryById(BigInteger id);

    /**
     * 分页查询
     *
     * @param mdWorkbenchStrandedwire 筛选条件
     * @return 查询结果
     */
    List<MdWorkbenchStrandedwire> selectMdWorkbenchStrandedwireList(MdWorkbenchStrandedwire mdWorkbenchStrandedwire);

    /**
     * 新增数据
     *
     * @param mdWorkbenchStrandedwire 实例对象
     * @return 实例对象
     */
    int insert(MdWorkbenchStrandedwire mdWorkbenchStrandedwire);

    /**
     * 修改数据
     *
     * @param mdWorkbenchStrandedwire 实例对象
     * @return 实例对象
     */
    int update(MdWorkbenchStrandedwire mdWorkbenchStrandedwire);

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

    int updateEnable(MdWorkbenchStrandedwire mdWorkbenchStrandedwire);

}
