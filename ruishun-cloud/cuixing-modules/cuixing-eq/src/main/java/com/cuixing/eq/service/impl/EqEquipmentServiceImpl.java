package com.cuixing.eq.service.impl;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.eq.entity.EqEquipment;
import com.cuixing.eq.entity.EqRepairReport;
import com.cuixing.eq.entity.vo.EqKanban;
import com.cuixing.eq.mapper.EqEquipmentMapper;
import com.cuixing.eq.mapper.EqListMapper;
import com.cuixing.eq.mapper.EqRepairReportMapper;
import com.cuixing.eq.service.IEqEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
* 设备台账;(eq_equipment)表服务实现类
* @author : http://www.chiner.pro
* @date : 2024-6-17
*/
@Service
public class EqEquipmentServiceImpl implements IEqEquipmentService{
   @Autowired
   private EqEquipmentMapper eqEquipmentMapper;

   @Autowired
   private EqListMapper eqListMapper;

   @Autowired
   private EqRepairReportMapper eqRepairReportMapper;

   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   @Override
   public EqEquipment queryById(BigInteger id){
       return eqEquipmentMapper.queryById(id);
   }

   /**
    * 分页查询
    *
    * @param eqEquipment 筛选条件
    * @return 查询结果
    */
   @Override
   public List<EqEquipment> selectEqEquipmentList(EqEquipment eqEquipment){
      return eqEquipmentMapper.selectEqEquipmentList(eqEquipment);
   }

   /**
    * 新增数据
    *
    * @param eqEquipment 实例对象
    * @return 实例对象
    */
   @Override
   public int insert(EqEquipment eqEquipment){
       eqEquipment.setCreateTime(DateUtils.getNowDate());
       eqEquipment.setCreateBy(SecurityUtils.getUsername());
       return eqEquipmentMapper.insert(eqEquipment);
   }

   /**
    * 更新数据
    *
    * @param eqEquipment 实例对象
    * @return 实例对象
    */
   @Override
   public int update(EqEquipment eqEquipment){
       eqEquipment.setUpdateTime(DateUtils.getNowDate());
       eqEquipment.setUpdateBy(SecurityUtils.getUsername());
       return eqEquipmentMapper.update(eqEquipment);
   }
   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 是否成功
    */
   @Override
   public int deleteByIds(BigInteger[] ids){
       return eqEquipmentMapper.deleteByIds(ids);
   }

    @Override
    public List<EqEquipment> selectEquipmentList() {
        return eqEquipmentMapper.selectEquipmentList();
    }

    @Override
    public EqKanban getEquipmentData() {
        EqKanban eqKanban = eqRepairReportMapper.getRepairReportKanban();
        int checkCount = eqListMapper.getListByPlanType("0");
        int maintainCount = eqListMapper.getListByPlanType("1");
        eqKanban.setCheckCount(checkCount);
        eqKanban.setMaintainCount(maintainCount);
        List<EqRepairReport> statusNotCompleteList = eqRepairReportMapper.getStatusNotCompleteList();
        //查询最近2天已完成的数据
        List<EqRepairReport> listTowDay = eqRepairReportMapper.getListTowDay();
        eqKanban.setCompleteRepairReportList(listTowDay);
        eqKanban.setEqRepairReportList(statusNotCompleteList);
        return eqKanban;
    }

    @Override
    public List<EqEquipment> selectEqEquipmentList2() {
        return this.eqEquipmentMapper.selectEquipmentList2();
    }

}