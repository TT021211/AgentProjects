package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.md.entity.MdWorkbenchAnnealing;
import com.cuixing.md.entity.MdWorkbenchEnamelling;
import com.cuixing.md.mapper.MdWorkbenchAnnealingMapper;
import com.cuixing.md.service.MdWorkbenchAnnealingService;
import com.cuixing.system.api.RemoteEqService;
import com.cuixing.system.api.domain.eq.EqEquipment;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;

/**
 * 机台设置(MdWorkbenchAnnealing)表服务实现类
 *
 * @author makejava
 * @since 2024-11-25 16:46:09
 */
@Service("mdWorkbenchAnnealingService")
public class MdWorkbenchAnnealingServiceImpl implements MdWorkbenchAnnealingService {
    @Resource
    private MdWorkbenchAnnealingMapper mdWorkbenchAnnealingMapper;
    @Autowired
    private RemoteEqService remoteEqService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdWorkbenchAnnealing queryById(BigInteger id) {
        return this.mdWorkbenchAnnealingMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdWorkbenchAnnealing 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdWorkbenchAnnealing> selectMdWorkbenchAnnealingList(MdWorkbenchAnnealing mdWorkbenchAnnealing) {
        return this.mdWorkbenchAnnealingMapper.selectMdWorkbenchAnnealingList(mdWorkbenchAnnealing);
    }

    /**
     * 新增数据
     *
     * @param mdWorkbenchAnnealing 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int insert(MdWorkbenchAnnealing mdWorkbenchAnnealing) {
        mdWorkbenchAnnealing.setCreateTime(DateUtils.getNowDate());
        mdWorkbenchAnnealing.setCreateBy(SecurityUtils.getUsername());
        if (mdWorkbenchAnnealing.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchAnnealing.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchAnnealing.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchAnnealing.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchAnnealing.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchAnnealing.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchAnnealingMapper.insert(mdWorkbenchAnnealing);
    }

    /**
     * 修改数据
     *
     * @param mdWorkbenchAnnealing 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int update(MdWorkbenchAnnealing mdWorkbenchAnnealing) {
        mdWorkbenchAnnealing.setUpdateTime(DateUtils.getNowDate());
        mdWorkbenchAnnealing.setUpdateBy(SecurityUtils.getUsername());
        //修改之前关联的机台为未关联
        MdWorkbenchAnnealing updateAnnealing = mdWorkbenchAnnealingMapper.queryById(mdWorkbenchAnnealing.getId());
        EqEquipment updateEquipment = remoteEqService.queryById(updateAnnealing.getEquipmentId(), SecurityConstants.INNER).getData();
        if (updateEquipment != null){
            updateEquipment.setAttr2(0);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(updateEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }
        }
        if (mdWorkbenchAnnealing.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchAnnealing.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR2 = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR2.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchAnnealing.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchAnnealing.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchAnnealing.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchAnnealing.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchAnnealingMapper.update(mdWorkbenchAnnealing);
    }

    @Override
    public boolean deleteById(BigInteger id) {
        return false;
    }

    @Override
    @GlobalTransactional
    public int updateEnable(MdWorkbenchAnnealing mdWorkbenchAnnealing) {
        //如果设备禁用，则设备台账状态变更为停机
        if (mdWorkbenchAnnealing.getIsEnable() == 0){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchAnnealing.getEquipmentId(), SecurityConstants.INNER).getData();
            if (null == eqEquipment){
                throw new EqException("该设备不存在");
            }
            eqEquipment.setStatus("1");
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备启用/禁用状态失败");
            }
            //如果设备禁用，则设备台账状态变更为正常
        }else{
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchAnnealing.getEquipmentId(), SecurityConstants.INNER).getData();
            if (null == eqEquipment){
                throw new EqException("该设备不存在");
            }
            eqEquipment.setStatus("0");
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备启用/禁用状态失败");
            }
        }
        return mdWorkbenchAnnealingMapper.update(mdWorkbenchAnnealing);
    }

    @Override
    @GlobalTransactional
    public int deleteByIds(BigInteger[] ids) {
        //关联机台设置为未关联
        for (int i = 0; i < ids.length; i++) {
            MdWorkbenchAnnealing mdWorkbenchAnnealing = mdWorkbenchAnnealingMapper.queryById(ids[i]);
            if (mdWorkbenchAnnealing != null){
                EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchAnnealing.getEquipmentId(), SecurityConstants.INNER).getData();
                if (eqEquipment != null){
                    eqEquipment.setAttr2(0);
                    R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
                    if (ajaxResultR.getCode() != 200){
                        throw new EqException("更新设备关联状态失败");
                    }
                }
            }
        }
        return mdWorkbenchAnnealingMapper.deleteByIds(ids);
    }

}
