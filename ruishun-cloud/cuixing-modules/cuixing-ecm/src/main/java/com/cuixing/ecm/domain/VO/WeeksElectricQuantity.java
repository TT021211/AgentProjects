package com.cuixing.ecm.domain.VO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class WeeksElectricQuantity {

    private String workshopType;

    private String week;
    @JsonFormat(pattern = "MM-dd")
    private Date recordDay;

    private Integer totalUsage;

    public String getWorkshopType() {
        return workshopType;
    }

    public void setWorkshopType(String workshopType) {
        this.workshopType = workshopType;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Date getRecordDay() {
        return recordDay;
    }

    public void setRecordDay(Date recordDay) {
        this.recordDay = recordDay;
    }

    public Integer getTotalUsage() {
        return totalUsage;
    }

    public void setTotalUsage(Integer totalUsage) {
        this.totalUsage = totalUsage;
    }
}
