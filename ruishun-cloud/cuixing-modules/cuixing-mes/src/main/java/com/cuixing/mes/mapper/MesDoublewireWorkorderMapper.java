package com.cuixing.mes.mapper;

import com.cuixing.mes.entity.MesAnnealingWorkorder;
import com.cuixing.mes.entity.MesDoublewireWorkorder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

/**
 * 并丝工单(MesDoublewireWorkorder)表数据库访问层
 *
 * @author makejava
 * @since 2024-11-25 11:04:48
 */
@Mapper
public interface MesDoublewireWorkorderMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesDoublewireWorkorder queryById(BigInteger id);

    /**
     * 查询指定行数据
     *
     * @param mesDoublewireWorkorder 查询条件
     * @return 对象列表
     */
    List<MesDoublewireWorkorder> selectMesDoublewireWorkorderList(MesDoublewireWorkorder mesDoublewireWorkorder);

    /**
     * 统计总行数
     *
     * @param mesDoublewireWorkorder 查询条件
     * @return 总行数
     */
    long count(MesDoublewireWorkorder mesDoublewireWorkorder);

    /**
     * 新增数据
     *
     * @param mesDoublewireWorkorder 实例对象
     * @return 影响行数
     */
    int insert(MesDoublewireWorkorder mesDoublewireWorkorder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MesDoublewireWorkorder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MesDoublewireWorkorder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MesDoublewireWorkorder> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MesDoublewireWorkorder> entities);

    /**
     * 修改数据
     *
     * @param mesDoublewireWorkorder 实例对象
     * @return 影响行数
     */
    int update(MesDoublewireWorkorder mesDoublewireWorkorder);

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
    List<MesDoublewireWorkorder> getListByEquipmentId(@Param("equipmentId")BigInteger equipmentId,@Param("axleNumber")String axleNumber);

    /**
     * 根据状态查询数量
     * @param status
     * @return
     */
    int countByStatus(@Param("status")String status);

    int getTodaySchedulingNum();

    List<MesDoublewireWorkorder> getMesDoublewireWorkorderList();

}

