package com.cuixing.mes.service;

import com.cuixing.mes.entity.MesDoublewireWorkorder;
import com.cuixing.mes.entity.MesStrandedwireWorkorder;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;


/**
 * 绞线工单(MesStrandedwireWorkorder)表服务接口
 *
 * @author makejava
 * @since 2024-11-25 11:04:48
 */
public interface MesStrandedwireWorkorderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesStrandedwireWorkorder queryById(BigInteger id);

    /**
     * 分页查询
     *
     * @param mesStrandedwireWorkorder 筛选条件
     * @return 查询结果
     */
    List<MesStrandedwireWorkorder> selectMesStrandedwireWorkorderList(MesStrandedwireWorkorder mesStrandedwireWorkorder);

    /**
     * 新增数据
     *
     * @param mesStrandedwireWorkorder 实例对象
     * @return 实例对象
     */
    int insert(MesStrandedwireWorkorder mesStrandedwireWorkorder) throws ParseException;

    /**
     * 修改数据
     *
     * @param mesStrandedwireWorkorder 实例对象
     * @return 实例对象
     */
    MesStrandedwireWorkorder update(MesStrandedwireWorkorder mesStrandedwireWorkorder);

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

    int insertWorkorder(MesStrandedwireWorkorder mesStrandedwireWorkorder);

    int cancelInsertWorkorder(BigInteger id);

    int cancelWorkorder(BigInteger id);

    /**
     * * 排序-优先
     */
    int updateSortById(BigInteger id);

    List<MesStrandedwireWorkorder> getListByEquipmentId(BigInteger equipmentId, String axleNumber);
}
