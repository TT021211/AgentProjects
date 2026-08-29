package com.cuixing.mes.mapper;


import com.cuixing.common.core.domain.R;
import com.cuixing.mes.entity.MesShiftSchedule;
import com.cuixing.system.api.domain.md.MdAccessory;
import com.cuixing.system.api.domain.md.MdItem;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * 生产交班表(MesShiftSchedule)表数据库访问层
 *
 * @author 阿江
 * @since 2025-03-24 11:18:54
 */
public interface MesShiftScheduleMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesShiftSchedule queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param mesShiftSchedule 查询条件
     * @return 对象列表
     */
    List<MesShiftSchedule> selectMesShiftScheduleList(MesShiftSchedule mesShiftSchedule);

    /**
     * 统计总行数
     *
     * @param mesShiftSchedule 查询条件
     * @return 总行数
     */
    long count(MesShiftSchedule mesShiftSchedule);

    /**
     * 新增数据
     *
     * @param mesShiftSchedule 实例对象
     * @return 影响行数
     */
    int insert(MesShiftSchedule mesShiftSchedule);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MesShiftSchedule> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MesShiftSchedule> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MesShiftSchedule> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MesShiftSchedule> entities);

    /**
     * 修改数据
     *
     * @param mesShiftSchedule 实例对象
     * @return 影响行数
     */
    int update(MesShiftSchedule mesShiftSchedule);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    MesShiftSchedule getInfoByWorkstationCode(@Param("workstationCode") String workstationCode, @Param("workorderSn") String workorderSn);

    MesShiftSchedule selectLastInsert(@Param("workstationCode")String workstationCode);

    MesShiftSchedule getInfoByWorkstationCodeToGy(@Param("workstationCode") String workstationCode, @Param("workorderSn") String workorderSn,@Param("alexPositionNumber") String alexPositionNumber, @Param("alexNumber") String alexNumber);

    MesShiftSchedule selectLastInsertToGy(@Param("workstationCode") String workstationCode, @Param("alexPositionNumber") Integer alexPositionNumber,@Param("alexNumber") String alexNumber);


    List<MdAccessory> queryByIds(@Param("insertedIds") Set<BigInteger> insertedIds);

    List<MdItem> getItems(@Param("outputIds") Set<BigInteger> outputIds);

    List<WmsWarehouse> getInfoByWorkstageIds(@Param("workstageIds") Set<BigInteger> workstageIds);
}

