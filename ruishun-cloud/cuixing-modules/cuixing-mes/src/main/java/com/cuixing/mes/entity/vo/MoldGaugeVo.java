package com.cuixing.mes.entity.vo;

import java.math.BigInteger;
import java.util.List;

public class MoldGaugeVo {

    private BigInteger id;

    private List<String> equipmentCodeList;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public List<String> getEquipmentCodeList() {
        return equipmentCodeList;
    }

    public void setEquipmentCodeList(List<String> equipmentCodeList) {
        this.equipmentCodeList = equipmentCodeList;
    }
}
