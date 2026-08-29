package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.md.entity.MdWorkbenchAnnealing;
import com.cuixing.md.entity.MdWorkbenchTinning;
import com.cuixing.md.mapper.MdWorkbenchTinningMapper;
import com.cuixing.md.service.MdWorkbenchTinningService;
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
 * 机台设置(MdWorkbenchTinning)表服务实现类
 *
 * @author makejava
 * @since 2024-11-25 16:46:10
 */
@Service("mdWorkbenchTinningService")
public class MdWorkbenchTinningServiceImpl implements MdWorkbenchTinningService {
    @Autowired
    private MdWorkbenchTinningMapper mdWorkbenchTinningMapper;
    @Autowired
    private RemoteEqService remoteEqService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdWorkbenchTinning queryById(BigInteger id) {
        return this.mdWorkbenchTinningMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdWorkbenchTinning 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdWorkbenchTinning> selectMdWorkbenchTinningList(MdWorkbenchTinning mdWorkbenchTinning) {
        return this.mdWorkbenchTinningMapper.selectMdWorkbenchTinningList(mdWorkbenchTinning);
    }

    /**
     * 新增数据
     *
     * @param mdWorkbenchTinning 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int insert(MdWorkbenchTinning mdWorkbenchTinning) {
        mdWorkbenchTinning.setCreateTime(DateUtils.getNowDate());
        mdWorkbenchTinning.setCreateBy(SecurityUtils.getUsername());
        if (mdWorkbenchTinning.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchTinning.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchTinning.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchTinning.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchTinning.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchTinning.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchTinningMapper.insert(mdWorkbenchTinning);
    }

    /**
     * 修改数据
     *
     * @param mdWorkbenchTinning 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int update(MdWorkbenchTinning mdWorkbenchTinning) {
        mdWorkbenchTinning.setUpdateTime(DateUtils.getNowDate());
        mdWorkbenchTinning.setUpdateBy(SecurityUtils.getUsername());
        //修改之前关联的机台为未关联
        MdWorkbenchTinning updateTinning = mdWorkbenchTinningMapper.queryById(mdWorkbenchTinning.getId());
        EqEquipment updateEquipment = remoteEqService.queryById(updateTinning.getEquipmentId(), SecurityConstants.INNER).getData();
        if (updateEquipment != null){
            updateEquipment.setAttr2(0);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(updateEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }
        }
        if (mdWorkbenchTinning.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchTinning.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR2 = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR2.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchTinning.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchTinning.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchTinning.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchTinning.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchTinningMapper.update(mdWorkbenchTinning);
    }


    @Override
    @GlobalTransactional
    public int updateEnable(MdWorkbenchTinning mdWorkbenchTinning) {
        //如果设备禁用，则设备台账状态变更为停机
        if (mdWorkbenchTinning.getIsEnable() == 0){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchTinning.getEquipmentId(), SecurityConstants.INNER).getData();
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
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchTinning.getEquipmentId(), SecurityConstants.INNER).getData();
            if (null == eqEquipment){
                throw new EqException("该设备不存在");
            }
            eqEquipment.setStatus("0");
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备启用/禁用状态失败");
            }
        }
        return mdWorkbenchTinningMapper.update(mdWorkbenchTinning);
    }

    @Override
    @GlobalTransactional
    public int deleteByIds(BigInteger[] ids) {
        //关联机台设置为未关联
        for (int i = 0; i < ids.length; i++) {
            MdWorkbenchTinning mdWorkbenchTinning= mdWorkbenchTinningMapper.queryById(ids[i]);
            if (mdWorkbenchTinning != null){
                EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchTinning.getEquipmentId(), SecurityConstants.INNER).getData();
                if (eqEquipment != null){
                    eqEquipment.setAttr2(0);
                    R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
                    if (ajaxResultR.getCode() != 200){
                        throw new EqException("更新设备关联状态失败");
                    }
                }
            }
        }
        return mdWorkbenchTinningMapper.deleteByIds(ids);
    }

}
