package com.cuixing.wms.mapper;

import com.cuixing.wms.entity.WmsWeighRecord;
import com.cuixing.wms.entity.vo.WmsWeighRecordVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 仓储管理---称重记录(WmsWeighRecord)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-21 11:44:28
 */
@Mapper
public interface WmsWeighRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsWeighRecord queryById(Long id);

    /**
     * 查询当天三个最大值
     *
     * @return
     */
    WmsWeighRecord selectMaxValues(@Param("username") String username);

    /**
     * 查询指定行数据
     *
     * @param wmsWeighRecord 查询条件
     * @return 对象列表
     */
    List<WmsWeighRecord> selectWmsWeighRecordList(WmsWeighRecord wmsWeighRecord);

    /**
     * 统计总行数
     *
     * @param wmsWeighRecord 查询条件
     * @return 总行数
     */
    long count(WmsWeighRecord wmsWeighRecord);

    /**
     * 新增数据
     *
     * @param wmsWeighRecord 实例对象
     * @return 影响行数
     */
    int insert(WmsWeighRecord wmsWeighRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsWeighRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WmsWeighRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsWeighRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WmsWeighRecord> entities);

    /**
     * 修改数据
     *
     * @param wmsWeighRecord 实例对象
     * @return 影响行数
     */
    int update(WmsWeighRecord wmsWeighRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 从托中移除关联信息(取消关联盘、箱记录)
     */
    int disassociationToRecord(WmsWeighRecord wmsWeighRecord);

    List<WmsWeighRecord> selectWmsWeighRecordToBoxCodeList(@Param("boxCode") String boxCode);

    List<WmsWeighRecord> queryYesterday(@Param("createBy") String username);

    boolean deleteByBoxCode(@Param("boxCode")String boxCode);

    WmsWeighRecord selectWmsWeighRecordToParams(WmsWeighRecord wmsWeighRecord);

    boolean disassociationToRecord2(WmsWeighRecord wmsWeighRecord);

    List<WmsWeighRecord> selectByBoxCode(@Param("boxCode")String boxCode);

    WmsWeighRecord selectWmsWeighRecordToParams2(WmsWeighRecord wmsWeighRecord);

    List<WmsWeighRecordVo> queryBySpecForDate(@Param("date") String date,@Param("wmsSpec") String wmsSpec,@Param("start") int start,@Param("end") int end);

    List<WmsWeighRecordVo> queryBySpecForMonth(@Param("date") String date,@Param("wmsSpec") String wmsSpec,@Param("start") int start,@Param("end") int end);

    List<WmsWeighRecordVo>  queryBySpecForYear(@Param("date")String date,@Param("wmsSpec") String wmsSpec,@Param("start") int start,@Param("end") int end);

    List<WmsWeighRecord> selectCountByBoxCodeAndCy(@Param("boxCode")String boxCode,@Param("productName")String productName,@Param("itemCode")String itemCode,@Param("wmsSpec")String wmsSpec,@Param("batchNumber2")String batchNumber2,@Param("installationStatus")String installationStatus,@Param("createBy")String createBy);
}

