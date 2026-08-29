package com.cuixing.mes.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.mes.entity.MesProcessStandard;
import org.apache.ibatis.annotations.Param;

/**
 * 工艺标准;(mes_process_standard)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-7-23
 */
@Mapper
public interface MesProcessStandardMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProcessStandard queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mesProcessStandard 查询条件
     * @return 对象列表
     */
    List<MesProcessStandard> selectMesProcessStandardList(MesProcessStandard mesProcessStandard);
    /** 
     * 新增数据
     *
     * @param mesProcessStandard 实例对象
     * @return 影响行数
     */
    int insert(MesProcessStandard mesProcessStandard);
    
    /** 
     * 更新数据
     *
     * @param mesProcessStandard 实例对象
     * @return 影响行数
     */
    int update(MesProcessStandard mesProcessStandard);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

     /**
      * * 查询标准(内控、国家、客户)
      * @param standardCode
      * @return
      */
     List<MesProcessStandard> getMesProcessStandardList(@Param("standardCode") String standardCode);
 }