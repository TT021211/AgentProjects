package com.cuixing.wms.mapper;

import com.cuixing.wms.entity.WmsWasteSilkPackageDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 废丝包详情表(WmsWasteSilkPackageDetails)表数据库访问层
 *
 * @author 阿江
 * @since 2025-04-18 14:20:55
 */
public interface WmsWasteSilkPackageDetailsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsWasteSilkPackageDetails queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param wmsWasteSilkPackageDetails 查询条件
     * @return 对象列表
     */
    List<WmsWasteSilkPackageDetails> selectWmsWasteSilkPackageDetailsList(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails);

    /**
     * 统计总行数
     *
     * @param wmsWasteSilkPackageDetails 查询条件
     * @return 总行数
     */
    long count(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails);

    /**
     * 新增数据
     *
     * @param wmsWasteSilkPackageDetails 实例对象
     * @return 影响行数
     */
    int insert(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsWasteSilkPackageDetails> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WmsWasteSilkPackageDetails> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsWasteSilkPackageDetails> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WmsWasteSilkPackageDetails> entities);

    /**
     * 修改数据
     *
     * @param wmsWasteSilkPackageDetails 实例对象
     * @return 影响行数
     */
    int update(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

