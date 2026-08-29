package com.cuixing.mes.service;

import com.cuixing.system.api.domain.mes.MesProductWorkorderEntry;
import java.math.BigInteger;
import java.util.List;

/**
 * 生产工单明细表;(mes_product_workorder_entry)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-5-29
 */
public interface IMesProductWorkorderEntryService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProductWorkorderEntry queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mesProductWorkorderEntry 筛选条件
     * @return 查询结果
     */
    List<MesProductWorkorderEntry> selectMesProductWorkorderEntryList(MesProductWorkorderEntry mesProductWorkorderEntry);
    /**
     * 新增数据
     *
     * @param mesProductWorkorderEntry 实例对象
     * @return 影响行数
     */
    BigInteger insert(MesProductWorkorderEntry mesProductWorkorderEntry);

    /**
     * 批量新增数据
     *
     * @param mesProductWorkorderEntrys 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MesProductWorkorderEntry> mesProductWorkorderEntrys);
    /**
     * 更新数据
     *
     * @param mesProductWorkorderEntry 实例对象
     * @return 影响行数
     */
    int update(MesProductWorkorderEntry mesProductWorkorderEntry);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigInteger id);
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    boolean deleteByIds(BigInteger[] ids);
}