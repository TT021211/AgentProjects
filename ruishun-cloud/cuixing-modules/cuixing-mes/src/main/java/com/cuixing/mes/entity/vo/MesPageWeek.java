package com.cuixing.mes.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MesPageWeek {

    @JsonFormat(pattern = "MM-dd")
    private Date date;

    private String week;

    private Integer schedulingNum;

    private Integer completedNum;

    private Integer putNum;

    private Integer outNum;

    private Integer carryNum;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Integer getSchedulingNum() {
        return schedulingNum;
    }

    public void setSchedulingNum(Integer schedulingNum) {
        this.schedulingNum = schedulingNum;
    }

    public Integer getCompletedNum() {
        return completedNum;
    }

    public void setCompletedNum(Integer completedNum) {
        this.completedNum = completedNum;
    }

    public Integer getPutNum() {
        return putNum;
    }

    public void setPutNum(Integer putNum) {
        this.putNum = putNum;
    }

    public Integer getOutNum() {
        return outNum;
    }

    public void setOutNum(Integer outNum) {
        this.outNum = outNum;
    }

    public Integer getCarryNum() {
        return carryNum;
    }

    public void setCarryNum(Integer carryNum) {
        this.carryNum = carryNum;
    }
}
