package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.md.entity.MdWorkbenchDoublewire;
import com.cuixing.md.entity.MdWorkbenchTinning;
import com.cuixing.md.mapper.MdWorkbenchDoublewireMapper;
import com.cuixing.md.service.MdWorkbenchDoublewireService;
import com.cuixing.system.api.RemoteEqService;
import com.cuixing.system.api.domain.eq.EqEquipment;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;

/**
 * 机台设置(MdWorkbenchDoublewire)表服务实现类
 *
 * @author makejava
 * @since 2024-11-25 16:46:09
 */
@Service("mdWorkbenchDoublewireService")
public class MdWorkbenchDoublewireServiceImpl implements MdWorkbenchDoublewireService {
    @Autowired
    private MdWorkbenchDoublewireMapper mdWorkbenchDoublewireMapper;
    @Autowired
    private RemoteEqService remoteEqService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdWorkbenchDoublewire queryById(BigInteger id) {
        return this.mdWorkbenchDoublewireMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdWorkbenchDoublewire 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdWorkbenchDoublewire> selectMdWorkbenchDoublewireList(MdWorkbenchDoublewire mdWorkbenchDoublewire) {
        return this.mdWorkbenchDoublewireMapper.selectMdWorkbenchDoublewireList(mdWorkbenchDoublewire);
    }

    /**
     * 新增数据
     *
     * @param mdWorkbenchDoublewire 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int insert(MdWorkbenchDoublewire mdWorkbenchDoublewire) {
        mdWorkbenchDoublewire.setCreateTime(DateUtils.getNowDate());
        mdWorkbenchDoublewire.setCreateBy(SecurityUtils.getUsername());
        if (mdWorkbenchDoublewire.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchDoublewire.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchDoublewire.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchDoublewire.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchDoublewire.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchDoublewire.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchDoublewireMapper.insert(mdWorkbenchDoublewire);
    }

    /**
     * 修改数据
     *
     * @param mdWorkbenchDoublewire 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int update(MdWorkbenchDoublewire mdWorkbenchDoublewire) {
        mdWorkbenchDoublewire.setUpdateTime(DateUtils.getNowDate());
        mdWorkbenchDoublewire.setUpdateBy(SecurityUtils.getUsername());
        //修改之前关联的机台为未关联
        MdWorkbenchDoublewire updateDoublewire = mdWorkbenchDoublewireMapper.queryById(mdWorkbenchDoublewire.getId());
        EqEquipment updateEquipment = remoteEqService.queryById(updateDoublewire.getEquipmentId(), SecurityConstants.INNER).getData();
        if (updateEquipment != null){
            updateEquipment.setAttr2(0);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(updateEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }
        }
        if (mdWorkbenchDoublewire.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchDoublewire.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR2 = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR2.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchDoublewire.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchDoublewire.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchDoublewire.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchDoublewire.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchDoublewireMapper.update(mdWorkbenchDoublewire);
    }


    @Override
    @GlobalTransactional
    public int updateEnable(MdWorkbenchDoublewire mdWorkbenchDoublewire) {
        //如果设备禁用，则设备台账状态变更为停机
        if (mdWorkbenchDoublewire.getIsEnable() == 0){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchDoublewire.getEquipmentId(), SecurityConstants.INNER).getData();
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
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchDoublewire.getEquipmentId(), SecurityConstants.INNER).getData();
            if (null == eqEquipment){
                throw new EqException("该设备不存在");
            }
            eqEquipment.setStatus("0");
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备启用/禁用状态失败");
            }
        }
        return mdWorkbenchDoublewireMapper.update(mdWorkbenchDoublewire);
    }

    @Override
    @GlobalTransactional
    public int deleteByIds(BigInteger[] ids) {
        //关联机台设置为未关联
        for (int i = 0; i < ids.length; i++) {
            MdWorkbenchDoublewire mdWorkbenchDoublewire= mdWorkbenchDoublewireMapper.queryById(ids[i]);
            if (mdWorkbenchDoublewire != null){
                EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchDoublewire.getEquipmentId(), SecurityConstants.INNER).getData();
                if (eqEquipment != null){
                    eqEquipment.setAttr2(0);
                    R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
                    if (ajaxResultR.getCode() != 200){
                        throw new EqException("更新设备关联状态失败");
                    }
                }
            }
        }
        return mdWorkbenchDoublewireMapper.deleteByIds(ids);
    }
}
