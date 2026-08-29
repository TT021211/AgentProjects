package com.cuixing.crm.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.crm.entity.CrmClient;
import com.cuixing.crm.entity.CrmClientInterview;
import com.cuixing.crm.entity.vo.CrmClientAnalyze;
import com.cuixing.crm.mapper.CrmClientInterviewMapper;
import com.cuixing.crm.mapper.CrmClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.crm.service.ICrmClientService;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 客户表;(crm_client)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class CrmClientServiceImpl implements ICrmClientService{
    @Autowired
    private CrmClientMapper crmClientMapper;

    @Autowired
    private CrmClientInterviewMapper crmClientInterviewMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public CrmClient queryById(BigInteger id){
        return crmClientMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param crmClient 筛选条件
     * @return 查询结果
     */
    public List<CrmClient> selectCrmClientList(CrmClient crmClient)
    {
        return crmClientMapper.selectCrmClientList(crmClient);
    }

    /**
     * 新增数据
     *
     * @param crmClient 实例对象
     * @return 影响行数
     */
    public int insert(CrmClient crmClient){
        crmClient.setCreateTime(DateUtils.getNowDate());
        crmClient.setCreateBy(SecurityUtils.getUsername());
        return crmClientMapper.insert(crmClient);
    }

    /**
     * 批量新增数据
     * @param crmClients 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<CrmClient> crmClients){
        return crmClientMapper.insertBatch(crmClients);
    }

    /**
     * 更新数据
     *
     * @param crmClient 实例对象
     * @return 影响行数
     */
    public int update(CrmClient crmClient){
        BigInteger id = crmClient.getId();
        CrmClient theUpdatecrmClient = crmClientMapper.queryById(id);
        Integer version = theUpdatecrmClient.getVersion();
        if(version != null) {
            crmClient.setVersion(version);
        }
        crmClient.setUpdateTime(DateUtils.getNowDate());
        crmClient.setUpdateBy(SecurityUtils.getUsername());
        return crmClientMapper.update(crmClient);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = crmClientMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = crmClientMapper.deleteByIds(ids);
        return total > 0;
    }

    @Override
    public CrmClientAnalyze getClientAnalyze() {
        //客户数量
        CrmClientAnalyze clientAnalyze = crmClientMapper.totalClientNum();
        //客户来源分析(来源-数量)
        List<Map<String, Object>> sourceMap = crmClientMapper.sourceClientNum();
        clientAnalyze.setSourceAnalyze(sourceMap);
        //跟进方式分布
        List<Map<String, Object>> typeMap = crmClientInterviewMapper.typeAnalyze();
        clientAnalyze.setTypeAnalyze(typeMap);
        //客户状态分析(潜在客户、高意向、已成交、待跟进等)
        List<Map<String, Object>> statusMap = crmClientMapper.statusAnalyze();
        clientAnalyze.setStatusAnalyze(statusMap);
        //客户地址分布
        List<Map<String, Object>> addressMap = crmClientMapper.addressAnalyze();
        clientAnalyze.setAddressAnalyze(addressMap);

        List<Map<String, Object>> interviewClientMonth = crmClientInterviewMapper.getInterviewClientMonth();
        clientAnalyze.setInterviewClientMonth(interviewClientMonth);
        return clientAnalyze;
    }

    @Override
    public Set<BigInteger> selectIds() {
        return crmClientMapper.selectIds();
    }
}