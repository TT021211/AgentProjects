package com.cuixing.mes.service;

import com.cuixing.mes.entity.MesAnnealingWorkorder;
import com.cuixing.mes.entity.MesDoublewireWorkorder;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;


/**
 * 并丝工单(MesDoublewireWorkorder)表服务接口
 *
 * @author makejava
 * @since 2024-11-25 11:04:48
 */
public interface MesDoublewireWorkorderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesDoublewireWorkorder queryById(BigInteger id);

    /**
     * 分页查询
     *
     * @param mesDoublewireWorkorder 筛选条件
     * @return 查询结果
     */
    List<MesDoublewireWorkorder> selectMesDoublewireWorkorderList(MesDoublewireWorkorder mesDoublewireWorkorder);

    /**
     * 新增数据
     *
     * @param mesDoublewireWorkorder 实例对象
     * @return 实例对象
     */
    int insert(MesDoublewireWorkorder mesDoublewireWorkorder) throws ParseException;

    /**
     * 修改数据
     *
     * @param mesDoublewireWorkorder 实例对象
     * @return 实例对象
     */
    MesDoublewireWorkorder update(MesDoublewireWorkorder mesDoublewireWorkorder);

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

    List<MesDoublewireWorkorder> getListByEquipmentId(BigInteger equipmentId, String axleNumber);

    int insertWorkorder(MesDoublewireWorkorder mesDoublewireWorkorder);

    int cancelInsertWorkorder(BigInteger id);

    int cancelWorkorder(BigInteger id);
}
