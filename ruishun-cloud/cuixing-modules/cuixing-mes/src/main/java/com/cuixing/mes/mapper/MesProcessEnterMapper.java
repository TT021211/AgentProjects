package com.cuixing.mes.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.mes.entity.MesProcessEnter;
import org.apache.ibatis.annotations.Param;

/**
 * 工艺录入;(mes_process_enter)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-6
 */
@Mapper
public interface MesProcessEnterMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProcessEnter queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mesProcessEnter 查询条件
     * @return 对象列表
     */
    List<MesProcessEnter> selectMesProcessEnterList(MesProcessEnter mesProcessEnter);
    /** 
     * 新增数据
     *
     * @param mesProcessEnter 实例对象
     * @return 影响行数
     */
    int insert(MesProcessEnter mesProcessEnter);
    
    /** 
     * 更新数据
     *
     * @param mesProcessEnter 实例对象
     * @return 影响行数
     */
    int update(MesProcessEnter mesProcessEnter);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

    MesProcessEnter getInfoByEquipmentIdAndSpecification(@Param("equipmentId") BigInteger equipmentId,@Param("productSpecification") String productSpecification);

    MesProcessEnter getInfoByEquipmentCodeAndOther(@Param("equipmentCode") String equipmentCode, @Param("productSpecification") String productSpecification,@Param("productModel")  String productModel,@Param("attr1") String attr1);
}