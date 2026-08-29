package com.cuixing.mes.entity.vo;

import java.util.List;

public class MesCarryLogVo {

    private String jobNumber;

    private List<String> itemIdentifiers;

    private String attr1;

    private String taskType;

    private String deviceCode;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public List<String> getItemIdentifiers() {
        return itemIdentifiers;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public void setItemIdentifiers(List<String> itemIdentifiers) {
        this.itemIdentifiers = itemIdentifiers;
    }
}
