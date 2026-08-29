package com.cuixing.mes.mapper;

import java.util.List;

import com.cuixing.mes.entity.vo.MesPageWeek;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.mes.entity.MesOuputLog;
import java.math.BigInteger;
import java.util.Set;

/**
 * 报工记录表;(mes_ouput_log)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Mapper
public interface MesOuputLogMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesOuputLog queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mesOuputLog 查询条件
     * @return 对象列表
     */
    List<MesOuputLog> selectMesOuputLogList(MesOuputLog mesOuputLog);
    /**
     * 统计总行数
     *
     * @param mesOuputLog 查询条件
     * @return 总行数
     */
    long count(MesOuputLog mesOuputLog);
    /**
     * 新增数据
     *
     * @param mesOuputLog 实例对象
     * @return 影响行数
     */
    int insert(MesOuputLog mesOuputLog);
    /**
     * 批量新增数据
     *
     * @param entities List<MesOuputLog> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MesOuputLog> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MesOuputLog> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MesOuputLog> entities);
    /**
     * 更新数据
     *
     * @param mesOuputLog 实例对象
     * @return 影响行数
     */
    int update(MesOuputLog mesOuputLog);
    /**
     * 通过主键逻辑删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(BigInteger id);
    /**
     * 通过主键批量逻辑删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
    /**
     * 通过主键物理删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int removeById(BigInteger id);

    MesOuputLog getInfoByOutputSn(String outputSn);

    /**
     * 查询该机台的该轴号的最新报工记录的批次
     */
    MesOuputLog getInfoByWorkstationIdAndAlexPositionNumberAndAlexNumber(@Param("workstationId") BigInteger workstationId, @Param("alexPositionNumber") Integer alexPositionNumber,@Param("alexNumber") String alexNumber);

    MesOuputLog getInfoByWorkstationCode(@Param("workstationCode") String workstationCode);

    List<MesOuputLog> selectMesOuputLogWmsList(MesOuputLog mesOuputLog);

    MesOuputLog queryByWorkstageName(@Param("workstationCode") String workstationCode,@Param("workorderSn") String workorderSn);

    List<MesPageWeek> selectPageKanban();

    List<MesOuputLog> selectByTime(@Param("taskType") String taskType);

    List<MesOuputLog> queryByIds(@Param("insertedIds") List<BigInteger> insertedIds);

    List<MesOuputLog> queryLatestByWorkstationCodes(@Param("workstationCodes") Set<String> workstationCodes);

}
