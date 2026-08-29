package com.cuixing.eq.service;

import java.math.BigInteger;
import com.cuixing.eq.entity.EqEquipmentType;
import java.util.List;

 /**
 * 设备类别设置;(eq_equipment_type)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-17
 */
public interface IEqEquipmentTypeService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqEquipmentType queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqEquipmentType 筛选条件
     * @return 查询结果
     */
    List<EqEquipmentType> selectEqEquipmentTypeList(EqEquipmentType eqEquipmentType);
    /** 
     * 新增数据
     *
     * @param eqEquipmentType 实例对象
     * @return 实例对象
     */
    int insert(EqEquipmentType eqEquipmentType);
    /** 
     * 更新数据
     *
     * @param eqEquipmentType 实例对象
     * @return 实例对象
     */
    int update(EqEquipmentType eqEquipmentType);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}