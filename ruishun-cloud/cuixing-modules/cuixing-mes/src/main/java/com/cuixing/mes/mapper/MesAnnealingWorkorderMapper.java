package com.cuixing.mes.mapper;

import com.cuixing.mes.entity.MesAnnealingWorkorder;
import com.cuixing.mes.entity.MesEnamellingWorkorder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 退火工单(MesAnnealingWorkorder)表数据库访问层
 *
 * @author makejava
 * @since 2024-11-25 10:30:34
 */
@Mapper
public interface MesAnnealingWorkorderMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesAnnealingWorkorder queryById(BigInteger id);

    /**
     * 查询指定行数据
     *
     * @param mesAnnealingWorkorder 查询条件
     * @return 对象列表
     */
    List<MesAnnealingWorkorder> selectMesAnnealingWorkorderList(MesAnnealingWorkorder mesAnnealingWorkorder);

    /**
     * 统计总行数
     *
     * @param mesAnnealingWorkorder 查询条件
     * @return 总行数
     */
    long count(MesAnnealingWorkorder mesAnnealingWorkorder);

    /**
     * 新增数据
     *
     * @param mesAnnealingWorkorder 实例对象
     * @return 影响行数
     */
    int insert(MesAnnealingWorkorder mesAnnealingWorkorder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MesAnnealingWorkorder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MesAnnealingWorkorder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MesAnnealingWorkorder> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MesAnnealingWorkorder> entities);

    /**
     * 修改数据
     *
     * @param mesAnnealingWorkorder 实例对象
     * @return 影响行数
     */
    int update(MesAnnealingWorkorder mesAnnealingWorkorder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(BigInteger id);

    int updateSortById(@Param("id")BigInteger id);

    int deleteByIds(BigInteger[] ids);

    int cancelById(@Param("id")BigInteger id);

    int updateStatusById(@Param("id")BigInteger id, @Param("status")String status);

    /**
     *  通过机台id查询该机台所有已排产、生产中列表
     * @param equipmentId,axleNumber
     * @return
     */
    List<MesAnnealingWorkorder> getListByEquipmentId(@Param("equipmentId")BigInteger equipmentId,@Param("axleNumber")String axleNumber);

    /**
     * 统计状态为状态( 0:已排产 1:生产中 2:已完成3：已作废)的数量
     * @param status
     * @return
     */
    int countByStatus(@Param("status")String status);

    int getTodaySchedulingNum();

    List<MesAnnealingWorkorder> getMesAnnealingWorkorderList();

    MesAnnealingWorkorder queryByMesAnnealingWorkorderCode(String workorderCode);

    int batchUpdate(@Param("mesAnnealingWorkorders") List<MesAnnealingWorkorder> list);
}

