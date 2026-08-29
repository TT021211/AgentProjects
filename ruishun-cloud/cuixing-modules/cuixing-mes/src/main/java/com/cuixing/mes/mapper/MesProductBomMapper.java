package com.cuixing.mes.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.system.api.domain.mes.MesProductBom;
import java.math.BigInteger;

/**
 * 产品BOM表;(mes_product_bom)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Mapper
public interface MesProductBomMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProductBom queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mesProductBom 查询条件
     * @return 对象列表
     */
    List<MesProductBom> selectMesProductBomList(MesProductBom mesProductBom);
    /**
     * 统计总行数
     *
     * @param mesProductBom 查询条件
     * @return 总行数
     */
    long count(MesProductBom mesProductBom);
    /**
     * 新增数据
     *
     * @param mesProductBom 实例对象
     * @return 影响行数
     */
    int insert(MesProductBom mesProductBom);
    /**
     * 批量新增数据
     *
     * @param entities List<MesProductBom> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MesProductBom> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MesProductBom> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MesProductBom> entities);
    /**
     * 更新数据
     *
     * @param mesProductBom 实例对象
     * @return 影响行数
     */
    int update(MesProductBom mesProductBom);
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
}