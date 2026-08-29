package com.cuixing.mes.service;

import com.cuixing.mes.entity.MesInputLog;
import com.cuixing.mes.entity.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 投料记录表;(mes_input_log)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
public interface IMesInputLogService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesInputLog queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mesInputLog 筛选条件
     * @return 查询结果
     */
    List<MesInputLog> selectMesInputLogList(MesInputLog mesInputLog);
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
     * @param mesInputLogs 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MesInputLog> mesInputLogs);
    /**
     * 更新数据
     *
     * @param mesInputLog 实例对象
     * @return 影响行数
     */
    int update(MesInputLog mesInputLog);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigInteger id);
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    boolean deleteByIds(BigInteger[] ids);

    List<MesInputLog> queryByWorkstationId(BigInteger workstationId);

    MesInputLog queryByWorkstationIdAndAxleNumber(BigInteger workstationId, String axleNumber);

    MesInputLog getInfoByWorkstationIdAndAlexPositionNumberAndAxleNumber(BigInteger workstationId, Integer alexPositionNumber,String axleNumber);
    MesInputLog getInfoByWorkstationIdAndAxleNumber(BigInteger workstationId,String axleNumber,String workorderSn);
    MesInputLog getInfoByWorkstationCodeAndWorkorderSn(String workstationCode,String workorderSn);

    List<EnamellingWorkorderVo> getWorkOdrerListByMesInputLogs(List<MesInputLog> mesInputLogs);

    List<AnnealingWorkorderVo> getWorkOdrerListByMesInputLogsToA(List<MesInputLog> mesInputLogs);
    List<DoublewireWorkorderVo> getWorkOdrerListByMesInputLogsToD(List<MesInputLog> mesInputLogs);
    List<StrandedwireWorkorderVo> getWorkOdrerListByMesInputLogsToS(List<MesInputLog> mesInputLogs);
    List<TinningWorkorderVo> getWorkOdrerListByMesInputLogsToT(List<MesInputLog> mesInputLogs);

    int updateWorkorderComplete(String id);

    MesInputLog getInfoByInputSn(String inputSn);

    MesInputLog getWiringInputLog();

    List<MesInputLog> queryByIds(Set<BigInteger> inputIds);
}
