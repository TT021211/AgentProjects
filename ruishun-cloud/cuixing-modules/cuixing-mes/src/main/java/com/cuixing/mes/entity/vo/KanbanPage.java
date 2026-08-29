package com.cuixing.mes.entity.vo;

import java.util.List;

public class KanbanPage {
    //排产中工单
    private Integer schedulingWorkorder;

    //排产中工单
    private Integer productionWorkorder;

    //排产中工单
    private Integer completeWorkorder;

    //一周内排单、完工数量
    private List<MesPageWeek> mesProductWorkorderWeek;

    //一周内拉丝车间投料报工搬运总数量
    private List<MesPageWeek> wiredrawingNumWeek;

    //一周内搬运总数量
    private List<MesPageWeek> carryNumWeek;


    public Integer getSchedulingWorkorder() {
        return schedulingWorkorder;
    }

    public void setSchedulingWorkorder(Integer schedulingWorkorder) {
        this.schedulingWorkorder = schedulingWorkorder;
    }

    public Integer getProductionWorkorder() {
        return productionWorkorder;
    }

    public void setProductionWorkorder(Integer productionWorkorder) {
        this.productionWorkorder = productionWorkorder;
    }

    public Integer getCompleteWorkorder() {
        return completeWorkorder;
    }

    public void setCompleteWorkorder(Integer completeWorkorder) {
        this.completeWorkorder = completeWorkorder;
    }

    public List<MesPageWeek> getMesProductWorkorderWeek() {
        return mesProductWorkorderWeek;
    }

    public void setMesProductWorkorderWeek(List<MesPageWeek> mesProductWorkorderWeek) {
        this.mesProductWorkorderWeek = mesProductWorkorderWeek;
    }

    public List<MesPageWeek> getWiredrawingNumWeek() {
        return wiredrawingNumWeek;
    }

    public void setWiredrawingNumWeek(List<MesPageWeek> wiredrawingNumWeek) {
        this.wiredrawingNumWeek = wiredrawingNumWeek;
    }

    public List<MesPageWeek> getCarryNumWeek() {
        return carryNumWeek;
    }

    public void setCarryNumWeek(List<MesPageWeek> carryNumWeek) {
        this.carryNumWeek = carryNumWeek;
    }
}
