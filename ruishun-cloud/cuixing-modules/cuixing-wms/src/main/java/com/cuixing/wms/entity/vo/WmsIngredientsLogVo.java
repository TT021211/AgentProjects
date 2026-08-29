package com.cuixing.wms.entity.vo;

import com.cuixing.wms.entity.WmsIngredientsLog;

import java.math.BigDecimal;
import java.util.List;

public class WmsIngredientsLogVo {
    //总数
    private Integer count;

    //总皮重
    private Double totalTareWeight;

    //总净重
    private Double totalNetWeight;

    private List<WmsIngredientsLog> wmsIngredientsLogList;


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

    public List<WmsIngredientsLog> getWmsIngredientsLogList() {
        return wmsIngredientsLogList;
    }

    public void setWmsIngredientsLogList(List<WmsIngredientsLog> wmsIngredientsLogList) {
        this.wmsIngredientsLogList = wmsIngredientsLogList;
    }

    @Override
    public String toString() {
        return "WmsIngredientsLogVo{" +
                "count=" + count +
                ", totalTareWeight=" + totalTareWeight +
                ", totalNetWeight=" + totalNetWeight +
                ", wmsIngredientsLogList=" + wmsIngredientsLogList +
                '}';
    }
}
