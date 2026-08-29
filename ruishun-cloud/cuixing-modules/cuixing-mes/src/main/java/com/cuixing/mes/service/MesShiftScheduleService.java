package com.cuixing.mes.service;

import com.cuixing.mes.entity.MesShiftSchedule;

import java.util.List;


/**
 * 生产交班表(MesShiftSchedule)表服务接口
 *
 * @author 阿江
 * @since 2025-03-24 11:18:54
 */
public interface MesShiftScheduleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesShiftSchedule queryById(Long id);

    /**
     * 分页查询
     *
     * @param mesShiftSchedule 筛选条件
     * @return 查询结果
     */
    List<MesShiftSchedule> selectMesShiftScheduleList(MesShiftSchedule mesShiftSchedule);

    /**
     * 新增数据
     *
     * @param mesShiftSchedule 实例对象
     * @return 实例对象
     */
    List<MesShiftSchedule> insert(List<MesShiftSchedule> mesShiftSchedule);

    /**
     * 修改数据
     *
     * @param mesShiftSchedule 实例对象
     * @return 实例对象
     */
    MesShiftSchedule update(MesShiftSchedule mesShiftSchedule);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    MesShiftSchedule getInfoByWorkstationCode(String workstationCode, String workorderSn);

    List<MesShiftSchedule> getInfoByWorkstationCodeToGy(String workstationCode, String workorderSn, List<String> alexPositionNumber, String alexNumber);
}
