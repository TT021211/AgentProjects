package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.md.entity.MdWorkbenchWiredrawing;
import com.cuixing.system.api.RemoteEqService;
import com.cuixing.system.api.domain.eq.EqEquipment;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdWorkbenchEnamelling;
import com.cuixing.md.mapper.MdWorkbenchEnamellingMapper;
import com.cuixing.md.service.IMdWorkbenchEnamellingService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigInteger;
import java.util.List;
/**
 * 机台设置;(md_workbench_enamelling)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-3
 */
@Service
public class MdWorkbenchEnamellingServiceImpl implements IMdWorkbenchEnamellingService{
    @Autowired
    private MdWorkbenchEnamellingMapper mdWorkbenchEnamellingMapper;

    @Autowired
    private RemoteEqService remoteEqService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdWorkbenchEnamelling queryById(BigInteger id){
        return mdWorkbenchEnamellingMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdWorkbenchEnamelling 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdWorkbenchEnamelling> selectMdWorkbenchEnamellingList(MdWorkbenchEnamelling mdWorkbenchEnamelling){
        return mdWorkbenchEnamellingMapper.selectMdWorkbenchEnamellingList(mdWorkbenchEnamelling);
    }

    /**
     * 新增数据
     *
     * @param mdWorkbenchEnamelling 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int insert(MdWorkbenchEnamelling mdWorkbenchEnamelling){
        mdWorkbenchEnamelling.setCreateTime(DateUtils.getNowDate());
        mdWorkbenchEnamelling.setCreateBy(SecurityUtils.getUsername());
        if (mdWorkbenchEnamelling.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchEnamelling.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchEnamelling.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchEnamelling.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchEnamelling.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchEnamelling.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchEnamellingMapper.insert(mdWorkbenchEnamelling);
    }

    /**
     * 更新数据
     *
     * @param mdWorkbenchEnamelling 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int update(MdWorkbenchEnamelling mdWorkbenchEnamelling){
        mdWorkbenchEnamelling.setUpdateTime(DateUtils.getNowDate());
        mdWorkbenchEnamelling.setUpdateBy(SecurityUtils.getUsername());
        //修改之前关联的机台为未关联
        MdWorkbenchEnamelling updateEnamelling = mdWorkbenchEnamellingMapper.queryById(mdWorkbenchEnamelling.getId());
        EqEquipment updateEquipment = remoteEqService.queryById(updateEnamelling.getEquipmentId(), SecurityConstants.INNER).getData();
        if (updateEquipment != null){
            updateEquipment.setAttr2(0);
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(updateEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }
        }
        if (mdWorkbenchEnamelling.getEquipmentId() != null){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchEnamelling.getEquipmentId(), SecurityConstants.INNER).getData();
            if (eqEquipment == null){
                throw new EqException("设备不存在");
            }
            //修改机台关联状态为已关联
            eqEquipment.setAttr2(1);
            R<AjaxResult> ajaxResultR2 = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR2.getCode() != 200){
                throw new EqException("更新设备关联状态失败");
            }

            mdWorkbenchEnamelling.setEquipmentCode(eqEquipment.getEquipmentCode());
            mdWorkbenchEnamelling.setEquipmentName(eqEquipment.getEquipmentName());
            mdWorkbenchEnamelling.setWorkbenchTypeId(eqEquipment.getWorkbenchId());
            mdWorkbenchEnamelling.setWorkbenchType(eqEquipment.getWorkbenchType());
        }
        return mdWorkbenchEnamellingMapper.update(mdWorkbenchEnamelling);
    }
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    @GlobalTransactional
    public int deleteByIds(BigInteger[] ids){
        //关联机台设置为未关联
        for (int i = 0; i < ids.length; i++) {
            MdWorkbenchEnamelling mdWorkbenchEnamelling = mdWorkbenchEnamellingMapper.queryById(ids[i]);
            if (mdWorkbenchEnamelling != null){
                EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchEnamelling.getEquipmentId(), SecurityConstants.INNER).getData();
                if (eqEquipment != null){
                    eqEquipment.setAttr2(0);
                    R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
                    if (ajaxResultR.getCode() != 200){
                        throw new EqException("更新设备关联状态失败");
                    }
                }
            }
        }
        return mdWorkbenchEnamellingMapper.deleteByIds(ids);
    }

    /**
     * 启用禁用
     */
    @Override
    @GlobalTransactional
    public int updateEnable(MdWorkbenchEnamelling mdWorkbenchEnamelling) {
        //如果设备禁用，则设备台账状态变更为停机
        if (mdWorkbenchEnamelling.getIsEnable() == 0){
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchEnamelling.getEquipmentId(), SecurityConstants.INNER).getData();
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
            EqEquipment eqEquipment = remoteEqService.queryById(mdWorkbenchEnamelling.getEquipmentId(), SecurityConstants.INNER).getData();
            if (null == eqEquipment){
                throw new EqException("该设备不存在");
            }
            eqEquipment.setStatus("0");
            R<AjaxResult> ajaxResultR = remoteEqService.updateEqStatus(eqEquipment, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new EqException("更新设备启用/禁用状态失败");
            }
        }
        return mdWorkbenchEnamellingMapper.update(mdWorkbenchEnamelling);
    }
}
