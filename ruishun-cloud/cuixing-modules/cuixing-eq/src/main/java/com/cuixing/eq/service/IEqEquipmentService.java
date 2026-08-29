package com.cuixing.eq.service;

import com.cuixing.eq.entity.EqEquipment;
import com.cuixing.eq.entity.vo.EqKanban;

import java.math.BigInteger;
import java.util.List;

/**
* 设备台账;(eq_equipment)表服务接口
* @author : http://www.chiner.pro
* @date : 2024-6-17
*/
public interface IEqEquipmentService {
   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   EqEquipment queryById(BigInteger id);
   /**
    * 分页查询
    *
    * @param eqEquipment 筛选条件
    * @return 查询结果
    */
   List<EqEquipment> selectEqEquipmentList(EqEquipment eqEquipment);
   /**
    * 新增数据
    *
    * @param eqEquipment 实例对象
    * @return 实例对象
    */
   int insert(EqEquipment eqEquipment);
   /**
    * 更新数据
    *
    * @param eqEquipment 实例对象
    * @return 实例对象
    */
   int update(EqEquipment eqEquipment);
   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 是否成功
    */
   int deleteByIds(BigInteger[] ids);

    List<EqEquipment> selectEquipmentList();

    EqKanban getEquipmentData();

    List<EqEquipment> selectEqEquipmentList2();

}