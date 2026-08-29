package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.md.entity.MdWorkbenchStrandedwire;
import com.cuixing.md.entity.MdWorkbenchTinning;
import com.cuixing.md.mapper.MdWorkbenchStrandedwireMapper;
import com.cuixing.md.service.MdWorkbenchStrandedwireService;
import com.cuixing.system.api.RemoteEqService;
import com.cuixing.system.api.domain.eq.EqEquipment;
import io.seata.spring.annotation.GlobalTransactional;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;

/**
 * 机台设置(MdWorkbenchStrandedwire)表服务实现类
 *
 * @author makejava
 * @since 2024-11-25 16:46:10
 */
@Service("mdWorkbenchStrandedwireService")
public class MdWorkbenchStrandedwireServiceImpl implements MdWorkbenchStrandedwireService {
    @Autowired
    private MdWorkbenchStrandedwireMapper mdWorkbenchStrandedwireMapper;
    @Autowired
    private RemoteEqService remoteEqService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdWorkbenchStrandedwire queryById(BigInteger id) {
        return this.mdWorkbenchStrandedwireMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdWorkbenchStrandedwire 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdWorkbenchStrandedwire> selectMdWorkbenchStrandedwireList(MdWorkbenchStrandedwire mdWorkbenchStrandedwire) {
        return this.mdWorkbenchStrandedwireMapper.selectMdWorkbenchStrandedwireList(mdWorkbenchStrandedwire);
    }

    /**
     * 新增数据
     *
     * @param mdWorkbenchStrandedwire 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int insert(MdWorkbenchStrandedwire mdWorkbenchStrandedwire) {
        mdWorkbenchStrandedwire.setCreateTime(DateUtils.getNowDate());
        mdWorkbenchStrandedwire.setCreateBy(SecurityUtils.getUsername());
        if (mdWorkbenchStrandedwire.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchStrandedwire.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchStrandedwire.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchStrandedwire.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchStrandedwire.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchStrandedwire.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchStrandedwireMapper.insert(mdWorkbenchStrandedwire);
    }

    /**
     * 修改数据
     *
     * @param mdWorkbenchStrandedwire 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int update(MdWorkbenchStrandedwire mdWorkbenchStrandedwire) {
        mdWorkbenchStrandedwire.setUpdateTime(DateUtils.getNowDate());
        mdWorkbenchStrandedwire.setUpdateBy(SecurityUtils.getUsername());
        //修改之前关联的机台为未关联
        MdWorkbenchStrandedwire updateStrandedwire = mdWorkbenchStrandedwireMapper.queryById(mdWorkbenchStrandedwire.getId());
        EqEquipment updateEquipment = remoteEqService.queryById(updateStrandedwire.getEquipmentId(), SecurityConstants.INNER).getData();
        if (updateEquipment != null){
            updateEquipment.setAttr2(0);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(updateEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }
        }
        if (mdWorkbenchStrandedwire.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchStrandedwire.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR2 = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR2.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchStrandedwire.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchStrandedwire.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchStrandedwire.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchStrandedwire.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchStrandedwireMapper.update(mdWorkbenchStrandedwire);
    }


    @Override
    @GlobalTransactional
    public int updateEnable(MdWorkbenchStrandedwire mdWorkbenchStrandedwire) {
        //如果设备禁用，则设备台账状态变更为停机
        if (mdWorkbenchStrandedwire.getIsEnable() == 0){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchStrandedwire.getEquipmentId(), SecurityConstants.INNER).getData();
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
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchStrandedwire.getEquipmentId(), SecurityConstants.INNER).getData();
            if (null == eqEquipment){
                throw new EqException("该设备不存在");
            }
            eqEquipment.setStatus("0");
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备启用/禁用状态失败");
            }
        }
        return mdWorkbenchStrandedwireMapper.update(mdWorkbenchStrandedwire);
    }

    @Override
    @GlobalTransactional
    public int deleteByIds(BigInteger[] ids) {
        //关联机台设置为未关联
        for (int i = 0; i < ids.length; i++) {
            MdWorkbenchStrandedwire mdWorkbenchStrandedwire= mdWorkbenchStrandedwireMapper.queryById(ids[i]);
            if (mdWorkbenchStrandedwire != null){
                EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchStrandedwire.getEquipmentId(), SecurityConstants.INNER).getData();
                if (eqEquipment != null){
                    eqEquipment.setAttr2(0);
                    R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
                    if (ajaxResultR.getCode() != 200){
                        throw new EqException("更新设备关联状态失败");
                    }
                }
            }
        }
        return mdWorkbenchStrandedwireMapper.deleteByIds(ids);
    }
}
