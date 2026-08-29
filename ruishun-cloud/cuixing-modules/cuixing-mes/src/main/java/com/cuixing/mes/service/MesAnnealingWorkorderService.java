package com.cuixing.mes.service;

import com.cuixing.mes.entity.MesAnnealingWorkorder;
import com.cuixing.mes.entity.MesEnamellingWorkorder;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * 退火工单(MesAnnealingWorkorder)表服务接口
 *
 * @author makejava
 * @since 2024-11-25 10:30:34
 */
public interface MesAnnealingWorkorderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesAnnealingWorkorder queryById(BigInteger id);

    /**
     * 分页查询
     *
     * @param mesAnnealingWorkorder 筛选条件
     * @return 查询结果
     */
    List<MesAnnealingWorkorder> selectMesAnnealingWorkorderList(MesAnnealingWorkorder mesAnnealingWorkorder);

    /**
     * 新增数据
     *
     * @param mesAnnealingWorkorder 实例对象
     * @return 实例对象
     */
    int insert(MesAnnealingWorkorder mesAnnealingWorkorder) throws ParseException;

    /**
     * 修改数据
     *
     * @param mesAnnealingWorkorder 实例对象
     * @return 实例对象
     */
    int update(MesAnnealingWorkorder mesAnnealingWorkorder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigInteger id);

    int deleteByIds(BigInteger[] ids);

    int updateStatusById(BigInteger id, String status);

    int cancelById(BigInteger id);

    /**
     * * 排序-优先
     */
    int updateSortById(BigInteger id);

    List<MesAnnealingWorkorder> getListByEquipmentId(BigInteger equipmentId, String axleNumber);

    int insertWorkorder(MesAnnealingWorkorder mesAnnealingWorkorder);

    int cancelInsertWorkorder(BigInteger id);

    int cancelWorkorder(BigInteger id);

    MesAnnealingWorkorder queryByMesAnnealingWorkorderCode(String workorderCode);

    int batchUpdate(ArrayList<MesAnnealingWorkorder> mesAnnealingWorkorders);
}
