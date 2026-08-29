package com.cuixing.wms.mapper;

import com.cuixing.wms.entity.WmsWasteSilkPackage;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 废丝包主表(WmsWasteSilkPackage)表数据库访问层
 *
 * @author 阿江
 * @since 2025-04-18 14:16:23
 */
public interface WmsWasteSilkPackageMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsWasteSilkPackage queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param wmsWasteSilkPackage 查询条件
     * @return 对象列表
     */
    List<WmsWasteSilkPackage> selectWmsWasteSilkPackageList(WmsWasteSilkPackage wmsWasteSilkPackage);

    /**
     * 统计总行数
     *
     * @param wmsWasteSilkPackage 查询条件
     * @return 总行数
     */
    long count(WmsWasteSilkPackage wmsWasteSilkPackage);

    /**
     * 新增数据
     *
     * @param wmsWasteSilkPackage 实例对象
     * @return 影响行数
     */
    int insert(WmsWasteSilkPackage wmsWasteSilkPackage);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsWasteSilkPackage> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WmsWasteSilkPackage> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsWasteSilkPackage> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WmsWasteSilkPackage> entities);

    /**
     * 修改数据
     *
     * @param wmsWasteSilkPackage 实例对象
     * @return 影响行数
     */
    int update(WmsWasteSilkPackage wmsWasteSilkPackage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    WmsWasteSilkPackage queryByTimeAndStatus(@Param("type") String type);

    WmsWasteSilkPackage queryByCode(@Param("wasteSilkPackageCode") String wasteSilkPackageCode);

    WmsWasteSilkPackage updateStatus(WmsWasteSilkPackage wmsWasteSilkPackage);
}

