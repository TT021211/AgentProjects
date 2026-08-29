package com.cuixing.ecm.domain.VO;

import java.util.List;

public class ElectricityConsumptionDate {

    private String electricDeviceCode;
    private String electricDeviceName;
    private String location;
    private String isMain;
    private int totalUsage;

    public String getElectricDeviceCode() {
        return electricDeviceCode;
    }

    public void setElectricDeviceCode(String electricDeviceCode) {
        this.electricDeviceCode = electricDeviceCode;
    }

    public String getElectricDeviceName() {
        return electricDeviceName;
    }

    public void setElectricDeviceName(String electricDeviceName) {
        this.electricDeviceName = electricDeviceName;
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    public int getTotalUsage() {
        return totalUsage;
    }

    public void setTotalUsage(int totalUsage) {
        this.totalUsage = totalUsage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
