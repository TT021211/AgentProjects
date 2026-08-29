package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.md.entity.vo.MdWorkbenchWeighVo;
import com.cuixing.system.api.RemoteEqService;
import com.cuixing.system.api.domain.eq.EqEquipment;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdWorkbenchWeigh;
import com.cuixing.md.mapper.MdWorkbenchWeighMapper;
import com.cuixing.md.service.IMdWorkbenchWeighService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;

import java.math.BigInteger;
import java.util.List;
 /**
 * 称重机台设置;(md_workbench_weigh)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-8-2
 */
@Service
public class MdWorkbenchWeighServiceImpl implements IMdWorkbenchWeighService{
    @Autowired
    private MdWorkbenchWeighMapper mdWorkbenchWeighMapper;
    @Autowired
    private RemoteEqService remoteEqService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdWorkbenchWeigh queryById(BigInteger id){
        return mdWorkbenchWeighMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdWorkbenchWeigh 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdWorkbenchWeigh> selectMdWorkbenchWeighList(MdWorkbenchWeigh mdWorkbenchWeigh){
       return mdWorkbenchWeighMapper.selectMdWorkbenchWeighList(mdWorkbenchWeigh);
    }

    /**
     * 新增数据
     *
     * @param mdWorkbenchWeigh 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MdWorkbenchWeigh mdWorkbenchWeigh){
        mdWorkbenchWeigh.setCreateTime(DateUtils.getNowDate());
        mdWorkbenchWeigh.setCreateBy(SecurityUtils.getUsername());
        if (mdWorkbenchWeigh.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchWeigh.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchWeigh.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchWeigh.setMachineName(eqEquipment.getEquipmentName());
            mdWorkbenchWeigh.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchWeigh.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchWeighMapper.insert(mdWorkbenchWeigh);
    }

    /**
     * 更新数据
     *
     * @param mdWorkbenchWeigh 实例对象
     * @return 实例对象
     */
    @Override
    //@GlobalTransactional
    public int update(MdWorkbenchWeigh mdWorkbenchWeigh){
        mdWorkbenchWeigh.setUpdateTime(DateUtils.getNowDate());
        mdWorkbenchWeigh.setUpdateBy(SecurityUtils.getUsername());
        //修改之前关联的机台为未关联
        MdWorkbenchWeigh updateWiredrawing = mdWorkbenchWeighMapper.queryById(mdWorkbenchWeigh.getId());
        EqEquipment updateEquipment = remoteEqService.queryById(updateWiredrawing.getEquipmentId(), SecurityConstants.INNER).getData();
        if (updateEquipment != null){
            updateEquipment.setAttr2(0);
            R<AjaxResult> ajaxResultR1 = remoteEqService.updateEqStatus(updateEquipment, SecurityConstants.INNER);
            if (ajaxResultR1.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }
        }
        if (mdWorkbenchWeigh.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchWeigh.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }
            mdWorkbenchWeigh.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchWeigh.setMachineName(eqEquipment.getEquipmentName());
            mdWorkbenchWeigh.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchWeigh.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchWeighMapper.update(mdWorkbenchWeigh);
    }
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        //关联机台设置为未关联
        for (int i = 0; i < ids.length; i++) {
            MdWorkbenchWeigh mdWorkbenchWeigh = mdWorkbenchWeighMapper.queryById(ids[i]);
            if (mdWorkbenchWeigh != null){
                EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchWeigh.getEquipmentId(), SecurityConstants.INNER).getData();
                if (eqEquipment == null){
                    throw new EqException("设备不存在");
                }
                eqEquipment.setAttr2(0);
                R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
                if (ajaxResultR.getCode() != 200){
                    throw new EqException("更新设备关联状态失败");
                }
            }
        }
        return mdWorkbenchWeighMapper.deleteByIds(ids);
    }

     /**
      * 启用禁用
      */
     @Override
     //@GlobalTransactional
     public int updateEnable(MdWorkbenchWeigh mdWorkbenchWeigh) {
         //如果设备禁用，则设备台账状态变更为停机
         if (mdWorkbenchWeigh.getIfEnable() == 0){
             EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchWeigh.getEquipmentId(), SecurityConstants.INNER).getData();
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
             EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchWeigh.getEquipmentId(), SecurityConstants.INNER).getData();
             if (null == eqEquipment){
                 throw new EqException("该设备不存在");
             }
             eqEquipment.setStatus("0");
             R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
             if (ajaxResultR.getCode() != 200){
                 throw new EqException("更新设备启用/禁用状态失败");
             }
         }
         return mdWorkbenchWeighMapper.update(mdWorkbenchWeigh);
     }

     @Override
     public List<MdWorkbenchWeighVo> selectMdWorkbenchWeighAll() {
         return mdWorkbenchWeighMapper.selectMdWorkbenchWeighAll();
     }
 }
