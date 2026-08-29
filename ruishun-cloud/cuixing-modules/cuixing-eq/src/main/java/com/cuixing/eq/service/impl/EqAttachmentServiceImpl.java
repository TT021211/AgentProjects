package com.cuixing.eq.service.impl;

import com.cuixing.common.core.exception.ServiceException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.eq.entity.EqAttachment;
import com.cuixing.eq.mapper.EqAttachmentMapper;
import com.cuixing.eq.service.IEqAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 设备配件设置;(ems_attachment)表服务实现类
 *
 * @author : zhan
 * @date : 2024-7-11
 */
@Service
public class EqAttachmentServiceImpl implements IEqAttachmentService {
    @Autowired
    private EqAttachmentMapper eqAttachmentMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @retrn 实例对象
     */
    @Override
    public EqAttachment queryById(BigInteger id) {
        return eqAttachmentMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param emsAttachment 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqAttachment> selectEmsAttachmentList(EqAttachment emsAttachment) {
        return eqAttachmentMapper.selectEmsAttachmentList(emsAttachment);
    }

    /**
     * 新增数据
     *
     * @param eqAttachment 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqAttachment eqAttachment) {
        eqAttachment.setCreateTime(DateUtils.getNowDate());
        eqAttachment.setCreateBy(SecurityUtils.getUsername());
        return eqAttachmentMapper.insert(eqAttachment);
    }

    /**
     * 更新数据
     *
     * @param eqAttachment 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqAttachment eqAttachment) {
        eqAttachment.setUpdateTime(DateUtils.getNowDate());
        eqAttachment.setUpdateBy(SecurityUtils.getUsername());
        return eqAttachmentMapper.update(eqAttachment);
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids) {
        return eqAttachmentMapper.deleteByIds(ids);
    }

    /**
     * 通过设备id获取配件列表
     */
    @Override
    public List<EqAttachment> selectListByEquipmentId(BigInteger equipmentId) {
        return eqAttachmentMapper.selectListByEquipmentId(equipmentId);
    }

    /**
     * 通过设备id和生产重量批量修改已生产重量
     * @param equipmentId 设备id
     * @param producedWeight 生产重量
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean updateProducedWeight(BigInteger equipmentId, BigDecimal producedWeight) {
        //根据设备id查询设备配件列表
        List<EqAttachment> emsAttachmentList = eqAttachmentMapper.selectListByEquipmentId(equipmentId);
        //过滤掉最大生产重量为0的配件
        emsAttachmentList.removeIf(emsAttachment -> emsAttachment.getMaxServiceWeight().compareTo(BigDecimal.ZERO) == 0);
        for (EqAttachment emsAttachment : emsAttachmentList) {
            //判断是否为空
            if (emsAttachment.getProducedWeight() == null) {
                throw new ServiceException("已生产重量为null");
            }
            BigDecimal newProducedWeight=emsAttachment.getProducedWeight().add(producedWeight);
            //判断查询出的已生产重量与生产重量之和是否大于最大生产重量
            if (newProducedWeight.compareTo(emsAttachment.getMaxServiceWeight()) > 0) {
                //状态设为预警
                emsAttachment.setStatus("预警");
                //发送预警警告
            }
            emsAttachment.setProducedWeight(newProducedWeight);
            return eqAttachmentMapper.update(emsAttachment) > 0;
        }
       return true;
    }

    @Override
    @Transactional
    public boolean replace(BigInteger oldEmsAttachmentId, EqAttachment emsAttachment) {
        EqAttachment oldEmsAttachment = new EqAttachment();
        oldEmsAttachment.setId(oldEmsAttachmentId);
        oldEmsAttachment.setStatus("已更换");
        oldEmsAttachment.setReplaceTime(DateUtils.getNowDate());
        int update = eqAttachmentMapper.update(oldEmsAttachment);
        if(update!=1){
            throw new ServiceException("更新失败");
        }
        int insert = insert(emsAttachment);
        if (insert!=1){
            throw new ServiceException("创建失败");
        }
        return true;
    }

}