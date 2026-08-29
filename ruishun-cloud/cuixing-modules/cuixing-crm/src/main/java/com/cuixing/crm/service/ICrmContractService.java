package com.cuixing.crm.service;

import com.cuixing.crm.entity.CrmContract;

import java.util.List;

/**
 * 销售合同Service接口
 *
 * @author zhan
 * @date 2024-04-17
 */
public interface ICrmContractService
{
    /**
     * 查询销售合同
     *
     * @param contractId 销售合同主键
     * @return 销售合同
     */
    public CrmContract selectCrmContractByContractId(Long contractId);

    /**
     * 查询销售合同列表
     *
     * @param crmContract 销售合同
     * @return 销售合同集合
     */
    public List<CrmContract> selectCrmContractList(CrmContract crmContract);


    /**
     * 根据合同id查询销售合同状态
     * @param contractId
     * @return 合同状态
     */
    public String selectContractStatus(Long contractId);

    /**
     * 新增销售合同
     *
     * @param crmContract 销售合同
     * @return 结果
     */
    public int insertCrmContract(CrmContract crmContract);


    /**
     * 修改销售合同
     *
     * @param crmContract 销售合同
     * @return 结果
     */
    public int updateCrmContract(CrmContract crmContract);

    /**
     * 批量删除销售合同
     *
     * @param contractIds 需要删除的销售合同主键集合
     * @return 结果
     */
    public Boolean deleteCrmContractByContractIds(Long[] contractIds);

    /**
     * 删除销售合同信息
     *
     * @param contractId 销售合同主键
     * @return 结果
     */
    public int deleteCrmContractByContractId(Long contractId);

    /**
     * 自动生成销售合同编号
     * @return 自动生成的销售合同编号
     */
    public String autoGenerateContractCode();

    /**
     * 判断是否销售合同编码是否重复
     */
    public Boolean checkContractCodeIsDuplicate(String contractCode);


//    List<ErpLockPriceOrder> selectBindLockPriceOrder(Long contractId);
}
