package com.cuixing.mes.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import com.cuixing.mes.entity.MesWiredrawingWorkorder;
import org.apache.ibatis.annotations.Param;

/**
 * 拉丝工单;(mes_wiredrawing_workorder)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Mapper
public interface MesWiredrawingWorkorderMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesWiredrawingWorkorder queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mesWiredrawingWorkorder 查询条件
     * @return 对象列表
     */
    List<MesWiredrawingWorkorder> selectMesWiredrawingWorkorderList(MesWiredrawingWorkorder mesWiredrawingWorkorder);
    /**
     * 新增数据
     *
     * @param mesWiredrawingWorkorder 实例对象
     * @return 影响行数
     */
    int insert(MesWiredrawingWorkorder mesWiredrawingWorkorder);

    /**
     * 更新数据
     *
     * @param mesWiredrawingWorkorder 实例对象
     * @return 影响行数
     */
    int update(MesWiredrawingWorkorder mesWiredrawingWorkorder);
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

    int sendChangeSpecification(@Param("id")BigInteger id);

    MesWiredrawingWorkorder getInfoByWiredrawingCode(@Param("wiredrawingCode") String wiredrawingCode);

    /**
     * 根据状态查询数量
     * @param status
     * @return
     */
    int countByStatus(@Param("status")String status);

    int getTodaySchedulingNum();

    List<MesWiredrawingWorkorder> getMesWiredrawingWorkorderList();

    int batchUpdate(@Param("mesWiredrawingWorkorders") List<MesWiredrawingWorkorder> mesWiredrawingWorkorders);
}
