package com.cuixing.mes.service;

import com.cuixing.mes.entity.MesWorkstage;
import java.math.BigInteger;
import java.util.List;

/**
 * 工序表;(mes_workstage)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
public interface IMesWorkstageService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesWorkstage queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mesWorkstage 筛选条件
     * @return 查询结果
     */
    List<MesWorkstage> selectMesWorkstageList(MesWorkstage mesWorkstage);
    /**
     * 新增数据
     *
     * @param mesWorkstage 实例对象
     * @return 影响行数
     */
    BigInteger insert(MesWorkstage mesWorkstage);

    /**
     * 批量新增数据
     *
     * @param mesWorkstages 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MesWorkstage> mesWorkstages);
    /**
     * 更新数据
     *
     * @param mesWorkstage 实例对象
     * @return 影响行数
     */
    int update(MesWorkstage mesWorkstage);
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