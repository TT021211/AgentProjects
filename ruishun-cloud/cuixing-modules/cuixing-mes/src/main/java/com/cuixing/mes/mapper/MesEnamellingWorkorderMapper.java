package com.cuixing.mes.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.mes.entity.MesEnamellingWorkorder;
import org.apache.ibatis.annotations.Param;

/**
 * 漆包工单;(mes_enamelling_workorder)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Mapper
public interface MesEnamellingWorkorderMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesEnamellingWorkorder queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mesEnamellingWorkorder 查询条件
     * @return 对象列表
     */
    List<MesEnamellingWorkorder> selectMesEnamellingWorkorderList(MesEnamellingWorkorder mesEnamellingWorkorder);
    /** 
     * 新增数据
     *
     * @param mesEnamellingWorkorder 实例对象
     * @return 影响行数
     */
    int insert(MesEnamellingWorkorder mesEnamellingWorkorder);
    
    /** 
     * 更新数据
     *
     * @param mesEnamellingWorkorder 实例对象
     * @return 影响行数
     */
    int update(MesEnamellingWorkorder mesEnamellingWorkorder);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

    /**
     * 排序优先
     * @return
     */
    int updateSortById(@Param("id")BigInteger id);

    int cancelById(@Param("id")BigInteger id);

    int updateStatusById(@Param("id")BigInteger id, @Param("status")String status);

    MesEnamellingWorkorder queryByWorkorderId(@Param("workorderId")BigInteger workorderId);

    /**
     *  通过机台id查询该机台所有已排产、生产中列表
     * @param equipmentId,axleNumber
     * @return
     */
    List<MesEnamellingWorkorder> getListByEquipmentId(@Param("equipmentId")BigInteger equipmentId,@Param("axleNumber")String axleNumber);

    /**
     * 根据状态查找工单数量
     */
    int getWorkorderCountByStatus(@Param("status") String status);

    /**
     * 今日排产工单数量
     */
    int getTodaySchedulingNum();


    List<MesEnamellingWorkorder> getEnamellingWorkorderList();
}