package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.RemoteEqService;
import com.cuixing.system.api.domain.eq.EqEquipment;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdWorkbenchWiredrawing;
import com.cuixing.md.mapper.MdWorkbenchWiredrawingMapper;
import com.cuixing.md.service.IMdWorkbenchWiredrawingService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
 /**
 * 机台设置;(md_workbench_wiredrawing)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-4
 */
@Service
public class MdWorkbenchWiredrawingServiceImpl implements IMdWorkbenchWiredrawingService{
    @Autowired
    private MdWorkbenchWiredrawingMapper mdWorkbenchWiredrawingMapper;

    @Autowired
    private RemoteEqService remoteEqService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdWorkbenchWiredrawing queryById(BigInteger id){
        return mdWorkbenchWiredrawingMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdWorkbenchWiredrawing 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdWorkbenchWiredrawing> selectMdWorkbenchWiredrawingList(MdWorkbenchWiredrawing mdWorkbenchWiredrawing){
       return mdWorkbenchWiredrawingMapper.selectMdWorkbenchWiredrawingList(mdWorkbenchWiredrawing);
    }

    /**
     * 新增数据
     *
     * @param mdWorkbenchWiredrawing 实例对象
     * @return 实例对象
     */
    @Override
    //@GlobalTransactional
    public int insert(MdWorkbenchWiredrawing mdWorkbenchWiredrawing){
    	mdWorkbenchWiredrawing.setCreateTime(DateUtils.getNowDate());
		mdWorkbenchWiredrawing.setCreateBy(SecurityUtils.getUsername());
        if (mdWorkbenchWiredrawing.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchWiredrawing.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchWiredrawing.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchWiredrawing.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchWiredrawing.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchWiredrawing.setWorkbenchType(eqEquipment.getWorkbenchType());
        }

        return mdWorkbenchWiredrawingMapper.insert(mdWorkbenchWiredrawing);
    }

    /**
     * 更新数据
     *
     * @param mdWorkbenchWiredrawing 实例对象
     * @return 实例对象
     */
    @Override
    //@GlobalTransactional
    public int update(MdWorkbenchWiredrawing mdWorkbenchWiredrawing){
        mdWorkbenchWiredrawing.setUpdateTime(DateUtils.getNowDate());
		mdWorkbenchWiredrawing.setUpdateBy(SecurityUtils.getUsername());
        //修改之前关联的机台为未关联
        MdWorkbenchWiredrawing updateWiredrawing = mdWorkbenchWiredrawingMapper.queryById(mdWorkbenchWiredrawing.getId());
        EqEquipment updateEquipment = remoteEqService.queryById(updateWiredrawing.getEquipmentId(), SecurityConstants.INNER).getData();
        if (updateEquipment != null){
            updateEquipment.setAttr2(0);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(updateEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }
        }
        if (mdWorkbenchWiredrawing.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchWiredrawing.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchWiredrawing.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchWiredrawing.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchWiredrawing.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchWiredrawing.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchWiredrawingMapper.update(mdWorkbenchWiredrawing);
    }
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    //@GlobalTransactional
    public int deleteByIds(BigInteger[] ids){
        //关联机台设置为未关联
        for (int i = 0; i < ids.length; i++) {
            MdWorkbenchWiredrawing mdWorkbenchWiredrawing = mdWorkbenchWiredrawingMapper.queryById(ids[i]);
            if (mdWorkbenchWiredrawing != null){
                EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchWiredrawing.getEquipmentId(), SecurityConstants.INNER).getData();
                if (eqEquipment != null){
                    eqEquipment.setAttr2(0);
                    R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
                    if (ajaxResultR.getCode() != 200){
                        throw new EqException("更新设备关联状态失败");
                    }
                }
            }
        }
        return mdWorkbenchWiredrawingMapper.deleteByIds(ids);
    }
     /**
      * 启用禁用
      */
     @Override
     //@GlobalTransactional
     public int updateEnable(MdWorkbenchWiredrawing mdWorkbenchWiredrawing) {
         //如果设备禁用，则设备台账状态变更为停机
         if (mdWorkbenchWiredrawing.getIsEnable() == 0){
             EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchWiredrawing.getEquipmentId(), SecurityConstants.INNER).getData();
             if (null == eqEquipment){
                 throw new EqException("该设备不存在");
             }
             eqEquipment.setStatus("1");
             R<AjaxResult> ajaxResultR1 = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
             if (ajaxResultR1.getCode() != 200){
                 throw new EqException("更新设备启用/禁用状态失败");
             }
             //如果设备禁用，则设备台账状态变更为正常
         }else{
             EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchWiredrawing.getEquipmentId(), SecurityConstants.INNER).getData();
             if (null == eqEquipment){
                 throw new EqException("该设备不存在");
             }
             eqEquipment.setStatus("0");
             R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
             if (ajaxResultR.getCode() != 200){
                 throw new EqException("更新设备启用/禁用状态失败");
             }
         }
         return mdWorkbenchWiredrawingMapper.update(mdWorkbenchWiredrawing);
     }
 }
