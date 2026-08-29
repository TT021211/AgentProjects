package com.cuixing.ecm.service.impl;

import java.util.List;
import com.cuixing.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.ecm.mapper.EcmStepTariffMapper;
import com.cuixing.ecm.domain.EcmStepTariff;
import com.cuixing.ecm.service.IEcmStepTariffService;

/**
 * 阶梯电价Service业务层处理
 * 
 * @author zhan
 * @date 2024-08-06
 */
@Service
public class EcmStepTariffServiceImpl implements IEcmStepTariffService 
{
    @Autowired
    private EcmStepTariffMapper ecmStepTariffMapper;

    /**
     * 查询阶梯电价
     * 
     * @param stepTariffId 阶梯电价主键
     * @return 阶梯电价
     */
    @Override
    public EcmStepTariff selectEcmStepTariffByStepTariffId(Long stepTariffId)
    {
        return ecmStepTariffMapper.selectEcmStepTariffByStepTariffId(stepTariffId);
    }

    /**
     * 查询阶梯电价列表
     * 
     * @param ecmStepTariff 阶梯电价
     * @return 阶梯电价
     */
    @Override
    public List<EcmStepTariff> selectEcmStepTariffList(EcmStepTariff ecmStepTariff)
    {
        return ecmStepTariffMapper.selectEcmStepTariffList(ecmStepTariff);
    }

    /**
     * 新增阶梯电价
     * 
     * @param ecmStepTariff 阶梯电价
     * @return 结果
     */
    @Override
    public int insertEcmStepTariff(EcmStepTariff ecmStepTariff)
    {
        ecmStepTariff.setCreateTime(DateUtils.getNowDate());
        return ecmStepTariffMapper.insertEcmStepTariff(ecmStepTariff);
    }

    /**
     * 修改阶梯电价
     * 
     * @param ecmStepTariff 阶梯电价
     * @return 结果
     */
    @Override
    public int updateEcmStepTariff(EcmStepTariff ecmStepTariff)
    {
        ecmStepTariff.setUpdateTime(DateUtils.getNowDate());
        return ecmStepTariffMapper.updateEcmStepTariff(ecmStepTariff);
    }

    /**
     * 批量删除阶梯电价
     * 
     * @param stepTariffIds 需要删除的阶梯电价主键
     * @return 结果
     */
    @Override
    public int deleteEcmStepTariffByStepTariffIds(Long[] stepTariffIds)
    {
        return ecmStepTariffMapper.deleteEcmStepTariffByStepTariffIds(stepTariffIds);
    }

    /**
     * 删除阶梯电价信息
     * 
     * @param stepTariffId 阶梯电价主键
     * @return 结果
     */
    @Override
    public int deleteEcmStepTariffByStepTariffId(Long stepTariffId)
    {
        return ecmStepTariffMapper.deleteEcmStepTariffByStepTariffId(stepTariffId);
    }
}
