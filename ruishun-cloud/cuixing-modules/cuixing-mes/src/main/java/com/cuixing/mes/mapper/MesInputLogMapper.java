package com.cuixing.mes.mapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.cuixing.mes.entity.MesOuputLog;
import com.cuixing.system.api.domain.md.MdItem;
import com.cuixing.system.api.domain.md.MdProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.mes.entity.MesInputLog;
import java.math.BigInteger;
import java.util.Set;

/**
 * 投料记录表;(mes_input_log)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Mapper
public interface MesInputLogMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesInputLog queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mesInputLog 查询条件
     * @return 对象列表
     */
    List<MesInputLog> selectMesInputLogList(MesInputLog mesInputLog);
    /**
     * 统计总行数
     *
     * @param mesInputLog 查询条件
     * @return 总行数
     */
    long count(MesInputLog mesInputLog);
    /**
     * 新增数据
     *
     * @param mesInputLog 实例对象
     * @return 影响行数
     */
    int insert(MesInputLog mesInputLog);
    /**
     * 批量新增数据
     *
     * @param entities List<MesInputLog> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MesInputLog> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MesInputLog> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MesInputLog> entities);
    /**
     * 更新数据
     *
     * @param mesInputLog 实例对象
     * @return 影响行数
     */
    int update(MesInputLog mesInputLog);
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

    /**
     * 通过机台查询单条数据
     *
     * @param workstationId 主键
     * @return 影响行数
     */
    List<MesInputLog> queryByWorkstationId(@Param("workstationId") BigInteger workstationId);

    /**
     * 通过机台、轴号查询单条数据
     *
     * @param workstationId 主键
     * @return 影响行数
     */
    MesInputLog queryByWorkstationIdAndAxleNumber(@Param("workstationId") BigInteger workstationId, @Param("axleNumber") String axleNumber);

    MesInputLog getInfoByWorkstationIdAndAlexPositionNumberAndAxleNumber(@Param("workstationId")BigInteger workstationId, @Param("alexPositionNumber")Integer alexPositionNumber,@Param("axleNumber")String axleNumber);
    MesInputLog getInfoByWorkstationIdAndAxleNumber(@Param("workstationId")BigInteger workstationId,@Param("axleNumber")String axleNumber,@Param("workorderSn")String workorderSn);
    MesInputLog getInfoByWorkstationCodeAndWorkorderSn(@Param("workstationCode")String workstationCode,@Param("workorderSn")String workorderSn);

    int updateWorkorderComplete(@Param("id") String id);

    MesInputLog getInfoByInputSn(String inputSn);

    int insertBatch(@Param("list") List<MesInputLog> list);

    MesInputLog getWiringInputLog();

    List<MesOuputLog> findByWorkOrderIdAndEquipmentAndTimeRange(@Param("startTime") Date startTime);

    MdProduct selectMdProduct(@Param("productId") BigInteger productId);

    List<MesInputLog> queryByIds(@Param("inputIds") Set<BigInteger> inputIds);

    //MdItem selectMdItem(BigInteger productId);
}
