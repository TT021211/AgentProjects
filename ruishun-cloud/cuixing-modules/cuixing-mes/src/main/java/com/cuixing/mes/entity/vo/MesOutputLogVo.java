package com.cuixing.mes.entity.vo;

import com.cuixing.mes.entity.MesOuputLog;

import java.util.List;

public class MesOutputLogVo {

    //总数
    private Integer count;

    //总皮重
    private Double totalTareWeight;

    //总净重
    private Double totalNetWeight;

    private List<MesOuputLog> MesOutputLogList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getTotalTareWeight() {
        return totalTareWeight;
    }

    public void setTotalTareWeight(Double totalTareWeight) {
        this.totalTareWeight = totalTareWeight;
    }

    public Double getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(Double totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public List<MesOuputLog> getMesOutputLogList() {
        return MesOutputLogList;
    }

    public void setMesOutputLogList(List<MesOuputLog> mesOutputLogList) {
        MesOutputLogList = mesOutputLogList;
    }
}
