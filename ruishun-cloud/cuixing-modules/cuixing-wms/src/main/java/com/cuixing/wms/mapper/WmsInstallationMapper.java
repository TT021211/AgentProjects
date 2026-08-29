package com.cuixing.wms.mapper;

import com.cuixing.wms.entity.WmsInstallation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (WmsInstallation)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-21 11:44:29
 */
@Mapper
public interface WmsInstallationMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsInstallation queryById(Long id);

    WmsInstallation queryByCode(String installationCode);

    WmsInstallation queryMaxCode();

    /**
     * 查询指定行数据
     *
     * @param wmsInstallation 查询条件
     * @return 对象列表
     */
    List<WmsInstallation> selectWmsInstallationList(WmsInstallation wmsInstallation);

    /**
     * 统计总行数
     *
     * @param wmsInstallation 查询条件
     * @return 总行数
     */
    long count(WmsInstallation wmsInstallation);

    /**
     * 新增数据
     *
     * @param wmsInstallation 实例对象
     * @return 影响行数
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(WmsInstallation wmsInstallation);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsInstallation> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WmsInstallation> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsInstallation> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WmsInstallation> entities);

    /**
     * 修改数据
     *
     * @param wmsInstallation 实例对象
     * @return 影响行数
     */
    int update(WmsInstallation wmsInstallation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    void initNetWeight(Long id);

    void initGrossWeight(Long id);
}

