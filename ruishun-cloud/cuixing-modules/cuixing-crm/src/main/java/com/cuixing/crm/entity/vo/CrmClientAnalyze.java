package com.cuixing.crm.entity.vo;

import java.util.List;
import java.util.Map;

public class CrmClientAnalyze {
    //客户总数
    private Integer totalClient;

    //已成交客户数
    private Integer completeClient;

    //未成交客户数
    private Integer potentialClient;

    //普通客户
    private Integer normalClient;
    //客户
    private Integer importanceClient;
    //战略客户
    private Integer strategyClient;


    //客户来源分析(来源-数量)
    private List<Map<String,Object>> sourceAnalyze;

    //跟进方式分布
    private List<Map<String,Object>> typeAnalyze;

    //客户状态分析(潜在客户、高意向、已成交、待跟进等)
    private List<Map<String,Object>> statusAnalyze;

    //客户地址分布
    private List<Map<String,Object>> addressAnalyze;

    //跟进客户数量(按月份)
    private List<Map<String,Object>> interviewClientMonth;

    public Integer getTotalClient() {
        return totalClient;
    }

    public void setTotalClient(Integer totalClient) {
        this.totalClient = totalClient;
    }

    public Integer getCompleteClient() {
        return completeClient;
    }

    public void setCompleteClient(Integer completeClient) {
        this.completeClient = completeClient;
    }

    public Integer getPotentialClient() {
        return potentialClient;
    }

    public void setPotentialClient(Integer potentialClient) {
        this.potentialClient = potentialClient;
    }

    public List<Map<String, Object>> getSourceAnalyze() {
        return sourceAnalyze;
    }

    public void setSourceAnalyze(List<Map<String, Object>> sourceAnalyze) {
        this.sourceAnalyze = sourceAnalyze;
    }

    public List<Map<String, Object>> getTypeAnalyze() {
        return typeAnalyze;
    }

    public void setTypeAnalyze(List<Map<String, Object>> typeAnalyze) {
        this.typeAnalyze = typeAnalyze;
    }

    public List<Map<String, Object>> getStatusAnalyze() {
        return statusAnalyze;
    }

    public void setStatusAnalyze(List<Map<String, Object>> statusAnalyze) {
        this.statusAnalyze = statusAnalyze;
    }

    public List<Map<String, Object>> getAddressAnalyze() {
        return addressAnalyze;
    }

    public void setAddressAnalyze(List<Map<String, Object>> addressAnalyze) {
        this.addressAnalyze = addressAnalyze;
    }

    public List<Map<String, Object>> getInterviewClientMonth() {
        return interviewClientMonth;
    }

    public void setInterviewClientMonth(List<Map<String, Object>> interviewClientMonth) {
        this.interviewClientMonth = interviewClientMonth;
    }

    public Integer getNormalClient() {
        return normalClient;
    }

    public void setNormalClient(Integer normalClient) {
        this.normalClient = normalClient;
    }

    public Integer getImportanceClient() {
        return importanceClient;
    }

    public void setImportanceClient(Integer importanceClient) {
        this.importanceClient = importanceClient;
    }

    public Integer getStrategyClient() {
        return strategyClient;
    }

    public void setStrategyClient(Integer strategyClient) {
        this.strategyClient = strategyClient;
    }
}
