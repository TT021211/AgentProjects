package com.cuixing.ecm.service;

import java.util.List;
import com.cuixing.ecm.domain.EcmStepTariff;

/**
 * 阶梯电价Service接口
 * 
 * @author zhan
 * @date 2024-08-06
 */
public interface IEcmStepTariffService 
{
    /**
     * 查询阶梯电价
     * 
     * @param stepTariffId 阶梯电价主键
     * @return 阶梯电价
     */
    public EcmStepTariff selectEcmStepTariffByStepTariffId(Long stepTariffId);

    /**
     * 查询阶梯电价列表
     * 
     * @param ecmStepTariff 阶梯电价
     * @return 阶梯电价集合
     */
    public List<EcmStepTariff> selectEcmStepTariffList(EcmStepTariff ecmStepTariff);

    /**
     * 新增阶梯电价
     * 
     * @param ecmStepTariff 阶梯电价
     * @return 结果
     */
    public int insertEcmStepTariff(EcmStepTariff ecmStepTariff);

    /**
     * 修改阶梯电价
     * 
     * @param ecmStepTariff 阶梯电价
     * @return 结果
     */
    public int updateEcmStepTariff(EcmStepTariff ecmStepTariff);

    /**
     * 批量删除阶梯电价
     * 
     * @param stepTariffIds 需要删除的阶梯电价主键集合
     * @return 结果
     */
    public int deleteEcmStepTariffByStepTariffIds(Long[] stepTariffIds);

    /**
     * 删除阶梯电价信息
     * 
     * @param stepTariffId 阶梯电价主键
     * @return 结果
     */
    public int deleteEcmStepTariffByStepTariffId(Long stepTariffId);
}
