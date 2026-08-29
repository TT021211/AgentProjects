package com.cuixing.mes.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cuixing.mes.entity.MesWorkstage;
import java.math.BigInteger;

/**
 * 工序表;(mes_workstage)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Mapper
public interface MesWorkstageMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesWorkstage queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mesWorkstage 查询条件
     * @return 对象列表
     */
    List<MesWorkstage> selectMesWorkstageList(MesWorkstage mesWorkstage);
    /**
     * 统计总行数
     *
     * @param mesWorkstage 查询条件
     * @return 总行数
     */
    long count(MesWorkstage mesWorkstage);
    /**
     * 新增数据
     *
     * @param mesWorkstage 实例对象
     * @return 影响行数
     */
    int insert(MesWorkstage mesWorkstage);
    /**
     * 批量新增数据
     *
     * @param entities List<MesWorkstage> 实例对象列表
     * @return 影响行数
     */
    // int insertBatch(@Param("entities") List<MesWorkstage> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<MesWorkstage> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MesWorkstage> entities);
    /**
     * 更新数据
     *
     * @param mesWorkstage 实例对象
     * @return 影响行数
     */
    int update(MesWorkstage mesWorkstage);
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