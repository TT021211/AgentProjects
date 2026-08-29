package com.cuixing.eq.mapper;

import com.cuixing.eq.entity.EqEquipment;
import com.cuixing.eq.entity.vo.EqKanban;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
* 设备台账;(eq_equipment)表数据库访问层
* @author : http://www.chiner.pro
* @date : 2024-6-17
*/
@Mapper
public interface EqEquipmentMapper {
   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   EqEquipment queryById(BigInteger id);
   /**
    * 分页查询指定行数据
    *
    * @param eqEquipment 查询条件
    * @return 对象列表
    */
   List<EqEquipment> selectEqEquipmentList(EqEquipment eqEquipment);
   /**
    * 新增数据
    *
    * @param eqEquipment 实例对象
    * @return 影响行数
    */
   int insert(EqEquipment eqEquipment);

   /**
    * 更新数据
    *
    * @param eqEquipment 实例对象
    * @return 影响行数
    */
   int update(EqEquipment eqEquipment);
   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 影响行数
    */
   int deleteByIds(BigInteger[] ids);

    /**
     * 获取所有未关联设备列表
     * @return
     */
    List<EqEquipment> selectEquipmentList();

    /**
     * 根据机台类型查找对应状态的设备数量
     */
    EqKanban getCountByWorkbenchTypeAndStatus();

    List<EqEquipment> selectEquipmentList2();
}