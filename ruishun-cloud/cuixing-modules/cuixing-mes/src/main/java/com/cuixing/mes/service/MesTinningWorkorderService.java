package com.cuixing.mes.service;

import com.cuixing.mes.entity.MesAnnealingWorkorder;
import com.cuixing.mes.entity.MesDoublewireWorkorder;
import com.cuixing.mes.entity.MesTinningWorkorder;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * 镀锡工单(MesTinningWorkorder)表服务接口
 *
 * @author makejava
 * @since 2024-11-25 11:04:48
 */
public interface MesTinningWorkorderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesTinningWorkorder queryById(BigInteger id);

    /**
     * 分页查询
     *
     * @param mesTinningWorkorder 筛选条件
     * @return 查询结果
     */
    List<MesTinningWorkorder> selectMesTinningWorkorderList(MesTinningWorkorder mesTinningWorkorder);

    /**
     * 新增数据
     *
     * @param mesTinningWorkorder 实例对象
     * @return 实例对象
     */
    int insert(MesTinningWorkorder mesTinningWorkorder) throws ParseException;

    /**
     * 修改数据
     *
     * @param mesTinningWorkorder 实例对象
     * @return 实例对象
     */
    MesTinningWorkorder update(MesTinningWorkorder mesTinningWorkorder);

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

    int insertWorkorder(MesTinningWorkorder mesTinningWorkorder);

    int cancelInsertWorkorder(BigInteger id);

    int cancelWorkorder(BigInteger id);

    /**
     * * 排序-优先
     */
    int updateSortById(BigInteger id);

    List<MesTinningWorkorder> getListByEquipmentId(BigInteger equipmentId, String axleNumber);

    MesTinningWorkorder queryByTinningWorkorderCode(String workorderCode);

    int batchUpdate(ArrayList<MesTinningWorkorder> mesTinningWorkorders);
}
