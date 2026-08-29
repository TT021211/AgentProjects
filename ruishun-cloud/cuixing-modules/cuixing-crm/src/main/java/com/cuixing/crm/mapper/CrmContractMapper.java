package com.cuixing.crm.mapper;



import com.cuixing.crm.entity.CrmContract;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * 销售合同Mapper接口
 *
 * @author zhan
 * @date 2024-04-17
 */
@Mapper
public interface CrmContractMapper
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
     * 查询销售合同状态
     *
     * @param contractId 销售合同主键
     * @return 销售合同状态
     */
    String selectContractStatus(Long contractId);
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
     * 删除销售合同
     *
     * @param contractId 销售合同主键
     * @return 结果
     */
    public int deleteCrmContractByContractId(Long contractId);

    /**
     * 批量删除销售合同
     *
     * @param contractIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrmContractByContractIds(Long[] contractIds);



    /**
     * 根据creat_time查询当日MaxDayContractNum
     * @param date
     * @return
     */
    public Integer selectMaxDayContractNum(Date date);

    int selectCountByContractCode(String contractCode);


    //List<CrmOrder> selectBindSalesOrder(Long contractId);
}
