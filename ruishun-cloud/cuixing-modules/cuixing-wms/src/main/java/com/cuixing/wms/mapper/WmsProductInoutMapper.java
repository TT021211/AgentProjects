package com.cuixing.wms.mapper;

import com.cuixing.wms.entity.WmsProductInout;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 成品出入库(WmsProductInout)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
@Mapper
public interface WmsProductInoutMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsProductInout queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param wmsProductInout 查询条件
     * @return 对象列表
     */
    List<WmsProductInout> selectWmsProductInoutList(WmsProductInout wmsProductInout);

    /**
     * 统计总行数
     *
     * @param wmsProductInout 查询条件
     * @return 总行数
     */
    long count(WmsProductInout wmsProductInout);

    /**
     * 新增数据
     *
     * @param wmsProductInout 实例对象
     * @return 影响行数
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(WmsProductInout wmsProductInout);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsProductInout> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WmsProductInout> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsProductInout> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WmsProductInout> entities);

    /**
     * 修改数据
     *
     * @param wmsProductInout 实例对象
     * @return 影响行数
     */
    int update(WmsProductInout wmsProductInout);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 查询最新的入库数据
     */
    WmsProductInout selectMaxValues(@Param("productCode")String productCode, @Param("productName")String productTypeName, @Param("specification")String specification, @Param("batchNumber")String batchNumber);

    WmsProductInout selectMinValues(@Param("productCode")String productCode, @Param("productName")String productTypeName, @Param("specification")String specification, @Param("batchNumber")String batchNumber);

    List<WmsProductInout> selectCountWmsProductInoutList(WmsProductInout wmsProductInout);

    List<WmsProductInout> selectToBoxCodes(@Param("boxCodes")List<String> boxCodes,@Param("documentNumber") String documentNumber);

    int deleteByBoxCode(@Param("boxCode")String boxCode);

    int updateToNull(@Param("id") Long id);

    int updateToDocumentNumber(@Param("id")Long id, @Param("outboundCode")String outboundCode);

    List<WmsProductInout> selectToBoxCodesAndStatus(@Param("boxCodes")List<String> boxCodes,@Param("outboundCode") String outboundCode);

    List<WmsProductInout> selectToBoxCode(@Param("boxCode") String boxCode);

    List<WmsProductInout> findRecordsByBoxCode(@Param("boxCode") String boxCode,
                                             @Param("productName") String productName,
                                             @Param("productSpec") String productSpec,
                                             @Param("productBatchNumber") String productBatchNumber,
                                             @Param("productCode") String productCode);

    int deleteRecordsByBoxCode(@Param("boxCode") String boxCode,
                               @Param("productName") String productName,
                               @Param("productSpec") String productSpec,
                               @Param("productBatchNumber") String productBatchNumber,
                               @Param("productCode") String productCode);

    List<WmsProductInout> findRecordsAfter(@Param("lastDeletedId") Long lastDeletedId,
                                       @Param("productName") String productName,
                                       @Param("productSpec") String productSpec,
                                       @Param("productBatchNumber") String productBatchNumber,
                                       @Param("productCode") String productCode);

    int batchUpdateRecords(@Param("records") List<WmsProductInout> records);

    /**
     * 查找在指定记录ID之前的所有记录，按ID降序排列
     */
    List<WmsProductInout> findRecordsBefore(@Param("id") Long id,
                                            @Param("productName") String productName,
                                            @Param("productSpec") String productSpec,
                                            @Param("productBatchNumber") String productBatchNumber,
                                            @Param("productCode") String productCode);

    WmsProductInout queryByBoxCode(@Param("boxCode") String boxCode);

    void updateWeight(@Param("id") Long id, @Param("countNetWeight") double countNetWeight, @Param("countGrossWeight") double countGrossWeight,@Param("countProductNumber")Integer countProductNumber );
}

