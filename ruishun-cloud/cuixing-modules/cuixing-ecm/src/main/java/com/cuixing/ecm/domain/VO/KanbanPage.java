package com.cuixing.ecm.domain.VO;

import java.util.List;
import java.util.Map;

public class KanbanPage {

    private List<ElectricQuantity> monthElectricQuantity;

    private List<ElectricQuantity> yearElectricQuantity;

    private List<WeeksElectricQuantity> weeksElectricQuantity;

    public List<ElectricQuantity> getMonthElectricQuantity() {
        return monthElectricQuantity;
    }

    public void setMonthElectricQuantity(List<ElectricQuantity> monthElectricQuantity) {
        this.monthElectricQuantity = monthElectricQuantity;
    }

    public List<ElectricQuantity> getYearElectricQuantity() {
        return yearElectricQuantity;
    }

    public void setYearElectricQuantity(List<ElectricQuantity> yearElectricQuantity) {
        this.yearElectricQuantity = yearElectricQuantity;
    }

    public List<WeeksElectricQuantity> getWeeksElectricQuantity() {
        return weeksElectricQuantity;
    }

    public void setWeeksElectricQuantity(List<WeeksElectricQuantity> weeksElectricQuantity) {
        this.weeksElectricQuantity = weeksElectricQuantity;
    }
}
