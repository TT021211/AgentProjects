package com.cuixing.eq.entity.vo;

import com.cuixing.eq.entity.EqRepairReport;

import java.util.List;

public class EqKanban {
    //漆包机运行状态台数
    private Integer enamellingNormalCount;
    //漆包机停机状态中台数
    private Integer enamellingStopCount;
    //漆包机总台数
    private Integer enamellingCount;

    //拉丝机运行状态台数
    private Integer wiredrawingNormalCount;
    //拉丝机停机状态中台数
    private Integer wiredrawingStopCount;
    //拉丝机总台数
    private Integer wiredrawingCount;

    //今日待点检设备数量
    private Integer checkCount;
    //今日待保养设备数量
    private Integer maintainCount;

    //待维修数量
    private Integer awaitRepairCount;
    //今日报修数量
    private Integer todayApplyRepairCount;
    //今日已维修数量
    private Integer todayRepairCount;

    //待维修设备列表
    private List<EqRepairReport> eqRepairReportList;

    //近2天已维修设备
    private List<EqRepairReport> completeRepairReportList;

    public Integer getEnamellingNormalCount() {
        return enamellingNormalCount;
    }

    public void setEnamellingNormalCount(Integer enamellingNormalCount) {
        this.enamellingNormalCount = enamellingNormalCount;
    }

    public Integer getEnamellingStopCount() {
        return enamellingStopCount;
    }

    public void setEnamellingStopCount(Integer enamellingStopCount) {
        this.enamellingStopCount = enamellingStopCount;
    }

    public Integer getEnamellingCount() {
        return enamellingCount;
    }

    public void setEnamellingCount(Integer enamellingCount) {
        this.enamellingCount = enamellingCount;
    }

    public Integer getWiredrawingNormalCount() {
        return wiredrawingNormalCount;
    }

    public void setWiredrawingNormalCount(Integer wiredrawingNormalCount) {
        this.wiredrawingNormalCount = wiredrawingNormalCount;
    }

    public Integer getWiredrawingStopCount() {
        return wiredrawingStopCount;
    }

    public void setWiredrawingStopCount(Integer wiredrawingStopCount) {
        this.wiredrawingStopCount = wiredrawingStopCount;
    }

    public Integer getWiredrawingCount() {
        return wiredrawingCount;
    }

    public void setWiredrawingCount(Integer wiredrawingCount) {
        this.wiredrawingCount = wiredrawingCount;
    }

    public Integer getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }

    public Integer getMaintainCount() {
        return maintainCount;
    }

    public void setMaintainCount(Integer maintainCount) {
        this.maintainCount = maintainCount;
    }

    public List<EqRepairReport> getEqRepairReportList() {
        return eqRepairReportList;
    }

    public void setEqRepairReportList(List<EqRepairReport> eqRepairReportList) {
        this.eqRepairReportList = eqRepairReportList;
    }

    public Integer getAwaitRepairCount() {
        return awaitRepairCount;
    }

    public void setAwaitRepairCount(Integer awaitRepairCount) {
        this.awaitRepairCount = awaitRepairCount;
    }

    public Integer getTodayRepairCount() {
        return todayRepairCount;
    }

    public void setTodayRepairCount(Integer todayRepairCount) {
        this.todayRepairCount = todayRepairCount;
    }

    public List<EqRepairReport> getCompleteRepairReportList() {
        return completeRepairReportList;
    }

    public void setCompleteRepairReportList(List<EqRepairReport> completeRepairReportList) {
        this.completeRepairReportList = completeRepairReportList;
    }

    public Integer getTodayApplyRepairCount() {
        return todayApplyRepairCount;
    }

    public void setTodayApplyRepairCount(Integer todayApplyRepairCount) {
        this.todayApplyRepairCount = todayApplyRepairCount;
    }
}
