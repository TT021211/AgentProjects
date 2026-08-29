package com.cuixing.md.service;

import com.cuixing.md.entity.MdWorkbenchStrandedwire;
import com.cuixing.md.entity.MdWorkbenchTinning;

import java.math.BigInteger;
import java.util.List;


/**
 * 机台设置(MdWorkbenchTinning)表服务接口
 *
 * @author makejava
 * @since 2024-11-25 16:46:10
 */
public interface MdWorkbenchTinningService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdWorkbenchTinning queryById(BigInteger id);

    /**
     * 分页查询
     *
     * @param mdWorkbenchTinning 筛选条件
     * @return 查询结果
     */
    List<MdWorkbenchTinning> selectMdWorkbenchTinningList(MdWorkbenchTinning mdWorkbenchTinning);

    /**
     * 新增数据
     *
     * @param mdWorkbenchTinning 实例对象
     * @return 实例对象
     */
    int insert(MdWorkbenchTinning mdWorkbenchTinning);

    /**
     * 修改数据
     *
     * @param mdWorkbenchTinning 实例对象
     * @return 实例对象
     */
    int update(MdWorkbenchTinning mdWorkbenchTinning);

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

    int updateEnable(MdWorkbenchTinning mdWorkbenchTinning);

}
