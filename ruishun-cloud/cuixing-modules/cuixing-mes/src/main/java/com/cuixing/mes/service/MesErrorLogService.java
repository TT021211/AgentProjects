package com.cuixing.mes.service;

import com.cuixing.mes.entity.MesErrorLog;

import java.util.List;


/**
 * 生产异常表(MesErrorLog)表服务接口
 *
 * @author 阿江
 * @since 2025-07-14 23:27:49
 */
public interface MesErrorLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesErrorLog queryById(Long id);

    /**
     * 分页查询
     *
     * @param mesErrorLog 筛选条件
     * @return 查询结果
     */
    List<MesErrorLog> selectMesErrorLogList(MesErrorLog mesErrorLog);

    /**
     * 新增数据
     *
     * @param mesErrorLog 实例对象
     * @return 实例对象
     */
    MesErrorLog insert(MesErrorLog mesErrorLog);

    /**
     * 修改数据
     *
     * @param mesErrorLog 实例对象
     * @return 实例对象
     */
    MesErrorLog update(MesErrorLog mesErrorLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
