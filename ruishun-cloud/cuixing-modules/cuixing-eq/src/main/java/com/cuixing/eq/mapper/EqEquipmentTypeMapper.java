package com.cuixing.eq.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqEquipmentType;

 /**
 * 设备类别设置;(eq_equipment_type)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-17
 */
@Mapper
public interface EqEquipmentTypeMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqEquipmentType queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqEquipmentType 查询条件
     * @return 对象列表
     */
    List<EqEquipmentType> selectEqEquipmentTypeList(EqEquipmentType eqEquipmentType);
    /** 
     * 新增数据
     *
     * @param eqEquipmentType 实例对象
     * @return 影响行数
     */
    int insert(EqEquipmentType eqEquipmentType);
    
    /** 
     * 更新数据
     *
     * @param eqEquipmentType 实例对象
     * @return 影响行数
     */
    int update(EqEquipmentType eqEquipmentType);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}