package com.cuixing.eq.service.impl;

import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.eq.entity.*;
import com.cuixing.eq.mapper.EqEquipmentMapper;
import com.cuixing.eq.mapper.EqItemGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.eq.mapper.EqItemGroupDetailMapper;
import com.cuixing.eq.service.IEqItemGroupDetailService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
 /**
 * 组别详情;(eq_item_group_detail)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-7-5
 */
@Service
public class EqItemGroupDetailServiceImpl implements IEqItemGroupDetailService{
    @Autowired
    private EqItemGroupDetailMapper eqItemGroupDetailMapper;

    @Autowired
    private EqEquipmentMapper eqEquipmentMapper;

    @Autowired
    private EqItemGroupMapper eqItemGroupMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqItemGroupDetail queryById(BigInteger id){
        return eqItemGroupDetailMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqItemGroupDetail 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqItemGroupDetail> selectEqItemGroupDetailList(EqItemGroupDetail eqItemGroupDetail){
       return eqItemGroupDetailMapper.selectEqItemGroupDetailList(eqItemGroupDetail);
    }
    
    /** 
     * 新增数据
     *
     * @param eqItemGroupDetail 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqItemGroupDetail eqItemGroupDetail){
    	eqItemGroupDetail.setCreateTime(DateUtils.getNowDate());
		eqItemGroupDetail.setCreateBy(SecurityUtils.getUsername());
        return eqItemGroupDetailMapper.insert(eqItemGroupDetail);
    }
    
    /** 
     * 更新数据
     *
     * @param eqItemGroupDetail 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqItemGroupDetail eqItemGroupDetail){
        eqItemGroupDetail.setUpdateTime(DateUtils.getNowDate());
		eqItemGroupDetail.setUpdateBy(SecurityUtils.getUsername());
        return eqItemGroupDetailMapper.update(eqItemGroupDetail);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        //删除的数量
        int totalEquipment = ids.length;
        //通过id查询项目类型
        EqItemGroupDetail eqItemGroupDetail = eqItemGroupDetailMapper.queryById(new BigInteger(ids[0].toString()));
        EqItemGroup eqItemGroup = eqItemGroupMapper.queryById(eqItemGroupDetail.getGroupId());
        eqItemGroup.setTotalEquipment(eqItemGroup.getTotalEquipment()-totalEquipment);
        eqItemGroupMapper.update(eqItemGroup);
        //更新项目类型数量
        return eqItemGroupDetailMapper.deleteByIds(ids);
    }

     /**
      * 批量新增数据
      */
     @Override
     @Transactional
     public int allocatedUserByIds(Long[] equipmentIds, EqItemGroup eqItemGroup) {
         int result = 0;
         //获取
         for (Long equipmentId : equipmentIds) {
             EqEquipment eqEquipment = eqEquipmentMapper.queryById(new BigInteger(equipmentId.toString()));
             EqItemGroupDetail eqItemGroupDetail = new EqItemGroupDetail();
             eqItemGroupDetail.setCreateTime(DateUtils.getNowDate());
             eqItemGroupDetail.setCreateBy(SecurityUtils.getUsername());
             eqItemGroupDetail.setGroupId(eqItemGroup.getId());
             eqItemGroupDetail.setGroupName(eqItemGroup.getGroupName());
             eqItemGroupDetail.setHeadId(eqItemGroup.getHeadId());
             eqItemGroupDetail.setHeadName(eqItemGroup.getHeadName());
             eqItemGroupDetail.setEquipmentId(eqEquipment.getId());
             eqItemGroupDetail.setEquipmentCode(eqEquipment.getEquipmentCode());
             eqItemGroupDetail.setEquipmentName(eqEquipment.getEquipmentName());
             eqItemGroupDetail.setEquipmentModel(eqEquipment.getEquipmentModel());
             eqItemGroupDetail.setTypeId(eqEquipment.getWorkbenchId());
             eqItemGroupDetail.setTypeName(eqEquipment.getWorkbenchType());
             eqItemGroupDetail.setPosition(eqEquipment.getPosition());
             result += eqItemGroupDetailMapper.insert(eqItemGroupDetail);
         }
         if (result != equipmentIds.length){
             throw new EqException("批量添加失败");
         }
         //新增的数量
         int num = equipmentIds.length;
         //更新项目类型数量
         EqItemGroup newEqItemGroup = eqItemGroupMapper.queryById(eqItemGroup.getId());
         newEqItemGroup.setTotalEquipment(newEqItemGroup.getTotalEquipment() + num);
         eqItemGroupMapper.update(newEqItemGroup);
         return result;
     }
}