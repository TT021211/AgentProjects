package com.cuixing.mes.entity.vo;

import com.cuixing.mes.entity.MesEnamellingWorkorder;

import java.util.List;

public class MesWorkorderKanban {

    //排产中漆包工单
    private Integer schedulingNum;

    //生产中漆包工单
    private Integer productionNum;

    //已完成漆包工单
    private Integer completedNum;

    //今日排产工单数量
    private Integer todaySchedulingNum;

    //工单数据
    List<MesEnamellingWorkorder> mesEnamellingWorkorders;


    public Integer getSchedulingNum() {
        return schedulingNum;
    }

    public void setSchedulingNum(Integer schedulingNum) {
        this.schedulingNum = schedulingNum;
    }

    public Integer getProductionNum() {
        return productionNum;
    }

    public void setProductionNum(Integer productionNum) {
        this.productionNum = productionNum;
    }

    public Integer getCompletedNum() {
        return completedNum;
    }

    public void setCompletedNum(Integer completedNum) {
        this.completedNum = completedNum;
    }

    public Integer getTodaySchedulingNum() {
        return todaySchedulingNum;
    }

    public void setTodaySchedulingNum(Integer todaySchedulingNum) {
        this.todaySchedulingNum = todaySchedulingNum;
    }

    public List<MesEnamellingWorkorder> getMesEnamellingWorkorders() {
        return mesEnamellingWorkorders;
    }

    public void setMesEnamellingWorkorders(List<MesEnamellingWorkorder> mesEnamellingWorkorders) {
        this.mesEnamellingWorkorders = mesEnamellingWorkorders;
    }
}
