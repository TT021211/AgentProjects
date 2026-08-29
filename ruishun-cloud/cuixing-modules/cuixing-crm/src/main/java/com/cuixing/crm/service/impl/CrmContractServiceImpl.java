package com.cuixing.crm.service.impl;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.crm.entity.CrmContract;
import com.cuixing.crm.mapper.CrmContractMapper;
import com.cuixing.crm.service.ICrmContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 销售合同Service业务层处理
 *
 * @author zhan
 * @date 2024-04-17
 */
@Service
public class CrmContractServiceImpl implements ICrmContractService {

    @Autowired
    private CrmContractMapper crmContractMapper;


    /**
     * 查询销售合同
     *
     * @param contractId 销售合同主键
     * @return 销售合同
     */
    @Override
    public CrmContract selectCrmContractByContractId(Long contractId) {
        return crmContractMapper.selectCrmContractByContractId(contractId);
    }

    /**
     * 查询销售合同列表
     *
     * @param crmContract 销售合同
     * @return 销售合同
     */
    @Override
    public List<CrmContract> selectCrmContractList(CrmContract crmContract) {
        return crmContractMapper.selectCrmContractList(crmContract);
    }


    @Override
    public String selectContractStatus(Long contractId) {
        return crmContractMapper.selectContractStatus(contractId);
    }

    /**
     * 新增销售合同
     *
     * @param crmContract 销售合同
     * @return 结果
     */
    @Override
    public int insertCrmContract(CrmContract crmContract) {
        crmContract.setCreateTime(DateUtils.getNowDate());
        return crmContractMapper.insertCrmContract(crmContract);
    }


    /**
     * 修改销售合同主表
     *
     * @param crmContract 销售合同
     * @return 结果
     */
    @Override
    public int updateCrmContract(CrmContract crmContract) {
        crmContract.setUpdateTime(DateUtils.getNowDate());
        return crmContractMapper.updateCrmContract(crmContract);
    }

    /**
     * 批量删除销售合同和明细
     *
     * @param contractIds 需要删除的销售合同主键
     * @return 结果
     */
    @Override
    @Transactional
    public Boolean deleteCrmContractByContractIds(Long[] contractIds) {
        //根据合同ids删除相关合同明细
        for (Long contractId : contractIds) {
            deleteCrmContractByContractId(contractId);
        }
        return true;
    }

    /**
     * 删除销售合同信息
     *
     * @param contractId 销售合同主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteCrmContractByContractId(Long contractId) {
        return crmContractMapper.deleteCrmContractByContractId(contractId);
    }

    @Override
    public String autoGenerateContractCode() {
        //获取当前日期从年精确到日
        Date date = DateUtils.getNowDate();
        //从数据库中根据日期查询日合同序号的最大值
        Integer maxDayContractNum = selectMaxDayContractNum(date);
        String contractCode = "YH" + DateUtils.parseDateToStr("yyyyMMdd",date) + String.format("%03d", maxDayContractNum + 1);
        return contractCode;
    }

    @Override
    public Boolean checkContractCodeIsDuplicate(String contractCode) {
        int count = crmContractMapper.selectCountByContractCode(contractCode);
        if (count > 0){
            return true;
        }else {
            return false;
        }
    }

    //从数据库中根据日期查询日合同序号的最大值
    private Integer selectMaxDayContractNum(Date date) {

        Integer maxDayContractNum = crmContractMapper.selectMaxDayContractNum(date);
        if (maxDayContractNum == null) {
            maxDayContractNum = 0;
        }
        return maxDayContractNum;
    }

//    @Override
//    public List<ErpLockPriceOrder> selectBindLockPriceOrder(Long contractId) {
//        return null;
//    }
}
